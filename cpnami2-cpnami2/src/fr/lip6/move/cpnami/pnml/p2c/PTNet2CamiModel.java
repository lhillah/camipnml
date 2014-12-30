/**
 *  Copyright 2009-2015 Universite Paris Ouest and Sorbonne Universites,
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
package fr.lip6.move.cpnami.pnml.p2c;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiRepositoryImpl;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.ModelFactory;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.cpnami.exceptions.CircularReferenceException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;
import fr.lip6.move.pnml.ptnet.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PageHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PlaceNodeHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PositionHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.RefPlaceHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.RefTransitionHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.TransitionHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.TransitionNodeHLAPI;

/**
 * Builds a CAMI PT Net model from a PNML model.
 */
public final class PTNet2CamiModel {
	/**
	 * Create an instance of Log object name parameter; used to know who is
	 * calling the logger like "import" or "export".
	 */
	private static final Log JOURNAL = LogMaster.giveLogger(PTNet2CamiModel.class.getCanonicalName());
	/**
	 * Cami Model Factory initialization.
	 */
	private static final ModelFactory MF = ModelFactory.SINSTANCE;
	/**
	 * Starting count of Cami Ids.
	 */
	private static final int CAMI_ID_START = 149;
	/**
	 * Random Id starting point in case the PNML nodes String ids could not be
	 * directly converted into integers for CAMI nodes ids.
	 */
	private int camiRandomId = CAMI_ID_START;

	/**
	 * Converts the PNML PT model into a Cami PT model in the Cami repository.
	 * 
	 * @param cr
	 *            the Cami repository that will hold the Cami PT model.
	 * @param netElement
	 *            the PNML PT net.
	 * @throws CamiException
	 *             Cami exception.
	 */
	public final void buildCamiModel(final CamiRepository cr, final PetriNetHLAPI netElement) throws CamiException {
		try {
			buildNet(cr, netElement);
		} catch (final CamiException ce) {
			JOURNAL.error(ce.getMessage());
			throw new CamiException(ce.getMessage());
		}
	}

	/**
	 * Populates a Cami repository with the net global information.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param netElement
	 *            the PNML PetriNet node.
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildNet(CamiRepository cr, final PetriNetHLAPI netElement) throws CamiException {

		if (netElement != null) {
			try {
				
				String netName = null;
				if (netElement.getNameHLAPI() != null)
					netName = netElement.getNameHLAPI().getText();
				if (netName == null) {
					netName = "defaultCamiNetName";
				}
				if (cr == null) {
					JOURNAL.info("#BuildNet: " + "Creating a new Cami repository.");
					cr = new CamiRepositoryImpl();
				}
				// Sets the net id. In Cami, it is always 1.
				final Cn aCn = MF.createCn();
				aCn.setCn("net", 1);
				cr.addCommand(aCn);
				// Sets the Cami net name.
				final Ct aCt = MF.createCt();
				aCt.setCt("name", 1, netName);
				cr.addCommand(aCt);
				// Builds pages
				for (final PageHLAPI page : netElement.getPagesHLAPI()) {
					buildPage(cr, page);
				}
			} catch (final CamiException ce) {
				throw new CamiException(ce.getMessage());
			}
		} else {
			throw new CamiException("BuildNet: the PNML Petri net is null.");
		}
	}

	/**
	 * Builds a Cami page from a PNML PT page. Actually, just iterate over page
	 * contents.
	 * 
	 * @param cr
	 *            the Cami repository.
	 * @param page
	 *            the PNML PT page.
	 * @throws CamiException
	 *             Cami exception.
	 */
	private void buildPage(final CamiRepository cr, final PageHLAPI page) throws CamiException {
		// We don't support pages in Cami in this context.
		// So we just pass.
		try {
			for (final PageHLAPI innerPage : page.getObjects_PageHLAPI()) {
				buildPage(cr, innerPage);
			}
			for (final PlaceHLAPI place : page.getObjects_PlaceHLAPI()) {
				buildPlace(cr, place);
			}
			for (final TransitionHLAPI transition : page.getObjects_TransitionHLAPI()) {
				buildTransition(cr, transition);
			}
			for (final RefPlaceHLAPI refPlace : page.getObjects_RefPlaceHLAPI()) {
				buildRefPlace(cr, refPlace);
			}
			for (final RefTransitionHLAPI refTransition : page.getObjects_RefTransitionHLAPI()) {
				buildRefTransition(cr, refTransition);
			}
			for (final ArcHLAPI arc : page.getObjects_ArcHLAPI()) {
				buildArc(cr, arc);
			}
		} catch (final CamiException ce) {
			throw ce;
		}
	}

	/**
	 * Buidls a Cami arc from a PNML arc.
	 * 
	 * @param cr
	 *            the Cami repository
	 * @param arc
	 *            the PNML arc.
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildArc(final CamiRepository cr, final ArcHLAPI arc) throws CamiException {
		try {
			// The arc basic info
			final Ca camiArc = MF.createCa();
			final int arcID = createCamiId(arc.getId(), false, cr);
			final Integer sourceId = cr.getCamiIdOfPnmlId(arc.getSourceHLAPI().getId());
			final Integer targetId = cr.getCamiIdOfPnmlId(arc.getTargetHLAPI().getId());
			if (sourceId != null && targetId != null) {
				camiArc.setCa("arc", arcID, sourceId.intValue(), targetId.intValue());
			} else if (sourceId != null) {
				// build the target node before building the cami command for
				// the arc.
				final NodeHLAPI targetNode = buildArcTarget(cr, arc);
				camiArc.setCa("arc", arcID, sourceId.intValue(), cr.getCamiIdOfPnmlId(targetNode.getId()).intValue());
			} else if (targetId != null) {
				// build the source node before building the cami command for
				// the arc.
				final NodeHLAPI sourceNode = buildArcSource(cr, arc);
				camiArc.setCa("arc", arcID, cr.getCamiIdOfPnmlId(sourceNode.getId()).intValue(), targetId.intValue());
			} else {
				// build both
				final NodeHLAPI targetNode = buildArcTarget(cr, arc);
				final NodeHLAPI sourceNode = buildArcSource(cr, arc);
				camiArc.setCa("arc", arcID, cr.getCamiIdOfPnmlId(sourceNode.getId()).intValue(), cr.getCamiIdOfPnmlId(targetNode.getId()).intValue());
			}
			cr.addCommand(camiArc);
			buildArcInflexionPoints(cr, arc, arcID);
			buildArcInscription(cr, arc, arcID);
		} catch (final CamiException ce) {
			throw ce;
		}

	}

	/**
	 * Builds an arc valuation into the Cami repository.
	 * 
	 * @param cr
	 *            the Cami repository
	 * @param arc
	 *            the arc in PNML
	 * @param arcID
	 *            the arc Id in Cami
	 * @throws CamiException
	 *             something went wrong when creating the arc inscription
	 */
	private void buildArcInscription(CamiRepository cr, ArcHLAPI arc, int arcID) throws CamiException {
		if (arc.getInscriptionHLAPI() != null) {
			final Ct arcVal = MF.createCt();
			final Integer arcInsc = arc.getInscriptionHLAPI().getText();
			arcVal.setCt("valuation", arcID, arcInsc != null ? arcInsc.toString() : "1");
			cr.addCommand(arcVal);
		}
	}

	/**
	 * Builds an arc bend points into the Cami repository.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param arc
	 *            the arc in PNML
	 * @param arcID
	 *            the arc Id in Cami
	 * @throws CamiException
	 *             something went wrong when creating the bend points.
	 */
	private void buildArcInflexionPoints(final CamiRepository cr, final ArcHLAPI arc, final int arcID) throws CamiException {
		if (arc.getArcgraphicsHLAPI() != null) {
			for (PositionHLAPI arcBP : arc.getArcgraphicsHLAPI().getPositionsHLAPI()) {
				final Pi arcPi = MF.createPi();
				arcPi.setPi(arcID, arcBP.getX(), arcBP.getY());
				cr.addCommand(arcPi);
			}
		}
	}

	/**
	 * Builds the Cami Node which is the target of a PNML arc.
	 * 
	 * @param cr
	 *            Cami Repository
	 * @param arc
	 *            the PNML arc
	 * @return the PNML Node which is the target of this arc.
	 * @throws CamiException
	 *             Cami Exception
	 */
	private NodeHLAPI buildArcTarget(final CamiRepository cr, final ArcHLAPI arc) throws CamiException {
		final NodeHLAPI targetNode = arc.getTargetHLAPI();
		try {
			if (targetNode instanceof TransitionHLAPI) {

				buildTransition(cr, (TransitionHLAPI) targetNode);

			} else {
				buildPlace(cr, (PlaceHLAPI) targetNode);
			}
		} catch (final CamiException ce) {
			throw ce;
		}
		return targetNode;
	}

	/**
	 * Builds the Cami Node which is the source of a PNML arc.
	 * 
	 * @param cr
	 *            Cami Repository
	 * @param arc
	 *            the PNML arc
	 * @return the PNML Node which is the source of this arc.
	 * @throws CamiException
	 *             Cami Exception
	 */
	private NodeHLAPI buildArcSource(final CamiRepository cr, final ArcHLAPI arc) throws CamiException {
		final NodeHLAPI sourceNode = arc.getTargetHLAPI();
		try {
			if (sourceNode instanceof TransitionHLAPI) {
				buildTransition(cr, (TransitionHLAPI) sourceNode);
			} else {
				buildPlace(cr, (PlaceHLAPI) sourceNode);
			}
		} catch (final CamiException ce) {
			throw ce;
		}
		return sourceNode;
	}

	/**
	 * Builds a Cami place in the repository from a PNML PT place.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param place
	 *            the PNML PT place
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildPlace(final CamiRepository cr, final PlaceHLAPI place) throws CamiException {

		try {
			// The place basic info
			final Cn camiPlace = MF.createCn();
			final int placeID = createCamiId(place.getId(), false, cr);
			camiPlace.setCn("place", placeID);
			cr.addCommand(camiPlace);
			// Its position
			if (place.getNodegraphicsHLAPI() != null && place.getNodegraphicsHLAPI().getPositionHLAPI() != null) {
				final Po placePosition = MF.createPo();
				placePosition.setPo(placeID, place.getNodegraphicsHLAPI().getPositionHLAPI().getX(), place.getNodegraphicsHLAPI().getPositionHLAPI().getY());
				cr.addCommand(placePosition);
			}
			// its name
			buildPlaceName(cr, place, placeID);
			// its marking
			buildPlaceMarking(cr, place, placeID);

		} catch (final CamiException ce) {
			if (!(ce instanceof CamiRepositoryImpl.AlreadyExistException)) {
				throw new CamiException(ce.getMessage());
			}
		}
	}

	/**
	 * Builds a Cami place name from a PNML place name.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param place
	 *            the PNML place
	 * @param placeID
	 *            the Cami place ID.
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildPlaceName(final CamiRepository cr, final PlaceHLAPI place, final int placeID) throws CamiException {

		try {
			
			// its name
			if (place.getNameHLAPI() != null) {
				final Ct placeName = MF.createCt();
				placeName.setCt("name", placeID, place.getNameHLAPI().getText());
				cr.addCommand(placeName);
			} else {
				final Ct placeName = MF.createCt();
				placeName.setCt("name", placeID, place.getId());
				cr.addCommand(placeName);
			}
			// its name offset
			if (place.getNameHLAPI() != null && place.getNameHLAPI().getAnnotationgraphicsHLAPI() != null
					&& place.getNameHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI() != null) {
				if (place.getNodegraphicsHLAPI() != null && place.getNodegraphicsHLAPI().getPositionHLAPI() != null) {
					final Pt namePosition = MF.createPt();
					namePosition.setPt(placeID, "name", place.getNameHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI().getX()
							+ place.getNodegraphicsHLAPI().getPositionHLAPI().getX(), place.getNameHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI().getY()
							+ place.getNodegraphicsHLAPI().getPositionHLAPI().getY());
					cr.addCommand(namePosition);
				}
			}
		} catch (final CamiException ce) {

			throw new CamiException(ce.getMessage());
		}
	}

	/**
	 * Builds a Cami place marking from a PNML place marking.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param place
	 *            the PNML place
	 * @param placeID
	 *            the Cami place ID.
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildPlaceMarking(final CamiRepository cr, final PlaceHLAPI place, final int placeID) throws CamiException {
		// its marking
		if (place.getInitialMarkingHLAPI() != null) {
			final Ct placeMarking = MF.createCt();
			placeMarking.setCt("marking", placeID, String.valueOf(place.getInitialMarkingHLAPI().getText()));
			cr.addCommand(placeMarking);
		}
		// marking offset
		if (place.getInitialMarkingHLAPI() != null && place.getInitialMarkingHLAPI().getAnnotationgraphicsHLAPI() != null
				&& place.getInitialMarkingHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI() != null) {
			if (place.getNodegraphicsHLAPI() != null && place.getNodegraphicsHLAPI().getPositionHLAPI() != null) {
				final Pt markingPosition = MF.createPt();
				markingPosition.setPt(placeID, "marking", place.getInitialMarkingHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI().getX()
						+ place.getNodegraphicsHLAPI().getPositionHLAPI().getX(), place.getInitialMarkingHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI()
						.getY()
						+ place.getNodegraphicsHLAPI().getPositionHLAPI().getY());
				cr.addCommand(markingPosition);
			}
		}
	}

	/**
	 * Buid a Cami transition from a PNML PT transition.
	 * 
	 * @param cr
	 *            the cami repository of the Cami model being uilt
	 * @param transition
	 *            the PNML transition
	 * @throws CamiException
	 *             CamiException.
	 */
	private void buildTransition(final CamiRepository cr, final TransitionHLAPI transition) throws CamiException {
		try {
			// The transition basic info
			final Cn camiTransition = MF.createCn();
			final int transitionID = createCamiId(transition.getId(), false, cr);
			camiTransition.setCn("transition", transitionID);
			cr.addCommand(camiTransition);
			// Its position
			if (transition.getNodegraphicsHLAPI() != null && transition.getNodegraphicsHLAPI().getPositionHLAPI() != null) {
				final Po trPosition = MF.createPo();
				trPosition.setPo(transitionID, transition.getNodegraphicsHLAPI().getPositionHLAPI().getX(), transition.getNodegraphicsHLAPI()
						.getPositionHLAPI().getY());
				cr.addCommand(trPosition);
			}
			// its name
			buildTransitionName(cr, transition, transitionID);
		} catch (final CamiException ce) {
			if (!(ce instanceof CamiRepositoryImpl.AlreadyExistException)) {
				throw new CamiException(ce.getMessage());
			}
		}
	}

	/**
	 * Builds a Cami transition name from a PNML transition Name.
	 * 
	 * @param cr
	 *            Cami repository
	 * @param transition
	 *            the PNML transition
	 * @param transitionID
	 *            the Cami transition Id
	 * @throws CamiException
	 *             Cami exception
	 */
	private void buildTransitionName(final CamiRepository cr, final TransitionHLAPI transition, final int transitionID) throws CamiException {
		try {
			// its name
			if (transition.getNameHLAPI() != null) {
				final Ct transitionName = MF.createCt();
				transitionName.setCt("name", transitionID, transition.getNameHLAPI().getText());
				cr.addCommand(transitionName);
			} else {
				final Ct transitionName = MF.createCt();
				transitionName.setCt("name", transitionID, transition.getId());
				cr.addCommand(transitionName);
			}
			// its name offset
			if (transition.getNameHLAPI() != null && transition.getNameHLAPI().getAnnotationgraphicsHLAPI() != null
					&& transition.getNameHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI() != null) {
				if (transition.getNodegraphicsHLAPI() != null && transition.getNodegraphicsHLAPI().getPositionHLAPI() != null) {
					final Pt namePosition = MF.createPt();
					namePosition.setPt(transitionID, "name", transition.getNameHLAPI().getAnnotationgraphicsHLAPI().getOffsetHLAPI().getX()
							+ transition.getNodegraphicsHLAPI().getPositionHLAPI().getX(), transition.getNameHLAPI().getAnnotationgraphicsHLAPI()
							.getOffsetHLAPI().getY()
							+ transition.getNodegraphicsHLAPI().getPositionHLAPI().getY());
					cr.addCommand(namePosition);
				}
			}
		} catch (final CamiException ce) {

			throw new CamiException(ce.getMessage());
		}
	}

	/**
	 * Builds a Cami place from a PNML RefPlace. Since we do not support
	 * reference places in Cami, the RefPlace should be fused with the original
	 * place it is referencing. Actually we just determine if we have already
	 * built the original place, otherwise we just build it.
	 * 
	 * @param cr
	 *            the Cami Repository
	 * @param refPlace
	 *            the PNML reference place
	 * @throws CamiException
	 *             Cami Exception
	 */
	private void buildRefPlace(final CamiRepository cr, final RefPlaceHLAPI refPlace) throws CamiException {
		try {
			final PlaceHLAPI originalNode = determineReferencedPlace(refPlace);
			if (originalNode != null) {
				if (cr.getCamiIdOfPnmlId(originalNode.getId()) == null) {
					buildPlace(cr, originalNode);
				}
			} else {
				throw new CamiException("PTNet2CamiModel#buildRefPlace: original node not found.");
			}
		} catch (final CircularReferenceException cre) {
			throw new CamiException("PTNet2CamiModel#buildRefPlace: caught circular reference for node " + refPlace.getId());
		}
	}

	/**
	 * Builds a Cami transition from a PNML RefTransition. Since we do not
	 * support reference transition in Cami, the RefTransition should be fused
	 * with the original transition it is referencing. Actually we just
	 * determine if we have already built the original transition, otherwise we
	 * just build it.
	 * 
	 * @param cr
	 *            the Cami Repository
	 * @param refTransition
	 *            the reference transition
	 * @throws CamiException
	 *             Cami exception.
	 */
	private void buildRefTransition(final CamiRepository cr, final RefTransitionHLAPI refTransition) throws CamiException {
		try {
			final TransitionHLAPI originalNode = determineReferencedTransition(refTransition);
			if (originalNode != null) {
				if (cr.getCamiIdOfPnmlId(originalNode.getId()) == null) {
					buildTransition(cr, originalNode);
				}
			} else {
				throw new CamiException("PTNet2CamiModel#buildRefTransition: original node not found.");
			}
		} catch (final CircularReferenceException cre) {
			throw new CamiException("PTNet2CamiModel#buildRefTransition: caught circular reference for node " + refTransition.getId());
		}
	}

	/**
	 * Since ref places may reference ref places or places, we need to determine
	 * the original referenced place id. The same consideration applies for
	 * reference transitions.
	 * 
	 * @throws CircularReferenceException
	 *             if there is a circular reference among the reference nodes.
	 * @param refPlace
	 *            the reference place for which we are looking for the original
	 *            node.
	 * @return PlaceHLAPI the original node, null if not any found.
	 */
	private PlaceHLAPI determineReferencedPlace(final RefPlaceHLAPI refPlace) throws CircularReferenceException {
		PlaceNodeHLAPI originalNode = null;
		originalNode = refPlace.getRefHLAPI();
		while (originalNode != null && !(originalNode instanceof PlaceHLAPI)) {
			final RefPlaceHLAPI nextRefPlace = (RefPlaceHLAPI) originalNode;
			originalNode = nextRefPlace.getRefHLAPI();
			// Avoiding circular refs.
			if (originalNode.equals(refPlace)) {
				throw new CircularReferenceException();
			}
		}
		return originalNode != null ? (PlaceHLAPI) originalNode : null;
	}

	/**
	 * Since ref transitions may reference ref transitions or places, we need to
	 * determine the original referenced transition id. The same consideration
	 * applies for reference places.
	 * 
	 * @throws CircularReferenceException
	 *             if there is a circular reference among the reference nodes.
	 * @param refTransition
	 *            the reference transition for which we are looking for the
	 *            original node.
	 * @return TransisitionHLAPI the original node, null if not any found.
	 */
	private TransitionHLAPI determineReferencedTransition(final RefTransitionHLAPI refTransition) throws CircularReferenceException {
		TransitionNodeHLAPI originalNode = null;
		originalNode = refTransition.getRefHLAPI();
		while (originalNode != null && !(originalNode instanceof TransitionHLAPI)) {
			final RefTransitionHLAPI nextRefTransition = (RefTransitionHLAPI) originalNode;
			originalNode = nextRefTransition.getRefHLAPI();
			// Avoiding circular refs.
			if (originalNode.equals(refTransition)) {
				throw new CircularReferenceException();
			}
		}
		return originalNode != null ? (TransitionHLAPI) originalNode : null;
	}

	/**
	 * Determines the corresponding Cami id (int) for the given PNML node id
	 * (String) If it cannot, generates a random number. It associates the
	 * generated Cami ID to the PNML Id it was generated for.
	 * 
	 * @param id
	 *            the node id.
	 * @param arcExtremity
	 *            indicates if the node is an arc extremity, in that case its
	 *            Cami id must have already been registered. It will be
	 *            irrelevant and incoherent to create a new one. Set this to
	 *            true when you are dealing with arcs after having processed all
	 *            nodes.
	 * @param cr
	 *            Cami repository
	 * @return the corresponding Cami id as a positive natural for the given
	 *         PNML node id.
	 */
	private int createCamiId(final String id, final boolean arcExtremity, final CamiRepository cr) {
		final int moinssixcent = -600;
		int result = moinssixcent;
		try {
			result = Integer.parseInt(id);
		} catch (final NumberFormatException nfe) {
			// determines a random id for the Cami object
			if (arcExtremity) {
				final Integer extremityId = Integer.valueOf(cr.getCamiIdOfPnmlId(id));
				if (extremityId != null) {
					result = extremityId.intValue();
					// else there is a serious problem...
				}
			} else {
				result = camiRandomId++;
				cr.relatePnmlCamiIds(id, result);
			}
		}
		return result;
	}

}
