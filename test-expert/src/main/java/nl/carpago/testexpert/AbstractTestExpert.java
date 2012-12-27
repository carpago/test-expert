package nl.carpago.testexpert;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;

public class AbstractTestExpert extends TestCase {

	private final Logger logger = Logger.getLogger(AbstractTestExpert.class);

	public Object cloneMe(Object obj) {
		XStream xstream = new XStream();

		String toString = xstream.toXML(obj);
		Object result = xstream.fromXML(toString);

		assertTrue(obj != result);

		return result;
	}

	public boolean checkForDeepEquality(Object expected, Object actual) {
		XStream xstream = new XStream();
		String expectedAsString = xstream.toXML(expected);
		expectedAsString = removeAllTagsAroundLiterals(expectedAsString);
		String actualAsString = xstream.toXML(actual);
		actualAsString = removeAllTagsAroundLiterals(actualAsString);

		boolean result = expectedAsString.equals(actualAsString);

		return result;
	}

	protected String removeAllTagsAroundLiterals(String expectedAsString) {

		String[] basicTypes = new String[] { "byte", "short", "int", "long", "float", "double", "char", "boolean", "string" };
		String result = expectedAsString;

		for (String basicType : basicTypes) {
			result = result.replaceAll("</?" + basicType + ">", "");
		}

		return result;
	}

	public Object setFieldThroughReflection(Object victim, String fieldName, Object what) {
		Field field = null;
		try {
			field = victim.getClass().getDeclaredField(fieldName);
		} catch (Exception exception) {
			logger.error(exception);
			throw new TestExpertException(exception);
		}
		if (field != null) {
			field.setAccessible(true);
			try {
				field.set(victim, what);
			} catch (Exception exception) {
				logger.error(exception);
				throw new TestExpertException(exception);
			}
		}

		return victim;
	}
}
