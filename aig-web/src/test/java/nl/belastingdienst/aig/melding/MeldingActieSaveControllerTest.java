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

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class MeldingActieSaveControllerTest extends TestCase {
	
	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);
	private final String BERICHTKENMERKAIG ="berichtkenmerk aig";
	private final String OORSPRONKELIJKE_MELDINGNAAM ="oorspronkelijke meldingnaam";
	
	private MeldingActieSaveController controller;
	private HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	private final String[] ACTIES= {"starten",  "voegen", "parkeren", "stoppen", "beeindigen"};

	@Override
	@Before
	public void setUp() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.controller = new MeldingActieSaveController();
		this.controller.setCommandClass(Melding.class);
	}
	
	@Test
	public void testAlleActies() throws Exception {
		for(String actie : this.ACTIES) {
			this.handmatigBeoordelenMeldingService = EasyMock.createMock(HandmatigBeoordelenMeldingService.class);
			this.controller.setHandmatigBeoordelenMeldingService(this.handmatigBeoordelenMeldingService);
			
			Actie deActie = Actie.valueOf(actie);
			this.testHandleSave(deActie);
		}
	}

	private void testHandleSave(Actie actie) throws Exception {
		Melding melding1 = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding1.setBetrokkene(betrokkene);
		melding1.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		EasyMock.expect(this.handmatigBeoordelenMeldingService.handmatigAfhandelenMelding(melding1, actie)).andReturn("xmlbericht");
		EasyMock.replay(this.handmatigBeoordelenMeldingService);
		
		request.setParameter("betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("oorspronkelijkeMeldingNaam", this.OORSPRONKELIJKE_MELDINGNAAM);
		request.setParameter("actie", actie.toString());
		
		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);
		
		assertNotNull("ModelAndView should not be null", mv);
		assertEquals("Invalid view name", "melding/list", mv.getViewName());
	}
	
}
