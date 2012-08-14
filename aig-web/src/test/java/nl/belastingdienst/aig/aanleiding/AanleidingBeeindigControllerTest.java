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

import junit.framework.TestCase;
import nl.belastingdienst.aig.melding.HandmatigBeoordelenMeldingService;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class AanleidingBeeindigControllerTest extends TestCase {

	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);

	private final String BERICHTKENMERKAIG = "berichtkenmerk aig";
	private final String REDEN_EINDE_DELTA = "delta";
	private final String REDEN_EINDE_ANDERS = "anders";
	private final String REDEN_EINDE_ANDERS_OMSCHRIJVING = "... proza ...";

	private AanleidingBeeindigController controller;

	private HandmatigBeoordelenMeldingService handmatigBeoordelenMeldingService;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Override
	@Before
	public void setUp() {

		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();

		this.controller = new AanleidingBeeindigController();

		this.handmatigBeoordelenMeldingService = EasyMock.createMock(HandmatigBeoordelenMeldingService.class);
		this.controller.setHandmatigBeoordelenMeldingService(handmatigBeoordelenMeldingService);
		this.controller.setCommandClass(Aanleiding.class);
		this.controller.setValidator(new AanleidingBeeindigValidator());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testHandleRequestDeltaOK() throws Exception {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.getMelding().getBetrokkene().setBelastingJaar(this.BELASTINGJAAR);
		aanleiding.getMelding().getBetrokkene().setBurgerservicenummer(this.BSN);
		aanleiding.getMelding().setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		aanleiding.setRedenEinde(this.REDEN_EINDE_DELTA);

		// moet! true zijn (business rule)
		aanleiding.setBeeindigDezeAanleidingMetActueleAig(true);

		EasyMock.expect(this.handmatigBeoordelenMeldingService.beeindigAanleiding(aanleiding)).andReturn("teststring");
		EasyMock.replay(this.handmatigBeoordelenMeldingService);

		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("redenEinde", this.REDEN_EINDE_DELTA);
		request.setParameter("beeindigDezeAanleidingMetActueleAig", "true");

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("Er zou een afgemelde aanleiding op de view moeten staan.", mv.getModel().get("afgemeldeAanleiding"));
		assertEquals("Aanleiding is niet gelijk aan afgemelde aanleiding.", aanleiding, mv.getModel().get("afgemeldeAanleiding"));
		assertEquals("Invalid view name", "list", mv.getViewName());

	}

	@Test
	public void testHandleRequestDeltaNOK() throws Exception {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.getMelding().getBetrokkene().setBelastingJaar(this.BELASTINGJAAR);
		aanleiding.getMelding().getBetrokkene().setBurgerservicenummer(this.BSN);
		aanleiding.getMelding().setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		aanleiding.setRedenEinde(this.REDEN_EINDE_DELTA);

		// moet! true zijn (business rule)
		aanleiding.setBeeindigDezeAanleidingMetActueleAig(true);

		EasyMock.expect(this.handmatigBeoordelenMeldingService.beeindigAanleiding(aanleiding)).andReturn("teststring");
		EasyMock.replay(this.handmatigBeoordelenMeldingService);

		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("redenEinde", this.REDEN_EINDE_DELTA);
		request.setParameter("isBeeindigDezeAanleidingMetActueleAig", "false");

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNull("Er zou geen afgemelde aanleiding op de view moeten staan.", mv.getModel().get("afgemeldeAanleiding"));

		assertEquals("Invalid view name", "list", mv.getViewName());

	}

	@Test
	public void testHandleRequestAndersOK() throws Exception {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.getMelding().getBetrokkene().setBelastingJaar(this.BELASTINGJAAR);
		aanleiding.getMelding().getBetrokkene().setBurgerservicenummer(this.BSN);
		aanleiding.getMelding().setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		aanleiding.setRedenEinde(this.REDEN_EINDE_DELTA);

		// moet! true zijn (business rule)
		aanleiding.setBeeindigDezeAanleidingMetActueleAig(true);

		EasyMock.expect(this.handmatigBeoordelenMeldingService.beeindigAanleiding(aanleiding)).andReturn("teststring");
		EasyMock.replay(this.handmatigBeoordelenMeldingService);

		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("redenEinde", this.REDEN_EINDE_ANDERS);
		request.setParameter("redenEindeAnders", this.REDEN_EINDE_ANDERS_OMSCHRIJVING);

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNotNull("Er zou een afgemelde aanleiding op de view moeten staan.", mv.getModel().get("afgemeldeAanleiding"));
		assertEquals("Aanleiding is niet gelijk aan afgemelde aanleiding.", aanleiding, mv.getModel().get("afgemeldeAanleiding"));
		assertEquals("Invalid view name", "list", mv.getViewName());
	}

	@Test
	public void testHandleRequestAndersNOK() throws Exception {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.getMelding().getBetrokkene().setBelastingJaar(this.BELASTINGJAAR);
		aanleiding.getMelding().getBetrokkene().setBurgerservicenummer(this.BSN);
		aanleiding.getMelding().setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		aanleiding.setRedenEinde(this.REDEN_EINDE_DELTA);

		// moet! true zijn (business rule)
		aanleiding.setBeeindigDezeAanleidingMetActueleAig(true);

		EasyMock.expect(this.handmatigBeoordelenMeldingService.beeindigAanleiding(aanleiding)).andReturn("teststring");
		EasyMock.replay(this.handmatigBeoordelenMeldingService);

		request.setParameter("melding.betrokkene.burgerservicenummer", this.BSN.toString());
		request.setParameter("melding.betrokkene.belastingJaar", this.BELASTINGJAAR.toString());
		request.setParameter("melding.berichtkenmerkAig", this.BERICHTKENMERKAIG);
		request.setParameter("redenEinde", this.REDEN_EINDE_ANDERS);
		// post reden einde anders omschrijving niet mee. Zou dus fout moeten
		// gaan.
		// request.setParameter("redenEindeAnders",
		// this.REDEN_EINDE_ANDERS_OMSCHRIJVING);

		this.request.setMethod("POST");

		ModelAndView mv = this.controller.handleRequest(this.request, this.response);

		assertNull("Er zou geen afgemelde aanleiding op de view moeten staan.", mv.getModel().get("afgemeldeAanleiding"));

		assertEquals("Invalid view name", "list", mv.getViewName());

	}

}
