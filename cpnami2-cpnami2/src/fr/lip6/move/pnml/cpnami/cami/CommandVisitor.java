
package fr.lip6.move.pnml.cpnami.cami;

/**
 * 
 * @author bbouzereau
 *
 */

public interface CommandVisitor {
	
	/**
	 * @param command 
	 * 			Cami Command 
	 */
	void visitAs(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCa(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCm(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCn(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCs(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitCt(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitDb(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitDe(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitFb(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitFe(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPi(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPo(Command command);

	/**
	 * @param command 
	 * 			Cami Command 
	 */
    void visitPt(Command command);

}
