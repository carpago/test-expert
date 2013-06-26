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

	@CreateUnittest(in={"3","2"},out="5")
	public String telLiteralsOp(int een, int twee)
	{
		return Integer.valueOf(een + twee).toString();
	}
	
	@CreateUnittest(in={"2","3"}, out="5")
	public int telLiteralOpInt(int een, int twee)
	{
		return een + twee;
	}
	
	@CreateUnittest(in={"2","3"}, out="23")
	public String concatString(String een, String twee)
	{
		return een+twee;
	}
	
	@CreateUnittest(in={"one","two"}, out="12")
	public String concatString2(String een, String twee)
	{
		return een+twee;
	}

	@CreateUnittest(in={"'a'","'b'"}, out="'a'")
	public char returnFirstChar(char een, char twee)
	{
		return een;
	}
	
	protected OnderhoudenVliegveldDao getOnderhoudenVliegveldDao() {
		return onderhoudenVliegveldDao;
	}
	
	
	
	
}
 