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
 * <em><b>Po</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Po#getNumObject <em>Num Object
 * </em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Po#getH <em>H</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Po#getV <em>V</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPo()
 */
public interface Po extends Command {
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPo_NumObject()
	 */
	int getNumObject();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Po#getNumObject
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPo_H()
	 */
	int getH();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Po#getH <em>H</em>}'
	 * attribute. <!-- begin-user-doc --> Sets the value of the X coordinate.
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>H</em>' attribute.
	 * @see #getH()
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
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getPo_V()
	 */
	int getV();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Po#getV <em>V</em>}'
	 * attribute. <!-- begin-user-doc --> Sets the value of the Y coordinate.
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>V</em>' attribute.
	 * @see #getV()
	 */
	void setV(int value);

	/**
	 * Sets the parser with the Po command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setPo(Parser parser);

	/**
	 * Sets the Po commands with its parameters.
	 * 
	 * @param numObject
	 *            the Id of the node it is associated to.
	 * @param h
	 *            the x coordinate
	 * @param v
	 *            the y coordinate
	 */
	void setPo(int numObject, int h, int v);

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Po
