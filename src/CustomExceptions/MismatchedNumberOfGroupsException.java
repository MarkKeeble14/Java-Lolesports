package CustomExceptions;

public class MismatchedNumberOfGroupsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchedNumberOfGroupsException(int expected, int given) {
		super("\nExpected: " + expected + " Groups; Given: " + given);
	}
}
