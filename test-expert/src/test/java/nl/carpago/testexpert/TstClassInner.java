package nl.carpago.testexpert;

import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;



public class TstClassInner {

	private List<String> lijst;
	@SuppressWarnings("unused")
	private String voornaam;
	@SuppressWarnings("unused")
	private int leeftijd;
	@SuppressWarnings("unused")
	private String variableWithSetterForTest;
	private PersoonDAO persoonDao;
	
	@CreateUnittest
	public void test1() {

	}

	@CreateUnittest
	public void test2() {

	}

	@CreateUnittest
	public void test3() {

	}

	@Expect(in = { "3" }, out = "5")
	public int test4(int a) {
		return a + 2;
	}

	@CreateUnittest(in={"one", "two", "three"}, out="onetwothree")
	public String testForParameterNames(String one, String two, String three) {
		return "" + one + two + three;
	}

	public String testForParameterTypes(int one, String two, Person three) {

		return "string";
	}

	public String testForParameterTypesAndName(int one, String two, Person three) {
		return "string";
	}

	@CreateUnittest(in = "person", out = "anotherPerson")
	public Person testForGenerateFixturesForMethod(Person in) {

		return new Person("Jane Doe", 45);
	}

	@CreateUnittest(in = "3")
	public void testIn(int i) {

	}
	
	@CreateUnittest(in={"two", "three"}, out="vier")
	public int testMethodeForGetInAndOutForCreateUnitTest(int getal, int getal2) {
		return getal++;
	}
	
	@CreateUnittest(in={"3","4"}, out="34")
	public String testMethodeConcatStringForCreateUnittest(String een, String twee)
	{
		return een + twee;
	}
	
	@Expect(in={"two","three"}, out="vier")
	public int testMethodeForGetInAndOutForExpect(int getal, int getal2) {
		return getal++;
	}

	@CreateUnittest(in = { "?", "?", "?" })
	public void methodForCreateArguments(int firstUnknowArgument, String secondUnknowArgument, Person thirdUnknowArgument) {

	}

	@CreateUnittest
	public void tryCollabCall() {
		int length = lijst.size();

	}
	
	@CreateUnittest(in={"person","anotherPerson"}, out="anotherPerson")
	public Person testMethodeForCreateCallToTestMethod(Person person, Person anotherPerson) {
		return new Person("John Doe", 44);
	}
	
	@CreateUnittest(in="3", out="4")
	public int inc(int input) {
		return input + 1;
	}
	
	@CreateUnittest(in={"number"}, out="string")
	public String getNumber(int aNumber) {
		String result = persoonDao.getSofi(aNumber);
		
		return result;
	}
	
	@CreateUnittest(in="number", out="eenAnderPersoon")
	public Persoon getPersoon(int number) {
		return persoonDao.getPersoon(number);
	}
	
	@CreateUnittest(in={"number", "person"}, out="anotherPerson")
	public Person testWithMoreThanOneArgument(int aNumber, Person aPerson) {
		Person result = this.persoonDao.getPerson(aNumber, aPerson);
		
		return result;
	}
	
	@CreateUnittest(in={"number"}, out="person")
	public Person testWithLocalVariable(int aNumber) {
		Person localPerson = new Person("John Doe", 46);
		
		Person result = this.persoonDao.getPersonWithoutHelp(aNumber,  localPerson);
		
		return result;
	}
	
	
	@CreateUnittest(in={"number"}, out="person")
	public Person testWithLocalVariableWithHelp(int aNumber) {
		Person localPerson = new Person("John Doe", 46);
		
		Person result = this.persoonDao.getPerson(aNumber,  localPerson);
		
		return result;
	}
	
	
	@CreateUnittest(in={"number"}, out="number")
	public int testWithCallToSelf(int in) {
	//	return inner.inc(in);
		return 3;
	}
	
	@CreateUnittest(in="number", out="4")
	public int testHelperMethodForQuestionmark(int number) {
		
		int aNumber = 3;
		int result = this.persoonDao.getPersonWithQuestionmarksAnnotation(aNumber);
		
		return result;
	}
	
	@CreateUnittest(in="number")
	public void helperVoidMethod(int input) {
		this.persoonDao.voidmethod(input);
	}

	
	@CreateUnittest(in="number")
	public void addMe(int number) {
		int result = this.persoonDao.inc(number);
	}
	
	@CreateUnittest(in="number")
	public int addMeWithoutUsingReturn(int number) {
		return this.persoonDao.inc(number);
	}
	
	public void setVariableWithSetterForTest(String variableWithSetterForTest) {
		this.variableWithSetterForTest = variableWithSetterForTest;
	}

	
	protected void setPersoonDao(PersoonDAO persoonDao) {
		this.persoonDao = persoonDao;
	}
	
	
}