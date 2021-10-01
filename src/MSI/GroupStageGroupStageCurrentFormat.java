package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.GroupStageFormat;
import Classes.Team;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStageGroupStageCurrentFormat extends GroupStageFormat {
	int requiredNumberOfGroups = 3;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		// Simulate Main Group Stage
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating Group Stage\n");
		System.out.println("------------------------------------------------\n");
		
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 2, true); 
		B.FullSimulate(Strings.MSGS, 2, true); 
		C.FullSimulate(Strings.MSGS, 2, true); 
		
		// Summary of Main Groups
		System.out.println("Summary of Main Stage Group Stage");
		System.out.println("\n------------------------------------------------------------------------\n");
		A.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		B.PrintStandings();
		System.out.println("\n------------------------------------------------\n");
		C.PrintStandings();
		
		System.out.println("\n------------------------------------------------");
		
		List<Team> Q = new ArrayList<Team>(Arrays.asList(A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2),
				B.GetTeamFromPlacement(1), B.GetTeamFromPlacement(2),
				C.GetTeamFromPlacement(1), C.GetTeamFromPlacement(2)));
		
		// Printing Out Results
		System.out.print("\nQualified Play-ins Teams:\n");
		for (int i = 0; i < Q.size(); i++) {
			if (i == Q.size()- 1) {
				System.out.println(Q.get(i));	
			} else {
				System.out.print(Q.get(i) + "\n");	
			}
		}
		System.out.println("\n------------------------------------------------------------------------\n");
	}
}
