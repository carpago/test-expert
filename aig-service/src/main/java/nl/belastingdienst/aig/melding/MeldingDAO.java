package nl.belastingdienst.aig.melding;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

public interface MeldingDAO {

	List<Melding> findAllWhere(Betrokkene betrokkene,
			String berichtkenmerkDerden);

	List<Melding> findAllWhereStatusHandmatigeAfhandeling();

	void setMelding(String string);

	List<Melding> getLijst();

	Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig,
			String voornaam);

	Melding geefMelding();

	int telOp(int aap, int bees);

}
