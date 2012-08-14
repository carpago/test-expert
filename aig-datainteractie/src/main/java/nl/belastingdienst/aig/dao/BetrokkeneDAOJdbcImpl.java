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

import org.springframework.dao.support.DataAccessUtils;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.util.AbstractJdbcDAO;

/**
 * JdbcDaoImpl voor het ophalen van Betrokkene
 * 
 * @author manef00
 */
public class BetrokkeneDAOJdbcImpl extends AbstractJdbcDAO implements BetrokkeneDAO {
	
	private String querySelectWhereBsnJaar;
	
	private String querySelectWhereBsn;
	
	private String querySelectAll;

	public void setQuerySelectWhereBsnJaar(String querySelectWhereBsnJaar) {
		this.querySelectWhereBsnJaar = querySelectWhereBsnJaar;
	}

	public void setQuerySelectWhereBsn(String querySelectWhereBsn) {
		this.querySelectWhereBsn = querySelectWhereBsn;
	}

	public void setQuerySelectAll(String querySelectAll) {
		this.querySelectAll = querySelectAll;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Betrokkene> findAllWhere(Integer burgerservicenummer) {
		this.logger.debug("Enter BetrokkeneDAO::findAllWhere.");
		List<Betrokkene> result = this.getJdbcTemplate()
			.query(querySelectWhereBsn, new Object[] { burgerservicenummer }, this);

		this.logger.debug("Leave BetrokkeneDAO::findAllWhere.");
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Betrokkene mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		this.logger.debug("Enter BetrokkeneDAO::mapRow");
		Betrokkene betrokkene = new Betrokkene(this.getInteger(rs, "BTK_BSN"), this.getShort(rs, "BTK_BELJAAR"));
		betrokkene.setGeldigeActueleWaarde(this.getInteger(rs, "BTK_WAARDE_GELDIG"));
		betrokkene.setIndicatieInOnderzoek(this.getString(rs, "BTK_IND_IN_ONDERZ"));
		this.logger.debug("Leave BetrokkeneDAO::mapRow");

		return betrokkene;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Betrokkene> retrieveAll() {
		this.logger.debug("Enter BetrokkeneDAO::retrieveAll");

		List<Betrokkene> result = this.selectAll();

		this.logger.debug("Leave BetrokkeneDAO::retrieveAll");

		return result;
	}

	/**
	 * @return Alle Betrokkenen
	 */
	@SuppressWarnings("unchecked")
	private List<Betrokkene> selectAll() {
		this.logger.debug("Enter BetrokkeneDAO::selectAll");

		List<Betrokkene> result = this.getJdbcTemplate()
			.query(this.getQuerySelectAll(), this);

		this.logger.debug("Leave BetrokkeneDAO::selectAll");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Betrokkene retrieveByKey(Short belastingJaar, Integer burgerservicenummer) {
		this.logger.debug("Enter BetrokkeneDAO::findAllWhere.");
		final List<Betrokkene> results =
				this.getJdbcTemplate()
					.query(querySelectWhereBsnJaar,
							new Object[] { belastingJaar, burgerservicenummer }, this);

		this.logger.debug("Leave BetrokkeneDAO::findAllWhere.");
		
		return (Betrokkene) DataAccessUtils.uniqueResult(results);		
	}
}
