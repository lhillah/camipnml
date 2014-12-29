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
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Cs;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Cs</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CsImpl#getAssociatedNode
 * <em>Associated Node</em>}</li>
 * <li>
 * {@link fr.lip6.move.pnml.cpnami.cami.model.impl.CsImpl#getDescriptionNode
 * <em>Description Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CsImpl#getDescNodeType
 * <em>Desc Node Type</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CsImpl#getDescNodeValue
 * <em>Desc Node Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CsImpl extends CommandImpl implements Cs {
	/**
	 * Identifiant de la commande.
	 */
	public final static String identifier = "CS";
	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
	 */
	public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.CS_LITERAL;

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
	 * The default value of the '{@link #getDescriptionNode()
	 * <em>Description Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescriptionNode()
	 * @generated
	 * @ordered
	 */
	protected static final int DESCRIPTION_NODE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDescriptionNode()
	 * <em>Description Node</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescriptionNode()
	 * @generated
	 * @ordered
	 */
	protected int descriptionNode = DESCRIPTION_NODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescNodeType()
	 * <em>Desc Node Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeType()
	 * @generated
	 * @ordered
	 */
	protected static final int DESC_NODE_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDescNodeType()
	 * <em>Desc Node Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeType()
	 * @generated
	 * @ordered
	 */
	protected int descNodeType = DESC_NODE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescNodeValue()
	 * <em>Desc Node Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DESC_NODE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescNodeValue()
	 * <em>Desc Node Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeValue()
	 * @generated
	 * @ordered
	 */
	protected String descNodeValue = DESC_NODE_VALUE_EDEFAULT;

	/**
	 * This is true if the Desc Node Value attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean descNodeValueESet = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CsImpl() {
		super();
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
	public int getDescriptionNode() {
		return descriptionNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescriptionNode(int newDescriptionNode) {
		int oldDescriptionNode = descriptionNode;
		descriptionNode = newDescriptionNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getDescNodeType() {
		return descNodeType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescNodeType(int newDescNodeType) {
		int oldDescNodeType = descNodeType;
		descNodeType = newDescNodeType;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getDescNodeValue() {
		return descNodeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescNodeValue(String newDescNodeValue) {
		String oldDescNodeValue = descNodeValue;
		descNodeValue = newDescNodeValue;
		boolean oldDescNodeValueESet = descNodeValueESet;
		descNodeValueESet = true;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetDescNodeValue() {
		String oldDescNodeValue = descNodeValue;
		boolean oldDescNodeValueESet = descNodeValueESet;
		descNodeValue = DESC_NODE_VALUE_EDEFAULT;
		descNodeValueESet = false;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetDescNodeValue() {
		return descNodeValueESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void setCs(Parser parser) {
		try {
			setCommand(parser);
		} catch (NotCamiCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void setCs(int associatedNode, int descriptionNode,
			int descNodeType, String descNodeValue) {
		this.id = identifier;
		setAssociatedNode(associatedNode);
		setDescriptionNode(descriptionNode);
		setDescNodeType(descNodeType);
		if (descNodeValue != null)
			setDescNodeValue(descNodeValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convert2String() {
		StringBuilder command = getCommandAsString();
		return command.toString();
	}

	/**
	 * @return
	 */
	private StringBuilder getCommandAsString() {
		StringBuilder command = new StringBuilder();
		command.append("(").append(this.associatedNode).append(",")
				.append(this.descriptionNode).append(",")
				.append(this.descNodeType).append(",");
		if (this.descNodeValue != null)
			command.append(this.descNodeValue.length()).append(":")
					.append(this.descNodeValue).append(")");
		else
			command.append(")");
		return command;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (associatedNode: ");
		result.append(associatedNode);
		result.append(", descriptionNode: ");
		result.append(descriptionNode);
		result.append(", descNodeType: ");
		result.append(descNodeType);
		result.append(", descNodeValue: ");
		if (descNodeValueESet)
			result.append(descNodeValue);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

	@Override
	protected List getAttributes() {
		ArrayList attributes = new ArrayList();
		attributes.add(new Integer(associatedNode));
		attributes.add(new Integer(descriptionNode));
		attributes.add(new Integer(descNodeType));
		if (isSetDescNodeValue())
			attributes.add(descNodeValue);
		return attributes;
	}

	@Override
	protected void setAttributes(List attributes) {
		if (attributes.size() < 3)
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
		else {
			Iterator i = attributes.iterator();
			Integer anode = (Integer) i.next();
			setAssociatedNode(anode.intValue());
			Integer descnode = (Integer) i.next();
			setDescriptionNode(descnode.intValue());
			Integer descnodeType = (Integer) i.next();
			setDescNodeType(descnodeType.intValue());
			if (attributes.size() == 4)
				setDescNodeValue((String) i.next());
		}
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visitCs(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lip6.move.pnml.cpnami.cami.Command#getCCIdentifier()
	 */
	public CAMICOMMANDS getCCIdentifier() {
		return idCAMICOMMAND;
	}

	public void setAssociatedNode(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String convert2StringNL() {
		
		return (getCommandAsString().append(NL)).toString();
	}

} // CsImpl
