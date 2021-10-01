package CustomExceptions;

public class MismatchedSizeOfPoolException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchedSizeOfPoolException(int expected, int given) {
		super("\nExpected: " + expected + " Groups; Given: " + given);
	}
}
