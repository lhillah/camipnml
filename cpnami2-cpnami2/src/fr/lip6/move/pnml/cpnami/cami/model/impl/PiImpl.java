/**
 *  Copyright 2009 Sorbonne Universités, Université Pierre & Marie Curie - CNRS UMR 7606 (LIP6/MoVe)
 *  All rights reserved.   This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Initial contributor:
 *    Lom Messan Hillah - <lom-messan.hillah@lip6.fr>
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
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Pi</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PiImpl#getNumObject <em>Num Object</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PiImpl#getH <em>H</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PiImpl#getV <em>V</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PiImpl extends CommandImpl implements Pi {
    /**
     * Identifiant de la commande.
     */
    public final static String identifier = "PI";
    /**
     * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
     */
    public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.PI_LITERAL;

    /**
     * The default value of the '{@link #getNumObject() <em>Num Object</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getNumObject()
     * @generated
     * @ordered
     */
    protected static final int NUM_OBJECT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNumObject() <em>Num Object</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getNumObject()
     * @generated
     * @ordered
     */
    protected int numObject = NUM_OBJECT_EDEFAULT;

    /**
     * The default value of the '{@link #getH() <em>H</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getH()
     * @generated
     * @ordered
     */
    protected static final int H_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getH() <em>H</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getH()
     * @generated
     * @ordered
     */
    protected int h = H_EDEFAULT;

    /**
     * The default value of the '{@link #getV() <em>V</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getV()
     * @generated
     * @ordered
     */
    protected static final int V_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getV() <em>V</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getV()
     * @generated
     * @ordered
     */
    protected int v = V_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected PiImpl() {
	super();
    }



    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getNumObject() {
	return numObject;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setNumObject(int newNumObject) {
	int oldNumObject = numObject;
	numObject = newNumObject;
	
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getH() {
	return h;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setH(int newH) {
	int oldH = h;
	h = newH;
	
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getV() {
	return v;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setV(int newV) {
	int oldV = v;
	v = newV;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     */
    public void setPi(Parser parser) {
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
    public void setPi(int numObject, int h, int v) {
	this.id = identifier;
	setNumObject(numObject);
	setH(h);
	setV(v);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     */
    public String convert2String() {

		return getCommandAsString().toString();
    	
    }




    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {


    	StringBuilder result = new StringBuilder(super.toString());
	result.append(" (numObject: ");
	result.append(numObject);
	result.append(", h: ");
	result.append(h);
	result.append(", v: ");
	result.append(v);
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
	Integer nob = new Integer(numObject);
	attributes.add(nob);
	Integer hi = new Integer(h);
	attributes.add(hi);
	Integer vi = new Integer(v);
	attributes.add(vi);
	return attributes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.lip6.cami.impl.CommandImpl#setAttributes(java.util.List)
     */
    @Override
    protected void setAttributes(List attributes) {
	if (attributes.size() == 5) {
	    Iterator i = attributes.iterator();
	    i.next();
	    Integer nob = (Integer) i.next();
	    setNumObject(nob.intValue());
	    Integer hi = (Integer) i.next();
	    setH(hi.intValue());
	    Integer vi = (Integer) i.next();
	    setV(vi.intValue());
	} else if (attributes.size() == 3) {
	    Iterator i = attributes.iterator();
	    Integer nob = (Integer) i.next();
	    setNumObject(nob.intValue());
	    Integer hi = (Integer) i.next();
	    setH(hi.intValue());
	    Integer vi = (Integer) i.next();
	    setV(vi.intValue());
	} else
	    throw new IllegalArgumentException(
		    "Pas le bon nombre d'attributes.");
    }

    @Override
    public void accept(CommandVisitor visitor) {
	visitor.visitPi(this);
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
	
	private StringBuilder getCommandAsString() {
		StringBuilder result = new StringBuilder();
		result.append(getIdentifier()).append("(").append(numObject)
				.append(",").append(h).append(",").append(v).append(")");
		return result;
	}

} // PiImpl
