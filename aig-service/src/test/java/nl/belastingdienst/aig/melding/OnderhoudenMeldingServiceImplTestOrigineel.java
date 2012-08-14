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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.MeldingDAO;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class OnderhoudenMeldingServiceImplTestOrigineel extends TestCase {

	private MeldingDAO meldingDAO;
	private OnderhoudenMeldingServiceImpl onderhoudenMeldingServiceImpl;

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
	private final String BERICHTKENMERKAIG = "berichtkenmerk aig";
	
	
	public OnderhoudenMeldingServiceImplTestOrigineel() {
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
		this.onderhoudenMeldingServiceImpl = new OnderhoudenMeldingServiceImpl();
		this.meldingDAO = EasyMock.createMock(MeldingDAO.class);
		this.onderhoudenMeldingServiceImpl.setMeldingDao(this.meldingDAO);
	}

	@Test
	public void testFindAllByExample() throws Exception {
		
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		melding.setWaarde(100);
		melding.setBerichtkenmerkDerden(this.BERICHTKENMERKDERDEN);
		melding.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding);
		
		EasyMock.expect(this.meldingDAO.findAllWhere(melding.getBetrokkene(), melding.getBerichtkenmerkDerden())).andReturn(meldingen);
		EasyMock.replay(this.meldingDAO);
		
		List<Melding> resultFromService = this.onderhoudenMeldingServiceImpl.findAllByExample(melding);
		
		assertNotNull(resultFromService);
		assertEquals(meldingen, resultFromService);
		
	}
	
	@Test
	public void testGeefMeldingenMetStatusHandmatigeAfhandeling()throws Exception {
		
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		melding.setWaarde(100);
		
		List<Melding> meldingen = new ArrayList<Melding>();
		meldingen.add(melding);
		
		EasyMock.expect(this.meldingDAO.findAllWhereStatusHandmatigeAfhandeling()).andReturn(meldingen);
		EasyMock.replay(this.meldingDAO);
		
		List<Melding> resultFromService = this.onderhoudenMeldingServiceImpl.geefMeldingenMetStatusHandmatigeAfhandeling();
		
		assertNotNull(resultFromService);
		assertEquals(meldingen, resultFromService);
		
	}
	
	public void testgeefMelding() {
		
		// tests: public Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig)
		
		Melding melding = new Melding();
		Betrokkene betrokkene = new Betrokkene(this.BSN, this.BELASTINGJAAR);
		melding.setBetrokkene(betrokkene);
		melding.setDatumPlaatsGevonden(this.DATUMBEGINGELDIGHEIDDATE);
		melding.setOorspronkelijkeMeldingNaam(this.OORSPRONKELIJKEMELDINGNAAM);
		melding.setWaarde(100);
		melding.setBerichtkenmerkDerden(this.BERICHTKENMERKDERDEN);
		melding.setBerichtkenmerkAig(this.BERICHTKENMERKAIG);
		
		
		EasyMock.expect(this.meldingDAO.geefMelding(melding.getBetrokkene(), melding.getBerichtkenmerkAig(),"Raymond")).andReturn(melding);
		EasyMock.replay(this.meldingDAO);
		
		Melding resultFromService = this.onderhoudenMeldingServiceImpl.geefMelding(betrokkene, this.BERICHTKENMERKAIG);
		assertTrue(OnderhoudenMeldingServiceImplMetSpringTest.equals(melding, resultFromService));
		
		assertNotNull(resultFromService);
		assertEquals(melding, resultFromService);
		
	}

	public MeldingDAO getMeldingDAO() {
		return meldingDAO;
	}

	public void setMeldingDAO(MeldingDAO meldingDAO) {
		this.meldingDAO = meldingDAO;
	}
	
	
}