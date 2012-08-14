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

package nl.belastingdienst.aig.aanleiding;

import java.io.Serializable;
import java.util.Date;

import nl.belastingdienst.aig.melding.Melding;
import nl.belastingdienst.aig.onderzoek.Onderzoek;
import nl.belastingdienst.aig.waarde.Waarde;

/**
 * Aanleiding om een AIG in onderzoek te zetten Feitelijk is het een Melding met een status
 * 
 * @author manef00
 */
public class Aanleiding implements Serializable {
	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 2239076778607906110L;

	private Onderzoek aigOnderzoek = new Onderzoek();

	private Waarde aigWaarde1 = new Waarde();

	private Waarde aigWaarde2 = new Waarde();

	private Date datumOnherroepelijk;

	private Melding melding = new Melding();

	private String redenEinde;
	
	private String redenEindeAnders;

	private Date registratieTijdstipBegin;

	private Date registratieTijdstipEinde;
	
	// deze instance variabele wordt in edit.jsp gebruikt bij het afhandelen van de beeindiging. (net als redenEinde)
	private boolean beeindigDezeAanleidingMetActueleAig;
	
	/**
	 * 
	 * @return de Aanleiding met het formaat van JavaScript Object Notation (JSON). 
	 * Een JSON String heeft het volgende formaat (zie ook json.org) : {property:value, property:value, ...}
	 */
	public String getAsJson() {
		
		//rloman: die toString vanuit impliciete aanroep naar de tijdstippen. levert denk ik niet het gewenste formaat op. :-)
		String jsonString = "{";
		jsonString += "registratieTijdstipBegin:'"+this.getRegistratieTijdstipBegin()+"',";
		jsonString += "registratieTijdstipEinde:'"+this.getRegistratieTijdstipEinde()+"',";
		jsonString += "datumOnherroepelijk:'"+this.getDatumOnherroepelijk()+"',";
		jsonString += "redenEinde:'"+this.getRedenEinde()+"',";
		
		String meldingAsJson = "";
		
		Melding melding = this.getMelding();
		if(melding != null) {
			meldingAsJson = melding.getAsJson();
		}
		
		jsonString += "melding:"+meldingAsJson;
		
		
		//rloman: welke waarde dan ?? 1,2 ... ?
		String waardeAsJson = "";
		Waarde waarde = this.getAigWaarde1();
		if(waarde != null) {
			waardeAsJson = waarde.getAsJson();
		}
		
		jsonString += "aigWaarde1:"+waardeAsJson;
		
		jsonString+="}";
		
		return jsonString;
	}
	
	
	/**
	 * @return Retourneert aigOnderzoek.
	 */
	public Onderzoek getAigOnderzoek() {
		return this.aigOnderzoek;
	}

	/**
	 * @return Retourneert aigWaarde1.
	 */
	public Waarde getAigWaarde1() {
		return this.aigWaarde1;
	}

	/**
	 * @return Retourneert aigWaarde2.
	 */
	public Waarde getAigWaarde2() {
		return this.aigWaarde2;
	}

	/**
	 * @return Retourneert datumOnherroepelijk.
	 */
	public Date getDatumOnherroepelijk() {
		return this.datumOnherroepelijk;
	}

	/**
	 * @return Retourneert melding.
	 */
	public Melding getMelding() {
		return this.melding;
	}

	/**
	 * @return Retourneert redenEinde.
	 */
	public String getRedenEinde() {
		return this.redenEinde;
	}

	/**
	 * @return Retourneert regitratieTijdstipBegin.
	 */
	public Date getRegitratieTijdstipBegin() {
		return this.registratieTijdstipBegin;
	}

	/**
	 * @return Retourneert regitratieTijdstipEinde.
	 */
	public Date getRegitratieTijdstipEinde() {
		return this.registratieTijdstipEinde;
	}

	/**
	 * @param aigOnderzoek
	 *            wordt in aigOnderzoek gezet.
	 */
	public void setAigOnderzoek(Onderzoek aigOnderzoek) {
		this.aigOnderzoek = aigOnderzoek;
	}

	/**
	 * @param aigWaarde1
	 *            wordt in aigWaarde1 gezet.
	 */
	public void setAigWaarde1(Waarde aigWaarde1) {
		this.aigWaarde1 = aigWaarde1;
	}

	/**
	 * @param aigWaarde2
	 *            wordt in aigWaarde2 gezet.
	 */
	public void setAigWaarde2(Waarde aigWaarde2) {
		this.aigWaarde2 = aigWaarde2;
	}

	/**
	 * @param datumOnherroepelijk
	 *            wordt in datumOnherroepelijk gezet.
	 */
	public void setDatumOnherroepelijk(Date datumOnherroepelijk) {
		this.datumOnherroepelijk = datumOnherroepelijk;
	}

	/**
	 * @param melding
	 *            wordt in melding gezet.
	 */
	public void setMelding(Melding melding) {
		this.melding = melding;
	}

	/**
	 * @param redenEinde
	 *            wordt in redenEinde gezet.
	 */
	public void setRedenEinde(String redenEinde) {
		this.redenEinde = redenEinde;
	}

	/**
	 * @param registratieTijdstipBegin
	 */
	public void setRegitratieTijdstipBegin(Date registratieTijdstipBegin) {
		this.registratieTijdstipBegin = registratieTijdstipBegin;
	}

	/**
	 * @param registratieTijdstipEinde
	 */
	public void setRegitratieTijdstipEinde(Date registratieTijdstipEinde) {
		this.registratieTijdstipEinde = registratieTijdstipEinde;
	}

	public Date getRegistratieTijdstipBegin() {
		return registratieTijdstipBegin;
	}

	public void setRegistratieTijdstipBegin(Date registratieTijdstipBegin) {
		this.registratieTijdstipBegin = registratieTijdstipBegin;
	}

	public Date getRegistratieTijdstipEinde() {
		return registratieTijdstipEinde;
	}

	public void setRegistratieTijdstipEinde(Date registratieTijdstipEinde) {
		this.registratieTijdstipEinde = registratieTijdstipEinde;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((melding == null) ? 0 : melding.hashCode());
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
		Aanleiding other = (Aanleiding) obj;
		if (melding == null) {
			if (other.melding != null)
				return false;
		} else if (!melding.equals(other.melding))
			return false;
		return true;
	}


	public String getRedenEindeAnders() {
		return redenEindeAnders;
	}


	public void setRedenEindeAnders(String redenEindeAnders) {
		this.redenEindeAnders = redenEindeAnders;
	}


	public boolean isBeeindigDezeAanleidingMetActueleAig() {
		return beeindigDezeAanleidingMetActueleAig;
	}


	public void setBeeindigDezeAanleidingMetActueleAig(boolean beeindigDezeAanleidingMetActueleAig) {
		this.beeindigDezeAanleidingMetActueleAig = beeindigDezeAanleidingMetActueleAig;
	}
}
