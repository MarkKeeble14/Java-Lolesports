package CustomExceptions;

public class MismatchedNumberOfPoolsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchedNumberOfPoolsException(int expected, int given) {
		super("\nExpected: " + expected + " Pools; Given: " + given);
	}
}
