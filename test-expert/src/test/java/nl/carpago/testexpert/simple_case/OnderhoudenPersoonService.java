package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class OnderhoudenPersoonService {
	
	@CreateUnittest(in="number", out="vier")
	public int add(int in) {
		
		return in++;
	}
	

}
