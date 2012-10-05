package nl.belastingdienst.aig.onderzoek;

import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

public interface OnderzoekDAO {

	Onderzoek retrieve(Betrokkene betrokkene, Date datumIngang);

	List<Onderzoek> findAllWhere(Betrokkene betrokkeneCommand);

}
