package fa.training.exception;

public class IDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IDException(int line, String passengerID) {
		System.out.println("Line " + line + ": Immigrant " + passengerID + " has duplicated ID");
	}
}
