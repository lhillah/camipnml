
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Pt</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PtImpl#getNumObject <em>
 * Num Object</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PtImpl#getH <em>H</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PtImpl#getV <em>V</em>}</li>
 * <li>{@link fr.lip6.move.pnml.cpnami.cami.model.impl.PtImpl#getAttributeName
 * <em>Attribute Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PtImpl extends CommandImpl implements Pt {
	/**
	 * Identifiant de la commande.
	 */
	public final static String identifier = "PT";
	/**
	 * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
	 */
	public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.PT_LITERAL;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PtImpl() {
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
	 */
	public void setPt(Parser parser) {
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
	public void setPt(int numObject, String attribute, int h, int v) {
		this.id = identifier;
		if (attribute == null) {
			throw new IllegalArgumentException(
					"Le nom de l'attribut ne doit pas etre null. ");
		}

		setAttributeName(attribute);
		setNumObject(numObject);
		setH(h);
		setV(v);
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
		result.append(", attributeName: ");
		result.append(attributeName);
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
		attributes.add(attributeName);
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
		if (attributes.size() != 4)
			throw new IllegalArgumentException(
					"Pas le bon nombre d'attributes.");
		else {
			Iterator i = attributes.iterator();
			Integer nob = (Integer) i.next();
			setNumObject(nob.intValue());
			setAttributeName((String) i.next());
			Integer hi = (Integer) i.next();
			setH(hi.intValue());
			Integer vi = (Integer) i.next();
			setV(vi.intValue());
		}
	}

	@Override
	public void accept(CommandVisitor visitor) {
		visitor.visitPt(this);
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

	/**
	 * @return
	 */
	private StringBuilder getCommandAsString() {
		StringBuilder result = new StringBuilder();
		result.append(getIdentifier()).append("(").append(numObject)
				.append(",").append(attributeName.length()).append(":")
				.append(attributeName).append(",").append(h).append(",")
				.append(v).append(")");
		return result;
	}

} // PtImpl
