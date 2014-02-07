/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites, Univ. Paris 06 - CNRS UMR 7606 (LIP6)
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

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2CoreModel;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2HLPNG;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2PTHLPNG;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2PTNetModel;
import fr.lip6.move.pnml.cpnami.cami.constructor.Cami2SN;
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

/**
 * Exporter from Cami to PNML. Runnable.
 * Reads export tasks from queue, sent by {@link Cami2PnmlImpl2}
 * @author lom
 *
 */
public final class Cami2PnmlExporter implements Runnable {

	/**
	 * Retrieves a model repository to store PNML models.
	 */
	private final ModelRepository mr;

	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	private Log journal;

	private final BlockingQueue<Cami2PnmlInfoSet> queue;

	public Cami2PnmlExporter(BlockingQueue<Cami2PnmlInfoSet> queue) {
		this.queue = queue;
		this.mr = ModelRepository.getInstance();
	}

	@Override
	public void run() {
		journal = LogMaster.giveLogger(Cami2PnmlExporter.class
				.getCanonicalName() + "#" + Thread.currentThread().getId());

		Cami2PnmlInfoSet c2pIS;
		try {
			c2pIS = queue.take();
			while (c2pIS.getCamiRepository() != null) {
				buildPNMLModel(c2pIS.getCamiRepository(), c2pIS.getDoc(),
						c2pIS.isCreateWspOnce(), c2pIS.getClOptions(),
						c2pIS.getPnmlFile());
				c2pIS = queue.take();
			}

		} catch (InterruptedException e) {
			journal.error("Thread error while exporting to PNML - "
					+ e.getMessage());
			e.printStackTrace();
		} catch (CamiException e) {
			journal.error("Cami error while exporting to PNML - "
					+ e.getMessage());
			e.printStackTrace();
		}
		journal.info("Finished exporting Cami to PNML");
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
	private void buildPNMLModel(final CamiRepository cr,
			final HLAPIRootClass pnDoc, final boolean createWorkspaceOnce,
			final CLOptions clOptions, final String pnmlFileOut)
			throws CamiException {
		boolean firstTime = true;
		String mId;
		try {
			// add it to the repository
			if (!createWorkspaceOnce) {
				mId = mr.createDocumentWorkspace(
						cr.getNetName()
								+ String.valueOf(cr.getNetName().hashCode()),
						pnDoc);

			} else {
				if (firstTime) {
					mId = mr.createDocumentWorkspace(
							cr.getNetName()
									+ String.valueOf(cr.getNetName().hashCode()),
							pnDoc);
					firstTime = false;
				}
			}
			if (clOptions != null) {
				if (clOptions.isToCoreModel()) {
					final fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI cmPnDoc = (fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI) pnDoc;
					final Cami2CoreModel myCoreModel = new Cami2CoreModel();
					Cami2CoreModel.setPnmlModelRepository(mr);
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
						Cami2HLPNG.setPnmlModelRepository(mr);
						myHLPN.setNet(cr);
						pthlpngPnDoc.addNetsHLAPI(myHLPN.getNet());
						myHLPN.setPlaces(cr);
						myHLPN.setTransitions(cr);
						myHLPN.setArcs(cr);
					} else {
						final fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI pthlpngPnDoc = (fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2PTHLPNG myPTHLPNG = new Cami2PTHLPNG();
						Cami2PTHLPNG.setPnmlModelRepository(mr);
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
						Cami2SN.setPnmlModelRepository(mr);
						mySN.setNet(cr);
						pthlpngPnDoc.addNetsHLAPI(mySN.getNet());
						mySN.setPlaces(cr);
						mySN.setTransitions(cr);
						mySN.setArcs(cr);
					} else {
						// PT Net
						final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI ptPnDoc = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) pnDoc;
						final Cami2PTNetModel myPTNetModel = new Cami2PTNetModel();
						myPTNetModel.setPnmlModelRepository(mr);
						myPTNetModel.setNet(cr);
						ptPnDoc.addNetsHLAPI(myPTNetModel.getNet());
						Thread tr = myPTNetModel.setPlaces(cr);
						myPTNetModel.setTransitions(cr);
						try {
							tr.join();
						} catch (InterruptedException e) {
							journal.error("#buildPNMLModel:" + e.getMessage());
							e.printStackTrace();
						}
						myPTNetModel.setArcs(cr);
					}
				}
			}
			exportToPNML(pnmlFileOut);

		} catch (final CamiException ce) {
			journal.error("#buildPNMLModel->CamiException: " + ce.getMessage());
			throw ce;
		} catch (final InvalidIDException iie) {
			journal.error("#buildPNMLModel->InvalidIDException");
			iie.printStackTrace();
			throw new CamiException(iie.getMessage());
		}
	}

	/**
	 * Export each current PNML model in the PNML repository into a PNML file.
	 * 
	 * @param pnmlFileOut
	 *            the output PNML file.
	 * @throws CamiException
	 *             CamiException
	 */
	private void exportToPNML(String pnmlFileOut) throws CamiException {
		final Set<String> modelsIds = mr.getRegisteredModelsId();
		final PnmlExport pex = new PnmlExport();
		mr.setPrettyPrintStatus(true);
		fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI coreModelDoc;
		fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI ptnetDoc;
		fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI hlpnDoc;
		fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI snDoc;
		fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI pthlpngDoc;
		if (modelsIds.size() > 1) {
			journal.error("This exporter does not handle more than one PNML model per Cami repository.");
			throw new CamiException(
					"This exporter does not handle more than one PNML model per Cami repository.");
		} else {
			for (final String aModel : modelsIds) {
				// TODO : remove System.out.println("registered model:" +
				// aModel);
				try {
					final String pnmlOut = pnmlFileOut;
					mr.changeCurrentDocWorkspace(aModel);
					final HLAPIRootClass roc = mr.getCurrentHLAPIRootClass();
					if (roc instanceof fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI) {
						coreModelDoc = (fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI) roc;
						pex.exportObject(coreModelDoc, pnmlOut);

					} else if (roc instanceof fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) {
						ptnetDoc = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) roc;
						pex.exportObject(ptnetDoc, pnmlOut);

					} else if (roc instanceof fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI) {
						snDoc = (fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI) roc;
						pex.exportObject(snDoc, pnmlOut);

					} else if (roc instanceof fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI) {
						hlpnDoc = (fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI) roc;
						pex.exportObject(hlpnDoc, pnmlOut);

					} else if (roc instanceof fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI) {
						pthlpngDoc = (fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI) roc;
						pex.exportObject(pthlpngDoc, pnmlOut);

					}
					// final PetriNetDocHLAPI pnDoc = (PetriNetDocHLAPI) roc;

				} catch (final InvalidIDException iie) {
					journal.error("#exportToPnml: InvalidIDException");
					throw new CamiException(iie.getMessage());
				} catch (final VoidRepositoryException vre) {
					journal.error("#exportToPnml: VoidRepositoryException");
					throw new CamiException(vre.getMessage());
				} catch (final UnhandledNetType une) {
					journal.error("#exportToPnml: UnhandledNetType exception");
					throw new CamiException(une.getMessage());
				} catch (final OCLValidationFailed ove) {
					journal.error("#exportToPnml: OCLValidationFailed");
					throw new CamiException(ove.getMessage());
				} catch (final IOException ioe) {
					journal.error("#exportToPnml: IOException");
					throw new CamiException(ioe.getMessage());
				} catch (final ValidationFailedException vfe) {
					journal.error("#exportToPnml: ValidationFailedException");
					throw new CamiException(vfe.getMessage());
				} catch (final BadFileFormatException bfe) {
					journal.error("#exportToPnml: BadFileFormatException");
					throw new CamiException(bfe.getMessage());
				} catch (final OtherException oe) {
					journal.error("#exportToPnml: undefined exception");
					throw new CamiException(oe.getMessage());
				}

			}
		}
	}
}
