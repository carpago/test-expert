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
public class FormatTekstToMax extends TagSupport {

	/**
	 * Commentaar voor &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;
	private String tekst;
	private String maxLengte;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doEndTag() {
		try {
			String resultTekst = this.tekst;
			final int lengte = Integer.parseInt(this.maxLengte);
			if (resultTekst.length() > lengte) {
				resultTekst = this.tekst.substring(0, (lengte - 1))
						+ "...";
			}
			this.pageContext.getOut()
				.println(resultTekst);
		} catch (final IOException e) {
		} // Ignore it
		return Tag.EVAL_PAGE;
	}

	/**
	 * @return sss
	 */
	public String getMaxLengte() {
		return this.maxLengte;
	}

	/**
	 * @return sss
	 */
	public String getTekst() {
		return this.tekst;
	}

	/**
	 * @param maxLengte
	 */
	public void setMaxLengte(final String maxLengte) {
		this.maxLengte = maxLengte;
	}

	/**
	 * @param tekst
	 */
	public void setTekst(final String tekst) {
		this.tekst = tekst;
	}

}
