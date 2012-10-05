package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.onderzoek.Onderzoek;

public interface AanleidingDAO {

	Aanleiding retrieve(Melding melding);

	Aanleiding findByKey(String berichtkenmerkAig);

	List<Aanleiding> findAllWhere(Onderzoek onderzoekCommand);

}
