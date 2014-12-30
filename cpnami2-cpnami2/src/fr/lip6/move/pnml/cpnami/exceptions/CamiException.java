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
package fr.lip6.move.pnml.cpnami.exceptions;

/**
 * Enables Cami Exceptions.
 */

public class CamiException extends Exception {

	/**
	 * . serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * . Launch constructor for CamiException
	 * 
	 */
	public CamiException() {
		super();
	}

	/**
	 * . Launch constructor for CamiException with message
	 * 
	 * @param message
	 *            permit to send message into Cami Exception
	 */
	public CamiException(String message) {
		super(message);
	}

	/**
	 * Contructor with cause and message.
	 * 
	 * @param message
	 *            the message.
	 * @param cause
	 *            the cause.
	 */
	public CamiException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Contructor with cause.
	 * 
	 * @param cause
	 *            the cause
	 */
	public CamiException(Throwable cause) {
		super(cause);
	}

}
