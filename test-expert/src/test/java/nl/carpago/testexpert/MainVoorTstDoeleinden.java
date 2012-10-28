package nl.carpago.testexpert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


class ReflectionTrainer {
	private List<String> lijst;
	
	public List<String> getLijst() {
		return lijst;
	}
}

public class MainVoorTstDoeleinden {

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName("java.lang.String");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(clazz);

	}
	
	
	public static Object converteer(Object o) {
		return o;
	}
	
	public static void tryReflection() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ReflectionTrainer t = new ReflectionTrainer();
		Field f = t.getClass().getDeclaredField("lijst");

		List<String> l = new ArrayList<String>();
		l.add("first");
		l.add("second");
		
		f.setAccessible(true);
		f.set(t, l);
		
		List<String> lijstViaGetter = t.getLijst();
		
		for(String element : lijstViaGetter) {
			System.out.println(element);
		}
		
		
		
	}
	
	public static void setFieldThroughReflection(Object victim, String fieldName, Object what) throws SecurityException, NoSuchFieldException {
		Field f = victim.getClass().getDeclaredField(fieldName);
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

}
