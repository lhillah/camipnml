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
 * <em><b>Pt</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getNumObject <em>Num Object
 * </em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getH <em>H</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getV <em>V</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getAttributeName <em>
 * Attribute Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPt()
 */
public interface Pt extends Command {
	/**
	 * Returns the value of the '<em><b>Num Object</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Num Object</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Num Object</em>' attribute.
	 * @see #setNumObject(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPt_NumObject()
	 */
	int getNumObject();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getNumObject
	 * <em>Num Object</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Num Object</em>' attribute.
	 * @see #getNumObject()
	 */
	void setNumObject(int value);

	/**
	 * Returns the value of the '<em><b>H</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * This attribute corresponds to the X coordinate.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>H</em>' attribute.
	 * @see #setH(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPt_H()
	 */
	int getH();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getH <em>H</em>}'
	 * attribute. <!-- begin-user-doc --> Sets the value of the X coordinate.
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>H</em>' attribute.
	 * @see #getH()
	 * @generated
	 */
	void setH(int value);

	/**
	 * Returns the value of the '<em><b>V</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * This attribute corresponds to the Y coordinate.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>V</em>' attribute.
	 * @see #setV(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPt_V()
	 * @model
	 * @generated
	 */
	int getV();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getV <em>V</em>}'
	 * attribute. <!-- begin-user-doc --> Sets the value of the Y coordinate.
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>V</em>' attribute.
	 * @see #getV()
	 */
	void setV(int value);

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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPt_AttributeName()
	 */
	String getAttributeName();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Pt#getAttributeName
	 * <em>Attribute Name</em>}' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>Attribute Name</em>' attribute.
	 * @see #getAttributeName()
	 */
	void setAttributeName(String value);

	/**
	 * Sets the parser with the Pt command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setPt(Parser parser);

	/**
	 * Set the Pt command with its parameters.
	 * 
	 * @param numObject
	 *            the Id of the node it is associated to.
	 * @param attribute
	 *            the corresponding attribute
	 * @param h
	 *            the x coordinate
	 * @param v
	 *            the y coordinate
	 */
	void setPt(int numObject, String attribute, int h, int v);

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Pt
