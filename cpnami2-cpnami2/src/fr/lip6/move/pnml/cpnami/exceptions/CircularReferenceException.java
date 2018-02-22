
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
