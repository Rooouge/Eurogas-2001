package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class InvalidCapException extends GenericException {

	private static final String MESSAGE = "Il CAP deve essere di 5 cifre";
	
	public InvalidCapException() {
		super(MESSAGE);
	}
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
	
	@Override
	public String getName() {
		return "Invalid CAP";
	}
}
