package nl.belastingdienst.aig.onderzoek;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.webstraat.wcb.datatypes.SofiNummer;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OnderzoekListValidator implements Validator {

	Logger logger = Logger.getLogger(this.getClass());

	final int MINIMAALJAAR = 2008;

	final int MAXIMAALJAAR = 9999;
	
	public boolean supports(Class clazz) {
		return clazz.equals(Onderzoek.class);
	}
	
	public void validate(Object command, Errors errors) {

		logger.debug("Enter OnderzoekListValidator::validate(Object, Errors");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "betrokkene.burgerservicenummer", "required.bsn", "Moet gevuld zijn.");

		// indien lege attributen gevonden dan is validatie op juistheid
		// triviaal dus nog niet nodig.
		if (errors.hasErrors()) {
			return;
		}
		
		Onderzoek onderzoek = (Onderzoek) command;
		Betrokkene betrokkene = onderzoek.getBetrokkene();
		if (!this.validateBurgerservicenummer(betrokkene)) {
			errors.rejectValue("betrokkene.burgerservicenummer", "typeMismatch.burgerservicenummer");
		}

		logger.debug("Leave OnderzoekListValidator::validate(Object, Errors");
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
}