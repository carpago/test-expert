package nl.belastingdienst.aig.onderzoek;

import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.aanleiding.OnderhoudenAanleidingService;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.betrokkene.OnderhoudenBetrokkeneService;
import nl.belastingdienst.aig.dao.OnderzoekDAO;
import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingService;
import nl.belastingdienst.aig.waarde.OnderhoudenWaardeService;
import nl.belastingdienst.aig.waarde.Waarde;

public class OnderhoudenOnderzoekServiceImpl implements OnderhoudenOnderzoekService {

	private OnderzoekDAO onderzoekDAO;

	private OnderhoudenAanleidingService onderhoudenAanleidingService;

	private OnderhoudenWaardeService onderhoudenWaardeService;

	private OnderhoudenMeldingService onderhoudenMeldingService;

	private OnderhoudenBetrokkeneService onderhoudenBetrokkeneService;

	public Onderzoek geefCompleetOnderzoek(Onderzoek onderzoekCommand) {

		Onderzoek onderzoek = getOnderzoekDAO().retrieve(onderzoekCommand.getBetrokkene(), onderzoekCommand.getDatumIngang());

		onderzoek.setAanleidingList(getOnderhoudenAanleidingService().geefAanleidingenVoor(onderzoekCommand));

		for (Aanleiding aanleiding : onderzoek.getAanleidingList()) {
			aanleiding = getOnderhoudenAanleidingService().geefAanleidingByKey(aanleiding.getMelding()
				.getBerichtkenmerkAig());

			Melding melding = getOnderhoudenMeldingService().geefMelding(aanleiding.getMelding().getBetrokkene(),aanleiding.getMelding()
				.getBerichtkenmerkAig());

			aanleiding.setMelding(melding);

			Waarde waarde1 = getOnderhoudenWaardeService().geefWaarde(aanleiding.getAigWaarde1()
				.getBetrokkene(), aanleiding.getAigWaarde1()
				.getDatumIngang(), aanleiding.getAigWaarde1()
				.getDatumTijdGeregistreerd());

			aanleiding.setAigWaarde1(waarde1);

			Waarde waarde2 = getOnderhoudenWaardeService().geefWaarde(aanleiding.getAigWaarde2()
				.getBetrokkene(), aanleiding.getAigWaarde2()
				.getDatumIngang(), aanleiding.getAigWaarde2()
				.getDatumTijdGeregistreerd());

			aanleiding.setAigWaarde2(waarde2);
		}

		return onderzoek;
	}

	public Onderzoek geefOnderzoek(Onderzoek onderzoekCommand) {

		Onderzoek onderzoek = getOnderzoekDAO().retrieve(onderzoekCommand.getBetrokkene(), onderzoekCommand.getDatumIngang());
		onderzoek.setAanleidingList(getOnderhoudenAanleidingService().geefAanleidingenVoor(onderzoekCommand));

		return onderzoekCommand;
	}

	public List<Onderzoek> geefOnderzoekList(Betrokkene betrokkeneCommand) {

		List<Onderzoek> result = getOnderzoekDAO().findAllWhere(betrokkeneCommand);

		return result;
	}

	public OnderhoudenAanleidingService getOnderhoudenAanleidingService() {
		return this.onderhoudenAanleidingService;
	}

	public OnderhoudenBetrokkeneService getOnderhoudenBetrokkeneService() {
		return this.onderhoudenBetrokkeneService;
	}

	public OnderhoudenMeldingService getOnderhoudenMeldingService() {
		return this.onderhoudenMeldingService;
	}

	public OnderhoudenWaardeService getOnderhoudenWaardeService() {
		return this.onderhoudenWaardeService;
	}

	public OnderzoekDAO getOnderzoekDAO() {
		return this.onderzoekDAO;
	}

	public void setOnderhoudenAanleidingService(OnderhoudenAanleidingService onderhoudenAanleidingService) {
		this.onderhoudenAanleidingService = onderhoudenAanleidingService;
	}

	public void setOnderhoudenBetrokkeneService(OnderhoudenBetrokkeneService onderhoudenBetrokkeneService) {
		this.onderhoudenBetrokkeneService = onderhoudenBetrokkeneService;
	}

	public void setOnderhoudenMeldingService(OnderhoudenMeldingService onderhoudenMeldingService) {
		this.onderhoudenMeldingService = onderhoudenMeldingService;
	}

	public void setOnderhoudenWaardeService(OnderhoudenWaardeService onderhoudenWaardeService) {
		this.onderhoudenWaardeService = onderhoudenWaardeService;
	}

	public void setOnderzoekDAO(OnderzoekDAO onderzoekDAO) {
		this.onderzoekDAO = onderzoekDAO;
	}
}
