package WorldChampionship;

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

// 4 Group Bracket
public class KnockoutBracketCurrentFormat extends Bracket {
	int requiredNumberOfGroups = 4;
	
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
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		Match M4 = new Match("M4");
		Match M5 = new Match("M5");
		Match M6 = new Match("M6");
		Match M7 = new Match("M7");
	
		ArrayList<Match> matches = new ArrayList<Match>(Arrays.asList(M1, M2, M3, M4));
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolOne.Draw());
		M3.setTeamA(poolOne.Draw());
		M4.setTeamA(poolOne.Draw());
		
		M1.setTeamB(poolTwo.DrawWithSameSideRule(M1, M2, poolTwo, new ArrayList<Team>(), matches, groups));
		M2.setTeamB(poolTwo.DrawWithSameSideRule(M2, M1, poolTwo, new ArrayList<Team>(), matches, groups));
		M3.setTeamB(poolTwo.DrawWithSameSideRule(M3, M4, poolTwo, new ArrayList<Team>(), matches, groups));
		M4.setTeamB(poolTwo.DrawWithSameSideRule(M4, M3, poolTwo, new ArrayList<Team>(), matches, groups));
				
		M1.Simulate(Strings.MSKS, 5);
		M2.Simulate(Strings.MSKS, 5);
		M3.Simulate(Strings.MSKS, 5);
		M4.Simulate(Strings.MSKS, 5);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate(Strings.MSKS, 5);
		M6.Simulate(Strings.MSKS, 5);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate(Strings.MSKS, 5);
		
		super.AddMatches(M1, M2, M3, M4, M5, M6, M7);
		
		super.PrintWinnerStats();
	}

}
