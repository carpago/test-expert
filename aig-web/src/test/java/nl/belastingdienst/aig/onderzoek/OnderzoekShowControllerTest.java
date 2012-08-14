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

package nl.belastingdienst.aig.onderzoek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class OnderzoekShowControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	
	private final Date DATUMBEGINGELDIGHEIDDATE;
	
	private OnderzoekShowController controller;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	// doe dit in de constructor want parsen is een dure operatie. Hoeft dus niet voor elke test te worden uitgevoerd.
	/**
	 * 
	 */
	public OnderzoekShowControllerTest() {
		// DateFormat df = DateFormat.getDateInstance();
		
		SimpleDateFormat df = new SimpleDateFormat("d-M-y");
		try {
			this.DATUMBEGINGELDIGHEIDDATE = df.parse(this.DATUMBEGINGELDIGHEIDSTRING);
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

		this.controller = new OnderzoekShowController();
		this.onderhoudenOnderzoekService = EasyMock.createMock(OnderhoudenOnderzoekService.class);
		this.controller.setOnderhoudenOnderzoekService(this.onderhoudenOnderzoekService);
		this.controller.setCommandClass(Onderzoek.class);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testHandleRequest() throws Exception {
		Onderzoek onderzoek = new Onderzoek();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		onderzoek.setBetrokkene(betrokkene);
		onderzoek.setDatumIngang(this.DATUMBEGINGELDIGHEIDDATE);
		
		Aanleiding aanleiding = new Aanleiding();
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding);
		
		onderzoek.setAanleidingList(aanleidingen);
		
		EasyMock.expect(this.onderhoudenOnderzoekService.geefCompleetOnderzoek(onderzoek)).andReturn(onderzoek);
		EasyMock.replay(this.onderhoudenOnderzoekService);
		
		this.request.addParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		this.request.addParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		this.request.addParameter("datumIngang", this.DATUMBEGINGELDIGHEIDSTRING);
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "onderzoek/show", mv.getViewName());

		Onderzoek onderzoekResult = (Onderzoek) mv.getModel()
			.get("onderzoekInstance");
		assertNotNull("onderzoekInstance should not be null", onderzoekResult);
		
		assertNotNull(onderzoekResult.getAanleidingList());
		assertEquals(aanleidingen, onderzoekResult.getAanleidingList());
		
		assertNotNull(onderzoekResult.getEersteAanleidingVanDitOnderzoek());
		assertEquals(aanleiding, onderzoekResult.getEersteAanleidingVanDitOnderzoek());

		assertEquals("Invalid onderzoek returned", onderzoek, onderzoekResult);
	}
}
