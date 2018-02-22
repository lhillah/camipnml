
package fr.lip6.move.pnml.cpnami.exceptions;

/**
 * Provides an Exception when a Cami command cannot not be created with a read
 * string.
 * 
 */
public class NotCamiCommandException extends CamiException {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The faulty string which is not a Cami command.
	 */
	private final String faultyCommand;

	/**
	 * Constructor with a message and the faulty read command.
	 * 
	 * @param message
	 *            the error message.
	 * @param readString
	 *            the faulty read command.
	 */
	public NotCamiCommandException(String message, String readString) {
		super(message);
		this.faultyCommand = readString;
	}

	/**
	 * Constructor with an error message.
	 * 
	 * @param message
	 *            the error message.
	 */
	public NotCamiCommandException(String message) {
		super(message);
		this.faultyCommand = "notProvided";
	}

	/**
	 * Default constructor.
	 */
	public NotCamiCommandException() {
		super();
		faultyCommand = "notProvided";
	}

	/**
	 * Returns the faulty command if provided; "notProvided" otherwise.
	 * 
	 * @return the faulty command; "notProvided" otherwise.
	 */
	public final String getFaultyCommand() {
		return this.faultyCommand;
	}
}
