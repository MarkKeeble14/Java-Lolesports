package WorldChampionship;

import java.util.List;

import Classes.Group;
import Classes.GroupStageFormat;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStagePICurrentFormat extends GroupStageFormat {
	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		// Simulate Main Group Stage
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating PI Stage Group Stage\n");
		System.out.println("------------------------------------------------\n");
		
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 1, true); 
		B.FullSimulate(Strings.MSGS, 1, true); 
		
		// Summary of Main Groups
		System.out.println("Summary of PI Group Stage");
		System.out.println("\n------------------------------------------------------------------------\n");
		A.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		B.PrintStandings();
	}

}
