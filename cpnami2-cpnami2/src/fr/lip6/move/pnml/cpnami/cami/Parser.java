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

import java.util.List;

import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Parser</b></em>'.
 * Parses a given Cami command string. From this parsing, the command id (e.g.,
 * CA, CN, etc.) and its attributes list can be retrieved. <!-- end-user-doc -->
 * 
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage#getParser()
 */
public interface Parser {

    /**.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return Return Cami command in String type
     * @throws NotCamiCommandException
     * 			throws NotCamiCommandException
     * @throws NotCamiCommandException 
     */
    String getCommand() throws NotCamiCommandException;

    /**.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return List of all attributes
     * @throws NotCamiCommandException
     * 			throws NotCamiCommandException
     */
    List<String> getAttributes() throws NotCamiCommandException;

    /**.
     * Parses a Cami command initially given as a string.
     * @param toParse 
     * 			String to parse
     * @throws ParseException
     * 			throws NotCamiCommandException
     */
    void parse(String toParse) throws ParseException;

} // Parser
