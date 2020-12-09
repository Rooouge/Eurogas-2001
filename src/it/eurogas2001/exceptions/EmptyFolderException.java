package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class EmptyFolderException extends GenericException {
	
	private static final String MESSAGE = "La cartella scelta è vuota";
	
	public EmptyFolderException() {
		super(MESSAGE);
	}
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}

	@Override
	public String getName() {
		return "Empty folder";
	}
}
