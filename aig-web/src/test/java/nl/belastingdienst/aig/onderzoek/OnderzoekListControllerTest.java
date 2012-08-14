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

public class OnderzoekListControllerTest extends TestCase {

	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);

	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derden";

	private OnderzoekListController controller;
	private OnderhoudenOnderzoekService onderhoudenOnderzoekService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private final String SUCCESS_VIEW = "onderzoek/list";
	private final String FORM_VIEW = "onderzoek/list";

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		controller = new OnderzoekListController();
		controller.setSuccessView("aanleiding/list");
		controller.setFormView("aanleiding/list");

		this.onderhoudenOnderzoekService = EasyMock.createMock(OnderhoudenOnderzoekService.class);
		controller.setOnderhoudenOnderzoekService(onderhoudenOnderzoekService);
		controller.setCommandClass(Onderzoek.class);
		controller.setCommandName("onderzoek");
		controller.setSuccessView(this.SUCCESS_VIEW);
		controller.setFormView(this.FORM_VIEW);
		controller.setValidator(new OnderzoekListValidator());
	}

	@Test
	public void testGetRequest() throws Exception {

		this.request.setMethod("GET");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("verkeerde view", this.controller.getFormView(), mv.getViewName());

	}

	@Test
	public void testPostRequestOK_1() throws Exception {

		Onderzoek onderzoek = new Onderzoek();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		onderzoek.setBetrokkene(betrokkene);
		Date now = new Date();
		onderzoek.setDatumIngang(now);

		List<Onderzoek> onderzoeken = new ArrayList<Onderzoek>();
		onderzoeken.add(onderzoek);

		EasyMock.expect(this.onderhoudenOnderzoekService.geefOnderzoekList(betrokkene)).andReturn(onderzoeken);
		EasyMock.replay(this.onderhoudenOnderzoekService);

		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());

		assertNotNull("Moet een onderzoekInstanceList op request staan.", mv.getModel().get("onderzoekInstanceList"));
		assertEquals(onderzoeken, mv.getModel().get("onderzoekInstanceList"));
	}
	
	@Test
	public void testPostRequestOK_2() throws Exception {

		Onderzoek onderzoek = new Onderzoek();
		Betrokkene betrokkene = new Betrokkene(this.BSN, null);
		onderzoek.setBetrokkene(betrokkene);
		Date now = new Date();
		onderzoek.setDatumIngang(now);

		List<Onderzoek> onderzoeken = new ArrayList<Onderzoek>();
		onderzoeken.add(onderzoek);

		EasyMock.expect(this.onderhoudenOnderzoekService.geefOnderzoekList(betrokkene)).andReturn(onderzoeken);
		EasyMock.replay(this.onderhoudenOnderzoekService);

		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());

		assertNotNull("Moet een onderzoekInstanceList op request staan.", mv.getModel().get("onderzoekInstanceList"));
		assertEquals(onderzoeken, mv.getModel().get("onderzoekInstanceList"));
	}
	
	@Test
	public void testPostRequestNOK() throws Exception {

		Onderzoek onderzoek = new Onderzoek();
		Betrokkene betrokkene = new Betrokkene(this.BSN, null);
		onderzoek.setBetrokkene(betrokkene);
		Date now = new Date();
		onderzoek.setDatumIngang(now);

		List<Onderzoek> onderzoeken = new ArrayList<Onderzoek>();
		onderzoeken.add(onderzoek);

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", this.controller.getSuccessView(), mv.getViewName());

		assertNull("Moet geen onderzoekInstanceList op request staan.", mv.getModel().get("onderzoekInstanceList"));
	}
}
