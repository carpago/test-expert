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
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.hit.pit.bo.Opdracht;
import nl.belastingdienst.hit.pit.bo.OpdrachtImpl;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class MeldingListControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";

	private final Integer BURGERSERVICENUMMERNUL = new Integer(0);
	private final Short BELASTINGJAARNUL = new Short((short) 0);
	
	private final Date DATUMBEGINGELDIGHEIDDATE = null;
	private final Date DATUMTIJDREGISTRATIEDATE = null;
	
	private final String OORSPRONKELIJKEMELDINGNAAM = "oorspronkelijke meldingnaam";
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derder";
	
	private final String STATUS = "bericht heeft geleid tot handmatige afhandeling";
	
	private MeldingListController controller;
	private OnderhoudenMeldingService onderhoudenMeldingService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		controller = new MeldingListController();
		controller.setSuccessView("list_ok");
		controller.setFormView("list_error");
		
		onderhoudenMeldingService = EasyMock.createMock(OnderhoudenMeldingService.class);
		controller.setOnderhoudenMeldingService(onderhoudenMeldingService);
		controller.setCommandClass(Melding.class);
		controller.setCommandName("melding");
		 controller.setValidator(new MeldingListValidator());
	}
	
	@Test
	public void testGetRequest() throws Exception {
		
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		Melding melding2 = new Melding();
		melding2.setBetrokkene(betrokkene);
		melding2.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding2.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding1);
		meldingen.add(melding2);
		
		
		EasyMock.expect(this.onderhoudenMeldingService.geefMeldingenMetStatusHandmatigeAfhandeling()).andReturn(meldingen);
		EasyMock.replay(this.onderhoudenMeldingService);

		this.request.setMethod("GET");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getFormView(), mv.getViewName());
	}
	
	@Test
	public void testPostRequestOK() throws Exception {
		
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(null,null);
		melding1.setBetrokkene(betrokkene);
		melding1.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		Melding melding2 = new Melding();
		melding2.setBetrokkene(betrokkene);
		melding2.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding2.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding1);
		meldingen.add(melding2);
		
		EasyMock.expect(this.onderhoudenMeldingService.findAllByExample(melding1)).andReturn(meldingen);
		EasyMock.replay(this.onderhoudenMeldingService);

		request.setParameter("betrokkene.burgerservicenummer", "");
		request.setParameter("betrokkene.belastingJaar", "");
		request.setParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		//request.setParameter("status", this.STATUS);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());
		
		assertNotNull(mv);
	}
	
	@Test
	public void testPostRequestOK3() throws Exception {
		
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(null,null);
		melding1.setBetrokkene(betrokkene);
		melding1.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		Melding melding2 = new Melding();
		melding2.setBetrokkene(betrokkene);
		melding2.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding2.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding1);
		meldingen.add(melding2);
		
		EasyMock.expect(this.onderhoudenMeldingService.findAllByExample(melding1)).andReturn(meldingen);
		EasyMock.replay(this.onderhoudenMeldingService);

		//request.setParameter("betrokkene.burgerservicenummer", "");
		//request.setParameter("betrokkene.belastingJaar", "");
		//request.setParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		request.setParameter("status", this.STATUS);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());
		
		assertNotNull(mv);
	}
	
	@Test
	public void testPostRequestNotOK() throws Exception {
		
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(null,null);
		melding1.setBetrokkene(betrokkene);
		melding1.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		Melding melding2 = new Melding();
		melding2.setBetrokkene(betrokkene);
		melding2.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding2.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding1);
		meldingen.add(melding2);
		
		EasyMock.expect(this.onderhoudenMeldingService.findAllByExample(melding1)).andReturn(meldingen);
		EasyMock.replay(this.onderhoudenMeldingService);

		//request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		// opzettelijk leeg gelaten.request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		// idem request.setParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		//request.setParameter("status", this.STATUS);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getFormView(), mv.getViewName());
		
		assertNotNull(mv);
	} 
	
	@Test
	public void testPostRequestOK2() throws Exception {
		
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding1.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		Melding melding2 = new Melding();
		melding2.setBetrokkene(betrokkene);
		melding2.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding2.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding1);
		meldingen.add(melding2);
		
		EasyMock.expect(this.onderhoudenMeldingService.findAllByExample(melding1)).andReturn(meldingen);
		EasyMock.replay(this.onderhoudenMeldingService);

		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		// idem request.setParameter("berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		request.setParameter("status", this.STATUS);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());
		
		assertNotNull(mv);
	} 
}
