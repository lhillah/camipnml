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
package fr.lip6.move.pnml.cpnami.cami.impl;

import fr.lip6.move.cpnami.cami.c2p.Cami2PnmlImpl2;
import fr.lip6.move.cpnami.p2c.Pnml2CamiImpl;
import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.Pnml2Cami;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
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
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;
import fr.lip6.move.pnml.cpnami.exceptions.UnhandledCommandException;

/**
 * An implementation of the Cami <b>Factory</b>.
 * 
 */
public final class CamiFactoryImpl implements CamiFactory {

	/**
	 * Retrieves the singleton of the Cami model factory.
	 */
	private static ModelFactory mf = ModelFactory.SINSTANCE;

	/**
	 * The default hidden constructor.
	 */
	private CamiFactoryImpl() {

	}

	/**
	 * Creates the singleton of the Cami Factory.
	 * 
	 */
	private static final class CamiFactorySingletonHolder {
		/**
		 * The singleton holder.
		 */
		private static final CamiFactory camifactoryInstance = new CamiFactoryImpl();

		/**
		 * The default hidden constructor.
		 */
		private CamiFactorySingletonHolder() {

		}
	}

	/**
	 * Returns the singleton instance of Cami Factory.
	 * 
	 * @return the singleton instance of CamiFactory
	 */
	public static CamiFactory getCamiFactoryInstance() {
		return CamiFactorySingletonHolder.camifactoryInstance;
	}

	/**
	 * Returns an instance of {@link Cami2Pnml}.
	 * 
	 * @return an instance of Cami2Pnml
	 */
	public Cami2Pnml createCami2Pnml() {
		return new Cami2PnmlImpl2();
	}

	/**
	 * Returns an instance of {@link Pnml2Cami}.
	 * 
	 * @return an instance of Pnml2Cami
	 */
	public Pnml2Cami createPnml2Cami() {
		return new Pnml2CamiImpl();
	}

	/**
	 * Creates the singleton of the parser.
	 * 
	 */
	private static final class ParserSingletonHolder {
		/**
		 * The singleton holder of the parser.
		 */
		private static final Parser parserInstance = new ParserImpl();

		/**
		 * The default hidden constructor.
		 */
		private ParserSingletonHolder() {
		}
	}

	/**
	 * Returns the singleton instance of {@link Parser}.
	 * 
	 * @return the Parser of Cami commands.
	 */
	public Parser createParser() {
		return ParserSingletonHolder.parserInstance;
	}

	/**
	 * Creer une commande CAMI a partir d'une chaine representant la commande.
	 * 
	 * @param command
	 *            La chaine CAMI de la commande a creer.
	 * @return l'objet commande
	 * @throws UnhandledCommandException
	 *             Cami command not handled
	 * @throws ParseException
	 *             a parse exception
	 * @throws NotCamiCommandException
	 *             the parser does not hold any command.
	 */
	public synchronized Command createACommand(final String command)
			throws UnhandledCommandException, ParseException,
			NotCamiCommandException {
		String identifier = null;
		try {

			final Parser parser = createParser();
			parser.parse(command);
			identifier = parser.getCommand();
			Command result = null;
			final CAMICOMMANDS idCC = CAMICOMMANDS.get(identifier);
			if (idCC != null) {
				switch (idCC.getValue()) {
				case CAMICOMMANDS.DB:
					final Db aDb = mf.createDb();
					aDb.setDb(parser);
					result = aDb;
					break;
				case CAMICOMMANDS.DE:
					final De aDe = mf.createDe();
					aDe.setDe(parser);
					result = aDe;
					break;
				case CAMICOMMANDS.FB:
					final Fb aFb = mf.createFb();
					aFb.setFb(parser);
					result = aFb;
					break;
				case CAMICOMMANDS.FE:
					final Fe aFe = mf.createFe();
					aFe.setFe(parser);
					result = aFe;
					break;
				case CAMICOMMANDS.AS:
					final As aAs = mf.createAs();
					aAs.setAs(parser);
					result = aAs;
					break;
				case CAMICOMMANDS.CN:
					final Cn aCn = mf.createCn();
					aCn.setCn(parser);
					result = aCn;
					break;
				case CAMICOMMANDS.CA:
					final Ca aCa = mf.createCa();
					aCa.setCa(parser);
					result = aCa;
					break;
				case CAMICOMMANDS.CS:
					final Cs aCs = mf.createCs();
					aCs.setCs(parser);
					result = aCs;
					break;
				case CAMICOMMANDS.CT:
					final Ct aCt = mf.createCt();
					aCt.setCt(parser);
					result = aCt;
					break;
				case CAMICOMMANDS.CM:
					final Cm aCm = mf.createCm();
					aCm.setCm(parser);
					result = aCm;
					break;
				case CAMICOMMANDS.PO:
					final Po aPo = mf.createPo();
					aPo.setPo(parser);
					result = aPo;
					break;
				case CAMICOMMANDS.PI:
					final Pi aPi = mf.createPi();
					aPi.setPi(parser);
					result = aPi;
					break;
				case CAMICOMMANDS.PT:
					final Pt aPt = mf.createPt();
					aPt.setPt(parser);
					result = aPt;
					break;
				default:
					throw new UnhandledCommandException(
							"Cami Factory and model to be updated with new command",
							identifier);
				}
			} else if (identifier == null) {
				throw new ParseException("No command identifier found!",
						command);
			} else {
				throw new NotCamiCommandException(
						"Not a Cami command or not handled!", command);
			}
			return result;
		} catch (final ParseException pe) {
			throw pe;
		} catch (final NotCamiCommandException ncce) {
			throw ncce;
		}
	}

	/**
	 * Creates the singleton instance of Runner.
	 */
	private static final class RunnerSingletonHolder {
		/**
		 * the singleton holder of Runner.
		 */
		// private static Runner myRunnerInstance = new RunnerImpl();
		private static final Runner myRunnerInstance = new ConversionDriver();

		/**
		 * The default hidden constructor.
		 */
		private RunnerSingletonHolder() {

		}
	}

	/**
	 * Returns the singleton instance of Runner.
	 * 
	 * @return the singleton instance of Runner.
	 */
	public Runner createRunner() {
		return RunnerSingletonHolder.myRunnerInstance;
	}

} // CamiFactoryImpl
