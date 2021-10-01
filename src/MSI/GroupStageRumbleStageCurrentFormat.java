package MSI;

import java.util.List;

import Classes.Group;
import Classes.GroupStageFormat;
import CustomExceptions.MismatchedNumberOfGroupsException;
import CustomExceptions.MismatchedNumberOfPoolsException;
import Misc.Strings;

public class GroupStageRumbleStageCurrentFormat extends GroupStageFormat {
	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		
		// Printout Groups
		System.out.println(A);
		
		// Simulate Main Group Stage
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating Rumble Stage\n");
		System.out.println("------------------------------------------------\n");
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 2, true); 
		
		// Summary of Main Groups
		System.out.println("Summary of Main Stage Group Stage");
		System.out.println("\n------------------------------------------------------------------------\n");
		A.PrintStandings();
	}
}
