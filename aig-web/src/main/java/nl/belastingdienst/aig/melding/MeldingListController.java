package nl.belastingdienst.aig.melding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class MeldingListController extends SimpleFormController {

	private OnderhoudenMeldingService onderhoudenMeldingService;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Deze methode wordt aangeroepen dan en slechts dan bij een GET request.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView: bestaande uit lijst van alle handmatig te beoordelen meldingen
	 * @throws Exception
	 */
	public ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("Enter MeldingListController::showForm(request, response)");

		
		//rloman: deze meuk hieronder verwijderen bij klaarkomen.
		// deze code haalt straks uit de service alle meldingen op die staus
		// "Beoordeling heeft geleid tot handmatige afhandeling"
		// hebben
		// en toont het resultaat hiervan in melding/list.jsp

		// testdata rloman...
		/*
		Melding een = new Melding();
		een.setWaarde(2);
		Betrokkene betrokkene = new Betrokkene(111111110, new Short("2011"));
		een.setBetrokkene(betrokkene);
		een.setFiscaleBron("code");

		Melding twee = new Melding();
		twee.setWaarde(3);
		betrokkene = new Betrokkene(111111110, new Short("2011"));
		twee.setBetrokkene(betrokkene);
		twee.setFiscaleBron("code aard bron bepaling");

		// eerst stub
		List<Melding> meldingInstanceList = new ArrayList<Melding>();
		meldingInstanceList.add(een);
		meldingInstanceList.add(twee);
		
		// Einde testdata.
		*/
		ModelAndView modelAndView = new ModelAndView(this.getFormView());

		List<Melding> meldingInstanceList = this.onderhoudenMeldingService.geefMeldingenMetStatusHandmatigeAfhandeling();

		modelAndView.addObject("meldingInstanceList", meldingInstanceList);

		logger.debug("Leave MeldingListController::showForm");

		return modelAndView;
	}

	/**
	 * Deze methode wordt aangeroepen dan en slechts dan bij een POST request.
	 * En als er dus een selectiecriteria is ingevoerd voor de melding.
	 * 
	 * Er komt hier een Melding object binnen als command object die moet
	 * voorzien zijn van:
	 * 
	 * - betrokkene.burgerservicenummer - betrokkene.belastingJaar -
	 * berichtKenmerkDerden. - status
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command,
			BindException bindException) throws Exception {

		this.logger.debug("Enter MeldingListController::onSubmit");

		Melding melding = (Melding) command;
		
		List<Melding> meldingInstanceList = this.onderhoudenMeldingService.findAllByExample(melding);


		ModelAndView modelAndView = this.showForm(request, bindException,
				this.getSuccessView());
		modelAndView.addObject("meldingInstanceList", meldingInstanceList);

		this.logger.debug("Leave MeldingListController::onSubmit");

		return modelAndView;
	}

	public void setOnderhoudenMeldingService(OnderhoudenMeldingService onderhoudenMeldingService) {
		this.onderhoudenMeldingService = onderhoudenMeldingService;
	}
		
}
