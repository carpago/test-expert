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

import java.util.Calendar;
import java.util.Date;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.waarde.BaseValidator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class MeldingRegistreerValidator extends BaseValidator {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(Melding.class);
	}

	@Override
	public void validate(Object command, Errors errors) {
		logger.debug("Enter MeldingValidator::validate(Object, Errors");
		Melding melding = (Melding) command;
		Betrokkene betrokkene = melding.getBetrokkene();
		
		super.validate(betrokkene, errors);
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datumPlaatsGevonden", "required.datumPlaatsGevonden", "Moet gevuld zijn.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oorspronkelijkeMeldingNaam", "required.oorspronkelijkeMeldingNaam", "Moet gevuld zijn.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "berichtkenmerkDerden", "required.berichtkenmerkDerden", "Moet gevuld zijn.");
		
		// indien lege attributen gevonden dan is validatie op juistheid triviaal dus nog niet nodig.
		if(errors.hasErrors()) {
			return;
		}
		
		//alle verplichte attributen zijn gevuld. Nu verder met valideren.
		// datumPlaatsgevonden is de enige die nog moet worden gevalideert.
		//rloman: hieronder reactoren en strak testen.
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR) - 1900;
		int validYearMax=year+1;
		//rloman: klopt deze aanroep ? Niet toevallig 2008-1900 - 108 ????
		calendar.set(2008,0,1);  // should be 1-1-2008
		Date valid = calendar.getTime();
		Date datumPlaatsgevonden = melding.getDatumPlaatsGevonden();
		if(datumPlaatsgevonden.compareTo(valid) < 0) {
			errors.rejectValue("datumPlaatsgevonden", "Datum plaatsgevonden moet groter zijn dan 31-12-2007");
		}
		if(datumPlaatsgevonden.getYear()-1900 > validYearMax) {
			errors.rejectValue("datumPlaatsgevonden", "Datum plaatsgevonden mag maximaal huidig jaar + 1 zijn");
		}
		
		//ontwerpwijziging: 0 <= waarde <= 9.999.999.999.999  (dus maximaal 10 biljoen -1 of 10^13 -1
		//rloman: dus wijzigingen van melding.waarde van Integer naar Long of domeinobject Waarde. Overleggen met ...
		
		logger.debug("Leave MeldingValidator::validate(Object, Errors");

	}
}
