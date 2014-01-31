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
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Ca</b></em>'. This command reprensents the creation of a link between
 * two cami nodes. This link could be an arc. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getArcID <em>Arc ID</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getArcType <em>Arc Type
 * </em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getStartNodeID <em>Start
 * Node ID</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getEndNodeID <em>End Node
 * ID</em>}</li>
 * </ul>
 * </p>
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCa()
 */
public interface Ca extends Command {
	/**
	 * Returns the value of the '<em><b>Arc ID</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arc ID</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arc ID</em>' attribute.
	 * @see #setArcID(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCa_ArcID()
	 */
	int getArcID();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getArcID <em>Arc ID</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arc ID</em>' attribute.
	 * @see #getArcID()
	 */
	void setArcID(int value);

	/**
	 * Returns the value of the '<em><b>Arc Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * An arc type may be <em>arc</em> or <em>link</em> etc.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arc Type</em>' attribute.
	 * @see #setArcType(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCa_ArcType()
	 */
	String getArcType();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getArcType
	 * <em>Arc Type</em>}' attribute. <!-- begin-user-doc --> An arc type may be
	 * <em>arc</em> or <em>link</em> etc. <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arc Type</em>' attribute.
	 * @see #getArcType()
	 */
	void setArcType(String value);

	/**
	 * Returns the value of the '<em><b>Start Node ID</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * Returns the source node id of this arc.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Node ID</em>' attribute.
	 * @see #setStartNodeID(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCa_StartNodeID()
	 */
	int getStartNodeID();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getStartNodeID
	 * <em>Start Node ID</em>}' attribute. <!-- begin-user-doc --> Sets the
	 * source node id of this arc. <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Node ID</em>' attribute.
	 * @see #getStartNodeID()
	 */
	void setStartNodeID(int value);

	/**
	 * Returns the value of the '<em><b>End Node ID</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * Returns the target node id of this arc.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Node ID</em>' attribute.
	 * @see #setEndNodeID(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCa_EndNodeID()
	 */
	int getEndNodeID();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Ca#getEndNodeID
	 * <em>End Node ID</em>}' attribute. <!-- begin-user-doc --> Set the target
	 * node id of this arc. <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Node ID</em>' attribute.
	 * @see #getEndNodeID()
	 */
	void setEndNodeID(int value);

	/**
	 * Sets this arc object from a Cami parsed command.
	 * @param parser the parser which holds this command.
	 * @throws NotCamiCommandException non-recognized command
	 */
	void setCa(Parser parser) throws NotCamiCommandException;

	/**
	 * Sets this arc object directly from the arguments.
	 * @param arcType the arc type (normal, inhibitor, etc)
	 * @param arcId the arc id
	 * @param startNodeId the source node
	 * @param endNodeId the target node
	 */
	void setCa(String arcType, int arcId, int startNodeId, int endNodeId);

	/**
	 * Converts this command into a string.
	 * @return this command a a string.
	 */
	String convert2String();

} // Ca
