package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class FolderNotExistsException extends GenericException {

	private static final String MESSAGE = "La cartella scelta non esiste";
	
	public FolderNotExistsException() {
		super(MESSAGE);
	}
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
	
	@Override
	public String getName() {
		return "Folder not exists";
	}
}
