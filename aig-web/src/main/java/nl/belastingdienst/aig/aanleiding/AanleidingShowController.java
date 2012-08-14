package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class AanleidingShowController extends AbstractCommandController {
	private OnderhoudenAanleidingService onderhoudenAanleidingService;

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter AanleidingShowController:handle(request, response");

		Aanleiding deAanleidingCommand = (Aanleiding) command;

		ModelAndView modelAndView = new ModelAndView("aanleiding/show");

		Aanleiding aanleiding = this.onderhoudenAanleidingService.geefAanleiding(deAanleidingCommand);
		
		//rloman: klopt het dat dit niet hoeft vanwege het eager laden van dit onderhet domeinobject "Aanleiding " 
		// fabian vragen.
		List<Aanleiding> deAanleidingenBijDitOnderzoekVanDeBetrokkene = this.onderhoudenAanleidingService
				.geefAanleidingenVoorBetrokkene(deAanleidingCommand.getMelding().getBetrokkene());

		modelAndView.addObject("aanleidingInstance", aanleiding);
		
		modelAndView.addObject("aanleidingInstanceList", deAanleidingenBijDitOnderzoekVanDeBetrokkene);
		
		// zet aanleiding op de sessie; tijdelijk want dan kan hij bij edit.jsp worden gebruikt
		// een cics transactie starten voor een aanleiding object lijkt me nu teveel van het goede
		request.getSession().setAttribute("aanleidingInstance", aanleiding);
		request.getSession().setAttribute("aanleidingInstanceList", deAanleidingenBijDitOnderzoekVanDeBetrokkene);

		this.logger.debug("Leave AanleidingShowController:handle(request, response");

		return modelAndView;
	}

	public void setOnderhoudenAanleidingService(OnderhoudenAanleidingService onderhoudenAanleidingService) {
		this.onderhoudenAanleidingService = onderhoudenAanleidingService;
	}

}
