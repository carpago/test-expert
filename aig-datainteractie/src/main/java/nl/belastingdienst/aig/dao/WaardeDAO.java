/*
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
package nl.belastingdienst.aig.dao;

import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.waarde.Waarde;

/**
 * Interface van DAO voor Waarde objecten. Bevat de standaard CRUD functies
 * (Create, Retrieve, Update en Delete) voor het aanmaken, ophalen, bijwerken en
 * verwijderen van een record in de datastore. En daarnaast een RetrieveAll
 * functie voor het ophalen van alle records in de datastore.
 * 
 * @author Raymond Loman
 */
public interface WaardeDAO {

	/**
	 * @param betrokkene
	 * @return Lijst met waarden van betrokkene
	 */
	List<Waarde> findAllWhere(Betrokkene betrokkene);

	/**
	 * @param burgerservicenummer
	 * @return Lijst met waarden van betrokkene met opgegeven bsn
	 */
	List<Waarde> findAllWhere(Integer burgerservicenummer);

	/**
	 * @param betrokkene
	 * @param datumIngang
	 * @param datumTijdGeregistreerd
	 * @return Waarde voor betreffende Betrokkene en opgegeven
	 *         datumbegingeldigheid en datumtijdregistratie
	 */
	Waarde retrieve(Betrokkene betrokkene, Date datumIngang, Date datumTijdGeregistreerd);

	/**
	 * @return alle Waarden
	 */
	List<Waarde> retrieveAll();
}
