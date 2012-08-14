/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CAO 
 *            Creator: Fabian van Manen samen met Raymond Loman
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

package nl.belastingdienst.aig.onderzoek;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class OnderzoekListController extends SimpleFormController {

	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;

	private Logger logger = Logger.getLogger(this.getClass());
	
	
	/**
	 * Deze methode wordt aangeroepen dan en slechts dan bij een GET request.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView: bestaande uit lijst van alle handmatig te beoordelen meldingen
	 * @throws Exception
	 */
	public ModelAndView showForm(HttpServletRequest request,HttpServletResponse response) throws Exception {

		logger.debug("Enter OnderzoekListController::showForm(request, response)");
		
		ModelAndView modelAndView = new ModelAndView(this.getFormView());

		logger.debug("Leave MeldingListController::showForm");

		return modelAndView;
	}

	
	
	
	
	
	

	/**
	 * Deze methode wordt aangeroepen dan en slechts dan bij een POST request.
	 * En als er dus een selectiecriteria is ingevoerd voor de onderzoek.
	 * 
	 * Er komt hier een Onderzoek object binnen als command object die moet
	 * voorzien zijn van:
	 * 
	 * - betrokkene.burgerservicenummer - betrokkene.belastingJaar -
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter OnderzoekListController::onSubmit");

		Onderzoek onderzoek = (Onderzoek) command;
		Betrokkene betrokkene = onderzoek.getBetrokkene();

		List<Onderzoek> onderzoekInstanceList = this.onderhoudenOnderzoekService.geefOnderzoekList(betrokkene);

		ModelAndView modelAndView = new ModelAndView(this.getSuccessView());
		modelAndView.addObject("onderzoekInstanceList", onderzoekInstanceList);

		this.logger.debug("Leave OnderzoekListController::onSubmit");

		return modelAndView;
	}








	public void setOnderhoudenOnderzoekService(OnderhoudenOnderzoekService onderhoudenOnderzoekService) {
		this.onderhoudenOnderzoekService = onderhoudenOnderzoekService;
	}
}
