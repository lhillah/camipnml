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
package fr.lip6.move.pnml.cpnami.cami.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.CAMICOMMANDS;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cm;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Cs;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.ModelFactory;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;

/**
 * Implements the CamiRepository interface. Stores Cami objects of a Petri net
 * model. Those objects basically correspond to the parsed Cami commands.
 * 
 */
public class CamiRepositoryImpl implements CamiRepository {

	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * Default capacity to initialize hash maps. Low capacity
	 */
	private static final int DEFAULT_CAPACITY_EXTREMELYLOW = 32;
	/**
	 * Default capacity to initialize hash maps. Low capacity
	 */
	private static final int DEFAULT_CAPACITY_LOW = 10000;
	/**
	 * Default capacity to initialize hash maps. Medium capacity
	 */
	private static final int DEFAULT_CAPACITY_MEDIUM = 20000;
	/**
	 * Default capacity to initialize hash maps. High capacity
	 */
	private static final int DEFAULT_CAPACITY_HIGH = 30000;
	/**
	 * The following maps store each kind of Arc command with the associated arc
	 * id as an Integer.
	 */
	protected ConcurrentMap<Integer, Ca> caMap;
	/**
	 * The key here is the command because there are multiple Cm commands for
	 * the same node. So we store the command as the key in the map.
	 */
	protected ConcurrentMap<Cm, Integer> cmMap;
	/**
	 * To each node id is associated the list of its Cm annotations
	 */
	protected ConcurrentMap<Integer, List<Cm>> cmInvMap;
	/**
	 * Places associated to their IDs.
	 */
	protected ConcurrentMap<Integer, Cn> placesMap;
	/**
	 * Transitions associated to their IDs.
	 */
	protected ConcurrentMap<Integer, Cn> transitionsMap;
	/**
	 * All nodes associated to their IDs.
	 */
	protected ConcurrentMap<Integer, Cn> cnMap;
	/**
	 * Each textual attribute associated to its respective node ID.
	 */
	protected ConcurrentMap<Ct, Integer> ctMap;
	/**
	 * All textual attributes associated to their respective node ID.
	 */
	protected ConcurrentMap<Integer, List<Ct>> ctInvMap;
	/**
	 * All intermediate positions associated to the respective IDs of their
	 * arcs.
	 */
	protected ConcurrentMap<Pi, Integer> piMap;

	protected ConcurrentMap<Integer, List<Pi>> piInvMap;
	/**
	 * All node positions associated to the respective IDs of their nodes.
	 */
	protected ConcurrentMap<Integer, Po> poMap;
	/**
	 * All textual attributes positions associated to the respective IDs of
	 * their nodes.
	 */
	protected ConcurrentMap<Pt, Integer> ptMap;

	protected ConcurrentMap<Integer, List<Pt>> ptInvMap;
	/**
	 * All SN root node associated to their IDs.
	 */
	protected ConcurrentMap<Cs, Integer> csMap;
	/**
	 * All As nodes associated to their IDs.
	 */
	protected ConcurrentMap<As, Integer> asMap;
	/**
	 * Stores ids of nodes that have a multi-line textual attribute.
	 */
	protected ConcurrentSkipListSet<Integer> cmNodes;
	/**
	 * stores each node id of the Petri net with his kind as String. Example :
	 * id = 341, kind = "place";
	 */
	protected ConcurrentMap<Integer, String> nodeKindMap;
	/**
	 * Stores SC nodes id. key = node id (Integer); value = root Cs command for
	 * each node; a root Cs command has math node description id < 0.
	 */
	protected ConcurrentMap<Integer, Cs> scNodeMap;
	/**
	 * Stores CS and AS command in reading order from CAMIX file.
	 */
	protected List<Command> csAsList;
	/**
	 * Stores the current net name.
	 */
	protected String netName = "unknown";
	/**
	 * Stores a SC object with its annotation (place, arc).
	 */
	protected ConcurrentMap<Integer, String> isObjectSCMap;
	/**
	 * Stores and relates the random generated XML-compliant PNML Id for each
	 * Cami Id.
	 */
	private ConcurrentMap<Integer, String> camiPnmlIds;
	/**
	 * Stores and relates the random generated Integer-compliant Cami Id for
	 * each PNML Id.
	 */
	private ConcurrentMap<String, Integer> pnmlCamiIds;

	/**
	 * Exception for Commands that already exist in the repository.
	 */
	public static class AlreadyExistException extends CamiException {
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Exception raised when a not Cami command is encountered.
		 * 
		 * @param message
		 *            the error message.
		 */
		public AlreadyExistException(final String message) {
			super(message);
		}

		/**
		 * Exception raised when a AlreadyExistExceptiond is encountered.
		 */
		public AlreadyExistException() {
			super();
		}
	}

	/**
	 * Default constructor, initializes the repository maps to a default
	 * capacity of 100.
	 */
	public CamiRepositoryImpl() {

		caMap = new ConcurrentHashMap<Integer, Ca>(DEFAULT_CAPACITY_HIGH);
		cmMap = new ConcurrentHashMap<Cm, Integer>(DEFAULT_CAPACITY_LOW);
		cmInvMap = new ConcurrentHashMap<Integer, List<Cm>>(
				DEFAULT_CAPACITY_LOW);
		placesMap = new ConcurrentHashMap<Integer, Cn>(DEFAULT_CAPACITY_MEDIUM);
		transitionsMap = new ConcurrentHashMap<Integer, Cn>(
				DEFAULT_CAPACITY_MEDIUM);
		cnMap = new ConcurrentHashMap<Integer, Cn>(DEFAULT_CAPACITY_HIGH);
		ctMap = new ConcurrentHashMap<Ct, Integer>(DEFAULT_CAPACITY_MEDIUM);
		ctInvMap = new ConcurrentHashMap<Integer, List<Ct>>(
				DEFAULT_CAPACITY_MEDIUM);
		piMap = new ConcurrentHashMap<Pi, Integer>(DEFAULT_CAPACITY_MEDIUM);
		piInvMap = new ConcurrentHashMap<Integer, List<Pi>>(
				DEFAULT_CAPACITY_EXTREMELYLOW);
		poMap = new ConcurrentHashMap<Integer, Po>(DEFAULT_CAPACITY_MEDIUM);
		ptMap = new ConcurrentHashMap<Pt, Integer>(DEFAULT_CAPACITY_MEDIUM);
		ptInvMap = new ConcurrentHashMap<Integer, List<Pt>>(
				DEFAULT_CAPACITY_MEDIUM);
		asMap = new ConcurrentHashMap<As, Integer>(DEFAULT_CAPACITY_MEDIUM);
		csMap = new ConcurrentHashMap<Cs, Integer>(DEFAULT_CAPACITY_MEDIUM);
		cmNodes = new ConcurrentSkipListSet<Integer>();

		camiPnmlIds = new ConcurrentHashMap<Integer, String>(
				DEFAULT_CAPACITY_HIGH);
		pnmlCamiIds = new ConcurrentHashMap<String, Integer>(
				DEFAULT_CAPACITY_HIGH);

		nodeKindMap = new ConcurrentHashMap<Integer, String>(
				DEFAULT_CAPACITY_HIGH);
		scNodeMap = new ConcurrentHashMap<Integer, Cs>(DEFAULT_CAPACITY_MEDIUM);

		csAsList = Collections.synchronizedList(new ArrayList<Command>(
				DEFAULT_CAPACITY_MEDIUM));

		isObjectSCMap = new ConcurrentHashMap<Integer, String>(
				DEFAULT_CAPACITY_LOW);
	}

	/**
	 * Constructor, initializes the repository maps according to the given
	 * numbers of commands of each kind to store.
	 * 
	 * @param nbCa
	 *            the number of arcs
	 * @param nbCm
	 *            the number of multiline textual attributes
	 * @param nbCn
	 *            the number of nodes
	 * @param nbCt
	 *            the number of single line textual attributes
	 * @param nbPi
	 *            the number of arc intermediate points
	 * @param nbPo
	 *            the number of node positions
	 * @param nbPt
	 *            the number of textual attribute positions
	 */
	public CamiRepositoryImpl(final int nbCa, final int nbCm, final int nbCn,
			final int nbCt, final int nbPi, final int nbPo, final int nbPt) {
		final int trois = 3;
		final int six = 6;
		caMap = new ConcurrentHashMap<Integer, Ca>(
				myRunner.optimalMapSize(nbCa));
		cmMap = new ConcurrentHashMap<Cm, Integer>(
				myRunner.optimalMapSize(nbCm));
		cmInvMap = new ConcurrentHashMap<Integer, List<Cm>>(
				myRunner.optimalMapSize(nbCm));
		cnMap = new ConcurrentHashMap<Integer, Cn>(
				myRunner.optimalMapSize(nbCn));
		placesMap = new ConcurrentHashMap<Integer, Cn>(
				myRunner.optimalMapSize(nbCn) / 2);
		transitionsMap = new ConcurrentHashMap<Integer, Cn>(
				myRunner.optimalMapSize(nbCn) / 2);
		ctMap = new ConcurrentHashMap<Ct, Integer>(
				myRunner.optimalMapSize(nbCt));
		ctInvMap = new ConcurrentHashMap<Integer, List<Ct>>(
				myRunner.optimalMapSize(nbCt));
		piMap = new ConcurrentHashMap<Pi, Integer>(
				myRunner.optimalMapSize(nbPi));
		piInvMap = new ConcurrentHashMap<Integer, List<Pi>>(
				myRunner.optimalMapSize(nbPi));
		poMap = new ConcurrentHashMap<Integer, Po>(
				myRunner.optimalMapSize(nbPo));
		ptMap = new ConcurrentHashMap<Pt, Integer>(
				myRunner.optimalMapSize(nbPt));
		ptInvMap = new ConcurrentHashMap<Integer, List<Pt>>(
				myRunner.optimalMapSize(nbPt));
		final int nbNode = nbCa + nbCn;
		cmNodes = new ConcurrentSkipListSet<Integer>();
		nodeKindMap = new ConcurrentHashMap<Integer, String>(
				myRunner.optimalMapSize(nbNode));
		scNodeMap = new ConcurrentHashMap<Integer, Cs>(
				myRunner.optimalMapSize(nbNode));
		asMap = new ConcurrentHashMap<As, Integer>(
				myRunner.optimalMapSize(trois * nbNode));
		csMap = new ConcurrentHashMap<Cs, Integer>(
				myRunner.optimalMapSize(trois * nbNode));
		csAsList = Collections.synchronizedList(new ArrayList<Command>(myRunner
				.optimalMapSize(six * nbNode)));
		isObjectSCMap = new ConcurrentHashMap<Integer, String>(
				myRunner.optimalMapSize(nbNode));
		camiPnmlIds = new ConcurrentHashMap<Integer, String>(
				DEFAULT_CAPACITY_HIGH);
		pnmlCamiIds = new ConcurrentHashMap<String, Integer>(
				DEFAULT_CAPACITY_HIGH);

	}

	/**
	 * Adds a new command to the Cami commands repository maps.
	 * 
	 * @param cmd
	 *            permit to add a command in Cami format
	 * 
	 * @throws CamiException
	 *             if the insertion was unsuccessful, especially if the command
	 *             was not recognized. <br>
	 *             NOTE: This methods doesn't allow re-insertion of CA and CN
	 *             commands with the same id, for they must be unique. Multiple
	 *             insertion of CM and PI commands is allowed. In that case, the
	 *             key is the command itself and the value the associated node
	 *             (or arc) id. A re-insertion of a Po, Pt or Ct command
	 *             overwrites the previous one with the same id. So beware of
	 *             endless loops !
	 * @see fr.lip6.move.pnml.cpnami.cami.CamiRepository#removeCommand(Command,
	 *      int)
	 * 
	 */
	public final synchronized void addCommand(final Command cmd)
			throws CamiException {
		switch (cmd.getCCIdentifier().getValue()) {
		case CAMICOMMANDS.CA:
			final Ca aCa = (Ca) cmd;
			final Integer arId = Integer.valueOf(aCa.getArcID());
			if (caMap.containsKey(arId)) {
				throw new AlreadyExistException(
						"CamiRepository#addCommand: CA command associated Id already exists.");
			}
			caMap.put(arId, aCa);
			nodeKindMap.put(arId, "arc");
			break;
		case CAMICOMMANDS.CM:
			final Cm aCm = (Cm) cmd;
			// there are multiple cm commands for the same node.
			final Integer assoNode = Integer.valueOf(aCm.getAssociatedNode());
			cmMap.put(aCm, assoNode);
			cmNodes.add(assoNode);
			if (aCm.getAttributeName().equalsIgnoreCase("valuation")
					|| aCm.getAttributeName().equalsIgnoreCase("marking")) {
				if (isAnnotationSc(aCm)) {
					final Integer nodeId = Integer.valueOf(aCm
							.getAssociatedNode());
					if (!isObjectSCMap.containsKey(nodeId)) {
						isObjectSCMap.put(nodeId, aCm.getValue());
					}
				}
			}
			List<Cm> cms;
			if (!cmInvMap.containsKey(assoNode)) {
				cms = Collections.synchronizedList(new ArrayList<Cm>());
				cmInvMap.put(assoNode, cms);
			} else {
				cms = cmInvMap.get(assoNode);
			}
			cms.add(aCm);
			break;
		case CAMICOMMANDS.CN:
			final Cn aCn = (Cn) cmd;
			final Integer nId = Integer.valueOf(aCn.getNodeId());
			if (cnMap.containsKey(nId)) {
				throw new AlreadyExistException(
						"CamiRepository#addCommand: CN command associated Id already exists.");
			}
			cnMap.put(nId, aCn);
			if (aCn.getNodeType().equalsIgnoreCase("place")) {
				placesMap.put(nId, aCn);
				nodeKindMap.put(nId, "place");
			} else if (aCn.getNodeType().equalsIgnoreCase("transition")) {
				transitionsMap.put(nId, aCn);
				nodeKindMap.put(nId, "transition");
			} else if (aCn.getNodeType().equalsIgnoreCase("net")) {
				nodeKindMap.put(nId, "net");
			}
			break;
		case CAMICOMMANDS.CT:
			final Ct aCt = (Ct) cmd;
			Integer anId = Integer.valueOf(aCt.getAssociatedNode());
			// there are often multiple CT commands of different attributes for
			// the same node, so in the map we store the Ct command as key.
			if (aCt.getAssociatedNode() == 1
					&& "name".equalsIgnoreCase(aCt.getAttributeName())) {
				netName = aCt.getAttributeName();
			} else {

				ctMap.put(aCt, anId);
				List<Ct> cts;
				if (!ctInvMap.containsKey(anId)) {
					cts = Collections.synchronizedList(new ArrayList<Ct>());
					ctInvMap.put(anId, cts);
				} else {
					cts = ctInvMap.get(anId);
				}
				cts.add(aCt);
			}
			if (aCt.getAttributeName().equalsIgnoreCase("valuation")
					|| aCt.getAttributeName().equalsIgnoreCase("marking")) {
				if (isAnnotationSc(aCt)) {
					isObjectSCMap.put(anId, aCt.getValue());
				}
			}
			break;
		case CAMICOMMANDS.CS:
			final Cs aCs = (Cs) cmd;
			final Integer nodeId = Integer.valueOf(aCs.getAssociatedNode());
			// stores the first root node of a semantic math annotation tree
			// associated to a net node.
			if (!scNodeMap.containsKey(nodeId) && aCs.getDescriptionNode() < 0) {
				scNodeMap.put(nodeId, aCs);
			}
			// there are multiple CS commands associated to the same node.
			csMap.put(aCs, nodeId);
			csAsList.add(aCs);
			break;
		case CAMICOMMANDS.AS:
			final As aAs = (As) cmd;
			// there are multiple AS commands associated to the same node.
			asMap.put(aAs, Integer.valueOf(aAs.getAssociatedNode()));
			csAsList.add(aAs);
			break;
		case CAMICOMMANDS.PI:
			final Pi aPi = (Pi) cmd;
			// there could be multiple intermediary positions for an arc
			Integer assArcId = Integer.valueOf(aPi.getNumObject());
			piMap.put(aPi, assArcId);
			List<Pi> pis;
			if (!piInvMap.containsKey(assArcId)) {
				pis = Collections.synchronizedList(new ArrayList<Pi>());
				piInvMap.put(assArcId, pis);
			} else {
				pis = piInvMap.get(assArcId);
			}
			pis.add(aPi);
			break;
		case CAMICOMMANDS.PO:
			final Po aPo = (Po) cmd;
			final Integer assoObj = Integer.valueOf(aPo.getNumObject());
			if (poMap.containsKey(assoObj)) {
				this.removeCommand(CAMICOMMANDS.PO_LITERAL, aPo.getNumObject());
			}
			poMap.put(assoObj, aPo);
			break;
		case CAMICOMMANDS.PT:
			final Pt aPt = (Pt) cmd;
			// there are often multiple PT commands of different attributes for
			// the same node.
			Integer assNodeId = Integer.valueOf(aPt.getNumObject());
			ptMap.put(aPt, assNodeId);
			List<Pt> pts;
			if (!ptInvMap.containsKey(assNodeId)) {
				pts = Collections.synchronizedList(new ArrayList<Pt>());
				ptInvMap.put(assNodeId, pts);
			} else {
				pts = ptInvMap.get(assNodeId);
			}
			pts.add(aPt);
			break;
		case CAMICOMMANDS.DB:
			// nothing to do...
			break;
		case CAMICOMMANDS.DE:
			// nothing to do...
			break;
		case CAMICOMMANDS.FB:
			// nothing to do...
			break;
		case CAMICOMMANDS.FE:
			// nothing to do...
			break;
		default:
			throw new CamiException(
					"CamiRepository#addCommand: Unrecognized cami command"
							+ cmd.getIdentifier());
		}
	}

	/**
	 * Removes command from the Cami commands repository maps.
	 * 
	 * @param kind
	 *            name of command
	 * @param objectId
	 *            id in the cami commands repository
	 * @throws CamiException
	 *             if the remove was unsuccessful, especially if the command was
	 *             not recognized. <br>
	 *             NOTE: The argument <em>kind</em> must have a concrete command
	 *             type (e.g., Pt). It is not required to have all members set.
	 *             The command type and the id suffice.
	 * @see fr.lip6.move.pnml.cpnami.cami.CamiRepository#addCommand(Command)
	 */
	public final void removeCommand(final CAMICOMMANDS kind, final int objectId)
			throws CamiException {
		final Integer oId = Integer.valueOf(objectId);
		switch (kind.getValue()) {
		case CAMICOMMANDS.CA:
			if (!caMap.isEmpty()) {
				caMap.remove(oId);
				nodeKindMap.remove(oId);
			}
			break;
		case CAMICOMMANDS.CN:
			if (!cnMap.isEmpty()) {
				cnMap.remove(oId);
				nodeKindMap.remove(oId);
				placesMap.remove(oId);
				transitionsMap.remove(oId);
			}
			break;
		case CAMICOMMANDS.CM:
			if (!cmMap.isEmpty()) {
				final Set<Entry<Cm, Integer>> mapValues = cmMap.entrySet();
				for (final Iterator<Entry<Cm, Integer>> iter = mapValues
						.iterator(); iter.hasNext();) {
					final Entry<Cm, Integer> anEnt = iter.next();
					final Integer anId = anEnt.getValue();
					if (anId.intValue() == objectId) {
						iter.remove();
					}
				}
			}
			cmInvMap.remove(Integer.valueOf(objectId));
			break;
		case CAMICOMMANDS.CT:
			if (!ctMap.isEmpty()) {
				final Set<Entry<Ct, Integer>> mapValues = ctMap.entrySet();
				Entry<Ct, Integer> anEnt;
				for (final Iterator<Entry<Ct, Integer>> iter = mapValues
						.iterator(); iter.hasNext();) {
					anEnt = iter.next();
					if (anEnt.getValue().intValue() == objectId) {
						iter.remove();
					}
				}
			}
			ctInvMap.remove(Integer.valueOf(objectId));
			break;
		case CAMICOMMANDS.PI:
			if (!piMap.isEmpty()) {
				final Set<Entry<Pi, Integer>> mapValues = piMap.entrySet();
				for (final Iterator<Entry<Pi, Integer>> iter = mapValues
						.iterator(); iter.hasNext();) {
					final Entry<Pi, Integer> anEnt = iter.next();
					final Integer anId = anEnt.getValue();
					if (anId.intValue() == objectId) {
						iter.remove();
					}
				}
			}
			break;
		case CAMICOMMANDS.PT:
			if (!ptMap.isEmpty()) {
				final Set<Entry<Pt, Integer>> mapValues = ptMap.entrySet();
				for (final Iterator<Entry<Pt, Integer>> iter = mapValues
						.iterator(); iter.hasNext();) {
					final Entry<Pt, Integer> anEnt = iter.next();
					final Integer anId = (Integer) anEnt.getValue();
					if (anId.intValue() == objectId) {
						iter.remove();
					}
				}
			}
			ptInvMap.remove(objectId);
			break;
		case CAMICOMMANDS.PO:
			if (!poMap.isEmpty()) {
				poMap.remove(oId);
			}
			break;
		case CAMICOMMANDS.DB:
			// nothing to do...
			break;
		case CAMICOMMANDS.DE:
			// nothing to do...
			break;
		case CAMICOMMANDS.FB:
			// nothing to do...
			break;
		case CAMICOMMANDS.FE:
			// nothing to do...
			break;
		case CAMICOMMANDS.CS:
			// nothing to do...
			break;
		case CAMICOMMANDS.AS:
			// nothing to do...
			break;
		default:
			throw new CamiException(
					"CamiRepository#removeCommand: Unrecognized cami command");
		}
		// TODO remove all CS and AS commands for a given node.
	}

	/**
	 * Removes all commands related to the given object id. That object might be
	 * a place, or a transition, or an arc
	 * 
	 * @param objectId
	 */
	@Override
	public final boolean removeObject(final int objectId) {
		boolean result = true;
		String objKind = getObjectKind(Integer.valueOf(objectId));
		try {
			if ("place".equalsIgnoreCase(objKind)
					|| "transition".equalsIgnoreCase(objKind)) {
				removeCommand(CAMICOMMANDS.CN_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.PO_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.CT_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.CM_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.PT_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.CS_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.AS_LITERAL, objectId);

			} else if ("arc".equalsIgnoreCase(objKind)) {
				removeCommand(CAMICOMMANDS.CA_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.PI_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.CT_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.CM_LITERAL, objectId);
				removeCommand(CAMICOMMANDS.PT_LITERAL, objectId);
			} else if ("net".equalsIgnoreCase(objKind)) {
				this.cleanRepository();
			} else {
				result = false;
			}
		} catch (CamiException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public final void removeAllPlaces() {
		List<Cn> places = getAllPlaces();
		int oId;
		Integer oIntId;
		for (Cn pl : places) {
			oId = pl.getNodeId();
			oIntId = Integer.valueOf(oId);
			cnMap.remove(oId);
			nodeKindMap.remove(oId);
			List<Cm> cms = cmInvMap.get(oIntId);
			if (cms != null) {
				for (Cm cm : cms) {
					cmMap.remove(cm);
				}
				cmInvMap.remove(oIntId);
			}
			List<Ct> cts = ctInvMap.get(oIntId);
			if (cts != null) {
				for (Ct ct : cts) {
					ctMap.remove(ct);
				}
				ctInvMap.remove(oIntId);
			}
			List<Pt> pts = ptInvMap.get(oIntId);
			if (pts != null) {
				for (Pt pt : pts) {
					ctMap.remove(pt);
				}
				ptInvMap.remove(oIntId);
			}
			poMap.remove(oId);
			cmNodes.remove(oIntId);
			Cs cs = scNodeMap.get(oIntId);
			if (cs != null) {
				csMap.remove(cs);
				csAsList.remove(cs);
			}
			scNodeMap.remove(oIntId);
			isObjectSCMap.remove(oIntId);
			placesMap.remove(oId);

		}
	}

	@Override
	public final void removeAllTransitions() {
		List<Cn> transitions = getAllTransitions();
		int oId;
		Integer oIntId;
		for (Cn tr : transitions) {
			oId = tr.getNodeId();
			oIntId = Integer.valueOf(oId);
			cnMap.remove(oId);
			nodeKindMap.remove(oId);
			List<Cm> cms = cmInvMap.get(oIntId);
			if (cms != null) {
				for (Cm cm : cms) {
					cmMap.remove(cm);
				}
				cmInvMap.remove(oIntId);
			}
			List<Ct> cts = ctInvMap.get(oIntId);
			if (cts != null) {
				for (Ct ct : cts) {
					ctMap.remove(ct);
				}
				ctInvMap.remove(oIntId);
			}
			List<Pt> pts = ptInvMap.get(oIntId);
			if (pts != null) {
				for (Pt pt : pts) {
					ptMap.remove(pt);
				}
				ptInvMap.remove(oIntId);
			}
			poMap.remove(oId);
			cmNodes.remove(oIntId);

			Cs cs = scNodeMap.get(oIntId);
			if (cs != null) {
				csMap.remove(cs);
				csAsList.remove(cs);
			}
			scNodeMap.remove(oIntId);
			transitionsMap.remove(oId);

		}
	}

	@Override
	public final void removeAllArcs() {
		List<Ca> arcs = getAllCa();
		int oId;
		Integer oIntId;
		for (Ca ar : arcs) {
			oId = ar.getArcID();
			oIntId = Integer.valueOf(oId);
			caMap.remove(oId);
			nodeKindMap.remove(oId);
			List<Cm> cms = cmInvMap.get(oIntId);
			if (cms != null) {
				for (Cm cm : cms) {
					cmMap.remove(cm);
				}
				cmInvMap.remove(oIntId);
			}
			List<Ct> cts = ctInvMap.get(oIntId);
			if (cts != null) {
				for (Ct ct : cts) {
					ctMap.remove(ct);
				}
				ctInvMap.remove(oIntId);
			}
			List<Pt> pts = ptInvMap.get(oIntId);
			if (pts != null) {
				for (Pt pt : pts) {
					ptMap.remove(pt);
				}
				ptInvMap.remove(oIntId);
			}
			List<Pi> pis = piInvMap.get(oIntId);
			if (pis != null) {
				for (Pi pi : pis) {
					piMap.remove(pi);
				}
				piInvMap.remove(oIntId);
			}
			cmNodes.remove(oIntId);
			Cs cs = scNodeMap.get(oIntId);
			if (cs != null) {
				csMap.remove(cs);
				csAsList.remove(cs);
			}
			scNodeMap.remove(oIntId);
			isObjectSCMap.remove(oIntId);
		}
	}

	/**
	 * Returns the list of all CA commands. A CA command corresponds to an arc.
	 * 
	 * @return the list of all CA commands, null if there is no such command.
	 */
	public final List<Ca> getAllCa() {
		List<Ca> allCa = null;
		if (!caMap.isEmpty()) {
			allCa = new ArrayList<Ca>(caMap.values());
		}
		return allCa;
	}

	/**
	 * Returns the list of multi line attribute command.
	 * 
	 * @return the liste of all Cm commands
	 */
	public final List<Cm> getAllCm() {
		List<Cm> allCm = null;
		if (!cmMap.isEmpty()) {
			allCm = new ArrayList<Cm>(cmMap.keySet());
		}
		return allCm;
	}

	/**
	 * Returns the list of node creation commands.
	 * 
	 * @return the list of all Cn commands.
	 */
	public final List<Cn> getAllCn() {
		List<Cn> allCn = null;
		if (!cnMap.isEmpty()) {
			allCn = new ArrayList<Cn>(cnMap.values());
		}
		return allCn;
	}

	/**
	 * Returns the list of single line attribute commands.
	 * 
	 * @return the list of all Ct commands.
	 */
	public final List<Ct> getAllCt() {
		List<Ct> allCt = null;
		if (!ctMap.isEmpty()) {
			allCt = new ArrayList<Ct>(ctMap.keySet());
		}
		return allCt;
	}

	/**
	 * Returns the list of intermediate points commands.
	 * 
	 * @return the list of all Pi commands.
	 */
	public final List<Pi> getAllPi() {
		List<Pi> allPi = null;
		if (!piMap.isEmpty()) {
			allPi = new ArrayList<Pi>(piMap.keySet());
		}
		return allPi;
	}

	/**
	 * Returns the list of node position commands.
	 * 
	 * @return the list of all Po commands.
	 */
	public final List<Po> getAllPo() {
		List<Po> allPo = null;
		if (!poMap.isEmpty()) {
			allPo = new ArrayList<Po>(poMap.values());
		}
		return allPo;
	}

	/**
	 * Returns the list of attribute position commands.
	 * 
	 * @return the list of all Pt commands.
	 */
	public final List<Pt> getAllPt() {
		List<Pt> allPt = null;
		if (!ptMap.isEmpty()) {
			allPt = new ArrayList<Pt>(ptMap.keySet());
		}
		return allPt;
	}

	/**
	 * Returns the number of arcs.
	 * 
	 * @return the number of all Ca commands.
	 */
	public final int getNumberOfCa() {
		return caMap.size();
	}

	/**
	 * Returns the number of multi line attribute commands.
	 * 
	 * @return the number of all Cm commands.
	 */
	public final int getNumberOfCm() {
		return cmMap.size();
	}

	/**
	 * Returns the number of nodes.
	 * 
	 * @return the number of all Cn commands.
	 */
	public final int getNumberOfCn() {
		return cnMap.size();
	}

	/**
	 * Returns the number of places.
	 * 
	 * @return the number of places.
	 */
	public final int getNumberOfPlaces() {

		return placesMap.size();
	}

	/**
	 * Returns the number of transitions.
	 * 
	 * @return the number of transitions.
	 */
	public final int getNumberOfTransitions() {
		return transitionsMap.size();
	}

	/**
	 * Returns the number of single line attribute commands.
	 * 
	 * @return the number of all Ct commands.
	 */
	public final int getNumberOfCt() {
		return ctMap.size();
	}

	/**
	 * Returns the number of intermediate point commands.
	 * 
	 * @return the number of all Pi commands.
	 */
	public final int getNumberOfPi() {
		return piMap.size();
	}

	/**
	 * Returns the number of node position commands.
	 * 
	 * @return the number of all Po commands.
	 */
	public final int getNumberOfPo() {
		return poMap.size();
	}

	/**
	 * Returns the number of attribute position commands.
	 * 
	 * @return the number of all Pt commands.
	 */
	public final int getNumberOfPt() {
		return ptMap.size();
	}

	/**
	 * <p>
	 * Resets all maps of the repository.
	 * </p>
	 * 
	 * <p>
	 * Invoking this method is equivalent to invoking:
	 * {@link #removeObject(int)} with the net node as argument (1 in this case)
	 * </p>
	 */
	public final void cleanRepository() {
		if (!caMap.isEmpty()) {
			caMap.clear();
		}
		if (!cmMap.isEmpty()) {
			cmMap.clear();
		}
		if (!cmInvMap.isEmpty()) {
			cmInvMap.clear();
		}
		if (!cnMap.isEmpty()) {
			cnMap.clear();
		}
		if (!csMap.isEmpty()) {
			csMap.clear();
		}
		if (!asMap.isEmpty()) {
			asMap.clear();
		}
		if (!ctMap.isEmpty()) {
			ctMap.clear();
		}
		if (!ctInvMap.isEmpty()) {
			ctInvMap.clear();
		}
		if (!piMap.isEmpty()) {
			piMap.clear();
		}
		if (!poMap.isEmpty()) {
			poMap.clear();
		}
		if (!ptMap.isEmpty()) {
			ptMap.clear();
		}
		if (!camiPnmlIds.isEmpty()) {
			camiPnmlIds.clear();
		}
		if (!pnmlCamiIds.isEmpty()) {
			pnmlCamiIds.clear();
		}
		if (!nodeKindMap.isEmpty()) {
			nodeKindMap.clear();
		}
		if (!scNodeMap.isEmpty()) {
			scNodeMap.clear();
		}
		if (!isObjectSCMap.isEmpty()) {
			isObjectSCMap.clear();
		}
	}

	@Override
	public void resetRepository() {
		this.cleanRepository();

		caMap = null;

		cmMap = null;

		cmInvMap = null;

		cnMap = null;

		csMap = null;

		ctMap = null;

		ctInvMap = null;

		piMap = null;

		poMap = null;

		ptMap = null;

		camiPnmlIds = null;

		pnmlCamiIds = null;

		nodeKindMap = null;

		scNodeMap = null;

		isObjectSCMap = null;
	}

	/**
	 * Returns the list of all places as Cn commands.
	 * 
	 * @return the list of all places.
	 */
	public final List<Cn> getAllPlaces() {
		List<Cn> allPlaces = null;
		if (!placesMap.isEmpty()) {
			allPlaces = new ArrayList<Cn>(placesMap.values());
		}
		return allPlaces;
	}

	/**
	 * Returns the list of all transitions as Cn commands.
	 * 
	 * @return the list of all transitions.
	 */
	public final List<Cn> getAllTransitions() {
		List<Cn> allTransitions = null;
		if (!transitionsMap.isEmpty()) {
			allTransitions = new ArrayList<Cn>(transitionsMap.values());
		}
		return allTransitions;
	}

	/**
	 * Returns node kind of the given id (i.e., "arc", "place", etc.).
	 * 
	 * @return the node kind.
	 * @param id
	 *            the node id.
	 */
	public final String getObjectKind(final Integer id) {
		return nodeKindMap.get(id);
	}

	/**
	 * Returns true if the specified node (by its id) has a symmetric net
	 * annotation. (SCNet).
	 * 
	 * @return true if the node is a SC command. False otherwise.
	 * @param nodeId
	 *            the node identifier.
	 */
	public final boolean isNodeSc(final Integer nodeId) {
		return scNodeMap.containsKey(nodeId);
	}

	/**
	 * Returns the list of Cs commands of a given node id.
	 * 
	 * @return the list of Cs commands of the node.
	 * @param nodeId
	 *            the node id.
	 */
	public final List<Cs> getNodeCs(final Integer nodeId) {
		List<Cs> result = new ArrayList<Cs>();

		if (csMap != null && csMap.size() != 0) {
			final Set<Cs> csKeys = csMap.keySet();
			for (final Cs aCs : csKeys) {
				final Integer nId = csMap.get(aCs);
				if (nId.equals(nodeId)) {
					result.add(aCs);
				}
			}
		}
		if (result.size() == 0) {
			result = null;
		}
		return result;
	}

	/**
	 * Returns the list of As commands of a given node id.
	 * 
	 * @return the list of As commands of the node.
	 * @param nodeId
	 *            the node id.
	 */
	public final List<As> getNodeAs(final Integer nodeId) {
		List<As> result = new ArrayList<As>();

		if (asMap != null && asMap.size() != 0) {
			final Set<As> asKeys = asMap.keySet();
			for (final As aAs : asKeys) {
				final Integer nId = asMap.get(aAs);
				if (nId.equals(nodeId)) {
					result.add(aAs);
				}
			}
		}
		if (result.size() == 0) {
			result = null;
		}
		return result;
	}

	/**
	 * Returns the root Cs command of a given node id.
	 * 
	 * @return the root Cs command of the node.
	 * @param nodeId
	 *            the node id.
	 */
	public final Cs getNodeRootCs(final Integer nodeId) {
		return scNodeMap.get(nodeId);
	}

	/**
	 * Tells if the net contained in the repository is a symmetric net.
	 * 
	 * @return true if this is a symmetric net.
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final boolean isNetSc() throws CamiException {
		boolean isCurrentNetSc = false;
		final int deux = 2;
		final int six = 6;
		final int vingt = 20;
		if (cnMap.size() != 0) {
			if (scNodeMap.size() != 0) {
				final Integer netnodeId = Integer.valueOf(1);
				final Cs aCs = scNodeMap.get(netnodeId);
				if (aCs != null) { // 2==CLASS, 6==DOMAIN, 20==VAR
					if (aCs.getDescNodeType() == deux
							|| aCs.getDescNodeType() == six
							|| aCs.getDescNodeType() == vingt) {
						isCurrentNetSc = true;
					}
				}
			}

		} else {
			throw new CamiException("There is no node in the cami repository");
		}
		return isCurrentNetSc;
	}

	/**
	 * Returns the list of Cs and As commands.
	 * 
	 * @return the list of Cs and As commands.
	 */
	public final List<Command> getAllCsAs() {
		return csAsList;
	}

	/**
	 * Returns the set of nodes that have multi line attributes (Cm commands).
	 * 
	 * @return a set of nodes identifiers. Those nodes have a multi line
	 *         attribute.
	 */
	public final Set<Integer> getCmNodes() {
		Set<Integer> allcmNodes = null;
		if (!cmNodes.isEmpty()) {
			allcmNodes = cmNodes;
		}
		return allcmNodes;
	}

	/**
	 * Returns the list of multi line attribute commands of a given node Id.
	 * 
	 * @return the list of Cm commands a given node has.
	 * @param nodeId
	 *            the node id.
	 */
	public final List<Cm> getCmOfNode(final Integer nodeId) {
		List<Cm> result = new ArrayList<Cm>();
		final List<Cm> allmyCm = getAllCm();
		if (allmyCm != null) {
			for (final Cm acm : allmyCm) {
				final Integer nId = cmMap.get(acm);
				if (nId.equals(nodeId)) {
					result.add(acm);
				}
			}
		}
		if (result.size() == 0) {
			result = null;
		}
		return result;
	}

	/**
	 * Return the node representing the net itself.
	 * 
	 * @return Cn the command representing the net node itself.
	 */
	public final Cn getNetNode() {
		return cnMap.get(Integer.valueOf(1));
	}

	/**
	 * Returns the Petri net model name.
	 * 
	 * @return the net name.
	 */
	public final String getNetName() {
		return netName;
	}

	/**
	 * Returns the position of a node.
	 * 
	 * @return Po the command representing the position of the node.
	 * @param nodeId
	 *            the node id.
	 */
	public final Po getNodePosition(final Integer nodeId) {
		return poMap.get(nodeId);
	}

	/**
	 * Returns the first intermediate position of an arc.
	 * 
	 * @return Pi the command representing the first intermediate position of
	 *         the arc.
	 * @param arcId
	 *            the arc id.
	 */
	public final Pi getArcPosition(final Integer arcId) {
		Pi myPi = null;
		final Set<Pi> piSet = piMap.keySet();
		if (piSet != null) {
			for (final Pi aPi : piSet) {
				if (arcId.equals(piMap.get(aPi))) {
					myPi = aPi;
				}
				break;
			}
		}
		return myPi;
	}

	/**
	 * Sets implicit arc valuations (i.e., 1) explicitly.
	 * 
	 * @throws CamiException
	 *             Cami exception
	 */
	public final void setImplicitPTInscriptions() throws CamiException {
		// first gets all the arcs
		final List<Ca> allArcs = this.getAllCa();
		if (allArcs != null) {
			for (final Ca aCa : allArcs) {
				final Integer arcId = Integer.valueOf(aCa.getArcID());
				// if (!this.ctMap.containsValue(arcId)) {
				if (!this.ctInvMap.containsKey(arcId)) {
					if (!this.isObjectAnnotationSc(arcId)) {
						final Ct newCt = ModelFactory.SINSTANCE.createCt();
						newCt.setCt("valuation", aCa.getArcID(), "1");
						this.ctMap.put(newCt, arcId);
						List<Ct> cts = Collections
								.synchronizedList(new ArrayList<Ct>());
						cts.add(newCt);
						this.ctInvMap.put(arcId, cts);
					}
				}
			}
		}
	}

	/**
	 * Determines if a given annotation is PT or SN.
	 * 
	 * @return true if the annotation is SN, false otherwise.
	 * @param aCommand
	 *            the command of the annotation.
	 */
	private boolean isAnnotationSc(final Command aCommand) {
		boolean result = false;
		if (aCommand != null) {
			if (aCommand instanceof Ct) {
				final Ct newCt = (Ct) aCommand;
				final String annotation = newCt.getValue();
				if (annotation.startsWith("<") && annotation.endsWith(">")) {
					result = true;
				}
			} else if (aCommand instanceof Cm) {
				final Cm newCm = (Cm) aCommand;
				final String annotation = newCm.getValue();
				if (annotation.startsWith("<") || annotation.endsWith(">")) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * Tells if the current Cami object has a symmetric net annotation.
	 * 
	 * @return true if the Cami object has a symmetric net annotation.
	 * @param objectId
	 *            the Cami object id.
	 */
	public final boolean isObjectAnnotationSc(final Integer objectId) {
		return this.isObjectSCMap.get(objectId) != null;
	}

	/**
	 * Visitor pattern, not used.
	 * 
	 * @param visitor
	 *            CommandVisitor class implementing the visitor.
	 * @deprecated
	 */
	public final void accept(final CommandVisitor visitor) {
		final Set<Entry<As, Integer>> asSet = asMap.entrySet();
		for (final Entry<As, Integer> i : asSet) {
			i.getKey().accept(visitor);
		}
		final Set<Entry<Integer, Ca>> caSet = caMap.entrySet();
		for (final Entry<Integer, Ca> i : caSet) {
			i.getValue().accept(visitor);
		}
		final Set<Cm> cmSet = cmMap.keySet();
		for (final Cm i : cmSet) {
			i.accept(visitor);
		}
		final Set<Entry<Integer, Cn>> cnSet = cnMap.entrySet();
		for (final Entry<Integer, Cn> i : cnSet) {
			i.getValue().accept(visitor);
		}
		final Set<Entry<Cs, Integer>> csSet = csMap.entrySet();
		for (final Entry<Cs, Integer> i : csSet) {
			i.getKey().accept(visitor);
		}
		final Set<Ct> ctSet = ctMap.keySet();
		for (final Ct i : ctSet) {
			i.accept(visitor);
		}
		final Set<Pi> piSet = piMap.keySet();
		for (final Pi i : piSet) {
			i.accept(visitor);
		}
		final Set<Entry<Integer, Po>> poSet = poMap.entrySet();
		for (final Entry<Integer, Po> i : poSet) {
			i.getValue().accept(visitor);
		}
		final Set<Pt> ptSet = ptMap.keySet();
		for (final Pt i : ptSet) {
			i.accept(visitor);
		}
	}

	/**
	 * Returns the valuation of a given arc id.
	 * 
	 * @return the name of the arc, whose id is the parameter.
	 * @param id
	 *            the identifier of the arc.
	 */
	public final String getArcInscription(final Integer id) {
		final StringBuffer inscription = new StringBuffer();
		if (ctInvMap.containsKey(id)) {
			List<Ct> cts = ctInvMap.get(id);
			for (final Ct ct : cts) {
				if ("valuation".equalsIgnoreCase(ct.getAttributeName())) {
					inscription.append(ct.getValue());
					break;
				}
			}
		} else if (cmInvMap.containsKey(id)) {
			List<Cm> cms = cmInvMap.get(id);
			for (final Cm cm : cms) {
				if ("valuation".equalsIgnoreCase(cm.getAttributeName())) {
					inscription.append(cm.getValue());
				}
			}
		}

		return inscription.length() != 0 ? inscription.toString() : null;
	}

	/**
	 * Returns the name of a given arc id.
	 * 
	 * @return the name of the arc, whose id is the parameter.
	 * @param id
	 *            the identifier of the arc.
	 */
	public final String getArcName(final Integer id) {
		/*
		 * Usually an arc does not have a name in CAMI formalism. Best effort
		 * search in ctMap only
		 */
		String arcName = null;
		/*
		 * if (caMap.containsKey(id)) { if (ctMap.containsValue(id)) { final
		 * Set<Ct> ctSet = ctMap.keySet(); for (final Ct ct : ctSet) { if
		 * (ct.getAssociatedNode() == id.intValue() &&
		 * "name".equalsIgnoreCase(ct.getAttributeName())) { arcName =
		 * ct.getValue(); // This is a Ct, not multi-line, so we can exit the
		 * loop break; } } } }
		 */
		List<Ct> cts = ctInvMap.get(id);
		if (cts != null) {
			for (final Ct ct : cts) {
				if ("name".equalsIgnoreCase(ct.getAttributeName())) {
					arcName = ct.getValue();
					break;
				}
			}
		}
		return arcName;
	}

	/**
	 * Returns the name of a given node id.
	 * 
	 * @return the name of the node, whose id is the parameter.
	 * @param id
	 *            the identifier of the node.
	 */
	public final String getNodeName(final Integer id) {
		/*
		 * Usually, a node name does not span across multiple lines. Best effort
		 * search in ctMap only.
		 */
		String nodeName = null;
		/*
		 * if (cnMap.containsKey(id)) { /*if (ctMap.containsValue(id)) { final
		 * Set<Ct> ctSet = ctMap.keySet(); for (final Ct ct : ctSet) { if
		 * (ct.getAssociatedNode() == id.intValue() &&
		 * "name".equalsIgnoreCase(ct.getAttributeName())) { nodeName =
		 * ct.getValue(); // This is a Ct, not multi-line, so we can exit the
		 * loop break; } } }
		 */

		List<Ct> cts = ctInvMap.get(id);
		for (final Ct ct : cts) {
			if ("name".equalsIgnoreCase(ct.getAttributeName())) {
				nodeName = ct.getValue();
				break;
			}
		}
		// }
		return nodeName;
	}

	/**
	 * Returns the marking of a given place id.
	 * 
	 * @return the marking of the place whose id is the parameter
	 * @param id
	 *            the id of the place
	 */
	public final String getPlaceMarking(final Integer id) {
		/* The marking may be contained in a simple Ct of multiple Cm */
		final StringBuffer placeMarking = new StringBuffer();

		if (ctInvMap.containsKey(id)) {
			List<Ct> cts = ctInvMap.get(id);
			for (final Ct ct : cts) {
				if ("marking".equalsIgnoreCase(ct.getAttributeName())) {
					placeMarking.append(ct.getValue());
					break;
				}
			}
		} else if (cmInvMap.containsKey(id)) {
			List<Cm> cms = cmInvMap.get(id);
			for (final Cm cm : cms) {
				if ("marking".equalsIgnoreCase(cm.getAttributeName())) {
					placeMarking.append(cm.getValue());
				}
			}
		}

		return placeMarking.length() != 0 ? placeMarking.toString() : null;
	}

	/**
	 * Returns the intermediate positions of an arc.
	 * 
	 * @param arcId
	 *            Cami arc id
	 * @return the list of this arc intermediate positions
	 */
	public final List<Pi> getArcPositions(final Integer arcId) {
		final List<Pi> arcPositions = piInvMap.get(arcId);
		/*
		 * final List<Pi> allPi = this.getAllPi(); if (allPi != null) { for
		 * (final Pi aPi : allPi) { if (aPi.getNumObject() == arcId.intValue())
		 * { arcPositions.add(aPi); } }
		 * 
		 * }
		 */
		// return arcPositions.isEmpty() ? null : arcPositions;
		// Concurrent maps do not allow null values
		return arcPositions;

	}

	/**
	 * Sets the name of the Petri net model stored in the Cami repository.
	 * 
	 * @param pnetName
	 *            the net name.
	 */
	public final void setNetName(final String pnetName) {
		if (pnetName != null) {
			this.netName = pnetName;
		}
	}

	/**
	 * Associates a Cami Id to its random-generated (XML compliant) Pnml
	 * equivalent.
	 * 
	 * @param camiId
	 *            the original Cami Id
	 * @param pnmlId
	 *            the random-generated Pnml Id.
	 */
	public final void relateCamiPnmlIds(final int camiId, final String pnmlId) {
		this.camiPnmlIds.put(Integer.valueOf(camiId), pnmlId);
	}

	/**
	 * Returns the associated random-generated Pnml Id to this Cami Id.
	 * 
	 * @param camiId
	 *            the Cami Id to which a Pnml Id is associated.
	 * @return the corresponding associated Pnml Id.
	 */
	public final String getPnmlIdOfCamiId(final int camiId) {
		return this.camiPnmlIds.get(Integer.valueOf(camiId));
	}

	/**
	 * Returns the associated random-generated Cami Id to this Pnml Id.
	 * 
	 * @param pnmlId
	 *            the Pnml Id to which a Cami Id is associated.
	 * @return the corresponding associated Cami Id.
	 */
	public final Integer getCamiIdOfPnmlId(final String pnmlId) {
		final Integer camiID = this.pnmlCamiIds.get(pnmlId);
		return camiID != null ? camiID.intValue() : null;
	}

	/**
	 * Associates a Pnml Id to its random-generated (Integer compliant) Cami
	 * equivalent. Null values are accepted, so you should check what you get
	 * before putting them in the repository.
	 * 
	 * @param camiId
	 *            the random-generated Cami Id
	 * @param pnmlId
	 *            the original Pnml Id.
	 */
	public final void relatePnmlCamiIds(final String pnmlId, final int camiId) {
		this.pnmlCamiIds.put(pnmlId, Integer.valueOf(camiId));
	}

	@Override
	public Pt getMarkingPositionOfNode(int nodeId) {
		List<Pt> pts = ptInvMap.get(Integer.valueOf(nodeId));
		Pt res = null;
		if (pts != null) {
			for (Pt aPt : pts) {
				if ("marking".equalsIgnoreCase(aPt.getAttributeName())) {
					res = aPt;
					break;
				}
			}
		}
		return res;
	}

	@Override
	public Pt getNamePositionOfNode(int nodeId) {
		List<Pt> pts = ptInvMap.get(Integer.valueOf(nodeId));
		Pt res = null;
		if (pts != null) {
			for (Pt aPt : pts) {
				if ("name".equalsIgnoreCase(aPt.getAttributeName())) {
					res = aPt;
					break;
				}
			}
		}
		return res;
	}

	@Override
	public Pt getValuationPosition(int arcID) {
		List<Pt> pts = ptInvMap.get(Integer.valueOf(arcID));
		Pt res = null;
		if (pts != null) {
			for (Pt aPt : pts) {
				if ("valuation".equalsIgnoreCase(aPt.getAttributeName())) {
					res = aPt;
					break;
				}
			}
		}
		return res;
	}
}
