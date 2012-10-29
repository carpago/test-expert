package nl.carpago.testexpert.simple_case;

import nl.carpago.testexpert.annotation.Expect;

public class OnderhoudenVliegveldDao {
	
	@Expect(in="number", out="vier")
	public int add(int number) {
		return number++;
	}

}
