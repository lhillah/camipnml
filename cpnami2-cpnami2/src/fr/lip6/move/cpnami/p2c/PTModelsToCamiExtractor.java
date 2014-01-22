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
package fr.lip6.move.cpnami.p2c;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.constructor.CamiExporter;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiRepositoryImpl;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;

/**
 * Extracts PT models from PNML to Cami.
 * 
 * @author lom
 * 
 */
public final class PTModelsToCamiExtractor {

	
	private final CamiExporter camiExporter;
	private final PTNet2CamiModel myPTNet2Cami;
	/**
	 * Constructor.
	 */
	public PTModelsToCamiExtractor() {
		super();
		camiExporter = new CamiExporter();
		myPTNet2Cami = new PTNet2CamiModel();
	}

	/**
	 * Determines if one PNML document exported Petri net models should span
	 * across multiples Cami files, in case a single Cami output file was given,
	 * instead of multiple corresponding Cami files.
	 * 
	 * @param myPetriNetDoc
	 *            the PNML document
	 * @param camiFile
	 *            the initial single output Cami file which might be multiplied
	 * @param camiFiles
	 *            the initial list of output Cami files
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final void extractPNModels(final HLAPIRootClass myPetriNetDoc, final String camiFile, final List<String> camiFiles) throws CamiException {
		int camiFilesSuffix = 1;
		try {

			if (camiFile == null && camiFiles != null) {
				exportPTModel(myPetriNetDoc, camiFiles);
			} else if (camiFiles == null && camiFile != null) {
				final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI rootClass = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) myPetriNetDoc;
				final List<String> outputCamiFiles = new ArrayList<String>(rootClass.getNetsHLAPI().size());
				if (rootClass.getNetsHLAPI().size() > 1) {
					for (final fr.lip6.move.pnml.ptnet.hlapi.PetriNetHLAPI netElement : rootClass.getNetsHLAPI()) {
						final String outputCamiFile = camiFile + netElement.getNameHLAPI() != null ? netElement.getNameHLAPI().getText() : camiFile
								+ String.valueOf(camiFilesSuffix++);
						outputCamiFiles.add(outputCamiFile);
					}
					exportPTModel(myPetriNetDoc, outputCamiFiles);
				} else {
					outputCamiFiles.add(camiFile);
					exportPTModel(myPetriNetDoc, outputCamiFiles);
				}
			} else {
				throw new CamiException("Unsupported extraction pattern for PT models into Cami files");
			}
		} catch (final CamiException ce) {
			throw ce;
		}
	}

	/**
	 * Exports a PNML document Petri net models into Cami files.
	 * 
	 * @param myPetriNetDoc
	 *            the PNML document
	 * @param camiFiles
	 *            the output Cami files.
	 * @throws CamiException
	 *             Cami exception
	 */
	private final void exportPTModel(final HLAPIRootClass myPetriNetDoc, final List<String> camiFiles) throws CamiException {
		int camiFilesIter = 0;
		 CamiRepository cr;
		try {
			final fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI rootClass = (fr.lip6.move.pnml.ptnet.hlapi.PetriNetDocHLAPI) myPetriNetDoc;
			for (final fr.lip6.move.pnml.ptnet.hlapi.PetriNetHLAPI netElement : rootClass.getNetsHLAPI()) {
				cr = new CamiRepositoryImpl();
				myPTNet2Cami.buildCamiModel(cr, netElement);
				camiExporter.exportCamiModel(cr, camiFiles.get(camiFilesIter));
				cr = null;
				camiFilesIter++;
			}
		} catch (final CamiException ce) {
			throw ce;
		}
	}

}
