/*
 * OnderhoudenBurgerservicenummerGegevensService.java 23-mei-2007
 *
 *-----------------------------------------------------------------------------------
 *     Ident: Sector Ontwikkeling - B/CICT
 *    Author: Raymond Loman
 * Copyright: (c) 2007 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *-----------------------------------------------------------------------------------
 * De Belastingdienst           |  Unpublished work. This computer program includes
 * Postbus 9050                 |  Confidential, Properietary Information and is a
 * 7300 GM Apeldoorn            |  trade Secret of the Belastingdienst. No part of
 * The Netherlands              |  this file may be reproduced or transmitted in any
 *                              |  form or by any means, electronic or mechanical,
 *                              |  for the purpose, without the express written
 *                              |  permission of the copyright holder.
 *-----------------------------------------------------------------------------------
 */
package nl.belastingdienst.aig.waarde;

import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

/**
 * Business service voor het onderhouden van Waarden.
 * 
 * @author Raymond Loman
 */
public interface OnderhoudenWaardeService {

	/**
	 * @param burgerservicenummer
	 * @return List<Waarde>
	 */
	List<Waarde> findAllWhere(Integer burgerservicenummer);

	/**
	 * @param belastingJaar
	 * @param burgerservicenummer
	 * @return List<Waarde>
	 */
	List<Waarde> findAllWhere(Short belastingJaar, Integer burgerservicenummer);

	/**
	 * @return List<Waarde>
	 */
	List<Waarde> geefAlleWaarden();

	/**
	 * @param burgerservicenummer
	 * @param belastingJaar
	 * @param datumIngang
	 * @param datumTijdGeregistreerd
	 * @return Waarde
	 */
	Waarde geefWaarde(Betrokkene betrokkene, Date datumIngang, Date datumTijdGeregistreerd);

}
