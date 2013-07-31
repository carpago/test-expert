package nl.carpago.testexpert;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;


class ReflectionTrainer {
	private List<String> lijst;
	
	public List<String> getLijst() {
		lijst = new ArrayList<String>();
		return lijst;
	}
}

/** This class acts as a wrapper for the Exception that occur
 * 
 * 
 * @author rloman
 *
 */
@SuppressWarnings("serial")
class CarpagoRuntimeException extends RuntimeException {

	public CarpagoRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CarpagoRuntimeException(Throwable cause) {
		super(cause);
	}
	
}

public class MainVoorTstDoeleinden {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		
		// testForIsCollectionOrSomething();
		new MainVoorTstDoeleinden().risky1();
	}

	
	public void risky1() {
		risky2();
	}
	
	public void risky2() {
		risky3();
	}
	
	public void risky3() {
		File file = new File("dezebestaatniet");
		Scanner s;
		try {
			s = new Scanner(file);
			while(s.hasNext()) {
				System.out.println(s.next());
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new CarpagoRuntimeException(e);
		}
		
	}
	
	public static void testForIsCollectionOrSomething() {
		List<String> lijst = new ArrayList<String>();
		
		Class <?> clazz = lijst.getClass();
		
		if(Collection.class.isAssignableFrom(clazz)) {
			System.out.println("its a collection");
		}
		
	}
	
	
	
	
	
	public static Object converteer(Object o) {
		return o;
	}
	
	public static void convertSlash() {
		String path = ".\\src\\main\\java";
		
		String line = path.replaceAll("\\\\", "/");
		
		System.out.println(line);
		
	}
	
	public void fileOpe() {
		String path = ".";
		
		File folder = new File(path);
		
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles) {
			if(file.isFile()) {
				System.out.println(file.getName());
				String fileName = file.getName();
				boolean testResult = fileName.endsWith("java");
				if(testResult)
				{
					//do dome stuff
				}
			}
		}
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
