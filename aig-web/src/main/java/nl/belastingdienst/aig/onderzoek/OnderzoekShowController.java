package nl.belastingdienst.aig.onderzoek;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.belastingdienst.aig.aanleiding.Aanleiding;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class OnderzoekShowController extends AbstractCommandController {

	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter MeldingShowController:handle(request, response");
		
		ModelAndView modelAndView = new ModelAndView("onderzoek/show");
		Onderzoek onderzoekCommand = (Onderzoek) command;
		Onderzoek hetOnderzoek = this.onderhoudenOnderzoekService.geefCompleetOnderzoek(onderzoekCommand);
		
		Aanleiding eersteAanleidingVanDitOnderzoek = hetOnderzoek.getEersteAanleidingVanDitOnderzoek();
		
		modelAndView.addObject("onderzoekInstance", hetOnderzoek);
		modelAndView.addObject("eersteAanleiding", eersteAanleidingVanDitOnderzoek);

		this.logger.debug("Leave OnderzoekShowController:handle(request, response");

		return modelAndView;
	}

	public void setOnderhoudenOnderzoekService(OnderhoudenOnderzoekService onderhoudenOnderzoekService) {
		this.onderhoudenOnderzoekService = onderhoudenOnderzoekService;
	}
	
	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("d-M-y"), false);
        binder.registerCustomEditor(Date.class, editor);
    }
}
