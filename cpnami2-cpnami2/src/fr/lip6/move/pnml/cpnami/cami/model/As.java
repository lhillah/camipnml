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
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>As</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.As#getAssociatedNode <em>
 * Associated Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.As#getDescriptionNode <em>
 * Description Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.As#getDescNodeFather <em>Desc
 * Node Father</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.As#getSubTree <em>Sub Tree
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getAs()
 */
public interface As extends Command {
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getAs_AssociatedNode()
	 */
	int getAssociatedNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getAssociatedNode
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getAs_DescriptionNode()
	 */
	int getDescriptionNode();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getDescriptionNode
	 * <em>Description Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Description Node</em>' attribute.
	 * @see #getDescriptionNode()
	 */
	void setDescriptionNode(int value);

	/**
	 * Returns the value of the '<em><b>Desc Node Father</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc Node Father</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Desc Node Father</em>' attribute.
	 * @see #setDescNodeFather(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getAs_DescNodeFather()
	 */
	int getDescNodeFather();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getDescNodeFather
	 * <em>Desc Node Father</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Desc Node Father</em>' attribute.
	 * @see #getDescNodeFather()
	 */
	void setDescNodeFather(int value);

	/**
	 * Returns the value of the '<em><b>Sub Tree</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Tree</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sub Tree</em>' attribute.
	 * @see #isSetSubTree()
	 * @see #unsetSubTree()
	 * @see #setSubTree(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getAs_SubTree()
	 * @model unsettable="true"
	 */
	String getSubTree();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getSubTree
	 * <em>Sub Tree</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Sub Tree</em>' attribute.
	 * @see #isSetSubTree()
	 * @see #unsetSubTree()
	 * @see #getSubTree()
	 */
	void setSubTree(String value);

	/**
	 * Unsets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getSubTree
	 * <em>Sub Tree</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #isSetSubTree()
	 * @see #getSubTree()
	 * @see #setSubTree(String)
	 */
	void unsetSubTree();

	/**
	 * Returns whether the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.As#getSubTree
	 * <em>Sub Tree</em>}' attribute is set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Sub Tree</em>' attribute is set.
	 * @see #unsetSubTree()
	 * @see #getSubTree()
	 * @see #setSubTree(String)
	 */
	boolean isSetSubTree();

	/**
	 * Sets the parser to parse As command.
	 * 
	 * @param parser
	 *            the parser.
	 */
	void setAs(Parser parser);

	/**
	 * Sets As command with given parameters.
	 * 
	 * @param associatedNode
	 *            the Cami node to which this command is associated
	 * @param descriptionNode
	 *            the description node
	 * @param descNodeFather
	 *            the description node father
	 * @param subTree
	 *            the subtree of the As command
	 */
	void setAs(int associatedNode, int descriptionNode, int descNodeFather,
			String subTree);

	/**
	 * Returns the string representation of the As command.
	 * 
	 * @return the string representation of the As command.
	 */
	String convert2String();

} // As
