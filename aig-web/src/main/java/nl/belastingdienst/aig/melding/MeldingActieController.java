package nl.belastingdienst.aig.melding;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.waarde.OnderhoudenWaardeService;
import nl.belastingdienst.aig.waarde.Waarde;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class MeldingActieController extends AbstractCommandController {

	private OnderhoudenMeldingService onderhoudenMeldingService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private OnderhoudenWaardeService onderhoudenWaardeService;

	private final String REQUEST_ACTIE = "actie";

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException bindException)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		Actie actie = Actie.valueOf(request.getParameter(REQUEST_ACTIE));

		Melding meldingCommand = (Melding) command;
		Melding deMelding = this.onderhoudenMeldingService.geefMelding(meldingCommand.getBetrokkene(),
				meldingCommand.getBerichtkenmerkAig());
		modelAndView.addObject("meldingInstance", deMelding);

		List<Waarde> waardenBijBetrokkeneVanMelding = new ArrayList<Waarde>();
		
		switch (actie) {
		case voegen:
			
			waardenBijBetrokkeneVanMelding = this.onderhoudenWaardeService.findAllWhere(deMelding.getBetrokkene()
					.getBelastingJaar(), deMelding.getBetrokkene().getBurgerservicenummer());
			modelAndView.addObject("waardeInstanceList", waardenBijBetrokkeneVanMelding);
			
			//voor voegen moet er het huidig onderzoek worden getoond. Voor andere acties niet!
			modelAndView.setViewName("melding/voegen");
			Onderzoek onderzoekCommand = new Onderzoek();
			onderzoekCommand.setBetrokkene(meldingCommand.getBetrokkene());
			
			
			
			//rloman: this is tricky! :-) Maar staat niet in ontwerp.
			onderzoekCommand.setDatumIngang(waardenBijBetrokkeneVanMelding.get(0).getDatumIngang());
			Onderzoek huidigOnderzoekVoorBetrokkene = this.onderhoudenOnderzoekService.geefOnderzoek(onderzoekCommand);
			modelAndView.addObject("onderzoekInstance", huidigOnderzoekVoorBetrokkene);
			
			

			break;

		case parkeren:
			modelAndView.setViewName("melding/parkeren");
			break;

		case starten:
			
			waardenBijBetrokkeneVanMelding = this.onderhoudenWaardeService.findAllWhere(deMelding.getBetrokkene()
					.getBelastingJaar(), deMelding.getBetrokkene().getBurgerservicenummer());
			modelAndView.addObject("waardeInstanceList", waardenBijBetrokkeneVanMelding);
			modelAndView.setViewName("melding/starten");
			break;

		case stoppen:
			modelAndView.setViewName("melding/stoppen");
			break;

		default:
			throw new UnsupportedOperationException();
		}

		return modelAndView;
	}

	public void setOnderhoudenOnderzoekService(OnderhoudenOnderzoekService onderhoudenOnderzoekService) {
		this.onderhoudenOnderzoekService = onderhoudenOnderzoekService;
	}

	public void setOnderhoudenWaardeService(OnderhoudenWaardeService onderhoudenWaardeService) {
		this.onderhoudenWaardeService = onderhoudenWaardeService;
	}

	public void setOnderhoudenMeldingService(OnderhoudenMeldingService onderhoudenMeldingService) {
		this.onderhoudenMeldingService = onderhoudenMeldingService;
	}

}
