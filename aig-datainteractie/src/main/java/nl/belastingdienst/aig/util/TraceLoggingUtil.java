package nl.belastingdienst.aig.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility klasse voor trace logging.
 * 
 * @author vriej39
 */
public final class TraceLoggingUtil {

	private static final int MAX_ARG_LENGTH = 1000;

	/**
	 * Logt een ENTERING statement met klassenaam van target en methodenaam.
	 * 
	 * @param target
	 * @param methodName
	 */
	public static void enter(final Object target, final String methodName) {
		TraceLoggingUtil.enter(target, methodName, null);
	}

	/**
	 * Logt een ENTERING statement met klassenaam van target, methodenaam en String representatie van de argumenten die aan de
	 * methode zijn meegegeven.
	 * 
	 * @param target
	 * @param methodName
	 * @param args
	 */
	public static void enter(final Object target, final String methodName, final Object[] args) {
		final Log log = LogFactory.getLog(target.getClass());
		if (log.isTraceEnabled()) {
			final StringBuffer buf = new StringBuffer();
			buf.append("ENTERING ");
			buf.append(StringUtils.substringAfterLast(target.getClass()
				.getName(), "."));
			buf.append(".");
			buf.append(methodName);
			buf.append("(");
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					final Object arg = args[i];
					if (arg == null) {
						buf.append(arg);
					} else {
						final String value = ParseUtil.toString(arg);
						if (value.length() > TraceLoggingUtil.MAX_ARG_LENGTH) {
							buf.append("[");
							buf.append(i + 1);
							buf.append("e argument van type ");
							buf.append(arg.getClass()
								.getName());
							buf.append(" heeft te lange String representatie]");
						} else {
							buf.append(value);
						}
					}
					if (i < args.length - 1) {
						buf.append(", ");
					}
				}
			}
			buf.append(")");
			log.trace(buf.toString());
		}
	}

	/**
	 * Logt een EXITING statement met de klassenaam van target en methodenaam.
	 * 
	 * @param target
	 * @param methodName
	 */
	public static void exit(final Object target, final String methodName) {
		TraceLoggingUtil.exit(target, methodName, true, null);
	}

	/**
	 * Logt een EXITING statement met de klassenaam van target, methodenaam, en returnValue indien methode niet-void is.
	 * 
	 * @param target
	 * @param methodName
	 * @param isVoid
	 * @param returnValue
	 */
	public static void exit(final Object target, final String methodName, final boolean isVoid, final Object returnValue) {
		final Log log = LogFactory.getLog(target.getClass());
		if (log.isTraceEnabled()) {
			final StringBuffer buf = new StringBuffer();
			buf.append("EXITING  ");
			buf.append(StringUtils.substringAfterLast(target.getClass()
				.getName(), "."));
			buf.append(".");
			buf.append(methodName);
			buf.append("()");
			if (!isVoid) {
				buf.append(", RETURNING ");
				buf.append(ParseUtil.toString(returnValue));
			}
			log.trace(buf.toString());
		}
	}

	/**
	 * Logt een EXITING statement met de klassenaam van target, methodenaam en returnValue van de methode.
	 * 
	 * @param target
	 * @param methodName
	 * @param returnValue
	 */
	public static void exit(final Object target, final String methodName, final Object returnValue) {
		TraceLoggingUtil.exit(target, methodName, false, returnValue);
	}

	/**
	 * Verborgen constructor, vanwege utility.
	 */
	private TraceLoggingUtil() {
	}

}
