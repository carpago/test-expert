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
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.util.AbstractJdbcDAO;

/**
 * JdbcDaoImpl voor het ophalen van Melding
 * 
 * @author manef00
 */
public class MeldingDAOJdbcImpl extends AbstractJdbcDAO implements MeldingDAO {

	private String querySelectWhereBsnJaarBerichtkenmerkDerden;
	private String querySelectWhereBsnJaarBerichtkenmerkAig;
	private String querySelectWhereStatusHandmatigeAfhandeling;

	/**
	 * {@inheritDoc}
	 */
	public List<Melding> findAllWhere(Betrokkene betrokkene, String berichtKenmerkDerden) {
		this.logger.debug("Enter OnderzoekDAO::findAllWhere.");

		List<Melding> result = this.findAllWhere(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar(),
				berichtKenmerkDerden);

		this.logger.debug("Leave OnderzoekDAO::findAllWhere.");

		return result;
	}

	public Melding mapRow(ResultSet rs, int rowNum) throws SQLException {
		this.logger.debug("Enter MeldingDAO::mapRow");

		Melding melding = new Melding();
		melding.setBetrokkene(new Betrokkene(this.getInteger(rs, "AIO_BTK_BSN"), this.getShort(rs, "AIO_BTK_BELJAAR")));
		melding.setBerichtkenmerkAig(this.getString(rs, "MEL_BERICHTKENM"));
		melding.setBerichtkenmerkDerden(this.getString(rs, "MEL_BERICHTKENM_DERDEN"));
		melding.setDatumPlaatsGevonden(this.getSqlDate(rs, "MEL_DAT_PLTS_GVNDN"));
		melding.setDatumTijdRegistratie(this.getTimestamp(rs, "MEL_TS_REG"));
		melding.setFiscaleBron(this.getString(rs, "MEL_FISC_BRON"));
		melding.setOorspronkelijkeMeldingNaam(this.getString(rs, "MEL_OORSPR_MELD_NAAM"));
		melding.setOpdrachtIdTaak(this.getString(rs, "MEL_TAAKOPDR_ID"));
		melding.setOpdrachtIdVktk(this.getString(rs, "MEL_VKTK_OPDR_ID"));
		melding.setVerkorteMeldingNaam(this.getString(rs, "MEL_VERK_MELD_NAAM"));
		melding.setWaarde(this.getInteger(rs, "MEL_WAARDE"));

		this.logger.debug("Leave MeldingDAO::mapRow");

		return melding;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Melding> retrieveAll() {
		this.logger.debug("Enter MeldingDAO::retrieveAll");

		List<Melding> result = this.selectAll();

		this.logger.debug("Leave MeldingDAO::retrieveAll");

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Melding> selectAll() {
		this.logger.debug("Enter MeldingDAO::selectAll");

		List<Melding> result = this.getJdbcTemplate().query(this.getQuerySelectAll(), this);

		this.logger.debug("Leave MeldingDAO::selectAll");

		return result;
	}

	private Melding selectByKey(Integer burgerservicenummer, Short belastingJaar, String berichtKenmerkAig) {
		this.logger.debug("Enter MeldingDAO::selectByKey");
		Melding result = (Melding) this.getJdbcTemplate().query(this.querySelectWhereBsnJaarBerichtkenmerkAig,
				new Object[] { belastingJaar, burgerservicenummer, berichtKenmerkAig }, this);

		this.logger.debug("Leave MeldingDAO::selectByKey");

		return result;
	}

	private List<Melding> findAllWhere(Integer burgerservicenummer, Short belastingJaar, String berichtKenmerkAig) {
		this.logger.debug("Enter MeldingDAO::selectByKey");
		List<Melding> result = this.getJdbcTemplate().query(this.querySelectWhereBsnJaarBerichtkenmerkDerden,
				new Object[] { belastingJaar, burgerservicenummer, berichtKenmerkAig }, this);

		this.logger.debug("Leave MeldingDAO::selectByKey");

		return result;
	}

	public List<Melding> findAllWhereStatusHandmatigeAfhandeling() {
		List<Melding> result = this.getJdbcTemplate().query(this.querySelectWhereStatusHandmatigeAfhandeling, this);

		return result;
	}

	public Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig) {
		return this.selectByKey(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar(), berichtkenmerkAig);
	}

	public void setQuerySelectWhereBsnJaarBerichtkenmerkAig(String querySelectWhereBsnJaarBerichtkenmerkAig) {
		this.querySelectWhereBsnJaarBerichtkenmerkAig = querySelectWhereBsnJaarBerichtkenmerkAig;
	}

	public void setQuerySelectWhereBsnJaarBerichtkenmerkDerden(String querySelectWhereBsnJaarBerichtkenmerkDerden) {
		this.querySelectWhereBsnJaarBerichtkenmerkDerden = querySelectWhereBsnJaarBerichtkenmerkDerden;
	}

	public void setQuerySelectWhereStatusHandmatigeAfhandeling(String querySelectWhereStatusHandmatigeAfhandeling) {
		this.querySelectWhereStatusHandmatigeAfhandeling = querySelectWhereStatusHandmatigeAfhandeling;
	}

}
