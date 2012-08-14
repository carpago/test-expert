package nl.belastingdienst.aig.melding;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.dao.HandmatigBeoordelenMeldingDAO;
import nl.belastingdienst.aig.melding.jaxb.AIGHandmatigBeoordelenMelding;
import nl.belastingdienst.aig.melding.jaxb.TClient2Server;

public class HandmatigBeoordelenMeldingServiceCTGImpl implements HandmatigBeoordelenMeldingService {

	private HandmatigBeoordelenMeldingDAO handmatigBeoordelenMeldingDAO;

	private final String REDEN_EINDE_ANDERS = "anders";

	private Map<Actie, Integer> acties;

	/* Uit ontwerpdocumentatie: */
	/*
	 * 1 - Stoppen, 2 - Wacht, 3 - Voegen, 4 - Starten en 5 - Aanleidinge
	 * beeindigen.
	 */

	public HandmatigBeoordelenMeldingServiceCTGImpl() {
		this.acties = new HashMap<Actie, Integer>();
		acties.put(Actie.stoppen, 1);
		acties.put(Actie.parkeren, 2);
		acties.put(Actie.voegen, 3);
		acties.put(Actie.starten, 4);
		acties.put(Actie.beeindigen, 5);
	}

	public String handmatigAfhandelenMelding(Melding deAfTeHandelenMelding, Actie actie) {
		Aanleiding aanleiding = new Aanleiding();
		aanleiding.setMelding(deAfTeHandelenMelding);
		TClient2Server client2Server = this.mapAanleidingNaarClient2Server(aanleiding, actie);

		return meldClient2ServerMeldingAf(client2Server);
	}

	private String meldClient2ServerMeldingAf(TClient2Server client2Server) {
		AIGHandmatigBeoordelenMelding melding = new AIGHandmatigBeoordelenMelding();
		melding.setWeb2ZOS(client2Server);

		// convert to XML
		JAXBContext context = null;
		String afmeldString = null;
		StringWriter tijdelijkeStringWriter = new StringWriter();
		try {
			context = JAXBContext.newInstance(melding.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(melding, tijdelijkeStringWriter);
			afmeldString = tijdelijkeStringWriter.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("JAX-BException in OnderhoudenMeldingServiceImpl::registreerMelding: " + e, e);
		}

		String resultaatVanDeVerwerkingUitBackend = this.handmatigBeoordelenMeldingDAO.handmatigAfhandelenMelding(afmeldString);

		return resultaatVanDeVerwerkingUitBackend;
	}

	private TClient2Server mapAanleidingNaarClient2Server(Aanleiding aanleiding, Actie actie) {
		Melding melding = aanleiding.getMelding();
		TClient2Server client2Server = new TClient2Server();
		client2Server.setActieGebruiker(BigInteger.valueOf(acties.get(actie)));
		client2Server.setBelastingjaar(BigInteger.valueOf(melding.getBetrokkene().getBelastingJaar()));
		client2Server.setBerichtkenmerkAIG(melding.getBerichtkenmerkAig());
		client2Server.setBSN(BigInteger.valueOf(melding.getBetrokkene().getBurgerservicenummer()));

		// rloman: nog formatteren.
		client2Server.setDatumIngangAIO(melding.getDatumPlaatsGevonden().toString());
		client2Server.setDatumIngangAIW1(melding.getDatumPlaatsGevonden().toString());
		client2Server.setDatumIngangAIW2(melding.getDatumPlaatsGevonden().toString());
		client2Server.setDatumtijdGeregistreerdAIW1(melding.getDatumPlaatsGevonden().toString());
		client2Server.setDatumtijdGeregistreerdAIW2(melding.getDatumPlaatsGevonden().toString());
		client2Server.setMedewerker(melding.getMedewerker());
		client2Server.setOorspronkelijkeMeldingnaam(melding.getOorspronkelijkeMeldingNaam());

		// Dit gedeelte is eigenlijk alleen van toepassing voor uc4:
		// behandelen/beeindigen aanleiding.
		String redenEinde = aanleiding.getRedenEinde();
		if (this.REDEN_EINDE_ANDERS.equals(redenEinde)) {
			client2Server.setRedenEinde(aanleiding.getRedenEindeAnders());
		} else {
			client2Server.setRedenEinde(aanleiding.getRedenEinde());
		}

		client2Server.setStatusMelding(melding.getStatus());

		return client2Server;
	}

	public String beeindigAanleiding(Aanleiding deTeBeeindigeinAanleiding) {
		TClient2Server client2Server = this.mapAanleidingNaarClient2Server(deTeBeeindigeinAanleiding, Actie.beeindigen);

		return this.meldClient2ServerMeldingAf(client2Server);
	}

	public void setHandmatigBeoordelenMeldingDAO(HandmatigBeoordelenMeldingDAO handmatigBeoordelenMeldingDAO) {
		this.handmatigBeoordelenMeldingDAO = handmatigBeoordelenMeldingDAO;
	}

}
