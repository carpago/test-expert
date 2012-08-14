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

import org.apache.log4j.Logger;

import nl.belastingdienst.aig.dao.BetrokkeneDAO;

/**
 * Business service implementatie voor het onderhouden van Betrokkenen.
 * 
 * @author manef00
 */
public class OnderhoudenBetrokkeneServiceImpl implements OnderhoudenBetrokkeneService {

	private BetrokkeneDAO betrokkeneDAO = null;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	public List<Betrokkene> findAllWhere(Integer burgerservicenummer) {

		this.logger.debug("Enter OnderhoudenBetrokkeneServiceImpl::findAllWhere(Integer)");

		if (getBetrokkeneDAO() == null) {
			return null;
		}

		List<Betrokkene> betrokkeneLijst = getBetrokkeneDAO().findAllWhere(burgerservicenummer);

		this.logger.debug("Leave OnderhoudenBetrokkeneServiceImpl::findAllWhere(Integer)");

		return betrokkeneLijst;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Betrokkene> getAllBetrokkene() {

		this.logger.debug("Enter OnderhoudenBetrokkeneServiceImpl::getAllBetrokkene()");

		if (getBetrokkeneDAO() == null) {
			return null;
		}

		List<Betrokkene> betrokkeneLijst = getBetrokkeneDAO().retrieveAll();

		this.logger.debug("Leave OnderhoudenBetrokkeneServiceImpl::getAllBetrokkene()");

		return betrokkeneLijst;
	}

	/**
	 * {@inheritDoc}
	 */
	public Betrokkene getBetrokkeneByKey(Short belastingJaar, Integer burgerservicenummer) {

		this.logger.debug("Enter OnderhoudenBetrokkeneServiceImpl::getBetrokkeneByKey(Short, Integer)");

		if (getBetrokkeneDAO() == null) {
			return null;
		}

		 Betrokkene betrokkene = getBetrokkeneDAO().retrieveByKey(belastingJaar, burgerservicenummer);

		this.logger.debug("Leave OnderhoudenBetrokkeneServiceImpl::getBetrokkeneByKey(Short, Integer)");

		return betrokkene;
	}

	/**
	 * @return .
	 */
	public BetrokkeneDAO getBetrokkeneDAO() {
		return this.betrokkeneDAO;
	}

	/**
	 * @param betrokkeneDAO
	 */
	public void setBetrokkeneDAO(BetrokkeneDAO betrokkeneDAO) {
		this.betrokkeneDAO = betrokkeneDAO;
	}
}
