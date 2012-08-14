/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: Raymond Loman
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

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.waarde.BaseValidator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class MeldingListValidator extends BaseValidator {
	
	private final int AANTAL_INVOERVELDEN = 4;
	
	
	private Logger logger = Logger.getLogger(this.getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(Melding.class);
	}

	@Override
	public void validate(Object command, Errors errors) {
		logger.debug("Enter MeldingListValidator::validate(Object, Errors");
		
		Melding melding = (Melding) command;
		Betrokkene betrokkene = melding.getBetrokkene();
		
		int aantalLegeVelden = 0;
		
		if(betrokkene.getBelastingJaar() == null) {
			aantalLegeVelden++;
		}
		
		if(betrokkene.getBelastingJaar() !=  null && !super.validateBelastingJaar(betrokkene)) {
			aantalLegeVelden++;
		}
		
		if(betrokkene.getBurgerservicenummer() == null) {
			aantalLegeVelden++;
		}
		if(betrokkene.getBurgerservicenummer() != null && !super.validateBurgerservicenummer(betrokkene)) {
			aantalLegeVelden++;
		}
		
		if(StringUtils.isEmpty(melding.getBerichtkenmerkDerden())) {
			aantalLegeVelden++;
		}
		
		if(StringUtils.isEmpty(melding.getStatus())) {
			aantalLegeVelden++;
		}
		
		if(aantalLegeVelden > this.AANTAL_INVOERVELDEN -1) {
			errors.reject("Minimaal 1 criterium is verplicht te vullen.");
		}
		
		logger.debug("Leave MeldingValidator::validate(Object, Errors");
	}
}
