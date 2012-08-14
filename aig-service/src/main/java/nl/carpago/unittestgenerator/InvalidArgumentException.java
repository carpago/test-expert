package nl.carpago.unittestgenerator;

@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {
	
	public InvalidArgumentException(String message) {
		super(message);
	}

}
