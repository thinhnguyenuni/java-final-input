/**
 * 
 */
package fa.training.exception;

/**
 * @author Administrator
 *
 */
public class CarNumberUnForMatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CarNumberUnForMatException() {
		System.out.println("Car number phai co dinh dang 43XX-YYYYY (XX tu A-Z; Y la so");
	}

}
