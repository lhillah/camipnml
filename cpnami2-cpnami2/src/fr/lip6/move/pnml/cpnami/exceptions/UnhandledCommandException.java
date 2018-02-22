
package fr.lip6.move.pnml.cpnami.exceptions;
/**
 * Provides an exception for unhandled Cami commands.
 * @author lom
 *
 */
public class UnhandledCommandException extends CamiException {
	/**
	 * Serial Version.
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * The unhandled commmand identifier.
	 */
	private final String commandIdentifier;
/**
 * Construct an exception for unhandled command with an error
 * message and the identifier of the command.
 * @param message error message
 * @param identifier the unhandled command identifier.
 */
	public UnhandledCommandException(String message, String identifier) {
		super(message);
		this.commandIdentifier = identifier;
	}
/**
 * Construct an exception for unhandled command with an error
 * message.
 * @param message the error message.
 */
	public UnhandledCommandException(String message) {
		super(message);
		this.commandIdentifier = "notProvided";
	}
/**
 * Default constructor.
 */
	public UnhandledCommandException() {
		super();
		this.commandIdentifier = "notProvided";
	}
/**
 * Returns the identifer of the unhandled command if provided, 
 * "notProvided" otherwise.
 * @return the identifer of the unhandled command if provided,
 * "notProvided" otherwise.
 */
	public final String getCommandIdentifier() {
		return this.commandIdentifier;
	}
}
