
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
