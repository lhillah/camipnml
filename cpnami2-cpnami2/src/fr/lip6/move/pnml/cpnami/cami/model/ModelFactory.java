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
package fr.lip6.move.pnml.cpnami.cami.model;

import fr.lip6.move.pnml.cpnami.cami.model.impl.ModelFactoryImpl;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see fr.lip6.move.pnml.cpnami.cami.model.ModelPackage
 */
public interface ModelFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 */
	ModelFactory SINSTANCE = ModelFactoryImpl.getModelFactoryInstance();

	/**
	 * Returns a new object of class '<em>Db</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Db</em>'.
	 */
	Db createDb();

	/**
	 * Creates a De command.
	 * 
	 * @return a new De command.
	 */
	De createDe();

	/**
	 * Returns a new object of class '<em>Fb</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Fb</em>'.
	 */
	Fb createFb();

	/**
	 * Creates a Fe command.
	 * 
	 * @return a new Fe command.
	 */
	Fe createFe();

	/**
	 * Returns a new object of class '<em>Cn</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cn</em>'.
	 */
	Cn createCn();

	/**
	 * Returns a new object of class '<em>Ca</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ca</em>'.
	 * @generated
	 */
	Ca createCa();

	/**
	 * Returns a new object of class '<em>As</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>As</em>'.
	 */
	As createAs();

	/**
	 * Returns a new object of class '<em>Cs</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cs</em>'.
	 */
	Cs createCs();

	/**
	 * Returns a new object of class '<em>Ct</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Ct</em>'.
	 */
	Ct createCt();

	/**
	 * Returns a new object of class '<em>Cm</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cm</em>'.
	 */
	Cm createCm();

	/**
	 * Returns a new object of class '<em>Po</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Po</em>'.
	 */
	Po createPo();

	/**
	 * Returns a new object of class '<em>Pi</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Pi</em>'.
	 */
	Pi createPi();

	/**
	 * Returns a new object of class '<em>Pt</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Pt</em>'.
	 */
	Pt createPt();

} // ModelFactory
