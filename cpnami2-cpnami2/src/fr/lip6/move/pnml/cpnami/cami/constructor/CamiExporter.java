/**
 *  Copyright 2009 Sorbonne Universités, Université Pierre & Marie Curie - CNRS UMR 7606 (LIP6/MoVe)
 *  All rights reserved.   This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Initial contributor:
 *    Lom Messan Hillah - <lom-messan.hillah@lip6.fr>
 *
 *  Mailing list:
 *    lom-messan.hillah@lip6.fr
 */
/**
 * 
 */
package fr.lip6.move.pnml.cpnami.cami.constructor;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;

import fr.lip6.move.cpnami.p2c.CamiWriter;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cm;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

/**
 * Exports Cami model(s) from a Cami repository into Cami file(s), or a vector.
 * 
 * @author lom
 * 
 */
public final class CamiExporter {

	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	private static final Log JOURNAL = LogMaster.giveLogger(CamiExporter.class.getCanonicalName());
	private static final String NL = "\n";
	private static final String DE = "DE()";
	private static final String FE = "FE()";
	private static final String STOP = "STOP";
	
	private static final long HOW_LONG = 5 * 1000L;

	
	private BlockingQueue<String> queue;
	
	public CamiExporter() {
		queue = new LinkedBlockingQueue<String>();
	}

	/**
	 * Exports the Cami model contained in the Cami repository into a file.
	 * 
	 * @param cr
	 *            the Cami repository.
	 * @param camiFileOut
	 *            the output file.
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final void exportCamiModel(final CamiRepository cr, final String camiFileOut) throws CamiException {
		

		if (camiFileOut != null) {
			
			/*
			 * PrintWriter pw = null;
			 * try {
				pw = new PrintWriter(new FileWriter(camiFileOut));
			} catch (final FileNotFoundException fnfe) {
				JOURNAL.error("#BuildCamiModelFromRepository: Problem when creating new CAMI file named " + camiFileOut);
				throw new CamiException("Error when creating the cami file. You should check the log.");

			} catch (final IOException ioe) {
				JOURNAL.error(ioe.getMessage());
				throw new CamiException("Error when creating the cami file. You should check the log.");
			}
			// Special command for header of CPN-AMI model
			myRunner.printString("DE()", pw);
			// Creating nodes
			exportCamiNodes(cr, pw);
			// creating arcs
			exportCamiArcs(cr, pw);
			// creating textual attributes
			exportCamiText(cr, pw);
			// Special CAMI command for end of CPN-AMI model
			myRunner.printString("FE()", pw);
			JOURNAL.info("#BuildCamiModelFromRepository: model built in " + camiFileOut);
			if (pw != null) {
				pw.flush();
				pw.close();
			}
			*/
			
			CamiWriter cw = null;
			try {
				cw = new CamiWriter(queue, camiFileOut);
				Thread t = new Thread(cw);
				t.start();
				queue.put(DE + NL);
				exportCamiNodes(cr);
				exportCamiArcs(cr);
				exportCamiText(cr);
				queue.put(FE + NL);
				queue.put(STOP);
				t.join(HOW_LONG);
			} catch (FileNotFoundException | InterruptedException e) {
				throw new CamiException(e.getMessage(), e.getCause());
			}	
				
		} else {
			JOURNAL.error("BuildCAMIModelFromRepository: The output should be a file but no file name is specified.");
			throw new CamiException("BuildCAMIModel: no ouput file name is specified.");
		}

		
	}

	/**
	 * Convenient API. Export Cami models into a vector of Cami commands.
	 * 
	 * @param camiModelOut
	 *            is an in-out parameter that is intended to grow by possibly
	 *            successive calls to methods that build parts of the models. It
	 *            may end containing several Cami models due those successive
	 *            calls.
	 * @param cr
	 *            Cami Repository
	 * @throws CamiException
	 *             Cami Exception
	 */
	public final void exportCAMIModel(final CamiRepository cr, final Vector<String> camiModelOut) throws CamiException {
		if (cr == null || camiModelOut == null) {
			throw new CamiException("#exportCAMIModel(vector): one of the parameters is null");
		}
		// Special command for header of CPN-AMI model, as a result of a service
		camiModelOut.add(DE);
		// Creating nodes
		exportCamiNodes(cr, camiModelOut);
		// creating arcs
		exportCamiArcs(cr, camiModelOut);
		// creating textual attributes
		exportCamiText(cr, camiModelOut);
		// creating node and arcs positions

		// creating textual attributes positions.

		// Special CAMI command for end of CPN-AMI model
		camiModelOut.add(FE);
		JOURNAL.info("#exportCAMIModel: model built.");
	}

	/**
	 * Export Cami nodes into a vector of Cami commands. The export organizes
	 * those commands in Cami models. So, a model is delimited by DE and FE
	 * 
	 * @param cr
	 *            Cami Repository
	 * @param camiModelOut
	 *            the vector of Cami commands
	 */
	private void exportCamiNodes(final CamiRepository cr, final Vector<String> camiModelOut) {
		JOURNAL.info("Exporting model nodes.");
		final List<Cn> allPl = cr.getAllPlaces();
		if (allPl != null) {
			for (final Cn aPl : allPl) {
				camiModelOut.add(aPl.convert2String());
			}
		}
		final List<Cn> allTr = cr.getAllTransitions();
		if (allTr != null) {
			for (final Cn aTr : allTr) {
				camiModelOut.add(aTr.convert2String());
			}
		}
		final Cn net = cr.getNetNode();
		camiModelOut.add(net.convert2String());
		JOURNAL.info("Exporting model nodes positions.");
		final List<Po> nodePositions = cr.getAllPo();
		if (nodePositions != null) {
			for (final Po aPos : nodePositions) {
				camiModelOut.add(aPos.convert2String());
			}
		}
	}

	/**
	 * Export Cami nodes into a vector of Cami commands. The export organizes
	 * those commands in Cami models. So, a model is delimited by DE and FE
	 * 
	 * @param cr
	 *            Cami Repository
	 * @param camiModelOut
	 *            the vector of Cami commands
	 */
	private void exportCamiArcs(final CamiRepository cr, final Vector<String> camiModelOut) {
		final List<Ca> allArcs = cr.getAllCa();
		if (allArcs != null) {
			for (final Ca anArc : allArcs) {
				camiModelOut.add(anArc.convert2String());
			}
		}
		final List<Pi> arcPositions = cr.getAllPi();
		if (arcPositions != null) {
			for (final Pi aPi : arcPositions) {
				camiModelOut.add(aPi.convert2String());
			}
		}
	}

	/**
	 * Export Cami nodes into a vector of Cami commands. The export organizes
	 * those commands in Cami models. So, a model is delimited by DE and FE
	 * 
	 * @param cr
	 *            Cami Repository
	 * @param camiModelOut
	 *            the vector of Cami commands
	 */
	private void exportCamiText(final CamiRepository cr, final Vector<String> camiModelOut) {
		JOURNAL.info("Exporting model textual attributes.");
		final List<Ct> allText = cr.getAllCt();
		if (allText != null) {
			for (final Ct aTxt : allText) {
				camiModelOut.add(aTxt.convert2String());
			}
		}
		final List<Cm> allMultiText = cr.getAllCm();
		if (allMultiText != null) {
			for (final Cm aMT : allMultiText) {
				camiModelOut.add(aMT.convert2String());
			}
		}
		JOURNAL.info("Exporting textual attributes positions.");
		final List<Pt> allTPos = cr.getAllPt();
		if (allTPos != null) {
			for (final Pt aPt : allTPos) {
				camiModelOut.add(aPt.convert2String());
			}
		}
	}

	/**
	 * Export Cami nodes into a Cami file.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param pw
	 *            the destination file descriptor
	 * @throws InterruptedException 
	 */
	private void exportCamiNodes(final CamiRepository cr) throws InterruptedException {
		// Export the net declaration
		final Cn net = cr.getNetNode();
		queue.put(net.convert2StringNL());
		// Creating nodes
		JOURNAL.info("Exporting model nodes.");
		final List<Cn> allPl = cr.getAllPlaces();
		if (allPl != null) {
			for (final Cn aPl : allPl) {
				queue.put(aPl.convert2StringNL());
			}
		}
		final List<Cn> allTr = cr.getAllTransitions();
		if (allTr != null) {
			for (final Cn aTr : allTr) {
				queue.put(aTr.convert2StringNL());
			}
		}
		// their positions
		JOURNAL.info("Exporting nodes positions.");

		final List<Po> nodePositions = cr.getAllPo();
		if (nodePositions != null) {
			for (final Po aPos : nodePositions) {
				queue.put(aPos.convert2StringNL());
			}
		}
	}

	/**
	 * Export Cami arcs into a Cami file.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param pw
	 *            the destination file descriptor
	 * @throws InterruptedException 
	 */
	private void exportCamiArcs(final CamiRepository cr) throws InterruptedException {
		final List<Ca> allArcs = cr.getAllCa();
		if (allArcs != null) {
			for (final Ca anArc : allArcs) {
				queue.put(anArc.convert2StringNL());
			}
		}
		final List<Pi> arcPositions = cr.getAllPi();
		if (arcPositions != null) {
			for (final Pi aPi : arcPositions) {
				queue.put(aPi.convert2StringNL());
			}
		}
	}

	/**
	 * Export Cami textual attrubutes into a Cami file.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param pw
	 *            the destination file descriptor
	 * @throws InterruptedException 
	 */
	private void exportCamiText(final CamiRepository cr) throws InterruptedException {
		JOURNAL.info("Exporting textual attributes.");
		final List<Ct> allText = cr.getAllCt();
		if (allText != null) {
			for (final Ct aTxt : allText) {
				queue.put(aTxt.convert2StringNL());
			}
		}
		final List<Cm> allMultiText = cr.getAllCm();
		if (allMultiText != null) {
			for (final Cm aMT : allMultiText) {
				queue.put(aMT.convert2StringNL());
			}
		}
		JOURNAL.info("Exporting textual attributes positions.");
		final List<Pt> allTPos = cr.getAllPt();
		if (allTPos != null) {
			for (final Pt aPt : allTPos) {
				queue.put(aPt.convert2StringNL());
			}
		}
	}

}
