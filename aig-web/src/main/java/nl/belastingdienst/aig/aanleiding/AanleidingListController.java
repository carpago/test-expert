package nl.belastingdienst.aig.aanleiding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.Melding;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AanleidingListController extends SimpleFormController {

	private OnderhoudenAanleidingService onderhoudenAanleidingService;

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException bindException) throws Exception {

		this.logger.debug("Enter AanleidingListController::onSubmit");

		Aanleiding deAanleiding = (Aanleiding) command;
		Melding deMelding = deAanleiding.getMelding();
		Betrokkene deBetrokkene = deMelding.getBetrokkene();
		
		List<Aanleiding> aanleidingInstanceList = this.onderhoudenAanleidingService.geefAanleidingenVoorBetrokkene(deBetrokkene);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(this.getSuccessView());
		modelAndView.addObject("aanleidingInstanceList", aanleidingInstanceList);

		this.logger.debug("Leave AanleidingListController::onSubmit");

		return modelAndView;
	}

	@Override
	public ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response, BindException be)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView(this.getFormView());

		List<Aanleiding> aanleidingInstanceList = this.onderhoudenAanleidingService.geefAanleidingenMetLegeEinddatum();

		modelAndView.addObject("aanleidingInstanceList", aanleidingInstanceList);

		logger.debug("Leave AanleidingListController::showForm");

		return modelAndView;
	}

	public void setOnderhoudenAanleidingService(OnderhoudenAanleidingService onderhoudenAanleidingService) {
		this.onderhoudenAanleidingService = onderhoudenAanleidingService;
	}

	
}
