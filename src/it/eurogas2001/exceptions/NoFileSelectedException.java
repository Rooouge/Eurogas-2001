package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class NoFileSelectedException extends GenericException {
	
	private static final String MESSAGE = "Non è stato selezionato nessun file";
	
	public NoFileSelectedException() {
		super(MESSAGE);
	}
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}

	@Override
	public String getName() {
		return "No file selected";
	}
}