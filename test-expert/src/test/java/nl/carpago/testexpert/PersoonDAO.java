package nl.carpago.testexpert;

import nl.carpago.testexpert.annotation.Expect;

public class PersoonDAO {
	
	@Expect(in="number", out="string")
	public String getSofi(int number) {
		return Integer.toString(number);
	}
	
	@Expect(in="number", out="eenAnderPersoon")
	public Persoon getPersoon(int aNumber) {
		return new Persoon(44, "John Doe");
	}
	
	@Expect(in={"number", "person"}, out="anotherPerson")
	public Person getPerson(int aNumber, Person aPerson) {
		return new Person("John Doe", 45);
	}
	
	public Person getPersonWithoutHelp(int aNumber, Person aPerson) {
		return new Person("John Doe", 99);
	}
	
	@Expect(in={"*"}, out="4")
	public int getPersonWithQuestionmarksAnnotation(int numbertje) {
		return numbertje++;
	}

	public void voidmethod(int input) {
		System.out.println("input:"+input);
		
	}
	
	@Expect(in="3", out="number")
	public int inc(int input) {
		return input++;
	}
}