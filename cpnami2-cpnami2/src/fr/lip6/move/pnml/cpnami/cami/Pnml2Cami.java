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
package fr.lip6.move.pnml.cpnami.cami;

import java.util.List;
import java.util.Vector;

import fr.lip6.move.pnml.cpnami.exceptions.CamiException;

/**
 * <!-- begin-user-doc -->
 * <p>
 * Implements model transformation from PNML models to CPN-AMI Petri net models
 * via PNML framework Petri nets models representation.
 * </p>
 * <p>
 * First, the PNML parser is called on the PNML file given as input. The parser
 * returns a PetriNetDoc object containing the model(s) parsed from the PNML
 * file. Then for each model, a PNML repository is created. Afterwards the model
 * elements are fetched into a Cami repository. A Cami model builder is then
 * asked to write the model into a Cami file.
 * </p>
 * <p>
 * This interface is implemented by a singleton.
 * </p>
 * <!-- end-user-doc -->
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage#getPnml2Cami()
 * 
 */
public interface Pnml2Cami {
	/**
	 * Converts Petri net models from PNML to CAMI. Each Page in the PNML Petri
	 * net model is transformed into an AMI-NET model. This program should be
	 * launched from the Runner Class.
	 * 
	 * 
	 * @param pnmlFileIn
	 *            input PNML file(s)
	 * @param camiFiles
	 *            the resulting CAMI file(s)
	 * @throws CamiException
	 *             throws CamiException
	 */
	void pnml2Cami(String pnmlFileIn, List<String> camiFiles) throws CamiException;

	/**
	 * Alternative method to convert PN models from PNML to Cami.
	 * 
	 * @param pnmlFileIn
	 *            list of PNML input files
	 * @param camiFiles
	 *            list of output Cami file(s), each for each PNML input file.
	 * @throws CamiException
	 *             throws CamiException
	 */
	void pnml2Cami(List<String> pnmlFileIn, List<String> camiFiles) throws CamiException;

	/**
	 * Alternative method to transform PN models from PNML to Cami.
	 * 
	 * @return returns an array of CAMI commands which build the CAMI models.
	 * @param pnmlFileIn
	 *            the PNML file from which to load PNML models.
	 * @throws CamiException
	 *             throws CamiException
	 */
	Vector<String> pnml2cami(String pnmlFileIn) throws CamiException;

} // Pnml2Cami
