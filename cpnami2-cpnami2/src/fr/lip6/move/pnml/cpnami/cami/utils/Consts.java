
package fr.lip6.move.pnml.cpnami.cami.utils;
/**
 * Provides widely used constants throughout the application.
 * @author lom
 *
 */
public final class Consts {
	/**
	 * Exit code upon success.
	 */
	public static final int EXIT_SUCCESS = 0;
	/**
	 * Exit code upon failure.
	 */
	public static final int EXIT_FAILURE = -1;
	
	/**
	 * Must not be instantiated.
	 * Prevents instantiation from native class.
	 */
	private Consts() {
		throw new AssertionError();
	}

}
