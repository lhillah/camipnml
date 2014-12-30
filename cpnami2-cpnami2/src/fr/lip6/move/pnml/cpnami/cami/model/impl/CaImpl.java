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
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ca</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CaImpl#getArcID <em>Arc
 * ID</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CaImpl#getArcType <em>Arc
 * Type</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CaImpl#getStartNodeID
 * <em>Start Node ID</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.CaImpl#getEndNodeID <em>
 * End Node ID</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CaImpl extends CommandImpl implements Ca {

	/**
	 * Identifiant de la commande.
	 */
	public static final String IDENTIFIER = "CA";

	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum
	 * CAMICOMMANDS.
	 */
	public static final CAMICOMMANDS ID_CAMICOMMAND = CAMICOMMANDS.CA_LITERAL;

	/**
	 * The default value of the '{@link #getArcID() <em>Arc ID</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArcID()
	 */
	protected static final int ARC_ID_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getArcType() <em>Arc Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArcType()
	 */
	protected static final String ARC_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getStartNodeID()
	 * <em>Start Node ID</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getStartNodeID()
	 */
	protected static final int START_NODE_ID_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getEndNodeID() <em>End Node ID</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndNodeID()
	 */
	protected static final int END_NODE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getArcType() <em>Arc Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArcType()
	 */
	protected String arcType = ARC_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArcID() <em>Arc ID</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArcID()
	 */
	protected int arcID = ARC_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStartNodeID() <em>Start Node ID</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartNodeID()
	 */
	protected int startNodeID = START_NODE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEndNodeID() <em>End Node ID</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndNodeID()
	 */
	protected int endNodeID = END_NODE_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> Constructeur.<!-- end-user-doc -->
	 */
	protected CaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> Returns the arc ID.<!-- end-user-doc -->
	 * 
	 * @return the arc ID.
	 */
	public final int getArcID() {
		return arcID;
	}

	/**
	 * <!-- begin-user-doc --> Sets the arc ID.<!-- end-user-doc -->
	 * 
	 * @param newArcID
	 *            the arc ID.
	 */
	public final void setArcID(int newArcID) {
		arcID = newArcID;
	}

	/**
	 * <!-- begin-user-doc --> Returns the arc type.<!-- end-user-doc -->
	 * 
	 * @return the arc type(normal, inhibitor, etc)
	 */
	public final String getArcType() {
		return arcType;
	}

	/**
	 * <!-- begin-user-doc --> Sets the arc type.<!-- end-user-doc -->
	 * 
	 * @param newArcType
	 *            the new arc type.
	 */
	public final void setArcType(String newArcType) {
		arcType = newArcType;
	}

	/** {@inheritDoc} */
	public final int getStartNodeID() {
		return startNodeID;
	}

	/** {@inheritDoc} */
	public final void setStartNodeID(int newStartNodeID) {
		startNodeID = newStartNodeID;
	}

	/** {@inheritDoc} */
	public final int getEndNodeID() {
		return endNodeID;
	}

	/** {@inheritDoc} */
	public final void setEndNodeID(int newEndNodeID) {
		endNodeID = newEndNodeID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ParseException
	 */
	public final void setCa(Parser parser) throws NotCamiCommandException {
		try {
			setCommand(parser);
		} catch (NotCamiCommandException e) {
			e.printStackTrace();
			throw e;

		}
	}

	/** {@inheritDoc} */
	public final void setCa(String theArcType, int arcId, int startNodeId,
			int endNodeId) {

		this.id = IDENTIFIER;
		if (theArcType == null) {
			throw new IllegalArgumentException(
					"Le type d'arc ne doit pas etre null. ");
		}
		setArcType(theArcType);
		setArcID(arcId);
		setStartNodeID(startNodeId);
		setEndNodeID(endNodeId);
	}

	/** {@inheritDoc} */
	public final String convert2String() {
		final StringBuilder result = getCommandAsString();
		return result.toString();
	}

	/**
	 * @return
	 */
	private StringBuilder getCommandAsString() {
		final StringBuilder result = new StringBuilder();
		result.append(getIdentifier()).append("(").append(arcType.length())
				.append(":").append(arcType).append(",").append(arcID)
				.append(",").append(startNodeID).append(",").append(endNodeID)
				.append(")");
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (arcID: ");
		result.append(arcID);
		result.append(", arcType: ");
		result.append(arcType);
		result.append(", startNodeID: ");
		result.append(startNodeID);
		result.append(", endNodeID: ");
		result.append(endNodeID);
		result.append(')');
		return result.toString();
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	protected final List getAttributes() {
		final ArrayList attributes = new ArrayList();

		attributes.add(arcType);
		final Integer arcint = new Integer(arcID);
		attributes.add(arcint);
		final Integer snodeint = new Integer(startNodeID);
		attributes.add(snodeint);
		final Integer enodeint = new Integer(endNodeID);
		attributes.add(enodeint);

		return attributes;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	protected final void setAttributes(List attributes) {
		final int nbAtt = 4;
		if (attributes.size() != nbAtt) {
			throw new IllegalArgumentException("Pas le bon nombre d'attributs.");
		} else {
			final Iterator i = attributes.iterator();
			setArcType((String) i.next());
			Integer anint = (Integer) i.next();
			setArcID(anint.intValue());
			anint = (Integer) i.next();
			setStartNodeID(anint.intValue());
			anint = (Integer) i.next();
			setEndNodeID(anint.intValue());
		}
	}

	/** {@inheritDoc} */
	@Override
	public final void accept(CommandVisitor visitor) {
		visitor.visitCa(this);
	}

	/** {@inheritDoc} */
	public final CAMICOMMANDS getCCIdentifier() {
		return ID_CAMICOMMAND;
	}

	@Override
	public String convert2StringNL() {
		return (getCommandAsString().append(NL)).toString();
	}

} // CaImpl
