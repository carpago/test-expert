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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.waarde.OnderhoudenWaardeService;
import nl.belastingdienst.aig.waarde.Waarde;
import nl.belastingdienst.aig.waarde.WaardeListController;
import nl.belastingdienst.aig.waarde.WaardeValidator;
import nl.belastingdienst.hit.pit.bo.Opdracht;
import nl.belastingdienst.hit.pit.bo.OpdrachtImpl;

public class WaardeListControllerTest extends TestCase {
	
	private final int BSN = 111111110;
	private final short BELASTINGJAAR = 2010;
	private final short BELASTINGJAAR_INVALID = 2000;
	
	private WaardeListController controller;
	private OnderhoudenWaardeService service;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@Before
	public void setUp() {

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		controller = new WaardeListController();
		controller.setSuccessView("list");
		controller.setFormView("list");
		
		service = EasyMock.createMock(OnderhoudenWaardeService.class);
		controller.setOnderhoudenWaardeService(service);
		controller.setCommandClass(Waarde.class);
		controller.setCommandName("waarde");
		controller.setValidator(new WaardeValidator());
	}
	
	
	@Test
	public void testShowZonderKlantId() {
		Opdracht opdracht = new OpdrachtImpl();
		request.setAttribute("opdracht", opdracht);
		request.setMethod("GET");
		ModelAndView mv = null;
		try {
			mv = this.controller.handleRequest(request, response);
			assertTrue("OK!, controller is bestand tegen ongeldige invoer.", true);
		}
		catch (Exception e) {
			assertFalse("Mag hier niet komen.", true);
		}
		assertEquals("invalid view", this.controller.getFormView(), mv.getViewName());
	}
	
	@Test
	public void testShowFormOK(){
		Opdracht opdracht = new OpdrachtImpl();
		opdracht.setKlantId(new Integer(this.BSN).toString());
		request.setAttribute("opdracht", opdracht);
		request.setMethod("GET");
		ModelAndView mv = null;
		try {
			mv = this.controller.handleRequest(request, response);
		} catch (Exception e) {
			assertFalse("Mag hier niet komen", true);
		}
		assertEquals("Invalid view name", controller.getFormView(), mv.getViewName());
		
	}
	
	@Test
	public final void testIndexKlantidIsGeenNummer() {
		
		Opdracht opdracht = new OpdrachtImpl();
		opdracht.setKlantId("abc");
		this.request.setAttribute("opdracht", opdracht);
		request.setMethod("GET");
		ModelAndView mav = null;
		try {
			mav = this.controller.handleRequest(request, response);
		} catch (Exception e) {
			assertTrue("Great to be here!", true);
		}
		assertNotNull(mav);
		assertEquals("verkeerde view", this.controller.getFormView(),mav.getViewName());
		
	}
	
	@Test
	public void testHandleRequest() throws Exception {
		Waarde waardeDummy = new Waarde();
		
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		waardeDummy.setBetrokkene(betrokkene);
				
		List dummyList = new ArrayList();
		dummyList.add(waardeDummy);
		EasyMock.expect(service.findAllWhere(this.BELASTINGJAAR, this.BSN)).andReturn(dummyList);
		EasyMock.replay(service);
		
		request.addParameter("betrokkene.belastingJaar", new Short(this.BELASTINGJAAR).toString());
		request.addParameter("betrokkene.burgerservicenummer", new Integer(this.BSN).toString());
		request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(request, response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(),  mv.getViewName());

		List<Waarde> lijst = (List<Waarde>) mv.getModel().get("waardeInstanceList");
		assertNotNull("Bsn should not be null", lijst);
		
		assertEquals("invalid view returned", this.controller.getSuccessView(), mv.getViewName());

		assertEquals("Invalid waarde returned", dummyList, lijst);
	}
	
	@Test
	public void testHandleRequestShouldFail() throws Exception {
		Waarde waardeDummy = new Waarde();
		
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR_INVALID);
		waardeDummy.setBetrokkene(betrokkene);
		
		List dummyList = null;
		
		request.addParameter("betrokkene.belastingJaar", new Short(this.BELASTINGJAAR_INVALID).toString());
		request.addParameter("betrokkene.burgerservicenummer", new Integer(this.BSN).toString());
		request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(request, response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getFormView(),  mv.getViewName());

		List<Waarde> lijst = (List<Waarde>) mv.getModel().get("waardeInstanceList");
		assertNull("WaardeLijst should!!! be null", lijst);
		
		assertEquals("invalid view returned, should be the form view.", this.controller.getFormView(), mv.getViewName());

		assertEquals("Invalid waarde returned", dummyList, lijst);
	}
	
	

	

}
