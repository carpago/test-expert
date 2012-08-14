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

package nl.belastingdienst.aig.pit;

import java.util.Date;
import java.util.Hashtable;

import nl.belastingdienst.hit.pit.bo.Opdracht;

/**
 * Deze class simuleert het gedrag van de echte Pitopdracht. Hierdoor zijn wij (bouwers) in staat
 * de applicatie zonder aanwezigheid van de PIT te bouwen.
 * 
 * @author Raymond Loman / Stefan Tollenaar.
 */
public class PitSimOpdracht implements Opdracht {

	Hashtable<String, String> parameters = new Hashtable<String, String>();
	
	/**
	 * {@inheritDoc}
	 */
	public String getAanleiding() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getActiviteitId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBerichtKenmerk() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getConstatering() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getEindDatum() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getKlantId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLbNrDossier() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLbNrKlant() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getMiddel() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getNaamDossier() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getNaamKlant() {
		return "TollLoSoft";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOpdrachtId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public Hashtable getParameterLijst() {
		
		return this.parameters;
	}

	/**
	 * @return belastingjaar
	 */
	public String getJaar() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBsn() {
			
			return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public long getRacId() {
		
		return 0;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRuifId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSysteemId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTaakOpdrachtId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getWFId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String getWorklistId() {
		
		return null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAanleiding(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setActiviteitId(Integer arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBerichtKenmerk(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setConstatering(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEindDatum(Date arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setKlantId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLbNrDossier(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMiddel(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setJaar(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNaamDossier(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNaamKlant(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOpdrachtId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setParameterlijst(Hashtable arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRacId(long arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRuifId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSysteemId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTaakOpdrachtId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUserId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWFId(String arg0) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWorklistId(String arg0) {
		
		
	}
	
}
