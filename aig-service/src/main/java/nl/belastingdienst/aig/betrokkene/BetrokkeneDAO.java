package nl.belastingdienst.aig.betrokkene;

import java.util.List;

public interface BetrokkeneDAO {

	List<Betrokkene> findAllWhere(Integer burgerservicenummer);

	List<Betrokkene> retrieveAll();

	Betrokkene retrieveByKey(Short belastingJaar, Integer burgerservicenummer);

}
