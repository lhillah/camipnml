/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites,
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
 * Provides exit status.
 * 
 * @author lom
 * 
 */
public class ExitException extends CamiException {

	/**
	 * Serial Version.
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * Exit code.
	 */
	private final int status;

	/**
	 * Constructor with exit code.
	 * 
	 * @param theStatus
	 *            the exit code.
	 */
	public ExitException(final int theStatus) {
		super("Application has ended execution.");
		this.status = theStatus;
	}

	/**
	 * Constructor with a message and an exit code.
	 * 
	 * @param theStatus
	 *            exit code.
	 * @param message
	 *            the exception message
	 */
	public ExitException(final int theStatus, final String message) {
		super(message);
		this.status = theStatus;
	}

	/**
	 * Returns the exit code.
	 * 
	 * @return the exit code.
	 */
	public final int getStatus() {
		return this.status;
	}
}
