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

package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;
import nl.foo.AccidentalPerson;
import nl.foo.Announcement;
import nl.foo.MessageDAO;

/**
 * Business service voor het onderhouden van Waarden.
 * 
 * @author manef00
 */
public final class OnderhoudenMeldingServiceImpl implements ManageMessageService {

	private MessageDAO meldingDao;

	private PersoonDAO persoonDao;

	private List<String> list = new ArrayList<String>();

	private List<MessageDAO> list2 = new ArrayList<MessageDAO>();

	public OnderhoudenMeldingServiceImpl(String in) {

	}

	public OnderhoudenMeldingServiceImpl() {

	}

	/*
	 * 
	 * 
	 * Hieronder een korte beschrijving hoe om te gaan met het afmelden van een
	 * Announcement via JMS / MQ in het algemeen en vooral het omgaan met JAXB en MQ
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

	public List<Announcement> findAllByExample(Announcement melding) {
		List<Announcement> result = this.getMeldingDao().findAllWhere(melding.getAccidentalPerson(), melding.getMessageDigestOtherParty());

		return result;
	}

	public List<Announcement> getMessagesWithStateManual() {
		return this.getMeldingDao().findAllWhereStateIsManual();
	}

	public void testMe(String in) throws InterruptedException {

		this.getMeldingDao().setMessage("test");

	}

	public List<Announcement> testMetGenerics() {
		return this.getMeldingDao().getList();
	}

	@CreateUnittest(in = { "andereBetrokkene", "anderBerichtkenmerkAig" }, out = "melding")
	public Announcement getMessage(AccidentalPerson betrokkene, String berichtkenmerkAig) {

		String voornaam = "Raymond";
		Announcement resultMelding = this.meldingDao.getMessage(betrokkene, berichtkenmerkAig, voornaam);
		String test = this.meldingDao.getMessage(betrokkene, berichtkenmerkAig, voornaam).getMessageDigest();

		if (this.getMeldingDao() != null && this.meldingDao.getMessage(betrokkene, "riemar", voornaam).getMessageDigest().equals("mijn") && true) {
			System.out.println("equal");
		}

		return resultMelding;
	}

	@CreateUnittest(in = "number", out = "string")
	public String getNumber(int aNumber) {
		String result = this.persoonDao.getSofi(aNumber);

		return result;
	}

	public PersoonDAO getPersoonDao() {
		return persoonDao;
	}

	public void setPersoonDao(PersoonDAO persoonDao) {
		this.persoonDao = persoonDao;
	}

	@CreateUnittest(out = "melding")
	public Announcement geefMelding() {

		list.add("aap");

		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		betrokkene.setSocialSecurityNumber(127797592);
		betrokkene.setYear((short) 2012);

		Announcement melding = new Announcement();
		melding.setMessageDigest("aig02");
		melding.setAccidentalPerson(betrokkene);

		return this.getMeldingDao().getMessage();

	}

	// @CreateUnittest
	public void zonderReturn() {

	}

	// @CreateUnittest(in="berichtkenmerkAig")
	public void zonderReturnMetParams(String eenString) {

		boolean resultjetemp = list.add("aap");

	}

	// @CreateUnittest(out="melding")
	public Announcement geefTestMelding() {
		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		String berichtkenmerkAig = "teststring";
		String voornaam = "Raymond";

		Announcement resultMelding = this.getMeldingDao().getMessage(betrokkene, berichtkenmerkAig, voornaam);

		return resultMelding;

	}

	@Expect(out = "meldingDao")
	public MessageDAO getMeldingDao() {
		return this.meldingDao;
	}

	@Expect(in = "meldingDao")
	public void setMeldingDao(MessageDAO meldingDao) {
		this.meldingDao = meldingDao;
	}

	// x verder met het maken van literals ipv vars voor EasyMock ...
	// @CreateUnittest(in={"1", "2"}, out="3")
	// straks ook testen zonder parameters
	 @CreateUnittest(in={"2", "3"}, out="drie")
	public int add(int monkey, int horses) {

		int resultFromDao = this.getMeldingDao().add(monkey, horses);

		int result = monkey + horses;

		return result;
	}
}
