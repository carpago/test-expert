/*
 *
 *  ----------------------------------------------------------------------------------------------------
 *              Ident: Belastingdienst - B/CICT
 *            Creator: tolls00
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

package nl.belastingdienst.util;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Taglib van BRS
 * 
 * @author tolls00
 */
public class FormatSofinummer extends TagSupport {

	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;
	private String tekst;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doEndTag() {
		if (this.tekst!=null) {
			try {
				String resultTekst = this.tekst;
				int lengte = resultTekst.length();
				resultTekst = ("000000000" + resultTekst);
				lengte = resultTekst.length();
				resultTekst = resultTekst.substring((lengte - 9), lengte);
				String PUNT = ".";
				String a = resultTekst.substring(0, 4);
				String b = resultTekst.substring(4, 6);
				String c = resultTekst.substring(6, 9);
				resultTekst = a + PUNT + b + PUNT + c;
				this.pageContext.getOut()
					.println(resultTekst);
			} catch (final IOException e) {	} // Ignore it
		}
		return Tag.EVAL_PAGE;
	}

/*	*//**
	 * @return tekst in gewenste formaat
	 * 
	 *//*
	public int formatSofinummer() {
		if (this.tekst!=null) {
			try {
				String resultTekst = this.tekst;
				resultTekst = (resultTekst + "000000000").substring((resultTekst.length()-9), resultTekst.length());
				String PUNT = ".";
				String a = resultTekst.substring(0, 4);
				String b = resultTekst.substring(4, 6);
				String c = resultTekst.substring(6, 9);
				resultTekst = a + PUNT + b + PUNT + c;
				this.pageContext.getOut()
					.println(resultTekst);
			} catch (final IOException e) {	} // Ignore it
		}
		return Tag.EVAL_PAGE;
	}
*/
	/**
	 * @return sss
	 */
	public String getTekst() {
		return this.tekst;
	}

	/**
	 * @param tekst
	 */
	public void setTekst(final String tekst) {
		this.tekst = tekst;
	}

}
