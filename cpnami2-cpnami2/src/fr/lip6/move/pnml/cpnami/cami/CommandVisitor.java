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
package fr.lip6.move.pnml.cpnami.cami;

/**
 * 
 * @author bbouzereau
 *
 */

public interface CommandVisitor {
	
	/**
	 * @param command 
	 * 			Cami Command 
	 */
	void visitAs(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCa(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCm(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCn(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCs(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCt(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitDb(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitDe(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitFb(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitFe(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPi(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPo(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPt(Command command);

}
