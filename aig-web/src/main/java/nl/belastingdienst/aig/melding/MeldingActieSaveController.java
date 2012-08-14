package nl.belastingdienst.aig.melding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class MeldingActieSaveController extends AbstractCommandController {
	private HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService;
	
	private final String REQUEST_ACTIE = "actie";
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter MeldingActieSaveController:handle(request, response");
		
		Actie actie = Actie.valueOf(request.getParameter(REQUEST_ACTIE));
		
		ModelAndView modelAndView = new ModelAndView("melding/list");
		Melding meldingCommand = (Melding) command;
		
		// in het ontwerp staat niet dat er wat met de resultstring gebeurt. Dus niets. Maar zet het wel in de view.
		this.handmatigBeoordelenMeldingService.handmatigAfhandelenMelding(meldingCommand, actie);
		
		this.logger.debug("Leave MeldingActieSaveController:handle(request, response");
		
		return modelAndView;
	}

	public void setHandmatigBeoordelenMeldingService(HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService) {
		this.handmatigBeoordelenMeldingService = handmatigBeoordelenMeldingService;
	}
}
