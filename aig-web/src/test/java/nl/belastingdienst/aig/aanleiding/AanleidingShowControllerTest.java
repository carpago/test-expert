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

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class AanleidingShowControllerTest extends TestCase {

	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);

	private final String BERICHTKENMERKAIG = "berichtkenmerk aig";

	private AanleidingShowController controller;

	private OnderhoudenAanleidingService onderhoudenAanleidingService;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Override
	@Before
	public void setUp() {

		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();

		this.controller = new AanleidingShowController();

		this.onderhoudenAanleidingService = EasyMock.createMock(OnderhoudenAanleidingService.class);
		this.controller.setOnderhoudenAanleidingService(onderhoudenAanleidingService);

		this.controller.setCommandClass(Aanleiding.class);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testHandleRequest() throws Exception {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.getMelding().getBetrokkene().setBelastingJaar(this.BELASTINGJAAR);
		aanleiding.getMelding().getBetrokkene().setBurgerservicenummer(this.BSN);
		aanleiding.getMelding().setBerichtkenmerkAig(this.BERICHTKENMERKAIG);

		List<Aanleiding> deAanleidingenBijDitOnderzoekVanDeBetrokkene = new ArrayList<Aanleiding>();

		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleiding(aanleiding)).andReturn(aanleiding);
		EasyMock.expect(this.onderhoudenAanleidingService.geefAanleidingenVoorBetrokkene(aanleiding.getMelding().getBetrokkene()))
				.andReturn(deAanleidingenBijDitOnderzoekVanDeBetrokkene);
		EasyMock.replay(onderhoudenAanleidingService);

		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkAig", this.BERICHTKENMERKAIG);

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("ModelAndView should not be null", mv);
		//controlleer of data op model and view staat.
		assertNotNull("Er moet een aanleidingInstance op het modelAndView staan.", mv.getModel().get("aanleidingInstance"));
		assertNotNull("Er moet een aanleidingInstanceList op het modelAndView staan.", mv.getModel().get("aanleidingInstanceList"));
		
		//controlleer of data ook op sessie staat. //rloman: dit lijkt me een review/overleg waard.
		assertNotNull("Er moet een aanleidingInstance op de sessie staan.", this.request.getSession().getAttribute("aanleidingInstance"));
		assertNotNull("Er moet een aanleidingInstance op de sessie staan.", this.request.getSession().getAttribute("aanleidingInstanceList"));
		
		assertEquals("Invalid view name", "aanleiding/show", mv.getViewName());
		assertEquals(" aanleiding is niet gelijk aan de verwachte aanleiding.", aanleiding, mv.getModel().get("aanleidingInstance"));
		assertEquals(" aanleidingInstanceList is niet gelijk ... ", deAanleidingenBijDitOnderzoekVanDeBetrokkene, mv.getModel().get("aanleidingInstanceList"));
		assertNotNull("view should not be null", mv);

	}
}
