/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites, Univ. Paris 06 - CNRS UMR 7606 (LIP6)
 *
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
package fr.lip6.move.pnml.cpnami.cami.model.impl;

import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cm;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Cs;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.Db;
import fr.lip6.move.pnml.cpnami.cami.model.De;
import fr.lip6.move.pnml.cpnami.cami.model.Fb;
import fr.lip6.move.pnml.cpnami.cami.model.Fe;
import fr.lip6.move.pnml.cpnami.cami.model.ModelFactory;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;

/**
 * An implementation of the model <b>Factory</b>.
 */
public final class ModelFactoryImpl implements ModelFactory {
	/**
	 * Creates an instance of the factory.
	 */
	private ModelFactoryImpl() {

	}
/**
 * The singleton holder of Model Factory.
 *
 */
	private static final class ModelFactorySingletonHolder {
		/**
		 * The singleton instance of Model Factory.
		 */
		private static final ModelFactory modelFactoryInstance = new ModelFactoryImpl();
		/**
		 * The default hidden constructor.
		 */
		private ModelFactorySingletonHolder() {
		}
	}

	/**
	 * Creates a Db command.
	 * @return a new Db command
	 */
	public Db createDb() {
		final DbImpl db = new DbImpl();
		return db;
	}
	/**
	 * Creates a De command.
	 * @return a new De command
	 */
	public De createDe() {
		final DeImpl de = new DeImpl();
		return de;
	}

	/**
	 * Creates a Fb command.
	 * @return a new Fb command
	 */
	public Fb createFb() {
		final FbImpl fb = new FbImpl();
		return fb;
	}
	/**
	 * Creates a Fe command.
	 * @return a new Fe command
	 */
	public Fe createFe() {
		final FeImpl fe = new FeImpl();
		return fe;
	}

	/**
	 * Creates a Cn command.
	 * @return a new Cn command
	 */
	public Cn createCn() {
		final CnImpl cn = new CnImpl();
		return cn;
	}

	/**
	 * Creates a Ca command.
	 * @return a new Ca command
	 */
	public Ca createCa() {
		final CaImpl ca = new CaImpl();
		return ca;
	}

	/**
	 * Creates a As command.
	 * @return a new As command
	 */
	public As createAs() {
		final AsImpl as = new AsImpl();
		return as;
	}

	/**
	 * Creates a Cs command.
	 * @return a new Cs command
	 */
	public Cs createCs() {
		final CsImpl cs = new CsImpl();
		return cs;
	}

	/**
	 * Creates a Ct command.
	 * @return a new Ct command
	 */
	public Ct createCt() {
		final CtImpl ct = new CtImpl();
		return ct;
	}

	/**
	 * Creates a Cm command.
	 * @return a new Cm command
	 */
	public Cm createCm() {
		final CmImpl cm = new CmImpl();
		return cm;
	}

	/**
	 * Creates a Po command.
	 * @return a new Po command
	 */
	public Po createPo() {
		final PoImpl po = new PoImpl();
		return po;
	}

	/**
	 * Creates a Pi command.
	 * @return a new Pi command
	 */
	public Pi createPi() {
		final PiImpl pi = new PiImpl();
		return pi;
	}

	/**
	 * Creates a Pt command.
	 * @return Pt a new Pt command
	 */
	public Pt createPt() {
		final PtImpl pt = new PtImpl();
		return pt;
	}
/**
 * Returns the singleton instance of Model Factory.
 * @return {@link ModelFactory}
 */
	public static ModelFactory getModelFactoryInstance() {
		return ModelFactorySingletonHolder.modelFactoryInstance;
	}

} // ModelFactoryImpl
