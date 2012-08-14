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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class WaardeShowController extends AbstractCommandController {
	private OnderhoudenWaardeService onderhoudenWaardeService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter ShowWaardeController:handle(request, response");

		ModelAndView mav = new ModelAndView("show");
		
		Waarde waarde = (Waarde) command;
		Betrokkene betrokkene = waarde.getBetrokkene();
		
		Waarde waardeInstance = this.onderhoudenWaardeService.geefWaarde(betrokkene, waarde.getDatumIngang(), waarde.getDatumTijdGeregistreerd());
		
		Onderzoek onderzoekCommand = new Onderzoek();
		onderzoekCommand.setBetrokkene(betrokkene);
		onderzoekCommand.setDatumIngang(waarde.getDatumIngang());
		Onderzoek huidigOnderzoek = this.onderhoudenOnderzoekService.geefOnderzoek(onderzoekCommand);

		mav.addObject("waardeInstance", waardeInstance);
		mav.addObject("onderzoekInstance", huidigOnderzoek);

		this.logger.debug("Leave ShowWaardeController:handle(request, response");

		return mav;
	}
	
	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("d-M-y"), false);
        binder.registerCustomEditor(Date.class, editor);
    }

	public void setOnderhoudenWaardeService(final OnderhoudenWaardeService onderhoudenWaardeService) {
		this.onderhoudenWaardeService = onderhoudenWaardeService;
	}

	public void setOnderhoudenOnderzoekService(OnderhoudenOnderzoekService onderhoudenOnderzoekService) {
		this.onderhoudenOnderzoekService = onderhoudenOnderzoekService;
	}
}
