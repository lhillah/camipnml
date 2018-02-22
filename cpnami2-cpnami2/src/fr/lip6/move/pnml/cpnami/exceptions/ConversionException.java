
package fr.lip6.move.pnml.cpnami.exceptions;
/**
 * Thrown exception when a CAMI <-> String is encountered.
 */
public class ConversionException extends CamiException {
		/**
		 * Serial Version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor of a conversion exception with 
		 * the error message.
		 * 
		 * @param message
		 *            Error message
		 */
		public ConversionException(String message) {
			super(message);
		}

		/**
		 * Default constructor of a conversion exception.
		 */
		public ConversionException() {
			super();
		}
}
