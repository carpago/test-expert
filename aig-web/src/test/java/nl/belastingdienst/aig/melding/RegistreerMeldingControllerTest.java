/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: Raymond Loman
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class RegistreerMeldingControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final Short BELASTINGJAAR_INVALID = new Short((short) 2033);
	private final Integer BSN_INVALID = new Integer(10101101);
	
	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";
	
	private final Date DATUMBEGINGELDIGHEIDDATE;
	private final Date DATUMTIJDREGISTRATIEDATE;
	
	private final String OORSPRONKELIJKEMELDINGNAAM = "oorspronkelijke meldingnaam";
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derden";
	
	private RegistreerMeldingController controller;
	private RegistreerMeldingService registreerMeldingService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// doe dit in de constructor want parsen is een dure operatie. Hoeft dus niet voor elke test te worden uitgevoerd.
	/**
	 * 
	 */
	public RegistreerMeldingControllerTest() {
		// DateFormat df = DateFormat.getDateInstance();
		
		SimpleDateFormat df = new SimpleDateFormat("d-M-y");
		try {
			this.DATUMBEGINGELDIGHEIDDATE = df.parse(this.DATUMBEGINGELDIGHEIDSTRING);
			this.DATUMTIJDREGISTRATIEDATE = df.parse(this.DATUMTIJDREGISTRATIESTRING);
		} catch (ParseException e) {
			System.out.println("datum(s) bevatten ongeldig formaat.");
			e.printStackTrace();
			throw new RuntimeException("datum(s) bevatten ongeldig formaat.:" + e, e);
		}
	}
	
	/**
	 * Deze methode wordt bij elke start van ELKE TEST uitgevoerd!!!
	 */
	@Override
	@Before
	public void setUp() {
		
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();

		this.controller = new RegistreerMeldingController();
		this.registreerMeldingService = EasyMock.createMock(RegistreerMeldingService.class);
		this.controller.setRegistreerMeldingService(this.registreerMeldingService);
		this.controller.setCommandClass(Melding.class);
		this.controller.setSuccessView("melding/registreer");
		this.controller.setFormView("melding/registreer_error");
		this.controller.setValidator(new MeldingRegistreerValidator());
	}

	@Test
	public void testHandleRequest() throws Exception {
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);

		this.registreerMeldingService.registreerMelding(melding);
		EasyMock.replay(this.registreerMeldingService);
		
		this.request.addParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		this.request.addParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		this.request.addParameter("datumPlaatsGevonden", this.DATUMBEGINGELDIGHEIDSTRING);
		this.request.addParameter("oorspronkelijkeMeldingNaam", this.OORSPRONKELIJKEMELDINGNAAM);
		this.request.addParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());

	}
	
	@Test
	public void testHandleRequestFout() throws Exception {
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN_INVALID, this.BELASTINGJAAR_INVALID);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);

		this.registreerMeldingService.registreerMelding(melding);
		EasyMock.replay(this.registreerMeldingService);
		
		this.request.addParameter("betrokkene.belastingJaar", this.BELASTINGJAAR_INVALID.toString());
		this.request.addParameter("betrokkene.burgerservicenummer", this.BSN_INVALID.toString());
		this.request.addParameter("datumPlaatsGevonden", this.DATUMBEGINGELDIGHEIDSTRING);
		this.request.addParameter("oorspronkelijkeMeldingNaam", this.OORSPRONKELIJKEMELDINGNAAM);
		this.request.addParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getFormView(), mv.getViewName());

	}
}
