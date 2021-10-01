package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.Group;
import Classes.Match;
import Classes.Team;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class KnockoutBracketCurrentPIFormat extends Bracket {
	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating PI Knockout Bracket");
		System.out.println("\n------------------------------------------------");
		
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Match M1 = new Match("M1", A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4));
		Match M2 = new Match("M2", B.GetTeamFromPlacement(3), B.GetTeamFromPlacement(4));
		M1.Simulate(Strings.PIKS, 5);
		M2.Simulate(Strings.PIKS, 5);
		
		Match M3 = new Match("M3", B.GetTeamFromPlacement(2), M1.getWinner());
		Match M4 = new Match("M4", A.GetTeamFromPlacement(2), M2.getWinner());
		M3.Simulate(Strings.PIKS, 5);
		M4.Simulate(Strings.PIKS, 5);
		
		super.AddMatches(M1, M2, M3, M4);
		List<Team> qualified = new ArrayList<Team>(Arrays.asList(A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				M3.getWinner(), M4.getWinner()));
		
		// Printing Out Results
		System.out.print("\nQualified Play-ins Teams:\n");
		for (int i = 0; i < qualified.size(); i++) {
			if (i == qualified.size()- 1) {
				System.out.println(qualified.get(i));	
			} else {
				System.out.print(qualified.get(i) + "\n");	
			}
		}
	}
}
