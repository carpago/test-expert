package nl.belastingdienst.aig.dao;

import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

public class BehandelenAanleidingDAOCtgImpl implements BehandelenAanleidingDAO {

	public List<Aanleiding> geefAanleidingenMetLegeEinddatum() {
		throw new UnsupportedOperationException();
	}
	
	public List<Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene) {
		throw new UnsupportedOperationException(); // rloman implementeren
	}

	public Aanleiding geefAanleiding(Aanleiding aanleidingCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	public void beeindigAanleiding(Aanleiding deAanleidingDieBeeindigtMoetWorden) {
		// TODO Auto-generated method stub
		
	}

}
