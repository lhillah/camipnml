/**
 *  Copyright 2009 Universite Paris Ouest Nanterre & Sorbonne Universites, Univ. Paris 06 - CNRS UMR 7606 (LIP6/MoVe)
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
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Fb</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getFb()
 */
public interface Fe extends Command {
	/**
	 * Sets the parser with the Fe command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setFe(Parser parser);

	/**
	 * Sets the Fe command.
	 */
	void setFe();

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Fe
