package nl.carpago.unittestgenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl;
import nl.carpago.testexpert.AbstractTestExpert;
import nl.carpago.testexpert.Fixtures;
import nl.carpago.testexpert.UnittestGenerator;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Fixtures.class})
public class UnittestGeneratorTestHandWritten extends AbstractTestExpert { 

	// class under test
	private UnittestGenerator unittestGenerator;

	// collaborating classes
	private Logger logger;
	private Class classUnderTest;
	private Package pakkage;
	private Set imports;
	private List annotionsBeforeTestClass;
	private String header;
	private String body;
	private HashMap fixtures;
	private Set collabs;
	private ApplicationContext ctx;
	private Class contextClass;
	private String footer;
	private String eMPTY_STRING;
	private String qUESTION_MARK;
	private String aSTERISK;
	private String rESULTFROMMETHOD;

	@Before
	public void setUp() {
		this.unittestGenerator = new UnittestGenerator(OnderhoudenMeldingServiceImpl.class, Fixtures.class);
	}

	@Test
	public void testIsLiteral(){ 
		
		assertTrue(this.unittestGenerator.isLiteral("melding()"));
		
		assertFalse(this.unittestGenerator.isLiteral("melding"));
		
		assertTrue(this.unittestGenerator.isLiteral("123"));
		
		assertFalse(this.unittestGenerator.isLiteral("a123"));
		
		assertTrue(this.unittestGenerator.isLiteral("--"));

	}
	
	@Test
	public void testGetInAnnotationsForMethod() {
		 Method method = null;
		try {
			method = OnderhoudenMeldingServiceImpl.class.getMethod("geefMelding", Betrokkene.class, String.class);
		} catch(SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] expectedIn = {"andereBetrokkene","anderBerichtkenmerkAig"};
		
		String[] actualIn = this.unittestGenerator.getInAnnotationsForMethod(method);
		
		
		
		assertEquals(Arrays.asList(actualIn), Arrays.asList(expectedIn));
		
	}
	
	@Test
	public void testGetOutAnnotationForMethod() {
		 Method method = null;
			try {
				method = OnderhoudenMeldingServiceImpl.class.getMethod("geefMelding", Betrokkene.class, String.class);
			} catch(SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String expectedOut = "melding";
			
			String actualOut = this.unittestGenerator.getOutAnnotationForMethod(method);
			
			assertEquals(expectedOut, actualOut);
	}
	
	@Test
	public void testGenerateConstructorForClass() {
		Class c = OnderhoudenMeldingServiceImpl.class;
		
		
		String expected = "new OnderhoudenMeldingServiceImpl()";
		
		assertEquals(expected, this.unittestGenerator.generateConstructorForClass(c));
		
		c = Persoon.class;
		
	    expected = "new Persoon(17, new String())";
	    
	    assertEquals(expected, this.unittestGenerator.generateConstructorForClass(c));
		
	}
	
	@Test
	public void testGenerateSomethingForInterface() {
		
		Class<?> c = List.class;
		
		String expected = "EasyMock.createMock(List.class)";
		
		assertEquals(expected, this.unittestGenerator.generateSomethingForInterface(c));
		
		c = Set.class;
		
		expected  = "EasyMock.createMock(Set.class)";
		
		assertEquals(expected, this.unittestGenerator.generateSomethingForInterface(c));
		
	}
	
	@Test
	public void testGetParameterNamesForMethodWithInterface() {
		Method m = null;
		try {
			m = MijnLijst.class.getMethod("add", int.class, int.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] expectedParameterNames = {"int1", "int2"};
		
		String[] actualParameterNames = this.unittestGenerator.getParameterNamesForMethod(m);
		
		assertEquals(Arrays.asList(expectedParameterNames), Arrays.asList(actualParameterNames));
		
		
		
	}

	@Test
	public void testGetParameterNamesForMethodWithConcreteClass() {
		Method m = null;
		try {
			m = TestClass.class.getMethod("add", int.class, int.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] expectedParameterNames = {"first", "second"};
		
		String[] actualParameterNames = this.unittestGenerator.getParameterNamesForMethod(m);
		
		assertEquals(Arrays.asList(expectedParameterNames), Arrays.asList(actualParameterNames));
	}
	
	@Test
	public void testGetParameterNamesForMethodWithInterfaceWithStringAsParameters() {
		
		Method m = null;
		try {
			m = MijnLijst.class.getMethod("add", String.class, String.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] expectedParameterNames = {"string1", "string2"};
		
		String[] actualParameterNames = this.unittestGenerator.getParameterNamesForMethod(m);
		
		assertEquals(Arrays.asList(expectedParameterNames), Arrays.asList(actualParameterNames));
		
		
		
	}
	
	@Test
	public void testGetPrimitiveType() {
		// Class<?> getPrimitiveType(String baseType) {
		assertEquals(byte.class, this.unittestGenerator.getPrimitiveType("byte"));
		assertEquals(short.class, this.unittestGenerator.getPrimitiveType("short"));
		assertEquals(int.class, this.unittestGenerator.getPrimitiveType("int"));
		assertEquals(long.class, this.unittestGenerator.getPrimitiveType("long"));
		assertEquals(float.class, this.unittestGenerator.getPrimitiveType("float"));
		assertEquals(double.class, this.unittestGenerator.getPrimitiveType("double"));
		assertEquals(char.class, this.unittestGenerator.getPrimitiveType("char"));
		assertEquals(boolean.class, this.unittestGenerator.getPrimitiveType("boolean"));
		assertEquals(void.class, this.unittestGenerator.getPrimitiveType("void"));
		
		try {
			this.unittestGenerator.getPrimitiveType("invalid argument");
			fail("should never come here since the argument is not a valid Java type ...");
		}
		catch (Exception e) {
			// normal condition
		}
		
	}

	
	
}