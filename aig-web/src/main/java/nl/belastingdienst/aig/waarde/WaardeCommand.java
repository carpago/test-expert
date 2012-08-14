///*
// *
// *  ----------------------------------------------------------------------------------------------------
// *              Ident: Belastingdienst - B/CICT
// *            Creator: lomar00
// *          Copyright: (c) 2010 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
// *  ----------------------------------------------------------------------------------------------------
// *                                              |   Unpublished work. This computer program includes
// *     De Belastingdienst                       |   Confidential, Properietary Information and is a
// *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
// *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
// *     The Netherlands                          |   form or by any means, electronic or mechanical,
// *     http://belastingdienst.nl/               |   for the purpose, without the express written
// *                                              |   permission of the copyright holder.
// *  ----------------------------------------------------------------------------------------------------
// *
// */
//
//package nl.belastingdienst.aig.waarde;
//
//import java.util.Date;
//
///**
// * Deze class wordt door het Spring MVC framework gebruikt om de gePOSTte waarden in een HTML pagina op te vangen
// * , te valideren en te gebruiken in de desbetreffende controllers.
// * 
// * @author Raymond Loman.
// */
//
//public class WaardeCommand {
//
//	private Short belastingJaar;
//
//	private Integer burgerservicenummer;
//
//	private Date datumBeginGeldigheid;
//
//	private Date datumtijdRegistratie;
//
//	public Short getBelastingJaar() {
//		return this.belastingJaar;
//	}
//
//	public Integer getBurgerservicenummer() {
//		return this.burgerservicenummer;
//	}
//
//	public Date getDatumBeginGeldigheid() {
//		return this.datumBeginGeldigheid;
//	}
//
//	public Date getDatumtijdRegistratie() {
//		return this.datumtijdRegistratie;
//	}
//
//	public void setBelastingJaar(final Short belastingJaar) {
//		this.belastingJaar = belastingJaar;
//	}
//
//	public void setBurgerservicenummer(final Integer burgerservicenummer) {
//		this.burgerservicenummer = burgerservicenummer;
//	}
//
//	public void setDatumBeginGeldigheid(final Date datumBeginGeldigheid) {
//		this.datumBeginGeldigheid = datumBeginGeldigheid;
//	}
//
//	public void setDatumtijdRegistratie(final Date datumtijdRegistratie) {
//		this.datumtijdRegistratie = datumtijdRegistratie;
//	}
//}
