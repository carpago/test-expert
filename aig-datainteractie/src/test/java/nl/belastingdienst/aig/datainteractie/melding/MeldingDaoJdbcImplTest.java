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

package nl.belastingdienst.aig.datainteractie.melding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.dao.MeldingDAO;
import nl.belastingdienst.aig.dao.MeldingDAOJdbcImpl;
import nl.belastingdienst.aig.melding.Melding;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class MeldingDaoJdbcImplTest extends TestCase {
	
	//queries
	private String querySelectWhereBsnJaarBerichtkenmerkDerden = "select MEL_BTK_BELJAAR, MEL_BTK_BSN, MEL_BERICHTKENM, MEL_DAT_PLTS_GVNDN, MEL_STATUS_MELDING, MEL_REDEN_NIETVERW, MEL_TS_REG, MEL_OORSPR_MELD_NAAM, MEL_VERK_MELD_NAAM, MEL_FISC_BRON, MEL_BERICHTKENM_DERDEN, MEL_WAARDE, MEL_VKTK_OPDR_ID, MEL_TAAKOPDR_ID from MELDING m where m.MEL_BTK_BSN=? and b.MEL_BTK_BSN=? and m.MEL_BERICHTKENM_DERDEN=?";
	private String querySelectWhereBsnJaarBerichtkenmerkAig = "select MEL_BTK_BELJAAR, MEL_BTK_BSN, MEL_BERICHTKENM, MEL_DAT_PLTS_GVNDN, MEL_STATUS_MELDING, MEL_REDEN_NIETVERW, MEL_TS_REG, MEL_OORSPR_MELD_NAAM, MEL_VERK_MELD_NAAM, MEL_FISC_BRON, MEL_BERICHTKENM_DERDEN, MEL_WAARDE, MEL_VKTK_OPDR_ID, MEL_TAAKOPDR_ID from MELDING m where m.MEL_BTK_BSN=? and b.MEL_BTK_BSN=? and m.MEL_BERICHTKENM=?";
	private String querySelectAll = "select * from MELDING";
	private String querySelectWhereStatusHandmatigeAfhandeling = "select MEL_BTK_BELJAAR, MEL_BTK_BSN, MEL_BERICHTKENM, MEL_DAT_PLTS_GVNDN, MEL_STATUS_MELDING, MEL_REDEN_NIETVERW, MEL_TS_REG, MEL_OORSPR_MELD_NAAM, MEL_VERK_MELD_NAAM, MEL_FISC_BRON, MEL_BERICHTKENM_DERDEN, MEL_WAARDE, MEL_VKTK_OPDR_ID, MEL_TAAKOPDR_ID from MELDING m where m.MEL_STATUS_MELDING='beoordeling heeft geleid tot handmatige afhandeling'";


	private MeldingDAOJdbcImpl meldingDAOJdbcImpl;
	private JdbcTemplate jdbcTemplate;

	private final Short BELASTINGJAAR = new Short((short) 2010);
	private final Integer BSN = new Integer(111111110);

	private final Short BELASTINGJAAR_INVALID = new Short((short) 2033);
	private final Integer BSN_INVALID = new Integer(10101101);

	private final String DATUMBEGINGELDIGHEIDSTRING = "01-01-2010";
	private final String DATUMTIJDREGISTRATIESTRING = "09-08-2010";

	private final Date DATUMBEGINGELDIGHEIDDATE;
	private final Date DATUMTIJDREGISTRATIEDATE;

	private final String OORSPRONKELIJKEMELDINGNAAM = "oorspronkelijke meldingnaam";
	private final String BERICHTKENMERKDERDEN = "berichtkenmerk derden";
	private final String BERICHTKENMERKAIG = "berichtkenmerk aig";

	public MeldingDaoJdbcImplTest() {
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
		this.meldingDAOJdbcImpl = new MeldingDAOJdbcImpl();
		this.jdbcTemplate = new JdbcTemplate();
	}

	@Test
	public void testFindAllWhere() throws Exception {
		
		//tests: public List<Melding> findAllWhere(Betrokkene betrokkene, String berichtKenmerkDerden) {
		
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
		
		//EasyMock.expect(this.jdbcTemplate.query(this.)

				//rloman:hierverder met maken unittesten MeldingDao ... en daarna Aanleiding en Onderzoek.
				// denk eraan dat Melding en Aanleiding meerdere Services hebben. Achteraf is dat wellicht niet zo'n handige
				// keuze.
		/*
			List<Melding> result = this.getJdbcTemplate().query(this.querySelectWhereBsnJaarBerichtkenmerkDerden,
				new Object[] { belastingJaar, burgerservicenummer, berichtKenmerkAig }, this);
		
		*/
		List<Melding> resultFromDao = this.meldingDAOJdbcImpl.findAllWhere(betrokkene, this.BERICHTKENMERKDERDEN);
		
		assertNotNull(resultFromDao);
		assertEquals(meldingen, resultFromDao);
		
	}
}