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
package fr.lip6.move.pnml.cpnami.cami;



import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.exceptions.ConversionException;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Command</b></em>'.
 * Represents the abstraction of cami command concept. Any concrete cami command
 * must extend this interface. <!-- end-user-doc -->
 * 
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage#getCommand()
 * @model abstract="true"
 * @generated
 */
public interface Command {
    /**.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return return  identifier object
     * @model parameters=""
     * @generated
     */
    String getIdentifier();

    /**.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return cami identifier command
     * @model parameters=""
     */
    CAMICOMMANDS getCCIdentifier();

    /**.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return Cami file in string format
     * @model
     * @generated
     * @throws ConversionException
     * 			throws ConversionException if an error occured during string creation
     */
    String toCamiString() throws ConversionException;

    /**.
     *Sets this command with what the parser has parsed.
     * @param parser
     * 			use a parser to read the command
     * @throws NotCamiCommandException the parser does not hold any comand.
     */
    void setCommand(Parser parser) throws NotCamiCommandException;

    /**
     * 
     * @param visitor
     * 			TODO : what is the functionality of this method
     */
    void accept(CommandVisitor visitor);
    
	/**
	 * String representation of the Cn command.
	 * 
	 * @return the string representation of this Cn command.
	 */
	String convert2String();
	
	/**
	 * String representation of the Cn command, appending a new line.
	 * 
	 * @return the string representation of this Cn command.
	 */
	String convert2StringNL();

} // Command
