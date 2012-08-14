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

package nl.belastingdienst.aig.waarde;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.onderzoek.OnderhoudenOnderzoekService;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.waarde.OnderhoudenWaardeService;
import nl.belastingdienst.aig.waarde.Waarde;
import nl.belastingdienst.aig.waarde.WaardeShowController;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class WaardeShowControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";
	
	private final Date DATUMBEGINGELDIGHEIDDATE;
	private final Date DATUMTIJDREGISTRATIEDATE;
	
	private WaardeShowController controller;
	private OnderhoudenWaardeService onderhoudenWaardeService;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// doe dit in de constructor want parsen is een dure operatie. Hoeft dus niet voor elke test te worden uitgevoerd.
	/**
	 * 
	 */
	public WaardeShowControllerTest() {
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

		this.controller = new WaardeShowController();
		this.onderhoudenWaardeService = EasyMock.createMock(OnderhoudenWaardeService.class);
		this.onderhoudenOnderzoekService = EasyMock.createMock(OnderhoudenOnderzoekService.class);
		this.controller.setOnderhoudenWaardeService(this.onderhoudenWaardeService);
		this.controller.setOnderhoudenOnderzoekService(this.onderhoudenOnderzoekService);
		this.controller.setCommandClass(Waarde.class);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testHandleRequest() throws Exception {
		Waarde waardeDummy = new Waarde();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		waardeDummy.setBetrokkene(betrokkene);
		waardeDummy.setDatumIngang(this.DATUMBEGINGELDIGHEIDDATE);
		waardeDummy.setDatumEinde(this.DATUMTIJDREGISTRATIEDATE);
		
		Onderzoek onderzoekDummy = new Onderzoek();
		onderzoekDummy.setBetrokkene(betrokkene);
		onderzoekDummy.setDatumIngang(this.DATUMBEGINGELDIGHEIDDATE);
		
		
		EasyMock.expect(this.onderhoudenWaardeService.geefWaarde(betrokkene, this.DATUMBEGINGELDIGHEIDDATE, this.DATUMBEGINGELDIGHEIDDATE)).andReturn(waardeDummy);
		EasyMock.replay(this.onderhoudenWaardeService);
		
		EasyMock.expect(this.onderhoudenOnderzoekService.geefOnderzoek(onderzoekDummy)).andReturn(onderzoekDummy);
		EasyMock.replay(this.onderhoudenOnderzoekService);
		
		this.request.addParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		this.request.addParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		this.request.addParameter("datumIngang", this.DATUMBEGINGELDIGHEIDSTRING);
		this.request.addParameter("datumTijdGeregistreerd", this.DATUMBEGINGELDIGHEIDSTRING);
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "show", mv.getViewName());

		Waarde waarde = (Waarde) mv.getModel()
			.get("waardeInstance");
		assertNotNull("waarde should not be null", waarde);

		assertEquals("Invalid waarde returned", waardeDummy, waarde);
	}
}
