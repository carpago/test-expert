package nl.carpago.testexpert;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.ParameterNamesNotFoundException;
import com.thoughtworks.paranamer.Paranamer;

public abstract class TestExpert extends TestCase {

	protected enum MockFramework {
		EASYMOCK, MOCKIT
	}

	protected MockFramework mockFramework;

	private static Logger logger = Logger.getLogger(TestExpert.class);

	private Class<?> classUnderTest;
	private Package pakkage;
	private Set<String> imports;

	private List<String> annotionsBeforeTestClass;

	private String header;

	private String body;

	private HashMap<String, Class<?>> fixtures;

	private Set<String> collabs;

	private ApplicationContext ctx;

	private String footer = "";

	private final List<String> generatedTestClasses = new ArrayList<String>();

	// constants
	private final String EMPTY_STRING = "";
	private final String QUESTION_MARK = "?";
	private final String ASTERISK = "*";
	private final String RESULTFROMMETHOD = "resultFromMethod";

	private final String[] illegalForVariables = new String[] { "\"", "'", "(", ")", "-", ".", "+", "!", "@", "#", "%", "^", "&", "*", "=", " " };

	private void clean() {

		this.classUnderTest = null;
		this.pakkage = null;
		this.imports = new TreeSet<String>();
		this.annotionsBeforeTestClass = new ArrayList<String>();
		this.header = "";
		this.body = "";
		this.fixtures = new HashMap<String, Class<?>>();
		this.collabs = new HashSet<String>();
		this.ctx = null;
		this.footer = "";
	}

	public void writeFile() throws FileNotFoundException {
		logger.debug("entering writeFile");
		final String fileName = getOutputFolder() + "/" + this.classUnderTest.getName().replaceAll("\\.", "/") + "Test.java";
		final String directoryName = getOutputFolder() + "/" + this.classUnderTest.getPackage().getName().replaceAll("\\.", "/");

		logger.debug("creating directory " + directoryName);
		File directory = new File(directoryName);
		directory.mkdirs();
		logger.debug("finished creating directory " + directoryName);

		File file = new File(fileName);
		if (!file.exists() || this.overwriteExistingFiles()) {
			FileOutputStream stream = new FileOutputStream(file);
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error("Unable to create outputfile. System halted.");
				System.exit(1);
			}

			PrintStream po = new PrintStream(stream);
			po.print(this.codeGen());
			po.close();

			addTestToTestsuite(this.classUnderTest.getName() + "Test.class");
		}

		logger.info(("Written '" + directoryName + this.classUnderTest.getSimpleName() + "Test'"));
		logger.debug("leaving writeFile");
	}

	protected File writeFile(String fileName, String directoryName, String content) throws FileNotFoundException {
		File directory = new File(directoryName);
		directory.mkdirs();

		File file = new File(directoryName + "/" + fileName);
		FileOutputStream stream = new FileOutputStream(file);
		try {
			file.createNewFile();
			PrintStream po = new PrintStream(stream);
			po.print(content);
			po.close();
		} catch (IOException e) {
			logger.error("Unable to create outputfile. System halted.");
			System.exit(1);
		}

		return file;
	}

	protected void addTestToTestsuite(String testClassName) {
		this.generatedTestClasses.add(testClassName);
	}

	public BufferedInputStream getInputStreamFromGeneratedCode() {

		final PipedInputStream pipedInputStream = new PipedInputStream();
		try {
			final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					PrintStream po = new PrintStream(pipedOutputStream);
					po.print(codeGen());
					po.flush();
					po.close();
					try {
						pipedOutputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						pipedOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			});
			t.start();

			return new BufferedInputStream(pipedInputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return new BufferedInputStream(pipedInputStream) {

				@Override
				public int read() throws IOException {
					return 0;
				}
			};
		}
	}

	protected List<String> findAllJavaFiles(String a_folderOrFile) throws IOException {
		logger.debug("entering");

		List<String> lines = new LinkedList<String>();
		File folderOrFile = new File(a_folderOrFile);

		for (File aFolderOrfile : folderOrFile.listFiles()) {
			if (aFolderOrfile.isFile()) {
				String aFile = aFolderOrfile.getPath();
				if (aFile.endsWith(".java")) {
					String sourceFolder = (getSourceFolder() + "/").replaceAll("\\\\", "/");
					aFile = aFile.replaceAll("\\\\", "/");
					aFile = aFile.replaceAll(sourceFolder, ""); // strip
																// sourcefolder
					aFile = aFile.replaceAll("/", "\\.");
					aFile = aFile.replaceAll(".java", "");

					lines.add(aFile);
				}
			} else {
				if (aFolderOrfile.isDirectory()) {
					lines.addAll(findAllJavaFiles(aFolderOrfile.getPath()));
				}
			}
		}

		return lines;
	}

	protected void init(Class<?> classUnderTest) {
		clean();
		logger.debug("entering init");
		this.classUnderTest = classUnderTest;

		this.addPackage();

		logger.debug("setting ApplicationContext");
		this.ctx = new AnnotationConfigApplicationContext(this.getFixture());

		initializeTestFramework();

		logger.debug("leaving");
	}

	private void initializeTestFramework() {

		this.checkAndAddImport(org.junit.Before.class);
		this.checkAndAddImport(org.junit.Test.class);
		this.checkAndAddImport(nl.carpago.testexpert.AbstractTestExpert.class);
		this.checkAndAddImport(this.getFixture());
		this.checkAndAddImport(org.junit.runner.RunWith.class);
		this.checkAndAddImport(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class);
		this.checkAndAddImport(org.springframework.test.context.ContextConfiguration.class);
	}

	protected void generateTestClass() throws InvalidAnnotationException {
		logger.debug("enter");

		generateAnnotationsForSpringTest();

		generateHeader();

		generateFixturesForEntireClass();

		addCodeLn();
		addCodeLn("\t// class under test");
		addCodeLn("\tprivate " + this.classUnderTest.getSimpleName() + " " + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + ";");

		generateCollaboratingClasses();
		generateSetup();
		generateMethodsWithAnnotationTestMe();

		generateGettersForCollaborators();

		generateFooter();
		logger.debug("leave");

	}

	private void generateFixturesForEntireClass() {
		logger.debug("enter");
		List<Method> methodes = getMethodsWithAnnotationCreateUnitTest(this.classUnderTest);
		for (Method methode : methodes) {
			generateFixturesForMethod(methode);
		}
		logger.debug("leave");
	}

	protected void generateHeader() {
		logger.debug("enter");
		this.header += "public class " + this.classUnderTest.getSimpleName() + "Test extends AbstractTestExpert {\n";
		logger.debug("leave");
	}

	protected void generateFooter() {
		logger.debug("enter");
		this.footer += "}";
		logger.debug("leave");
	}

	protected void generateAnnotationsForSpringTest() {
		logger.debug("enter");

		this.annotionsBeforeTestClass.add("@RunWith(SpringJUnit4ClassRunner.class)");
		this.annotionsBeforeTestClass.add("@ContextConfiguration(classes={" + this.getFixture().getSimpleName() + ".class})");

		logger.debug("leave");

	}

	private void addPackage() {
		logger.debug("enter");
		this.pakkage = this.classUnderTest.getPackage();
		logger.debug("leave");

	}

	// deze methode print naar standard output de methode zijn invokeinterface
	// geEasyMockte aanroepen.
	// rloman: known issues:
	/*
	 * 
	 * argumenten van call naar service indien van toepassing gelijk maken in
	 * call naar interface (dao)
	 */
	// rloman: deze methode is VEEEEEL te lang geworden. Dus ook refactoren.

	// TODO Test
	protected String getPathToBinaryFile(Class<?> clazz) {
		return this.getBinaryFolder() + "/" + clazz.getName().replaceAll("\\.", "/");
	}

	// TODO test
	protected List<String> getLinesFromFile(String pathToBinaryFile) throws IOException {
		// via jad
		ProcessBuilder builder = new ProcessBuilder("jad", "-af", "-p", pathToBinaryFile);

		Process process = builder.start();

		InputStream stream = process.getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader bufferReader = new BufferedReader(reader);
		LinkedList<String> lines = new LinkedList<String>();

		String line = null;
		while ((line = bufferReader.readLine()) != null) {
			lines.add(line);
		}

		return lines;
	}

	// TODO Test
	protected Pattern getRegularExpressionForMethod(Method methodeArgument) {
		// create here the Regular expression for and from the method
		String accessMod = Modifier.toString(methodeArgument.getModifiers());
		String returnClass = methodeArgument.getReturnType().getSimpleName();
		String methodeLocalName = methodeArgument.getName();
		Class<?>[] formalParameters = methodeArgument.getParameterTypes();

		String regexp = accessMod + " " + returnClass + " " + methodeLocalName + "\\(";
		for (int i = 0; i < formalParameters.length; i++) {
			regexp += formalParameters[i].getSimpleName() + " ";
			regexp += "(\\w)+";
			if (i != formalParameters.length - 1) {
				regexp += ", ";
			}
		}
		regexp += "\\)";

		Pattern pattern = Pattern.compile(regexp);

		return pattern;
	}

	// TODO Test
	protected List<Class<?>> parseParameters(Scanner s, Pattern p) {
		List<Class<?>> params = new ArrayList<Class<?>>();

		while (s.hasNext()) {
			String param = s.next().trim();
			logger.debug(param);
			Class<?> parameter = null;
			if (!StringUtils.isEmpty(param)) {
				try {
					parameter = Class.forName(param);
				} catch (ClassNotFoundException e) {
					logger.info("Class not found for " + param);
					if (this.isPrimitiveFallback((param))) {
						parameter = this.getPrimitiveType(param);
					} else {
						assert false; // should never happen.
					}
				}
				params.add(parameter);
			}
		}

		return params;

	}

	protected String generateExpectForCollaboratorsOfMethod(Method methodeArgument) throws IOException {

		logger.debug("enter generateExpectForCollabsOfMethod");

		String result = EMPTY_STRING;
		String[] inputParametersViaAnnotations = this.getInAnnotationsForMethod(methodeArgument);

		Set<String> localVars = new HashSet<String>();
		String fileName = this.getPathToBinaryFile(this.classUnderTest);
		List<String> lines = this.getLinesFromFile(fileName);
		Pattern p = this.getRegularExpressionForMethod(methodeArgument);

		String linesLocal = null;
		Set<String> mocked = new HashSet<String>();
		List<Integer> gemockteRegelsUitSource = new ArrayList<Integer>();
		outer: for (int i = 0; i < lines.size(); i = i + 1) {
			linesLocal = lines.get(i);
			Matcher m = p.matcher(linesLocal);
			if (m.find()) {
				inner: for (int j = i; j < lines.size(); j++) {
					linesLocal = lines.get(j);
					if (linesLocal.equals("    }")) {
						break outer;
					}

					if (linesLocal.indexOf("invokeinterface") > -1 || linesLocal.indexOf("invokevirtual") > -1) {

						Pattern patternForSeparatingParameters = Pattern.compile("\\(|,|\\)>");
						Scanner s = new Scanner(linesLocal).useDelimiter(patternForSeparatingParameters);
						String collabAndInvokee = null;
						Scanner collabScanner = new Scanner(s.next()).useDelimiter(" ");
						while (collabScanner.hasNext()) {
							collabAndInvokee = collabScanner.next();
						}

						List<Class<?>> params = this.parseParameters(s, patternForSeparatingParameters);

						String[] collabs = collabAndInvokee.split("\\.");
						String collab = EMPTY_STRING;

						for (int collabLoop = 0; collabLoop < collabs.length - 1; collabLoop++) {
							collab += collabs[collabLoop] + ".";
						}

						collab = collab.substring(0, collab.length() - 1);
						String invokee = collabs[collabs.length - 1];

						Class<?>[] parametersVoorInvokee = new Class<?>[params.size()];
						parametersVoorInvokee = (Class[]) params.toArray(parametersVoorInvokee);

						Method method = null;
						try {
							method = Class.forName(collab).getMethod(invokee, parametersVoorInvokee);
						} catch (SecurityException e) {
							logger.error(e);
						} catch (NoSuchMethodException e) {
							logger.error(e);
							assert false;
						} catch (ClassNotFoundException e) {
							logger.error(e);
							assert false;
						}

						if (collab.equals(this.classUnderTest.getName())) {
							continue inner;
						}

						for (int k = j - 1; k > i; k--) {
							String regelHoger = lines.get(k);
							if (regelHoger.indexOf(invokee) > -1) {
								if (gemockteRegelsUitSource.contains(k)) {
									continue inner; // already done ...
								} else {
									gemockteRegelsUitSource.add(k);
								}
								InvokeDTO invokeDTO = null;
								if (this.isCallerForCollab(regelHoger.trim())) {
									invokeDTO = new InvokeDTO(regelHoger.trim(), this.collabs);
								} else {
									continue inner;
								}

								String construction = invokeDTO.getCollabMethodParams();
								mocked.add(construction);

								// create a list of them both.
								List<String> testMethodeZijnParams = new ArrayList<String>(Arrays.asList(this.getParameterNamesForMethod(methodeArgument)));
								List<String> collabZijnParams = invokeDTO.getParams();

								String[] in = this.getInAnnotationsForMethod(method);
								// En neem de wiskundige A-B
								for (int n = 0; n < collabZijnParams.size(); n++) {
									String element = collabZijnParams.get(n);
									if (!testMethodeZijnParams.contains(element) && !(isLiteral(element)) && !(localVars.contains(element))) {
										localVars.add(element);
										if (in != null && in.length != 0) {
											String annotatieElement = in[n];
											if (!(QUESTION_MARK.equals(annotatieElement) || ASTERISK.equals(annotatieElement))) {
												if (!this.isLiteral(annotatieElement)) {
													addFixture(annotatieElement);
												}
												construction = construction.replaceAll(element, annotatieElement);

											} else {
												result += generateParameter(method, element, n);
											}
										} else {
											try {
												if (!this.isLiteral(element)) {
													Class<?> parameterType = method.getParameterTypes()[n];
													result += addCode("\t\t" + parameterType.getSimpleName() + " " + element + " = ");
													result += addCode(generateConstructorForClass(parameterType));
													result += addCodeLn(";");
												}
											} catch (IndexOutOfBoundsException iobe) {
												logger.error("INdexOutOfBoundException for method:" + method.getName() + ", index:" + n);
											}
										}
									} else {
										try {
											if (!this.isLiteral(collabZijnParams.get(n))) {
												construction = construction.replaceAll(element, inputParametersViaAnnotations[n]);
											}
										} catch (IndexOutOfBoundsException iobe) {
											logger.error("IndexOutOfBoundsException in replacing elemnt with annotation.");
										}
									}
								}
								result += this.generateReturnForMethod(method, construction);

								continue inner;
							}
						}
					}
				}
			}
		}
		logger.debug("leave");

		return result;
	}

	// TODO Test
	private String generateParameter(Method method, String element, int elementIndex) {
		String result = EMPTY_STRING;
		if (!this.isLiteral(element)) {
			Class<?> parameterType = method.getParameterTypes()[elementIndex];
			result += addCode("\t\t" + parameterType.getSimpleName() + " " + element + " = ");
			result += addCode(generateConstructorForClass(parameterType));
			result += addCodeLn(";");
		}

		return result;
	}

	// TODO Test
	private String generateReturnForMethod(Method method, String construction) {
		String returnFromMethod = null;
		String result = EMPTY_STRING;
		String out = this.getOutAnnotationForMethod(method);

		if ("void".equals(method.getReturnType().toString())) {
			result += addCodeLn("\t\t" + construction + ";");
		} else {
			if (out != null) {
				returnFromMethod = out;
			} else {
				returnFromMethod = generateConstructorForClass(method.getReturnType());
			}

			result += this.generateExpectationForMethod(method, construction);

			if (MockFramework.EASYMOCK.equals(getMockFramework())) {
				assert returnFromMethod != null;
				String cloneString = EMPTY_STRING;
				if (!this.isLiteral(returnFromMethod)) {
					if (this.isPrimitive(method.getReturnType())) {
						cloneString += returnFromMethod;
					} else {
						cloneString += "(" + method.getReturnType().getSimpleName() + ") this.cloneMe(" + returnFromMethod + ")";
					}
				} else {
					cloneString = returnFromMethod;
				}

				result += addCode(cloneString);

				result += addCodeLn(");");

			} else {
				if (MockFramework.MOCKIT.equals(getMockFramework())) {
					// return the value.
					result += addCodeLn("\t\t\t\t\t\treturn " + returnFromMethod + ";");

					result += addCodeLn("\t\t\t\t\t}");
					result += addCodeLn("\t\t\t\t};");
					result += addCodeLn("\t\t\t}");
					result += addCodeLn("\t\t};");
				}
			}
		}

		return result;
	}

	// TODO Test
	private String generateExpectationForMethod(Method method, String construction) {
		String result = EMPTY_STRING;
		if (MockFramework.MOCKIT.equals(getMockFramework())) {
			this.checkAndAddImport(mockit.Mocked.class);
			this.checkAndAddImport(mockit.Expectations.class);
			result += addCodeLn("\t\tnew Expectations() {");
			result += addCodeLn("\t\t\t{");
			result += addCodeLn("\t\t\t\t" + construction + ";");
			result += addCodeLn("\t\t\t\tforEachInvocation = new Object() {");
			result += addCodeLn("\t\t\t\t\t@SuppressWarnings(\"unused\")");
			this.checkAndAddImport(method.getReturnType().getClass());
			result += addCode("\t\t\t\t\t" + method.getReturnType().getSimpleName());
			result += addCode(" validate(");
			result += addCode(this.getParameterTypesAndNameAsString(method));
			result += addCodeLn("){");

		} else {
			if (MockFramework.EASYMOCK.equals(getMockFramework())) {
				this.checkAndAddImport(org.easymock.EasyMock.class);
				result += addCode("\t\tEasyMock.expect(" + construction + ").andReturn(");
			}
		}

		return result;
	}

	protected boolean isCallerForCollab(String aCollabKandidate) {

		for (String element : this.collabs) {
			if (aCollabKandidate.toLowerCase().indexOf(element.toLowerCase()) > -1) {
				return true;
			}
		}

		return false;

	}

	protected String generateReplays() {
		String result = addCodeLn();
		for (String collab : this.collabs) {
			result += addCodeLn("\t\tEasyMock.replay(" + collab + ");");
		}

		return result;
	}

	protected String generateVerifies() {
		String result = addCodeLn();
		for (String collab : this.collabs) {
			result += addCodeLn("\t\tEasyMock.verify(" + collab + ");");
		}

		return result;
	}

	protected String[] getInAnnotationsForMethod(Method method) {

		Annotation annotatie = method.getAnnotation(CreateUnittest.class);
		String[] in = null;
		if (annotatie == null) {
			annotatie = method.getAnnotation(nl.carpago.testexpert.annotation.Expect.class);
		}
		if (annotatie instanceof CreateUnittest) {
			in = ((CreateUnittest) annotatie).in();
		}
		if (annotatie instanceof Expect) {
			in = ((Expect) annotatie).in();
		}

		return in;
	}

	protected String getOutAnnotationForMethod(Method method) {
		Annotation annotatie = method.getAnnotation(CreateUnittest.class);
		String out = null;
		if (annotatie == null) {
			annotatie = method.getAnnotation(nl.carpago.testexpert.annotation.Expect.class);
		}
		if (annotatie instanceof CreateUnittest) {
			out = ((CreateUnittest) annotatie).out();
		}
		if (annotatie instanceof Expect) {
			out = ((Expect) annotatie).out();
		}

		return out;
	}

	protected boolean isLiteral(String literalOrVariablename) {
		logger.debug("enter");

		if (literalOrVariablename == null || EMPTY_STRING.equals(literalOrVariablename.trim())) {
			logger.info("String " + literalOrVariablename + " is considered as a literal!");
			return true;
		}
		for (String element : this.illegalForVariables) {
			if (literalOrVariablename.indexOf(element) > -1) {
				logger.info("String " + literalOrVariablename + " is considered as a literal!");
				return true;
			}
		}
		// have boolean or integer number
		if ("true".equalsIgnoreCase(literalOrVariablename) || "false".equalsIgnoreCase(literalOrVariablename)) {
			logger.info("String " + literalOrVariablename + " is considered as a literal!");
			return true;
		}

		// should be integer

		try {
			Long.parseLong(literalOrVariablename);
		} catch (NumberFormatException nfe) {
			logger.info("String " + literalOrVariablename + " is considered as a literal!");
			return false;
		}

		logger.info("String " + literalOrVariablename + " is NOT considered as a literal!");

		return true;
	}

	protected String generateConstructorForClass(Type t) {
		logger.debug("enter");
		String result = "";
		if ("byte".equals(t.toString())) {
			result += "(byte) 15";

		}
		if ("short".equals(t.toString())) {
			result += "(short) 1";
		}

		if ("int".equals(t.toString())) {
			result += "17";
		}
		if ("long".equals(t.toString())) {
			result += "18L";
		}
		if ("float".equals(t.toString())) {
			result += "19.5F";
		}
		if ("double".equals(t.toString())) {
			result += "20.5";
		}

		if ("char".equals(t.toString())) {
			result += "'a'";
		}

		if ("boolean".equals(t.toString())) {
			result += "true";
		}

		if ("class [I".equals(t.toString())) {
			result += "new int[]{1,2,3}";
		}

		Class<?> clazz = null;
		if (t instanceof Class) {

			clazz = (Class<?>) t;
			if (clazz.isInterface()) {
				result += generateSomethingForInterface(clazz);

				return result;
			}
			if (clazz.getConstructors().length > 0) {

				// first try default constructor. If not available, try first
				// constructor.
				Constructor<?> c = null;
				try {
					c = clazz.getConstructor();
				} catch (SecurityException e) {
					logger.error(e);
				} catch (NoSuchMethodException e) {
					// default constructor failed so trying first (real)
					// constructor");
					c = clazz.getConstructors()[0];
				}
				this.checkAndAddImport(c.getDeclaringClass());
				result += "new " + c.getDeclaringClass().getSimpleName() + "(";

				Class<?>[] parameterTypes = c.getParameterTypes();
				int counter = 0;
				for (Class<?> parameterType : parameterTypes) {
					counter++;
					// recursive create constructors parameters constructors
					result += generateConstructorForClass(parameterType);
					if (counter != parameterTypes.length) {
						result += ", ";
					}
				}
				result += ")";
			}
		}

		logger.debug("leave");

		return result;
	}

	protected String generateSomethingForInterface(Class<?> eenInterface) {
		logger.debug("enter");

		this.checkAndAddImport(eenInterface);

		logger.debug("leave");

		return "EasyMock.createMock(" + eenInterface.getSimpleName() + ".class)";
	}

	private void generateCollaboratingClasses() {
		logger.debug("enter generateCollaboratingClasses");
		Field[] fields = this.classUnderTest.getDeclaredFields();
		if (fields.length > 0) {
			addCodeLn();
			addCodeLn("\t// collaborating classes");
		}
		for (Field field : fields) {
			if (!(this.isPrimitive(field.getType()))) {
				this.checkAndAddImport(field.getType());
			}
			if (MockFramework.MOCKIT.equals(this.getMockFramework())) {
				this.checkAndAddImport(mockit.Mocked.class);
				addCodeLn("\t@Mocked");
			}
			addCode("\tprivate " + field.getType().getSimpleName() + " ");
			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) field.getGenericType();
				this.generateGeneric(pType);
			}
			addCodeLn(WordUtils.uncapitalize(field.getName()) + ";");
			this.collabs.add(WordUtils.uncapitalize(field.getName()));

		}
		addCodeLn();

		logger.debug("leave generateCollaboratingClasses");
	}

	private String generateGeneric(ParameterizedType pType) {
		String result = addCode("<");
		for (Type t : pType.getActualTypeArguments()) {
			if (t instanceof Class) {
				Class<?> genericArgument = (Class<?>) t;
				result += addCode(genericArgument.getSimpleName());
				this.checkAndAddImport(genericArgument);
			}
		}
		result += addCode(">");

		return result;
	}

	protected String generateGettersForCollaborators() {
		logger.debug("enter");
		Field[] fields = this.classUnderTest.getDeclaredFields();
		String result = "";
		for (Field field : fields) {
			result += addCodeLn();
			result += addCode("\tpublic " + field.getType().getSimpleName());
			if (field.getGenericType() instanceof ParameterizedType) {
				result += this.generateGeneric((ParameterizedType) field.getGenericType());
			}
			result += addCodeLn(" get" + WordUtils.capitalize(field.getName()) + "(){");
			result += addCodeLn("\t\treturn this." + WordUtils.uncapitalize(field.getName()) + ";");
			result += addCodeLn("\t}");
		}
		logger.debug("leave");

		return result;
	}

	protected String generateSetup() {
		logger.debug("enter");

		String result = EMPTY_STRING;

		result += addCodeLn("\t@Before");
		if (MockFramework.EASYMOCK.equals(getMockFramework())) {
			result += addCodeLn("\t@SuppressWarnings(\"unchecked\")");
		}

		result += addCodeLn("\t@Override");
		result += addCodeLn("\tpublic void setUp() {");
		// initialize the class under test
		result += addCode("\t\tthis." + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + " = ");
		result += addCode(generateConstructorForClass(this.classUnderTest));
		result += addCodeLn(";");

		// init the collaborating classes
		for (Field field : this.classUnderTest.getDeclaredFields()) {
			result += addCodeLn();
			if (!(this.isPrimitive(field.getType()))) {
				if (MockFramework.EASYMOCK.equals(getMockFramework())) {
					result += addCodeLn("\t\tthis." + WordUtils.uncapitalize(field.getName()) + " = EasyMock.createMock(" + field.getType().getSimpleName() + ".class);");
				}

				// probeer de setter te vinden. Indien dit niet kan dan niet ...
				// dan lijkt het niet nodig.
				try {
					String fieldNameFirstLetterCap = WordUtils.capitalize(field.getName());
					Method setter = this.classUnderTest.getMethod("set" + fieldNameFirstLetterCap, field.getType());
					result += addCodeLn("\t\tthis." + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + "." + setter.getName() + "(" + "this." + WordUtils.uncapitalize(field.getName())
							+ ");");

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					result += addCodeLn("\t\tsetFieldThroughReflection(" + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + ", \"" + field.getName() + "\", this."
							+ WordUtils.uncapitalize(field.getName()) + ");");
				}
			}
		}
		result += addCodeLn("\t}");

		logger.debug("leave");

		return result;
	}

	protected void generateMethodsWithAnnotationTestMe() throws InvalidAnnotationException {
		logger.debug("enter");
		List<Method> methodes = getMethodsWithAnnotationCreateUnitTest(this.classUnderTest);
		for (Method methode : methodes) {

			String testMethodName = EMPTY_STRING;
			Class<?>[] parameterTypes = methode.getParameterTypes();

			testMethodName += "test" + WordUtils.capitalize(methode.getName());
			String[] parameterNames = this.getParameterNamesForMethod(methode);

			if (parameterNames != null && parameterNames.length > 0) {
				testMethodName += "For" + parameterTypes[0].getSimpleName() + WordUtils.capitalize(parameterNames[0]);
			}

			if (parameterNames != null) {
				for (int i = 1; i <= parameterNames.length - 1; i++) {
					testMethodName += "And" + parameterTypes[i].getSimpleName() + WordUtils.capitalize(parameterNames[i]);
				}
			}

			addCodeLn();
			addCodeLn("\t@Test");
			addCode("\tpublic void " + testMethodName + "(");

			addCodeLn("){ ");

			if (methode.getParameterTypes().length != 0) {
				generateCreateArgumentsForTestMethod(methode);
			}

			try {
				generateExpectForCollaboratorsOfMethod(methode);
			} catch (IOException e) {
				logger.error(e);
			}

			if (MockFramework.EASYMOCK.equals(getMockFramework())) {
				generateReplays();
			}

			generateCallToTestMethod(methode);

			if (MockFramework.EASYMOCK.equals(getMockFramework())) {
				generateVerifies();
			}

			if (!"void".equals(methode.getReturnType().toString())) {
				generateAssertStatements(methode);
			}
			addCodeLn("\t}");
		}
		logger.debug("leave");
	}

	protected String generateCallToTestMethod(Method methode) {
		logger.debug("enter");

		String parameterString = EMPTY_STRING;
		String result = EMPTY_STRING;

		result += addCodeLn();
		result += addCode("\t\t");

		if (!"void".equals(methode.getReturnType().toString())) {
			this.checkAndAddImport(methode.getReturnType());
			result += addCode(methode.getReturnType().getSimpleName() + " " + this.RESULTFROMMETHOD + " = ");
		}
		result += addCode(WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + "." + methode.getName() + "(");

		String[] parameterNames = methode.getAnnotation(CreateUnittest.class).in(); // this.getParameterNamesForMethod(methode);
		String first = EMPTY_STRING;
		String tail = EMPTY_STRING;
		if (!(parameterNames.length < 1)) {
			first = parameterNames[0];
		}
		for (int i = 1; i <= parameterNames.length - 1; i++) {
			tail += ", " + parameterNames[i];
		}

		parameterString = first + tail;

		result += addCode(parameterString);
		result += addCode(");");

		result += addCodeLn();

		logger.debug("leave");

		return result;
	}

	protected String generateAssertStatements(Method method) {
		logger.debug("enter");
		String result = EMPTY_STRING;
		String expected = method.getAnnotation(CreateUnittest.class).out();
		String actual = this.RESULTFROMMETHOD;
		result += addCode("\n\t\t");
		result += addCodeLn("assertTrue(\"variable '" + expected + "' and '" + actual + "' should be deep equal!\", this.checkForDeepEquality(" + expected + ", " + actual + "));");

		logger.debug("leave");

		return result;
	}

	protected List<Class<?>> generateFixturesForMethod(Method methode) {
		logger.debug("enter");

		List<Class<?>> result = new ArrayList<Class<?>>();

		CreateUnittest annotation = (CreateUnittest) methode.getAnnotation(nl.carpago.testexpert.annotation.CreateUnittest.class);
		String[] inputFixtures = annotation.in();
		String outputFixture = annotation.out();

		List<String> fixturesAll = new LinkedList<String>(Arrays.asList(inputFixtures));
		if (!EMPTY_STRING.equals(outputFixture)) {
			fixturesAll.add(outputFixture);
		}

		for (String fixture : fixturesAll) {
			if (!(this.isLiteral(fixture) || QUESTION_MARK.equals(fixture) || ASTERISK.equals(fixture))) {
				Class<?> fixtureClass = addFixture(fixture);
				result.add(fixtureClass);
			}
		}

		logger.debug("leave");

		return result;
	}

	private Class<?> addFixture(String fixture) {
		logger.debug("enter");

		this.checkAndAddImport(org.springframework.beans.factory.annotation.Autowired.class);

		Object o = ctx.getBean(fixture);
		this.checkAndAddImport(o);
		this.fixtures.put(fixture, o.getClass());

		logger.debug("leave");

		return o.getClass();
	}

	protected String codeGenFixtures() {
		logger.debug("enter");

		String result = EMPTY_STRING;

		Set<Entry<String, Class<?>>> entrySet = this.fixtures.entrySet();

		if (!entrySet.isEmpty()) {
			result += "\n\t// fixtures\n";
		}
		for (Entry<String, Class<?>> entry : entrySet) {
			String variableName = entry.getKey();
			Class<?> c = entry.getValue();

			result += "\t@Autowired ";
			result += "private " + c.getSimpleName() + " ";
			result += variableName + ";\n";
		}
		logger.debug("leave");
		return result;
	}

	protected static List<Method> getMethodsWithAnnotationCreateUnitTest(Class<?> clazz) {
		logger.debug("enter");
		List<Method> result = new ArrayList<Method>();
		for (Method methode : clazz.getDeclaredMethods()) {
			if (methode.getAnnotation(nl.carpago.testexpert.annotation.CreateUnittest.class) != null) {
				result.add(methode);
			}
		}

		logger.debug("leave");

		return result;
	}

	protected String generateCreateArgumentsForTestMethod(Method methodeToBeTested) throws InvalidAnnotationException {
		logger.debug("enter");

		String[] parameterNames = null;

		parameterNames = this.getParameterNamesForMethod(methodeToBeTested);

		assert parameterNames != null;

		String[] inputParametersViaAnnotatie = methodeToBeTested.getAnnotation(nl.carpago.testexpert.annotation.CreateUnittest.class).in();

		if (parameterNames.length != inputParametersViaAnnotatie.length) {
			logger.warn("Annotation and parameters are ordinal not equal!");
			logger.warn("parameternames:" + Arrays.asList(parameterNames));
			logger.warn("InputParameters through annotation:" + Arrays.asList(inputParametersViaAnnotatie));
			throw new InvalidAnnotationException("Annotation and parameters are ordinal invalid.");
		}

		Class<?>[] parameterTypes = methodeToBeTested.getParameterTypes();

		String result = EMPTY_STRING;
		for (int i = 0; i < parameterNames.length; i++) {
			// if ? than create via constructor. if * than try first via
			// appcontext else via constructor through name of the variable.
			if (QUESTION_MARK.equals(inputParametersViaAnnotatie[i]) || (ASTERISK.equals(inputParametersViaAnnotatie[i]) && !this.fixtures.containsKey(parameterNames[i]))) {
				result += addCode("\t\t" + parameterTypes[i].getSimpleName() + " " + parameterNames[i] + " = ");
				result += addCode(generateConstructorForClass(parameterTypes[i]));
				result += addCodeLn(";");
			}
		}
		logger.debug("leave");

		return result;
	}

	protected String[] getParameterNamesForMethod(Method method) {
		if (method == null || method.getParameterTypes().length == 0) {
			return new String[] {};
		}
		logger.debug("methode:" + method);

		Paranamer paranamer = new AdaptiveParanamer();
		String[] result = null;
		String parameter = null;
		List<String> tempListToCreateArray = new ArrayList<String>();

		try {
			result = paranamer.lookupParameterNames(method, true);
		} catch (ParameterNamesNotFoundException npe) {
			// else use the names of the type camelcased.
			Class<?>[] parameterClasses = method.getParameterTypes();
			int counter = 0;
			for (Class<?> parameterClass : parameterClasses) {
				parameter = WordUtils.uncapitalize(parameterClass.getSimpleName()) + ++counter;
				tempListToCreateArray.add(parameter);
			}
			result = tempListToCreateArray.toArray(new String[tempListToCreateArray.size()]);
		}

		logger.debug("leave");

		return result;
	}

	protected String[] getParameterTypesForMethod(Method method) {

		if (method == null || method.getParameterTypes().length == 0) {
			return new String[] {};
		}

		List<String> result = new ArrayList<String>();

		for (Class<?> clazz : method.getParameterTypes()) {
			result.add(clazz.getSimpleName());
		}

		return result.toArray(new String[result.size()]);
	}

	protected String getParameterTypesAndNameAsString(Method method) {

		String result = "";

		String[] parameterTypes = this.getParameterTypesForMethod(method);
		String[] parameterNames = this.getParameterNamesForMethod(method);

		String first = EMPTY_STRING;
		String tail = EMPTY_STRING;
		if (!(parameterNames.length < 1)) {
			first = parameterTypes[0] + " " + parameterNames[0];
		}
		for (int i = 1; i <= parameterNames.length - 1; i++) {
			tail += ", " + parameterTypes[i] + " " + parameterNames[i];
		}

		result = first + tail;

		return result;
	}

	// in: int, boolean, char ... out: Integer, Boolean Character
	protected Class<?> getPrimitiveType(String baseType) {
		logger.debug("enter");

		if ("byte".equals(baseType))
			return byte.class;
		if ("short".equals(baseType))
			return short.class;
		if ("int".equals(baseType))
			return int.class;
		if ("long".equals(baseType))
			return long.class;
		if ("float".equals(baseType))
			return float.class;
		if ("double".equals(baseType))
			return double.class;
		if ("char".equals(baseType))
			return char.class;
		if ("boolean".equals(baseType))
			return boolean.class;
		if ("void".equals(baseType))
			return void.class;

		throw new InvalidArgumentException("Invalid Argument");
	}

	protected boolean isPrimitive(Class<?> clazz) {
		logger.debug("enter");
		assert clazz != null;

		if (clazz == null) {
			logger.warn("TestExpert::isPrimitive ... clazz is null!");
			return false;
		} else {
			return clazz.isPrimitive();
		}
	}

	protected boolean isPrimitiveFallback(String type) {
		logger.debug("enter");
		try {
			this.getPrimitiveType(type);
			logger.info("String " + type + " is considered primitive!");
			logger.debug("leave");

			return true;
		} catch (InvalidArgumentException iae) {
			logger.info("String " + type + " is NOT considered primitive!");
			logger.debug("leave");

			return false;
		}
	}

	protected void checkAndAddImport(Object classOrArrayOfClassesToImport) {

		if (this.isPrimitive(classOrArrayOfClassesToImport.getClass())) {
			return;
		}

		assert classOrArrayOfClassesToImport != null;
		logger.debug("enter");

		if (classOrArrayOfClassesToImport.getClass().isArray()) {
			Object[] ourTempObjectArray = new Object[Array.getLength(classOrArrayOfClassesToImport)];
			ourTempObjectArray[0] = Array.get(classOrArrayOfClassesToImport, 0);
			this.checkAndAddImport(ourTempObjectArray[0]);
		} else {
			if (classOrArrayOfClassesToImport instanceof Collection<?>) {

				Object anObject = ((Collection<?>) classOrArrayOfClassesToImport).iterator().next();
				checkAndAddImport(classOrArrayOfClassesToImport.getClass()); // insert
																				// the
																				// Collection
																				// type
				if (anObject != null) {
					checkAndAddImport(anObject);
				}
			} else {
				if (classOrArrayOfClassesToImport instanceof Map) {
					Map map = (Map) classOrArrayOfClassesToImport;
					Set<Entry<?, ?>> set = map.entrySet();
					for (Entry<?, ?> entry : set) {
						this.checkAndAddImport(classOrArrayOfClassesToImport.getClass());
						this.checkAndAddImport(entry.getKey());
						this.checkAndAddImport(entry.getValue());
					}
					this.checkAndAddImport(map.keySet().iterator().next());
					this.checkAndAddImport(map.values().iterator().next());

				} else {
					if (classOrArrayOfClassesToImport instanceof Class) {
						Class<?> aRealClass = (Class<?>) classOrArrayOfClassesToImport;
//						System.out.println(this.isPrimitive(aRealClass));
						try {
							System.out.println(aRealClass.getPackage().getName());
						}
						catch(NullPointerException npe) {
							System.out.println("Deze heeft geen package "+aRealClass+" en is primitivie is:"+this.isPrimitive(aRealClass));
						}
	//					System.out.println(aRealClass.getName());
						if (!this.isPrimitive(aRealClass) && !"java.lang".equals(aRealClass.getPackage().getName()) && !this.pakkage.getName().equals(aRealClass.getPackage().getName())
								&& !this.imports.contains(aRealClass.getName())) {
							this.imports.add(aRealClass.getName());
						}
					} else {
						assert classOrArrayOfClassesToImport instanceof Object;
						checkAndAddImport(classOrArrayOfClassesToImport.getClass());
					}
				}

			}

		}
		logger.debug("leave");
	}

	private String addCode(String code) {
		logger.debug("enter");
		logger.debug("Adding code " + code);

		this.body += code;

		logger.debug("leave");

		return code;
	}

	private String addCodeLn(String code) {
		return this.addCode(code + "\n");
	}

	private String addCodeLn() {
		return this.addCode("\n");
	}

	public String codeGen() {
		logger.debug("enter");

		String result = EMPTY_STRING;

		result += codeGenPackage();
		result += codeGenImports();
		result += codeGenAnnotationsForSpringTest();
		result += codeGenHeader();
		result += codeGenFixtures();
		result += codeGenBody();
		result += codeGenFooter();

		logger.debug("leave");

		return result.trim();
	}

	protected String codeGenTestsuite() {
		String result = EMPTY_STRING;
		result += "import junit.framework.JUnit4TestAdapter;\n";
		result += "import junit.framework.TestCase;\n" + "import junit.framework.TestSuite;\n";
		result += "\n";
		result += "import org.junit.runner.RunWith;\n" + "import org.junit.runners.AllTests;\n";

		result += "\n";

		result += "@RunWith(AllTests.class)\n";
		result += "public final class " + this.getTestsuiteName() + " extends TestCase {\n";
		result += "\n";

		result += "\tpublic static TestSuite suite() {\n";
		result += "\t\tTestSuite suite = new TestSuite();\n";
		result += "\n";

		for (String testClassName : this.getAllTests()) {
			result += "\t\tsuite.addTest(new JUnit4TestAdapter(";
			result += testClassName;
			result += "));\n";
		}

		result += "\n";

		result += "\t\treturn suite;\n";

		result += "\t}\n";
		result += "}";

		return result;
	}

	protected String codeGenHeader() {
		return this.header;
	}

	private String codeGenBody() {
		return this.body;
	}

	protected String codeGenFooter() {
		return this.footer;
	}

	protected String codeGenAnnotationsForSpringTest() {
		logger.debug("enter");
		String result = EMPTY_STRING;

		for (String annotatie : this.annotionsBeforeTestClass) {
			result += annotatie + "\n";
		}

		logger.debug("leave");

		return result;
	}

	protected String codeGenPackage() {

		String result = EMPTY_STRING;
		result += this.pakkage + ";\n\n";

		return result;
	}

	protected String codeGenImports() {
		logger.debug("enter");
		String result = EMPTY_STRING;

		for (String importLine : this.imports) {

			result += "import " + importLine + ";\n";
		}
		result += "\n";

		logger.debug("leave");

		return result;
	}

	protected Class<?> getClassUnderTest() {
		return classUnderTest;
	}

	protected Package getPakkage() {
		return pakkage;
	}

	protected ApplicationContext getCtx() {
		return ctx;
	}

	protected String getHeader() {
		return header;
	}

	protected String getFooter() {
		return footer;
	}

	protected Set<String> getImports() {
		return imports;
	}

	protected List<String> getAnnotionsBeforeTestClass() {
		return annotionsBeforeTestClass;
	}

	protected HashMap<String, Class<?>> getFixtures() {
		return fixtures;
	}

	protected void setCurrentFramework(MockFramework currentFramework) {
		this.mockFramework = currentFramework;
	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * 
	 *             this is the entryPoint / mainmethod of the TestExpert
	 *             application
	 */
	@Test
	public void testStarter() throws ClassNotFoundException, FileNotFoundException {
		logger.debug("entering setup");
		List<String> lijstMetAlleJavaFilesUitProject = null;

		try {
			lijstMetAlleJavaFilesUitProject = findAllJavaFiles(getSourceFolder());
		} catch (IOException e) {
			logger.fatal(e.getMessage());
		}

		Class<?> classUnderTest = null;
		for (String classFile : lijstMetAlleJavaFilesUitProject) {
			classUnderTest = Class.forName(classFile); //

			List<Method> methods = getMethodsWithAnnotationCreateUnitTest(classUnderTest);
			if (methods != null && !methods.isEmpty()) {

				this.init(classUnderTest);

				try {
					this.generateTestClass();
				} catch (InvalidAnnotationException e) {
					logger.warn(e);
				}

				this.writeFile();
			}
		}

		if (this.getAllTests() != null && !this.getAllTests().isEmpty()) {
			String fileName = getTestsuiteName();
			if (!fileName.endsWith(".java")) {
				fileName += ".java";
			}
			this.writeFile(fileName, this.getOutputFolder(), this.codeGenTestsuite());
		}

		logger.debug("leaving main");

	}

	protected List<String> getAllTests() {
		return this.generatedTestClasses;
	}

	// e.g. "src/main/java
	public abstract String getSourceFolder();

	public abstract Class<?> getFixture();

	public abstract boolean overwriteExistingFiles();

	public abstract String getBinaryFolder();

	public abstract String getOutputFolder();

	public abstract String getTestsuiteName();

	public abstract MockFramework getMockFramework();

}