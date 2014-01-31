/**
 *  Copyright 2009 Universite Paris Ouest Nanterre & Sorbonne Universites, Univ. Paris 06 - CNRS UMR 7606 (LIP6/MoVe)
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
