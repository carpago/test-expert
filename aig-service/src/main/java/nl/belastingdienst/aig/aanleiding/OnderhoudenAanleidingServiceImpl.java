package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.AanleidingDAO;
import nl.belastingdienst.aig.dao.BehandelenAanleidingDAO;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;

public class OnderhoudenAanleidingServiceImpl implements OnderhoudenAanleidingService {

	private AanleidingDAO aanleidingDAO;
	private BehandelenAanleidingDAO behandelenAanleidingDAO;

	/**
	 * {@inheritDoc}
	 */
	public Aanleiding geefAanleiding(Aanleiding aanleidingCommand) {
		return getAanleidingDAO().retrieve(aanleidingCommand.getMelding());
	}

	/**
	 * @param berichtkenmerkAig
	 * @return Aanleiding voor sleutel
	 */
	@CreateUnittest(in="berichtkenmerkAig", out="aanleiding")
	public Aanleiding geefAanleidingByKey(String berichtkenmerkAig) {
		
		Aanleiding aanleiding = getAanleidingDAO().findByKey(berichtkenmerkAig);
		// aanleiding.setBeeindigDezeAanleidingMetActueleAig(true);
		// aanleiding.getAigWaarde1().getBetrokkene().setBelastingJaar((short) 2002);
		
		return aanleiding;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Aanleiding> geefAanleidingenVoor(Onderzoek onderzoekCommand) {

		return getAanleidingDAO().findAllWhere(onderzoekCommand);
	}
	
		
	public AanleidingDAO getAanleidingDAO() {
		return this.aanleidingDAO;
	}

	/**
	 * @param aanleidingDAO
	 */
	public void setAanleidingDAO(AanleidingDAO aanleidingDAO) {
		this.aanleidingDAO = aanleidingDAO;
	}

	public List<Aanleiding> geefAanleidingenMetLegeEinddatum() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Aanleiding> geefAanleidingenVoorBetrokkene(Betrokkene betrokkene) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBehandelenAanleidingDAO(BehandelenAanleidingDAO behandelenAanleidingDAO) {
		this.behandelenAanleidingDAO = behandelenAanleidingDAO;
	}
}
