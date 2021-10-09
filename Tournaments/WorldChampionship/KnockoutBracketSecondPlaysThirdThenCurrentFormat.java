package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class KnockoutBracketSecondPlaysThirdThenCurrentFormat extends Bracket {
	public KnockoutBracketSecondPlaysThirdThenCurrentFormat(Tournament partOf) {
		super(partOf);
	}

	public KnockoutBracketSecondPlaysThirdThenCurrentFormat(Tournament tournamentWorldChampionship, String msgs) {
		super(tournamentWorldChampionship, msgs);
	}
	
	int requiredNumberOfGroups = 4;
	
	@Override
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
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(Strings.LPoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.R34), "M1", 3, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.R34), "M2", 3, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.R34), "M3", 3, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.R34), "M4", 3, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.QFS), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.QFS), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.QFS), "M7", 5, tracker);
		Series M8 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.QFS), "M8", 5, tracker);
		Series M9 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.SFS), "M9", 5, tracker);
		Series M10 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.SFS), "M10", 5, tracker);
		Series M11 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.FS), "M11", 5, tracker);
		
		// Defining certain regions of the bracket
		ArrayList<Series> upperMatchups = new ArrayList<Series>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Series> lowerMatchups = new ArrayList<Series>(Arrays.asList(M5, M6, M7, M8));
		
		M1.setTeamA(poolTwo.Draw());
		M2.setTeamA(poolTwo.Draw());
		M3.setTeamA(poolTwo.Draw());
		M4.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolThree.DrawWithSameSideRule(M1, M2, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M2.setTeamB(poolThree.DrawWithSameSideRule(M2, M1, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M3.setTeamB(poolThree.DrawWithSameSideRule(M3, M4, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M4.setTeamB(poolThree.DrawWithSameSideRule(M4, M3, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M1.Simulate();
		M2.Simulate();
		M3.Simulate();
		M4.Simulate();
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate();
		M6.Simulate();
		M7.Simulate();
		M8.Simulate();
		
		M9.setTeamA(M5.getWinner());
		M9.setTeamB(M6.getWinner());
		M10.setTeamA(M7.getWinner());
		M10.setTeamB(M8.getWinner());
		M9.Simulate();
		M10.Simulate();
		
		M11.setTeamA(M9.getWinner());
		M11.setTeamB(M10.getWinner());
		M11.Simulate();
		
		super.addSeries(M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11);
		super.setChampionshipSeries(M11);
	}
}
