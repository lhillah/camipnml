/**
 *  Copyright 2009-2015 Universite Paris Ouest and Sorbonne Universites,
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
import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>As</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.AsImpl#getAssociatedNode
 * <em>Associated Node</em>}</li>
 * <li>
 * {@link fr.lip6.move.pnml.cpnami.cami.model.impl.AsImpl#getDescriptionNode
 * <em>Description Node</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.AsImpl#getDescNodeFather
 * <em>Desc Node Father</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.AsImpl#getSubTree <em>Sub
 * Tree</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public class AsImpl extends CommandImpl implements As {
	/**
	 * Identifiant de la commande.
	 */
	public static final String IDENTIFIER = "AS";

	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum
	 * CAMICOMMANDS.
	 */
	public static final CAMICOMMANDS ID_CAMICOMMAND = CAMICOMMANDS.AS_LITERAL;

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
	 * The default value of the '{@link #getDescNodeFather()
	 * <em>Desc Node Father</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeFather()
	 * @generated
	 * @ordered
	 */
	protected static final int DESC_NODE_FATHER_EDEFAULT = 0;
	/**
	 * The default value of the '{@link #getSubTree() <em>Sub Tree</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubTree()
	 * @generated
	 * @ordered
	 */
	protected static final String SUB_TREE_EDEFAULT = null;
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
	 * The cached value of the '{@link #getDescNodeFather()
	 * <em>Desc Node Father</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescNodeFather()
	 * @generated
	 * @ordered
	 */
	protected int descNodeFather = DESC_NODE_FATHER_EDEFAULT;
	/**
	 * The cached value of the '{@link #getSubTree() <em>Sub Tree</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSubTree()
	 * @generated
	 * @ordered
	 */
	protected String subTree = SUB_TREE_EDEFAULT;

	/**
	 * This is true if the Sub Tree attribute has been set. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean subTreeESet;

	/**
	 * Constructor.
	 * 
	 * @generated
	 */
	protected AsImpl() {
		super();
	}

	/**
	 * Returns associated node of this command.
	 * 
	 * @return the id of the associated node of this command.
	 */
	public final int getAssociatedNode() {
		return associatedNode;
	}

	/**
	 * Sets the associated node to this command.
	 * 
	 * @param newAssociatedNode
	 *            the id of the associated node.
	 */
	public final void setAssociatedNode(int newAssociatedNode) {
		this.associatedNode = newAssociatedNode;

	}

	/**
	 * Returns the id of the description node of this command.
	 * 
	 * @return the id of the description node.
	 */
	public final int getDescriptionNode() {
		return descriptionNode;
	}

	/**
	 * Sets the description node of this command.
	 * 
	 * @param newDescriptionNode
	 *            the description node id.
	 */
	public final void setDescriptionNode(int newDescriptionNode) {
		this.descriptionNode = newDescriptionNode;
	}

	/**
	 * Returns the id of the father of the description node of this command.
	 * 
	 * @return the id of the description node father.
	 */
	public final int getDescNodeFather() {
		return this.descNodeFather;
	}

	/**
	 * Set the id of the father of the description node of this command.
	 * 
	 * @param newDescNodeFather
	 *            the new id.
	 */
	public final void setDescNodeFather(int newDescNodeFather) {
		this.descNodeFather = newDescNodeFather;
	}

	/**
	 * Returns the subtree of this command.
	 * 
	 * @return the subtree of this command.
	 */
	public final String getSubTree() {
		return subTree;
	}

	/**
	 * Sets the subtree of this command.
	 * 
	 * @param newSubTree
	 *            the new subtree.
	 */
	public final void setSubTree(String newSubTree) {
		this.subTree = newSubTree;
		this.subTreeESet = true;

	}

	/**
	 * Unsets the subtree of this command.
	 * 
	 */
	public final void unsetSubTree() {
		this.subTree = SUB_TREE_EDEFAULT;
		this.subTreeESet = false;

	}

	/**
	 * Tells if the subtree is set.
	 * 
	 * @return true if the subtree is set.
	 */
	public final boolean isSetSubTree() {
		return subTreeESet;
	}

	/**
	 * Sets the parser for this command.
	 * 
	 * @param parser
	 *            the parser
	 */
	public final void setAs(Parser parser) {
		try {
			setCommand(parser);
		} catch (NotCamiCommandException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets As command with given parameters.
	 * 
	 * @param associatedNode0
	 *            the Cami node to which this command is associated
	 * @param descriptionNode0
	 *            the description node
	 * @param descNodeFather0
	 *            the description node father
	 * @param subTree0
	 *            the subtree of the As command
	 */
	public final void setAs(int associatedNode0, int descriptionNode0,
			int descNodeFather0, String subTree0) {
		this.id = IDENTIFIER;
		setAssociatedNode(associatedNode);
		setDescriptionNode(descriptionNode);
		setDescNodeFather(descNodeFather);
		setSubTree(subTree);
	}

	/**
	 * Returns the string representation of the As command.
	 * 
	 * @return the string representation of the As command.
	 */
	public final String convert2String() {

		final StringBuilder command = getCommandAsString();
		return command.toString();
	}

	/**
	 * @return
	 */
	private StringBuilder getCommandAsString() {
		final StringBuilder command = new StringBuilder();
		command.append(getIdentifier()).append("(").append(this.associatedNode)
				.append(",").append(this.descriptionNode).append(",")
				.append(this.descNodeFather).append(",")
				.append(this.subTree.length()).append(":").append(this.subTree)
				.append(")");
		return command;
	}

	/**
	 * Classic toString method.
	 * 
	 * @return a string which recap the contents of this command.
	 */
	@Override
	public final String toString() {

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (associatedNode: ");
		result.append(associatedNode);
		result.append(", descriptionNode: ");
		result.append(descriptionNode);
		result.append(", descNodeFather: ");
		result.append(descNodeFather);
		result.append(", subTree: ");
		if (subTreeESet) {
			result.append(subTree);
		} else {
			result.append("<unset>");
		}
		result.append(')');
		return result.toString();
	}

	/**
	 * Returns the list of this command attributes.
	 * 
	 * @return the list of attributes.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected final List getAttributes() {
		final ArrayList attributes = new ArrayList();
		attributes.add(new Integer(associatedNode));
		attributes.add(new Integer(descriptionNode));
		attributes.add(new Integer(descNodeFather));
		attributes.add(subTree);
		return attributes;
	}

	/**
	 * Sets the list of this command attributes.
	 * 
	 * @param attributes
	 *            the list of attributes.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected final void setAttributes(List attributes) {
		final int quatre = 4;
		if (attributes.size() != quatre) {
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
		} else {
			final Iterator i = attributes.iterator();
			final Integer anode = (Integer) i.next();
			setAssociatedNode(anode.intValue());
			final Integer descnode = (Integer) i.next();
			setDescriptionNode(descnode.intValue());
			final Integer descnodeFather = (Integer) i.next();
			setDescNodeFather(descnodeFather.intValue());
			setSubTree((String) i.next());
		}
	}

	/**
	 * Visitor patter.
	 * 
	 * @param visitor
	 *            the visitor.
	 */
	@Override
	public final void accept(CommandVisitor visitor) {
		visitor.visitAs(this);
	}

	/**
	 * Returns the enum value which identifies this Cami command.
	 * 
	 * @return the corresponding enum value which identifies this command.
	 * @see fr.lip6.move.pnml.cpnami.cami.Command#getCCIdentifier()
	 */
	public final CAMICOMMANDS getCCIdentifier() {
		return ID_CAMICOMMAND;
	}

	@Override
	public String convert2StringNL() {
		return (getCommandAsString().append(NL)).toString();
	}

} // AsImpl
