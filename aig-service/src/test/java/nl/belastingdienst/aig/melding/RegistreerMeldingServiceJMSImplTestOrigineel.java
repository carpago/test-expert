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
import nl.belastingdienst.aig.jms.RegistreerMeldingGateway;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class RegistreerMeldingServiceJMSImplTestOrigineel extends TestCase {

	private RegistreerMeldingGateway registreerMeldingGateway;
	private RegistreerMeldingServiceJMSImpl registreerMeldingService;

	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);

	private final Short BELASTINGJAAR_INVALID = new Short((short) 2033);
	private final Integer BSN_INVALID = new Integer(10101101);

	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";

	private final Date DATUMBEGINGELDIGHEIDDATE ;
	private final Date DATUMTIJDREGISTRATIEDATE;

	private final String OORSPRONKELIJKEMELDINGNAAM = "oorspronkelijke meldingnaam";
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derden";
	
	
	public RegistreerMeldingServiceJMSImplTestOrigineel() {
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

	@Override
	@Before
	public void setUp() {
		this.registreerMeldingService = new RegistreerMeldingServiceJMSImpl();
		this.registreerMeldingGateway = EasyMock.createMock(RegistreerMeldingGateway.class);
		this.registreerMeldingService.setRegistreerMeldingGateway(this.registreerMeldingGateway);
	}

	@Test
	public void testRegistreerMelding() throws Exception {
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		melding.setWaarde(100);
		
		String expectedArgumentToGateway = new String("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Client2Mq><BSN>111111110</BSN><Belastingjaar>2010</Belastingjaar><DatumPlaatsGevonden>Fri Jan 01 00:00:00 CET 2010</DatumPlaatsGevonden><OorspronkelijkeMeldingnaam>oorspronkelijke meldingnaam</OorspronkelijkeMeldingnaam><Waarde>100</Waarde></Client2Mq>");

		this.registreerMeldingGateway.registreerMelding(expectedArgumentToGateway);
		EasyMock.replay(this.registreerMeldingGateway);

		this.registreerMeldingService.registreerMelding(melding);

	}

}