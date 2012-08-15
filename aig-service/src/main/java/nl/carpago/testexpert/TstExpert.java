package nl.carpago.testexpert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.carpago.unittestgenerator.annotation.CreateUnittest;
import nl.carpago.unittestgenerator.annotation.Expect;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.ParameterNamesNotFoundException;
import com.thoughtworks.paranamer.Paranamer;

/*
 To do:
 - apart project maken van de UnittestGenerator.
 - logging gebruikt nu de log4j.properties van aig-deployment. Dit dan ook aanpakken.
 - refactoren
 - verder testen met een niet te extenden class. Hiervoor PowerMock gebruiken.
 - een constructie als if(getmeldingdao gaat nog fout. hievoor parser inzetten).
 	- en ook System.out.println(...) m.a.w. ik MOET hiervoor structuur kunnen aanbrengen dus parsen.
 
 - (done), gewoon standaard trainwreck werkt met assignment, niet in if, ander issue hievoor gemaakt. 
   verder testen met een regel die uit een train-wreck bestaat.

 - nog zorgen dat alle projecten in 1 keer kunnen worden gevuld met unittesten. Wellicht vragen aan classloader waar de sources staan ???
 - zorgen dat er geen magical strings als src/main/java en src/test/generated-java zijn dan wel dit 
 vanaf buiten kunnen laten vullen. Wellicht valt dit samen met bovenstaande. (alle projecten in eens!).
 -  
 - let op: final classes kunniet niet (standaard) worden gemockt. Moet dus PowerMock inzetten.

 - TestSuite maken ???
 - maven plugin
 - doorlopen van alle sources?
 - applciatie context doorgeven, hoe??? via Java metadata gedaan. Nu nog evt. xml app context ?
 -  */

/*
 * 
 * done'
 * - (check) logging
 * - (done) volgens Dirk Groot moet EasyMock.verify(...) wel als je echt goed wilt testen als stub is het niet nodig.
 * (antwoord: nee lijkt geen goed idee. Is geen werk voor Fixture bouwer. lijkt autowiring van collabs en te testen class een idee ??? Weet niet zo.
 * (check: dit was een bug) testMethodeZonderReturnMetParam lijkt nu in ONderhoudenMeldingServiceImpl te werken terwijl hij verkeerde argument naar list doorgeeft...

 * (check) clonen kan alleen met serialiseerbare opjecten ?
 * (check) ReflectionBuilder.equals(.) van Commons gaat fout. Opgelost door XStream te gebruiken. Is dit opensource?
 * 	(check) lijkt goed (genoeg) zo. als dao niet geannoteerd is moet er toch een goed resultaat uit komen. Dan maar met parametername of zo...
 * ((check) nog zorgen voor unieke namen in unittest geefMelding(-) en geefMelding(overloaded) levert nu nog zelfde naam op
 lijkt iets als testGeefMeldingForBetrokkeneAndVoornaam() te moeten worden.
 * (nog testen) literals opgeven in CreateUnittest annotatie moet kunnen.
 * - (check: gedaan via EasyMock.createMock(...) implementors van interfaces maken.
 *  (check) constructor printen met meerdere argumenten
 - (check) als argumenten van dao en service niet gelijk zijn dan moet ik wat anders verzinnen. Anders kan de EasyMock.expect zo blijven.
 *  (check) literals (numeriek, String, boolean en char) afvangen zodat er geen var van wordt gemaakt in de call naar de collab.
 - (check) import statements.
 * (check) netter maken door code in een list of in een string in een instance variabele te bewaren
 -  (lijkt toch niet zinnig) alles in de service in de test plaatsen met een niet te maken var zoals voornaam in Meldingservice?
 - - annotations voor verwachte waarden. Eigenlijk zodat je in de code een bereik van geldige waarden kunt aangeven.  Nu via EasyMock.createMock ... maar werkt dit ?
 - - (check) AanleidingDao ipv aanleidingDao faalt
 - (check) Annotatie Creer veranderen naar @CreateUnittest
 -  (check) moet 'melding' bij EasyMock of zo nog clonen voor de correcte deep equality vergelijking.
 -  (check) nog testen.service methode zonder argumenten klapt nog.Vast vraag is of dat mag. Vraag is dat het in ieder geval uitgezocht moet worden.
 */

public class TstExpert {
	// doing
	// x achterhalen waarom, wat en hoe van het nummmer achter invokeinterface
	// en invokevirtual voor mocking
	/*
	 * x hieronder dus oplossen dat hij de hashMap.put ... to STring in:
	 * EasyMockExpect voor hashmap en EasyMockexpect voor STring::toStringmaakt
	 * /*
	 * 
	 * 
	 * public void welParameterGeenReturn(String in) { list.add("teststring");
	 * // 0 0:aload_0 // 1 1:getfield #56 <Field java.util.List
	 * nl.carpago.unittestgenerator
	 * .testers.UnittestGeneratorMethodesMetCollabs.list> // 2 4:ldc1 #58
	 * <String "teststring"> // 3 6:invokeinterface #60 <Method boolean
	 * java.util.List.add(java.lang.Object)> // 4 11:pop
	 * hashMap.put("teststring2", new Object()).toString(); // 5 12:aload_0 // 6
	 * 13:getfield #40 <Field java.util.HashMap
	 * nl.carpago.unittestgenerator.testers
	 * .UnittestGeneratorMethodesMetCollabs.hashMap> // 7 16:ldc1 #42 <String
	 * "teststring2"> // 8 18:new #3 <Class java.lang.Object> // 9 21:dup // 10
	 * 22:invokespecial #16 <Method void Object()> // 11 25:invokevirtual #44
	 * <Method java.lang.Object java.util.HashMap.put(java.lang.Object,
	 * java.lang.Object)> // 12 28:invokevirtual #50 <Method java.lang.String
	 * java.lang.Object.toString()> rloman@blackpanther-mint
	 * ~/data/EclipseProjects
	 * /aig/view_aig_2011.1/aig-service/bin/nl/carpago/unittestgenerator/testers
	 * $
	 */

	
	
	private static Logger logger = Logger.getLogger(TstExpert.class);

	private Class<?> classUnderTest;
	private Package pakkage;
	private Set<String> imports = new TreeSet<String>();

	private List<String> annotionsBeforeTestClass = new ArrayList<String>();

	private String header = "";

	private String body = "";

	private HashMap<String, Class<?>> fixtures = new HashMap<String, Class<?>>();
	
	private Set <String> collabs = new HashSet<String>();

	private ApplicationContext ctx;
	private Class<?> contextClass;

	private String footer = "";

	// constants
	private final String EMPTY_STRING = "";
	private final String QUESTION_MARK = "?";
	private final String ASTERISK = "*";
	private final String RESULTFROMMETHOD = "resultFromMethod";

	public static void main(String args[]) throws IOException, ClassNotFoundException {
		logger.debug("entering main");
		List<String> lijstMetAlleJavaFilesUitProject = null;

		try {
			lijstMetAlleJavaFilesUitProject = findAllJavaFiles();
		} catch (IOException e) {
			logger.fatal(e.getMessage());
		}

		// Class<?> classUnderTest =
		// nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl.class;
		// rloman: hier nog ff uitzoeken of dit sneller en / of mooier kan. heb
		// dit gisterenavond ff snel in elkaar geklust. nog ff over nadenken.
		Class<?> classUnderTest = null;
		for (String classFile : lijstMetAlleJavaFilesUitProject) {
			//logger.debug("processing class " + classFile);

			//classUnderTest = nl.belastingdienst.aig.melding.OnderhoudenMeldingServiceImpl.class;
			classUnderTest = Class.forName(classFile); // 
					
			List<Method> methods = getMethodsWithAnnotationTestMe(classUnderTest);
			if (methods != null && !methods.isEmpty()) {

				TstExpert generator = new TstExpert(classUnderTest, Fixtures.class);

				generator.generateTestClass();

				generator.writeFile();
			}
		}
		logger.debug("leaving main");
	}

	// rloman: refactoren. en zo ...
	public void writeFile() throws FileNotFoundException {
		logger.debug("entering writeFile");
		String fileName = "src/test/generated-test/" + this.classUnderTest.getName().replaceAll("\\.", "/") + "Test.java";
		String directoryName = "src/test/generated-test/" + this.classUnderTest.getPackage().getName().replaceAll("\\.", "/");// +

		logger.debug("creating directory " + directoryName);
		File directory = new File(directoryName);
		directory.mkdirs();
		logger.debug("finished creating directory " + directoryName);

		File file = new File(fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream po = new PrintStream(file);
		po.print(this.codeGen());

		logger.info(("Written '" + directoryName + this.classUnderTest.getSimpleName() + "Test'"));
		logger.debug("leaving writeFile");
	}

	public static List<String> findAllJavaFiles() throws IOException {
		logger.debug("entering");

		List<String> lines = new LinkedList<String>();

		ProcessBuilder builder = new ProcessBuilder("find", "./src/main/java", "-name", "*.java");
		Process process = builder.start();

		InputStream stream = process.getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader bufferReader = new BufferedReader(reader);

		String line = null;
		while ((line = bufferReader.readLine()) != null) {
			logger.debug("processing line " + line);
			line = line.replaceAll("./src/main/java/", "");
			line = line.replaceAll("/", ".");
			line = line.replaceAll(".java", "");
			lines.add(line);
		}

		logger.debug("leaving");

		return lines;
	}

	public TstExpert(Class<?> classUnderTest, Class<?> context) {
		logger.debug("entering constructor");
		this.classUnderTest = classUnderTest;
		this.contextClass = context;
		this.init();
		logger.debug("leaving constructor");
	}

	public void init() {
		logger.debug("entering");

		this.addPackage();

		logger.debug("setting ApplicationContext");
		this.ctx = new AnnotationConfigApplicationContext(this.contextClass);

		this.checkAndAddImport(org.easymock.EasyMock.class);
		this.checkAndAddImport(org.junit.Before.class);
		this.checkAndAddImport(org.junit.Test.class);
		this.checkAndAddImport(nl.carpago.testexpert.AbstractTstExpert.class);
		this.checkAndAddImport(this.contextClass);

		logger.debug("leaving");
	}

	public void generateTestClass() {
		logger.debug("enter");

		generateAnnotationsForSpringTest();

		generateHeader();

		generateFixturesForEntireClass();

		addCodeLn();
		addCodeLn("\t// class under test");
		addCodeLn("\tprivate " + this.classUnderTest.getSimpleName() + " "
				+ WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + ";");

		generateCollaboratingClasses();
		generateSetup();
		generateMethodsWithAnnotationTestMe();

		generateGettersForCollaborators();

		generateFooter();
		logger.debug("leave");

	}

	private void generateFixturesForEntireClass() {
		logger.debug("enter");
		List<Method> methodes = getMethodsWithAnnotationTestMe(this.classUnderTest);
		for (Method methode : methodes) {
			generateFixturesForMethod(methode);
		}
		logger.debug("leave");
	}

	private void generateHeader() {
		logger.debug("enter");
		this.header += "public class " + this.classUnderTest.getSimpleName() + "Test extends AbstractTstExpert { \n";
		logger.debug("leave");
	}

	private void generateFooter() {
		logger.debug("enter");
		this.footer += "}";
		logger.debug("leave");
	}

	private void generateAnnotationsForSpringTest() {
		logger.debug("enter");

		this.checkAndAddImport(org.junit.runner.RunWith.class);
		this.checkAndAddImport(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class);
		this.checkAndAddImport(org.springframework.test.context.ContextConfiguration.class);
		this.checkAndAddImport(org.springframework.beans.factory.annotation.Autowired.class);
		// ???? fixtures ??? wordt toch geinsert door klant ????
		// this.addImport(classToImport)

		this.annotionsBeforeTestClass.add("@RunWith(SpringJUnit4ClassRunner.class)");
		this.annotionsBeforeTestClass.add("@ContextConfiguration(classes={Fixtures.class})");

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
	private void generateExpectAndReplayForCollaboratorsOfMethod(Method methodeArgument) throws IOException {
		logger.debug("enter");

		String[] inputParametersViaAnnotations = methodeArgument.getAnnotation(
				nl.carpago.unittestgenerator.annotation.CreateUnittest.class).in();
		logger.info("methode " + methodeArgument + " is annotated with in:" + Arrays.asList(inputParametersViaAnnotations));

		Set<String> localVars = new HashSet<String>();
		// via javap
		// ProcessBuilder builder = new ProcessBuilder("javap", "-c",
		// "bin/nl/belastingdienst/aig/melding/OnderhoudenMeldingServiceImpl");

		// creer String path to .class file
		String fileName = "bin/" + this.classUnderTest.getName().replaceAll("\\.", "/");

		// via jad
		ProcessBuilder builder = new ProcessBuilder("jad", "-af", "-p", fileName);

		// "bin/nl/belastingdienst/aig/melding/OnderhoudenMeldingServiceImpl");
		Process process = builder.start();

		InputStream stream = process.getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader bufferReader = new BufferedReader(reader);
		LinkedList<String> lines = new LinkedList<String>();

		String line = null;
		while ((line = bufferReader.readLine()) != null) {
			lines.add(line);
		}

		// create here the String from the method
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
		
		Pattern p = Pattern.compile(regexp);
		// get a matcher object
		//int delta = 1;
		String linesLocal = null;
		Set <String> mocked = new HashSet<String>();
		List <Integer> gemockteRegelsUitSource = new ArrayList<Integer>();
		outer: for (int i = 0; i < lines.size(); i = i + 1) {
			linesLocal = lines.get(i);
			Matcher m = p.matcher(linesLocal);
			logger.debug(">" + linesLocal + "<");
			int count = 0;
			if (m.find()) {
				inner: for (int j = i; j < lines.size(); j++) {
					linesLocal = lines.get(j);
					if (linesLocal.equals("    }")) {
						logger.debug("breaking:" + linesLocal);
						break outer;
					}
					// x gaat ergens een Object newObject aanmaken in
					// Unittestgeneratormetcollabstest en dat gaat niet goed.
					if (linesLocal.indexOf("invokeinterface") > -1 || linesLocal.indexOf("invokevirtual") > -1) {
						logger.debug("found:" + linesLocal);
						Pattern patterntje = Pattern.compile("\\(|,|\\)>");
						Scanner s = new Scanner(linesLocal).useDelimiter(patterntje);
						String prelude = s.next();
						String collabAndInvokee = null;
						Scanner collabScanner = new Scanner(prelude).useDelimiter(" ");
						while (collabScanner.hasNext()) {
							collabAndInvokee = collabScanner.next();
						}
						logger.debug(collabAndInvokee);

						String[] collabs = collabAndInvokee.split("\\.");
						String collab = EMPTY_STRING;

						for (int collabLoop = 0; collabLoop < collabs.length - 1; collabLoop++) {
							collab += collabs[collabLoop] + ".";
						}
						collab = collab.substring(0, collab.length() - 1);
						String invokee = collabs[collabs.length - 1];

						logger.debug("collab=" + collab);
						logger.debug("invokee=" + invokee);

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
									try {
										parameter = this.getPrimitiveType(param);
									} catch (InvalidArgumentException iae) {
										logger.error("zowel geen class alsook geen primitive " + param);
										iae.printStackTrace();

										throw iae;
									}

								}
								params.add(parameter);
							}
						}

						Class<?>[] parametersVoorInvokee = new Class<?>[params.size()];
						parametersVoorInvokee = (Class[]) params.toArray(parametersVoorInvokee);
						// System.out.println("collab:" + collab);
						// System.out.println("invokee:" + invokee);
						// System.out.println("parameters:" + params);
						// now I can get the signature of the to be colled
						// method of the collab
						// and know the EXACT returntype ... Not just List but
						// also List <Melding> that's
						// why I needed this verbose code.
						Method method = null;
						try {
							// rloman: hier collab nog automatisch verlengen
							// door gebruik te maken
							// van jad met zijn FQCN - mogelijkheid zodat
							// MeldingDAO wordt:
							// nl.belastingdienst.aig.dao.MeldingDao
							method = Class.forName(collab).getMethod(invokee, parametersVoorInvokee);
							if (collab.equals(this.classUnderTest.getName())) {
								continue inner;
							}
							
							// method = Class.forName(collab).getmet
						} catch (SecurityException e) {
							logger.error(e);
						} catch (NoSuchMethodException e) {
							logger.error(e);
						} catch (ClassNotFoundException e) {
							logger.error(e);
						}
						logger.debug("methode to collab is:" + method);
						logger.debug("methods return type:" + method.getReturnType());
						logger.debug("methods generic return type:" + method.getGenericReturnType());
						// tjakkaa: hier heb ik dus het generieke type.
						
						for (int k = j - 1; k > i; k--) {
							String regelHoger = lines.get(k);
							
							if (regelHoger.indexOf(invokee) > -1) {
								
								//if (regelHoger.indexOf(invokee) > -1) {
								
								if(gemockteRegelsUitSource.contains(k)) {
									continue inner; // already done ...
								}
								else {
									gemockteRegelsUitSource.add(k);
								}

								InvokeDTO invokeDTO = new InvokeDTO(regelHoger);
								if(this.collabs.contains(invokeDTO.getCollab()) || this.isCallerForCollab(invokeDTO.getCollab())) {
									//continue processing this collab below ...
								}
								else {
									continue inner;
								}
								
								String construction = invokeDTO.getCollabMethodParams();
								if(mocked.contains(construction)) {
									//continue inner;
								}
								mocked.add(construction);
								
								// maak nu een lijst van beiden.
								List<String> testMethodeZijnParams = new ArrayList<String>(Arrays.asList(this
										.getParameterNamesForMethod(methodeArgument)));
								List<String> collabZijnParams = invokeDTO.getParams();

								String[] in = this.getInAnnotationsForMethod(method);
								String out = this.getOutAnnotationForMethod(method);

								// En neem de wiskundige A-B
								logger.debug(method);
								logger.debug("returns:" + out);

								for (int n = 0; n < collabZijnParams.size(); n++) {
									String element = collabZijnParams.get(n);
									if (!testMethodeZijnParams.contains(element) && !(isLiteral(element))
											&& !(localVars.contains(element))) {
										localVars.add(element);

										// hier de code uit service halen.
										// hier probeer ik of ik een annotatie
										// uit de dao kan halen van het
										// betreffende element.
										// Zo niet dan onderstaande code. (via
										// constructor dus)

										/*
										 * deze code hieronder haalt de
										 * annotatie op van de collab en kijkt
										 * of hij daar wat mee kan als de
										 * variable niet via de te testen
										 * methode binnen komt. Refactoring is
										 * gewenst .... :-))
										 */
										if (in != null && in.length != 0) {
											String annotatieElement = in[n];
											// element = annotatieElement;
											if (!(QUESTION_MARK.equals(annotatieElement) || ASTERISK.equals(annotatieElement))) {
												if (!this.isLiteral(annotatieElement)) {
													addFixture(annotatieElement);
												}
												// moet "aap" in
												// call naar list niet vervangen
												// door annotatie.
												// System.out.println("Regel 1: vervang "+
												// element
												// +" door "+annotatieElement);
												construction = construction.replaceAll(element, annotatieElement); // rloman

												// rloman dit lijkt te moeten!
												// construction =
												// construction.replaceAll("[(,]"+element+"[,)]",
												// annotatieElement);
												// beter gezegd: dit lijkt te
												// kunnen door hierboven in
												// paramsVanCollab te tweaken!
											} else {
												if (!this.isLiteral(element)) {
													Class<?> parameterType = method.getParameterTypes()[n];
													addCode("\t\t" + parameterType.getSimpleName() + " " + element + " = ");
													addCode(generateConstructorForClass(parameterType));
													addCodeLn(";");
												}

											}
											// klaar want dan komt hij gewoon
											// uit de appcontext....
										} else {
											// dan moet er maar gewoon via de
											// constructor een lokaal object
											// worden gemaakt.
											try {
												if (!this.isLiteral(element)) {
													Class<?> parameterType = method.getParameterTypes()[n];
													addCode("\t\t" + parameterType.getSimpleName() + " " + element + " = ");
													addCode(generateConstructorForClass(parameterType));
													addCodeLn(";");
												}

											} catch (IndexOutOfBoundsException iobe) {
												logger.error("INdexOutOfBoundException for method:" + method.getName() + ", index:"
														+ n);
											}

										}
									} else {
										try { // rloman refactoren.

											// hieronder lijkt het te kloppen
											// dat indien ik de collab aanroep
											// met een literal (bijv.
											// list.add("aap")
											// dat hij dan de variabele niet
											// gebruikt.
											// rloman: nog testen ...!!!
											if (!this.isLiteral(collabZijnParams.get(n))) {
												construction = construction.replaceAll(element, inputParametersViaAnnotations[n]);
											}

										} catch (IndexOutOfBoundsException iobe) {
											logger.error("IndexOutOfBoundsException in replacing elemnt with annotation.");
										}
									}
								}
								//addCodeLn();
								String returnFromMethod = null;
								this.checkAndAddImport(org.easymock.EasyMock.class);
								if (method.getReturnType().toString().equals("void")) {
									addCodeLn("\t\t" + construction + ";");
								} else {
									if (out != null) {
										returnFromMethod = out;
									} else {
										// refactoren ... generateConstructor...
										// moet String returnen returnFromMethod
										// =
										// temp
										// returnFromMethod =
										// method.getGenericReturnType().getClass().getCanonicalName();
										returnFromMethod = generateConstructorForClass(method.getReturnType());
									}
									addCode("\t\tEasyMock.expect(" + construction + ").andReturn(");

									if (returnFromMethod != null) {
										String cloneString = EMPTY_STRING;
										if (!this.isLiteral(returnFromMethod)) {
											try {
												this.getPrimitiveType(method.getReturnType().toString());
												cloneString += returnFromMethod;

											} catch (RuntimeException rte) { // rloman
																				// :-((
												cloneString += "(" + method.getReturnType().getSimpleName() + ") this.cloneMe("
														+ returnFromMethod + ")";

											}
										} else {
											cloneString = returnFromMethod;
										}

										addCode(cloneString);

									} else {
										addCode(generateConstructorForClass(method.getReturnType()));
									}
									addCodeLn(");");

								}
								String replayClass = invokeDTO.getCollab();// new
																			// Scanner(construction).useDelimiter("\\.").next().trim();
								// addCodeLn("\t\tEasyMock.replay(" + replayClass + ");");
								//addCodeLn();
								continue inner;

							}
						}
					}

				}
			}

		}
		logger.debug("leave");
	}

	
	private boolean isCallerForCollab(String aCollabKandidate) {
		
		for(String element : this.collabs) {
			if(aCollabKandidate.toLowerCase().indexOf(element.toLowerCase()) > -1) {  // kandidate is a get or set for the real collab.
				return true;
			}
		}
		
		return false;
		
	}

	private void generateReplays() {
		addCodeLn();
		for(String collab : this.collabs) {
			addCodeLn("\t\tEasyMock.replay("+collab+");");
		}
	}
	
	private void generateVerifies() {
		addCodeLn();
		for(String collab : this.collabs) {
			addCodeLn("\t\tEasyMock.verify("+collab+");");
		}
	}
	
	public String[] getInAnnotationsForMethod(Method method) {

		Annotation annotatie = method.getAnnotation(CreateUnittest.class);
		String[] in = null;
		if (annotatie == null) {
			annotatie = method.getAnnotation(nl.carpago.unittestgenerator.annotation.Expect.class);
		}
		if (annotatie instanceof CreateUnittest) {
			in = ((CreateUnittest) annotatie).in();
		}
		if (annotatie instanceof Expect) {
			in = ((Expect) annotatie).in();
		}

		return in;
	}

	//@CreateUnittest(in={"methode"},out="methodeOutAnnotations")
	public String getOutAnnotationForMethod(Method method) {
		Annotation annotatie = method.getAnnotation(CreateUnittest.class);
		String out = null;
		if (annotatie == null) {
			annotatie = method.getAnnotation(nl.carpago.unittestgenerator.annotation.Expect.class);
		}
		if (annotatie instanceof CreateUnittest) {
			out = ((CreateUnittest) annotatie).out();
		}
		if (annotatie instanceof Expect) {
			out = ((Expect) annotatie).out();
		}

		return out;
	}

	
	public boolean isLiteral(String literalOrVariablename) {
		logger.debug("enter");

		if (literalOrVariablename == null || EMPTY_STRING.equals(literalOrVariablename.trim())) {
			logger.info("String " + literalOrVariablename + " is considered as a literal!");
			return true;
		}

		// en deze moet als instance var om overbodige GC te voorkomen
		// zou ook kunnen met eventuele char waarden ??? En anders uitbreiden
		// met alle andere toetenbordkeys die illegaal zijn.
		String[] illegalForVariable = new String[] { "\"", "'", "(", ")", "-", ".", "+", "!", "@", "#", "%", "^", "&", "*", "=",
				" " };

		for (String element : illegalForVariable) {
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
			long l = Long.parseLong(literalOrVariablename);
		} catch (NumberFormatException nfe) {
			logger.info("String " + literalOrVariablename + " is considered as a literal!");
			return false;
		}

		logger.info("String " + literalOrVariablename + " is NOT considered as a literal!");

		return true;
	}

	public String generateConstructorForClass(Type t) {
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
			addCode("'a'");
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
					logger.info(e + ", default constructor failed so trying first (real) constructor");
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

	public String generateSomethingForInterface(Class<?> eenInterface) {
		logger.debug("enter");

		this.checkAndAddImport(eenInterface);

		logger.debug("leave");

		return "EasyMock.createMock(" + eenInterface.getSimpleName() + ".class)";
	}

	private void generateCollaboratingClasses() {
		logger.debug("enter");
		Field[] fields = this.classUnderTest.getDeclaredFields();
		if (fields.length > 0) {
			addCodeLn();
			addCodeLn("\t// collaborating classes");
		}
		for (Field field : fields) {
			if (!(this.isPrimitive(field.getType().toString()))) {
				this.checkAndAddImport(field.getType());
			}
			addCodeLn("\tprivate " + field.getType().getSimpleName() + " " + WordUtils.uncapitalize(field.getName()) + ";");
			this.collabs.add(WordUtils.uncapitalize(field.getName()));

		}
		addCodeLn();

		logger.debug("leave");
	}

	private void generateGettersForCollaborators() {
		logger.debug("enter");
		Field[] fields = this.classUnderTest.getDeclaredFields();

		for (Field field : fields) {
			addCodeLn();
			addCodeLn("\tpublic " + field.getType().getSimpleName() + " get" + WordUtils.capitalize(field.getName()) + "(){");
			addCodeLn("\t\treturn this." + WordUtils.uncapitalize(field.getName()) + ";");
			addCodeLn("\t}");
		}

		logger.debug("leave");
	}

	private void generateSetup() {
		logger.debug("enter");
		addCodeLn("\t@Before");
		// hoeft niet meer met JUNit 4 ? System.out.println("\t@Override");
		addCodeLn("\tpublic void setUp() {");
		// initialize the class under test
		addCode("\t\tthis." + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + " = ");
		addCode(generateConstructorForClass(this.classUnderTest));
		addCodeLn(";");

		// init the collaborating classes
		for (Field field : this.classUnderTest.getDeclaredFields()) {
			addCodeLn();
			if (!(this.isPrimitive(field.getType().getName()))) {
				addCodeLn("\t\tthis." + WordUtils.uncapitalize(field.getName()) + " = EasyMock.createMock("
						+ field.getType().getSimpleName() + ".class);");

				// probeer de setter te vinden. Indien dit niet kan dan niet ...
				// dan lijkt het niet nodig.
				try {
					String fieldNameFirstLetterCap = WordUtils.capitalize(field.getName());
					Method setter = this.classUnderTest.getMethod("set" + fieldNameFirstLetterCap, field.getType());
					addCodeLn("\t\tthis." + WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + "." + setter.getName()
							+ "(" + "this." + WordUtils.uncapitalize(field.getName()) + ");");

				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) { // rloman: refactoren. Houd
													// niet van exceptions
					addCodeLn("\t\tsetFieldThroughReflection(" + WordUtils.uncapitalize(this.classUnderTest.getSimpleName())
							+ ", \"" + field.getName() + "\", this." + WordUtils.uncapitalize(field.getName()) + ");");
					// only in this case ... this exception is valid
				}
			}
		}

		addCodeLn("\t}");

		logger.debug("leave");

	}

	public void generateMethodsWithAnnotationTestMe() {
		logger.debug("enter");
		List<Method> methodes = getMethodsWithAnnotationTestMe(this.classUnderTest);
		for (Method methode : methodes) {

			String testMethodName = EMPTY_STRING;
			Class<?>[] parameterTypes = methode.getParameterTypes();

			testMethodName += "test" + WordUtils.capitalize(methode.getName());
			String[] parameterNames = this.getParameterNamesForMethod(methode);

			if (parameterNames != null && parameterNames.length > 0) {
				testMethodName += "For" + parameterTypes[0].getSimpleName() + WordUtils.capitalize(parameterNames[0]);
			}

			for (int i = 1; i <= parameterNames.length - 1; i++) {
				testMethodName += "And" + parameterTypes[i].getSimpleName() + WordUtils.capitalize(parameterNames[i]);
			}

			addCodeLn();
			addCodeLn("\t@Test");
			addCode("\tpublic void " + testMethodName + "(");

			addCodeLn("){ ");

			if (methode.getParameterTypes().length != 0) {
				generateCreerParametersVoorTestmethode(methode);
			}

			try {
				generateExpectAndReplayForCollaboratorsOfMethod(methode);
			} catch (IOException e) {
				logger.error(e);
			}

			generateReplays();
			
			generateCallToTestMethod(methode);
			
			generateVerifies();
			
			if (!"void".equals(methode.getReturnType().toString())) {
				generateAssertStatements(methode);
			}
			addCodeLn("\t}");
		}
		logger.debug("leave");
	}

	private void generateCallToTestMethod(Method methode) {
		logger.debug("enter");

		String parameterString = EMPTY_STRING;

		addCodeLn();
		addCode("\t\t");

		if (!"void".equals(methode.getReturnType().toString())) {
			this.checkAndAddImport(methode.getReturnType());
			addCode(methode.getReturnType().getSimpleName() + " " + this.RESULTFROMMETHOD + " = ");
		}
		addCode(WordUtils.uncapitalize(this.classUnderTest.getSimpleName()) + "." + methode.getName() + "(");

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

		addCode(parameterString);
		addCode(");");

		addCodeLn();

		logger.debug("leave");
	}

	private void generateAssertStatements(Method method) {
		logger.debug("enter");
		String expected = method.getAnnotation(CreateUnittest.class).out();
		String actual = this.RESULTFROMMETHOD;
		addCode("\n\t\t");
		if (this.isPrimitive(method.getReturnType().toString())) {
			addCodeLn("assertTrue(\"variable '" + expected + "' and '" + actual + "' should be equal!\", " + expected + " == "
					+ actual + ");");
		} else {
			addCodeLn("assertTrue(\"variable '" + expected + "' and '" + actual
					+ "' should be deep equal!\", this.checkForDeepEquality(" + expected + ", " + actual + "));");
		}

		logger.debug("leave");
	}

	private void generateFixturesForMethod(Method methode) {
		logger.debug("enter");

		CreateUnittest annotation = (CreateUnittest) methode
				.getAnnotation(nl.carpago.unittestgenerator.annotation.CreateUnittest.class);
		String[] inputFixtures = annotation.in();
		String outputFixture = annotation.out();

		List<String> fixturesAll = new LinkedList<String>(Arrays.asList(inputFixtures));
		if (!EMPTY_STRING.equals(outputFixture)) {
			fixturesAll.add(outputFixture);
		}

		for (String fixture : fixturesAll) {
			if (this.isLiteral(fixture) || QUESTION_MARK.equals(fixture) || ASTERISK.equals(fixture)) {
				continue;
			}
			addFixture(fixture);
		}

		logger.debug("leave");
	}

	private void addFixture(String fixture) {
		logger.debug("enter");

		Object o = ctx.getBean(fixture);
		this.checkAndAddImport(o.getClass());
		this.fixtures.put(fixture, o.getClass());

		logger.debug("leave");
	}

	private String codeGenFixtures() {
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

	public static List<Method> getMethodsWithAnnotationTestMe(Class clazz) {
		logger.debug("enter");
		List<Method> result = new ArrayList<Method>();
		for (Method methode : clazz.getDeclaredMethods()) {
			if (methode.getAnnotation(nl.carpago.unittestgenerator.annotation.CreateUnittest.class) != null) {
				result.add(methode);
			}
		}

		logger.debug("leave");

		return result;
	}

	public void generateCreerParametersVoorTestmethode(Method methode) {
		logger.debug("enter");

		String[] parameterNames = null;

		parameterNames = this.getParameterNamesForMethod(methode);

		String[] inputParametersViaAnnotatie = methode.getAnnotation(nl.carpago.unittestgenerator.annotation.CreateUnittest.class)
				.in();

		if (parameterNames.length != inputParametersViaAnnotatie.length) {
			logger.fatal("Annotation and parameters are ordinal not equal!");
			throw new RuntimeException("Annotation and parameters are ordinal not equal!");
		}

		Class<?>[] parameterTypes = methode.getParameterTypes();

		for (int i = 0; i < parameterNames.length; i++) {
			// if ? than create via constructor. if * than try first via
			// appcontext else via constructor through name of the variable.
			if (QUESTION_MARK.equals(inputParametersViaAnnotatie[i])
					|| (ASTERISK.equals(inputParametersViaAnnotatie[i]) && !this.fixtures.containsKey(parameterNames[i]))) {
				addCode("\t\t" + parameterTypes[i].getSimpleName() + " " + parameterNames[i] + " = ");
				addCode(generateConstructorForClass(parameterTypes[i]));
				addCodeLn(";");
			}

		}
		logger.debug("leave");
	}

	public String[] getParameterNamesForMethod(Method method) {
		logger.debug("methode:" + method);

		// ??Paranamer paranamer = new BytecodeReadingParanamer();
		Paranamer paranamer = new AdaptiveParanamer();
		String[] result = null;
		String parameter = null;
		String[] parameterNames = null;
		List<String> tempListToCreateArray = new ArrayList<String>();

		try {
			result = paranamer.lookupParameterNames(method, true);
		} catch (ParameterNamesNotFoundException npe) {
			// else use the names of the type camelcased.
			Class<?>[] parameterClasses = method.getParameterTypes();
			int counter = 0;
			for (Class<?> parameterClass : parameterClasses) {
				parameter = WordUtils.uncapitalize(parameterClass.getSimpleName())+ ++counter;
				tempListToCreateArray.add(parameter);
			}
			result = tempListToCreateArray.toArray(new String[tempListToCreateArray.size()]);
		}

		logger.debug("leave");

		return result;
	}

	// in: int, boolean, char ... out: Integer, Boolean Character
	public Class<?> getPrimitiveType(String baseType) {
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

	private boolean isPrimitive(String type) {
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

	private void checkAndAddImport(Class<?> classToImport) {
		logger.debug("enter");
		if (this.isPrimitive(classToImport.getName())) {
			return;
		}
		if(classToImport.isArray()) {
			
			return; // voor later...
			// rloman: zoiets als dit doen ...
			/*
			Object array = Array.newInstance(classToImport, 1);
			List lijst = Arrays.asList(array);
			Object object = lijst.get(0);
			System.out.println("object is:"+object.getClass());
			classToImport = object.getClass();
			*/
		}
		if ("java.lang".equals(classToImport.getPackage().getName())
				|| this.pakkage.getName().equals(classToImport.getPackage().getName())) {
			return;
		} else {
			this.imports.add(classToImport.getName());
		}
		logger.debug("leave");
	}

	private void addCode(String code) {
		logger.debug("enter");
		logger.debug("Adding code " + code);

		this.body += code;

		logger.debug("leave");
	}

	private void addCodeLn(String code) {
		this.addCode(code + "\n");
	}

	private void addCodeLn() {
		this.addCode("\n");
	}

	public void printCode() {
		System.out.println(this.codeGen());
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

	private String codeGenHeader() {
		return this.header;
	}

	private String codeGenBody() {
		return this.body;
	}

	private String codeGenFooter() {
		return this.footer;
	}

	private String codeGenAnnotationsForSpringTest() {
		logger.debug("enter");
		String result = EMPTY_STRING;

		for (String annotatie : this.annotionsBeforeTestClass) {
			result += annotatie + "\n";
		}

		logger.debug("leave");

		return result;
	}

	private String codeGenPackage() {

		String result = EMPTY_STRING;
		result += this.pakkage + ";\n\n";

		return result;
	}

	private String codeGenImports() {
		logger.debug("enter");
		String result = EMPTY_STRING;

		for (String importLine : this.imports) {

			result += "import " + importLine + ";\n";
		}
		result += "\n";

		logger.debug("leave");

		return result;
	}
}