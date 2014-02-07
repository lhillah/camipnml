/**
 *  Copyright 2009 Universite Paris Ouest and Sorbonne Universites, Univ. Paris 06 - CNRS UMR 7606 (LIP6)
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
package fr.lip6.move.cpnami.cami.c2p;

import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.utils.CLOptions;
import fr.lip6.move.pnml.framework.hlapi.HLAPIRootClass;

public final class Cami2PnmlInfoSet {

	private final CamiRepository cr;
	private final HLAPIRootClass doc;
	private final boolean createWspOnce;
	private final CLOptions clOptions;
	private final String pnmlFile;

	public Cami2PnmlInfoSet(final CamiRepository cr,
			final HLAPIRootClass pnDoc, final boolean createWorkspaceOnce,
			final CLOptions clOptions, final String pnmlFileOut) {
		this.cr = cr;
		this.doc = pnDoc;
		this.createWspOnce = createWorkspaceOnce;
		this.clOptions = clOptions;
		this.pnmlFile = pnmlFileOut;
	}

	public CamiRepository getCamiRepository() {
		return cr;
	}

	public HLAPIRootClass getDoc() {
		return doc;
	}

	public boolean isCreateWspOnce() {
		return createWspOnce;
	}

	public CLOptions getClOptions() {
		return clOptions;
	}

	public String getPnmlFile() {
		return pnmlFile;
	}

}
