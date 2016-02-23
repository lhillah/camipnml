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
package fr.lip6.move.pnml.cpnami.cami.impl;

import fr.lip6.move.pnml.cpnami.cami.Command;
import fr.lip6.move.pnml.cpnami.cami.CommandVisitor;
import fr.lip6.move.pnml.cpnami.cami.model.As;
import fr.lip6.move.pnml.cpnami.cami.model.Ca;
import fr.lip6.move.pnml.cpnami.cami.model.Cm;
import fr.lip6.move.pnml.cpnami.cami.model.Cn;
import fr.lip6.move.pnml.cpnami.cami.model.Cs;
import fr.lip6.move.pnml.cpnami.cami.model.Ct;
import fr.lip6.move.pnml.cpnami.cami.model.Db;
import fr.lip6.move.pnml.cpnami.cami.model.De;
import fr.lip6.move.pnml.cpnami.cami.model.Fb;
import fr.lip6.move.pnml.cpnami.cami.model.Fe;
import fr.lip6.move.pnml.cpnami.cami.model.Pi;
import fr.lip6.move.pnml.cpnami.cami.model.Po;
import fr.lip6.move.pnml.cpnami.cami.model.Pt;
/**
 * Visitor that prints Cami commands.
 */
public class PrintCommandVisitorImpl implements CommandVisitor {
/**
 * Visits As command.
 * @param command the command
 */
    public final void visitAs(Command command) {
	System.out.println(((As) command).convert2String());
    }
    /**
     * Visits Ca command.
     * @param command the command
     */
    public final void visitCa(Command command) {
	System.out.println(((Ca) command).convert2String());
    }
    /**
     * Visits Cm command.
     * @param command the command
     */
    public final void visitCm(Command command) {
	System.out.println(((Cm) command).convert2String());
    }
    /**
     * Visits Cn command.
     * @param command the command
     */
    public final void visitCn(Command command) {
	System.out.println(((Cn) command).convert2String());
    }
    /**
     * Visits Cs command.
     * @param command the command
     */
    public final void visitCs(Command command) {
	System.out.println(((Cs) command).convert2String());
    }
    /**
     * Visits Ct command.
     * @param command the command
     */
    public final void visitCt(Command command) {
	System.out.println(((Ct) command).convert2String());
    }
    /**
     * Visits Db command.
     * @param command the command
     */
    public final void visitDb(Command command) {
	System.out.println(((Db) command).convert2String());
    }
    /**
     * Visits De command.
     * @param command the command
     */
    public final void visitDe(Command command) {
	System.out.println(((De) command).convert2String());
    }
    /**
     * Visits Fb command.
     * @param command the command
     */
    public final void visitFb(Command command) {
	System.out.println(((Fb) command).convert2String());
    }
    /**
     * Visits Fe command.
     * @param command the command
     */
    public final void visitFe(Command command) {
	System.out.println(((Fe) command).convert2String());
    }
    /**
     * Visits Pi command.
     * @param command the command
     */
    public final void visitPi(Command command) {
	System.out.println(((Pi) command).convert2String());
    }
    /**
     * Visits Po command.
     * @param command the command
     */
    public final void visitPo(Command command) {
	System.out.println(((Po) command).convert2String());
    }
    /**
     * Visits Pt command.
     * @param command the command
     */
    public final void visitPt(Command command) {
	System.out.println(((Pt) command).convert2String());
    }

}
