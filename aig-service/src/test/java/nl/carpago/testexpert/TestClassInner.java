package nl.carpago.testexpert;

import java.util.List;

import nl.carpago.unittestgenerator.annotation.CreateUnittest;
import nl.carpago.unittestgenerator.annotation.Expect;

public class TestClassInner {

	private List<String> lijst;

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

	@CreateUnittest
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

	@CreateUnittest(in = { "?", "?", "?" })
	public void methodForCreateArguments(int firstUnknowArgument, String secondUnknowArgument, Person thirdUnknowArgument) {

	}

	@CreateUnittest(in = { "?", "?" })
	public void methodForCreateArgumentsError(int firstUnknowArgument, String secondUnknowArgument, Person thirdUnknowArgument) {

	}

	@CreateUnittest
	public void tryCollabCall() {
		int length = lijst.size();

	}
}