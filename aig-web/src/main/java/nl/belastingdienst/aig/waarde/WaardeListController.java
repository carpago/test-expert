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

package nl.belastingdienst.aig.waarde;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import nl.belastingdienst.hit.pit.bo.Opdracht;

/**
 * Deze klass implement de ZoekenController. Hij verzorgt het opzoeken van het waarde domein object. Deze controller haalt de
 * waarden via de service via de de dao uit de db en dispatched dna naar de raadplegen.gsp
 * 
 * 
 * @author Raymond Loman.
 */
public class WaardeListController extends SimpleFormController {

	private OnderhoudenWaardeService onderhoudenWaardeService;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException bindException) throws Exception {

		this.logger.debug("Enter ZoekenWaardeController::onSubmit");

		List waardeInstanceList = null;

		final Waarde waarde = (Waarde) command;

		if ((waarde.getBetrokkene()
			.getBelastingJaar() == null)
				|| (waarde.getBetrokkene()
					.getBelastingJaar()
					.shortValue() == 0)) {
			waardeInstanceList = this.onderhoudenWaardeService.findAllWhere(waarde.getBetrokkene()
				.getBurgerservicenummer());
		} else {
			waardeInstanceList = this.onderhoudenWaardeService.findAllWhere(waarde.getBetrokkene()
				.getBelastingJaar(), waarde.getBetrokkene()
				.getBurgerservicenummer());
		}

		final ModelAndView mav = this.showForm(request, bindException, this.getSuccessView());
		mav.addObject("waardeInstanceList", waardeInstanceList);

		this.logger.debug("Leave ZoekenWaardeController::onSubmit");

		return mav;
	}

	/**
	 * @param onderhoudenWaardeService
	 */
	public void setOnderhoudenWaardeService(final OnderhoudenWaardeService onderhoudenWaardeService) {
		this.onderhoudenWaardeService = onderhoudenWaardeService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response, BindException be)
			throws Exception {

		this.logger.debug("Enter ListWaardeController::showForm(request, response)");

		Integer burgerservicenummer = null;

		ModelAndView mav = super.showForm(request, response, be);
		Opdracht opdracht = (Opdracht) request.getAttribute("opdracht");

		try {
			String bsnWithPrefix = (String) opdracht.getParameterLijst()
				.get("BRI_BSN");
			String bsnWithoutPrefix = bsnWithPrefix.substring(bsnWithPrefix.length() - 9);
			burgerservicenummer = new Integer(bsnWithoutPrefix);
		} catch (final NullPointerException npe) {
			this.logger.info("NullpointerException in ListWaardeController. opdracht.klantId niet gevuld.");
			return mav;
		} catch (final NumberFormatException nfe) {
			this.logger.info("NumberFormatException in ListWaardeController. opdracht.klantId ongeldige integer.");
			return mav;
		} catch (IndexOutOfBoundsException ioe) {
			// only happens when backend delivers illegal bsn
			ioe.printStackTrace();
			this.logger.debug("BurgerservicenummerGegevensController::index::IndexOutOfBoundsException");
			return mav;
		}

		final List<Waarde> waardeInstanceList = this.onderhoudenWaardeService.findAllWhere(burgerservicenummer);

		mav.addObject("waardeInstanceList", waardeInstanceList);

		this.logger.debug("Leave ListWaardeController::showForm");

		return mav;
	}
}
