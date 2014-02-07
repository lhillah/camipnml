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
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Fb;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Fb</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class FbImpl extends CommandImpl implements Fb {
	/**
	 * Identifiant de la commande.
	 */
	public final static String identifier = "FB";
	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
	 */
	public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.FB_LITERAL;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FbImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void setFb(Parser parser) {
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
	public void setFb() {
		this.id = identifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public String convert2String() {
		return "FB()";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lip6.cami.impl.CommandImpl#getAttributes()
	 */
	@Override
	protected List getAttributes() {
		ArrayList alist = new ArrayList();
		return alist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lip6.cami.impl.CommandImpl#setAttributes(java.util.List)
	 */
	@Override
	protected void setAttributes(List attributes) {
		if (attributes.size() != 0)
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visitFb(this);
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
		return "FB()\n";
	}
} // FbImpl
