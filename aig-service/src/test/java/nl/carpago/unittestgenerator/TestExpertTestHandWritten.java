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
import nl.carpago.testexpert.TestExpert;
import nl.carpago.unittestgenerator.annotation.CreateUnittest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Fixtures.class})
public class TestExpertTestHandWritten extends AbstractTestExpert { 
	
	
	class TestClassInner {
		
		@CreateUnittest
		public void test1() {
			
		}
		
		@CreateUnittest
		public void test2() {
			
		}
		
		@CreateUnittest
		public void test3() {
			
		}
		
		@CreateUnittest
		public String testForParameterNames(String one, String two, String three) {
			return ""+one+two+three;
		}
		
		public String testForParameterTypes(int one, String two, Person three) {
			
			return "string";
		}
		
		public String testForParameterTypesAndName(int one, String two, Person three) {
			return "string";
		}
	}
	
	class Person {
		
	}

	// class under test
	private TestExpert testExpert;

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
		this.testExpert = new TestExpert(OnderhoudenMeldingServiceImpl.class, Fixtures.class);
	}

	@Test
	public void testIsLiteral(){ 
		
		assertTrue(this.testExpert.isLiteral("melding()"));
		
		assertFalse(this.testExpert.isLiteral("melding"));
		
		assertTrue(this.testExpert.isLiteral("123"));
		
		assertFalse(this.testExpert.isLiteral("a123"));
		
		assertTrue(this.testExpert.isLiteral("--"));
		
		assertFalse(this.testExpert.isLiteral("counter"));
		
		assertTrue(this.testExpert.isLiteral(null));
		
		assertTrue(this.testExpert.isLiteral(""));

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
		
		String[] actualIn = this.testExpert.getInAnnotationsForMethod(method);
		
		
		
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
			
			String actualOut = this.testExpert.getOutAnnotationForMethod(method);
			
			assertEquals(expectedOut, actualOut);
	}
	
	@Test
	public void testGenerateConstructorForClass() {
		Class c = OnderhoudenMeldingServiceImpl.class;
		
		
		String expected = "new OnderhoudenMeldingServiceImpl()";
		
		assertEquals(expected, this.testExpert.generateConstructorForClass(c));
		
		c = Persoon.class;
		
	    expected = "new Persoon(17, new String())";
	    
	    assertEquals(expected, this.testExpert.generateConstructorForClass(c));
		
	}
	
	
	@Test
	public void testGetMethodsWithAnnotationCreateUnittest() {
		List<Method> methods = TestExpert.getMethodsWithAnnotationTestMe(TestClassInner.class);
		
		assertTrue(methods.size() == 4);
	}
	
	
	@Test
	public void testGenerateSomethingForInterface() {
		
		Class<?> c = List.class;
		
		String expected = "EasyMock.createMock(List.class)";
		
		assertEquals(expected, this.testExpert.generateSomethingForInterface(c));
		
		c = Set.class;
		
		expected  = "EasyMock.createMock(Set.class)";
		
		assertEquals(expected, this.testExpert.generateSomethingForInterface(c));
		
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
		
		String[] actualParameterNames = this.testExpert.getParameterNamesForMethod(m);
		
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
		
		String[] actualParameterNames = this.testExpert.getParameterNamesForMethod(m);
		
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
		
		String[] actualParameterNames = this.testExpert.getParameterNamesForMethod(m);
		
		assertEquals(Arrays.asList(expectedParameterNames), Arrays.asList(actualParameterNames));
		
		
		
	}
	
	@Test
	public void testGetPrimitiveType() {
		// Class<?> getPrimitiveType(String baseType) {
		assertEquals(byte.class, this.testExpert.getPrimitiveType("byte"));
		assertEquals(short.class, this.testExpert.getPrimitiveType("short"));
		assertEquals(int.class, this.testExpert.getPrimitiveType("int"));
		assertEquals(long.class, this.testExpert.getPrimitiveType("long"));
		assertEquals(float.class, this.testExpert.getPrimitiveType("float"));
		assertEquals(double.class, this.testExpert.getPrimitiveType("double"));
		assertEquals(char.class, this.testExpert.getPrimitiveType("char"));
		assertEquals(boolean.class, this.testExpert.getPrimitiveType("boolean"));
		assertEquals(void.class, this.testExpert.getPrimitiveType("void"));
		
		try {
			this.testExpert.getPrimitiveType("invalid argument");
			fail("should never come here since the argument is not a valid Java type ...");
		}
		catch (Exception e) {
			// normal condition
		}
		
	}
	
	@Test
	public void testGetParameterNamesForMethod() {
		Method method = null;
		Assert.assertEquals(new String[]{},this.testExpert.getParameterNamesForMethod(method));
		try {
			method = TestClassInner.class.getMethod("testForParameterNames", new Class<?>[]{String.class, String.class, String.class});
		} catch (SecurityException e) {
			fail();
		} catch (NoSuchMethodException e) {
			fail();
		}
		
		Assert.assertTrue(method != null);
		
		String [] parameterNames = this.testExpert.getParameterNamesForMethod(method);
		
		Assert.assertTrue(parameterNames != null);
		Assert.assertEquals(3, parameterNames.length);
		Assert.assertEquals("one", parameterNames[0]);
		Assert.assertEquals("two", parameterNames[1]);
		Assert.assertEquals("three", parameterNames[2]);
	}

	@Test
	public void testGetParameterTypesForMethod() {
		Method method = null;
		Assert.assertEquals(new String[]{},this.testExpert.getParameterTypesForMethod(method));
		try {
			method = TestClassInner.class.getMethod("testForParameterTypes", new Class<?>[]{int.class, String.class, Person.class});
			
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
		
		Assert.assertNotNull(method);
		
		String[] parameterTypes = this.testExpert.getParameterTypesForMethod(method);
		
		Assert.assertTrue(parameterTypes != null);
		Assert.assertEquals(3, parameterTypes.length);
		Assert.assertEquals("int", parameterTypes[0]);
		Assert.assertEquals("String", parameterTypes[1]);
		Assert.assertEquals("Person", parameterTypes[2]);
		
	}
	
	@Test
	public void testGetParameterTypeAndNameForMethod() {
		Method method = null;
		try {
			method = TestClassInner.class.getMethod("testForParameterTypesAndName", new Class<?>[]{int.class, String.class, Person.class});
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(method);
		
		String arguments = this.testExpert.getParameterTypesAndNameAsString(method);
		
		Assert.assertEquals("int one, String two, Person three", arguments);
	}
	
	@Test
	public void testIsPrimitive(String type) {
		Assert.assertTrue(this.testExpert.isPrimitive("byte"));
		Assert.assertTrue(this.testExpert.isPrimitive("short"));
		Assert.assertTrue(this.testExpert.isPrimitive("int"));
		Assert.assertTrue(this.testExpert.isPrimitive("long"));
		Assert.assertTrue(this.testExpert.isPrimitive("float"));
		Assert.assertTrue(this.testExpert.isPrimitive("double"));
		Assert.assertTrue(this.testExpert.isPrimitive("char"));
		Assert.assertTrue(this.testExpert.isPrimitive("boolean"));
		Assert.assertTrue(this.testExpert.isPrimitive("void"));
	}
	
}