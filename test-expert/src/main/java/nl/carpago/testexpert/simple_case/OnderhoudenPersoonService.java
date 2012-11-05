package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class OnderhoudenPersoonService {
	
	@CreateUnittest(in="3", out="4")
	public String add(int in) {
		
		return ""+(in++);
	}
	

}
