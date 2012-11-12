package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.CreateUnittest;


public class OnderhoudenVliegveldService {
	
	private OnderhoudenVliegveldDao onderhoudenVliegveldDao;

	public OnderhoudenVliegveldService() {
		// TODO Auto-generated constructor stub
	}
	
	@CreateUnittest(in="number", out="vier")
	public String getStringForNumber(int eenGetal){
		int resultFromDao = onderhoudenVliegveldDao.add(eenGetal);
		
		return Integer.toString(resultFromDao);
	}
	
	protected OnderhoudenVliegveldDao getOnderhoudenVliegveldDao() {
		return onderhoudenVliegveldDao;
	}
	
	
	
	
}
 