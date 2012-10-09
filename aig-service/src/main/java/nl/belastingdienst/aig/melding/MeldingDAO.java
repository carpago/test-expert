package nl.belastingdienst.aig.melding;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;

public interface MeldingDAO {

	/**
	 * @param betrokkene
	 * @param berichtKenmerk
	 * @return Lijst met Meldingen voor Betrokkene met gegeven berichtKenmerk
	 */
	List<Melding> findAllWhere(Betrokkene betrokkene, String berichtKenmerk);

	@CreateUnittest(in = { "betrokkene", "berichtkenmerkAig", "voornaam" }, out = "melding")
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
