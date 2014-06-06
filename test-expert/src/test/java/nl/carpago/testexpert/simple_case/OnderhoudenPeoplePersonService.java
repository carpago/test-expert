package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class OnderhoudenPeoplePersonService {
	
	@CreateUnittest(in="3", out="4")
	public String add(int in) {
		
		return ""+(++in);
	}
	
	@CreateUnittest(in="true", out="false")
	public boolean invert(boolean bool) {
		return !bool;
	}
	
	@CreateUnittest(in="3", out="4")
	public int addString(int in) {
		
		int result = Integer.valueOf(++in);
		
		return result;
		
	}
}
