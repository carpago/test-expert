package nl.belastingdienst.aig.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Utility class voor diverse parsing methoden.
 * 
 * @author vriej39
 */
public final class ParseUtil {

	private static final String FORMAT_TIMESTAMP = "dd-MM-yyyy HH:mm";

	private static final String FORMAT_DATE = "dd-MM-yyyy";

	private static final String FORMAT_TIME = "HH:mm";

	/**
	 * Telt het gegeven aantal dagen bij de gegeven datum op.
	 * 
	 * @param date
	 * @param numberOfDays
	 *            aantal dagen, mag negatief zijn
	 * @return aangepaste date
	 */
	public static Date addDaysToDate(final Date date, final int numberOfDays) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numberOfDays);
		return cal.getTime();
	}

	/**
	 * Formateert het gegeven Date object als een String in het formaat dd-MM-yyyy.
	 * 
	 * @param date
	 * @return String met datum in het formaat dd-MM-yyyy
	 */
	public static String formatDate(final Date date) {
		return DateFormatUtils.format(date, ParseUtil.FORMAT_DATE);
	}

	/**
	 * Formateert het gegeven Date object als een String in het formaat HH:mm.
	 * 
	 * @param date
	 * @return String met datum in het formaat HH:mm
	 */
	public static String formatTime(final Date date) {
		return DateFormatUtils.format(date, ParseUtil.FORMAT_TIME);
	}

	/**
	 * Formateert het gegeven Date object als een String in het formaat dd-MM-yyyy HH:mm.
	 * 
	 * @param date
	 * @return String met datum in het formaat dd-MM-yyyy HH:mm
	 */
	public static String formatTimestamp(final Date date) {
		return DateFormatUtils.format(date, ParseUtil.FORMAT_TIMESTAMP);
	}

	/**
	 * Maakt van de gegeven date String een Date object, als de date in het formaat dd-MM-yyyy is. Anders wordt <code>null</code>
	 * opgeleverd.
	 * 
	 * @param date
	 * @return Date, of null als gegeven date niet van het formaat dd-MM-yyyy is
	 */
	public static Date parseDate(final String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		Date parsedDate = null;
		try {
			parsedDate = DateUtils.parseDate(date, new String[] { ParseUtil.FORMAT_DATE });
		} catch (final ParseException e) {
			// fout inslikken, en null retourneren
			parsedDate = null;
		}
		return parsedDate;
	}

	/**
	 * Maakt van de gegeven time String een Date object, als de date in het formaat HH:mm is. Anders wordt <code>null</code>
	 * opgeleverd.
	 * 
	 * @param time
	 * @return Date, of null als gegeven date niet van het formaat HH:mm is
	 */
	public static Date parseTime(final String time) {
		if (StringUtils.isBlank(time)) {
			return null;
		}
		Date parsedDate = null;
		try {
			parsedDate = DateUtils.parseDate(time, new String[] { ParseUtil.FORMAT_TIME });
		} catch (final ParseException e) {
			// fout inslikken, en null retourneren
			parsedDate = null;
		}
		return parsedDate;
	}

	/**
	 * Maakt van de gegeven timestamp String een Date object, als de date in het formaat dd-MM-yyyy HH:mm is. Anders wordt
	 * <code>null</code> opgeleverd.
	 * 
	 * @param timestamp
	 * @return Date, of null als gegeven date niet van het formaat dd-MM-yyyy HH:mm is
	 */
	public static Date parseTimestamp(final String timestamp) {
		if (StringUtils.isBlank(timestamp)) {
			return null;
		}
		Date parsedDate = null;
		try {
			parsedDate = DateUtils.parseDate(timestamp, new String[] { ParseUtil.FORMAT_TIMESTAMP });
		} catch (final ParseException e) {
			// fout inslikken, en null retourneren
			parsedDate = null;
		}
		return parsedDate;
	}

	/**
	 * Geeft een string representatie van het gegeven object. Vooral bedoeld voor debug doeleinden.
	 * 
	 * @param obj
	 * @return String weergave van obj
	 */
	public static String toString(final Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Verborgen constructor, vanwege utility klasse.
	 */
	private ParseUtil() {
	}

}
