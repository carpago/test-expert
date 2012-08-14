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

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.util.AbstractJdbcDAO;

/**
 * JdbcDaoImpl voor het ophalen van Onderzoek
 * 
 * @author manef00
 */
public class OnderzoekDAOJdbcImpl extends AbstractJdbcDAO implements OnderzoekDAO {

	private String querySelectWhereBsnJaarDatumingang;

	private String querySelectWhereBsnJaar;

	private String querySelectWhereBsn;

	/**
	 * {@inheritDoc}
	 */
	public List<Onderzoek> findAllWhere(Betrokkene betrokkene) {
		this.logger.debug("Enter OnderzoekDAO::findAllWhere.");

		List<Onderzoek> result = this.selectByKey(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar());

		this.logger.debug("Leave OnderzoekDAO::findAllWhere.");

		return result;
	}

	/**
	 * @return Retourneert querySelectWhereBsn.
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
	 * @return Retourneert querySelectWhereBsnJaarDatumingang.
	 */
	public String getQuerySelectWhereBsnJaarDatumingang() {
		return this.querySelectWhereBsnJaarDatumingang;
	}

	/**
	 * {@inheritDoc}
	 */
	public Onderzoek mapRow(ResultSet rs, int rowNum) throws SQLException {
		this.logger.debug("Enter OnderzoekDAO::mapRow");

		Onderzoek onderzoek = new Onderzoek();
		onderzoek.setBetrokkene(new Betrokkene(this.getInteger(rs, "AIO_BTK_BSN"), this.getShort(rs, "AIO_BTK_BELJAAR")));
		onderzoek.setDatumIngang(this.getSqlDate(rs, "AIO_DAT_INGANG"));
		onderzoek.setDatumEinde(this.getSqlDate(rs, "AIO_DAT_EIND"));
		onderzoek.setIndicatieGeattendeerd(this.getString(rs, "AIO_IND_ATT"));
		onderzoek.setKetenVanOnderzoek(this.getString(rs, "AIO_KETEN_ONDERZ"));
		onderzoek.setMedewerker(this.getString(rs, "AIO_MEDEW"));

		this.logger.debug("Leave OnderzoekDAO::mapRow");

		return onderzoek;
	}

	/**
	 * {@inheritDoc}
	 */
	public Onderzoek retrieve(Betrokkene betrokkene, Date datumIngang) {
		this.logger.debug("Enter OnderzoekDAO::findAllWhere.");

		Onderzoek result = this.selectByKey(betrokkene.getBurgerservicenummer(), betrokkene.getBelastingJaar(), datumIngang);

		this.logger.debug("Leave OnderzoekDAO::findAllWhere.");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Onderzoek> retrieveAll() {
		this.logger.debug("Enter OnderzoekDAO::retrieveAll");

		List<Onderzoek> result = this.selectAll();

		this.logger.debug("Leave OnderzoekDAO::retrieveAll");

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Onderzoek> selectAll() {
		this.logger.debug("Enter OnderzoekDAO::selectAll");

		List<Onderzoek> result = this.getJdbcTemplate()
			.query(this.getQuerySelectAll(), this);

		this.logger.debug("Leave OnderzoekDAO::selectAll");

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Onderzoek> selectByKey(Integer burgerservicenummer, Short belastingJaar) {
		this.logger.debug("Enter OnderzoekDAO::selectByKey");
		List<Onderzoek> results = this.getJdbcTemplate()
			.query(this.getQuerySelectWhereBsnJaar(), new Object[] { belastingJaar, burgerservicenummer }, this);

		this.logger.debug("Leave OnderzoekDAO::selectByKey");

		return results;
	}

	@SuppressWarnings("unchecked")
	private Onderzoek selectByKey(Integer burgerservicenummer, Short belastingJaar, Date datumIngang) {
		this.logger.debug("Enter OnderzoekDAO::selectByKey");
		List<Onderzoek> results =
				this.getJdbcTemplate()
					.query(this.getQuerySelectWhereBsnJaarDatumingang(),
							new Object[] { belastingJaar, burgerservicenummer, datumIngang }, this);

		this.logger.debug("Leave OnderzoekDAO::selectByKey");

		return (Onderzoek) DataAccessUtils.uniqueResult(results);
	}

	/**
	 * @param querySelectWhereBsn
	 *            wordt in querySelectWhereBsn gezet.
	 */
	public void setQuerySelectWhereBsn(String querySelectWhereBsn) {
		this.querySelectWhereBsn = querySelectWhereBsn;
	}

	/**
	 * @param querySelectWhereBsnJaar
	 *            wordt in querySelectWhereBsnJaar gezet.
	 */
	public void setQuerySelectWhereBsnJaar(String querySelectWhereBsnJaar) {
		this.querySelectWhereBsnJaar = querySelectWhereBsnJaar;
	}

	/**
	 * @param querySelectWhereBsnJaarDatumingang
	 *            wordt in querySelectWhereBsnJaarDatumingang gezet.
	 */
	public void setQuerySelectWhereBsnJaarDatumingang(String querySelectWhereBsnJaarDatumingang) {
		this.querySelectWhereBsnJaarDatumingang = querySelectWhereBsnJaarDatumingang;
	}
}
