package nl.belastingdienst.aig.melding;

import nl.carpago.testexpert.Persoon;
import nl.carpago.unittestgenerator.annotation.Expect;

public class PersoonDAO {
	
	@Expect(in="number", out="string")
	public String getSofi(int number) {
		return Integer.toString(number);
	}
	
	@Expect(in="number", out="eenAnderPersoon")
	public Persoon getPersoon(int aNumber) {
		return new Persoon(44, "John Doe");
	}
}