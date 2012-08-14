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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.support.DataAccessUtils;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.util.AbstractJdbcDAO;
import nl.belastingdienst.aig.waarde.Waarde;

/**
 * dbcDaoImpl voor het ophalen van Aanleiding
 * 
 * @author manef00
 */
public class AanleidingDAOJdbcImpl extends AbstractJdbcDAO implements AanleidingDAO {
	
	private String querySelectWhereMelding;
	private String querySelectWhereOnderzoek;
	private String querySelectWhereBerichtKenmerkAigselect;
	private String querySelectAll;
	private String querySelectAllWhereLegeEinddatum;
	private String querySelectAllWhereBetrokkene;

	public void setQuerySelectWhereMelding(String querySelectWhereMelding) {
		this.querySelectWhereMelding = querySelectWhereMelding;
	}

	public void setQuerySelectWhereOnderzoek(String querySelectWhereOnderzoek) {
		this.querySelectWhereOnderzoek = querySelectWhereOnderzoek;
	}

	public void setQuerySelectWhereBerichtKenmerkAigselect(String querySelectWhereBerichtKenmerkAigselect) {
		this.querySelectWhereBerichtKenmerkAigselect = querySelectWhereBerichtKenmerkAigselect;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Aanleiding> findAllWhere(Onderzoek onderzoek) {
		this.logger.debug("Enter AanleidingDAO::findAllWhere.");

		List<Aanleiding> result = this.selectByKey(onderzoek.getBetrokkene()
			.getBurgerservicenummer(), onderzoek.getBetrokkene()
			.getBelastingJaar(), onderzoek.getDatumIngang());

		this.logger.debug("Leave AanleidingDAO::findAllWhere.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Aanleiding findByKey(String berichtkenmerkAig) {
		this.logger.debug("Enter AanleidingDAO::findByKey.");

		Aanleiding result = this.selectByKey(berichtkenmerkAig);

		this.logger.debug("Leave AanleidingDAO::findByKey.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Aanleiding mapRow(ResultSet rs, int rowNum) throws SQLException {
		this.logger.debug("Enter AanleidingDAO::mapRow");
		Aanleiding aanleiding = new Aanleiding();
		Melding melding = new Melding();
		melding.setBetrokkene(new Betrokkene(this.getInteger(rs, "AAN_MEL_BTK_BSN"), this.getShort(rs, "AAN_MEL_BTK_BELJAAR")));
		melding.setBerichtkenmerkAig(this.getString(rs, "AAN_MEL_BERICHTKENM"));
		aanleiding.setMelding(melding);
		Onderzoek onderzoek = new Onderzoek();
		onderzoek.setBetrokkene(new Betrokkene(this.getInteger(rs, "AAN_AIO_BTK_BSN"), this.getShort(rs, "AAN_AIO_BTK_BELJAAR")));
		onderzoek.setDatumIngang(this.getSqlDate(rs, "AAN_AIO_DAT_INGANG"));
		aanleiding.setAigOnderzoek(onderzoek);
		Waarde waarde1 = new Waarde();
		waarde1.setBetrokkene(new Betrokkene(this.getInteger(rs, "AAN_AIW_BTK_BSN_1"), this.getShort(rs, "AAN_AIW_BTK_BELJAAR_1")));
		waarde1.setDatumIngang(this.getSqlDate(rs, "AAN_AIW_DAT_GELDIG_BEGIN_1"));
		waarde1.setDatumTijdGeregistreerd(this.getTimestamp(rs, "AAN_AIW_TS_REG_1"));
		aanleiding.setAigWaarde1(waarde1);
		Waarde waarde2 = new Waarde();
		waarde2.setBetrokkene(new Betrokkene(this.getInteger(rs, "AAN_AIW_BTK_BSN_2"), this.getShort(rs, "AAN_AIW_BTK_BELJAAR_2")));
		waarde2.setDatumIngang(this.getSqlDate(rs, "AAN_AIW_DAT_GELDIG_BEGIN_2"));
		waarde2.setDatumTijdGeregistreerd(this.getTimestamp(rs, "AAN_AIW_TS_REG_2"));
		aanleiding.setAigWaarde2(waarde2);
		aanleiding.setRegitratieTijdstipBegin(this.getTimestamp(rs, "AAN_TS_REG_BEGIN"));
		aanleiding.setRegitratieTijdstipEinde(this.getTimestamp(rs, "AAN_TS_REG_EINDE"));
		aanleiding.setRedenEinde(this.getString(rs, "AAN_REDEN_EINDE"));
		aanleiding.setDatumOnherroepelijk(this.getSqlDate(rs, "AAN_DATUM_ONHERROEP"));

		this.logger.debug("Leave AanleidingDAO::mapRow");

		return aanleiding;
	}

	/**
	 * {@inheritDoc}
	 */
	public Aanleiding retrieve(Melding melding) {
		this.logger.debug("Enter AanleidingDAO::retrieve.");

		Aanleiding result = this.selectByKey(melding.getBetrokkene()
			.getBurgerservicenummer(), melding.getBetrokkene()
			.getBelastingJaar());

		this.logger.debug("Leave AanleidingDAO::retrieve.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Aanleiding> retrieveAll() {
		this.logger.debug("Enter AanleidingDAO::retrieveAll");

		List<Aanleiding> result = this.selectAll();

		this.logger.debug("Leave AanleidingDAO::retrieveAll");

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Aanleiding> selectAll() {
		this.logger.debug("Enter AanleidingDAO::selectAll");

		List<Aanleiding> result = this.getJdbcTemplate()
			.query(this.getQuerySelectAll(), this);

		this.logger.debug("Leave AanleidingDAO::selectAll");

		return result;
	}

	/**
	 * @param burgerservicenummer
	 * @param belastingJaar
	 * @return
	 */
	//rloman: dit lijkt me niet goed! @Fabian: Kan toch niet selectByKey en een List komen uit de backend?
	@SuppressWarnings("unchecked")
	private Aanleiding selectByKey(Integer burgerservicenummer, Short belastingJaar) {
		this.logger.debug("Enter AanleidingDAO::selectByKey");
		List<Aanleiding> results = this.getJdbcTemplate()
			.query(this.querySelectWhereMelding, new Object[] { belastingJaar, burgerservicenummer }, this);

		this.logger.debug("Leave AanleidingDAO::selectByKey");

		return (Aanleiding) DataAccessUtils.uniqueResult(results);
	}

	/**
	 * @param onderzoek
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Aanleiding> selectByKey(Integer burgerservicenummer, Short belastingJaar, Date datumIngang) {
		this.logger.debug("Enter AanleidingDAO::selectByKey");
		List<Aanleiding> results = this.getJdbcTemplate()
			.query(this.querySelectWhereOnderzoek, new Object[] { burgerservicenummer, belastingJaar, datumIngang }, this);

		this.logger.debug("Leave AanleidingDAO::selectByKey");

		return results;
	}

	/**
	 * @param berichtkenmerkAig
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Aanleiding selectByKey(String berichtkenmerkAig) {
		this.logger.debug("Enter AanleidingDAO::selectByKey");
		List<Aanleiding> results = this.getJdbcTemplate()
			.query(this.querySelectWhereBerichtKenmerkAigselect, new Object[] { berichtkenmerkAig }, this);

		this.logger.debug("Leave AanleidingDAO::selectByKey");

		return (Aanleiding) DataAccessUtils.uniqueResult(results);
	}

	@SuppressWarnings("unchecked")
	public List<Aanleiding> geefAanleidingenMetLegeEinddatum() {
		this.logger.debug("Enter AanleidingDAO::geefAanleidingenMetLegeEinddatum");
		List<Aanleiding> results = this.getJdbcTemplate()
			.query(this.querySelectAllWhereLegeEinddatum, this);

		this.logger.debug("Leave AanleidingDAO::geefAanleidingenMetLegeEinddatum");

		return results;
	}

	public void setQuerySelectAllWhereLegeEinddatum(String querySelectAllWhereLegeEinddatum) {
		this.querySelectAllWhereLegeEinddatum = querySelectAllWhereLegeEinddatum;
	}

	public List<Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene) {
		this.logger.debug("Enter AanleidingDAO::geefAanleidingenVoorBetrokkene");
		List<Aanleiding> results = this.getJdbcTemplate()
			.query(this.querySelectAllWhereBetrokkene, new Object[] {betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar()}, this);

		this.logger.debug("Leave AanleidingDAO::geefAanleidingenMetLegeEinddatum");

		return results;
	}

	public void setQuerySelectAllWhereBetrokkene(String querySelectAllWhereBetrokkene) {
		this.querySelectAllWhereBetrokkene = querySelectAllWhereBetrokkene;
	}
}
