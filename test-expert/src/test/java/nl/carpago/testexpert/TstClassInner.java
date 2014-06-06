package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;



public class TstClassInner {

	private List<String> lijst = new ArrayList<String>();
	@SuppressWarnings("unused")
	private String voornaam;
	@SuppressWarnings("unused")
	private int leeftijd;
	@SuppressWarnings("unused")
	private String variableWithSetterForTest;
	private PersonDAO personDao;
	private TstClassInner tstClassInner;
	
	@SuppressWarnings("unused")
	private PersonDAO unusedPeoplePersonDao;
	
	private PersonDAO onceUsedPeoplePersonDaoWithoutSetter;
	
	@CreateUnittest
	public void test1() {

	}
	
	/**
	 * This method is only used for increasing the coverage en to see if when
	 * a collab is the same as the tstclass will not be mocked.
	 */
	@CreateUnittest
	public void testOfInnerClassNietWordtGecollabbed()
	{
		@SuppressWarnings("unused")
		int leeftijd = tstClassInner.inc(3);
	}

	@CreateUnittest
	public void test2() {

	}

	/**
	 * this method should (and will) create reflections statements.
	 */
	@CreateUnittest
	public void testDaoWithoutSettersShouldCreateTestStatements() {
		onceUsedPeoplePersonDaoWithoutSetter.inc(3);
	}
	
	@CreateUnittest
	public void testForLijst()
	{
		this.lijst.add("string");
	}
	
	@CreateUnittest
	public void testForLijst2()
	{
		lijst.add("string");
	}

	@Expect(in = { "3" }, out = "5")
	public int test4(int a) {
		return a + 2;
	}

	@CreateUnittest(in={"one", "two", "three"}, out="onetwothree")
	public String testForParameterNames(String one, String two, String three) {
		return "" + one + two + three;
	}
	
	@CreateUnittest(in={"erwaseeneen", "erwaseentwee", "erwaseendrie"}, out="eraseeeneenerwaseeentweeerwaseeendrie")
	public String testForParameterNamesLiterals(String one, String two, String three) {
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
		String result = personDao.getSofi(aNumber);
		
		return result;
	}
	
	@CreateUnittest(in="number", out="eenAnderPeoplePerson")
	public PeoplePerson getPeoplePerson(int number) {
		return personDao.getPeoplePerson(number);
	}
	
	@CreateUnittest(in={"number", "person"}, out="anotherPerson")
	public Person testWithMoreThanOneArgument(int aNumber, Person aPerson) {
		Person result = this.personDao.getPerson(aNumber, aPerson);
		
		return result;
	}
	
	@CreateUnittest(in={"number"}, out="person")
	public Person testWithLocalVariable(int aNumber) {
		Person localPerson = new Person("John Doe", 46);
		
		Person result = this.personDao.getPersonWithoutHelp(aNumber,  localPerson);
		
		return result;
	}
	
	
	@CreateUnittest(in={"number"}, out="person")
	public Person testWithLocalVariableWithHelp(int aNumber) {
		Person localPerson = new Person("John Doe", 46);
		
		Person result = this.personDao.getPerson(aNumber,  localPerson);
		
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
		int result = this.personDao.getPersonWithQuestionmarksAnnotation(aNumber);
		
		return result;
	}
	
	@CreateUnittest(in={"'a'","'b'"}, out="'a'")
	public char testReturnFirstChar(char een, char twee)
	{
		return een;
	}
	
	@CreateUnittest(in="number")
	public void helperVoidMethod(int input) {
		this.personDao.voidmethod(input);
	}

	
	@CreateUnittest(in="number")
	public void addMe(int number) {
		int result = this.personDao.inc(number);
	}
	
	@CreateUnittest(in="number")
	public int addMeWithoutUsingReturn(int number) {
		return this.personDao.inc(number);
	}
	
	@CreateUnittest(out="<literal>")
	public String getLiteral()
	{
		return "<literal>";
	}
	
	@CreateUnittest(in={"3"}, post="leeftijd==3")
	public void setLeeftijd(int leeftijd)
	{
		this.leeftijd = leeftijd;
	}
	
	@CreateUnittest(in={"3"}, post="leeftijd.equals(3)")
	public void setLeeftijd2(int leeftijd)
	{
		this.leeftijd = leeftijd;
	}
	
	public void setVariableWithSetterForTest(String variableWithSetterForTest) {
		this.variableWithSetterForTest = variableWithSetterForTest;
	}

	
	public void setPersonDao(PersonDAO personDao) {
		this.personDao = personDao;
	}
}