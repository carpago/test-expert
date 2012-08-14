package nl.carpago.testexpert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;

public  class AbstractTstExpert extends TestCase {

	public Object cloneMe(Object obj) {
		XStream xstream = new XStream();
		
		String toString = xstream.toXML(obj);
		Object result = xstream.fromXML(toString);
		
		assertTrue(obj != result);
		
		return result;
		
		// byte[] bytesArray = SerializationUtils.serialize(o);
		// Object result = SerializationUtils.deserialize(bytesArray);
		//Serializable result = (Serializable) org.apache.commons.lang.SerializationUtils.clone(serializable);
	}

	public boolean checkForDeepEquality(Object expected, Object actual) {
		XStream xstream = new XStream();
		String expectedAsString = xstream.toXML(expected);
		String actualAsString = xstream.toXML(actual);
		
		//System.out.println(een);
		// System.out.println(twee);
		boolean result = expectedAsString.equals(actualAsString); //= EqualsBuilder.reflectionEquals(expected, actual); werkt namelijk niet.

		return result;
	}
	
	public void setFieldThroughReflection(Object victim, String fieldName, Object what) {
		Field f = null;
		try {
			f = victim.getClass().getDeclaredField(fieldName);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		f.setAccessible(true);
		try {
			f.set(victim, what);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
	}
}
