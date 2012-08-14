package nl.belastingdienst.aig.waarde;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.webstraat.wcb.datatypes.SofiNummer;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public abstract class BaseValidator implements Validator {

	Logger logger = Logger.getLogger(this.getClass());

	final int MINIMAALJAAR = 2008;

	final int MAXIMAALJAAR = 9999;
	
	//rloman: refactoren. BaseValidator is wel handige naamgeving ?

	public void validate(Object command, Errors errors) {

		logger.debug("Enter BAseValidator::validate(Object, Errors");

		Betrokkene betrokkene = (Betrokkene) command;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "betrokkene.burgerservicenummer", "required.bsn", "Moet gevuld zijn.");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "betrokkene.belastingJaar", "required.belastingJaar", "Moet gevuld zijn.");

		// indien lege attributen gevonden dan is validatie op juistheid
		// triviaal dus nog niet nodig.
		if (errors.hasErrors()) {
			return;
		}

		if (!this.validateBelastingJaar(betrokkene)) {
			errors.rejectValue("betrokkene.belastingJaar", "ongeldig.belastingJaar");
		}
		if (!this.validateBurgerservicenummer(betrokkene)) {
			errors.rejectValue("betrokkene.burgerservicenummer", "typeMismatch.burgerservicenummer");
		}

		logger.debug("Leave WaardeValidator::validate(Object, Errors");
	}

	public boolean validateBurgerservicenummer(Betrokkene betrokkene) {
		try {
			String metVoorloopnullen = "" + betrokkene.getBurgerservicenummer();
			metVoorloopnullen = "000000000" + metVoorloopnullen;
			metVoorloopnullen = metVoorloopnullen.substring(metVoorloopnullen.length() - 9);
			SofiNummer.parseSofiNummer(metVoorloopnullen);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean validateBelastingJaar(Betrokkene betrokkene) {
		if ((betrokkene.getBelastingJaar() < this.MINIMAALJAAR) || (betrokkene.getBelastingJaar() > this.MAXIMAALJAAR)) {
			return false;
		} else {
			return true;
		}

	}

}