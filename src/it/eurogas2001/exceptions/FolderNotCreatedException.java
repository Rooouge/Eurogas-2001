package it.eurogas2001.exceptions;

@SuppressWarnings("serial")
public class FolderNotCreatedException extends GenericException {

	private static final String MESSAGE = "Non è stato possibile creare la cartella";
	
	public FolderNotCreatedException() {
		super(MESSAGE);
	}
	
	@Override
	public String getMessage() {
		return MESSAGE;
	}
	
	@Override
	public String getName() {
		return "Folder not created";
	}
}
