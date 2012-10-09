/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: lomar00
 *          Copyright: (c) 2011 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
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
import java.util.List;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;
import nl.carpago.unittestgenerator.annotation.Expect;

/**
 * Business service voor het onderhouden van Waarden.
 * 
 * @author manef00
 */
public class OnderhoudenMeldingServiceImpl implements OnderhoudenMeldingService {

	private MeldingDAO meldingDao;
	
	private List <String> list = new ArrayList<String>();

	public OnderhoudenMeldingServiceImpl(String in) {

	}

	public OnderhoudenMeldingServiceImpl() {

	}

	/*
	 * 
	 * 
	 * Hieronder een korte beschrijving hoe om te gaan met het afmelden van een
	 * Melding via JMS / MQ in het algemeen en vooral het omgaan met JAXB en MQ
	 * in het bijzonder.
	 * 
	 * Van Dick Jansen en Ruud Wegdam de XSD voor AT300 ontvangen. Deze plaats
	 * je in de DAO directory onder de src/main/resources directory. In Maven2
	 * (zie webstraatvoorschrift) plaats je de dependency in de project pom en
	 * daarna gebruik je deze dependency ook in het web-project. Tot nu toe vrij
	 * standaard en straight-forward. Run $ mvn validate en eventueel (indien
	 * niet goed gaat, $ mvn clean install) waarna de jaxb jar wordt opgehaald
	 * van de webstraat-repo.
	 * 
	 * Je moet! nu wellicht weer $ mvn clean install draaien om jaxb zijn werk
	 * te laten doen. In ieder geval moet door jaxb een aantal classes zijn
	 * gegenereerd onder src/main/generated-java in het dao project. Deze
	 * classes kunnen we straks gaan vullen met data en daarna marshallen naar
	 * een string zodat we die via jms naar het mainframe kunnen sturen.
	 * 
	 * Het vullen van de data in de door jax-b gecreeerde classes.
	 * 
	 * rloman: verder aanvullen indien ik dit gedaaan heb :-) object boom maken
	 * met jax-b. new Aigmelding jaxb instantieren dan via get en set vullen. en
	 * dan . via jax-b via jax-b helper gewoon onder jax-b
	 * 
	 * dan string naar de gateway toe brengen. en dan bericht versturen zonder
	 * messageconverter.
	 * 
	 * niet aanroepen met bedrijf maar met de String die ik uit jaxb krijg.
	 * getJmsTemplate().convertAndSend(requestQueue, bericht);
	 * 
	 * zie url in mail.
	 */

	public List<Melding> findAllByExample(Melding melding) {
		List<Melding> result = this.getMeldingDao().findAllWhere(melding.getBetrokkene(), melding.getBerichtkenmerkDerden());

		return result;
	}

	public List<Melding> geefMeldingenMetStatusHandmatigeAfhandeling() {
		return this.getMeldingDao().findAllWhereStatusHandmatigeAfhandeling();
	}

	public void testMe(String in) throws InterruptedException {

		this.getMeldingDao().setMelding("test");

	}

	public List<Melding> testMetGenerics() {
		return this.getMeldingDao().getLijst();
	}

	@CreateUnittest(in={"andereBetrokkene","anderBerichtkenmerkAig"}, out="melding")
	public Melding geefMelding(Betrokkene betrokkene, String berichtkenmerkAig) {

		String voornaam = "Raymond";
		Melding resultMelding = this.meldingDao.geefMelding(betrokkene, berichtkenmerkAig, voornaam);
		
		return resultMelding;
	}
	
	@CreateUnittest(out="melding")
	public Melding geefMelding() {
		
		list.add("aap");
		
		Betrokkene betrokkene = new Betrokkene(127797592, (short) 2012);
		betrokkene.setBurgerservicenummer(127797592);
		betrokkene.setBelastingJaar((short) 2012);
		
		Melding melding = new Melding();
		melding.setBerichtkenmerkAig("aig02");
		melding.setBetrokkene(betrokkene);
		
		return this.getMeldingDao().geefMelding();
		
	}
	
	//@CreateUnittest
	public void zonderReturn() {
		
	}
	//@CreateUnittest(in="berichtkenmerkAig")
	public void zonderReturnMetParams(String eenString) {
		
		boolean resultjetemp = list.add("aap");
		
	}
	
	//@CreateUnittest(out="melding")
	public Melding geefTestMelding() {
		Betrokkene betrokkene = new Betrokkene(127797592, (short) 2012);
		String berichtkenmerkAig = "teststring";
		String voornaam = "Raymond";
		
		Melding resultMelding = this.getMeldingDao().geefMelding(betrokkene, berichtkenmerkAig, voornaam);
		
		return resultMelding;
		
	}

	@Expect(out="meldingDao")
	public MeldingDAO getMeldingDao() {
		return this.meldingDao;
	}

	@Expect(in="meldingDao")
	public void setMeldingDao(MeldingDAO meldingDao) {
		this.meldingDao = meldingDao;
	}

	
	// x verder met het maken van literals ipv vars voor EasyMock ...
	//@CreateUnittest(in={"1", "2"}, out="3")
	// straks ook testen zonder parameters
	//@CreateUnittest(in={"een", "twee"}, out="drie")
	public int telOp(int aap, int bees) {
		
		int resultFromDao = this.getMeldingDao().telOp(aap, bees);
		
	
		int result = aap + bees;
		
		
		return  result;
	}
	
}
