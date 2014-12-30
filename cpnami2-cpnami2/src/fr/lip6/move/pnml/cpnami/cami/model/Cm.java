/**
 *  Copyright 2009-2015 Universite Paris Ouest and Sorbonne Universites,
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
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Cm</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getAssociatedNode <em>
 * Associated Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getLineNumber <em>Line
 * Number</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getValue <em>Value</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getAttributeName <em>
 * Attribute Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCm()
 */
public interface Cm extends Command {
	/**
	 * Returns the value of the '<em><b>Associated Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Associated Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Associated Node</em>' attribute.
	 * @see #setAssociatedNode(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCm_AssociatedNode()
	 */
	int getAssociatedNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getAssociatedNode
	 * <em>Associated Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Associated Node</em>' attribute.
	 * @see #getAssociatedNode()
	 */
	void setAssociatedNode(int value);

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Number</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCm_LineNumber()
	 */
	int getLineNumber();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getLineNumber
	 * <em>Line Number</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 */
	void setLineNumber(int value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCm_Value()
	 */
	String getValue();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getValue <em>Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Attribute Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attribute Name</em>' attribute.
	 * @see #setAttributeName(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCm_AttributeName()
	 */
	String getAttributeName();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cm#getAttributeName
	 * <em>Attribute Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Attribute Name</em>' attribute.
	 * @see #getAttributeName()
	 */
	void setAttributeName(String value);

	/**
	 * Sets the parser with the Cm command.
	 * 
	 * @param parser
	 *            the Cami parser
	 */
	void setCm(Parser parser);

	/**
	 * Sets the Cm command with its parameters.
	 * 
	 * @param attributeName
	 *            the attribute for the command
	 * @param associatedNode
	 *            the node to which it is associated
	 * @param lineNumber
	 *            the line number
	 * @param value
	 *            the value of the attribute
	 */
	void setCm(String attributeName, int associatedNode, int lineNumber,
			String value);

	/**
	 * Converts the command into a string.
	 * 
	 * @return the command in a string format.
	 */
	String convert2String();

} // Cm
