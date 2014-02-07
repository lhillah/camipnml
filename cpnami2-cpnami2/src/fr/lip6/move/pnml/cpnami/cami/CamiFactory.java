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

import fr.lip6.move.pnml.cpnami.cami.impl.CamiFactoryImpl;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;
import fr.lip6.move.pnml.cpnami.exceptions.UnhandledCommandException;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage
 * @generated
 */
public interface CamiFactory {
	/**
	 * The singleton instance of the factory.
	 * 
	 */
	CamiFactory SINSTANCE = CamiFactoryImpl.getCamiFactoryInstance();

	/**
	 * Returns a new instance of class '<em>Cami2Pnml</em>' with which conversion
	 * of CAMI models to PNML models can be performed.
	 * 
	 * @return a new object of class '<em>Cami2Pnml</em>'.
	 */
	Cami2Pnml createCami2Pnml();

	/**
	 * Returns a new instance of class '<em>Pnml2Cami</em>' with which conversion
	 * of PNML models to CAMI models can be performed.
	 * 
	 * @return a new object of class '<em>Pnml2Cami</em>'.
	 */
	Pnml2Cami createPnml2Cami();

	/**
	 * Returns the singleton instance of class '<em>Parser</em>', which parses CAMI
	 * command strings and produces the corresponding CAMI command Java object.
	 * 
	 * @return the singleton instance of class '<em>Parser</em>'.
	 */
	Parser createParser();

	/**
	 * Returns a new CAMI command from a parsed string.
	 * 
	 * @param command
	 *            is the Cami command string read from a file and to be parsed.
	 * @return a new Object of class '<em>Command</em>'.
	 * @throws UnhandledCommandException
	 *             Cami command not handled
	 * @throws ParseException
	 *             parse exception
	 * @throws NotCamiCommandException
	 *             the parser does not hold any command.
	 */
	Command createACommand(String command) throws UnhandledCommandException,
			ParseException, NotCamiCommandException;

	/**
	 * Returns the singleton instance of '<em>Runner</em>'. The
	 * Runner is the entry point to drive any model conversion, from CAMI to
	 * PNML or the other way round.
	 * 
	 * @return the singleton instance of class '<em>Runner</em>'.
	 */
	Runner createRunner();

} // CamiFactory
