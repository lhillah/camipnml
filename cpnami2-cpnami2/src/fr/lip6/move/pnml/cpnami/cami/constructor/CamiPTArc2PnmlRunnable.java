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

import org.apache.commons.logging.Log;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Runner;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
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
import fr.lip6.move.pnml.ptnet.hlapi.NodeHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.OffsetHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PTArcAnnotationHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PageHLAPI;
import fr.lip6.move.pnml.ptnet.hlapi.PositionHLAPI;

public final class CamiPTArc2PnmlRunnable implements Runnable {
	/**
	 * Retrieves the singleton instance of Runner to use its utility methods.
	 */
	private static Runner myRunner = CamiFactory.SINSTANCE.createRunner();
	/**
	 * Create an instance of Log object name parameter.
	 */
	private Log journal;
	/**
	 * Cami repository
	 */
	private final CamiRepository cr;
	/**
	 * The singleton Pnml model repository that is being used by Cami2Pnml.
	 */
	private ModelRepository pnmlMr;
	/**
	 * The default page of the processed net.
	 */
	private PageHLAPI defPage;
	/**
	 * How many arcs to process?
	 */
	private int nbIter;
	/**
	 * Collection of arcs to process
	 */
	private List<Ca> allArcs;

	public CamiPTArc2PnmlRunnable(CamiRepository cr, ModelRepository pnmlMr,
			PageHLAPI defPage, List<Ca> allAr, int nbIter) {
		this.cr = cr;
		this.pnmlMr = pnmlMr;
		this.defPage = defPage;
		this.allArcs = allAr;
		this.nbIter = nbIter;

	}

	@Override
	public void run() {
		journal = LogMaster.giveLogger(CamiPTArc2PnmlRunnable.class
				.getCanonicalName() + "#" + Thread.currentThread().getId());
		setArcs();
	}

	/**
	 * Sets the Arcs in the portion of arcs collection indicated
	 * by the upper bound nbIter.
	 */
	private final void setArcs() {
		String arcId;
		String sId;
		String tId;
		IdRepository pnmlIdRep;
		Ca anArc;
		ArcHLAPI myArc;
		try {
			pnmlIdRep  = pnmlMr.getCurrentIdRepository();
			for (int i = 0; i < nbIter; i++) {
				anArc = allArcs.get(i);
				arcId = myRunner.transformCamiId2XmlId(anArc.getArcID());
				sId = cr.getPnmlIdOfCamiId(anArc.getStartNodeID());
				tId = cr.getPnmlIdOfCamiId(anArc.getEndNodeID());
				// creates the arc
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
		} catch (InvalidIDException iee) {
			journal.error(iee.getMessage());
			iee.printStackTrace();
		} catch (VoidRepositoryException vre) {
			journal.error(vre.getMessage());
			vre.printStackTrace();
		} catch (CamiException ce) {
			journal.error(ce.getMessage());
			ce.printStackTrace();
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
	private final void setArcInscription(ArcHLAPI arc, Ca ar, CamiRepository cr)
			throws CamiException {
		final String valuation = cr.getArcInscription(ar.getArcID());
		try {
			if (valuation != null) {
				final Integer arcInsc = Integer.valueOf(Integer
						.parseInt(valuation));
				final PTArcAnnotationHLAPI arcAnnotation = new PTArcAnnotationHLAPI(
						arcInsc, null);
				arc.setInscriptionHLAPI(arcAnnotation);
				// set the offset of the inscription
				// (ArcAnnotation graphics) w.r.t the first intermediate
				// position
				// here we want to set just one intermediate point
				final Pi arcPosition = cr.getArcPosition(ar.getArcID());
				Integer offX = null;
				Integer offY = null;
				Pt aPt = cr.getValuationPosition(ar.getArcID());
				// discovers textual attributes positions
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
	private final void setArcPositions(ArcHLAPI arc, Ca ar, CamiRepository cr) {
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

}
