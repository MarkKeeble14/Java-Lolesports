package bracketgen;

public class ImpossibleDrawException extends Exception {
	public ImpossibleDrawException(String errorPool, String errorGroups, String rule) {
		super("\nCannot resolve the following teams into the following groups while adhering to the " + rule 
				+ " rule\nTeams: " + errorPool + "\n" + errorGroups);
	}
}
