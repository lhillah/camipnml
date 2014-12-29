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
package fr.lip6.move.pnml.cpnami.cami.impl;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.Pnml2Cami;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

/**
 * Implements the Runner interface, which is the entry point to run this
 * application to convert CAMI models into PNML and vice versa.
 */
public class RunnerImpl implements Runner {
	/**
	 * copyright.
	 */
	public static final String COPYRIGHT = "Copyright (C) 2009-2014 LIP6";
	/**
	 * Retrieves the journal this application, whose name is monJournal.
	 */
	private static final Log JOURNAL = LogMaster.giveLogger("monJournal");
	/**
	 * The constant expressing the cami to pnml option.
	 */
	private static final String CAMI2P_OPT = "cami2p";
	/**
	 * The constant expressing the pnml to cami option.
	 */
	private static final String P2CAMI_OPT = "p2cami";
	/**
	 * The constant expressing the output option.
	 */
	private static final String OUTPUT_OPT = "output";
	/**
	 * The constant expressing the stdout option.
	 */
	private static final String STDOUT_OPT = "stdout";
	
	/**
	 * The prefix constant for creating PNML ids.
	 */
	private static final String CID = "cId";
	/**
	 * The constant holding the big help text.
	 */
	private static StringBuffer bighelp;
	/**
	 * The constant holding the small help text.
	 */
	private static StringBuffer smallhelp;
	/**
	 * Retrieves Cami factory singleton.
	 */
	private static CamiFactory caf = CamiFactory.SINSTANCE;
	/**
	 * Conversion option asked for in the command line.
	 */
	private String conversionKind;
	/**
	 * Did the user specified a conversion option?
	 */
	private boolean isCkPresent;
	/**
	 * Unrecognized conversion option?
	 */
	private boolean isUnrecognized;
	/**
	 * Rank of the conversion option in the command line.
	 */
	private int ckrank = -1;
	/**
	 * What is the rank on the command line of the output option?
	 */
	private int outputRank = -1;
	/**
	 * Has the user specified the output option?
	 */
	private boolean isOutputPresent;
	/**
	 * Has the user specified the stdout option?
	 */
	private boolean isStdoutPresent;
	/**
	 * Has the user asked for help?
	 */
	private boolean isHelpAskedFor;
	/**
	 * The list of input files.
	 */
	private List<String> filesIn = new ArrayList<String>();
	/**
	 * The list of output files.
	 */
	private List<String> filesOut = new ArrayList<String>();
	/**
	 * To avoid final string concatenations
	 */
	private StringBuffer buffer;

	/**
	 * <!-- begin-user-doc --> The singleton instance of this class must be
	 * created through CamiFactory. <!-- end-user-doc -->
	 * 
	 */
	protected RunnerImpl() {
		super();
		this.buffer = new StringBuffer();
	}

	// TODO: Les listes dans les arguments de cami2pnml et pnml2cami
	// servent a prevoir une extension de la ligne de commande
	// lorsque l'utilisateur voudra par exemple mettre un ensemble de modeles
	// CAMI dans le meme fichier PNML.
	/**
	 * Processes command line arguments and run the application.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws CamiException
	 *             Cami exception
	 */
	public final void run(String[] args) throws CamiException {

		try {
			// Processes command-line arguments and gives usage information if
			// necessary
			processArguments(args);
			if (conversionKind.equalsIgnoreCase(CAMI2P_OPT)) {
				final Cami2Pnml c2p = caf.createCami2Pnml();
				if (isStdout()) {
					c2p.cami2Pnml(filesIn, null);
				} else {
					c2p.cami2Pnml(filesIn, filesOut);
				}
				JOURNAL.info("Runner: CAMI model(s) exported  into PNML. ");

			} else if (conversionKind.equalsIgnoreCase(P2CAMI_OPT)) {
				final Pnml2Cami p2c = caf.createPnml2Cami();
				if (isStdout()) {
					p2c.pnml2Cami(filesIn, null);
				} else {
					System.out.println("Exporting into cami...");
					p2c.pnml2Cami(filesIn, filesOut);
				}
				System.err.print("Runner: PNML model(s) exported  into CAMI. ");
			}
			System.err.print("See: ");
			if (isStdout()) {
				System.err.println("Standard out.");
			} else {
				for (final Iterator<String> it = filesOut.iterator(); it.hasNext();) {
					System.err.println((String) it.next());
				}
			}
		} catch (CamiException ce) {
			ce.printStackTrace();
			if (isHelpAskedFor) {
				System.err.println(ce.getMessage());
			} else {
				final String runnerMessage = "Runner: An error occured; ";
				throw new CamiException(runnerMessage + ce.getMessage());
			}
		}
	}

	/**
	 * Processes the arguments.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws CamiException
	 *             Cami exception
	 */
	private void processArguments(String[] args) throws CamiException {
		initHelp();
		if (isHelp(args)) {
			printBigHelp();
			throw new CamiException("End of help message.");
		}
		if (args.length < 2) {
			printBigHelp();
			throw new CamiException("Unsufficient number of arguments.");
		}
		processConversionOption(args);
		processOutputOption(args);
		processStdoutOption(args);
		if (args.length == 2) {
			// conversionKind and stdout or output specified but no input file
			if (isConversionOption() && isStdout()) {
				printSmallHelp();
				throw new CamiException("No input file specified.");
			} else if (isConversionOption() && isOutput()) {
				printSmallHelp();
				throw new CamiException("No input file specified.");
			} else if (!isConversionOption() && !isUnrecognizedOption()) {
				printSmallHelp();
				throw new CamiException("Conversion option not specified.");
			} else if (isUnrecognizedOption()) {
				printSmallHelp();
				throw new CamiException(
						"Exception: unrecognized conversion option.");
			}
			/*
			 * Here, at least conversion kind and an input file name must have
			 * been given. No stdout. The output file will have same name as the
			 * input file.
			 */
			if (isConversionOption()) {
				// Gets the input file. Checks if file name is specified before
				// option of conversion.
				String filein = args[0].toLowerCase().endsWith(conversionKind) ? null
						: args[0];
				final String fileout = (filein != null) ? filein : args[1];
				if (filein == null) {
					filein = fileout;
				}
				final String newFileOut = formatOutFileName(fileout);
				// final String myPath = extractPath(filein);
				filesIn.add(filein);
				filesOut.add(newFileOut);
			}

		} else { // We have more than 2 arguments...
			if (isConversionOption()) {
				int i = 0;
				String filein = null;
				String fileout = null;
				String newFileOut = null;
				// if Stdout specified, output will be redirected to standard
				// out.
				if (isStdout()) {
					fileout = "SystemOut";
				}
				// Retrieve files names according to options.
				for (; i < args.length; i++) {
					if (args[i].toLowerCase().endsWith(STDOUT_OPT)
							|| i == ckrank || i == outputRank) {
						continue;
					} else {
						if ((i - 1) == outputRank) {
							if (!filesIn.isEmpty()) {
								fileout = resolveDestinationPath(
										(String) filesIn.get(0), args[i]);
							} else {
								fileout = args[i];
							}
							filesOut.add(fileout);

						} else {
							filein = args[i];
							filesIn.add(filein);
							if (fileout == null && !isOutput() && !isStdout()) {
								fileout = filein;
								newFileOut = formatOutFileName(fileout);
								filesOut.add(newFileOut);
							}
						}
					}
				}
				if (isStdout()) {
					filesOut.add(fileout); // null
				}
				// The journal will be located near the last input file
				// final String myPath = extractPath(filein);
			} else if (isUnrecognizedOption()) {
				printBigHelp();
				throw new CamiException("Exception: unrecognized option.");
			} else {
				printBigHelp();
				throw new CamiException("Exception: unexpected arguments.");
			}
		}
	}

	/**
	 * Figures out what conversion options are.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	private void processConversionOption(String[] args) {
		int i = 0;
		for (; i < args.length; i++) {
			if (args[i].toLowerCase().endsWith(CAMI2P_OPT)
					|| args[i].toLowerCase().endsWith(P2CAMI_OPT)) {
				isCkPresent = true;
				break;
			} else if (args[i].toLowerCase().startsWith("-")
					|| (args[i].toLowerCase().endsWith("2p") || args[i]
							.toLowerCase().endsWith("2cami"))) {
				isUnrecognized = true;
				break;
			}
		}
		if (isCkPresent) {
			if (args[i].toLowerCase().endsWith(CAMI2P_OPT)) {
				conversionKind = CAMI2P_OPT;
			} else if (args[i].toLowerCase().endsWith(P2CAMI_OPT)) {
				conversionKind = P2CAMI_OPT;
			}
			ckrank = i;
		}
	}

	/**
	 * Tells if the conversion option was specified on the command line.
	 * 
	 * @return true if the conversion option was specified.
	 */
	private boolean isConversionOption() {
		return isCkPresent;
	}

	/**
	 * Tells if an unrecognized option was specified on the command line.
	 * 
	 * @return true if conversion option was unrecognized
	 */
	private boolean isUnrecognizedOption() {
		return isUnrecognized;
	}

	/**
	 * Figures out if stdout option was specified.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	private void processStdoutOption(String[] args) {
		int i = 0;
		for (; i < args.length; i++) {
			if (args[i].toLowerCase().endsWith(STDOUT_OPT)) {
				isStdoutPresent = true;
				break;
			}
		}
	}

	/**
	 * Tells if standard out (screen) was specified.
	 * 
	 * @return true if stdout option was specified.
	 */
	private boolean isStdout() {
		return isStdoutPresent;
	}

	/**
	 * Figures out if the output option was specified.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	private void processOutputOption(String[] args) {
		int i = 0;
		for (; i < args.length; i++) {
			if (args[i].toLowerCase().endsWith(OUTPUT_OPT)) {
				isOutputPresent = true;
				outputRank = i;
				break;
			}
		}
	}

	/**
	 * Tells if output option was specified.
	 * 
	 * @return true of output option.
	 */
	private boolean isOutput() {
		return isOutputPresent;
	}

	/**
	 * Returns a given filename without its extension. If a full path was
	 * specified it is kept in the result.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file name without its extension
	 */
	public final String extractNameWithoutExtension(String fileName) {
		final int index = fileName.lastIndexOf(".");
		if (index != -1) {
			return fileName.substring(0, index);
		} else {
			return fileName;
		}
	}

	/**
	 * Format the name of output file. It appends to the input file name the
	 * conversion kind asked for. Example: myModel.cami becomes myModel.pnml
	 * Instance method.
	 * 
	 * @param fileName
	 *            the name of the input file
	 * @return the output file name derived from the input one
	 */
	public final String formatOutFileName(String fileName) {
		final String[] format = conversionKind.split("2");
		// Is it to Pnml ?
		if (format[1].equalsIgnoreCase("p")) {
			return extractNameWithoutExtension(fileName) + ".pnml";
		} else {
			return extractNameWithoutExtension(fileName) + "." + format[1];
		}
	}

	/**
	 * Extracts the very last name of an input file from its name (absolute path
	 * or not).
	 * 
	 * @param fileName
	 *            the file name
	 * @return the last name of the file path
	 */
	public final String extractLastName(String fileName) {
		String[] format = null;
		String result = null;

		if (fileName != null) {
			String fileSep = System.getProperty("file.separator");
			boolean isWind = false;
			if (fileSep != null) {
				if (fileSep.equalsIgnoreCase("\\")) {
					fileSep += "\\";
					isWind = true;
				}
				format = fileName.split(fileSep);
				if (isWind && format.length == 1) {
					format = fileName.split("/"); // we are on the platform
				}
				result = format[format.length - 1];
			} else {
				System.err
						.println("CamiRunner.extractLastName: I cannot recognize your system file separator property."
								+ " This may lead the program to an error.");
			}
		} else {
			System.err
					.println("Runner#extractLastName: the file name parameter is null."
							+ " This may lead the program to an error.");
		}
		return result;
	}

	/**
	 * Returns the path in a given file name.
	 * 
	 * @param fileName
	 *            the file name from which the path is extracted.
	 * @return the path of the file, empty string if the file is located in the
	 *         current directory.
	 */
	public final String extractPath(String fileName) {
		String result = "";
		String fileSep = System.getProperty("file.separator");
		if (fileSep.equalsIgnoreCase("\\")) {
			fileSep += "\\";
		}
		final String[] format = fileName.split(fileSep);

		if (format.length >= 1) {
			final int index = fileName.lastIndexOf(fileSep);
			if (index != -1) {
				final File dr = new File(fileName);
				if (dr.isDirectory()) {
					result = fileName;
				} else {
					result = fileName.substring(0, index + 1);
				}
			}

		}
		return result;
	}

	/**
	 * When using -output option, the user may have just specified the
	 * destination path instead of the destination complete file name. We
	 * recover from this by deriving the destination file name from the path +
	 * the input file name converted for the destination format (.cami or .pnml)
	 * 
	 * @param inputFile
	 *            the input file name
	 * @param destinationPath
	 *            the path of the destination (output) file
	 * @return the destination file name
	 */
	public final String resolveDestinationPath(String inputFile,
			String destinationPath) {
		String result = destinationPath;
		String fileSep = System.getProperty("file.separator");
		boolean isWind = false;
		if (fileSep.equalsIgnoreCase("\\")) {
			fileSep += "\\";
			isWind = true;
		}
		if (destinationPath.equalsIgnoreCase(".")) {
			result = formatOutFileName(extractLastName(inputFile));
		} else if (destinationPath.equalsIgnoreCase("..")) {
			if (isWind) {
				result = "..\\" + formatOutFileName(extractLastName(inputFile));
			} else {
				result = "../" + formatOutFileName(extractLastName(inputFile));
			}
		} else {
			final String thePath = extractPath(destinationPath);
			final String theLastName = extractLastName(destinationPath);
			final String recoverPath = thePath + theLastName;
			if (!recoverPath.equalsIgnoreCase(destinationPath)) {
				result = (thePath.endsWith(fileSep) ? thePath : thePath
						+ fileSep)
						+ formatOutFileName(extractLastName(inputFile));
			}
		}
		return result;
	}

	/**
	 * Tells if help is solicited.
	 * 
	 * @param args
	 *            command line arguments.
	 * @return true if help is asked for
	 */
	private boolean isHelp(String[] args) {
		int i = 0;
		for (; i < args.length; i++) {
			if (args[i].toLowerCase().endsWith("help")
					|| (args[i].toLowerCase().equalsIgnoreCase("-h"))) {
				isHelpAskedFor = true;
				break;
			}
		}
		return isHelpAskedFor;
	}

	/**
	 * Inits bighelp and smallhelp buffers.
	 */
	private static void initHelp() {
		final int bigBufferSize = 512;
		final int smallBufferSize = 128;
		bighelp = new StringBuffer(bigBufferSize);
		bighelp
				.append("You must specify which kind of export you want to do.\n");
		bighelp.append("\n");
		bighelp
				.append("Usage: java -jar JarFile -conversionOption [-stdout] FileIn1 [FileIn2 ..] [-output destinationFile]\n");
		bighelp.append("\n");
		bighelp.append("Where -conversionOption is -cami2p or -p2cami.\n");
		bighelp.append("\n");
		bighelp
				.append("-stdout is optional.\n Specify it to see the results on standard out (usually the screen).\n\n");
		bighelp
				.append("-output is optional.\n Use it if you want to specifiy your own destination file.\n\n");
		bighelp
				.append("-output and -stdout are exclusive one of the other.\n If you specify both, -stdout takes precedence and -output is ignored.\n\n");
		bighelp
				.append("[FileIn2 ..] is also optional.\n It means you can specify more than one CPN-AMI or PNML input file.\n\n");
		bighelp
				.append("Output files will have the same name as input files with .pnml or .cami extension\n");
		bighelp
				.append("and will be located in the same directory as input ones.\nUnless you have specified your own destination file with -output.\n");
		bighelp.append("\n");
		bighelp
				.append("Note: if stdout is selected, there will be no output files.\n");
		// inits smallhelp buffer
		smallhelp = new StringBuffer(smallBufferSize);
		smallhelp
				.append("Something went wrong with your command. See error message.\n");
		smallhelp.append("Re-run this program with -help or -h to get help,\n");
	}

	/**
	 * Prints reduced help.
	 */
	private static void printSmallHelp() {
		System.err.println(smallhelp.toString());
	}

	/**
	 * Prints help.
	 */
	private static void printBigHelp() {
		System.err.println(bighelp.toString());
	}

	/**
	 * . Provides a compatible XML ID given a CAMI ID
	 * 
	 * @param camiId
	 *            the CAMI ID to transform into a comptatible XML ID
	 * @return the compatible XML ID
	 */
	public final synchronized String transformCamiId2XmlId(int camiId) {
		final Random generator = new Random(new Date().getTime());
		final long rand = generator.nextLong();
		buffer.delete(0, buffer.length());
		buffer.append(CID).append(rand).append(camiId);
		return buffer.toString();
	}
	

	/**
	 * @param camiModelIn
	 *            the vector containing Cami models delimited by (DB, FB) or
	 *            (DE, FE).
	 * @param pnmlFileOut
	 *            the PNML output file.
	 * @throws CamiException
	 *             Cami exception
	 * @see fr.lip6.move.pnml.cpnami.cami.Runner#cami2p(java.lang.String[],
	 *      java.lang.String)
	 */
	public final void cami2p(Vector<String> camiModelIn, String pnmlFileOut)
			throws CamiException {
		final Cami2Pnml c2p = caf.createCami2Pnml();
		c2p.cami2p(camiModelIn, pnmlFileOut);
		JOURNAL.info("Runner#cami2p: CAMI model(s) exported  into PNML. ");
	}

	/**
	 * @param pnmlFileIn
	 *            the PNML input file
	 * @return the vector containing obtained Cami models from the PNML file.
	 * @throws CamiException
	 *             Cami exception
	 * @see fr.lip6.move.pnml.cpnami.cami.Runner#p2cami(java.lang.String)
	 */
	public final Vector<String> p2cami(String pnmlFileIn) throws CamiException {
		final Pnml2Cami p2c = caf.createPnml2Cami();
		return p2c.pnml2cami(pnmlFileIn);
	}

	/**
	 * Calculates the optimal size for a map given the initial number of
	 * elements.
	 * 
	 * @param initialSize
	 *            number of objects existing in hashmap
	 * @return int
	 */
	public final int optimalMapSize(int initialSize) {
		final int quatre = 4;
		final int trois = 3;
		final int seize = 16;
		return ((Math.abs(initialSize) * quatre) / trois) + seize;
	}

	/**
	 * Printing routine to write a given String into Standard out or FileWriter
	 * descriptor.
	 * 
	 * @param toPrint
	 *            the String to print
	 * @param pw
	 *            the destination descriptor
	 */
	public final void printString(String toPrint, PrintWriter pw) {
		if (pw != null) {
			pw.println(toPrint);
		} else {
			throw new NullPointerException(
					"Runner#PrintString: the pointer to the Cami file is null!");
		}
	}

} // RunnerImpl
