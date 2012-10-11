package nl.belastingdienst.aig.melding;

import nl.carpago.unittestgenerator.annotation.Expect;

public final class PersoonDAO {
	
	@Expect(in="number", out="string")
	public String getSofi(int number) {
		return Integer.toString(number);
	}
}