/**
 *  Copyright 2009-2015 Universite Paris Ouest and Sorbonne Universites,
 * 							Univ. Paris 06 - CNRS UMR 7606 (LIP6)
 *
 *  All rights reserved.   This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Project leader / Initial Contributor:
 *    Lom Messan Hillah - <lom-messan.hillah@lip6.fr>
 *
 *  Contributors:
 *    ${ocontributors} - <$oemails}>
 *
 *  Mailing list:
 *    lom-messan.hillah@lip6.fr
 */
package fr.lip6.move.cpnami.cami.c2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiChunk;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiRepositoryImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.utils.CLOptions;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;
import fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI;

/**
 * <!-- begin-user-doc -->
 * <p>
 * Implements model transformation between Petri net CPN-AMI models PNML models.
 * </p>
 * <p>
 * First, Petri nets CPN-AMI models are parsed and stored in a repository as
 * Cami objects. Then these Cami objects are processed to create a a particular
 * type of Petri net (PT or SN). Afterwards, the built models are written into
 * PNML.
 * </p>
 * <p>
 * You can export one or several Cami models in one pass.
 * 
 * @see Cami2PnmlImpl2#cami2Pnml(List, List) and
 * @see Cami2PnmlImpl2#cami2p(Vector, String) </p>
 *      <!-- end-user-doc -->
 * 
 */
public final class Cami2PnmlImpl2 implements Cami2Pnml {

	private static final String EOF = "EOF";

	private static final String FILE_ENCONDING = "ISO-8859-1";

	private static final String END = "END";

	private static final String NL = "\n";

	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	private static final Logger JOURNAL = LogMaster.getLogger(Cami2PnmlImpl2.class.getCanonicalName());
	/**
	 * Factory initialization.
	 */
	protected static final CamiFactory CF = CamiFactory.SINSTANCE;
	/**
	 * Reading buffer size.
	 */
	protected static final int BUFFER_SIZE = 102400;

	private static final long MILLISEC_1000 = 1000L;

	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();

	private HashMap<String, String> camiToPnmlFilesMap;

	private HashMap<CamiRepository, String> camiRepToCamiFile;

	/**
	 * <!-- begin-user-doc --> Constructor. An instance of this class must be
	 * created through CamiFactory. <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Cami2PnmlImpl2() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> Main method to convert a (CPN-AMI) Petri net
	 * model written in CAMI language into PNML. More than one translation per
	 * session is offered. It means there may be many Petri nets to transform
	 * within that session. Therefore it is possible to add another Petri net
	 * from another Cami file into the same PNML destination file. <!--
	 * end-user-doc -->
	 * 
	 * @param camiFiles
	 *            list of input Cami files
	 * @param pnmlFilesOut
	 *            list of output PNML files where to write the converted models,
	 *            one per Cami file.
	 * @throws CamiException
	 *             CamiException
	 */
	public final void cami2Pnml(final List<String> camiFiles, final List<String> pnmlFilesOut) throws CamiException {

		try {
			linkCamiFilesToPnmlFiles(camiFiles, pnmlFilesOut);
			buildPNMLModels(camiFiles, null);
			// exportToPNML(pnmlFilesOut);
		} catch (final CamiException ce) {
			JOURNAL.error(ce.getMessage());
			throw ce;
		}
	}

	private void linkCamiFilesToPnmlFiles(List<String> camiFiles, List<String> pnmlFilesOut) {

		this.camiToPnmlFilesMap = new HashMap<String, String>();
		int i = 0;
		for (String cf : camiFiles) {
			camiToPnmlFilesMap.put(cf, pnmlFilesOut.get(i));
			i++;
		}
	}

	/**
	 * Builds PNs in PNML from Cami documents.
	 * 
	 * @param camiFiles
	 *            input Cami files
	 * @param clOptions
	 *            command-line options
	 * @throws CamiException
	 *             something went wrong during conversion
	 */
	private void buildPNMLModels(final List<String> camiFiles, final CLOptions clOptions) throws CamiException {
		// Initialization stuffs - How many CAMI files (CPN-AMI models) do we
		// have?
		int nbCamiFiles = 0;
		String camiFileIn = null;
		if (camiFiles != null && camiFiles.size() > 0) {
			nbCamiFiles = camiFiles.size();
		} else {
			final String message = "No input cami file(s)!";
			JOURNAL.error(message);
			throw new CamiException(message);
		}
		// Retrieves the singleton instance of CommandAnalyser. For String
		// manipulation.

		try {
			camiRepToCamiFile = new HashMap<CamiRepository, String>();
			BlockingQueue<CamiChunk> ccQueue = new LinkedBlockingQueue<CamiChunk>();
			BlockingQueue<String> notifQueue = new LinkedBlockingQueue<String>();
			CamiReader camiReader1 = new CamiReader(ccQueue, notifQueue);
			CamiReader camiReader2 = new CamiReader(ccQueue, notifQueue);
			Thread crTh1 = new Thread(camiReader1);
			Thread crTh2 = new Thread(camiReader2);
			crTh1.start();
			crTh2.start();

			BlockingQueue<Cami2PnmlInfoSet> c2pQueue = new LinkedBlockingQueue<Cami2PnmlInfoSet>();
			Cami2PnmlExporter exporter1 = new Cami2PnmlExporter(c2pQueue);
			Cami2PnmlExporter exporter2 = new Cami2PnmlExporter(c2pQueue);
			Thread c2pTh1 = new Thread(exporter1);
			Thread c2pTh2 = new Thread(exporter2);
			c2pTh1.start();
			c2pTh2.start();

			// starts the processing of CPN-AMI models (one per CAMI file)
			for (int i = 0; i < nbCamiFiles; i++) {
				camiFileIn = camiFiles.get(i);
				// extracts the net name from the Cami file name
				final String theNetName = myRunner.extractNameWithoutExtension(myRunner.extractLastName(camiFileIn));
				// Get camiFileIn opened - a cami command line is no more than
				// 256
				// characters.

				final CamiRepository cr = new CamiRepositoryImpl();
				File cfile = new File(camiFileIn);
				if (cfile.exists() && cfile.isFile()) {
					camiRepToCamiFile.put(cr, camiFileIn);
					readFileIntoQueue(cfile, cr, ccQueue);
				} else {
					JOURNAL.error(camiFileIn + " does not exist or is not a regular file.");
					continue;
				}

				/*
				 * final BufferedReader in = new BufferedReader(new FileReader(
				 * camiFileIn), BUFFER_SIZE); String aCommand;
				 * 
				 * 
				 * For each Cami command read, builds the corresponding command
				 * model with it. A CPN-AMI model in Cami is between DB() and
				 * FB() commands or DE() and FE() However, this method does not
				 * care if one these commands is reached. Instead, it reads the
				 * Cami file until its end is reached.
				 * 
				 * aCommand = in.readLine(); if (aCommand == null) { final
				 * String msg = "Cami2pnml: Empty Cami file.";
				 * JOURNAL.error(msg); in.close(); throw new CamiException(msg);
				 * } while (aCommand != null) { try { final Command aCamiCommand
				 * = CF .createACommand(aCommand); cr.addCommand(aCamiCommand);
				 * } catch (final CamiException e) {
				 * JOURNAL.error("Cami2pnml: While reading input Cami file: " +
				 * e.getMessage()); in.close(); throw e; } aCommand =
				 * in.readLine(); } in.close();
				 */
				cr.setNetName(theNetName);

				// blocking calls - wait for the 2 threads to finish processing
				// the cami
				String finished = notifQueue.take();
				finished = notifQueue.take();

				/*
				 * Creates the PNML model top container class. PN type fall back
				 * can be performed to Coremodel. PN type escalation : SN ->
				 * HLPN; PT -> PT-HLPNG
				 */
				HLAPIRootClass pnDoc = null;
				if (clOptions.isToCoreModel()) {
					pnDoc = new fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI();
				} else if (clOptions.istoHLPN()) {
					if (cr.isNetSc()) {
						pnDoc = new fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI();
					} else {
						pnDoc = new fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI();
					}
					// Normal conversion
				} else {
					if (cr.isNetSc()) {
						// Symmetric net - not completely supported
						pnDoc = new fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI();
					} else {
						// PT Net
						pnDoc = new PetriNetDocHLAPI();
					}
				}

				Cami2PnmlInfoSet cp2IS = new Cami2PnmlInfoSet(cr, pnDoc, true, clOptions,
						camiToPnmlFilesMap.get(camiFileIn));
				c2pQueue.put(cp2IS);
			}
			CamiChunk cc1 = new CamiChunk(END, null);
			ccQueue.put(cc1);
			CamiChunk cc2 = new CamiChunk(END, null);
			ccQueue.put(cc2);
			Cami2PnmlInfoSet cp2IS1 = new Cami2PnmlInfoSet(null, null, false, null, null);
			c2pQueue.put(cp2IS1);
			Cami2PnmlInfoSet cp2IS2 = new Cami2PnmlInfoSet(null, null, false, null, null);
			c2pQueue.put(cp2IS2);
			crTh1.join(MILLISEC_1000);
			crTh2.join(MILLISEC_1000);
			c2pTh1.join();
			c2pTh2.join();
		} catch (final IOException ioe) {
			JOURNAL.error(ioe.getMessage());
			throw new CamiException(ioe.getMessage());
		} catch (final CamiException ce) {
			JOURNAL.error(ce.getMessage());
			throw ce;
		} catch (InterruptedException e) {
			JOURNAL.error(e.getMessage());
			throw new CamiException(e.getMessage());
		}

	}

	private void readFileIntoQueue(File file, CamiRepository cr, BlockingQueue<CamiChunk> queue)
			throws UnsupportedEncodingException, IOException, InterruptedException {
		final int bufferSizeKB = 8;
		final int barraySizeKB = 4;
		final int bufferSize = bufferSizeKB * 1024;
		final int barraySize = barraySizeKB * 1024;
		final FileInputStream f = new FileInputStream(file);
		final FileChannel ch = f.getChannel();
		final ByteBuffer bb = ByteBuffer.allocateDirect(bufferSize);
		final byte[] barray = new byte[barraySize];
		int nRead, nGet, lastOccLS;
		final StringBuilder str = new StringBuilder();
		final StringBuilder remaining = new StringBuilder("");
		JOURNAL.info("Reading Cami file " + file.getName());
		while ((nRead = ch.read(bb)) != -1) {
			if (nRead == 0)
				continue;
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining()) {
				nGet = Math.min(bb.remaining(), barraySize);
				bb.get(barray, 0, nGet);
				str.delete(0, str.length());
				str.append(remaining.toString());
				str.append(new String(barray, 0, nGet, FILE_ENCONDING));
				lastOccLS = str.lastIndexOf(NL);
				if (lastOccLS < 0) {
					JOURNAL.error("Error in reading Cami file " + file.getName()
							+ " - probably line encoding problem (no occurrence of line feed (LF) character.");
					JOURNAL.error("Skipping Cami file " + file.getName());
					f.close();
					return;
				}
				CamiChunk cc = new CamiChunk(str.substring(0, lastOccLS), cr);
				queue.put(cc);
				remaining.delete(0, remaining.length());
				remaining.append(str.substring(lastOccLS + 1, str.length()));
				/*
				 * for (int i = 0; i < nGet; i++) checkSum += barray[i];
				 */
			}
			bb.clear();
		}
		f.close();
		JOURNAL.info("Finished reading Cami file " + file.getName());
		CamiChunk cc1 = new CamiChunk(EOF, null);
		queue.put(cc1);
		CamiChunk cc2 = new CamiChunk(EOF, null);
		queue.put(cc2);
	}

	/**
	 * Alternative method to convert Cami models into PNML. Provides a vector of
	 * Strings as input and a single PNML output file.
	 * 
	 * @see fr.lip6.move.pnml.cpnami.cami.Cami2Pnml#cami2p(Vector, String),
	 * 
	 * @param camiModelIn
	 *            the vector containing the Cami models.
	 * @param pnmlFileOut
	 *            the PNML output file.
	 * @throws CamiException
	 *             CamiException
	 * @deprecated
	 */
	public final void cami2p(final Vector<String> camiModelIn, final String pnmlFileOut) throws CamiException {
		CamiRepository cr = null;
		List<CamiRepository> camiRepositories = null;
		try {
			if (camiModelIn != null) {
				camiRepositories = new ArrayList<CamiRepository>();
				for (final String aCommand : camiModelIn) {
					final Command aCamiCommand = CF.createACommand(aCommand);
					// A repository per model
					if (aCamiCommand.getCCIdentifier().getValue() == CAMICOMMANDS.DB
							|| aCamiCommand.getCCIdentifier().getValue() == CAMICOMMANDS.DE) {
						cr = new CamiRepositoryImpl();
						camiRepositories.add(cr);
					}
					cr.addCommand(aCamiCommand);
				}
				final PetriNetDocHLAPI pnDoc = new PetriNetDocHLAPI();
				for (final CamiRepository aCr : camiRepositories) {
					// Either remove the whole method or adapt to new
					// thread-safe operations.
					throw new CamiException("Cami2p: Operation no more supported");
				}
				final List<String> exportFiles = new ArrayList<String>();
				exportFiles.add(pnmlFileOut);
				// exportToPNML(exportFiles);
			}

		} catch (final CamiException ce) {
			final String msg = "Cami2p: Caught CamiException.";
			JOURNAL.error(msg + ce.getMessage());
			throw ce;
		}
	}

	/**
	 * <!-- begin-user-doc --> Main method to convert a (CPN-AMI) Petri net
	 * model written in CAMI language into PNML. More than one conversion per
	 * session is offered. It means there may be many Petri nets to transform
	 * within a session. Therefore it is possible to add another Petri net from
	 * another Cami file into the same PNML destination file. <!-- end-user-doc
	 * -->
	 * 
	 * @param camiFiles
	 *            list of input Cami files
	 * @param pnmlFilesOut
	 *            list of output PNML files where to write the converted models,
	 *            one per Cami file.
	 * @param clOptions
	 *            command-line options
	 * @throws CamiException
	 *             CamiException
	 * 
	 */
	public final void cami2Pnml(final List<String> camiFiles, final List<String> pnmlFilesOut, final CLOptions clOptions)
			throws CamiException {

		try {
			linkCamiFilesToPnmlFiles(camiFiles, pnmlFilesOut);
			buildPNMLModels(camiFiles, clOptions);
			// exportToPNML(pnmlFilesOut);
		} catch (final CamiException ce) {
			JOURNAL.error(ce.getMessage());
			throw ce;
		}

	}

} // Cami2PnmlImpl2
