package nl.carpago.testexpert;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class SimpleServiceDemo {
	
	private String name = "simpleDemo";
	
	@CreateUnittest(out="simpleDemo")
	public String getName() {
		
		return name;
	}

}
