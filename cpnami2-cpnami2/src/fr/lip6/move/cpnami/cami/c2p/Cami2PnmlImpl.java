/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites,
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2CoreModel;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2HLPNG;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2PTHLPNG;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2PTNetModel;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2SN;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiRepositoryImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.utils.CLOptions;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.general.PnmlExport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.BadFileFormatException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.OCLValidationFailed;
import fr.lip6.move.pnml.framework.utils.exception.OtherException;
import fr.lip6.move.pnml.framework.utils.exception.UnhandledNetType;
import fr.lip6.move.pnml.framework.utils.exception.ValidationFailedException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
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
 * @see Cami2PnmlImpl#cami2Pnml(List, List) and
 * @see Cami2PnmlImpl#cami2p(Vector, String) </p>
 *      <!-- end-user-doc -->
 * 
 */
public class Cami2PnmlImpl implements Cami2Pnml {
	/**
	 * Retrieves a model repository to store PNML models.
	 */
	protected static final ModelRepository MR = ModelRepository.getInstance();
	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	protected static final Log JOURNAL = LogMaster.giveLogger(Cami2PnmlImpl.class.getCanonicalName());
	/**
	 * Factory initialization.
	 */
	protected static final CamiFactory CF = CamiFactory.SINSTANCE;
	/**
	 * Reading buffer size.
	 */
	protected static final int BUFFER_SIZE = 102400;
	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();

	/**
	 * <!-- begin-user-doc --> Constructor. An instance of this class must be
	 * created through CamiFactory. <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Cami2PnmlImpl() {
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
			buildPNMLModels(camiFiles, null);
			exportToPNML(pnmlFilesOut);
		} catch (final CamiException ce) {
			JOURNAL.error("Cami2Pnml#: " + ce.getMessage());
			throw ce;
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
			final String message = "Cami2pnml: no input cami file(s)!";
			JOURNAL.error(message);
			throw new CamiException(message);
		}
		// Retrieves the singleton instance of CommandAnalyser. For String
		// manipulation.

		try {
			// starts the processing of CPN-AMI models (one per CAMI file)
			for (int i = 0; i < nbCamiFiles; i++) {
				camiFileIn = camiFiles.get(i);
				// extracts the net name from the Cami file name
				final String theNetName = myRunner.extractNameWithoutExtension(myRunner.extractLastName(camiFileIn));
				// Get camiFileIn opened - a cami command line is no more than
				// 256
				// characters.

				final BufferedReader in = new BufferedReader(new FileReader(camiFileIn), BUFFER_SIZE);
				String aCommand;
				final CamiRepository cr = new CamiRepositoryImpl();
				/*
				 * For each Cami command read, builds the corresponding command
				 * model with it. A CPN-AMI model in Cami is between DB() and
				 * FB() commands or DE() and FE() However, this method does not
				 * care if one these commands is reached. Instead, it reads the
				 * Cami file until its end is reached.
				 */
				aCommand = in.readLine();
				if (aCommand == null) {
					final String msg = "Cami2pnml: Empty Cami file.";
					JOURNAL.error(msg);
					in.close();
					throw new CamiException(msg);
				}
				while (aCommand != null) {
					try {
						final Command aCamiCommand = CF.createACommand(aCommand);
						cr.addCommand(aCamiCommand);
					} catch (final CamiException e) {
						JOURNAL.error("Cami2pnml: While reading input Cami file: " + e.getMessage());
						in.close();
						throw e;
					}
					aCommand = in.readLine();
				}
				cr.setNetName(theNetName);
				in.close();
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
				buildPNMLModel(cr, pnDoc, false, clOptions);
			}
		} catch (final IOException ioe) {
			JOURNAL.error("Cami2Pnml#: " + ioe.getMessage());
			throw new CamiException(ioe.getMessage());
		} catch (final CamiException ce) {
			JOURNAL.error("Cami2Pnml#: " + ce.getMessage());
			throw ce;
		}
	}

	/**
	 * Builds a PNML model from the corresponding Cami Petri net model found in
	 * a Cami repository.
	 * 
	 * @param cr
	 *            the Cami repository containing the Petri net model.
	 * @param pnDoc
	 *            the Petri net document in which to store the Petri net model.
	 * @param createWorkspaceOnce
	 *            used to indicate if you are creating a PNML document for each
	 *            Cami model or if you are gathering them all in the same PNML
	 *            document. Set false for the former case, true for the latter.
	 *            Beware! all Petri nets in the same PNML document must be of
	 *            the same PN type, otherwise the export will fail!
	 * @param clOptions
	 *            command-line options
	 * @throws CamiException
	 *             CamiException
	 */
	private void buildPNMLModel(final CamiRepository cr, final HLAPIRootClass pnDoc, final boolean createWorkspaceOnce, final CLOptions clOptions)
			throws CamiException {
		boolean firstTime = true;
		try {
			// add it to the repository
			if (!createWorkspaceOnce) {
				MR.createDocumentWorkspace(cr.getNetName() + String.valueOf(cr.getNetName().hashCode()), pnDoc);
			} else {
				if (firstTime) {
					MR.createDocumentWorkspace(cr.getNetName() + String.valueOf(cr.getNetName().hashCode()), pnDoc);
					firstTime = false;
				}
			}
			if (clOptions != null) {
				if (clOptions.isToCoreModel()) {
					final fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI cmPnDoc = (fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI) pnDoc;
					final Cami2CoreModel myCoreModel = new Cami2CoreModel();
					Cami2CoreModel.setPnmlModelRepository(MR);
					// call HLAPI to create root node
					myCoreModel.setNet(cr);
					// input the model into the PNML document
					cmPnDoc.addNetsHLAPI(myCoreModel.getNet());
					// call HLAPI for all places
					myCoreModel.setPlaces(cr);
					// call HLAPI for all Transitions
					myCoreModel.setTransitions(cr);
					// call HLAPI for all arcs
					myCoreModel.setArcs(cr);
				} else if (clOptions.istoHLPN()) {
					if (cr.isNetSc()) {
						final fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI pthlpngPnDoc = (fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2HLPNG myHLPN = new Cami2HLPNG();
						Cami2HLPNG.setPnmlModelRepository(MR);
						myHLPN.setNet(cr);
						pthlpngPnDoc.addNetsHLAPI(myHLPN.getNet());
						myHLPN.setPlaces(cr);
						myHLPN.setTransitions(cr);
						myHLPN.setArcs(cr);
					} else {
						final fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI pthlpngPnDoc = (fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2PTHLPNG myPTHLPNG = new Cami2PTHLPNG();
						Cami2PTHLPNG.setPnmlModelRepository(MR);
						myPTHLPNG.setNet(cr);
						pthlpngPnDoc.getNetsHLAPI().add(myPTHLPNG.getNet());
						myPTHLPNG.setPlaces(cr);
						myPTHLPNG.setTransitions(cr);
						myPTHLPNG.setArcs(cr);
					}
					// Normal conversion
				} else {
					if (cr.isNetSc()) {
						// Symmetric net
						final fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI pthlpngPnDoc = (fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2SN mySN = new Cami2SN();
						Cami2SN.setPnmlModelRepository(MR);
						mySN.setNet(cr);
						pthlpngPnDoc.addNetsHLAPI(mySN.getNet());
						mySN.setPlaces(cr);
						mySN.setTransitions(cr);
						mySN.setArcs(cr);
					} else {
						// PT Net
						final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI ptPnDoc = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2PTNetModel myPTNetModel = new Cami2PTNetModel();
						myPTNetModel.setPnmlModelRepository(MR);
						myPTNetModel.setNet(cr);
						ptPnDoc.addNetsHLAPI(myPTNetModel.getNet());
						myPTNetModel.setPlaces(cr);
						myPTNetModel.setTransitions(cr);
						myPTNetModel.setArcs(cr);
					}
				}
			}

		} catch (final CamiException ce) {
			System.out.println("Exception: " + ce.getMessage());
			throw ce;
		} catch (final InvalidIDException iie) {
			JOURNAL.error("Cami2Pnml#buildPNMLModel: caught InvalidIDException");
			throw new CamiException(iie.getMessage());
		}
	}

	/**
	 * Export each current PNML model in the PNML repository into a PNML file.
	 * 
	 * @param pnmlFileOut
	 *            the list of output PNML files.
	 * @throws CamiException
	 *             CamiException
	 */
	private void exportToPNML(final List<String> pnmlFileOut) throws CamiException {
		final Set<String> modelsIds = MR.getRegisteredModelsId();
		int filesIter = 0;
		MR.setPrettyPrintStatus(true);
		for (final String aModel : modelsIds) {
			// TODO : remove System.out.println("registered model:" + aModel);
			try {
				MR.changeCurrentDocWorkspace(aModel);
				final HLAPIRootClass roc = MR.getCurrentHLAPIRootClass();
				final PetriNetDocHLAPI pnDoc = (PetriNetDocHLAPI) roc;
				final String pnmlOut = pnmlFileOut.get(filesIter);
				filesIter++;
				final PnmlExport pex = new PnmlExport();
				pex.exportObject(pnDoc, pnmlOut);

			} catch (final InvalidIDException iie) {
				JOURNAL.error("Cami2Pnml#exportToPnml: InvalidIDException");
				throw new CamiException(iie.getMessage());
			} catch (final VoidRepositoryException vre) {
				JOURNAL.error("Cami2Pnml#exportToPnml: VoidRepositoryException");
				throw new CamiException(vre.getMessage());
			} catch (final UnhandledNetType une) {
				JOURNAL.error("Cami2Pnml#exportToPnml: UnhandledNetType exception");
				throw new CamiException(une.getMessage());
			} catch (final OCLValidationFailed ove) {
				JOURNAL.error("Cami2Pnml#exportToPnml: OCLValidationFailed");
				throw new CamiException(ove.getMessage());
			} catch (final IOException ioe) {
				JOURNAL.error("Cami2Pnml#exportToPnml: IOException");
				throw new CamiException(ioe.getMessage());
			} catch (final ValidationFailedException vfe) {
				JOURNAL.error("Cami2Pnml#exportToPnml: ValidationFailedException");
				throw new CamiException(vfe.getMessage());
			} catch (final BadFileFormatException bfe) {
				JOURNAL.error("Cami2Pnml#exportToPnml: BadFileFormatException");
				throw new CamiException(bfe.getMessage());
			} catch (final OtherException oe) {
				JOURNAL.error("Cami2Pnml#exportToPnml: undefined exception");
				throw new CamiException(oe.getMessage());
			}

		}
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
					if (aCamiCommand.getCCIdentifier().getValue() == CAMICOMMANDS.DB || aCamiCommand.getCCIdentifier().getValue() == CAMICOMMANDS.DE) {
						cr = new CamiRepositoryImpl();
						camiRepositories.add(cr);
					}
					cr.addCommand(aCamiCommand);
				}
				final PetriNetDocHLAPI pnDoc = new PetriNetDocHLAPI();
				for (final CamiRepository aCr : camiRepositories) {
					buildPNMLModel(aCr, pnDoc, true, null);
				}
				final List<String> exportFiles = new ArrayList<String>();
				exportFiles.add(pnmlFileOut);
				exportToPNML(exportFiles);
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
	 */
	public final void cami2Pnml(final List<String> camiFiles, final List<String> pnmlFilesOut, final CLOptions clOptions) throws CamiException {

		try {
			buildPNMLModels(camiFiles, clOptions);
			exportToPNML(pnmlFilesOut);
		} catch (final CamiException ce) {
			JOURNAL.error("Cami2Pnml#: " + ce.getMessage());
			throw ce;
		}

	}

} // Cami2PnmlImpl
