package nl.belastingdienst.aig.melding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.aanleiding.OnderhoudenAanleidingService;
import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * Deze klass implement de ShowController. Hij verzorgt het tonen (show) van het
 * melding domein object. Deze controller haalt de melding via de service via de
 * dao uit de db en dispatched dan naar de show.gsp
 * 
 */
public class MeldingShowController extends AbstractCommandController {

	private OnderhoudenMeldingService onderhoudenMeldingService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private OnderhoudenAanleidingService onderhoudenAanleidingService;
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter MeldingShowController:handle(request, response");
		
		ModelAndView modelAndView = new ModelAndView("melding/show");
		Melding meldingCommand = (Melding) command;
		Melding deMelding = this.onderhoudenMeldingService.geefMelding(meldingCommand.getBetrokkene(),
				meldingCommand.getBerichtkenmerkAig());
		
		Onderzoek onderzoekCommand = new Onderzoek();
		onderzoekCommand.setBetrokkene(deMelding.getBetrokkene());
		onderzoekCommand.setDatumIngang(deMelding.getDatumPlaatsGevonden());
		Onderzoek onderzoek = this.onderhoudenOnderzoekService.geefCompleetOnderzoek(onderzoekCommand);
		
		List<Aanleiding> aanleidingen = this.onderhoudenAanleidingService.geefAanleidingenVoor(onderzoekCommand);
		
		modelAndView.addObject("meldingInstance", deMelding);
		modelAndView.addObject("onderzoek", onderzoek);
		modelAndView.addObject("aanleidingInstanceList",aanleidingen);

		this.logger.debug("Leave MeldingShowController:handle(request, response");

		return modelAndView;
	}

	public void setOnderhoudenMeldingService(OnderhoudenMeldingService onderhoudenMeldingService) {
		this.onderhoudenMeldingService = onderhoudenMeldingService;
	}

	public void setOnderhoudenOnderzoekService(OnderhoudenOnderzoekService onderhoudenOnderzoekService) {
		this.onderhoudenOnderzoekService = onderhoudenOnderzoekService;
	}

	public void setOnderhoudenAanleidingService(OnderhoudenAanleidingService onderhoudenAanleidingService) {
		this.onderhoudenAanleidingService = onderhoudenAanleidingService;
	}
	
	
}
