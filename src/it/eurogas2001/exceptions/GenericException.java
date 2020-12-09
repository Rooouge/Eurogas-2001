package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class GenericException extends IllegalArgumentException {

	protected String message;
	
	public GenericException(String message) {
		super(message);
		this.message = message;
	}
	
	public <T> GenericException(T value, String reason) {
		super("   " + value + " : " + reason);
		this.message = "   " + value + " : " + reason;
	}
	
	public String getMessage() {
		return null;
	}
	
	public String getName() {
		return null;
	}
}
