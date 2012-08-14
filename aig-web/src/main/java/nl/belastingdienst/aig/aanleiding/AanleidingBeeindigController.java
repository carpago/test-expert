package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.melding.Actie;
import nl.belastingdienst.aig.melding.HandmatigBeoordelenMeldingService;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AanleidingBeeindigController extends SimpleFormController {
	
	// een Aanleiding is een Melding en daarom kan ik die gewoon hier gebruiken.
	private HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService;

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter AanleidingBeeindigController:handle(request, response");

		Aanleiding deAanleiding = (Aanleiding) command;

		//rloman: welk view gaat aanleiding na beeindigen naar toe ?
		ModelAndView modelAndView = new ModelAndView("list");
		
		modelAndView.addObject("afgemeldeAanleiding", deAanleiding);
		
		this.handmatigBeoordelenMeldingService.beeindigAanleiding(deAanleiding);
		
		this.logger.debug("Leave AanleidingBeeindigController:handle(request, response");

		return modelAndView;
	}
	

	public void setHandmatigBeoordelenMeldingService(HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService) {
		this.handmatigBeoordelenMeldingService = handmatigBeoordelenMeldingService;
	}


	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView("list");
		
		return modelAndView;
	}

	
	
}
