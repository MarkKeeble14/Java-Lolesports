package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.Group;
import Classes.Match;
import Classes.Pool;
import Classes.RegionalWLTracker;
import Classes.Team;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

// 4 Group Double Elim Bracket
public class KnockoutBracketDoubleElimFormat extends Bracket {
	public KnockoutBracketDoubleElimFormat(Tournament partOf) {
		super(partOf);
	}

	int requiredNumberOfGroups = 4;
	
	public void Simulate(String label, List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		super.setLabel(label);
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Set Pools
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(Strings.LPoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), 
				C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		Match M4 = new Match("M4");
		Match M5 = new Match("M5");
		Match M6 = new Match("M6");
		Match M7 = new Match("M7");
		Match M8 = new Match("M8");
		Match M9 = new Match("M9");
		Match M10 = new Match("M10");
		Match M11 = new Match("M11");
		Match M12 = new Match("M12");
		Match M13 = new Match("M13");
		Match M14 = new Match("M14");
		Match M15 = new Match("M15");
		Match M16 = new Match("M16");
		Match M17 = new Match("M17");
		Match M18 = new Match("M18");
		
		// Defining certain regions of the bracket
		ArrayList<Match> upperMatchups = new ArrayList<Match>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Match> lowerMatchups = new ArrayList<Match>(Arrays.asList(M5, M6, M7, M8));
		
		M1.setTeamA(poolTwo.Draw());
		M2.setTeamA(poolTwo.Draw());
		M3.setTeamA(poolTwo.Draw());
		M4.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolThree.DrawWithSameSideRule(M1, M2, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M2.setTeamB(poolThree.DrawWithSameSideRule(M2, M1, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M3.setTeamB(poolThree.DrawWithSameSideRule(M3, M4, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M4.setTeamB(poolThree.DrawWithSameSideRule(M4, M3, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M1.Simulate(label, tracker, 3, true);
		M2.Simulate(label, tracker, 3, true);
		M3.Simulate(label, tracker, 3, true);
		M4.Simulate(label, tracker, 3, true);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate(label, tracker, 5, true);
		M6.Simulate(label, tracker, 5, true);
		M7.Simulate(label, tracker, 5, true);
		M8.Simulate(label, tracker, 5, true);
		
		M9.setTeamA(M5.getLoser());
		M9.setTeamB(M7.getLoser());
		M10.setTeamA(M6.getLoser());
		M10.setTeamB(M8.getLoser());
		M11.setTeamA(M5.getWinner());
		M11.setTeamB(M6.getWinner());
		M12.setTeamA(M7.getWinner());
		M12.setTeamB(M8.getWinner());
		M9.Simulate(label, tracker, 5, true);
		M10.Simulate(label, tracker, 5, true);
		M11.Simulate(label, tracker, 5, true);
		M12.Simulate(label, tracker, 5, true);
		
		M13.setTeamA(M11.getLoser());
		M13.setTeamB(M9.getWinner());
		M14.setTeamA(M12.getLoser());
		M14.setTeamB(M10.getWinner());
		M15.setTeamA(M11.getWinner());
		M15.setTeamB(M12.getWinner());
		M13.Simulate(label, tracker, 5, true);
		M14.Simulate(label, tracker, 5, true);
		M15.Simulate(label, tracker, 5, true);
		
		M16.setTeamA(M13.getWinner());
		M16.setTeamB(M14.getWinner());
		M16.Simulate(label, tracker, 5, true);
		
		M17.setTeamA(M15.getLoser());
		M17.setTeamB(M16.getWinner());
		M17.Simulate(label, tracker, 5, true);
		
		M18.setTeamA(M15.getWinner());
		M18.setTeamB(M17.getWinner());
		M18.Simulate(label, tracker, 5, true);
		
		super.addMatches(M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18);
		super.setChampionshipMatch(M18);
	}
}
