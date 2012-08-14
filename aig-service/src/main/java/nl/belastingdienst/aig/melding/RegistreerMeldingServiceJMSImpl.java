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

import java.io.StringWriter;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.belastingdienst.aig.jms.RegistreerMeldingGateway;
import nl.belastingdienst.aig.melding.jaxb.AIGWEBBehandelenMelding;
import nl.belastingdienst.aig.melding.jaxb.ObjectFactory;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;

public class RegistreerMeldingServiceJMSImpl implements RegistreerMeldingService {
	
	private RegistreerMeldingGateway registreerMeldingGateway;

	@CreateUnittest(in="melding")
	public void registreerMelding(Melding melding) {
		// UC 02
		AIGWEBBehandelenMelding.Client2Mq client2Mq = new ObjectFactory().createAIGWEBBehandelenMeldingClient2Mq();

		client2Mq.setBelastingjaar(new BigInteger(new Short(melding.getBetrokkene().getBelastingJaar()).toString()));
		client2Mq.setBerichtkenmerkDerden(melding.getBerichtkenmerkDerden());
		client2Mq.setBSN(new BigInteger(new Integer(melding.getBetrokkene().getBurgerservicenummer()).toString()));

		// rloman: gaat dit goed? Moet hier geen formatter in ??
		client2Mq.setDatumPlaatsGevonden(melding.getDatumPlaatsGevonden()
			.toString());
		client2Mq.setMedewerker(melding.getMedewerker());
		client2Mq.setOorspronkelijkeMeldingnaam(melding.getOorspronkelijkeMeldingNaam());
		client2Mq.setWaarde(new BigInteger(new Integer(melding.getWaarde()).toString()));

		// convert to XML
		JAXBContext context = null;
		String result = null;
		StringWriter r = new StringWriter();
		try {
			context = JAXBContext.newInstance(client2Mq.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(client2Mq, r);
			result = r.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("JAX-BException in OnderhoudenMeldingServiceImpl::registreerMelding: "
					+ e, e);
		}
		

		this.getRegistreerMeldingGateway().registreerMelding(result);
	}

	public RegistreerMeldingGateway getRegistreerMeldingGateway() {
		return this.registreerMeldingGateway;
	}

	public void setRegistreerMeldingGateway(RegistreerMeldingGateway registreerMeldingGateway) {
		this.registreerMeldingGateway = registreerMeldingGateway;
	}

	
	
}
