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

package nl.belastingdienst.aig.onderzoek;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

/**
 * Service interface voor Onderzoek
 * 
 * @author manef00
 */
public interface OnderhoudenOnderzoekService {
	/**
	 * @param onderzoekCommand
	 * @return Geeft een compleet gevuld Onderzoek terug, inclusief compleet gevulde subobjecten
	 */
	public Onderzoek geefCompleetOnderzoek(Onderzoek onderzoekCommand);

	/**
	 * @param onderzoekCommand
	 * @return Geeft een Onderzoek terug met gevulde primitieven maar met 'kale' subobjecten
	 */
	public Onderzoek geefOnderzoek(Onderzoek onderzoekCommand);

	/**
	 * @param betrokkeneCommand
	 * @return Lijst met Onderzoeken voor opgegeven Betrokkene
	 */
	public List<Onderzoek> geefOnderzoekList(Betrokkene betrokkeneCommand);
}
