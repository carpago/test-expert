package nl.carpago.testexpert;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.carpago.testexpert.TestExpert.MockFramework;
import nl.foo.AccidentalPerson;
import nl.foo.Announcement;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FixturesForTst.class })
public class TestExpertTest extends AbstractTestExpert {

	private static final String EMPTY_STRING = "";

	// class under test
	private TestExpert testExpert;

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.testExpert = null;
	}

	@Test
	public void testConstructor() {
		TestExpert testExpertLocal = new TestExpertImplForUnittestingPurposes();
		testExpertLocal.init(AClassUnderTst.class);

		Assert.assertEquals(AClassUnderTst.class, testExpertLocal.getClassUnderTest());
		Assert.assertEquals(FixturesForTst.class, testExpertLocal.getFixture());
		Assert.assertEquals(AClassUnderTst.class.getPackage(), testExpertLocal.getPakkage());

		ApplicationContext context = testExpertLocal.getCtx();
		Assert.assertNotNull(context);

		Person personFromContext = (Person) context.getBean("person");
		Assert.assertNotNull(personFromContext);

		Assert.assertEquals("John Doe", personFromContext.getName());

		// should test initalizeTestClass but we do it outside this block.

	}

	@Test
	public void testIsLiteral() {

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		assertTrue(this.testExpert.isLiteral("message()"));

		assertFalse(this.testExpert.isLiteral("message"));

		assertTrue(this.testExpert.isLiteral("123"));

		assertFalse(this.testExpert.isLiteral("a123"));

		assertTrue(this.testExpert.isLiteral("--"));

		assertFalse(this.testExpert.isLiteral("counter"));

		assertTrue(this.testExpert.isLiteral(null));

		assertTrue(this.testExpert.isLiteral(""));

		assertTrue(this.testExpert.isLiteral("true"));

		assertTrue(this.testExpert.isLiteral("false"));
		
		assertTrue(this.testExpert.isLiteral("John Doe"));

	}

	@Test
	public void testGenerateConstructorForClass() {

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Class<?> c = ManageMessageServiceImpl.class;

		String expected = "new ManageMessageServiceImpl()";

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

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Class<?> c = List.class;

		String expected = "EasyMock.createMock(List.class)";

		assertEquals(expected, this.testExpert.generateSomethingForInterface(c));

		c = Set.class;

		expected = "EasyMock.createMock(Set.class)";

		assertEquals(expected, this.testExpert.generateSomethingForInterface(c));

	}

	@Test
	public void testGetParameterNamesForMethodWithInterface() {

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

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

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Method m = null;
		try {
			m = TstClass.class.getMethod("add", int.class, int.class);
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

		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

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
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

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
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Method method = null;
		Assert.assertTrue(Arrays.equals(new String[] {}, this.testExpert.getParameterNamesForMethod(method)));
		try {
			method = TstClassInner.class.getMethod("testForParameterNames", new Class<?>[] { String.class, String.class, String.class });
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
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Method method = null;
		Assert.assertTrue(Arrays.equals(new String[] {}, this.testExpert.getParameterTypesForMethod(method)));
		try {
			method = TstClassInner.class.getMethod("testForParameterTypes", new Class<?>[] { int.class, String.class, Person.class });

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
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		Method method = null;
		try {
			method = TstClassInner.class.getMethod("testForParameterTypesAndName", new Class<?>[] { int.class, String.class, Person.class });
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
	public void testFindAllJavaFilesForFolder() {
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		try {
			List<String> files = this.testExpert.findAllJavaFiles("./src/test/java");
			String environment = this.testExpert.getBinaryFolder();
			int expected = 0;
			if("bin".equals(environment))
			{
				expected = 21;
			}
			else
			{
				expected = 24;
			}
			
			Assert.assertEquals("Expected:" + expected + ", actual:" + files.size(), expected, files.size());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGenerateHeader() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(AClassUnderTst.class);
		t.generateHeader();
		String header = t.getHeader();
		String expected = "public class AClassUnderTstTest extends AbstractTestExpert {\n";
		assertEquals(expected, header);
		Assert.assertEquals(expected, t.codeGenHeader());
	}

	@Test
	public void testGenerateFooter() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(AClassUnderTst.class);
		t.generateFooter();
		String footer = t.getFooter();
		String expected = "}";
		Assert.assertEquals(expected, footer);
		Assert.assertEquals(expected, t.codeGenFooter());
	}

	@Test
	public void testGenerateAnnotationsForSpringTest() {
		this.testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(ManageMessageServiceImpl.class);

		testExpert.generateAnnotationsForSpringTest();
		List<String> annotations = testExpert.getAnnotionsBeforeTestClass();

		Assert.assertEquals(2, annotations.size());
		Assert.assertTrue(annotations.contains("@RunWith(SpringJUnit4ClassRunner.class)"));
		Assert.assertTrue(annotations.contains("@ContextConfiguration(classes={FixturesForTst.class})"));

		String codeGenAnnotations = testExpert.codeGenAnnotationsForSpringTest();
		Assert.assertTrue(codeGenAnnotations.contains("@RunWith(SpringJUnit4ClassRunner.class)"));
		Assert.assertTrue(codeGenAnnotations.contains("@ContextConfiguration(classes={FixturesForTst.class})") );
	}

	@Test
	public void testInitalizeTestFramework() {
		// the work should already be done by the constructor

		// same package
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(AClassUnderTst.class);

		Set<String> imports = testExpert.getImports();
		Assert.assertEquals(5, imports.size());

		Assert.assertTrue(imports.contains("org.junit.Before"));
		Assert.assertTrue(imports.contains("org.junit.Test"));
		// not te be inserted:
		// Assert.assertTrue(imports.contains("nl.carpago.testexpert.AbstractTestExpert"));
		// not to be inserted:
		// Assert.assertTrue(imports.contains(t.getContextClass().getSimpleName()));
		Assert.assertTrue(imports.contains("org.junit.runner.RunWith"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.ContextConfiguration"));

		// test the code that eventually is generated.
		String code = testExpert.codeGenImports();
		String[] lines = code.split("\n");
		Assert.assertEquals(5, lines.length);
		Assert.assertTrue(code.contains("org.junit.Before;"));
		Assert.assertTrue(code.contains("org.junit.Test;"));
		Assert.assertTrue(code.contains("org.junit.runner.RunWith;"));
		Assert.assertTrue(code.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner;"));
		Assert.assertTrue(code.contains("org.springframework.test.context.ContextConfiguration;"));

		// other package
		testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(String.class);
		imports = testExpert.getImports();
		Assert.assertEquals(7, imports.size());
		Assert.assertTrue(imports.contains("org.junit.Before"));
		Assert.assertTrue(imports.contains("org.junit.Test"));
		Assert.assertTrue(imports.contains("nl.carpago.testexpert.AbstractTestExpert"));
		Assert.assertTrue(imports.contains(testExpert.getFixture().getName()));
		Assert.assertTrue(imports.contains("org.junit.runner.RunWith"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner"));
		Assert.assertTrue(imports.contains("org.springframework.test.context.ContextConfiguration"));
		// Assert.assertTrue(imports.contains("org.springframework.beans.factory.annotation.Autowired"));

		code = testExpert.codeGenImports();
		lines = code.split("\n");

		Assert.assertEquals(7, lines.length);
		Assert.assertTrue(code.contains("org.junit.Before;"));
		Assert.assertTrue(code.contains("org.junit.Test;"));
		Assert.assertTrue(code.contains("nl.carpago.testexpert.AbstractTestExpert"));
		Assert.assertTrue(code.contains(testExpert.getFixture().getSimpleName()));
		Assert.assertTrue(code.contains("org.junit.runner.RunWith;"));
		Assert.assertTrue(code.contains("org.springframework.test.context.junit4.SpringJUnit4ClassRunner;"));
		Assert.assertTrue(code.contains("org.springframework.test.context.ContextConfiguration;"));
	}

	@Test
	public void testGenerateFixturesForMethod() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TstClassInner.class.getMethod("testForGenerateFixturesForMethod", new Class<?>[] { Person.class }));
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
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TstClassInner.class.getMethod("testIn", new Class<?>[] { int.class }));
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
	public void testCodegenPackage() {
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(TstClassInner.class);

		String code = testExpert.codeGenPackage();

		Assert.assertTrue(code + " is not equal to 'nl.carpago.testexpert<crlf><crlf>'", "package nl.carpago.testexpert;\n\n".equals(code));
	}

	@Test
	public void testCodegenFixtures() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			List<Class<?>> fixtures = t.generateFixturesForMethod(TstClassInner.class.getMethod("testForGenerateFixturesForMethod", new Class<?>[] { Person.class }));
			Assert.assertEquals(2, fixtures.size());
			Assert.assertTrue(fixtures.contains(Person.class));

			String actual = t.codeGenFixtures();

			String expected = "\t@Autowired private Person person;";
			Assert.assertTrue("@Autowired private Person person not found in fixtureCodeGen", actual.contains(expected));

			expected = "\t@Autowired private Person anotherPerson;";
			Assert.assertTrue("@Autowired private Person anotherPerson not found in fixtureCodeGen", actual.contains(expected));

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

		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		try {
			Method method = TstClassInner.class.getMethod("methodForCreateArguments", new Class<?>[] { int.class, String.class, Person.class });
			String code;
			try {
				code = t.generateCreateArgumentsForTestMethod(method);
				String expected = "" + "int firstUnknowArgument = 17;" + "String secondUnknowArgument = new String();" + "Person thirdUnknowArgument = new Person(new String(), 17);";
				String[] codeSplitted = code.split("\n");
				for (String line : codeSplitted) {
					Assert.assertTrue(expected.contains(line.trim()));
				}
			} catch (InvalidAnnotationException e) {
				logger.error("Invalid annotations.");
			}

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
		try {
			Method method = TstClassInnerWithError.class.getMethod("methodForCreateArgumentsError", new Class<?>[] { int.class, String.class, Person.class });
			try {
				t.generateCreateArgumentsForTestMethod(method);
			} catch (InvalidAnnotationException e) {
				logger.error("in this case the error messsage is correct!");
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
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			// ok
		}
		Assert.assertTrue("lijst should be a collab InnerclassTest", t.isCallerForCollab("lijst"));
		Assert.assertFalse("klasdfjalk;sdfjkads should not be a ..", t.isCallerForCollab("alksdfjkasdf"));
	}

	@Test
	public void testGenerateReplays() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		// ??? why nog?Assert.assertEquals("The generated replay code is wrong",
		// "EasyMock.replay(list);", t.generateReplays());
		Assert.assertTrue(t.generateReplays("testForLijst").contains("EasyMock.replay(lijst);"));
	}

	@Test
	public void testGenerateVerifies() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		// ??? why nog?Assert.assertEquals("The generated replay code is wrong",
		// "EasyMock.replay(list);", t.generateReplays());
		Assert.assertTrue(t.generateVerifies("testForLijst").contains("EasyMock.verify(lijst);"));
	}

	@Test
	public void testGenerateGettersForCollaborators() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		try {
			t.generateTestClass();
		} catch (RuntimeException rte) {
			// ok for now since the generateCreateArgumentsFormethod should
			// throw since there a intentional error in this class...
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		String collabString = t.generateGettersForCollaborators();
		Assert.assertTrue(collabString.contains("public List<String> getLijst()"));
		Assert.assertTrue(collabString.contains("return this.lijst;"));
	}

	@Test
	public void testGenerateCallToTestMethod() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			Method method = TstClassInner.class.getMethod("testMethodeForCreateCallToTestMethod", new Class<?>[] { Person.class, Person.class });
			String callTotTestMethod = t.generateCallToTestMethod(method);
			String expected = "Person resultFromMethod = tstClassInner.testMethodeForCreateCallToTestMethod(person, anotherPerson);";
			Assert.assertTrue(callTotTestMethod.contains(expected));
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGenerateCallToTestMethodWithParameterString() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			Method method = TstClassInner.class.getMethod("testForParameterNamesLiterals", new Class<?>[] { String.class, String.class, String.class });
			String callToTestMethod = t.generateCallToTestMethod(method);
			String expected = "String resultFromMethod = tstClassInner.testForParameterNamesLiterals(\"erwaseeneen\", \"erwaseentwee\", \"erwaseendrie\");";
			Assert.assertTrue(callToTestMethod.contains(expected));
		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testgenerateAssertStatementsForReturnOfMethod() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			Method method = TstClassInner.class.getMethod("testMethodeForCreateCallToTestMethod", new Class<?>[] { Person.class, Person.class });
			String assertStatement = t.generateAssertStatementsForReturnOfMethod(method);
			String expected = "assertTrue(\"variable 'anotherPerson' and 'resultFromMethod' should be deep equal!\", this.checkForDeepEquality(anotherPerson, resultFromMethod));";
			Assert.assertTrue(assertStatement.contains(expected));

			method = TstClassInner.class.getMethod("inc", new Class<?>[] { int.class });
			assertStatement = t.generateAssertStatementsForReturnOfMethod(method);
			expected = "assertTrue(\"variable '4' and 'resultFromMethod' should be deep equal!\", this.checkForDeepEquality(\"4\", resultFromMethod));";
			Assert.assertTrue(assertStatement.contains(expected));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
			e.printStackTrace();
		}

	}

	@Test
	public void testCheckAndImport() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		int currentSize = t.getImports().size();

		t.checkAndAddImport(int.class);
		t.checkAndAddImport(java.lang.String.class);

		int newSize = t.getImports().size();

		Assert.assertTrue(currentSize == newSize);

		Person one = new Person("John Doe", 45);

		Person[] people = new Person[3];
		people[0] = one;

		t.checkAndAddImport(people);

		Assert.assertFalse("Imports shouldn't contain " + one.getClass().getName(), t.getImports().contains(one.getClass().getName()));

		Announcement aMelding = new Announcement();
		Announcement[] messageen = new Announcement[] { aMelding };
		t.checkAndAddImport(messageen);
		Assert.assertTrue("Imports should contain " + aMelding.getClass().getName(), t.getImports().contains(aMelding.getClass().getName()));

		AccidentalPerson accidentalPerson = new AccidentalPerson();
		List<AccidentalPerson> accidentalPersonn = new ArrayList<AccidentalPerson>();
		accidentalPersonn.add(accidentalPerson);
		t.checkAndAddImport(accidentalPersonn);
		Assert.assertTrue("Imports should contain " + accidentalPerson.getClass().getName(), t.getImports().contains(accidentalPerson.getClass().getName()));

		Assert.assertTrue(t.getImports().contains(java.util.ArrayList.class.getName()));

		Set<Announcement> messageenSet = new HashSet<Announcement>();
		messageenSet.add(aMelding);
		t.checkAndAddImport(messageenSet);
		Assert.assertTrue(t.getImports().contains(java.util.HashSet.class.getName()));

		t.init(TstClassInner.class);
		Assert.assertFalse(t.getImports().contains(java.util.HashMap.class.getName()));
		Assert.assertFalse(t.getImports().contains(Announcement.class.getName()));
		Map<String, Announcement> map = new HashMap<String, Announcement>();
		map.put("aKey", aMelding);
		t.checkAndAddImport(map);
		Assert.assertTrue(t.getImports().contains(Announcement.class.getName()));
		Assert.assertTrue(t.getImports().contains(HashMap.class.getName()));

		t.init(TstClassInner.class);
		int aNumber = 3;
		t.checkAndAddImport(aNumber);
		Assert.assertFalse(t.getImports().contains(Integer.class.getName()));
		Assert.assertFalse(t.getImports().contains(int.class.getName()));
	}

	@Test
	public void testGenerateSetup() throws InvalidAnnotationException {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		t.generateTestClass();

		String setup = t.generateSetup();
		String anExpectedLine = "@Before";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "this.tstClassInner = new TstClassInner();";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "this.lijst = EasyMock.createMock(List.class);";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "setFieldThroughReflection(tstClassInner, \"lijst\", this.lijst);";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "this.persoonDao = EasyMock.createMock(PersonDAO.class);";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "setFieldThroughReflection(tstClassInner, \"onceUsedPersoonDaoWithoutSetter\", this.onceUsedPersoonDaoWithoutSetter);";
		Assert.assertTrue(setup.contains(anExpectedLine));
		anExpectedLine = "this.tstClassInner.setPersoonDao(this.persoonDao);";
		Assert.assertTrue(setup.contains(anExpectedLine));
	}

	@Test
	public void testGetInAnnotationsForMethodForAnnotationCreateUnitTest() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testMethodeForGetInAndOutForCreateUnitTest", new Class<?>[] { int.class, int.class });
			String[] ins = t.getInAnnotationsForMethod(method);

			Assert.assertFalse(ins.length == 0);
			Assert.assertTrue("two".equals(ins[0]));
			Assert.assertTrue("three".equals(ins[1]));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
		}
	}
	
	@Test
	public void testFortestMethodeConcatStringForCreateUnittestForInAnnation() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testMethodeConcatStringForCreateUnittest", new Class<?>[] { String.class, String.class });
			String[] ins = t.getInAnnotationsForMethod(method);

			Assert.assertTrue(ins.length == 2);
			Assert.assertTrue("\"3\"".equals(ins[0]));
			Assert.assertTrue("\"4\"".equals(ins[1]));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
		}
	}
	
	@Test
	public void testFortestMethodeConcatReturnFirstChar() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testReturnFirstChar", new Class<?>[] { char.class, char.class });
			String[] ins = t.getInAnnotationsForMethod(method);

			Assert.assertTrue(ins.length == 2);
			Assert.assertTrue("'a'".equals(ins[0]));
			Assert.assertTrue("'b'".equals(ins[1]));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			fail();
		}
	}

	@Test
	public void testGetOutAnnotationsForMethodForCreateUnitTest1() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testMethodeForGetInAndOutForCreateUnitTest", new Class<?>[] { int.class, int.class });
			String out = t.getOutAnnotationForMethod(method);

			Assert.assertFalse(out == null);
			Assert.assertTrue("vier".equals(out));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGetOutAnnotationsForMethodForCreateUnitTest2() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("getLiteral");
			String out = t.getOutAnnotationForMethod(method);

			Assert.assertFalse(out == null);
			Assert.assertTrue("\"<literal>\"".equals(out));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetOutAnnotationsForMethodForExpect() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testMethodeForGetInAndOutForExpect", new Class<?>[] { int.class, int.class });
			String out = t.getOutAnnotationForMethod(method);

			Assert.assertFalse(out == null);
			Assert.assertTrue("vier".equals(out));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testGetInAnnotationsForMethodForAnnotationExpect() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		Method method;
		try {
			method = TstClassInner.class.getMethod("testMethodeForGetInAndOutForExpect", new Class<?>[] { int.class, int.class });
			String[] ins = t.getInAnnotationsForMethod(method);

			Assert.assertFalse(ins.length == 0);
			Assert.assertTrue("two".equals(ins[0]));
			Assert.assertTrue("three".equals(ins[1]));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testGenerateExpectAndReplayForCollaboratorsOfMethod() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("getNumber", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(persoonDao.getSofi(number)).andReturn((String) this.cloneMe(string));"));
				method = TstClassInner.class.getMethod("getPersoon", new Class<?>[] { int.class });
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(persoonDao.getPersoon(number)).andReturn((Persoon) this.cloneMe(eenAnderPersoon));"));

				method = TstClassInner.class.getMethod("testWithMoreThanOneArgument", new Class<?>[] { int.class, Person.class });
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(persoonDao.getPerson(number, person)).andReturn((Person) this.cloneMe(anotherPerson));"));

				method = TstClassInner.class.getMethod("testWithCallToSelf", new Class<?>[] { int.class });
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				// System.out.println(">"+expectAndReplays+"<");
				// rloman: to be implemented in the next release.
				// .Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(inner.inc(number)).andReturn(4);")
				// >-1);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateExpectAndReplayForCollaboratorsOfMethodWithALocalVar() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("testWithLocalVariable", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				Assert.assertTrue(expectAndReplays.contains("Person localPerson = new Person(new String(), 17);"));
				Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(persoonDao.getPersonWithoutHelp(number, localPerson)).andReturn(new Person(new String(), 17));"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateExpectAndReplayForCollaboratorsOfMethodWithALocalVarWithHelp() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("testWithLocalVariableWithHelp", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);
				Assert.assertTrue(expectAndReplays.contains("EasyMock.expect(persoonDao.getPerson(number, person)).andReturn((Person) this.cloneMe(anotherPerson));"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateExpectAndReplayForMockit() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.MOCKIT);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("getNumber", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);

				String[] expectedLines = new String[6];
				expectedLines[0] = "new Expectations() {";
				expectedLines[1] = "persoonDao.getSofi(number);";
				expectedLines[2] = "forEachInvocation = new Object() {";
				expectedLines[3] = "@SuppressWarnings(\"unused\")";
				expectedLines[4] = "String validate(int number){";
				expectedLines[5] = "return string;";

				for (String anExpectedLine : expectedLines) {
					Assert.assertTrue("Line:" + anExpectedLine + " not found!", expectAndReplays.contains(anExpectedLine));
				}

				// asssert that mockit is imported
				Assert.assertTrue(t.getImports().contains("mockit.Mocked"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testWithQuestionMarkOrAsteriskInAnnotation() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("testHelperMethodForQuestionmark", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method);

				String[] expectedLines = new String[2];
				expectedLines[0] = "int aNumber = 17;";
				expectedLines[1] = "EasyMock.expect(persoonDao.getPersonWithQuestionmarksAnnotation(aNumber)).andReturn(4);";

				for (String anExpectedLine : expectedLines) {
					Assert.assertTrue("Line:" + anExpectedLine + " not found!", expectAndReplays.contains(anExpectedLine));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMethodWithVoidReturn() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("helperVoidMethod", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method).trim();
				String expected = "persoonDao.voidmethod(number);";
				Assert.assertEquals(expected, expectAndReplays);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This methods tests - and tests only - if a non-literal return from a
	 * collab is correctly used.
	 */
	@Test
	public void testCallToCollabWithReturn() {
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		t.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			t.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		Method method;
		try {
			method = TstClassInner.class.getMethod("addMe", new Class<?>[] { int.class });
			String expectAndReplays;
			try {
				expectAndReplays = t.generateExpectForCollaboratorsOfMethod(method).trim();
				String expected = "EasyMock.expect(persoonDao.inc(number)).andReturn(number);";
				Assert.assertEquals(expected, expectAndReplays);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAllGeneratedCodeThroughInputstream() {
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(TstClassInner.class);
		testExpert.setCurrentFramework(MockFramework.EASYMOCK);
		try {
			testExpert.generateTestClass();
		} catch (InvalidAnnotationException e) {
			logger.error("Annotation and Parameters are ordinal not equal!");
		}

		String allCode = testExpert.codeGen();

		BufferedInputStream in = testExpert.getInputStreamFromGeneratedCode();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line = null;
		int counter = 0;
		try {
			while ((line = reader.readLine()) != null) {
				counter++;
				Assert.assertTrue(allCode.contains(line));
			}
			Assert.assertTrue(counter > 25);
			Assert.assertTrue(allCode.split("\n").length == counter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddTestToTestsuiteAndTestCodegenTestsuite() {
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		testExpert.init(TstClassInner.class);
		testExpert.setCurrentFramework(MockFramework.EASYMOCK);
		final String testClassName = "nl.carpago.testexpert.OurTest.class";
		final String anotherTestClassName = "nl.carpago.testexpert.Another.class";

		final List<String> mockTestsuite = new ArrayList<String>();
		mockTestsuite.add(testClassName);
		mockTestsuite.add(anotherTestClassName);

		testExpert.addTestToTestsuite(testClassName);
		testExpert.addTestToTestsuite(anotherTestClassName);

		List<String> result = testExpert.getAllTests();

		Assert.assertTrue("Alltest does not contain the just added test:" + testClassName, result.contains(testClassName));
		Assert.assertTrue("Alltest does not contain the just added test:" + anotherTestClassName, result.contains(anotherTestClassName));

		String codeGenExpected = EMPTY_STRING;
		codeGenExpected += "import junit.framework.JUnit4TestAdapter;\n";
		codeGenExpected += "import junit.framework.TestCase;\n" + "import junit.framework.TestSuite;\n";
		codeGenExpected += "\n";
		codeGenExpected += "import org.junit.runner.RunWith;\n" + "import org.junit.runners.AllTests;\n";

		codeGenExpected += "\n";

		codeGenExpected += "@RunWith(AllTests.class)\n";
		codeGenExpected += "public final class " + testExpert.getTestsuiteName() + " extends TestCase {\n";
		codeGenExpected += "\n";

		codeGenExpected += "\tpublic static TestSuite suite() {\n";
		codeGenExpected += "\t\tTestSuite suite = new TestSuite();\n";
		codeGenExpected += "\n";

		for (String testClassNameLocal : mockTestsuite) {
			codeGenExpected += "\t\tsuite.addTest(new JUnit4TestAdapter(";
			codeGenExpected += testClassNameLocal;
			codeGenExpected += "));\n";
		}

		codeGenExpected += "\n";

		codeGenExpected += "\t\treturn suite;\n";

		codeGenExpected += "\t}\n";
		codeGenExpected += "}";

		String codeGenActual = testExpert.codeGenTestsuite();

		for (String line : codeGenExpected.split("\n")) {
			Assert.assertTrue("Line '" + line + "' is not found in generated code!", codeGenActual.contains(line));
		}
	}

	@Test
	public void testWriteFileForFileDirContent() {
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		final String fileName = "writefile-unittest.txt";
		final String directoryName = "src/test/java/nl/carpago/testexpert";
		final String content = "This should be in the file " + new Date();
		testExpert.writeFile(fileName, directoryName, content);

		File testFile = new File("src/test/java/nl/carpago/testexpert/writefile-unittest.txt");
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(testFile);
			InputStreamReader inputstreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputstreamReader);
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					if (content.equals(line)) {
						reader.close();
						return;
					}
				}
			} catch (IOException e) {
				fail(e.getMessage());
			}
			fail("Testline of file not found back in file.");
		} catch (FileNotFoundException e) {
			fail("There was an error with reading the testfile back.");
		}
	}
	
	@Test
	public void testWriteFileWrongCase() {
		TestExpert testExpert = new TestExpertImplForUnittestingPurposes();
		final String fileName = "1-2-3-/invalidfilename-writefile-unittest.txt";
		final String directoryName = "src/test/java/nl/carpago/testexpert";
		final String content = "This should be in the file " + new Date();

		try {
			testExpert.writeFile(fileName, directoryName, content);
			Assert.fail("Exception should have been thrown by the callee");
		}
		catch (TestExpertException tee)
		{
			//ok
		}
	}
	
	@Test
	public void testGenerateAssertStatementsForMethodWithEqualityOperator()
	{
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			Method method = TstClassInner.class.getMethod("setLeeftijd", new Class<?>[] { int.class });
			String assertStatement = t.generateAssertStatementsForMethod(method);

			String expected = "Object leeftijd= getFieldvalueThroughReflection(tstClassInner,\"leeftijd\");";
			Assert.assertTrue(assertStatement.contains(expected));
			
			expected = "assertTrue(\"variable 'leeftijd' and '3' should be deep equal!\",checkForDeepEquality(leeftijd,\"3\"));";
			Assert.assertTrue(assertStatement.contains(expected));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGenerateAssertStatementsForMethodWithEquals()
	{
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);

		try {
			Method method = TstClassInner.class.getMethod("setLeeftijd2", new Class<?>[] { int.class });
			String assertStatement = t.generateAssertStatementsForMethod(method);

			String expected = "Object leeftijd= getFieldvalueThroughReflection(tstClassInner,\"leeftijd\");";
			Assert.assertTrue(assertStatement.contains(expected));
			
			expected = "assertTrue(\"variable 'leeftijd' and '3' should be deep equal!\",checkForDeepEquality(leeftijd,\"3\"));";
			Assert.assertTrue(assertStatement.contains(expected));

		} catch (SecurityException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testIsPrimitive(){
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		
		boolean result = t.isPrimitive(null);
		
		Assert.assertTrue("null ref is primitive should be (though it is funny) be true", result);
	}
	
	@Test
	public void testIsPrimitiveFallback()
	{
		TestExpert t = new TestExpertImplForUnittestingPurposes();
		t.init(TstClassInner.class);
		
		boolean result = t.isPrimitiveFallback("notanixistingtipe");
		
		Assert.assertFalse("not existing primitive type should return false", result);
	}
}