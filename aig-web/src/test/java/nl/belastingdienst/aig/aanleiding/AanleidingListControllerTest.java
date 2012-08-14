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

package nl.belastingdienst.aig.aanleiding;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class AanleidingListControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derden";
	
	private AanleidingListController controller;
	private OnderhoudenAanleidingService onderhoudenAanleidingService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		controller = new AanleidingListController();
		controller.setSuccessView("aanleiding/list");
		controller.setFormView("aanleiding/error");
		
		this.onderhoudenAanleidingService = EasyMock.createMock(OnderhoudenAanleidingService.class);
		controller.setOnderhoudenAanleidingService(onderhoudenAanleidingService);
		controller.setCommandClass(Aanleiding.class);
		controller.setCommandName("aanleiding");
		controller.setValidator(new AanleidingListValidator());
	}
	
	
	@Test
	public void testGetRequest() throws Exception {
		
		Aanleiding aanleiding1 = new Aanleiding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		aanleiding1.getMelding().setBetrokkene(betrokkene);
		aanleiding1.getMelding().setBerichtkenmerkDerden(this.BERICHTKENMERKDERDEN);
		
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding1);
		
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenMetLegeEinddatum()).andReturn(aanleidingen);
		EasyMock.replay(this.onderhoudenAanleidingService);

		this.request.setMethod("GET");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getFormView(), mv.getViewName());
		
		assertNotNull(mv);
		assertEquals("verkeerde view", this.controller.getFormView(),mv.getViewName());
		assertNotNull("Moet een aanleidingInstanceList op request staan.", mv.getModel().get("aanleidingInstanceList"));
		
	}
	
	@Test
	public void testPostRequest() throws Exception {
		
		Aanleiding aanleiding = new Aanleiding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		aanleiding.getMelding().setBetrokkene(betrokkene);
		aanleiding.getMelding().setBerichtkenmerkDerden(this.BERICHTKENMERKDERDEN);
		
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding);
		
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenVoorBetrokkene(betrokkene)).andReturn(aanleidingen);
		EasyMock.replay(this.onderhoudenAanleidingService);
		
		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkDerden", this.BERICHTKENMERKDERDEN);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertNotNull("Moet een aanleidingInstanceList op request staan.", mv.getModel().get("aanleidingInstanceList"));
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());
		
		assertNotNull(mv);
	}
	
	@Test
	public void testPostRequestOKMet1Selectiecriterium() throws Exception {
		
		Aanleiding aanleiding = new Aanleiding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, null);
		aanleiding.getMelding().setBetrokkene(betrokkene);
		
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding);
		
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenVoorBetrokkene(betrokkene)).andReturn(aanleidingen);
		EasyMock.replay(this.onderhoudenAanleidingService);
		
		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertNotNull("Moet een aanleidingInstanceList op request staan.", mv.getModel().get("aanleidingInstanceList"));
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());
		
		assertNotNull(mv);
	}
	
	
	@Test
	public void testPostRequestOKMetGeenSelectieCriterium() throws Exception {
		
		Aanleiding aanleiding = new Aanleiding();
		Betrokkene betrokkene = new Betrokkene(null, null);
		aanleiding.getMelding().setBetrokkene(betrokkene);
		
		List<Aanleiding> aanleidingen = new ArrayList<Aanleiding>();
		aanleidingen.add(aanleiding);
		
		// wordt geredirect na errors in validator naar controller:showForm(...);
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenMetLegeEinddatum()).andReturn(aanleidingen);
		EasyMock.replay(this.onderhoudenAanleidingService);
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertNotNull("Moet een aanleidingInstanceList op request staan.", mv.getModel().get("aanleidingInstanceList"));
		assertEquals("Invalid view name", this.controller.getFormView(), mv.getViewName());
		
		assertNotNull(mv);
	}
}
