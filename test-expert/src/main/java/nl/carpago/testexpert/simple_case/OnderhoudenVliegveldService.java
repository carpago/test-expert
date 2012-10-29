package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.Person;
import nl.carpago.testexpert.annotation.CreateUnittest;


public class OnderhoudenVliegveldService {
	
	private OnderhoudenVliegveldDao onderhoudenVliegVelddao;

	public OnderhoudenVliegveldService() {
		// TODO Auto-generated constructor stub
	}
	@CreateUnittest(in="person", out="anotherPerson")
	public Person getPerson(Person person) {
		Person result = new Person("Jane Doe", 45);
		
		return result;
	}
	
	@CreateUnittest(in="number", out="vier")
	public String getStringForNumber(int eenGetal){
		int resultFromDao = onderhoudenVliegVelddao.add(eenGetal);
		
		return Integer.toString(resultFromDao);
	}

}
 