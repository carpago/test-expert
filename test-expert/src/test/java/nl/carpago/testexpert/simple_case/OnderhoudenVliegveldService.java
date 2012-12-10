package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.Person;
import nl.carpago.testexpert.annotation.CreateUnittest;


public class OnderhoudenVliegveldService {
	
	private OnderhoudenVliegveldDao onderhoudenVliegveldDao;

	@CreateUnittest(in="number", out="vier")
	public String getStringForNumber(int eenGetal){
		int resultFromDao = onderhoudenVliegveldDao.add(eenGetal);
		
		return Integer.toString(resultFromDao);
	}
	
	
	@CreateUnittest(in="number", out="three")
	public String getStringForNumberAlt(int aNumber) {
		String result = onderhoudenVliegveldDao.addAlt(aNumber);
		
		return result;
	}
	
	@CreateUnittest(in="person", out="anotherPerson")
	public Person getPerson(Person aPerson)
	{
		Person janeDoe = onderhoudenVliegveldDao.getPerson(aPerson);
		
		return janeDoe;
	}
	

	
	protected OnderhoudenVliegveldDao getOnderhoudenVliegveldDao() {
		return onderhoudenVliegveldDao;
	}
	
	
	
	
}
 