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

package nl.belastingdienst;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import nl.carpago.testexpert.annotation.Valid;

/**
 * Aanleiding om een AIG in onderzoek te zetten Verschilt van Aanleiding door
 * het ontbreken van een status
 * 
 * Author: Fabian van Manen
 */
public class Melding /*implements Serializable */{

	private static final long serialVersionUID = -6625896018812577552L;

	// start key
	private Betrokkene betrokkene;

	@Valid(in="aig01", out="aig02")
	private String berichtkenmerkAig;
	// einde key
	private String berichtkenmerkDerden;

	private Date datumPlaatsGevonden;

	private Date datumTijdRegistratie;

	private String fiscaleBron;

	private String medewerker;

	private String oorspronkelijkeMeldingNaam;

	private String opdrachtIdTaak;

	private String opdrachtIdVktk;

	private String verkorteMeldingNaam;

	private Integer waarde;

	/*
	 * nodig voor het POSTen vanuit uc03 Beoordelen Melding (mist status niet in
	 * ontwerp ?) //rloman zie namelijk nergens ergens een 'status' terug.
	 * Nergens!!! Ook nodig in uc4 ... VAAG .
	 */
	private String status;
	
	
	
	
	public Melding() {
		this.betrokkene = new Betrokkene(1277, (short) 2012);
	}
	
	/**
	 * 
	 * @return het Object in het formaat van JavaScript Object Notation (JSON).
	 * 
	 *         Een JSON String heeft het volgende formaat (zie ook json.org) :
	 *         {property:value, property:value, ...} De JSON strings kun je
	 *         gebruiken binnen JavaScript pagina's (zoals show.jsp van
	 *         Onderzoek om het object mee te geven in een methode aanroep. Er
	 *         zijn ook Json frameworks voor Java maar vanwege de
	 *         kleinschaligheid van gebruik hier nu met de hand. Weet nu ook
	 *         niet wat WebSTraat over JSON zegt.
	 * 
	 */
	public String getAsJson() {
		// rloman: die datumvelden hieronder leveren denk ik niet het
		// gewenste formaat op. :-)
		String jsonString = "";
		jsonString += "{";

		jsonString += "oorspronkelijkeMeldingNaam:'" + this.getOorspronkelijkeMeldingNaam() + "',";
		jsonString += "status:'" + this.getStatus() + "',";
		jsonString += "berichtkenmerkDerden:'" + this.getBerichtkenmerkDerden() + "',";
		jsonString += "waarde:'" + this.getWaarde() + "',";
		jsonString += "datumPlaatsGevonden:'" + this.getDatumPlaatsGevonden() + "',";
		jsonString += "datumTijdRegistratie:'" + this.getDatumTijdRegistratie() + "',";
		jsonString += "verkorteMeldingNaam:'" + this.getVerkorteMeldingNaam() + "',";
		jsonString += "fiscaleBron:'" + this.getFiscaleBron() + "'"; // laatste
																		// property
																		// dus
																		// GEEN
																		// comma.

		jsonString += "}";
		
		return jsonString;
		
	}
	
	/**
	 * @return Retourneert berichtkenmerkAig.
	 */
	public String getBerichtkenmerkAig() {
		return this.berichtkenmerkAig;
	}

	/**
	 * @return Retourneert berichtkenmerkDerden.
	 */
	public String getBerichtkenmerkDerden() {
		return this.berichtkenmerkDerden;
	}

	/**
	 * @return Retourneert betrokkene.
	 */
	public Betrokkene getBetrokkene() {
		return this.betrokkene;
	}

	/**
	 * @return Retourneert datumPlaatsGevonden.
	 */
	public Date getDatumPlaatsGevonden() {
		return this.datumPlaatsGevonden;
	}

	/**
	 * @return Retourneert datumTijdRegistratie.
	 */
	public Date getDatumTijdRegistratie() {
		return this.datumTijdRegistratie;
	}

	/**
	 * @return Retourneert fiscaleBron.
	 */
	public String getFiscaleBron() {
		return this.fiscaleBron;
	}

	/**
	 * @return Retourneert medewerker.
	 */
	public String getMedewerker() {
		return this.medewerker;
	}

	/**
	 * @return Retourneert oorspronkelijkeMeldingnaam.
	 */
	public String getOorspronkelijkeMeldingNaam() {
		return this.oorspronkelijkeMeldingNaam;
	}

	/**
	 * @return Retourneert opdrachtIdTaak.
	 */
	public String getOpdrachtIdTaak() {
		return this.opdrachtIdTaak;
	}

	/**
	 * @return Retourneert opdrachtIdVktk.
	 */
	public String getOpdrachtIdVktk() {
		return this.opdrachtIdVktk;
	}

	/**
	 * @return Retourneert verkorteMeldingNaam.
	 */
	public String getVerkorteMeldingNaam() {
		return this.verkorteMeldingNaam;
	}

	/**
	 * @return Retourneert waarde.
	 */
	public Integer getWaarde() {
		return this.waarde;
	}

	/**
	 * @param berichtkenmerkAig
	 *            wordt in berichtkenmerkAig gezet.
	 */
	public void setBerichtkenmerkAig(String berichtkenmerkAig) {
		this.berichtkenmerkAig = berichtkenmerkAig;
	}

	/**
	 * @param berichtkenmerkDerden
	 *            wordt in berichtkenmerkDerden gezet. Veldnaam en type in XSD:
	 *            BerichtkenmerkDerden, string 32
	 */
	public void setBerichtkenmerkDerden(String berichtkenmerkDerden) {
		this.berichtkenmerkDerden = berichtkenmerkDerden;
	}

	/**
	 * @param betrokkene
	 *            wordt in betrokkene gezet.
	 */
	public void setBetrokkene(Betrokkene betrokkene) {
		this.betrokkene = betrokkene;
	}

	/**
	 * @param datumPlaatsGevonden
	 *            wordt in datumPlaatsGevonden gezet. Veldnaam en type in XSD:
	 *            DatumPlaatsGevonden, string met pattern
	 *            [2][0-9]{3}-[0-3][0-9]-[0-9]{2}
	 */
	public void setDatumPlaatsGevonden(Date datumPlaatsGevonden) {
		this.datumPlaatsGevonden = datumPlaatsGevonden;
	}

	/**
	 * @param datumTijdRegistratie
	 *            wordt in datumTijdRegistratie gezet.
	 */
	public void setDatumTijdRegistratie(Date datumTijdRegistratie) {
		this.datumTijdRegistratie = datumTijdRegistratie;
	}

	/**
	 * @param fiscaleBron
	 *            wordt in fiscaleBron gezet.
	 */
	public void setFiscaleBron(String fiscaleBron) {
		this.fiscaleBron = fiscaleBron;
	}

	/**
	 * @param medewerker
	 *            wordt in medewerker gezet. Veldnaam en type in XSD:
	 *            Medewerker, string 7
	 */
	public void setMedewerker(String medewerker) {
		this.medewerker = medewerker;
	}

	/**
	 * @param oorspronkelijkeMeldingnaam
	 *            wordt in oorspronkelijkeMeldingnaam gezet. Veldnaam en type in
	 *            XSD: OorspronkelijkeMeldingnaam, string 5
	 */
	public void setOorspronkelijkeMeldingNaam(String oorspronkelijkeMeldingnaam) {
		this.oorspronkelijkeMeldingNaam = oorspronkelijkeMeldingnaam;
	}

	/**
	 * @param opdrachtIdTaak
	 *            wordt in opdrachtIdTaak gezet.
	 */
	public void setOpdrachtIdTaak(String opdrachtIdTaak) {
		this.opdrachtIdTaak = opdrachtIdTaak;
	}

	/**
	 * @param opdrachtIdVktk
	 *            wordt in opdrachtIdVktk gezet.
	 */
	public void setOpdrachtIdVktk(String opdrachtIdVktk) {
		this.opdrachtIdVktk = opdrachtIdVktk;
	}

	/**
	 * @param verkorteMeldingNaam
	 *            wordt in verkorteMeldingNaam gezet.
	 */
	public void setVerkorteMeldingNaam(String verkorteMeldingNaam) {
		this.verkorteMeldingNaam = verkorteMeldingNaam;
	}

	/**
	 * @param waarde
	 *            wordt in waarde gezet. Veldnaam en type in XSD: Waarde,
	 *            integer 13
	 */
	public void setWaarde(Integer waarde) {
		this.waarde = waarde;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((berichtkenmerkAig == null) ? 0 : berichtkenmerkAig.hashCode());
		result = prime * result + ((betrokkene == null) ? 0 : betrokkene.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Melding other = (Melding) obj;
		if (berichtkenmerkAig == null) {
			if (other.berichtkenmerkAig != null)
				return false;
		} else if (!berichtkenmerkAig.equals(other.berichtkenmerkAig))
			return false;
		if (betrokkene == null) {
			if (other.betrokkene != null)
				return false;
		} else if (!betrokkene.equals(other.betrokkene))
			return false;
		return true;
	}
}
