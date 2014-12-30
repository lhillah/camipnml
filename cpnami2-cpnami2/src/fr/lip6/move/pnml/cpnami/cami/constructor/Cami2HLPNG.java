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
package fr.lip6.move.pnml.cpnami.cami.constructor;

import java.util.List;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.utils.IdRepository;
import fr.lip6.move.pnml.framework.utils.ModelRepository;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.AnnotationGraphicsHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.ArcGraphicsHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.NameHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.NodeGraphicsHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.OffsetHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PNTypeHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PageHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.PositionHLAPI;
import fr.lip6.move.pnml.hlpn.hlcorestructure.hlapi.TransitionHLAPI;

/**
 * Converts PN models from Cami into PNML High-Level Petri Net Graphs (HLPN).
 * 
 * @author lom
 * 
 */
public class Cami2HLPNG {

	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * The singleton Pnml model repository that is being used by Cami2Pnml.
	 */
	private static ModelRepository pnmlMr;
	/**
	 * The HLPNG net being constructed.
	 */
	private PetriNetHLAPI net;
	/**
	 * The default page of this net.
	 */
	private PageHLAPI defPage;

	/**
	 * Constructor.
	 */
	public Cami2HLPNG() {
		super();
	}

	/**
	 * Returns the constructed HLPNG Petri net.
	 * 
	 * @return PetriNetHLAPI the constructed HLPNG net.
	 */
	public final PetriNetHLAPI getNet() {
		return this.net;
	}

	/**
	 * Builds the net node with its name and type found in Cami repository.
	 * 
	 * @param cr
	 *            Cami repository
	 * @throws CamiException
	 *             CamiException
	 */
	public final void setNet(final CamiRepository cr) throws CamiException {
		String netName = cr.getNetName();
		if (!(netName != null)) {
			netName = "DefaultNetName";
		}
		try {
			final String myId = myRunner.transformCamiId2XmlId(1);
			cr.relateCamiPnmlIds(1, myId);
			this.net = new PetriNetHLAPI(myId, PNTypeHLAPI.PTNET);
			this.net.setNameHLAPI(new NameHLAPI(netName));
			this.setDefaultPage();
		} catch (final InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
	}

	/**
	 * Sets the default page.
	 * 
	 * @throws CamiException
	 *             CamiException
	 */
	private void setDefaultPage() throws CamiException {
		try {
			this.defPage = new PageHLAPI("page0", new NameHLAPI("DefaultPage"), null);
			this.defPage.setContainerPetriNetHLAPI(this.net);
		} catch (final InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}

	}

	/**
	 * Construction of PNML places from CAMI ones.
	 * 
	 * @param cr
	 *            Cami Repository which collects all Cami objects
	 * @throws CamiException
	 *             CamiException
	 */
	public final void setPlaces(final CamiRepository cr) throws CamiException {

		// Populates places ids ( Cn commands)
		final List<Cn> allPl = cr.getAllPlaces();
		Po nodePosition = null;
		try {
			if (allPl != null) {
				// we look for all Places existing in our model
				for (final Cn aPl : allPl) {
					final String nId = myRunner.transformCamiId2XmlId(aPl.getNodeId());
					cr.relateCamiPnmlIds(aPl.getNodeId(), nId);
					nodePosition = cr.getNodePosition(aPl.getNodeId());
					final PlaceHLAPI placeNode = new PlaceHLAPI(nId);
					final NodeGraphicsHLAPI placeGraphics = new NodeGraphicsHLAPI(new PositionHLAPI(Integer.valueOf(nodePosition.getH()), Integer
							.valueOf(nodePosition.getV())), null, null, null);
					placeNode.setNodegraphicsHLAPI(placeGraphics);
					// sets place name
					setPlaceName(placeNode, aPl, nodePosition, cr);
					setPlaceMarking(placeNode, aPl, nodePosition, cr);
					defPage.addObjectsHLAPI(placeNode);
				}
			}
		} catch (final InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
	}

	/**
	 * Sets a place name and the graphics of the name in PNML model.
	 * 
	 * @param placeNode
	 *            the place in the PNML model
	 * @param pl
	 *            the place in Cami
	 * @param placePosition
	 *            the place position in Cami
	 * @param cr
	 *            Cami repository
	 */
	private void setPlaceName(final PlaceHLAPI placeNode, final Cn pl, final Po placePosition, final CamiRepository cr) {
		final List<Pt> allPt = cr.getAllPt();
		Integer offX = null;
		Integer offY = null;
		final String nodeName = cr.getNodeName(pl.getNodeId());
		if (nodeName != null) {
			final NameHLAPI nodeNameHLAPI = new NameHLAPI(nodeName);
			placeNode.setNameHLAPI(nodeNameHLAPI);
			// Graphics of the name: offset of the place position.
			for (final Pt aPt : allPt) {
				if (aPt.getNumObject() == pl.getNodeId()) {
					if ("name".equalsIgnoreCase(aPt.getAttributeName())) {
						if (placePosition != null) {
							offX = Integer.valueOf(placePosition.getH() - aPt.getH());
							offY = Integer.valueOf(placePosition.getV() - aPt.getV());
							if (offX != null && offY != null) {
								final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
								final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(myOffset, null, null, null);
								nodeNameHLAPI.setAnnotationgraphicsHLAPI(myAnnotation);
							}
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Sets the marking (and its graphics) of the place in the PNML model.
	 * 
	 * @param placeNode
	 *            the place in PNML
	 * @param pl
	 *            the place in Cami
	 * @param nodePosition
	 *            the place position in Cami
	 * @param cr
	 *            Cami repository.
	 */
	private void setPlaceMarking(final PlaceHLAPI placeNode, final Cn pl, final Po nodePosition, final CamiRepository cr) {
		// TODO implement this
		System.out.println(this.getClass().getCanonicalName() + ": Place marking not yet implemented.");
	}

	/**
	 * This function gets all Cami arcs and translate them to PNML arcs :
	 * inscription and intermediate positions.
	 * 
	 * @param cr
	 *            Cami repository
	 * @throws CamiException
	 *             CamiException
	 */
	public final void setArcs(final CamiRepository cr) throws CamiException {
		/*
		 * Get all arcs from Cami Files
		 */
		final List<Ca> allAr = cr.getAllCa();
		try {
			if (allAr != null) {
				for (final Ca aAr : allAr) {
					final String arcId = myRunner.transformCamiId2XmlId(aAr.getArcID());
					cr.relateCamiPnmlIds(aAr.getArcID(), arcId);
					// get nodes source and target Ids
					final String sId = cr.getPnmlIdOfCamiId(aAr.getStartNodeID());
					final String tId = cr.getPnmlIdOfCamiId(aAr.getEndNodeID());
					final IdRepository pnmlIdRep = pnmlMr.getCurrentIdRepository();
					// creates the arc
					final ArcHLAPI myArc = new ArcHLAPI(arcId, (NodeHLAPI) pnmlIdRep.getObject(sId), (NodeHLAPI) pnmlIdRep.getObject(tId));
					// sets the intermediate positions
					setArcPositions(myArc, aAr, cr);
					setArcInscription(myArc, aAr, cr);
					// In Cami, an arc has no name, so nothing to do in this
					// respect.
					defPage.addObjectsHLAPI(myArc);
				}
			}
		} catch (final InvalidIDException iee) {
			throw new CamiException(iee.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
	}

	/**
	 * Sets the intermediate positions of an arc in the PNM model.
	 * 
	 * @param arc
	 *            the arc in PNML
	 * @param ar
	 *            the arc in Cami
	 * @param cr
	 *            Cami repository
	 */
	private void setArcPositions(final ArcHLAPI arc, final Ca ar, final CamiRepository cr) {
		final List<Pi> arcPositions = cr.getArcPositions(ar.getArcID());
		if (arcPositions != null) {
			final ArcGraphicsHLAPI myArcGraphics = new ArcGraphicsHLAPI(arc);
			for (final Pi aPi : arcPositions) {
				final PositionHLAPI myPosition = new PositionHLAPI(Integer.valueOf(aPi.getH()), Integer.valueOf(aPi.getV()));
				myArcGraphics.addPositionsHLAPI(myPosition);
			}
		}
	}

	/**
	 * Sets the inscription of an arc in the PNML model.
	 * 
	 * @param arc
	 *            the arc in PNML
	 * @param ar
	 *            the arc in Cami
	 * @param cr
	 *            repository
	 * @throws CamiException
	 *             CamiException
	 */
	private void setArcInscription(final ArcHLAPI arc, final Ca ar, final CamiRepository cr) throws CamiException {
		// TODO: implement this
		System.out.println("Not yet implemented.");
	}

	/**
	 * Sets the transitions in the PNML model.
	 * 
	 * @param cr
	 *            repository which stores all Cami Objects
	 * @throws CamiException
	 *             CamiException
	 */
	public final void setTransitions(final CamiRepository cr) throws CamiException {
		final List<Cn> allTr = cr.getAllTransitions();
		Po nodePosition = null;
		try {
			if (allTr != null) {
				for (final Cn aTr : allTr) {
					final String nId = myRunner.transformCamiId2XmlId(aTr.getNodeId());
					cr.relateCamiPnmlIds(aTr.getNodeId(), nId);
					final TransitionHLAPI transitionNode = new TransitionHLAPI(nId);
					nodePosition = cr.getNodePosition(aTr.getNodeId());
					final PositionHLAPI myPosition = new PositionHLAPI(Integer.valueOf(nodePosition.getH()), Integer.valueOf(nodePosition.getV()));
					final NodeGraphicsHLAPI myNodeGraphics = new NodeGraphicsHLAPI(myPosition, null, null, null);
					transitionNode.setNodegraphicsHLAPI(myNodeGraphics);
					// sets transition name
					setTransitionName(transitionNode, aTr, nodePosition, cr);
					setTransitionGuard(transitionNode, aTr, nodePosition, cr);
					defPage.addObjectsHLAPI(transitionNode);
				}
			}
		} catch (final InvalidIDException iee) {
			throw new CamiException(iee.getMessage());
		} catch (final VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
	}

	/**
	 * Sets the name of a transition in the PNML model, along with the graphics
	 * of this name (offset w.r.t to the transition position).
	 * 
	 * @param transitionNode
	 *            the transition in PNML
	 * @param tr
	 *            the transition in Cami
	 * @param nodePosition
	 *            the transition position in Cami
	 * @param cr
	 *            Cami repository
	 */
	private void setTransitionName(final TransitionHLAPI transitionNode, final Cn tr, final Po nodePosition, final CamiRepository cr) {
		final String nodeName = cr.getNodeName(tr.getNodeId());
		NameHLAPI nodeNameHLAPI = null;
		if (nodeName != null) {
			nodeNameHLAPI = new NameHLAPI(nodeName);
			transitionNode.setNameHLAPI(nodeNameHLAPI);
		}
		final List<Pt> allPt = cr.getAllPt();
		Integer offX = null;
		Integer offY = null;
		for (final Pt aPt : allPt) {
			if (aPt.getNumObject() == tr.getNodeId()) {
				if ("name".equalsIgnoreCase(aPt.getAttributeName())) {
					if (nodePosition != null) {
						offX = Integer.valueOf(nodePosition.getH() - aPt.getH());
						offY = Integer.valueOf(nodePosition.getV() - aPt.getV());
						if (offX != null && offY != null) {
							final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
							final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(myOffset, null, null, null);
							nodeNameHLAPI.setAnnotationgraphicsHLAPI(myAnnotation);
						}
					}
				}
				break;
			}
		}
	}

	/**
	 * Sets the guard (and its graphics) of a transition in the PNML model.
	 * 
	 * @param trNode
	 *            the transition in PNML
	 * @param tr
	 *            the transition in Cami
	 * @param nodePosition
	 *            the transition position in Cami
	 * @param cr
	 *            Cami repository.
	 */
	private void setTransitionGuard(final TransitionHLAPI trNode, final Cn tr, final Po nodePosition, final CamiRepository cr) {
		// TODO implement this
		System.out.println(this.getClass().getCanonicalName() + ": transition condition not yet implemented.");
	}

	/**
	 * Sets the reference to the singleton PNML model repository being used by
	 * Cami2Pnml.
	 * 
	 * @param mr
	 *            the PNML model repository.
	 */
	public static final void setPnmlModelRepository(final ModelRepository mr) {
		pnmlMr = mr;
	}
}
