package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class EmptyFieldException extends GenericException {
	
	private static final String MESSAGE = "Campo vuoto";
	private String info;
	
	public EmptyFieldException(String info) {
		super(MESSAGE);
		this.info = ": " + info;
	}
	
	@Override
	public String getMessage() {
		return MESSAGE + info;
	}

	@Override
	public String getName() {
		return "Empty field";
	}
}
