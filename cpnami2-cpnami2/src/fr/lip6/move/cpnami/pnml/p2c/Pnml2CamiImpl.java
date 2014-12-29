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
package fr.lip6.move.cpnami.pnml.p2c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Pnml2Cami;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.constructor.CamiExporter;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiRepositoryImpl;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.general.PNType;
import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.AssociatedPluginNotFound;
import fr.lip6.move.pnml.framework.utils.exception.BadFileFormatException;
import fr.lip6.move.pnml.framework.utils.exception.InnerBuildException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.OCLValidationFailed;
import fr.lip6.move.pnml.framework.utils.exception.OtherException;
import fr.lip6.move.pnml.framework.utils.exception.UnhandledNetType;
import fr.lip6.move.pnml.framework.utils.exception.ValidationFailedException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

/**
 * Implements <em><b>Pnml2Cami</b></em> interface.
 * <p>
 * </p>
 */
public final class Pnml2CamiImpl implements Pnml2Cami {

	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	private static final Log JOURNAL = LogMaster.giveLogger(Pnml2CamiImpl.class.getCanonicalName());
	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * Retrieves a model repository to store PNML models.
	 */
	private static final ModelRepository MR = ModelRepository.getInstance();
	/**
	 * Message to tell that the export of a HLPN into Cami is performed on a
	 * best-effort basis.
	 */
	private static final String HLPN_CAMI = "Petri net type is HLPN. Exported as a Cami implicit SN model. Cami syntax checker will fail on this model.";
	/**
	 * Message to tell that the export of a HLPN into Cami is performed on a
	 * best-effort basis.
	 */
	private static final String COREMODE_CAMI = "Petri net type is Core Model. Exported as a Cami implicit PT model.";

	/**
	 * Constructor.
	 */
	public Pnml2CamiImpl() {
		super();
	}

	/**
	 * Loads a PNML document using PNML Framework.
	 * 
	 * @param pnmlFileIn
	 *            the PNML document
	 * @return the root class of the loaded PNML document
	 * @throws CamiException
	 *             something went wrong during loading
	 */
	private HLAPIRootClass importPNMLDocument(final String pnmlFileIn) throws CamiException {

		try {
			final PnmlImport pnmlImp = new PnmlImport();
			// enables fall back for PNML Framework
			pnmlImp.setFallUse(true);
			final HLAPIRootClass myPetriNetDoc = (HLAPIRootClass) pnmlImp.importFile(pnmlFileIn);
			return myPetriNetDoc;
		} catch (final InvalidIDException iee) {
			throw new CamiException(iee.getMessage());
		} catch (final IOException ioe) {
			throw new CamiException(ioe.getMessage());
		} catch (final BadFileFormatException bdfe) {
			throw new CamiException(bdfe.getMessage());
		} catch (final UnhandledNetType unt) {
			throw new CamiException(unt.getMessage());
		} catch (final ValidationFailedException vfe) {
			throw new CamiException(vfe.getMessage());
		} catch (final InnerBuildException ibe) {
			throw new CamiException(ibe.getMessage());
		} catch (final OCLValidationFailed ovf) {
			throw new CamiException(ovf.getMessage());
		} catch (final OtherException oe) {
			throw new CamiException(oe.getMessage());
		} catch (final AssociatedPluginNotFound apnf) {
			throw new CamiException(apnf.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}

	}

	/**
	 * Determines the net type that was imported by PNML Framework.
	 * 
	 * @param myPetriNetDoc
	 *            the root class of the model for which we want to determine the
	 *            type.
	 * @return the Petri net type of the model. Returns null if the type is none
	 *         of PT or SN.
	 */
	private PNType determineNetType(final HLAPIRootClass myPetriNetDoc) {
		PNType myPnType = null;
		if (myPetriNetDoc.getClass().equals(fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI.class)) {
			myPnType = PNType.PTNET;
		} else if (myPetriNetDoc.getClass().equals(fr.lip6.move.pnml.symmetricnet.hlcorestructure.hlapi.PetriNetDocHLAPI.class)) {
			myPnType = PNType.SYMNET;
		} else if (myPetriNetDoc.getClass().equals(fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetDocHLAPI.class)) {
			myPnType = PNType.HLPN;
		} else if (myPetriNetDoc.getClass().equals(fr.lip6.move.pnml.pthlpng.hlcorestructure.hlapi.PetriNetDocHLAPI.class)) {
			myPnType = PNType.PTHLPN;
		} else if (myPetriNetDoc.getClass().equals(fr.lip6.move.pnml.pnmlcoremodel.hlapi.PetriNetDocHLAPI.class)) {
			myPnType = PNType.COREMODEL;
		}
		return myPnType;
	}

	/**
	 * Performs the actual conversion dispatching.
	 * 
	 * @param pnmlFileIn
	 *            PNML document
	 * @param camiFile
	 *            a destination Cami file
	 * @param camiFiles
	 *            destination Cami files
	 * @throws CamiException
	 *             something went wrong during transformation
	 */
	private void convert2Cami(final String pnmlFileIn, final String camiFile, final List<String> camiFiles) throws CamiException {
		final String pnmlFileName = myRunner.extractNameWithoutExtension(myRunner.extractLastName(pnmlFileIn));
		try {
			MR.createDocumentWorkspace(pnmlFileName);
			final HLAPIRootClass myPetriNetDoc = importPNMLDocument(pnmlFileIn);
			final PNType pnType = determineNetType(myPetriNetDoc);
			if (pnType != null) {
				if (pnType == PNType.PTNET) {
					final PTModelsToCamiExtractor ptExtractor = new PTModelsToCamiExtractor();
					ptExtractor.extractPNModels(myPetriNetDoc, camiFile, camiFiles);
				} else if (pnType == PNType.SYMNET) { // SN case
					JOURNAL.error("Symmetric net export into Cami not supported");
					// TODO : implement SN processing
				} else if (pnType == PNType.HLPN) {
					JOURNAL.error("HLPN net export into Cami not supported");
					// TODO convert the net into Cami with annotations as CM or
					// TODO convert the net into Cami with annotations as CM or
					// CT. Validation will fail with Cami syntax checker.
					//JOURNAL.warn(HLPN_CAMI);
					//System.err.println(HLPN_CAMI);
				} else if (pnType == PNType.COREMODEL) {
					// TODO export as a PT.
					JOURNAL.error("Core model export into Cami not supported");

					//JOURNAL.warn(COREMODE_CAMI);
					//System.err.println(COREMODE_CAMI);
				}  else if (pnType == PNType.PTHLPN) {
					JOURNAL.error("P/T Net as HLPN export into Cami not supported");
				}

			} else {
				// TODO: Best effort: we just retrieve the net graph -> core model
				JOURNAL.error("Unrecognized net type.");
			}
			MR.destroyCurrentWorkspace();

		} catch (final CamiException ce) {
			throw ce;
		} catch (final VoidRepositoryException e) {
			throw new CamiException(e.getMessage());
		} catch (final InvalidIDException e) {
			throw new CamiException(e.getMessage());
		}
	}

	/**
	 * Converts PNML models read from a single PNML document to Cami models into
	 * multiple Cami files. Each Cami file corresponds to one PNML PN model.
	 * 
	 * @param pnmlFileIn
	 *            the input PNML file
	 * @param camiFiles
	 *            the list of output Cami files.
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final void pnml2Cami(final String pnmlFileIn, final List<String> camiFiles) throws CamiException {
		if (pnmlFileIn == null) {
			throw new CamiException("PNML input file name is null!");
		}
		try {
			convert2Cami(pnmlFileIn, null, camiFiles);
		} catch (final CamiException ce) {
			throw ce;
		}
	}

	/**
	 * Each PNML document should contain one Petri net to match with the number
	 * of deduced Cami output files. But if not so, for each Petri net in a PNML
	 * document, a suffix is appended to the corresponding initial Cami file to
	 * create the necessary number of Cami files for the Petri nets of the PNML
	 * document.
	 * 
	 * @param pnmlFileIn
	 *            the list of PNML documents
	 * @param camiFiles
	 *            the list of Cami output files.
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final void pnml2Cami(final List<String> pnmlFileIn, final List<String> camiFiles) throws CamiException {
		if (pnmlFileIn == null) {
			throw new CamiException("PNML input files list is null!");
		}
		if (camiFiles == null) {
			throw new CamiException("Cami output files list is null!");
		}
		try {
			for (final Iterator<String> pnmlFile = pnmlFileIn.iterator(), camiFile = camiFiles.iterator(); pnmlFile.hasNext() && camiFile.hasNext();) {
				convert2Cami(pnmlFile.next(), camiFile.next(), null);
			}
		} catch (final CamiException ce) {
			throw ce;
		}
	}

	/**
	 * Convenient alternative method to export PNML models into Cami. Here only
	 * a PNML document is accepted.
	 * 
	 * @param pnmlFileIn
	 *            the PNML document name
	 * @return a vector of Cami commands which build the Cami model(s).
	 * @throws CamiException
	 *             Cami exception.
	 *             @deprecated
	 */
	public final Vector<String> pnml2cami(final String pnmlFileIn) throws CamiException {
		if (pnmlFileIn == null) {
			throw new CamiException("pnml2cami: PNML input file name is null!");
		}
		// each PNML PN model corresponds to one CAMI model.
		final Vector<String> camiModelsOut = new Vector<String>(500, 250);
		final List<CamiRepository> camiRepositories = new ArrayList<CamiRepository>();
		try {
			MR.createDocumentWorkspace(pnmlFileIn);
			final HLAPIRootClass myPetriNetDoc = importPNMLDocument(pnmlFileIn);
			final PNType pnType = determineNetType(myPetriNetDoc);

			if (pnType != null) {
				if (pnType == PNType.PTNET) {
					final PTNet2CamiModel pt2cami = new PTNet2CamiModel();
					final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI ptNetDoc = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) myPetriNetDoc;
					for (final fr.lip6.move.pnml.ptnet.hlapi.PetriNetHLAPI ptNet : ptNetDoc.getNetsHLAPI()) {
						final CamiRepository cr = new CamiRepositoryImpl();
						pt2cami.buildCamiModel(cr, ptNet);
						camiRepositories.add(cr);
					}
				} else if (pnType == PNType.SYMNET) { // SN case
					System.err.println("Symmetric net export into Cami not yet implemented");
					// TODO : implement SN processing
				} else if (pnType == PNType.HLPN) {
					System.err.println("HLPN net export into Cami not yet implemented");
					// TODO convert the net into Cami with annotations as CM or
					// CT. Validation will fail with Cami syntax checker.
					JOURNAL.warn(HLPN_CAMI);
					System.err.println(HLPN_CAMI);
				} else if (pnType == PNType.COREMODEL) {
					// TODO export as a PT.
					System.out.println("Core model export into Cami not yet implemented");
					JOURNAL.warn(COREMODE_CAMI);
					System.err.println(COREMODE_CAMI);
				}

			} else {
				// TODO: Best effort: we just retrieve the net graph -> core
				// model
				System.out.println("Best effort strategy not yet implemented");
				JOURNAL.warn(COREMODE_CAMI);
				System.err.println(COREMODE_CAMI);
			}
			MR.destroyCurrentWorkspace();
			final CamiExporter cexp = new CamiExporter();
			for (CamiRepository cr : camiRepositories) {
				cexp.exportCAMIModel(cr, camiModelsOut);
			}
		} catch (final CamiException ce) {
			throw ce;
		} catch (final VoidRepositoryException e) {
			throw new CamiException(e.getMessage());
		} catch (final InvalidIDException e) {
			throw new CamiException(e.getMessage());
		}

		return camiModelsOut;
	}
} // Pnml2CamiImpl
