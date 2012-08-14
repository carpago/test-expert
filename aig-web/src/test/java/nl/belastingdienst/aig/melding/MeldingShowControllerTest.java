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
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.aanleiding.OnderhoudenAanleidingService;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class MeldingShowControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";
	
	private final String OORSPRONKELIJKEMELDINGNAAM = "oorspronkelijke meldingnaam";
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derder";
	private final String BERICHTKENMERKAIG ="berichtkenmerk aig";
	
	private final String STATUS = "bericht heeft geleid tot handmatige afhandeling";
	
	private MeldingShowController controller;
	
	private OnderhoudenMeldingService onderhoudenMeldingService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private OnderhoudenAanleidingService onderhoudenAanleidingService;
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	/**
	 * Deze methode wordt bij elke start van ELKE TEST uitgevoerd!!!
	 */
	@Override
	@Before
	public void setUp() {
		
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();

		this.controller = new MeldingShowController();
		
		this.onderhoudenMeldingService = EasyMock.createMock(OnderhoudenMeldingService.class);
		this.controller.setOnderhoudenMeldingService(onderhoudenMeldingService);
		
		this.onderhoudenOnderzoekService = EasyMock.createMock(OnderhoudenOnderzoekService.class);
		this.controller.setOnderhoudenOnderzoekService(onderhoudenOnderzoekService);
		
		this.onderhoudenAanleidingService = EasyMock.createMock(OnderhoudenAanleidingService.class);
		this.controller.setOnderhoudenAanleidingService(onderhoudenAanleidingService);
		
		this.controller.setCommandClass(Melding.class);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testHandleRequest() throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMelding(betrokkene,
				this.BERICHTKENMERKAIG)).andReturn(melding1);
		EasyMock.replay(onderhoudenMeldingService);
		
		//
		Onderzoek onderzoek = new Onderzoek();
		onderzoek.setBetrokkene(betrokkene);
		onderzoek.setDatumIngang(melding1.getDatumPlaatsGevonden());
		
		EasyMock.expect(this.onderhoudenOnderzoekService.geefCompleetOnderzoek(onderzoek)).andReturn(onderzoek);
		EasyMock.replay(this.onderhoudenOnderzoekService);
		
		Aanleiding aanleiding = new Aanleiding();
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding);
		
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenVoor(onderzoek)).andReturn(aanleidingen);
		EasyMock.replay(this.onderhoudenAanleidingService);
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/show", mv.getViewName());
		assertEquals("Invalid melding returned", melding1,mv.getModel().get("meldingInstance"));
		assertNotNull("view should not be null", mv);

	}
}
