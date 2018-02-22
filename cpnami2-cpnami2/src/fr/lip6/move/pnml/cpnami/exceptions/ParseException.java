
package fr.lip6.move.pnml.cpnami.exceptions;


/**
 * Provides parse exception.
 * 
 */
public class ParseException extends CamiException {
	/**
	 * Serial Version.
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * The string that could not be parsed correctly.
	 */
	private final String faultyString; 
/**
 * Constructor with an error message and the faulty string 
 * that could not be correctly parsed. 
 * @param message the error message.
 * @param faulty the faulty string.
 */
	public ParseException(String message, String faulty) {
		super(message);
		this.faultyString = faulty;
	}
	/**
	 * Constructor with exception message.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ParseException(String message) {
		super(message);
		this.faultyString = "notProvided";
	}

	/**
	 * Default constructor.
	 */
	public ParseException() {
		super();
		this.faultyString = "notProvided";
	}
	/**
	 * Returns the string that could not be parsed
	 * correctly; "notProvided" otherwise.
	 * @return the faulty string if provided; "notProvided" otherwise.
	 */
	public final String getFaultyString() {
		return this.faultyString;
	}
}
