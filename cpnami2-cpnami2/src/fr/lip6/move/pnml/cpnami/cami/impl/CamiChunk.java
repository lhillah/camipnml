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

import fr.lip6.move.pnml.cpnami.cami.CamiRepository;

public final class CamiChunk {

	private String contents;
	private CamiRepository cr;

	public CamiChunk(String contents, CamiRepository cr) {
		this.setContents(contents);
		this.setCr(cr);
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public CamiRepository getCr() {
		return cr;
	}

	public void setCr(CamiRepository cr) {
		this.cr = cr;
	}

}
