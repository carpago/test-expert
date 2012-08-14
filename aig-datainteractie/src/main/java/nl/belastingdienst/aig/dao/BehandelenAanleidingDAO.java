package nl.belastingdienst.aig.dao;

import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

public interface BehandelenAanleidingDAO {
	
	List<Aanleiding> geefAanleidingenMetLegeEinddatum();
	
	List<Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene);
	
	Aanleiding geefAanleiding(Aanleiding aanleidingCommand);
	
	void beeindigAanleiding(Aanleiding deAanleidingDieBeeindigtMoetWorden);
}
