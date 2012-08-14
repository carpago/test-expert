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

package nl.belastingdienst.aig.melding;

import java.util.Date;


/**
 * Deze class wordt door het Spring MVC framework gebruikt om de gePOSTte waarden in een HTML pagina op te vangen
 * , te valideren en te gebruiken in de desbetreffende controllers.
 * 
 * @author Raymond Loman.
 */

public class MeldingCommand {

	private Integer bsn;
	private Short belastingJaar;
	private Date datumPlaatsgevonden;
	private String berichtkenmerkDerden;
	private Double waarde;
	private String oorspronkelijkeMeldingnaam;
	
	
	public Integer getBsn() {
		return this.bsn;
	}
	public void setBsn(Integer bsn) {
		this.bsn = bsn;
	}
	public Short getBelastingJaar() {
		return this.belastingJaar;
	}
	public void setBelastingJaar(Short belastingJaar) {
		this.belastingJaar = belastingJaar;
	}
	public Date getDatumPlaatsgevonden() {
		return this.datumPlaatsgevonden;
	}
	public void setDatumPlaatsgevonden(Date datumPlaatsgevonden) {
		this.datumPlaatsgevonden = datumPlaatsgevonden;
	}
	public String getBerichtkenmerkDerden() {
		return this.berichtkenmerkDerden;
	}
	public void setBerichtkenmerkDerden(String berichtkenmerkDerden) {
		this.berichtkenmerkDerden = berichtkenmerkDerden;
	}
	public Double getWaarde() {
		return this.waarde;
	}
	public void setWaarde(Double waarde) {
		this.waarde = waarde;
	}
	public String getOorspronkelijkeMeldingnaam() {
		return this.oorspronkelijkeMeldingnaam;
	}
	public void setOorspronkelijkeMeldingnaam(String oorspronkelijkeMeldingnaam) {
		this.oorspronkelijkeMeldingnaam = oorspronkelijkeMeldingnaam;
	}
}
