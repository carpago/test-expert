package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.CreateUnittest;


public class OnderhoudenVliegveldService {
	
	private OnderhoudenVliegveldDao onderhoudenVliegVelddao;

	public OnderhoudenVliegveldService() {
		// TODO Auto-generated constructor stub
	}
	
	@CreateUnittest(in="number", out="vier")
	public String getStringForNumber(int eenGetal){
		int resultFromDao = onderhoudenVliegVelddao.add(eenGetal);
		
		return Integer.toString(resultFromDao);
	}
	
	
	@CreateUnittest(in="three", out="vier")
	public int getAddInt(int aNumber) {
		int result = aNumber ++;
		
		return result;
	}
	
	@CreateUnittest(in="three", out="vier")
	public int getForBooleanTest(int aNumber) {
		if(onderhoudenVliegVelddao.add(aNumber) == 3) {
			System.out.println("drie");
		}
		
		return 4;
	}
	

}
 