/*
 * CompareUtil.java 17-mei-2007
 *
 *-----------------------------------------------------------------------------------
 *     Ident: Sector Ontwikkeling - B/CICT
 *    Author: vriej39
 * Copyright: (c) 2007 De Belastingdienst / Centrum voor ICT,  All Rights Reserved.
 *-----------------------------------------------------------------------------------
 * De Belastingdienst           |  Unpublished work. This computer program includes
 * Postbus 9050                 |  Confidential, Properietary Information and is a
 * 7300 GM Apeldoorn            |  trade Secret of the Belastingdienst. No part of
 * The Netherlands              |  this file may be reproduced or transmitted in any
 *                              |  form or by any means, electronic or mechanical,
 *                              |  for the purpose, without the express written
 *                              |  permission of the copyright holder.
 *-----------------------------------------------------------------------------------
 */
package nl.belastingdienst.aig.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Utility met null-safe vergelijkingsmethoden.
 * 
 * @author Jacob
 */
public final class CompareUtil {

	public static int compare(final String str1, final String str2) {
		int result = 0;
		if ((str1 != null)
				&& (str2 != null)) {
			result = str1.compareTo(str2);
		} else if (str1 != null) {
			result = -1;
		} else if (str2 != null) {
			result = 1;
		}
		return result;
	}

	public static boolean equals(final Date date1, final Date date2) {
		boolean result = false;
		if ((date1 == null)
				&& (date2 == null)) {
			result = true;
		} else if ((date1 == null)
				|| (date2 == null)) {
			result = false;
		} else {
			result = date1.equals(date2);
		}
		return result;
	}

	public static boolean equals(final String str1, final String str2) {
		return StringUtils.equals(str1, str2);
	}

	public static boolean equalsDays(final Date date1, final Date date2) {
		boolean result = false;
		if ((date1 == null)
				&& (date2 == null)) {
			result = true;
		} else if ((date1 == null)
				|| (date2 == null)) {
			result = false;
		} else {
			result = DateUtils.truncate(date1, Calendar.DAY_OF_MONTH)
				.equals(DateUtils.truncate(date2, Calendar.DAY_OF_MONTH));
		}
		return result;
	}

	public static boolean equalsSeconds(final Date date1, final Date date2) {
		boolean result = false;
		if ((date1 == null)
				&& (date2 == null)) {
			result = true;
		} else if ((date1 == null)
				|| (date2 == null)) {
			result = false;
		} else {
			result = DateUtils.truncate(date1, Calendar.SECOND)
				.equals(DateUtils.truncate(date2, Calendar.SECOND));
		}
		return result;
	}

	/**
	 * Constructor is verborgen, ivm utility karakter.
	 */
	private CompareUtil() {
	}

}
