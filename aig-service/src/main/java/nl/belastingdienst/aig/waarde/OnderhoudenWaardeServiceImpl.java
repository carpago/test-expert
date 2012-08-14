/*
 * 

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
package nl.belastingdienst.aig.waarde;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.WaardeDAO;

/**
 * Implementatie van business service voor het onderhouden van Waarde.
 * 
 * @author lomar00
 */
public class OnderhoudenWaardeServiceImpl implements OnderhoudenWaardeService {

	private WaardeDAO waardeDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> findAllWhere(final Integer burgerservicenummer) {
		this.logger.debug("Enter OnderhoudenWaardeServiceImpl::findAllWhere(Integer)");

		final List<Waarde> result = getWaardeDAO().findAllWhere(burgerservicenummer);

		this.logger.debug("Leave OnderhoudenWaardeServiceImpl::findAllWhere");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> findAllWhere(final Short belastingJaar, final Integer burgerservicenummer) {
		this.logger.debug("Enter OnderhoudenWaardeServiceImpl::findAllWhere(Short,Integer)");
		final List<Waarde> result = getWaardeDAO().findAllWhere(new Betrokkene(burgerservicenummer, belastingJaar));

		this.logger.debug("Leave OnderhoudenWaardeServiceImpl::findAllWhere(Short,Integer)");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Waarde> geefAlleWaarden() {
		this.logger.debug("Enter OnderhoudenWaardeServiceImpl::geefAlleWaarden()");
		final List<Waarde> result = getWaardeDAO().retrieveAll();

		this.logger.debug("Leave OnderhoudenWaardeServiceImpl::geefAlleWaarden()");

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Waarde geefWaarde(Betrokkene betrokkene,  Date datumIngang, Date datumTijdGeregistreerd) {
		this.logger.debug("Enter OnderhoudenWaardeServiceImpl::geefWaarde(Short,Integer,String,String)");

		final Waarde waarde =
				getWaardeDAO().retrieve(betrokkene, datumIngang, datumTijdGeregistreerd);

		this.logger.debug("Leave OnderhoudenWaardeServiceImpl::geefWaarde(Short,Integer,String,String)");

		return waarde;
	}

	/**
	 * @return Retourneert waardeDAO.
	 */
	public WaardeDAO getWaardeDAO() {
		return this.waardeDAO;
	}

	/**
	 * @param waardeDAO
	 */
	public void setWaardeDAO(final WaardeDAO waardeDAO) {
		this.waardeDAO = waardeDAO;
	}
}
