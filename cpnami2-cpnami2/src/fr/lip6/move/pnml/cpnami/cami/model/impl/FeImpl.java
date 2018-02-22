
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.impl.CommandImpl;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Fe;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Fb</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class FeImpl extends CommandImpl implements Fe {
    /**
     * Identifiant de la commande.
     */
    public final static String identifier = "FE";
    /**
     * Identifiant de la commande sous forme d'une valeur de l'enum CAMICOMMANDS
     */
    public final static CAMICOMMANDS idCAMICOMMAND = CAMICOMMANDS.FE_LITERAL;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected FeImpl() {
	super();
    }



    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     */
    public void setFe(Parser parser) {
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
    public void setFe() {
	this.id = identifier;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     */
    public String convert2String() {
	return "FE()";
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
	visitor.visitFe(this);
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
		return "FE()\n";
	}

} // FbImpl
