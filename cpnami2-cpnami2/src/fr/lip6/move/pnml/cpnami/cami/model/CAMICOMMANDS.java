
package fr.lip6.move.pnml.cpnami.cami.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * Enum class of known CAMI commands by this application.
 */
public final class CAMICOMMANDS extends AbstractEnumerator {

	/**
	 * Copyright text.
	 */
	public static final String COPYRIGHT = "Copyright (C) 2008 LIP6";
	/**
	 * Commande AS.
	 */
	public static final int AS = 0;
	/**
	 * Commande CA.
	 */
	public static final int CA = 1;
	/**
	 * Commande CM.
	 */
	public static final int CM = 2;
	/**
	 * Commande CN.
	 */
	public static final int CN = 3;
	/**
	 * Commande CS.
	 */
	public static final int CS = 4;
	/**
	 * Commande CT.
	 */
	public static final int CT = 5;
	/**
	 * Commande FB.
	 */
	public static final int DB = 6;
	/**
	 * Commande FB.
	 */
	public static final int DE = 7;
	/**
	 * Commande FB.
	 */
	public static final int FB = 8;
	/**
	 * Commande FE.
	 */
	public static final int FE = 9;
	/**
	 * Commande PI.
	 */
	public static final int PI = 10;
	/**
	 * Commande PO.
	 */
	public static final int PO = 11;
	/**
	 * Commande PT.
	 */
	public static final int PT = 12;
	/**
	 * Commande CB. Not Supported. For test purposes.
	 */
	public static final int CB = 13;
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS AS_LITERAL = new CAMICOMMANDS(AS, "AS");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CA_LITERAL = new CAMICOMMANDS(CA, "CA");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CM_LITERAL = new CAMICOMMANDS(CM, "CM");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CN_LITERAL = new CAMICOMMANDS(CN, "CN");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CS_LITERAL = new CAMICOMMANDS(CS, "CS");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CT_LITERAL = new CAMICOMMANDS(CT, "CT");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS DB_LITERAL = new CAMICOMMANDS(DB, "DB");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS DE_LITERAL = new CAMICOMMANDS(DE, "DE");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS FB_LITERAL = new CAMICOMMANDS(FB, "FB");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS FE_LITERAL = new CAMICOMMANDS(FE, "FE");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS PI_LITERAL = new CAMICOMMANDS(PI, "PI");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS PO_LITERAL = new CAMICOMMANDS(PO, "PO");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS PT_LITERAL = new CAMICOMMANDS(PT, "PT");
	/**
	 * Literal expression of the command.
	 */
	public static final CAMICOMMANDS CB_LITERAL = new CAMICOMMANDS(CB, "CB");
	/**
	 * Literal expression of all commands in a array.
	 */
	public static final CAMICOMMANDS[] VALUES_ARRAY = new CAMICOMMANDS[] {
			AS_LITERAL, CA_LITERAL, CM_LITERAL, CN_LITERAL, CS_LITERAL,
			CT_LITERAL, DB_LITERAL, DE_LITERAL, FB_LITERAL, FE_LITERAL,
			PI_LITERAL, PO_LITERAL, PT_LITERAL, CB_LITERAL, };
	/**
	 * A public read-only list of all the '<em><b>LOGMEDIA</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public static final List<CAMICOMMANDS> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * @param value
	 *            the numeric representation of the Cami command.
	 * @param name
	 *            the name of the command.
	 */
	protected CAMICOMMANDS(int value, String name) {
		super(value, name);
	}

	/**
	 * Returns the '<em><b>CAMICOMMANDS</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name of the command (ex: "As")
	 * @return the enum CAMICOMMANDS of the specified command.
	 */
	public static CAMICOMMANDS get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			final CAMICOMMANDS result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>CAMICOMMANDS</b></em>' literal with the specified
	 * value.
	 * 
	 * @param value
	 *            the numeric value of the command. * @return the enum
	 *            CAMICOMMANDS of the specified command.
	 */
	public static CAMICOMMANDS get(int value) {
		switch (value) {
		case AS:
			return AS_LITERAL;
		case CA:
			return CA_LITERAL;
		case CM:
			return CM_LITERAL;
		case CN:
			return CN_LITERAL;
		case CS:
			return CS_LITERAL;
		case CT:
			return CT_LITERAL;
		case DB:
			return DB_LITERAL;
		case DE:
			return DE_LITERAL;
		case FB:
			return FB_LITERAL;
		case FE:
			return FE_LITERAL;
		case PI:
			return PI_LITERAL;
		case PO:
			return PO_LITERAL;
		case PT:
			return PT_LITERAL;
		case CB:
			return CB_LITERAL;
		default:
		}
		return null;
	}
}
