package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.Bracket;

// 4 Group Bracket
public class KnockoutBracketCurrentFormat extends Bracket {
	public KnockoutBracketCurrentFormat(String label, Tournament partOf) {
		super(label, partOf);
	}

	public KnockoutBracketCurrentFormat(String label, Tournament tournamentWorldChampionship, String msgs) {
		super(label, tournamentWorldChampionship, msgs);
	}

	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M1", 5, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M2", 5, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M3", 5, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M4", 5, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.FS), "M7", 5, tracker);
		
		ArrayList<Series> matches = new ArrayList<Series>(Arrays.asList(M1, M2, M3, M4));
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolOne.Draw());
		M3.setTeamA(poolOne.Draw());
		M4.setTeamA(poolOne.Draw());
		
		M1.setTeamB(poolTwo.DrawWithSameSideRule(M1, M2, poolTwo, new ArrayList<Team>(), matches, groups));
		M2.setTeamB(poolTwo.DrawWithSameSideRule(M2, M1, poolTwo, new ArrayList<Team>(), matches, groups));
		M3.setTeamB(poolTwo.DrawWithSameSideRule(M3, M4, poolTwo, new ArrayList<Team>(), matches, groups));
		M4.setTeamB(poolTwo.DrawWithSameSideRule(M4, M3, poolTwo, new ArrayList<Team>(), matches, groups));
				
		M1.Simulate();
		M2.Simulate();
		M3.Simulate();
		M4.Simulate();
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate();
		M6.Simulate();
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		
		// General Tracking Stuff
		super.addSeries(M1, M2, M3, M4, M5, M6, M7);
		super.setChampionshipSeries(M7);
	}

}
