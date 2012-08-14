/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: lomar00
 *          Copyright: (c) 2011 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *  ----------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ----------------------------------------------------------------------------------------------------
 *
 */

package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.Onderzoek;

public interface OnderhoudenAanleidingService {

	/**
	 * @param aanleidingCommand
	 * @return .
	 */
	public Aanleiding geefAanleiding(Aanleiding aanleidingCommand);

	/**
	 * @param berichtkenmerkAig
	 * @return Aanleiding voor berichtkenmerkAig
	 */
	public Aanleiding geefAanleidingByKey(String berichtkenmerkAig);

	/**
	 * @param onderzoekCommand
	 * @return Lijst met Aanledingen voor opgegeven onderzoek
	 */
	public List<Aanleiding> geefAanleidingenVoor(Onderzoek onderzoekCommand);

	/**
	 * @param burgerserviceNummer
	 * @param belastingJaar
	 * @return Lijst met Aanledingen voor opgegeven bsn en belastingjaar
	 */
	List<Aanleiding> geefAanleidingenMetLegeEinddatum();

	List<Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene);
}
