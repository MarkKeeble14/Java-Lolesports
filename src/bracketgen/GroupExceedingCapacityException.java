package bracketgen;

public class GroupExceedingCapacityException extends Exception {
	public GroupExceedingCapacityException(String errorGroup, String extraTeam, int capacity) {
		super("\n" + errorGroup + "\nAlready at capacity: " + capacity + "; Cannot add: " + extraTeam);
	}
}
