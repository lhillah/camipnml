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
package fr.lip6.move.pnml.cpnami.cami;

import java.util.List;
import java.util.Set;

import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cm;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Cs;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;

/**
 * Allows for Cami commands storage. It is thus a repository of cami commands
 * for one Petri net at a time. If the user wants to store more than one Petri
 * net then more repository instances must be created.
 */

public interface CamiRepository {

    /**
     * Adds a new command to the Cami commands repository maps.
     * 
     * @param cmd
     * 			permit to add a command in Cami format
     * 
     * @throws CamiException
     *                 if the insertion was unsuccessfull, especially if the
     *                 command was not recognized. <br>
     *                 NOTE: This methods doesn't allow re-insertion of CA and
     *                 CN commands with the same id, for they must be unique.
     *                 Multiple insertion of CM and PI commands is allowed. In
     *                 that case, the key is the command itself and the value
     *                 the associated node (or arc) id. A re-insertion of a Po,
     *                 Pt or Ct command overwrites the previous one with the
     *                 same id. So beware of endless loops !
     * @see fr.lip6.move.pnml.cpnami.cami.CamiRepository#removeCommand(Command,
     *      int)
     *    
     */
    void addCommand(Command cmd) throws CamiException;

    /**
     * Removes command from the Cami commands repository maps.
     * 
     * @param kind 
     * 			name of command
     * @param objectId
     * 			id in the cami commands repository			
     * @throws CamiException
     *                 if the remove was unsuccessful, especially if the
     *                 command was not recognized. <br>
     *                 NOTE: The argument <em>kind</em> must have a concrete
     *                 command type (e.g., Pt). It is not required to have all
     *                 members set. The command type and the id suffice.
     * @see fr.lip6.move.pnml.cpnami.cami.CamiRepository#addCommand(Command)
     */
    void removeCommand(CAMICOMMANDS kind, int objectId) throws CamiException;
    
    /**
     * Associates a Cami Id to its random-generated (XML compliant) 
     * Pnml equivalent. Null values are accepted, so you should check
     * what you get before putting them in the repository.
     * @param camiId the original Cami Id
     * @param pnmlId the random-generated Pnml Id.
     */
    void relateCamiPnmlIds(int camiId, String pnmlId);
    
    /**
     * Returns the associated random-generated Pnml Id to 
     * this Cami Id.
     * @param camiId the Cami Id to which a Pnml Id is associated. 
     * @return the corresponding associated Pnml Id.
     */
    String getPnmlIdOfCamiId(int camiId);
    /**
     * Associates a Pnml Id to its random-generated (Integer compliant) 
     * Cami equivalent. Null values are accepted, so you should check
     * what you get before putting them in the repository.
     * @param camiId the random-generated Cami Id
     * @param pnmlId the original Pnml Id.
     */
    void relatePnmlCamiIds(String pnmlId, int camiId);
    
    /**
     * Returns the associated random-generated Cami Id to 
     * this Pnml Id.
     * @param pnmlId the Pnml Id to which a Cami Id is associated. 
     * @return the corresponding associated Cami Id; null otherwise.
     */
    Integer getCamiIdOfPnmlId(String pnmlId);

    /**
     * Returns the list of all CA commands. A CA command corresponds to an arc.
     * 
     * @return the list of all CA commands, null if there is no such command.
     */
    List<Ca> getAllCa();

    /**
     * Returns the list of all CM commands. A CM command corresponds to a
     * multi-line. textual attribute.
     * 
     * @return the list of all CM commands, null if there is no such command.
     */
    List<Cm> getAllCm();

    /**.
     * Returns the set of nodes ids that have multiline attributes
     * 
     * @return the Set of nodes ids that have multiline attributes, <code>null</code> if
     *         there are no such nodes.
     */
    Set<Integer> getCmNodes();

    /**
     * Returns the list of Cm commands for a given node.
     * 
     * @param nodeId
     * 			Cami id node
     * @return the list of Cm commands for given node, null if there is no such
     *         commands for the given node.
     */
    List<Cm> getCmOfNode(Integer nodeId);

    /**
     * Returns the list of all CN commands. A CN command corresponds to a node.
     * It could be either a place or a transition, a reference place or a
     * reference transition.
     * 
     * @return the list of all CN commands, null if there is no such command.
     */
    List<Cn> getAllCn();

    /**
     * Returns the list of all CT commands. A CT command corresponds to a single
     * line textual attribute.
     * 
     * @return the list of all CT commands, null if there is no such command
     */
    List<Ct> getAllCt();

    /**
     * Returns the list of all PI commands. A PI command corresponds to an arc
     * intermediary position.
     * 
     * @return the list of all PI commands, null if there is no such command.
     */
    List<Pi> getAllPi();

    /**
     * Returns the list of all PO commands. A PO command corresponds to a node
     * absolute position.
     * 
     * @return the list of all PO commands, null if there is no such command.
     */
    List<Po> getAllPo();

    /**
     * Returns the list of all PT commands. A PT command corresponds to a
     * textual attribute position.
     * 
     * @return the list of all PT commands, null if there is no such command.
     */
    List<Pt> getAllPt();

    /**
     * Returns the list of all CN commands representing places.
     * 
     * @return the list of all CN commands, representing places.
     */
    List<Cn> getAllPlaces();

    /**.
     * Returns the list of all CN commands representing transitions
     * 
     * @return the list of all CN commands, representing transitions.
     */
    List<Cn> getAllTransitions();

    /**
     * @return Returns the number of CA commands, thus the number of Arcs.
     */
    int getNumberOfCa();

    /**
     * @return Returns the number of CM commands.
     */
    int getNumberOfCm();

    /**
     * @return Returns the number of CN commands, thus the number of Nodes.
     */
    int getNumberOfCn();

    /**
     * @return Returns the number of places.
     */
    int getNumberOfPlaces();

    /**
     * @return Returns the number of Transitions.
     */
    int getNumberOfTransitions();

    /**
     * @return Returns the number of CT commands.
     */
    int getNumberOfCt();

    /**
     * @return Returns the number of PI commands.
     */
    int getNumberOfPi();

    /**
     * @return Returns the number of PO commands.
     */
    int getNumberOfPo();

    /**
     * @return Returns the number of PT commands.
     */
    int getNumberOfPt();

    /**
     * Returns the Po object representing the absolute position of a given node.
     * Useful to compute Offset information for the node textual attribute in
     * PNML model.
     * 
     * @param nodeId
     *                the node id.
     * @return its absolute position
     */
    Po getNodePosition(Integer nodeId);

    /**
     * Returns the Pi object representing the first intermediate position of a
     * given arc. Useful to compute Offset information for the arc valuation in
     * PNML model.
     * 
     * @param arcId
     * 			Cami id arc
     * @return its first intermediate position
     */
    Pi getArcPosition(Integer arcId);
    
    /**
     * Returns the intermediate positions of an arc.
     * @param arcId Cami arc id
     * @return the list of this arc intermediate positions
     */
    List<Pi> getArcPositions(Integer arcId);

    /**
     *@param id 
     *	 	Cami id object
     * @return Returns object kind of the given id. An object is either a place, a transition
     * or an arc, or the net itself (for signatures)
     */
    String getObjectKind(Integer id);
    /**
     * Returns the name of a node (place or transition).<br/>
     * We assume that name does not span across multiple lines,
     * therefore, Cm commands will not be searched for this.
     * @param id the unique integer identifier of the node
     * @return the name of the node (place or transition),
     * <code>null</code> otherwise.
     */
    String getNodeName(Integer id);
    
    /**
     * Usually, arcs don't have a name in CAMI formalism.
     * Thus this method will most of the time return <code>null</code>.
     * @param id the unique integer identifier of the arc
     * @return the name of the arc, <code>null</code> otherwise.
     */
    String getArcName(Integer id);
    
    /**
     * Returns the initial marking of a place, as a String.<br/>
     * In Place/Transition nets (PTNET) this marking is a single integer.
     * In Symmetric nets (SN) it may be an integer, but most of the time
     * it is much more sophisticated (retrieved from AS and CS commands, which
     * build an abstract syntax tree for this marking).
     * @param id the unique integer identifier of the place.
     * @return its initial marking, as a String, <code>null</code> otherwise.
     */
    String getPlaceMarking(Integer id);
    
    /**
     * Returns the inscription of an arc, as a String.
     * In Place/Transition nets (PTNET), this inscription is 
     * a simple integer.
     * In Symmetric nets (SN) it may be an integer, but it is most
     * of the time much more sophisticated (retrieved from AS and CS commands, which
     * build an abstract syntax tree for this inscription).
     * @param id the unique integer identifier of the arc.
     * @return the inscription of the arc, <code>null</code> otherwise.
     */
    String getArcInscription(Integer id);
    
    /**
     * Returns true if the specified node (by its id) has Symmetric net-like 
     * annotation (SN), in which case we are dealing with a Symmetric net.
     * The detection is based on CS command.
     * @param nodeId the unique integer identifier of the node.
     * @return true if this node has a Symmetric net-like annotation.
     */
    boolean isNodeSc(Integer nodeId);

    /**
     * @param nodeId
     * 		permit to find cami node with its id
     * @return Returns the list of all Cs commands associated to a given node. Returns
     * <em>null</em> if the specified node has no associated Cs command,
     * <code>null</code> otherwise
     */
    List<Cs> getNodeCs(Integer nodeId);

    /**
     * @param nodeId
     * 		permit to find cami node with its id
     * @return Returns the list of all As commands associated to a given net node.
     * Returns <em>null</em> if the specified node has no associated As
     * command
     */
    List<As> getNodeAs(Integer nodeId);

    /**
     * @return Return the list of all Cs and As commands.
     * 
     *  the reading ordered list of Cs and As command as they were taken
     *         from camix file.
     */
    List<Command> getAllCsAs();

    /**
     * @param nodeId
     * 		permit to find cami node with its id
     * @return Returns the root node of the math annotation AST associated to a given
     * net node.
     */
    Cs getNodeRootCs(Integer nodeId);

    /**
     * Returns true if the current net stored in the repository is a SCNet. The
     * detection is based on SC declarations, for the net node (of id 1).
     * 
     * @return true if the current stored net in the repository is a SCNet.
     * @throws CamiException
     *                 if there is no net in the repository.
     */
    boolean isNetSc() throws CamiException;

    /**
     * Cleans repository all internal data structures (maps and lists),
     * but does not set them to null.
     * @see #resetRepository()
     */
    void cleanRepository();

    /**
     * Returns the CN command representing the net node itself.
     * 
     * @return the CN command that represents the net node itself.
     */
    Cn getNetNode();

    /**
     * Returns the name of the Petri net model contained in the repository.
     * 
     * @return the Petri net model name.
     */
    String getNetName();
    /**
     * Sets the name of the Petri net model stored in the Cami repository.
     * @param pnetName the net name.
     */
    void setNetName(String pnetName);

    /**
     * If there is a PT net in the repository, populates explicitly the implicit
     * inscriptions (i.e., 1) for the final PNML model.
     * 
     * @throws CamiException
     * 			throw cami Exception
     */
    void setImplicitPTInscriptions() throws CamiException;

    /**
     * Tells if the given object (arc, place) annotation is SC. The detection is
     * based on the object annotation String.
     * 
     * @param objectId
     *                the object id
     * @return true if the object annotation is SC. <br>
     *         <b>Note:</b> This method is used when transforming PT nets into
     *         PNML. Its main purpose is to help setting non SC annotations
     *         (inscriptions and markings) for the final PNML model. The reason
     *         to make the distinction on the annotation nature (PT or SC) is to
     *         determine if the net is the underlying PT of a SC or a genuine PT
     *         (in that case implicit PT annotations are set).<br>
     *         <b>Note:</b> Therefore, guards are simply ignored for the moment
     *         since in PT nets algorithms guards are discarded.
     */
    boolean isObjectAnnotationSc(Integer objectId);

    /**
     * 
     * @param visitor
     * 		TODO : what does this function do?
     */
    void accept(CommandVisitor visitor);

	Pt getMarkingPositionOfNode(int nodeId);

	Pt getNamePositionOfNode(int nodeId);

	Pt getValuationPosition(int arcID);

	boolean removeObject(int objectId);

	void removeAllPlaces();

	void removeAllTransitions();

	void removeAllArcs();

	/**
	 * Resets repository contents by setting all internal
	 * data structures to null.
	 * @see #cleanRepository()
	 */
	void resetRepository();

}
