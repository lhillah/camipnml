
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
