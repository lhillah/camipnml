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
package fr.lip6.move.pnml.cpnami.cami.constructor;

import java.util.List;

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.framework.utils.exception.InvalidIDException;
import fr.lip6.move.pnml.framework.utils.exception.VoidRepositoryException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;
import fr.lip6.move.pnml.ptnet.hlapi.AnnotationGraphicsHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NameHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.NodeGraphicsHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.OffsetHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PTMarkingHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PageHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PlaceHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PositionHLAPI;

public final class CamiPTPlace2PnmlRunnable implements Runnable {
	
	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * Create an instance of Log object name parameter.
	 */
	private Log journal;
	/**
	 * Cami Repository.
	 */
	private final CamiRepository cr;
	
	/**
	 * The default page of this net.
	 */
	private PageHLAPI defPage;
	
	public CamiPTPlace2PnmlRunnable(CamiRepository cr, PageHLAPI page) {
		this.cr = cr;
		this.defPage = page;
	}

	@Override
	public void run() {
		journal = LogMaster.giveLogger(CamiPTPlace2PnmlRunnable.class
				.getCanonicalName() + "#" + Thread.currentThread().getId());
		try {
			setPlaces();
		} catch (CamiException e) {
			journal.error(e.getMessage());
			e.printStackTrace();
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
	private final void setPlaces() throws CamiException {
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
					setPlaceName(placeNode, aPl, nodePosition);
					// set place marking
					setPlaceMarking(placeNode, aPl, nodePosition);
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
					defPage.addObjectsHLAPI(placeNode);
				}
			}
		} catch (InvalidIDException iie) {
			throw new CamiException(iie.getMessage());
		} catch (VoidRepositoryException vre) {
			throw new CamiException(vre.getMessage());
		}
		//Remove no longer used Places data structures
		cr.removeAllPlaces();
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
	private final void setPlaceMarking(PlaceHLAPI placeNode, Cn pl, Po nodePosition) {
		final String plMark = cr.getPlaceMarking(pl.getNodeId());
		Integer offX = null;
		Integer offY = null;
		if (plMark != null) {
			final PTMarkingHLAPI ptMarking = new PTMarkingHLAPI(new Integer(
					plMark), placeNode);
			Pt aPt = cr.getMarkingPositionOfNode(pl.getNodeId());
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
	private final void setPlaceName(PlaceHLAPI placeNode, Cn pl, Po placePosition) {
		Integer offX = null;
		Integer offY = null;
		final String nodeName = cr.getNodeName(pl.getNodeId());
		if (nodeName != null) {
			final NameHLAPI nodeNameHLAPI = new NameHLAPI(nodeName);
			placeNode.setNameHLAPI(nodeNameHLAPI);
			// Graphics of the name: offset of the place position.
			Pt aPt = cr.getNamePositionOfNode(pl.getNodeId());
			if (placePosition != null) {
				offX = Integer.valueOf(placePosition.getH() - aPt.getH());
				offY = Integer.valueOf(placePosition.getV() - aPt.getV());
				if (offX != null && offY != null) {
					final OffsetHLAPI myOffset = new OffsetHLAPI(offX, offY);
					final AnnotationGraphicsHLAPI myAnnotation = new AnnotationGraphicsHLAPI(
							myOffset, null, null, null);
					nodeNameHLAPI.setAnnotationgraphicsHLAPI(myAnnotation);
				}
			}
		}
	}

}
