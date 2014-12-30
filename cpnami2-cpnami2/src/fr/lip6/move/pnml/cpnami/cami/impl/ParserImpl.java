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
package fr.lip6.move.pnml.cpnami.cami.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;

/**
 * An implementation of the model object '<em><b>Parser</b></em>'.
 */
public class ParserImpl implements Parser {
	/**
	 * Expression reguliere pour extraire la commande et les attributs d'une
	 * chaine CAMI.
	 */
	private static final Pattern COMMAND_PATTERN = Pattern
			.compile("^(\\p{Upper}+)\\((.*)\\)\\s?");

	/**
	 * Expression reguliere pour extraire le premier attribut d'une chaine
	 * d'attributs.
	 */
	private static final Pattern FIRST_ATTRIBUTE_PATTERN = Pattern
			.compile("^((\\d+):|((\\-)?\\d+)|())(.*)$");

	/**
	 * Expression reguliere pour extraire les attributs suivants.
	 */
	private static final Pattern OTHER_ATTRIBUTE_PATTERN = Pattern
			.compile("^,((\\d+):|((\\-)?\\d+)|())(.*)$");

	/**
	 * Commande extraite de la chaine CAMI.
	 */
	private String command;

	/**
	 * Attributs extraits de la chaine CAMI.
	 */
	@SuppressWarnings("rawtypes")
	private List attributes;

	/**
	 * Constructor.
	 * 
	 */
	protected ParserImpl() {
		super();
	}

	/**
	 * Returns the string representation of the current Cami command hold by the
	 * parser.
	 * 
	 * @return the Cami command as a string.
	 * @throws NotCamiCommandException
	 *             not Cami exception
	 */
	public final String getCommand() throws NotCamiCommandException {
		if (this.command != null) {
			return this.command;
		} else {
			throw new NotCamiCommandException();
		}
	}

	/**
	 * @return the list of attributes of the Cami command hold by the parser.
	 * @throws NotCamiCommandException
	 *             not Cami exception.
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final List getAttributes() throws NotCamiCommandException {
		if (this.attributes != null) {
			return this.attributes;
		} else {
			throw new NotCamiCommandException("Attributes List is null");
		}
	}

	/**
	 * Parses a Cami command string to construct the corresponding in-memory
	 * command object.
	 * 
	 * @param toParse
	 *            the string to parse.
	 * @throws ParseException
	 *             parse exception.
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void parse(String toParse) throws ParseException {
		final Matcher commandMatch = ParserImpl.COMMAND_PATTERN
				.matcher(toParse);
		final int deux = 2;
		final int trois = 3;
		final int cinq = 5;
		final int six = 6;
		if (!commandMatch.find()) {
			throw new ParseException("No match found for Cami command!",
					toParse);
		} else {
			this.command = commandMatch.group(1);
			String s = commandMatch.group(2);
			boolean first = true;
			// Construction de la liste des attributs :
			this.attributes = new ArrayList();
			// System.err.println(" S="+s);
			Matcher attributeMatch = ParserImpl.FIRST_ATTRIBUTE_PATTERN
					.matcher(s);
			Pattern stringPattern;
			Matcher stringMatch = null;
			Integer length;
			String sa;
			String ia;
			String ea;
			while (!s.equals("")) {
				if (attributeMatch.find()) {
					sa = attributeMatch.group(deux);
					ia = attributeMatch.group(trois);
					ea = attributeMatch.group(cinq);
					s = attributeMatch.group(six);
					if (sa != null) {
						// Attribut de type "chaine" :
						length = Integer.valueOf(Integer.parseInt(sa));
						// System.err.println(" length="+length.toString());
						stringPattern = Pattern.compile("^(.{"
								+ length.toString() + "})(.*)$");
						// System.err.println(stringPattern.toString());
						stringMatch = stringPattern.matcher(s);
						/*if (stringMatch == null) {
							stringMatch = stringPattern.matcher(s);
						} else {
							stringMatch.reset().usePattern(stringPattern);
						}*/
						if (stringMatch.find()) {
							// System.err.println("Trouve");
							this.attributes.add(stringMatch.group(1));
							s = stringMatch.group(2);
						} else {
							throw new ParseException(
									"First attribute not matched!", toParse);
						}
					} else if (ia != null) {
						// Attribut de type "entier" :
						this.attributes.add(Integer.valueOf(Integer
								.parseInt(ia)));
					} else if (ea != null) {
						this.attributes.add(null);
					} else {
						throw new ParseException();
					}
					attributeMatch = ParserImpl.OTHER_ATTRIBUTE_PATTERN
							.matcher(s);
				} else {
					throw new ParseException("Other attributes not matched!",
							toParse);
				}
				if (first) {
					attributeMatch = ParserImpl.OTHER_ATTRIBUTE_PATTERN
							.matcher(s);
					first = false;
				}
			}
		}

		// System.err.println("Parser: "+this.command+" was parsed");
	}

} // ParserImpl
