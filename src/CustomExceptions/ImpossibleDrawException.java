package CustomExceptions;

public class ImpossibleDrawException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImpossibleDrawException(String errorPool, String errorGroups, String rule) {
		super("\nCannot resolve the following teams into the following groups while adhering to the " + rule 
				+ " rule\nTeams: " + errorPool + "\n" + errorGroups);
	}
}
