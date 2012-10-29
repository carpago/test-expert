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

package nl.belastingdienst;

import java.io.Serializable;

import nl.carpago.testexpert.annotation.Valid;

/**
 * Betrokkene is een natuurlijk persoon, die inkomen geniet in relatie tot het Nederlands Belastingrecht per belastingjaar
 * 
 * @author manef00
 */
public class Betrokkene implements Serializable {
	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = -8305974970017431717L;

	@Valid(in="2011", out="2012")
	private Short belastingJaar;

	@Valid(in="127797592", out="127797592")
	private Integer burgerservicenummer;

	private Integer geldigeActueleWaarde;

	private String indicatieInOnderzoek;
	
	public Betrokkene() {}

	/**
	 * @param burgerservicenummer
	 * @param belastingJaar
	 */
	public Betrokkene(Integer burgerservicenummer, Short belastingJaar) {
		this.burgerservicenummer = burgerservicenummer;
		this.belastingJaar = belastingJaar;
	}
	
	/**
	 * @return Retourneert belastingJaar.
	 */
	public Short getBelastingJaar() {
		return this.belastingJaar;
	}

	/**
	 * @return Retourneert burgerservicenummer.
	 */
	public Integer getBurgerservicenummer() {
		return this.burgerservicenummer;
	}

	/**
	 * @return Retourneert geldigeActueleWaarde.
	 */
	public Integer getGeldigeActueleWaarde() {
		return this.geldigeActueleWaarde;
	}

	/**
	 * @return Retourneert indicatieInOnderzoek.
	 */
	public String getIndicatieInOnderzoek() {
		return this.indicatieInOnderzoek;
	}

	/**
	 * @param belastingJaar
	 *            wordt in belastingJaar gezet. Veldnaam en type in XSD: Belastingjaar, positiveInteger 4
	 */
	public void setBelastingJaar(Short belastingJaar) {
		this.belastingJaar = belastingJaar;
	}

	/**
	 * @param burgerservicenummer
	 *            wordt in burgerservicenummer gezet.
	 */
	public void setBurgerservicenummer(Integer burgerservicenummer) {
		this.burgerservicenummer = burgerservicenummer;
	}

	/**
	 * @param geldigeActueleWaarde
	 *            wordt in geldigeActueleWaarde gezet.
	 */
	public void setGeldigeActueleWaarde(Integer geldigeActueleWaarde) {
		this.geldigeActueleWaarde = geldigeActueleWaarde;
	}

	/**
	 * @param indicatieInOnderzoek
	 *            wordt in indicatieInOnderzoek gezet.
	 */
	public void setIndicatieInOnderzoek(String indicatieInOnderzoek) {
		this.indicatieInOnderzoek = indicatieInOnderzoek;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belastingJaar == null) ? 0 : belastingJaar.hashCode());
		result = prime * result + ((burgerservicenummer == null) ? 0 : burgerservicenummer.hashCode());
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
		Betrokkene other = (Betrokkene) obj;
		if (belastingJaar == null) {
			if (other.belastingJaar != null)
				return false;
		} else if (!belastingJaar.equals(other.belastingJaar))
			return false;
		if (burgerservicenummer == null) {
			if (other.burgerservicenummer != null)
				return false;
		} else if (!burgerservicenummer.equals(other.burgerservicenummer))
			return false;
		return true;
	}
}
