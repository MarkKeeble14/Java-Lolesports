package CustomExceptions;

public class GroupExceedingCapacityException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupExceedingCapacityException(String errorGroup, String extraTeam, int capacity) {
		super("\n" + errorGroup + "\nAlready at capacity: " + capacity + "; Cannot add: " + extraTeam);
	}
}
