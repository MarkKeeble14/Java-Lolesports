package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.Group;
import Classes.Match;
import Classes.Pool;
import Classes.Team;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class KnockoutBracketCurrentFormat extends Bracket {
	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		System.out.println("\n------------------------------------------------------------------------");
		System.out.println("\nSimulating Current Knockout Bracket");
		System.out.println("\n------------------------------------------------");
		
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolOne.Draw());
		M2.setTeamB(poolTwo.Draw());

		M1.Simulate(Strings.MSKS, 5);
		M2.Simulate(Strings.MSKS, 5);
		
		M3.setTeamA(M1.getWinner());
		M3.setTeamB(M2.getWinner());
		
		M3.Simulate(Strings.MSKS, 5);
		
		super.AddMatches(M1, M2, M3);
		
		super.PrintWinnerStats();
	}
}
