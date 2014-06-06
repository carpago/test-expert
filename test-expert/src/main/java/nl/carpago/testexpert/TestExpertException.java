package nl.carpago.testexpert;

@SuppressWarnings("serial")
public class TestExpertException extends RuntimeException {

	public TestExpertException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestExpertException(Throwable cause) {
		super(cause);
	}
	
	

}
