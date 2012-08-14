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

package nl.belastingdienst.aig.aanleiding;

import nl.belastingdienst.aig.waarde.BaseValidator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class AanleidingBeeindigValidator extends BaseValidator {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(Aanleiding.class);
	}

	@Override
	public void validate(Object command, Errors errors) {
		logger.debug("Enter AanleidingBeeindigValidator::validate(Object, Errors");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "redenEinde", "required.redenEinde", "Moet gevuld zijn.");
		
		// Ambtenaar dient eerst deze foutmelding af te handelen.
		if(errors.hasErrors()) {
			return;
		}
		
		// hier is redenEinde gevuld, anders komt hij hier niet.
		Aanleiding aanleiding = (Aanleiding) command;
		//rloman:refactoren (instance var of enum)
		if("delta".equals(aanleiding.getRedenEinde()) && !aanleiding.isBeeindigDezeAanleidingMetActueleAig()) {
			errors.reject("Indien nieuwe AIG dan moet u Actueel AIG selecteren(Ja)");
		}
		
		if("anders".equals(aanleiding.getRedenEinde())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "redenEindeAnders", "required.redenEindeAnders", "Moet gevuld zijn.");
		}
		
		logger.debug("Leave AanleidingValidator::validate(Object, Errors");
	}
}
