/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites,
 * 							Univ. Paris 06 - CNRS UMR 7606 (LIP6)
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
package fr.lip6.move.pnml.cpnami.general;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;
import fr.lip6.move.pnml.cpnami.cami.Pnml2Cami;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.exceptions.NotCamiCommandException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;
import fr.lip6.move.pnml.cpnami.exceptions.UnhandledCommandException;

/**
 * Tests for Cami Factory.
 * 
 */
public class CamiFactoryTest {
/**
 * InitialiZation.
 */
	@BeforeTest(groups = { "camifactory" })
	protected final void giveInfoBefore() {
		System.out.println("Testing Cami Factory...");

	}

	/**
	 * Data provider of Cami commands as strings. Fake or null commands.
	 * 
	 * @return Table of Strings which are supposed to be Cami commands.
	 */
	@DataProvider(name = "giveCamiBogusCommands")
	public final Object[][] createDataCamiBogusCommands() {
		return new Object[][] { { "" }, { "CX(7:marking,151,1:1)" },
				{ "CB(14:multiprocessor,5,-2)" },
				{ "PTD(176,4:name,688,394)" },

		};
	}

	/**
	 * Data provider of Cami commands as strings. Valid commands.
	 * 
	 * @return Table of Strings which are supposed to be Cami commands.
	 */
	@DataProvider(name = "giveCamiCommands")
	public final Object[][] createDataCamiCommands() {
		return new Object[][] {
				{ "CN(5:place,154)" },
				{ "CN(10:transition,203)" },
				{ "PO(166,335,214)" },
				{ "CT(4:name,208,18:TAKE_LEFT_1_FORK_1)" },
				{ "CA(3:arc,252,189,178)" },
				{ "PT(204,4:name,333,263)" },
				{ "FB()" },
				{ "DB()" },
				{ "CM(5:guard,442,1,-181,48:[((state_ego <> state_peer and cmd_new = nop) or)" },
				{ "DB()" }, { "DE()" }, { "FE()" }, { "PI(-1,48,57,72,-1)" }, };
	}

	/**
	 * Singleton of Cami Factory.
	 */
	@Test(groups = { "camifactory" })
	public final void testFactorySingleton() {
		final CamiFactory sing1 = CamiFactory.SINSTANCE;
		final CamiFactory sing2 = CamiFactory.SINSTANCE;
		Assert
				.assertEquals(sing1, sing2,
						"Any two references of Cami factory must refer to the same object!");
	}

	/**
	 * Cami2Pnml not null.
	 */
	@Test(groups = { "camifactory" })
	public final void testCami2PnmlNotNull() {
		final Cami2Pnml c2p = CamiFactory.SINSTANCE.createCami2Pnml();
		Assert.assertNotNull(c2p,
				"Any instance of Cami2Pnml should not be null!");
	}

	/**
	 * Cami2Pnml different instances.
	 */
	@Test(groups = { "camifactory" })
	public final void testCami2PnmlInstances() {
		final Cami2Pnml c2p1 = CamiFactory.SINSTANCE.createCami2Pnml();
		final Cami2Pnml c2p2 = CamiFactory.SINSTANCE.createCami2Pnml();
		Assert
				.assertNotSame(c2p1, c2p2,
						"Two any references of Cami2Pnml should not refer to the same instance!");
	}

	/**
	 * Pnml2Cami not null.
	 */
	@Test(groups = { "camifactory" })
	public final void testPnml2CamiNotNull() {
		final Pnml2Cami p2c = CamiFactory.SINSTANCE.createPnml2Cami();
		Assert.assertNotNull(p2c,
				"Any instance of Pnml2Cami should not be null!");
	}

	/**
	 * Pnml2Cami different instances.
	 */
	@Test(groups = { "camifactory" })
	public final void testPnml2CamiInstances() {
		final Pnml2Cami p2c1 = CamiFactory.SINSTANCE.createPnml2Cami();
		final Pnml2Cami p2c2 = CamiFactory.SINSTANCE.createPnml2Cami();
		Assert
				.assertNotSame(p2c1, p2c2,
						"Two any references of Pnml2Cami should not refer to the same instance!");
	}

	/**
	 * Singleton instance of Cami Parser.
	 */
	@Test(groups = { "camifactory" })
	public final void testParserSingleton() {
		final Parser p1 = CamiFactory.SINSTANCE.createParser();
		final Parser p2 = CamiFactory.SINSTANCE.createParser();
		Assert
				.assertEquals(p1, p2,
						"Any two references of Cami parser must refer to the same object!");
	}

	/**
	 * Singleton instance of Runner.
	 */
	@Test(groups = { "camifactory" })
	public final void testRunnerSingleton() {
		final Runner r1 = CamiFactory.SINSTANCE.createRunner();
		final Runner r2 = CamiFactory.SINSTANCE.createRunner();
		Assert
				.assertEquals(r1, r2,
						"Any two references of Cami runner must refer to the same object!");
	}

	/**
	 * Cami bogus commands.
	 * 
	 * @param command a Cami command.
	 * @throws UnhandledCommandException command not yet handled.
	 * @throws ParseException parse exception.
	 * @throws NotCamiCommandException not recognized as a Cami command.
	 */
	@Test(groups = { "camifactory" }, dataProvider = "giveCamiBogusCommands", expectedExceptions = {
			UnhandledCommandException.class, ParseException.class,
			NotCamiCommandException.class })
	public final void testBogusCommandCreation(String command)
			throws UnhandledCommandException, ParseException,
			NotCamiCommandException {
		 CamiFactory.SINSTANCE.createACommand(command);
		
	}

	/**
	 * Correct Cami commands. All that are currently supported in this
	 * application.
	 * 
	 * @param command a Cami command.
	 * @throws UnhandledCommandException command not yet handled.
	 * @throws ParseException command not yet handled.
	 * @throws NotCamiCommandException not recognized as a Cami command.
	 */
	@Test(groups = { "camifactory" }, dataProvider = "giveCamiCommands")
	public final void testCommandCreation(String command)
			throws UnhandledCommandException, ParseException,
			NotCamiCommandException {
		final Command cmd = CamiFactory.SINSTANCE.createACommand(command);
		Assert.assertNotNull(cmd,
				"Any corrctly created command should not be null!!");
	}
}
