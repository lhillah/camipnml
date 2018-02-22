
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
public interface Fe extends Command {
	/**
	 * Sets the parser with the Fe command to parse.
	 * 
	 * @param parser
	 *            the parser of Cami commands.
	 */
	void setFe(Parser parser);

	/**
	 * Sets the Fe command.
	 */
	void setFe();

	/**
	 * Returns a string representation of this command.
	 * 
	 * @return its string representation
	 */
	String convert2String();

} // Fe
