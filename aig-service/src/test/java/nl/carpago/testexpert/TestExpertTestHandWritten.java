package nl.carpago.testexpert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.belastingdienst.aig.betrokkene.Betrokkene;
import nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FixturesForTest.class })
public class TestExpertTestHandWritten extends AbstractTestExpert {

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
		this.testExpert = new TestExpert(OnderhoudenMeldingServiceImpl.class, FixturesForTest.class);
	}

	@Test
	public void testConstructor() {
		TestExpert testExpertLocal = new TestExpert(AClassUnderTest.class, FixturesForTest.class);

		Assert.assertEquals(AClassUnderTest.class, testExpertLocal.getClassUnderTest());
		Assert.assertEquals(FixturesForTest.class, testExpertLocal.getContextClass());
		Assert.assertEquals(AClassUnderTest.class.getPackage(), testExpertLocal.getPakkage());

		ApplicationContext context = testExpertLocal.getCtx();
		Assert.assertNotNull(context);

		Person personFromContext = (Person) context.getBean("person");
		Assert.assertNotNull(personFromContext);

		Assert.assertEquals("John Doe", personFromContext.getName());

		// should test initalizeTestClass but we do it outside this block.

	}

	@Test
	public void testIsLiteral() {

		assertTrue(this.testExpert.isLiteral("melding()"));

		assertFalse(this.testExpert.isLiteral("melding"));

		assertTrue(this.testExpert.isLiteral("123"));

		assertFalse(this.testExpert.isLiteral("a123"));

		assertTrue(this.testExpert.isLiteral("--"));

		assertFalse(this.testExpert.isLiteral("counter"));

		assertTrue(this.testExpert.isLiteral(null));

		assertTrue(this.testExpert.isLiteral(""));

		assertTrue(this.testExpert.isLiteral("true"));

		assertTrue(this.testExpert.isLiteral("false"));

	}

	@Test
	public void testGetInAnnotationsForMethod() {
		Method method = null;
		try {
			method = OnderhoudenMeldingServiceImpl.class.getMethod("geefMelding", Betrokkene.class, String.class);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}

		String[] expectedIn = { "andereBetrokkene", "anderBerichtkenmerkAig" };

		String[] actualIn = this.testExpert.getInAnnotationsForMethod(method);

		assertEquals(Arrays.asList(actualIn), Arrays.asList(expectedIn));

		try {
			method = TestClassInner.class.getMethod("test4", new Class<?>[] { int.class });
			expectedIn = new String[] { "3" };
			actualIn = this.testExpert.getInAnnotationsForMethod(method);
			assertEquals(Arrays.asList(expectedIn), Arrays.asList(actualIn));
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetOutAnnotationForMethod() {
		Method method = null;
		try {
			method = OnderhoudenMeldingServiceImpl.class.getMethod("geefMelding", Betrokkene.class, String.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String expectedOut = "melding";

		String actualOut = this.testExpert.getOutAnnotationForMethod(method);

		assertEquals(expectedOut, actualOut);

		try {
			method = TestClassInner.class.getMethod("test4", new Class<?>[] { int.class });
			expectedOut = "5";
			actualOut = this.testExpert.getOutAnnotationForMethod(method);
			assertEquals(expectedOut, actualOut);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGenerateConstructorForClass() {
		Class c = OnderhoudenMeldingServiceImpl.class;

		String expected = "new OnderhoudenMeldingServiceImpl()";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = Persoon.class;

		expected = "new Persoon(17, new String())";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = byte.class;

		expected = "(byte) 15";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = short.class;

		expected = "(short) 1";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = long.class;

		expected = "18L";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = float.class;

		expected = "19.5F";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = double.class;

		expected = "20.5";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = char.class;

		expected = "'a'";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		c = boolean.class;

		expected = "true";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));

		int[] array = new int[] { 1, 2, 3 };

		c = array.getClass();

		expected = "new int[]{1,2,3}";

		assertEquals(expected, testExpert.generateConstructorForClass(c));

		c = Set.class;

		expected = "EasyMock.createMock(Set.class)";

		assertEquals(expected, this.testExpert.generateConstructorForClass(c));
	}

	@Test
	public void testGenerateSomethingForInterface() {

		Class<?> c = List.class;

		String expected = "EasyMock.createMock(List.class)";

		assertEquals(expected, this.testExpert.generateSomethingForInterface(c));

		c = Set.class;

		expected = "EasyMock.createMock(Set.class)";

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

		String[] expectedParameterNames = { "int1", "int2" };

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

		String[] expectedParameterNames = { "first", "second" };

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

		String[] expectedParameterNames = { "string1", "string2" };

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
		} catch (Exception e) {
			// normal condition
		}

	}

	@Test
	public void testGetParameterNamesForMethod() {
		Method method = null;
		Assert.assertEquals(new String[] {}, this.testExpert.getParameterNamesForMethod(method));
		try {
			method = TestClassInner.class.getMethod("testForParameterNames", new Class<?>[] { String.class, String.class, String.class });
		} catch (SecurityException e) {
			fail();
		} catch (NoSuchMethodException e) {
			fail();
		}

		Assert.assertTrue(method != null);

		String[] parameterNames = this.testExpert.getParameterNamesForMethod(method);

		Assert.assertTrue(parameterNames != null);
		Assert.assertEquals(3, parameterNames.length);
		Assert.assertEquals("one", parameterNames[0]);
		Assert.assertEquals("two", parameterNames[1]);
		Assert.assertEquals("three", parameterNames[2]);
	}

	@Test
	public void testGetParameterTypesForMethod() {
		Method method = null;
		Assert.assertEquals(new String[] {}, this.testExpert.getParameterTypesForMethod(method));
		try {
			method = TestClassInner.class.getMethod("testForParameterTypes", new Class<?>[] { int.class, String.class, Person.class });

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
			method = TestClassInner.class.getMethod("testForParameterTypesAndName", new Class<?>[] { int.class, String.class, Person.class });
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

	@Test
	public void testFindAllJavaFilesForFolder() {
		try {
			List<String> files = TestExpert.findAllJavaFiles("./src/test/java");
			Assert.assertEquals(8, files.size());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGenerateHeader() {
		TestExpert t = new TestExpert(AClassUnderTest.class, FixturesForTest.class);
		t.generateHeader();
		String header = t.getHeader();
		String expected = "public class AClassUnderTestTest extends AbstractTestExpert {\n";
		assertEquals(expected, header);
	}

	@Test
	public void testGenerateFooter() {
		TestExpert t = new TestExpert(AClassUnderTest.class, FixturesForTest.class);
		t.generateFooter();
		String footer = t.getFooter();
		String expected = "}";
		Assert.assertEquals(expected, footer);
	}

	@Test
	public void testGenerateAnnotationsForSpringTest() {
		/*
		 * 
		 * logger.debug("enter");
		 * 
		 * this.checkAndAddImport(org.junit.runner.RunWith.class);
		 * this.checkAndAddImport
		 * (org.springframework.test.context.junit4.SpringJUnit4ClassRunner
		 * .class); this.checkAndAddImport(org.springframework.test.context.
		 * ContextConfiguration.class);
		 * this.checkAndAddImport(org.springframework
		 * .beans.factory.annotation.Autowired.class); // ???? fixtures ???
		 * wordt toch geinsert door klant ???? // this.addImport(classToImport)
		 * 
		 * this.annotionsBeforeTestClass.add(
		 * "@RunWith(SpringJUnit4ClassRunner.class)");
		 * this.annotionsBeforeTestClass
		 * .add("@ContextConfiguration(classes={Fixtures.class})");
		 */

		TestExpert t = new TestExpert(AClassUnderTest.class, FixturesForTest.class);

		t.generateAnnotationsForSpringTest();
		List<String> annotations = t.getAnnotionsBeforeTestClass();

		Assert.assertEquals(2, annotations.size());
		Assert.assertTrue(annotations.contains("@RunWith(SpringJUnit4ClassRunner.class)"));
		Assert.assertTrue(annotations.contains("@ContextConfiguration(classes={FixturesForTest.class})"));
	}

	@Test
	/**
	 * this.checkAndAddImport(org.junit.Before.class);
		this.checkAndAddImport(org.junit.Test.class);
		this.checkAndAddImport(nl.carpago.testexpert.AbstractTestExpert.class);
		this.checkAndAddImport(this.contextClass);
		this.checkAndAddImport(org.junit.runner.RunWith.class);
		this.checkAndAddImport(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class);
		this.checkAndAddImport(org.springframework.test.context.ContextConfiguration.class);
		this.checkAndAddImport(org.springframework.beans.factory.annotation.Autowired.class);
	 */
	public void testInitalizeTestFramework() {
		// the work should already be done by the constructor

		// same package
		TestExpert t = new TestExpert(AClassUnderTest.class, FixturesForTest.class);

		Set<String> imports = t.getImports();
		Assert.assertEquals(6, imports.size());

		Assert.assertTrue(imports.contains("org.junit.Before"));
		Assert.assertTrue(imports.contains("org.junit.Test"));
		// Assert.assertTrue(imports.contains("nl.carpago.testexpert.AbstractTestExpert"));
		// Assert.assertTrue(imports.contains(t.getContextClass().getSimpleName()));
		Assert.assertTrue(imports.contains("org.junit.runner.RunWith"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.ContextConfiguration"));
		Assert.assertTrue(imports.contains("org.springframework.beans.factory.annotation.Autowired"));

		// other package
		t = new TestExpert(String.class, FixturesForTest.class);
		imports = t.getImports();
		Assert.assertEquals(8, imports.size());
		Assert.assertTrue(imports.contains("org.junit.Before"));
		Assert.assertTrue(imports.contains("org.junit.Test"));
		Assert.assertTrue(imports.contains("nl.carpago.testexpert.AbstractTestExpert"));
		Assert.assertTrue(imports.contains(t.getContextClass().getName()));
		Assert.assertTrue(imports.contains("org.junit.runner.RunWith"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.ContextConfiguration"));
		Assert.assertTrue(imports.contains("org.springframework.beans.factory.annotation.Autowired"));

	}

	@Test
	public void testGenerateFixturesForMethod() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TestClassInner.class.getMethod("testForGenerateFixturesForMethod", new Class<?>[] { Person.class }));
			Assert.assertEquals(2, fixtures.size());
			Assert.assertTrue(fixtures.contains(Person.class));
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

		Map<String, Class<?>> fixturesFromTestExpert = t.getFixtures();

		Assert.assertTrue(fixturesFromTestExpert.containsKey("person"));
		Assert.assertTrue(fixturesFromTestExpert.containsKey("anotherPerson"));
	}

	@Test
	public void testGenerateFixturesForMethodWithLiteral() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TestClassInner.class.getMethod("testIn", new Class<?>[] { int.class }));
			Assert.assertEquals(0, fixtures.size());
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

		Map<String, Class<?>> fixturesFromTestExpert = t.getFixtures();

		Assert.assertFalse(fixturesFromTestExpert.containsKey("3"));
	}

	@Test
	public void testCodegenFixtures() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TestClassInner.class.getMethod("testForGenerateFixturesForMethod", new Class<?>[] { Person.class }));
			Assert.assertEquals(2, fixtures.size());
			Assert.assertTrue(fixtures.contains(Person.class));

			String actual = t.codeGenFixtures();

			String expected = "\t@Autowired private Person person;";
			Assert.assertTrue("@Autowired private Person person not found in fixtureCodeGen", actual.indexOf(expected) > -1);

			expected = "\t@Autowired private Person anotherPerson;";
			Assert.assertTrue("@Autowired private Person anotherPerson not found in fixtureCodeGen", actual.indexOf(expected) > -1);

			String[] actualSplitted = actual.split("\n");
			Assert.assertEquals(4, actualSplitted.length); // 4 --> emptyline
															// followed by
															// header
															// (//fixtures)
															// followed by two
															// @Autowired
															// statements.

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

		Map<String, Class<?>> fixturesFromTestExpert = t.getFixtures();

		Assert.assertTrue(fixturesFromTestExpert.containsKey("person"));
		Assert.assertTrue(fixturesFromTestExpert.containsKey("anotherPerson"));
	}

	@Test
	public void testGenerateCreateArgumentsForTestMethod() {

		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);
		try {
			Method method = TestClassInner.class.getMethod("methodForCreateArguments", new Class<?>[] { int.class, String.class, Person.class });
			String code = t.generateCreateArgumentsForTestMethod(method);
			String expected = "" + "int firstUnknowArgument = 17;" + "String secondUnknowArgument = new String();" + "Person thirdUnknowArgument = new Person(new String(), 17);";
			String[] codeSplitted = code.split("\n");
			for (String line : codeSplitted) {
				Assert.assertTrue(expected.indexOf(line.trim()) > -1);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
		try {
			Method method = TestClassInner.class.getMethod("methodForCreateArgumentsError", new Class<?>[] { int.class, String.class, Person.class });
			try {
				t.generateCreateArgumentsForTestMethod(method);
				fail();
			} catch (RuntimeException rte) {

			}

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testIsCallerForCollab() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		}

		Assert.assertTrue("lijst should be a collab InnerclassTest", t.isCallerForCollab("lijst"));
		Assert.assertFalse("klasdfjalk;sdfjkads should not be a ..", t.isCallerForCollab("alksdfjkasdf"));
	}

	@Test
	public void testGenerateReplays() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		}

		// ??? why nog?Assert.assertEquals("The generated replay code is wrong",
		// "EasyMock.replay(list);", t.generateReplays());
		Assert.assertTrue(t.generateReplays().indexOf("EasyMock.replay(lijst);") > -1);
	}

	@Test
	public void testGenerateVerifies() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		}

		// ??? why nog?Assert.assertEquals("The generated replay code is wrong",
		// "EasyMock.replay(list);", t.generateReplays());
		Assert.assertTrue(t.generateVerifies().indexOf("EasyMock.verify(lijst);") > -1);
	}

	@Test
	public void testGenerateGettersForCollaborators() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		}

		String collabString = t.generateGettersForCollaborators();
		Assert.assertTrue(collabString.indexOf("public List<String> getLijst()") > -1);
		Assert.assertTrue(collabString.indexOf("return this.lijst;") > -1);
	}

	@Test
	public void testGenerateCallToTestMethod() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);

		try {
			Method method = TestClassInner.class.getMethod("testMethodeForCreateCallToTestMethod", new Class<?>[] { Person.class, Person.class });
			String callTotTestMethod = t.generateCallToTestMethod(method);
			String expected = "Person resultFromMethod = testClassInner.testMethodeForCreateCallToTestMethod(person, anotherPerson);";
			Assert.assertTrue(callTotTestMethod.indexOf(expected) > -1);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateAssertStatementsForMethod() {
		TestExpert t = new TestExpert(TestClassInner.class, FixturesForTest.class);

		try {
			Method method = TestClassInner.class.getMethod("testMethodeForCreateCallToTestMethod", new Class<?>[] { Person.class, Person.class });
			String assertStatement = t.generateAssertStatements(method);
			System.out.println(assertStatement);
			String expected = "assertTrue(\"variable 'anotherPerson' and 'resultFromMethod' should be deep equal!\", this.checkForDeepEquality(anotherPerson, resultFromMethod));";
			Assert.assertTrue(assertStatement.indexOf(expected) > -1);
			
			method = TestClassInner.class.getMethod("testMethodeForCreateCallToTestMethod", new Class<?>[] { int.class });
			assertStatement = t.generateAssertStatements(method);
			System.out.println(assertStatement);
			expected = "assertTrue(\"variable 'anotherPerson' and 'resultFromMethod' should be equal!\", 4 == resultFromMethod));";
			Assert.assertTrue(assertStatement.indexOf(expected) > -1);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}

	}
}