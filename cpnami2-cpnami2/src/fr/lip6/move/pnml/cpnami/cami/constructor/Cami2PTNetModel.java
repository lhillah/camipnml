
package fr.lip6.move.pnml.cpnami.cami.constructor;

import java.util.List;

import org.slf4j.Logger;

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
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;
import fr.lip6.move.pnml.ptnet.hlapi.AnnotationGraphicsHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.ArcGraphicsHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.ArcHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NameHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NodeGraphicsHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.OffsetHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PNTypeHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PTArcAnnotationHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PTMarkingHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PageHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PetriNetHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PositionHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.TransitionHLAPI;

/**
 * Construction of a PT Net from CAMI into PNML.
 */
public final class Cami2PTNetModel {
	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * Create an instance of Log object name parameter.
	 */
	private Logger journal;
	/**
	 * The singleton Pnml model repository that is being used by Cami2Pnml.
	 */
	private ModelRepository pnmlMr;
	/**
	 * The PT net being constructed.
	 */
	private PetriNetHLAPI net;
	/**
	 * The default page of this net.
	 */
	private PageHLAPI defPage;

	/**
	 * Constructor.
	 */
	public Cami2PTNetModel() {
		super();
		journal = LogMaster.getLogger(Cami2PTNetModel.class
				.getCanonicalName() + "#" + Thread.currentThread().getId());
	}

	/**
	 * Returns the constructed Place/Transition Petri net.
	 * 
	 * @return PetriNetHLAPI the constructed PT net.
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
	public final void setNet(CamiRepository cr) throws CamiException {
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
		} catch (InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (VoidRepositoryException vre) {
			vre.printStackTrace();
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
			this.defPage = new PageHLAPI("page0", new NameHLAPI("DefaultPage"),
					null);
			this.defPage.setContainerPetriNetHLAPI(this.net);
		} catch (InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (VoidRepositoryException vre) {
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
	public final Thread setPlaces(CamiRepository cr) throws CamiException {
		CamiPTPlace2PnmlRunnable plCreator = new CamiPTPlace2PnmlRunnable(cr, defPage);
		Thread tr = new Thread(plCreator);
		tr.start();
		return tr;
		/*
		// Populates places ids ( Cn commands)
		final List<Cn> allPl = cr.getAllPlaces();
		Po nodePosition = null;
		try {
			if (allPl != null) {
				// we look for all Places existing in our model
				for (Cn aPl : allPl) {
					final String nId = myRunner.transformCamiId2XmlId(aPl
							.getNodeId());
					cr.relateCamiPnmlIds(aPl.getNodeId(), nId);
					final PlaceHLAPI placeNode = new PlaceHLAPI(nId);
					nodePosition = cr.getNodePosition(aPl.getNodeId());
					if (nodePosition != null) {
						final NodeGraphicsHLAPI placeGraphics = new NodeGraphicsHLAPI(
								new PositionHLAPI(Integer.valueOf(nodePosition
										.getH()), Integer.valueOf(nodePosition
										.getV())), null, null, null);
						placeNode.setNodegraphicsHLAPI(placeGraphics);
					}
					// sets place name
					setPlaceName(placeNode, aPl, nodePosition, cr);
					// set place marking
					setPlaceMarking(placeNode, aPl, nodePosition, cr);
					/*
					 * Experimental!!! Sets a tool specific info for each place
					 * fr.lip6.move.pnml.ptnet.ToolInfo ti =
					 * fr.lip6.move.pnml.ptnet
					 * .PtnetFactory.eINSTANCE.createToolInfo();
					 * ti.setTool("CPNAMI"); ti.setVersion("3.5"); StringBuffer
					 * formattedXML = new StringBuffer();
					 * formattedXML.append("<code language=\"C\">\n");
					 * formattedXML
					 * .append("int main(int argc, char** argv) {\n");
					 * formattedXML.append("printf(\"Hello World\");\n");
					 * formattedXML.append("}\n");
					 * formattedXML.append("</code>");
					 * ti.setFormattedXMLBuffer(formattedXML);
					 * ti.setContainerPnObject(placeNode.getContainedItem());
					 */
					//defPage.addObjectsHLAPI(placeNode);
				/*}
			}
		} catch (InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
		//Remove no longer used Places data structures
		cr.removeAllPlaces();
		*/
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
	@SuppressWarnings("unused")
	private void setPlaceMarking(PlaceHLAPI placeNode, Cn pl, Po nodePosition,
			CamiRepository cr) {
		final String plMark = cr.getPlaceMarking(pl.getNodeId());
		// final List<Pt> allPt = cr.getAllPt();
		Integer offX = null;
		Integer offY = null;
		if (plMark != null) {
			final PTMarkingHLAPI ptMarking = new PTMarkingHLAPI(new Long(
					plMark), placeNode);
			Pt aPt = cr.getMarkingPositionOfNode(pl.getNodeId());
			// for (Pt aPt : allPt) {
			// if ("marking".equalsIgnoreCase(aPt.getAttributeName())) {
			// Graphics of the marking: offset of the place position
			if (nodePosition != null) {
				offX = Integer.valueOf(nodePosition.getH() - aPt.getH());
				offY = Integer.valueOf(nodePosition.getV() - aPt.getV());
				if (offX != null && offY != null) {
					final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
					final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(
							myOffset, null, null, null);
					ptMarking.setAnnotationgraphicsHLAPI(myAnnotation);
				}
			}
			// }
			// }
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
	@SuppressWarnings("unused")
	private void setPlaceName(PlaceHLAPI placeNode, Cn pl, Po placePosition,
			CamiRepository cr) {
		// final List<Pt> allPt = cr.getAllPt();
		Integer offX = null;
		Integer offY = null;
		final String nodeName = cr.getNodeName(pl.getNodeId());
		if (nodeName != null) {
			final NameHLAPI nodeNameHLAPI = new NameHLAPI(nodeName);
			placeNode.setNameHLAPI(nodeNameHLAPI);
			// Graphics of the name: offset of the place position.
			Pt aPt = cr.getNamePositionOfNode(pl.getNodeId());
			// if (allPt != null) {
			// for (Pt aPt : allPt) {
			// if (aPt.getNumObject() == pl.getNodeId()) {
			// if ("name".equalsIgnoreCase(aPt.getAttributeName())) {
			if (placePosition != null && aPt!= null) {
				offX = Integer.valueOf(placePosition.getH() - aPt.getH());
				offY = Integer.valueOf(placePosition.getV() - aPt.getV());
				if (offX != null && offY != null) {
					final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
					final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(
							myOffset, null, null, null);
					nodeNameHLAPI.setAnnotationgraphicsHLAPI(myAnnotation);
				}
			}
			// break;
			// }
			// }
			// }
			// }
		}
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
	public final void setArcs(CamiRepository cr) throws CamiException {
		/*
		 * Get all arcs from Cami Files
		 * TODO : try to go beyond  INTEGER.MAX_VALUE = 2147483647
		 * Returns a list of lists of arcs (same for places and transitions ?)
		 */
		final List<Ca> allAr = cr.getAllCa();
		String arcId;
		String sId;
		String tId;
		IdRepository pnmlIdRep;
		ArcHLAPI myArc;
		try {
			if (allAr != null) {
				// TODO : Limited by INTEGER.MAX_VALUE = 2147483647
				int nbArcs = allAr.size();
				int half = nbArcs / 2;
				CamiPTArc2PnmlRunnable arCreator = new CamiPTArc2PnmlRunnable(cr, pnmlMr, defPage, allAr, half);
				Thread tr = new Thread(arCreator);
				tr.start();
				try {
					tr.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Ca anArc;
				pnmlIdRep  = pnmlMr.getCurrentIdRepository();
				for (int i = half; i < nbArcs; i++) {
					anArc = allAr.get(i);
					arcId = myRunner.transformCamiId2XmlId(anArc.getArcID());
					sId = cr.getPnmlIdOfCamiId(anArc.getStartNodeID());
					tId = cr.getPnmlIdOfCamiId(anArc.getEndNodeID());
					// creates the arc
					if (sId == null) {
						journal.error("This translation will fail! Cami arc " + anArc.getArcID() + " does not have source node id!");
					} 
					if (tId == null) {
						journal.error("This translation will fail! Cami arc " + anArc.getArcID() + " does not have target node id!");
					}
					myArc = new ArcHLAPI(arcId,
							(NodeHLAPI) pnmlIdRep.getObject(sId),
							(NodeHLAPI) pnmlIdRep.getObject(tId));
					// sets the intermediate positions
					setArcPositions(myArc, anArc, cr);
					// sets the valuation
					setArcInscription(myArc, anArc, cr);
					// In Cami, an arc has no name, so nothing to do in this
					// respect.
					defPage.addObjectsHLAPI(myArc);
				}		
				/*for (Ca aAr : allAr) {
					arcId = myRunner.transformCamiId2XmlId(aAr.getArcID());
					//cr.relateCamiPnmlIds(aAr.getArcID(), arcId);
					// get nodes source and target Ids
					sId = cr.getPnmlIdOfCamiId(aAr.getStartNodeID());
					tId = cr.getPnmlIdOfCamiId(aAr.getEndNodeID());
					pnmlIdRep = pnmlMr.getCurrentIdRepository();
					// creates the arc
					myArc = new ArcHLAPI(arcId,
							(NodeHLAPI) pnmlIdRep.getObject(sId),
							(NodeHLAPI) pnmlIdRep.getObject(tId));
					// sets the intermediate positions
					setArcPositions(myArc, aAr, cr);
					// sets the valuation
					setArcInscription(myArc, aAr, cr);
					// In Cami, an arc has no name, so nothing to do in this
					// respect.
					//defPage.addObjectsHLAPI(myArc);
				}*/
			}
		} catch (InvalidIDException iee) {
			throw new CamiException(iee.getMessage());
		} catch (VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
		cr.removeAllArcs();
		cr.resetRepository();
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
	private void setArcInscription(ArcHLAPI arc, Ca ar, CamiRepository cr)
			throws CamiException {
		final String valuation = cr.getArcInscription(ar.getArcID());
		try {
			if (valuation != null) {
				final Long arcInsc =Long.valueOf(Long
						.parseLong(valuation));
				final PTArcAnnotationHLAPI arcAnnotation = new PTArcAnnotationHLAPI(
						arcInsc, null);
				arc.setInscriptionHLAPI(arcAnnotation);
				// set the offset of the inscription
				// (ArcAnnotation graphics) w.r.t the first intermediate
				// position
				// String nodeKind = null;
				// here we want to setjust one intermediate point
				final Pi arcPosition = cr.getArcPosition(ar.getArcID());
				Integer offX = null;
				Integer offY = null;
				Pt aPt = cr.getValuationPosition(ar.getArcID());
				// discovers textual attributes positions
				// final List<Pt> allPt = cr.getAllPt();
				// if (allPt != null) {
				// for (Pt aPt : allPt) {
				// final Integer nId = Integer.valueOf(aPt.getNumObject());
				// nodeKind = cr.getNodeKind(nId);
				// if ("arc".equalsIgnoreCase(nodeKind)) {
				// if (nId == ar.getArcID()) {
				if (arcPosition != null) {
					offX = Integer.valueOf(arcPosition.getH() - aPt.getH());
					offY = Integer.valueOf(arcPosition.getV() - aPt.getV());
					if (offX != null && offY != null) {
						final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
						final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(
								myOffset, null, null, null);
						arcAnnotation.setAnnotationgraphicsHLAPI(myAnnotation);

					}
				}
				// break;
				// }
				// }
				// }
				// }
			}
		} catch (NumberFormatException nfe) {
			throw new CamiException(nfe.getMessage());
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
	private void setArcPositions(ArcHLAPI arc, Ca ar, CamiRepository cr) {
		final List<Pi> arcPositions = cr.getArcPositions(ar.getArcID());
		if (arcPositions != null) {
			final ArcGraphicsHLAPI myArcGraphics = new ArcGraphicsHLAPI(arc);
			for (Pi aPi : arcPositions) {
				final PositionHLAPI myPosition = new PositionHLAPI(
						Integer.valueOf(aPi.getH()),
						Integer.valueOf(aPi.getV()));
				myArcGraphics.addPositionsHLAPI(myPosition);
			}
		}
	}

	/**
	 * Sets the transitions in the PNML model.
	 * 
	 * @param cr
	 *            repository which stores all Cami Objects
	 * @throws CamiException
	 *             CamiException
	 */
	public final void setTransitions(CamiRepository cr) throws CamiException {
		final List<Cn> allTr = cr.getAllTransitions();
		Po nodePosition = null;
		try {
			if (allTr != null) {
				for (Cn aTr : allTr) {
					final String nId = myRunner.transformCamiId2XmlId(aTr
							.getNodeId());
					cr.relateCamiPnmlIds(aTr.getNodeId(), nId);
					final TransitionHLAPI transitionNode = new TransitionHLAPI(
							nId);
					nodePosition = cr.getNodePosition(aTr.getNodeId());
					if (nodePosition != null) {
						final PositionHLAPI myPosition = new PositionHLAPI(
								Integer.valueOf(nodePosition.getH()),
								Integer.valueOf(nodePosition.getV()));
						final NodeGraphicsHLAPI myNodeGraphics = new NodeGraphicsHLAPI(
								myPosition, null, null, null);
						transitionNode.setNodegraphicsHLAPI(myNodeGraphics);
					}
					// sets transition name
					setTransitionName(transitionNode, aTr, nodePosition, cr);
					defPage.addObjectsHLAPI(transitionNode);
				}
			}
		} catch (InvalidIDException iee) {
			throw new CamiException(iee.getMessage());
		} catch (VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
		cr.removeAllTransitions();
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
	private void setTransitionName(TransitionHLAPI transitionNode, Cn tr,
			Po nodePosition, CamiRepository cr) {
		final String nodeName = cr.getNodeName(tr.getNodeId());
		NameHLAPI nodeNameHLAPI = null;
		if (nodeName != null) {
			nodeNameHLAPI = new NameHLAPI(nodeName);
			transitionNode.setNameHLAPI(nodeNameHLAPI);
		}
		// final List<Pt> allPt = cr.getAllPt();
		Integer offX = null;
		Integer offY = null;
		Pt aPt = cr.getNamePositionOfNode(tr.getNodeId());
		// if (allPt != null) {
		// for (Pt aPt : allPt) {
		// if (aPt.getNumObject() == tr.getNodeId()) {
		// if ("name".equalsIgnoreCase(aPt.getAttributeName())) {
		if (nodePosition != null) {
			offX = Integer.valueOf(nodePosition.getH() - aPt.getH());
			offY = Integer.valueOf(nodePosition.getV() - aPt.getV());
			if (offX != null && offY != null) {
				final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
				final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(
						myOffset, null, null, null);
				nodeNameHLAPI.setAnnotationgraphicsHLAPI(myAnnotation);
			}
		}
		// }
		// break;
		// }
		// }
		// }
	}

	/**
	 * Sets the reference to the singleton PNML model repository being used by
	 * Cami2Pnml.
	 * 
	 * @param mr
	 *            the PNML model repository.
	 */
	public final void setPnmlModelRepository(ModelRepository mr) {
		pnmlMr = mr;
	}
}
