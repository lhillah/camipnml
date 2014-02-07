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
package fr.lip6.move.pnml.cpnami.cami.impl;

import java.util.Iterator;
import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.exceptions.ConversionException;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Command</b></em>'. This abstract class represent the concept of cami
 * command, therefore it can never be instantiated. It is extended and
 * implemented by all known concrete cami commands, such as Cn or Pt. <!--
 * end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public abstract class CommandImpl implements Command {

	/**
	 * Taille maximale d'une commande CAMI.
	 */
	public static final int MAXCOMMANDSIZE = 256;
	/**
	 * Caractere d'ouverture d'une liste d'attributs.
	 */
	public static final String OPEN_ATTRIBUTES = "(";

	/**
	 * Caractere de fermeture d'une liste d'attributs.
	 */
	public static final String CLOSE_ATTRIBUTES = ")";

	/**
	 * Caractere de separation entre deux attributs dans une liste d'attributs.
	 */
	public static final String ATTRIBUTES_SEPARATOR = ",";

	/**
	 * Caractere de separation entre la taille et le contenu d'une chaine.
	 */
	public static final String STRING_ATTRIBUTE_SEPARATOR = ":";
	
	protected static final String NL = "\n";

	/**
	 * Identifiant de la commande.
	 */
	protected String id;

	/**
	 * Constructeur.
	 */
	protected CommandImpl() {
		super();
	}

	/**
	 * Returns the command identifier. Example : CN, CA, CT, DB, etc.
	 * 
	 * @return the identifier of this command.
	 */
	public final String getIdentifier() {
		return this.id;
	}

	/**
	 * Serializes this Cami command object into a string representation.
	 * 
	 * @return the string serialization of the Cami command.
	 * @throws ConversionException
	 *             conversion exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public final String toCamiString() throws ConversionException {
		String result = this.getIdentifier();
		final List attributes = this.getAttributes();
		boolean first = true;
		result += OPEN_ATTRIBUTES;
		for (final Iterator e = attributes.iterator(); e.hasNext();) {
			final Object value = e.next();
			if (first) {
				first = false;
			} else {
				result += ATTRIBUTES_SEPARATOR;
			}
			if (value instanceof String) {
				result += Integer.toString(((String) value).length());
				result += STRING_ATTRIBUTE_SEPARATOR;
				result += (String) value; // Check ASCII ?
			} else if (value instanceof Integer) {
				result += ((Integer) value).toString();
			} else if (value == null) {
				System.err.println("Command#toCamiString: the value of an attribute of a "
						+ this.getIdentifier() + " command was not defined.");
			} else {
				throw new ConversionException("Command#toCamiString: undefined type of attribute");
			}
		}
		result += CLOSE_ATTRIBUTES;
		return result;

	}

	/**
	 * Sets this Cami command object with id and
	 * attributes taken from the argument. 
	 * The argument is a parsed Cami command hold in a Parser.
	 * @param parser the parser
	 * @throws NotCamiCommandException the parser does not hold any command.
	 */
	public final void setCommand(Parser parser) throws NotCamiCommandException {
		try {
			this.id = parser.getCommand();
			this.setAttributes(parser.getAttributes());
		} catch (NotCamiCommandException ncce) {
			throw ncce;
		}
	}

	/**
	 * Obtenir les attributs de la commande.
	 * 
	 * Cette methode doit retourner une liste des attributs de la commande, dans
	 * l'ordre dans lequel ils doivent apparaitre dans la representation
	 * textuelle de la commande.
	 * 
	 * @return La liste des attributs de la commande.
	 */
	@SuppressWarnings("unchecked")
	protected abstract List getAttributes();

	/**
	 * Mettre ajour les attributs d'une commande.
	 * 
	 * Cette methode doit utiliser la liste fournie pour mettre a jour les
	 * attributs de la commande, dans l'ordre ou les attributs sont dans la
	 * representation textuelle de la commande.
	 * 
	 * @param attributes
	 *            La liste des attributs a mettre a jour.
	 */
	@SuppressWarnings("unchecked")
	protected abstract void setAttributes(List attributes);

	/**
	 * Visitor pattern.
	 * @param visitor
	 *            the visitor.
	 */
	public abstract void accept(CommandVisitor visitor);

} // CommandImpl
