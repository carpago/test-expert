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

package nl.belastingdienst.aig.dao;

import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;

/**
 * Interface van DAO voor Aanleiding objecten. Bevat de standaard CRUD functies
 * (Create, Retrieve, Update en Delete) voor het aanmaken, ophalen, bijwerken en
 * verwijderen van een record in de datastore. En daarnaast een RetrieveAll
 * functie voor het ophalen van alle records in de datastore.
 * 
 * @author manef00
 */
public interface AanleidingDAO {

	/**
	 * @param onderzoek
	 * @return Lijst met aanleidingen voor opgegeven onderzoek
	 */
	List<Aanleiding> findAllWhere(Onderzoek onderzoek);

	/**
	 * @param melding
	 * @return Aanleiding voor opgegeven Melding
	 */
	Aanleiding retrieve(Melding melding);

	/**
	 * @param berichtkenmerkAig
	 * @return Aanleiding voor opgegeven berichtkenmerkAig
	 */
	@CreateUnittest(in="berichtkenmerkAig", out="aanleiding")
	Aanleiding findByKey(String berichtkenmerkAig);

	/**
	 * @return allle Aanleidingen
	 */
	List<Aanleiding> retrieveAll();

	List<Aanleiding> geefAanleidingenMetLegeEinddatum();
	
	List <Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene);
}
