
package fr.lip6.move.pnml.cpnami.cami.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;

import fr.lip6.move.pnml.cpnami.cami.Cami2Pnml;
import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.Pnml2Cami;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.utils.CLOptions;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.cpnami.exceptions.ConversionException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

/**
 * Implements the Runner interface, which is the entry point to run this
 * application to convert CAMI models into PNML and vice versa.
 */
public final class ConversionDriver implements Runner {
	/**
	 * Retrieves the journal this application, whose name is monJournal.
	 */
	private static final Logger JOURNAL = LogMaster.getLogger(ConversionDriver.class.getCanonicalName());

	/**
	 * Usage.
	 */
	private static final String USAGE = "java -jar jar_name conversion_Kind [options] inputFile1 [inputFile2 ...] [outputFile]";
	/**
	 * Parser printing space width.
	 */
	private static final int PARSER_WIDTH = 80;
	/**
	 * The prefix constant for creating PNML ids.
	 */
	private static final String CID = "cId";
	/**
	 * The command-line parser.
	 */
	private static CmdLineParser parser;

	/**
	 * Retrieves Cami factory singleton.
	 */
	private static CamiFactory caf = CamiFactory.SINSTANCE;

	/**
	 * The list of input files.
	 */
	private List<String> filesIn;
	/**
	 * The list of output files.
	 */
	private List<String> filesOut = new ArrayList<String>();
	/**
	 * To avoid final string concatenations
	 */
	private StringBuilder buffer;

	/**
	 * <!-- begin-user-doc --> The singleton instance of this class must be
	 * created through CamiFactory. <!-- end-user-doc -->
	 * 
	 */
	protected ConversionDriver() {
		super();
		this.buffer = new StringBuilder();
	}

	// TODO: Les listes dans les arguments de cami2pnml et pnml2cami
	// servent a prevoir une extension de la ligne de commande
	// lorsque l'utilisateur voudra par exemple mettre un ensemble de modeles
	// CAMI dans le meme fichier PNML.
	/**
	 * Processes command line arguments and runs the application.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws CamiException
	 *             Cami exception
	 */
	public final void run(String[] args) throws CamiException {

		try {
			long startTime = System.nanoTime();
			filesOut.clear();
			if (filesIn != null) 
				filesIn.clear();
			parseArgs(args);
			long endTime = System.nanoTime();
			JOURNAL.info("Conversion between CAMI and PNML took " + (endTime - startTime) / 1.0e9
					+ " seconds.");
		} catch (CamiException e1) {
			JOURNAL.error(e1.getMessage());
			//e1.printStackTrace();
		}
	}

	/**
	 * Prints helps.
	 * 
	 * @param message
	 *            help message
	 */
	private void printHelp(String message) {
		//parser.setUsageWidth(PARSER_WIDTH);
		System.err.println(message);
		System.err.println(USAGE);
		parser.printUsage(System.err);
		System.err.println();
	}

	/**
	 * Parses command-line arguments and launch the conversion the user asked
	 * for (Cami to PNML or vice versa).
	 * 
	 * @param args
	 *            command-line arguments
	 * @throws CamiException
	 *             something went during option parsing.
	 */
	private void parseArgs(String[] args) throws CamiException {
		final CLOptions cloptions = new CLOptions();
		parser = new CmdLineParser(cloptions);
		try {
			// Processes command-line arguments and gives usage information if
			// necessary
			parser.parseArgument(args);
			processArguments(cloptions);
			if (cloptions.isCami2P()) {
				final Cami2Pnml c2p = caf.createCami2Pnml();
				// TODO : implement fall back and export to HLPN
				if (cloptions.isStdout()) {
					c2p.cami2Pnml(filesIn, null, cloptions);
				} else {
					c2p.cami2Pnml(filesIn, filesOut, cloptions);
				}

			} else if (cloptions.isPnml2C()) {
				final Pnml2Cami p2c = caf.createPnml2Cami();
				if (cloptions.isStdout()) {
					p2c.pnml2Cami(filesIn, null);
				} else {
					JOURNAL.info("Exporting PNML into Cami.");
					p2c.pnml2Cami(filesIn, filesOut);
				}
			}
			JOURNAL.info("See (check error log if an output file is missing): ");
			if (cloptions.isStdout()) {
				JOURNAL.info("Printed result on Standard out.");
			} else {
				for (final String it : filesOut) {
					JOURNAL.info(it);
				}
			}
		} catch (CamiException ce) {
			if (cloptions.isHelp()) {
				JOURNAL.warn(ce.getMessage());
			} else {
				//ce.printStackTrace(); 
				throw new CamiException(ce.getMessage());
			}
		} catch (CmdLineException e) {
			throw new CamiException(e.getMessage(), e.getCause());
		}

	}

	/**
	 * Processes the arguments.
	 * 
	 * @param cloptions
	 *            command-line option
	 * @throws CamiException
	 *             Cami exception
	 */
	private void processArguments(CLOptions cloptions) throws CamiException {
		if (cloptions.isHelp()) {
			printHelp("USAGE:");
			throw new CamiException("End of help message.");
		}
		try {
			processOutputOption(cloptions);
			processConversionOption(cloptions);
		} catch (IOException e) {
			throw new CamiException(e.getMessage(), e.getCause());
		}

	}

	/**
	 * Figures out what conversion options are.
	 * 
	 * @param cloptions
	 *            command line arguments.
	 * @throws ConversionException
	 *             insufficient arguments
	 */
	private void processConversionOption(CLOptions cloptions) throws ConversionException {
		if (!cloptions.isCami2P() && !cloptions.isPnml2C()) {
			printHelp("You must specify at least one conversion option.");
			throw new ConversionException("No conversion option specified");
		}
		filesIn = cloptions.getArguments();
		if (filesIn == null || filesIn.isEmpty()) {
			printHelp("You must spcecify at least one document to convert.");
			throw new ConversionException("No file to convert specified.");
		}
		createOutputFilesPaths(cloptions);

	}

	/**
	 * Create output files paths by deducing them from input files paths. They
	 * are put into list of output files.
	 * 
	 * @param cloptions
	 *            command-line options
	 */
	private void createOutputFilesPaths(CLOptions cloptions) {
		// We deduce output files paths only if a global output file
		// path has not been specified by the user.
		if (!cloptions.isOutput()) {
			for (String inFile : filesIn) {
				final String outFile = formatOutFileName(inFile, cloptions);
				filesOut.add(outFile);
			}
		}
	}

	/**
	 * Figures out if the output file option was specified and checks it. If the
	 * output is just a destination directory, then a default output file path
	 * is deduced from the first input file path.
	 * 
	 * @param clOptions
	 *            command line options.
	 * @throws IOException
	 *             something went wrong while resolving destination file path
	 * 
	 */
	private void processOutputOption(CLOptions clOptions) throws IOException {
		String outputFilePath;
		if (clOptions.isOutput()) {
			final File outputFile = clOptions.getOutput();
			if (outputFile.isDirectory()) {
				try {
					outputFilePath = resolveDestinationPath(filesIn.get(0), outputFile.getCanonicalPath(), clOptions);

				} catch (IOException e) {
					throw e;
				}

			} else {
				outputFilePath = outputFile.getCanonicalPath();
			}
			filesOut.add(outputFilePath);
		}
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
	 * Format the name of output files. It appends to the input file name the
	 * destination format extension. Example: myModel.cami becomes myModel.pnml
	 * Instance method.
	 * 
	 * @param fileName
	 *            the name of the input file
	 * @param clOptions
	 *            command-line options
	 * @return the output file name derived from the input one
	 */
	public final String formatOutFileName(String fileName, CLOptions clOptions) {
		// Is it to Pnml ?
		if (clOptions.isCami2P()) {
			return extractNameWithoutExtension(fileName) + ".pnml";
		} else {
			return extractNameWithoutExtension(fileName) + ".cami";
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
				System.err.println("#extractLastName: cannot recognize your system file separator property.\n"
						+ " This may lead the program to an error.");
			}
		} else {
			System.err.println("#extractLastName: file name parameter is null.\n" + " This may lead the program to an error.");
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
	 * @param clOptions
	 *            command-line option
	 * @return the destination file name
	 */
	public final String resolveDestinationPath(String inputFile, String destinationPath, CLOptions clOptions) {
		String result = destinationPath;
		String fileSep = System.getProperty("file.separator");
		boolean isWind = false;
		if (fileSep.equalsIgnoreCase("\\")) {
			fileSep += "\\";
			isWind = true;
		}
		if (destinationPath.equalsIgnoreCase(".")) {
			result = formatOutFileName(extractLastName(inputFile), clOptions);
		} else if (destinationPath.equalsIgnoreCase("..")) {
			if (isWind) {
				result = "..\\" + formatOutFileName(extractLastName(inputFile), clOptions);
			} else {
				result = "../" + formatOutFileName(extractLastName(inputFile), clOptions);
			}
		} else {
			final String thePath = extractPath(destinationPath);
			final String theLastName = extractLastName(destinationPath);
			final String recoverPath = thePath + theLastName;
			if (!recoverPath.equalsIgnoreCase(destinationPath)) {
				result = (thePath.endsWith(fileSep) ? thePath : thePath + fileSep) + formatOutFileName(extractLastName(inputFile), clOptions);
			}
		}
		return result;
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
	public final void cami2p(Vector<String> camiModelIn, String pnmlFileOut) throws CamiException {
		final Cami2Pnml c2p = caf.createCami2Pnml();
		c2p.cami2p(camiModelIn, pnmlFileOut);
		JOURNAL.info("#cami2p: CAMI model(s) exported  into PNML. ");
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
		return Math.abs(initialSize) * quatre / trois + seize;
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
			throw new NullPointerException("#PrintString: the pointer to the Cami file is null!");
		}
	}

} // RunnerImpl
