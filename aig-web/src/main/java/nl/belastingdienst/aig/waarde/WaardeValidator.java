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

import nl.belastingdienst.aig.betrokkene.Betrokkene;

import org.springframework.validation.Errors;

public class WaardeValidator extends BaseValidator {

	public boolean supports(final Class clazz) {
		return clazz.equals(Waarde.class);
	}
	
	@Override
	public void validate(final Object command, final Errors errors) {
		
		logger.debug("Enter WaardeValidator::validate(Object, Errors");
		
		Waarde waarde = (Waarde) command;
		Betrokkene betrokkene = waarde.getBetrokkene();
		
		super.validate(betrokkene, errors);
		
		logger.debug("Leave WaardeValidator::validate(Object, Errors");
	
	}
}