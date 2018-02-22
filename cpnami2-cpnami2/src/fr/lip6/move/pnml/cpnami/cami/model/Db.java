
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.Parser;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Db</b></em>'.
 * <!-- end-user-doc -->
 * 
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage#getDb()
 */
public interface Db extends Command {
	/**
	 * Sets the parser with the Db command to parse.
	 * @param parser the parser of Cami commands.
	 */
    void setDb(Parser parser);

   /**
    * Sets the Db command.
    */
    void setDb();

    /**
     * Returns a string representation of this command.
     * @return its string representation
     */
    String convert2String();

} // Db
