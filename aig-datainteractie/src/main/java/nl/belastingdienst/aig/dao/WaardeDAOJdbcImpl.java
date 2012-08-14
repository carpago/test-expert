/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: lomar00
 *          Copyright: (c) 2010 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
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

package nl.belastingdienst.aig.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.support.DataAccessUtils;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.util.AbstractJdbcDAO;
import nl.belastingdienst.aig.waarde.Waarde;

/**
 * JdbcDaoImpl voor het ophalen van Waarde !
 * 
 * @author lomar00
 */
public class WaardeDAOJdbcImpl extends AbstractJdbcDAO implements WaardeDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private String querySelectWhereBsnJaarDatumingangDatumgeregistreerd;

	private String querySelectWhereBsnJaar;

	private String querySelectWhereBsn;

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> findAllWhere(Betrokkene betrokkene) {
		this.logger.debug("Enter WaardeDAO::findAllWhere.");

		List<Waarde> result = this.selectByKey(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar());

		this.logger.debug("Leave WaardeDAO::findAllWhere.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> findAllWhere(Integer burgerservicenummer) {
		this.logger.debug("Enter WaardeDAO::findAllWhere.");

		List<Waarde> result = this.selectByKey(burgerservicenummer);

		this.logger.debug("Leave WaardeDAO::findAllWhere.");

		return result;
	}

	/**
	 * @return Retourneert querySelectByBsn.
	 */
	public String getQuerySelectWhereBsn() {
		return this.querySelectWhereBsn;
	}

	/**
	 * @return Retourneert querySelectWhereBsnJaar.
	 */
	public String getQuerySelectWhereBsnJaar() {
		return this.querySelectWhereBsnJaar;
	}

	/**
	 * @return Retourneert querySelectWhereBsnJaarDatumingangDatumgeregistreerd.
	 */
	public String getQuerySelectWhereBsnJaarDatumingangDatumgeregistreerd() {
		return this.querySelectWhereBsnJaarDatumingangDatumgeregistreerd;
	}

	/**
	 * {@inheritDoc}
	 */
	public Waarde mapRow(ResultSet rs, int rowNum) throws SQLException {
		this.logger.debug("Enter WaardeDAO::mapRow");
		Waarde waarde = new Waarde();
		waarde.setBetrokkene(new Betrokkene(this.getInteger(rs, "AIW_BTK_BSN"), this.getShort(rs, "AIW_BTK_BELJAAR")));
		waarde.setDatumIngang(this.getSqlDate(rs, "AIW_DAT_GELDIG_BEGIN"));
		waarde.setDatumTijdGeregistreerd(this.getJavaUtilTimestamp(rs, "AIW_TS_REG"));
		waarde.setDatumEinde(this.getSqlDate(rs, "AIW_DAT_GELDIG_EIND"));
		waarde.setDatumBepalingBron(this.getSqlDate(rs, "AIW_DAT_BRON_BEPALING"));
		waarde.setAardBepalingCode(this.getString(rs, "aiw_omschrijving_aard_bepaling"));
		waarde.setGrondslagCode(this.getString(rs, "aiw_omschrijving_grondslag"));
		waarde.setWaarde(this.getDouble(rs, "AIW_AIG_WAARDE"));
		waarde.setDossierVolgnummer(this.getInteger(rs, "AIW_DOSSIER_VOLGNR"));
		waarde.setDatumVervallen(this.getSqlDate(rs, "AIW_DAT_VERVALLEN"));

		this.logger.debug("Leave WaardeDAO::mapRow");

		return waarde;
	}

	/**
	 * {@inheritDoc}
	 */
	public Waarde retrieve(Betrokkene betrokkene, Date datumIngang, Date datumTijdGeregistreerd) {
		this.logger.debug("Enter WaardeDAO::retrieve.");

		Waarde result =
				this.selectByKey(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar(), datumIngang,
						datumTijdGeregistreerd);

		this.logger.debug("Leave WaardeDAO::retrieve.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> retrieveAll() {
		this.logger.debug("Enter WaardeDAO::retrieveAll");

		List<Waarde> result = this.selectAll();

		this.logger.debug("Leave WaardeDAO::retrieveAll");

		return result;
	}

	/**
	 * @return alle Waarden
	 */
	@SuppressWarnings("unchecked")
	private List<Waarde> selectAll() {
		this.logger.debug("Enter WaardeDAO::selectAll");

		List<Waarde> result = this.getJdbcTemplate()
			.query(this.getQuerySelectAll(), this);

		this.logger.debug("Leave WaardeDAO::selectAll");

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Waarde> selectByKey(Integer burgerservicenummer) {
		this.logger.debug("Enter WaardeDAO::selectByKey");

		List<Waarde> results = this.getJdbcTemplate()
			.query(this.getQuerySelectWhereBsn(), new Object[] { burgerservicenummer }, this);

		this.logger.debug("Leave WaardeDAO::selectByKey");

		return results;
	}

	/**
	 * @param burgerservicenummer
	 * @param belastingJaar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Waarde> selectByKey(Integer burgerservicenummer, Short belastingJaar) {
		this.logger.debug("Enter WaardeDAO::selectByKey");
		List<Waarde> results = this.getJdbcTemplate()
			.query(this.getQuerySelectWhereBsnJaar(), new Object[] { burgerservicenummer, belastingJaar }, this);

		this.logger.debug("Leave WaardeDAO::selectByKey");

		return results;
	}

	/**
	 * @param burgerservicenummer
	 * @param belastingJaar
	 * @param datumIngang
	 * @param datumTijdGeregistreerd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Waarde selectByKey(Integer burgerservicenummer, Short belastingJaar, Date datumIngang, Date datumTijdGeregistreerd) {	
			this.logger.debug("Enter WaardeDAO::selectByKey");
			List<Waarde> results =
					this.getJdbcTemplate()
						.query(this.getQuerySelectWhereBsnJaarDatumingangDatumgeregistreerd(),
								new Object[] { belastingJaar, burgerservicenummer, datumIngang, datumTijdGeregistreerd }, this);

			this.logger.debug("Leave WaardeDAO::selectByKey");

			return (Waarde) DataAccessUtils.uniqueResult(results);
	}

	/**
	 * @param querySelectByBsn
	 *            wordt in querySelectByBsn gezet.
	 */
	public void setQuerySelectWhereBsn(String querySelectByBsn) {
		this.querySelectWhereBsn = querySelectByBsn;
	}

	/**
	 * @param querySelectWhereBsnJaar
	 *            wordt in querySelectWhereBsnJaar gezet.
	 */
	public void setQuerySelectWhereBsnJaar(String querySelectWhereBsnJaar) {
		this.querySelectWhereBsnJaar = querySelectWhereBsnJaar;
	}

	/**
	 * @param querySelectWhereBsnJaarDatumingangDatumgeregistreerd
	 *            wordt in querySelectWhereBsnJaarDatumingangDatumgeregistreerd gezet.
	 */
	public void setQuerySelectWhereBsnJaarDatumingangDatumgeregistreerd(String querySelectWhereBsnJaarDatumingangDatumgeregistreerd) {
		this.querySelectWhereBsnJaarDatumingangDatumgeregistreerd = querySelectWhereBsnJaarDatumingangDatumgeregistreerd;
	}
}
