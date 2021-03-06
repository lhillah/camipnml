
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
