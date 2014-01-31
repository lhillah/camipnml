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
package fr.lip6.move.pnml.cpnami.cami;

import java.io.PrintWriter;
import java.util.Vector;

import fr.lip6.move.pnml.cpnami.exceptions.CamiException;

/**
 * <!-- begin-user-doc --> This class is the CPN-AMI application start engine.
 * It provides a single {@link #run(String[])} method which expects command-line
 * parameters. It calls the appropriate model transformation program according
 * to what is specified in the parameters. Tools designers must provide the
 * program package which:
 * <ul>
 * <li>performs transformation from their tool internal representation to PNML
 * models using PNML Framework;
 * <li>vice versa
 * </ul>
 * This CPN-AMI application is a typical example of what a tool developer could
 * do. NOTE: this interface is implemented by a singleton. <!-- end-user-doc -->
 * 
 * @see fr.lip6.move.pnml.cpnami.cami
 * @see fr.lip6.move.pnml.cpnami.cami.model
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage#getRunner()
 * @model
 * @generated
 */
public interface Runner {
	/**
	 * . <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String COPYRIGHT = "Copyright (C) 2008 LIP6";

	/**
	 * <p>
	 * Runs this CAMI-PNML application with specified parameters. It performs
	 * command-line parameters processing.
	 * </p>
	 * <p>
	 * This method must be called by main programs. It is provided as a runner
	 * for CPN-AMI-PNML model transformation application.
	 * </p>
	 * <p>
	 * It is provided with default running options for CAMI input files as
	 * user-side input files.
	 * </p>
	 * (Cf. <a href="http://move.lip6.fr/CPNAMI/index.html">CPN-AMI</a>)
	 * 
	 * @param args
	 *            command line arguments throws VoidRepositoryException
	 * @throws CamiException
	 *             throws CamiException
	 * @model
	 */
	void run(String[] args) throws CamiException;

	/**
	 * In-Memory method for Coloane, to transform PNML models into CAMI.
	 * 
	 * @return return an array of CAMI commands which build the CAMI model.
	 * @param pnmlFileIn
	 *            the PNML file from which to load PNML models.
	 * @throws CamiException
	 *             throws CamiException
	 */
	Vector<String> p2cami(String pnmlFileIn) throws CamiException;

	/**
	 * Alternative method to transform CAMI models into PNML.
	 * 
	 * @param camiModelIn
	 *            is an array of CAMI commands which build the CAMI models.
	 *            There may be one or several Cami models delimited by the pair
	 *            DB FB, or DE FE commands. You can even simply just use DB or
	 *            DE.
	 * @param pnmlFileOut
	 *            Cami2p will write all built PNML models into this file. They
	 *            all must be of the same type (PT net or Symmetric Net).
	 * @throws CamiException
	 *             throws CamiException
	 */
	void cami2p(Vector<String> camiModelIn, String pnmlFileOut)
			throws CamiException;

	/**
	 * . Provides a compatible XML ID given a CAMI ID
	 * 
	 * @param camiId
	 *            the CAMI ID to transform into a compatible XML ID
	 * @return the compatible XML ID
	 */
	String transformCamiId2XmlId(int camiId);

	/**
	 * Returns a given filename without its extension. If a full path was
	 * specified, it is kept in the result.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file name without its extension
	 */
	String extractNameWithoutExtension(String fileName);

	/**
	 * Format the name of output file. It appends to the input file name the
	 * conversion kind asked for. Example: myModel.cami becomes myModel.pnml
	 * Instance method.
	 * 
	 * @param fileName
	 *            the name of the input file
	 * @return the output file name derived from the input one
	 */
	// String formatOutFileName(String fileName);
	/**
	 * Extracts the very last name of an input file from its name (absolute path
	 * or not). If there is a full path, it is thus pruned. The file extension
	 * is kept, if any.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the last name of the file path
	 */
	String extractLastName(String fileName);

	/**
	 * Returns the path in a given file name.
	 * 
	 * @param fileName
	 *            the file name from which the path is extracted.
	 * @return the path of the file, empty string if the file is located in the
	 *         current directory.
	 */
	String extractPath(String fileName);

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
	// String resolveDestinationPath(String inputFile, String destinationPath);
	/**
	 * Calculates the optimal size for a map given the initial number of
	 * elements.
	 * 
	 * @param initialSize
	 *            number of objects existing in hashmap
	 * @return int
	 */
	int optimalMapSize(int initialSize);

	/**
	 * Printing routine to write a given String into Standard out or FileWriter
	 * descriptor.
	 * 
	 * @param toPrint
	 *            the String to print
	 * @param pw
	 *            the destination descriptor
	 */
	void printString(String toPrint, PrintWriter pw);

} // Runner
