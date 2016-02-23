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
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Cn</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CnImpl#getNodeType <em>
 * Node Type</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CnImpl#getNodeId <em>Node
 * Id</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CnImpl extends CommandImpl implements Cn {
	/**
	 * Identifiant de la commande.
	 */
	public final static String identifier = "CN";
	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
	 */
	public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.CN_LITERAL;

	/**
	 * The default value of the '{@link #getNodeType() <em>Node Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodeType()
	 * @generated
	 * @ordered
	 */
	protected static final String NODE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNodeType() <em>Node Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodeType()
	 * @generated
	 * @ordered
	 */
	protected String nodeType = NODE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNodeId() <em>Node Id</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodeId()
	 * @generated
	 * @ordered
	 */
	protected static final int NODE_ID_EDEFAULT = 0;
	private static final String NL = "\n";

	/**
	 * The cached value of the '{@link #getNodeId() <em>Node Id</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodeId()
	 * @generated
	 * @ordered
	 */
	protected int nodeId = NODE_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CnImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> Returns the type of the node created by the CN
	 * command. <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getNodeType() {
		return nodeType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNodeType(String newNodeType) {
		String oldNodeType = nodeType;
		nodeType = newNodeType;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNodeId(int newNodeId) {
		int oldNodeId = nodeId;
		nodeId = newNodeId;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void setCn(String nodeType, int nodeId) {
		this.id = identifier;
		if (nodeType == null) {
			throw new IllegalArgumentException(
					"Le type du noeud ne doit pas etre null. ");
		}
		setNodeType(nodeType);
		setNodeId(nodeId);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void setCn(Parser parser) {
		try {
			setCommand(parser);
		} catch (NotCamiCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public String convert2String() {
		StringBuilder result = getCommandAsString();
		return result.toString();
	}

	/**
	 * @return
	 */
	private StringBuilder getCommandAsString() {
		StringBuilder result = new StringBuilder();
		result.append(getIdentifier()).append("(").append(nodeType.length())
				.append(":").append(nodeType).append(",").append(nodeId)
				.append(")");
		return result;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public String convert2StringNL() {
		return (getCommandAsString().append(NL)).toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (nodeType: ");
		result.append(nodeType);
		result.append(", nodeId: ");
		result.append(nodeId);
		result.append(')');
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lip6.cami.impl.CommandImpl#getAttributes()
	 */
	@Override
	protected List getAttributes() {
		ArrayList attributes = new ArrayList();

		attributes.add(nodeType);
		Integer nid = new Integer(nodeId);
		attributes.add(nid);

		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lip6.cami.impl.CommandImpl#setAttributes(java.util.List)
	 */
	@Override
	protected void setAttributes(List attributes) {
		if (attributes.size() != 2)
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
		else {
			Iterator i = attributes.iterator();
			setNodeType((String) i.next());
			Integer nid = (Integer) i.next();
			setNodeId(nid.intValue());
		}

	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visitCn(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lip6.move.pnml.cpnami.cami.Command#getCCIdentifier()
	 */
	public CAMICOMMANDS getCCIdentifier() {
		return idCAMICOMMAND;
	}

} // CnImpl
