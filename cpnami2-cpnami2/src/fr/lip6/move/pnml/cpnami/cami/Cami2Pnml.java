
package fr.lip6.move.pnml.cpnami.cami;

import java.util.List;
import java.util.Vector;

import fr.lip6.move.pnml.cpnami.cami.utils.CLOptions;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;

/**
 * <!-- begin-user-doc -->
 * <p>
 * Performs model transformation between CPN-AMI models and PNML Petri nets
 * models representation.
 * </p>
 * <p>
 * First, CPN-AMI models are parsed and stored in a repository as CAMI (internal
 * language) objects. Then these CAMI objects are processed to populate the
 * framework model repository for a particular type of Petri net (PT, SC, etc.).
 * Afterwards, the framework model builder for this particular type of Petri net
 * is called with the repository as parameter.
 * </p>
 * <p>
 * Finally the framework model is written into a PNML document, which can
 * contain several models.
 * </p>
 * <p>
 * This interface is implemented by a singleton.
 * </p>
 * <!-- end-user-doc -->
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.CamiPackage#getCami2Pnml()
 * @model
 * 
 */
public interface Cami2Pnml {
	/**
	 * <!-- begin-user-doc --> Performs CAMI models parsing and transformation
	 * to PNML models. It uses PNML Framework API to create the PNML models.
	 * Then the models are exported into one or several PNML files according to
	 * the input. <!-- end-user-doc -->
	 * 
	 * @param pnmlFilesOut
	 *            outputfile in PNML format
	 * @param camiFiles
	 *            inputfile in Cami format
	 * @throws CamiException
	 *             throw a CamiException
	 */
	void cami2Pnml(List<String> camiFiles, List<String> pnmlFilesOut)
			throws CamiException;

	/**
	 * Cami to PNML conversion. Command-line options argument will be probed to
	 * discover if fall-back or PN type escalation mechanism should be used.
	 * 
	 * @param camiFiles
	 *            list of input Cami files
	 * @param pnmlFilesOut
	 *            list of output PNML documents
	 * @param clOptions
	 *            command-line options
	 * @throws CamiException
	 *             something went wrong during conversion
	 */
	void cami2Pnml(List<String> camiFiles, List<String> pnmlFilesOut,
			CLOptions clOptions) throws CamiException;

	/**
	 * Alternative method to convert Cami models into PNML. Provides a vector of
	 * Strings as input and a single PNML output file. Thus all Cami models you
	 * have put in the vector will be exported in a single PNML file, since a
	 * PNML document can contain many Petri net models, of course of the same
	 * type.
	 * 
	 * @see fr.lip6.move.pnml.cpnami.cami.Cami2Pnml#cami2p(java.lang.String[],
	 *      java.lang.String)
	 * @param camiModelIn
	 *            the vector containing the Cami models.
	 * @param pnmlFileOut
	 *            the PNML output file.
	 * @throws CamiException
	 *             CamiException
	 */
	void cami2p(Vector<String> camiModelIn, String pnmlFileOut)
			throws CamiException;

} // Cami2Pnml
