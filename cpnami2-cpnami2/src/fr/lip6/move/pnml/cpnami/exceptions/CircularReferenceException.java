/**
 *  Copyright 2009-2016 Universite Paris Ouest and Sorbonne Universites,
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
package fr.lip6.move.pnml.cpnami.exceptions;


/**
 * Exception for circular references when resolving
 * Pnml Refnodes references.
 */
public class CircularReferenceException extends CamiException {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception raised when a circular reference is encountered,
	 * when resolving Pnml RefNodes references.
	 * 
	 * @param message
	 *            the error message.
	 */
	public CircularReferenceException(String message) {
		super(message);
	}

	/**
	 * Exception raised when a circular reference is encountered,
	 * when resolving Pnml RefNodes references.
	 */
	public CircularReferenceException() {
		super();
	}

}
