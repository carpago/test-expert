package nl.belastingdienst;

import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;

public interface MeldingDAO {

	/**
	 * @param betrokkene
	 * @param berichtKenmerk
	 * @return Lijst met Meldingen voor Betrokkene met gegeven berichtKenmerk
	 */
	List<Melding> findAllWhere(Betrokkene betrokkene, String berichtKenmerk);

	@Expect(in = { "betrokkene", "berichtkenmerkAig", "voornaam" }, out = "melding")
	Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig,
			String voornaam);

	List<Melding> findAllWhereStatusHandmatigeAfhandeling();

	void setMelding(String in);

	List<Melding> getLijst();

	@CreateUnittest(out="melding")
	Melding geefMelding();

	@CreateUnittest(in = { "een", "twee" }, out = "drie")
	int telOp(int een, int twee);

}
