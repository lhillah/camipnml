/**
 *  Copyright 2009-2016 Universite Paris Ouest and Sorbonne Universites,
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
package fr.lip6.move.cpnami.cami.c2p;

import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import fr.lip6.move.pnml.cpnami.cami.CamiFactory;
import fr.lip6.move.pnml.cpnami.cami.CamiRepository;
import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.impl.CamiChunk;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.cpnami.exceptions.ParseException;
import fr.lip6.move.pnml.framework.utils.logging.LogMaster;

/**
 * Threaded task to parse chunks of Cami commands into a Cami repository.
 * 
 * @author lom
 * 
 */
public final class CamiReader implements Runnable {

	private final BlockingQueue<CamiChunk> queue;

	private Logger journal ;

	private BlockingQueue<String> notificationQueue;
	/**
	 * Factory initialization.
	 */
	private static final CamiFactory CF = CamiFactory.SINSTANCE;

	public CamiReader(BlockingQueue<CamiChunk> camiQueue, BlockingQueue<String> notifQueue) {
		this.queue = camiQueue;
		this.notificationQueue = notifQueue;
	}

	@Override
	public void run() {
		journal = LogMaster.getLogger(CamiReader.class
				.getCanonicalName() + "#" + Thread.currentThread().getId());
		String camichunk;
		String[] commands;
		CamiChunk cc;
		CamiRepository cr;
		Pattern p = Pattern.compile("\n");
		try {
			cc = queue.take();
			camichunk = cc.getContents();
			
			while (!camichunk.equalsIgnoreCase("END")) {

				while (!camichunk.equalsIgnoreCase("EOF")) {
					cr = cc.getCr();
					commands = p.split(camichunk);
					for (String aCommand : commands) {
						try {
							final Command aCamiCommand = CF
									.createACommand(aCommand);
							cr.addCommand(aCamiCommand);
						} catch (final ParseException pe) {
							journal.error("Cami command parsing error: "
									+ pe.getMessage() + " --> "
									+ pe.getFaultyString());
						} catch (final CamiException e) {
							journal.error("Thread error while creating Cami command: "
									+ e.getMessage());
							e.printStackTrace();
						}
					}
					cc = queue.take();
					camichunk = cc.getContents();
				}
				notificationQueue.put("FINISHED");
				cc = queue.peek();
				//camichunk = cc.getContents();
				if (cc != null && cc.getContents().equalsIgnoreCase("EOF")) {
					Thread.sleep(1000L);
				}
				cc = queue.take();
				camichunk = cc.getContents();
			}
		} catch (InterruptedException e) {
			journal.error(e.getMessage());
			e.printStackTrace();
		}
		journal.info("Finished parsing Cami commands");
	}

}
