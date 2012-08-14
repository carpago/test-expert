/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: lomar00
 *          Copyright: (c) 2010 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *  ----------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ----------------------------------------------------------------------------------------------------
 *
 */

package nl.belastingdienst.aig.melding;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.waarde.OnderhoudenWaardeService;
import nl.belastingdienst.aig.waarde.Waarde;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class MeldingActieControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String BERICHTKENMERKAIG ="berichtkenmerk aig";
	
	private MeldingActieController controller;
	private OnderhoudenMeldingService onderhoudenMeldingService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private OnderhoudenWaardeService onderhoudenWaardeService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	private final String REDEN_STOPPEN = "stoppen";
	private final String REDEN_PARKEREN = "parkeren";
	private final String REDEN_STARTEN = "starten";
	private final String REDEN_VOEGEN = "voegen";

	/**
	 * Deze methode wordt bij elke start van ELKE TEST uitgevoerd!!!
	 */
	@Override
	@Before
	public void setUp() {
		
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();

		this.controller = new MeldingActieController();
		this.onderhoudenMeldingService = EasyMock.createMock(OnderhoudenMeldingService.class);
		this.onderhoudenOnderzoekService = EasyMock.createMock(OnderhoudenOnderzoekService.class);
		this.onderhoudenWaardeService = EasyMock.createMock(OnderhoudenWaardeService.class);
		this.controller.setOnderhoudenMeldingService(onderhoudenMeldingService);
		this.controller.setOnderhoudenOnderzoekService(onderhoudenOnderzoekService);
		this.controller.setOnderhoudenWaardeService(onderhoudenWaardeService);
		this.controller.setCommandClass(Melding.class);
	}

	@Test
	public void testHandleRequestVoegen() throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMelding(betrokkene, this.BERICHTKENMERKAIG)).andReturn(melding1);
		EasyMock.replay(onderhoudenMeldingService);
		
		Waarde waarde = new Waarde();
		List<Waarde> waarden = new ArrayList<Waarde>();
		waarden.add(waarde);
		
		
		EasyMock.expect(this.onderhoudenWaardeService.findAllWhere(betrokkene.getBelastingJaar(), betrokkene.getBurgerservicenummer())).andReturn(waarden);
		EasyMock.replay(onderhoudenWaardeService);
		
		Onderzoek onderzoekCommand = new Onderzoek();
		onderzoekCommand.setBetrokkene(betrokkene);
		onderzoekCommand.setDatumIngang(waarden.get(0).getDatumIngang());
		EasyMock.expect(this.onderhoudenOnderzoekService.geefOnderzoek(onderzoekCommand)).andReturn(onderzoekCommand);
		EasyMock.replay(this.onderhoudenOnderzoekService);
		
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("actie", REDEN_VOEGEN);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("Moet meldingInstance op request staan.", mv.getModel().get("meldingInstance"));
		assertNotNull("Moet onderzoekInstance op request staan.", mv.getModel().get("onderzoekInstance"));
		assertNotNull("Moet waardenInstanceList op request staan.", mv.getModel().get("waardeInstanceList"));
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/voegen", mv.getViewName());
	}
	
	@Test
	public void testHandleRequestStarten() throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMelding(betrokkene, this.BERICHTKENMERKAIG)).andReturn(melding1);
		EasyMock.replay(onderhoudenMeldingService);
		
		Waarde waarde = new Waarde();
		List<Waarde> waarden = new ArrayList<Waarde>();
		waarden.add(waarde);
		
		
		EasyMock.expect(this.onderhoudenWaardeService.findAllWhere(betrokkene.getBelastingJaar(), betrokkene.getBurgerservicenummer())).andReturn(waarden);
		EasyMock.replay(onderhoudenWaardeService);
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("actie", REDEN_STARTEN);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("Moet meldingInstance op request staan.", mv.getModel().get("meldingInstance"));
		assertNotNull("Moet waardenInstanceList op request staan.", mv.getModel().get("waardeInstanceList"));
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/starten", mv.getViewName());
	}
	
	@Test
	public void testHandleRequestParkeren() throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMelding(betrokkene, this.BERICHTKENMERKAIG)).andReturn(melding1);
		EasyMock.replay(onderhoudenMeldingService);
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("actie", REDEN_PARKEREN);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("Moet meldingInstance op request staan.", mv.getModel().get("meldingInstance"));
		assertNull("Mag juist geen waardenInstanceList op request staan.", mv.getModel().get("waardeInstanceList"));
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/parkeren", mv.getViewName());
	}
	
	@Test
	public void testHandleRequestStoppen() throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMelding(betrokkene, this.BERICHTKENMERKAIG)).andReturn(melding1);
		EasyMock.replay(onderhoudenMeldingService);
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("actie", REDEN_STOPPEN);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("Moet meldingInstance op request staan.", mv.getModel().get("meldingInstance"));
		assertNull("Mag juist geen waardenInstanceList op request staan.", mv.getModel().get("waardeInstanceList"));
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/stoppen", mv.getViewName());
	}
}
