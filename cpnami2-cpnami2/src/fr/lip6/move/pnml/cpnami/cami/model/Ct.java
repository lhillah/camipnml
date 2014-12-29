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
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Ct</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getAttributeName <em>
 * Attribute Name</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getAssociatedNode <em>
 * Associated Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCt()
 */
public interface Ct extends Command {
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCt_AttributeName()
	 */
	String getAttributeName();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getAttributeName
	 * <em>Attribute Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Attribute Name</em>' attribute.
	 * @see #getAttributeName()
	 */
	void setAttributeName(String value);

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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCt_AssociatedNode()
	 */
	int getAssociatedNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getAssociatedNode
	 * <em>Associated Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Associated Node</em>' attribute.
	 * @see #getAssociatedNode()
	 */
	void setAssociatedNode(int value);

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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCt_Value()
	 */
	String getValue();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ct#getValue <em>Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 */
	void setValue(String value);

	/**
	 * Sets the parser with the Ct command.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setCt(Parser parser);

	/**
	 * Sets the Ct command with its parameters.
	 * 
	 * @param attributeName
	 *            the correspondint attribute name.
	 * @param associatedNode
	 *            the node it is associated to
	 * @param value
	 *            the attribute value.
	 */
	void setCt(String attributeName, int associatedNode, String value);

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Ct
