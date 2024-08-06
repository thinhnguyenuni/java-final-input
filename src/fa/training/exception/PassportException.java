package fa.training.exception;

public class PassportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PassportException() {
	}

	public PassportException(int line) {
		System.out.println("Line " + line + ": Passport is invalid");
	}

}
