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
 * <em><b>Cn</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cn#getNodeType <em>Node Type
 * </em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.Cn#getNodeId <em>Node Id</em>}
 * </li>
 * </ul>
 * </p>
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCn()
 */
public interface Cn extends Command {
	/**
	 * Returns the value of the '<em><b>Node Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Type</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Node Type</em>' attribute.
	 * @see #setNodeType(String)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCn_NodeType()
	 */
	String getNodeType();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cn#getNodeType
	 * <em>Node Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Node Type</em>' attribute.
	 * @see #getNodeType()
	 */
	void setNodeType(String value);

	/**
	 * Returns the value of the '<em><b>Node Id</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Id</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Node Id</em>' attribute.
	 * @see #setNodeId(int)
	 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getCn_NodeId()
	 */
	int getNodeId();

	/**
	 * Sets the value of the '
	 * {@link fr.lip6.move.pnml.cpnami.cami.model.Cn#getNodeId <em>Node Id</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Node Id</em>' attribute.
	 * @see #getNodeId()
	 */
	void setNodeId(int value);

	/**
	 * Sets a Cn command with its parameters.
	 * 
	 * @param nodeType
	 *            the node type: place, transition or net.
	 * @param nodeId
	 *            the node Id.
	 */
	void setCn(String nodeType, int nodeId);

	/**
	 * Sets the parser with the Cn command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setCn(Parser parser);

} // Cn
