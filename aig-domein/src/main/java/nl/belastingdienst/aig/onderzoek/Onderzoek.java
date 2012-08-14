/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CAO 
 *            Creator: manef00
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

package nl.belastingdienst.aig.onderzoek;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.belastingdienst.aig.aanleiding.Aanleiding;
import nl.belastingdienst.aig.betrokkene.Betrokkene;

/**
 * Onderzoek tegen een AIG
 * 
 * @author manef00
 */
public class Onderzoek implements Serializable {
	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 5168666648784847423L;

	private Betrokkene betrokkene;

	private Date datumEinde;

	private Date datumIngang;

	private String indicatieGeattendeerd;

	private String ketenVanOnderzoek;

	private String medewerker;
	
	private List<Aanleiding> aanleidingList = new ArrayList<Aanleiding>();
	
	public Onderzoek() {
		this.betrokkene = new Betrokkene(127797592, (short) 2012);
	}
	
	public Aanleiding getEersteAanleidingVanDitOnderzoek() {
		Aanleiding deEerste = this.getAanleidingList().get(0);
		if(deEerste == null) {
			deEerste = new Aanleiding();
		}
		
		return deEerste;
	}

	/**
	 * @return Retourneert aanleidingList.
	 */
	public List<Aanleiding> getAanleidingList() {
		return this.aanleidingList;
	}

	/**
	 * @return Retourneert betrokkene.
	 */
	public Betrokkene getBetrokkene() {
		return this.betrokkene;
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
	 * @return Retourneert indicatieGeattendeerd.
	 */
	public String getIndicatieGeattendeerd() {
		return this.indicatieGeattendeerd;
	}

	/**
	 * @return Retourneert ketenVanOnderzoek.
	 */
	public String getKetenVanOnderzoek() {
		return this.ketenVanOnderzoek;
	}

	/**
	 * @return Retourneert medewerker.
	 */
	public String getMedewerker() {
		return this.medewerker;
	}

	/**
	 * @param aanleidingList
	 *            wordt in aanleidingList gezet.
	 */
	public void setAanleidingList(List<Aanleiding> aanleidingList) {
		this.aanleidingList = aanleidingList;
	}

	/**
	 * @param betrokkene
	 *            wordt in betrokkene gezet.
	 */
	public void setBetrokkene(Betrokkene betrokkene) {
		this.betrokkene = betrokkene;
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
	 * @param indicatieGeattendeerd
	 *            wordt in indicatieGeattendeerd gezet.
	 */
	public void setIndicatieGeattendeerd(String indicatieGeattendeerd) {
		this.indicatieGeattendeerd = indicatieGeattendeerd;
	}

	/**
	 * @param ketenVanOnderzoek
	 *            wordt in ketenVanOnderzoek gezet.
	 */
	public void setKetenVanOnderzoek(String ketenVanOnderzoek) {
		this.ketenVanOnderzoek = ketenVanOnderzoek;
	}

	/**
	 * @param medewerker
	 *            wordt in medewerker gezet.
	 */
	public void setMedewerker(String medewerker) {
		this.medewerker = medewerker;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((betrokkene == null) ? 0 : betrokkene.hashCode());
		result = prime * result + ((datumIngang == null) ? 0 : datumIngang.hashCode());
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
		Onderzoek other = (Onderzoek) obj;
		if (betrokkene == null) {
			if (other.betrokkene != null)
				return false;
		} else if (!betrokkene.equals(other.betrokkene))
			return false;
		if (datumIngang == null) {
			if (other.datumIngang != null)
				return false;
		} else if (!datumIngang.equals(other.datumIngang))
			return false;
		return true;
	}
}
