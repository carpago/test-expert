package nl.carpago.testexpert;

import nl.carpago.testexpert.annotation.CreateUnittest;

public class TstClassInnerWithError {

	@CreateUnittest(in = { "?", "?" })
	public void methodForCreateArgumentsError(int firstUnknowArgument, String secondUnknowArgument, Person thirdUnknowArgument) {

	}
}
