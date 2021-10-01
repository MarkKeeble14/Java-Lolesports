package WorldChampionship;

import java.util.List;

import Classes.Group;
import Classes.GroupStageFormat;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStageMainCurrentFormat extends GroupStageFormat {
	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		// Simulate Main Group Stage
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating Main Stage Group Stage\n");
		System.out.println("------------------------------------------------\n");
		
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 2, true); 
		B.FullSimulate(Strings.MSGS, 2, true); 
		C.FullSimulate(Strings.MSGS, 2, true); 
		D.FullSimulate(Strings.MSGS, 2, true); 
		
		// Summary of Main Groups
		System.out.println("Summary of Main Stage Group Stage");
		System.out.println("\n------------------------------------------------------------------------\n");
		A.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		B.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		C.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		D.PrintStandings();
	}

}
