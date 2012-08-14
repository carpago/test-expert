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

package nl.belastingdienst.aig.melding;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class RegistreerMeldingController extends SimpleFormController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private RegistreerMeldingService registreerMeldingService;
	
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException bindException) throws Exception {
		
		Melding melding = (Melding) command;
		
		logger.debug("Enter MeldingRegistreerController:handle(request, response");
		ModelAndView mav = super.onSubmit(request, response, command, bindException);
		
		this.getRegistreerMeldingService().registreerMelding(melding);
			
		logger.debug("Leave MeldingRegistreerController:handle(request, response");

		return mav;
	}
	
	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("d-M-y"), false);
        binder.registerCustomEditor(Date.class, editor);
    }

	public RegistreerMeldingService getRegistreerMeldingService() {
		return this.registreerMeldingService;
	}

	public void setRegistreerMeldingService(RegistreerMeldingService registreerMeldingService) {
		this.registreerMeldingService = registreerMeldingService;
	}


}
