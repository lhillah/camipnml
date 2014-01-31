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
package fr.lip6.move.pnml.cpnami.general;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fr.lip6.move.pnml.cpnami.cami.CpnAmiMain;
import fr.lip6.move.pnml.cpnami.cami.utils.Consts;
import fr.lip6.move.pnml.cpnami.exceptions.ExitException;

/**
 * Oracle which drives Cami to Pnml and Pnml To Cami transformations.
 * 
 */
public class Oracle {
	/**
	 * Root path to Cami and Pnml files.
	 */
	private static final String RESOURCES = "/resources";
	/**
	 * Cami to Pnml option.
	 */
	private static final String CAMI2P = "-c2p";
	/**
	 * �Pnml to Cami option.
	 */
	private static final String P2CAMI = "-p2c";
	/**
	 * Root path to Cami and Pnml models.
	 */
	private static String basePath = "";
	/**
	 * Path to Cami model.
	 */
	private static String camiPath = "";
	/**
	 * Path to Pnml models.
	 */
	private static String pnmlPath = "";

	/**
	 * Before all tests.
	 */
	@BeforeTest(groups = { "Oracle" })
	protected final void giveinfo() {
		//basePath = new File("").getAbsolutePath().split("/trunk")[0] + "/trunk/cpnami2" + RESOURCES;
		basePath = new File("").getAbsolutePath().split(RESOURCES)[0]  + RESOURCES;
		camiPath = basePath + "/camiModels";
		pnmlPath = basePath + "/pnmlModels";
		System.out.println("the root folder is " + new File("").getAbsolutePath());
		System.out.println("full path to resources is " + basePath);
		System.out.println("path to camiModels " + camiPath);
		System.out.println("path to pnmlModels " + pnmlPath);

	}

	/**
	 * Before test method to export Cami to Pnml.
	 * 
	 * @throws java.lang.Exception
	 *             an exception
	 */
	@BeforeMethod(groups = { "OracleCami2Pnml" })
	protected final void setCamiExportUp() throws Exception {
		System.out.println("Export Cami to Pnml tests...");

	}

	/**
	 * Before test method to import Pnml to Cami.
	 * 
	 * @throws java.lang.Exception
	 *             an exception
	 */
	@BeforeMethod(groups = { "OraclePnml2Cami" })
	protected final void setPnmlImportUp() throws Exception {
		System.out.println("Import Pnml to Cami tests...");

	}

	/**
	 * Data provider for Cami files.
	 * 
	 * @return Table of tables of Strings which are the command-line arguments.
	 */
	@DataProvider(name = "giveCamifiles")
	public final Object[][] createDataCami() {
		return new Object[][] { { CAMI2P, camiPath + "/philo_10p4-ord.cami" },
				//{ CAMI2P, camiPath + "/Piscine.cami" }, 
				//{ CAMI2P, camiPath + "/Token-ring.cami", },
				//{ CAMI2P, camiPath + "/airplane-landing-detector.cami", },
				};
	}

	/**
	 * Data provider for Pnml files.
	 * 
	 * @return Table of tables of Strings which are the command-line arguments.
	 */
	@DataProvider(name = "givePnmlfiles")
	public final Object[][] createDataPnml() {
		return new Object[][] { { P2CAMI, pnmlPath + "/philo_10p4-ord.pnml" },
				//{ P2CAMI, pnmlPath + "/Piscine.pnml" },
				//{ P2CAMI, pnmlPath + "/Token-ring.pnml", },
				//{ P2CAMI, pnmlPath + "/airplane-landing-detector.pnml", },
				};
	}

	/**
	 * Test of Cami to Pnml.
	 * 
	 * @param conversionKind
	 *            Cami to Pnml.
	 * @param camiFile
	 *            Cami file path
	 */
	@Test(groups = { "OracleCami2Pnml" }, dataProvider = "giveCamifiles")
	public final void testCamiExport(String conversionKind, String camiFile) {
		try {
			final String[] args = { conversionKind, camiFile, };
			CpnAmiMain.main(args);
		} catch (ExitException ee) {
			Assert.assertEquals(ee.getStatus(), Consts.EXIT_SUCCESS, "Exit status should be 0!");
		}

	}

	/**
	 * Test of Pnml to Cami.
	 * 
	 * @param conversionKind
	 *            Pnml to Cami
	 * @param pnmlFile
	 *            PNML file path
	 */
	@Test(groups = { "OraclePnml2Cami" }, dataProvider = "givePnmlfiles")
	public final void testPnmlImport(String conversionKind, String pnmlFile) {
		try {
			final String[] args = { conversionKind, pnmlFile, };
			CpnAmiMain.main(args);
		} catch (ExitException ee) {
			Assert.assertEquals(ee.getStatus(), Consts.EXIT_SUCCESS, "Exit status should be 0!");
		}

	}
}
