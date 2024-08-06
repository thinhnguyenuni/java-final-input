package fa.training.exception;

public class EndDateOverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndDateOverException() {
		System.out.println("Ngay ket thuc khong duoc nho hon ngay bat dau va lon hon 100 ngay so voi ngay bat dau");
	}
}
