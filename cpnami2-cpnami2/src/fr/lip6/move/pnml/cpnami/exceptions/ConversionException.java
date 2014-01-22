/**
 *  Copyright 2009 Sorbonne Universités, Université Pierre & Marie Curie - CNRS UMR 7606 (LIP6/MoVe)
 *  All rights reserved.   This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Initial contributor:
 *    Lom Messan Hillah - <lom-messan.hillah@lip6.fr>
 *
 *  Mailing list:
 *    lom-messan.hillah@lip6.fr
 */
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
