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
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ct</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CtImpl#getAttributeName
 * <em>Attribute Name</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CtImpl#getAssociatedNode
 * <em>Associated Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CtImpl#getValue <em>Value
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CtImpl extends CommandImpl implements Ct {
	/**
	 * Identifiant de la commande.
	 */
	public final static String identifier = "CT";
	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
	 */
	public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.CT_LITERAL;

	/**
	 * The default value of the '{@link #getAttributeName()
	 * <em>Attribute Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeName()
	 * <em>Attribute Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getAssociatedNode()
	 * <em>Associated Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAssociatedNode()
	 * @generated
	 * @ordered
	 */
	protected static final int ASSOCIATED_NODE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAssociatedNode()
	 * <em>Associated Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAssociatedNode()
	 * @generated
	 * @ordered
	 */
	protected int associatedNode = ASSOCIATED_NODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CtImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAttributeName(String newAttributeName) {
		String oldAttributeName = attributeName;
		attributeName = newAttributeName;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getAssociatedNode() {
		return associatedNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAssociatedNode(int newAssociatedNode) {
		int oldAssociatedNode = associatedNode;
		associatedNode = newAssociatedNode;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void setCt(Parser parser) {
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
	public void setCt(String attributeName, int associatedNode, String value) {
		this.id = identifier;
		if (attributeName == null) {
			throw new IllegalArgumentException(
					"Le nom de l'attribut ne doit pas etre null. ");
		}
		if (value == null) {
			throw new IllegalArgumentException(
					"La valeur de l'attribut ne doit pas etre null. ");
		}
		setAttributeName(attributeName);
		setAssociatedNode(associatedNode);
		setValue(value);
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
		result.append(getIdentifier()).append("(")
				.append(attributeName.length()).append(":")
				.append(attributeName).append(",").append(associatedNode)
				.append(",").append(value.length()).append(":").append(value)
				.append(")");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (attributeName: ");
		result.append(attributeName);
		result.append(", associatedNode: ");
		result.append(associatedNode);
		result.append(", value: ");
		result.append(value);
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

		attributes.add(attributeName);
		Integer anode = new Integer(associatedNode);
		attributes.add(anode);
		attributes.add(value);
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lip6.cami.impl.CommandImpl#setAttributes(java.util.List)
	 */
	@Override
	protected void setAttributes(List attributes) {
		if (attributes.size() != 3)
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
		else {
			Iterator i = attributes.iterator();
			setAttributeName((String) i.next());
			Integer anode = (Integer) i.next();
			setAssociatedNode(anode.intValue());
			setValue((String) i.next());
		}
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visitCt(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lip6.move.pnml.cpnami.cami.Command#getCCIdentifier()
	 */
	public CAMICOMMANDS getCCIdentifier() {
		return idCAMICOMMAND;
	}

	@Override
	public String convert2StringNL() {
		return (getCommandAsString().append(NL)).toString();
	}

} // CtImpl
