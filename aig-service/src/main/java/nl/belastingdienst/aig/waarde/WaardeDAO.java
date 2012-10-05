package nl.belastingdienst.aig.waarde;

import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

public interface WaardeDAO {

	List<Waarde> findAllWhere(Integer burgerservicenummer);

	List<Waarde> findAllWhere(Betrokkene betrokkene);

	List<Waarde> retrieveAll();

	Waarde retrieve(Betrokkene betrokkene, Date datumIngang,
			Date datumTijdGeregistreerd);

}
