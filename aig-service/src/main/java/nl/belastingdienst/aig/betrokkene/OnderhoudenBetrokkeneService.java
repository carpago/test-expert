/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CAO 
 *            Creator: manef00
 *          Copyright: (c) 2011 Belastingdienst / Centrum voor Applicatieontwikkeling en Onderhoud,
 *                         All Rights Reserved.
 *  ---------------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ---------------------------------------------------------------------------------------------------------
 *
 */

package nl.belastingdienst.aig.betrokkene;

import java.util.List;

/**
 * Business service interface voor het onderhouden van Betrokkenen.
 * 
 * @author manef00
 */
public interface OnderhoudenBetrokkeneService {

	/**
	 * @param burgerservicenummer
	 * @return alle Betrokkenen met opgegeven bsn
	 */
	List<Betrokkene> findAllWhere(Integer burgerservicenummer);

	/**
	 * @return alle Betrokkenen
	 */
	List<Betrokkene> getAllBetrokkene();

	/**
	 * @param belastingJaar
	 * @param burgerservicenummer
	 * @return Betrokkene op basis van de key (bsn + belastingjaar)
	 */
	Betrokkene getBetrokkeneByKey(Short belastingJaar, Integer burgerservicenummer);

}
