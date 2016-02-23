/**
 *  Copyright 2009-2016 Universite Paris Ouest and Sorbonne Universites,
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
 * <em><b>Cs</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getAssociatedNode <em>
 * Associated Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescriptionNode <em>
 * Description Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeType <em>Desc
 * Node Type</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeValue <em>Desc
 * Node Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCs()
 */
public interface Cs extends Command {
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCs_AssociatedNode()
	 */
	int getAssociatedNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getAssociatedNode
	 * <em>Associated Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Associated Node</em>' attribute.
	 * @see #getAssociatedNode()
	 */
	void setAssociatedNode(int value);

	/**
	 * Returns the value of the '<em><b>Description Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description Node</em>' attribute.
	 * @see #setDescriptionNode(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCs_DescriptionNode()
	 */
	int getDescriptionNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescriptionNode
	 * <em>Description Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Description Node</em>' attribute.
	 * @see #getDescriptionNode()
	 */
	void setDescriptionNode(int value);

	/**
	 * Returns the value of the '<em><b>Desc Node Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc Node Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Desc Node Type</em>' attribute.
	 * @see #setDescNodeType(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCs_DescNodeType()
	 */
	int getDescNodeType();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeType
	 * <em>Desc Node Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Desc Node Type</em>' attribute.
	 * @see #getDescNodeType()
	 */
	void setDescNodeType(int value);

	/**
	 * Returns the value of the '<em><b>Desc Node Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc Node Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Desc Node Value</em>' attribute.
	 * @see #isSetDescNodeValue()
	 * @see #unsetDescNodeValue()
	 * @see #setDescNodeValue(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCs_DescNodeValue()
	 * @model unsettable="true"
	 */
	String getDescNodeValue();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeValue
	 * <em>Desc Node Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Desc Node Value</em>' attribute.
	 * @see #isSetDescNodeValue()
	 * @see #unsetDescNodeValue()
	 * @see #getDescNodeValue()
	 */
	void setDescNodeValue(String value);

	/**
	 * Unsets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeValue
	 * <em>Desc Node Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isSetDescNodeValue()
	 * @see #getDescNodeValue()
	 * @see #setDescNodeValue(String)
	 */
	void unsetDescNodeValue();

	/**
	 * Returns whether the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cs#getDescNodeValue
	 * <em>Desc Node Value</em>}' attribute is set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Desc Node Value</em>' attribute is
	 *         set.
	 * @see #unsetDescNodeValue()
	 * @see #getDescNodeValue()
	 * @see #setDescNodeValue(String)
	 */
	boolean isSetDescNodeValue();

	/**
	 * Sets the Cami parser with the Cs command.
	 * 
	 * @param parser
	 *            the Cami parser.
	 */
	void setCs(Parser parser);

	/**
	 * Sets a Cs command with its parameters.
	 * 
	 * @param associatedNode
	 *            the node it is associated to.
	 * @param descriptionNode
	 *            the description node
	 * @param descNodeType
	 *            the type of the description node
	 * @param descNodeValue
	 *            the desc node value
	 */
	void setCs(int associatedNode, int descriptionNode, int descNodeType,
			String descNodeValue);

	/**
	 * Returns a string representation of the Cs command.
	 * 
	 * @return the string representation of this Cs command.
	 */
	String convert2String();

} // Cs
