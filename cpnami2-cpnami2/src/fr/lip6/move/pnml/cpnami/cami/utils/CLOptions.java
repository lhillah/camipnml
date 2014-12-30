/**
 *  Copyright 2009-2015 Universite Paris Ouest and Sorbonne Universites,
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
package fr.lip6.move.pnml.cpnami.cami.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Command-line options I am expecting, so that I can implement a strategy
 * between the command-line execution mode and the server one.
 * @author lom
 */
public class CLOptions {

	/**
	 * Conversion of Cami to PNML mode.
	 */
	private boolean cami2p;
	/**
	 * Conversion of PNML to Cami mode.
	 */
	private boolean p2cami;
	/**
	 * Help option.
	 */
	private boolean help;

	/**
	 * Output result on stdout.
	 */
	private boolean stdout;
	/**
	 * Is output file path set?
	 */
	private boolean outputSet;
	/**
	 * Export as a Core Model.
	 */
	private boolean toCoreModel;
	/**
	 * Export a HLPNG. PT will be exported as PT-HLPNG and SN will be exported
	 * as HLPNG.
	 */
	private boolean toHighLevel;
	/**
	 * A unique output file specified by the user.
	 */
	private File outputFile;

	/**
	 * Other arguments than options.
	 */
	@Argument
	private List<String> arguments = new ArrayList<String>();

	/**
	 * Export as a PNML Core Model.
	 * @param cm export a Core Model option.
	 */
	@Option(name = "-tocm", aliases = { "--toCoremodel" }, required = false, usage = "Export as a Core Model.\n"
			+ "This a kind of fall-back mechanism. The Cami Petri net model will be exported as a PNML Core Model.")
	private void setCoreModel(boolean cm) {
		toCoreModel = cm;
	}

	/**
	 * Tells if the export as Core model was asked.
	 * @return true if so
	 */
	public final boolean isToCoreModel() {
		return toCoreModel;
	}

	/**
	 * Export as a HLPN graph.
	 * @param tohlpn export a HLPN graph option.
	 */
	@Option(name = "-tohlpn", aliases = { "--toHighLevelPN" }, required = false, usage = "Export as a High-Level PN.\n"
			+ "The Cami Symmetric net model will be exported as a High-Level PN, and the Cami PT net will be exported as a PT-HLPNG.")
	private void setHighLevel(boolean tohlpn) {
		toHighLevel = tohlpn;
	}

	/**
	 * Tells if export to HLPN was asked.
	 * @return true if so.
	 */
	public final boolean istoHLPN() {
		return toHighLevel;
	}

	/**
	 * @param ofile the output file path
	 */
	@Option(name = "-o", aliases = { "--output" }, metaVar = "FILE", required = false, usage = "Use this option to specify a unique output file. Not required.\n"
			+ "By default, each output file name will be deduced from the corresponding input file name.")
	private void setOutput(String ofile) {
		outputFile = new File(ofile);
		outputSet = true;
	}

	/**
	 * Help option processing.
	 * @param h the help option.
	 */
	@Option(name = "-h", aliases = { "--help" }, usage = "Prints this help")
	private void setHelp(boolean h) {
		help = h;
	}

	/**
	 * Is help solicited?
	 * @return true if so.
	 */
	public final boolean isHelp() {
		return help;
	}

	/**
	 * Returns output file location.
	 * @return output file location.
	 */
	public final File getOutput() {
		return outputFile;
	}

	/**
	 * Cami to PNML conversion.
	 * @param c2p the Cami to PNML execution mode set to c2p.
	 */
	@Option(name = "-c2p", aliases = { "--cami2pnml", "-cami2p" }, usage = "Conversion of Cami to PNML.\n" + "You must provide paths to files to convert.")
	private void setC2Pnml(boolean c2p) {
		cami2p = c2p;
	}

	/**
	 * Is cami2p conversion solicited?
	 * @return true if so.
	 */
	public final boolean isCami2P() {
		return cami2p;
	}

	/**
	 * Conversion of PNML to Cami.
	 * @param p2c the PNML to cami conversion mode is set to s.
	 */
	@Option(name = "-p2c", aliases = { "--pnml2cami", "-p2cami" }, usage = "Conversion of PNML 2 Cami\n"
			+ "Automatically disables Cami2P, to prevent two exclusive options to be set at the same time.\n" + "You must provide paths to files to convert.")
	private void setP2Cami(boolean p2c) {
		cami2p = false;
		p2cami = p2c;
	}

	/**
	 * Is PNML to Cami conversion solicited?
	 * @return true is so
	 */
	public final boolean isPnml2C() {
		return p2cami;
	}

	/**
	 * Print result on stdout.
	 * @param std stdout
	 */
	@Option(name = "-stdout", usage = "Output the result onto stdout instead of a file. Not required.")
	private void setStdout(boolean std) {
		stdout = std;
	}

	/**
	 * Tells if user asked for result to be printed on stdout.
	 * @return true if so
	 */
	public final boolean isStdout() {
		return stdout;
	}

	/**
	 * Is output file path specified?
	 * @return true if so.
	 */
	public final boolean isOutput() {
		return outputSet;
	}

	/**
	 * List of arguments other that options.
	 * @return list of arguments.
	 */
	public final List<String> getArguments() {
		return arguments;
	}

}
