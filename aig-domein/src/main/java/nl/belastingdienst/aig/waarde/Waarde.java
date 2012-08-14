/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CAO 
 *            Creator: Fabian van Manen en Raymond Loman
 *          Copyright: (c) 2011 Belastingdienst / Centrum voor Applicatieontwikkeling en Onderhoud,
 *                         All Rights Reserved.
 *  ---------------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ---------------------------------------------------------------------------------------------------------
 *
 */

package nl.belastingdienst.aig.waarde;

import java.io.Serializable;
import java.util.Date;

import nl.belastingdienst.aig.betrokkene.Betrokkene;

/**
 * Een AIG-waarde is het (actuele) AIG of een eerder AIG
 * 
 */
public class Waarde implements Serializable {
	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = -9069422817277335250L;

	private String aardBepalingCode;

	private Betrokkene betrokkene;

	private Date datumBepalingBron;

	private Date datumEinde;

	private Date datumIngang;

	private Date datumTijdGeregistreerd;

	private Date datumVervallen;

	private Integer dossierVolgnummer;

	private String grondslagCode;

	private Double waarde;
	
	public Waarde() {
		this.betrokkene = new Betrokkene();
	}
	
	/**
	 * 
	 * @return het Object in het formaat van JavaScript Object Notation (JSON). 
	 * 
	 * Een JSON String heeft het volgende formaat (zie ook json.org) : {property:value, property:value, ...}
	 * De JSON strings kun je gebruiken binnen JavaScript pagina's (zoals show.jsp van Onderzoek om het object mee te geven in een methode aanroep.
	 * Er zijn ook Json frameworks voor Java maar vanwege de kleinschaligheid van gebruik hier nu met de hand. Weet nu ook niet wat WebSTraat over JSON zegt.
	 * 
	 */
	public String getAsJson() {
		
		//rloman: die datumvelden hieronder leveren denk ik niet het gewenste formaat op. :-)
		String jsonString = "{";
		
		jsonString += "datumIngang:'"+this.getDatumIngang()+"',";
		jsonString += "datumEinde:'"+this.getDatumEinde()+"',";
		
		String datumIngangDatumEinde=this.getDatumIngang()+" / "+this.getDatumEinde();
		jsonString += "datumIngangDatumEinde:'" + datumIngangDatumEinde+"',";
		
		jsonString += "datumTijdGeregistreerd:'"+this.getDatumTijdGeregistreerd()+"',";
		jsonString += "waarde:'"+this.getWaarde()+"',";
		jsonString += "grondslagCode:'"+this.getGrondslagCode()+"',";
		jsonString += "aardBepalingCode:'"+this.getAardBepalingCode()+"'";
		
		jsonString+="}";
		
		return jsonString;
	}


	/**
	 * @return Retourneert aardBepalingCode.
	 */
	public String getAardBepalingCode() {
		return this.aardBepalingCode;
	}

	/**
	 * @return Retourneert betrokkene.
	 */
	public Betrokkene getBetrokkene() {
		return this.betrokkene;
	}

	/**
	 * @return Retourneert datumBepalingBron.
	 */
	public Date getDatumBepalingBron() {
		return this.datumBepalingBron;
	}

	/**
	 * @return Retourneert datumEinde.
	 */
	public Date getDatumEinde() {
		return this.datumEinde;
	}

	/**
	 * @return Retourneert datumIngang.
	 */
	public Date getDatumIngang() {
		return this.datumIngang;
	}

	/**
	 * @return Retourneert datumTijdGeregistreerd.
	 */
	public Date getDatumTijdGeregistreerd() {
		return this.datumTijdGeregistreerd;
	}

	/**
	 * @return Retourneert datumVervallen.
	 */
	public Date getDatumVervallen() {
		return this.datumVervallen;
	}

	/**
	 * @return Retourneert dossierVolgnummer.
	 */
	public Integer getDossierVolgnummer() {
		return this.dossierVolgnummer;
	}

	/**
	 * @return Retourneert grondslagCode.
	 */
	public String getGrondslagCode() {
		return this.grondslagCode;
	}

	/**
	 * @return Retourneert waarde.
	 */
	public Double getWaarde() {
		return this.waarde;
	}

	/**
	 * @param aardBepalingCode
	 *            wordt in aardBepalingCode gezet.
	 */
	public void setAardBepalingCode(String aardBepalingCode) {
		this.aardBepalingCode = aardBepalingCode;
	}

	/**
	 * @param betrokkene
	 *            wordt in betrokkene gezet.
	 */
	public void setBetrokkene(Betrokkene betrokkene) {
		this.betrokkene = betrokkene;
	}

	/**
	 * @param datumBepalingBron
	 *            wordt in datumBepalingBron gezet.
	 */
	public void setDatumBepalingBron(Date datumBepalingBron) {
		this.datumBepalingBron = datumBepalingBron;
	}

	/**
	 * @param datumEinde
	 *            wordt in datumEinde gezet.
	 */
	public void setDatumEinde(Date datumEinde) {
		this.datumEinde = datumEinde;
	}

	/**
	 * @param datumIngang
	 *            wordt in datumIngang gezet.
	 */
	public void setDatumIngang(Date datumIngang) {
		this.datumIngang = datumIngang;
	}

	/**
	 * @param datumTijdGeregistreerd
	 *            wordt in datumTijdGeregistreerd gezet.
	 */
	public void setDatumTijdGeregistreerd(Date datumTijdGeregistreerd) {
		this.datumTijdGeregistreerd = datumTijdGeregistreerd;
	}

	/**
	 * @param datumVervallen
	 *            wordt in datumVervallen gezet.
	 */
	public void setDatumVervallen(Date datumVervallen) {
		this.datumVervallen = datumVervallen;
	}

	/**
	 * @param dossierVolgnummer
	 *            wordt in dossierVolgnummer gezet.
	 */
	public void setDossierVolgnummer(Integer dossierVolgnummer) {
		this.dossierVolgnummer = dossierVolgnummer;
	}

	/**
	 * @param grondslagCode
	 *            wordt in grondslagCode gezet.
	 */
	public void setGrondslagCode(String grondslagCode) {
		this.grondslagCode = grondslagCode;
	}

	/**
	 * @param waarde
	 *            wordt in waarde gezet.
	 */
	public void setWaarde(Double waarde) {
		this.waarde = waarde;
	}
}
