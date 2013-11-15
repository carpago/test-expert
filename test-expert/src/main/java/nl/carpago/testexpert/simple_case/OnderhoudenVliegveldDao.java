package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.Person;
import nl.carpago.testexpert.annotation.Expect;

public class OnderhoudenVliegveldDao {
	
	@Expect(in="number", out="four")
	public int add(int number) {
		return number++;
	}
	
	@Expect(in="number", out="three")
	public String addAlt(int number)
	{
		int resultAsInt = this.add(number);
		resultAsInt--;
		String result = Integer.valueOf(resultAsInt).toString();
		
		return result;
	}
	
	@Expect(in="person", out="anotherPerson")
	public Person getPerson(Person in) {
		return new Person("Jane Doe", 45);
	}
}
