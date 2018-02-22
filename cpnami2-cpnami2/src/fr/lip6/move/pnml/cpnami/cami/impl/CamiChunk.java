
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
