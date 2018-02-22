
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Fb</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getFb()
 */
public interface Fb extends Command {
	/**
	 * Sets the parser with the Fb command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setFb(Parser parser);

	/**
	 * Sets the Fb command.
	 */
	void setFb();

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Fb
