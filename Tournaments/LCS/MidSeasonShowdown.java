package LCS;

import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class MidSeasonShowdown extends Bracket {
	public MidSeasonShowdown(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}	
	
	public MidSeasonShowdown(Tournament partOf, String fedTeamsThrough) {
		super(partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		super.setLabel(label);
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR1), "M1", 5, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR1), "M2", 5, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR1), "M3", 5, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR1), "M4", 5, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR2), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR2), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR3), "M7", 5, tracker);
		Series M8 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.GFS), "M8", 5, tracker);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(A.GetTeamFromPlacement(4));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(3));
		M2.setTeamB(A.GetTeamFromPlacement(2));
		M2.Simulate();
		
		M3.setTeamA(A.GetTeamFromPlacement(6));
		M3.setTeamB(M2.getLoser());
		M3.Simulate();
		M4.setTeamA(A.GetTeamFromPlacement(5));
		M4.setTeamB(M1.getLoser());
		M4.Simulate();
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M5.Simulate();
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M6.Simulate();
		
		M7.setTeamA(M5.getLoser());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		
		M8.setTeamA(M5.getWinner());
		M8.setTeamB(M7.getWinner());
		M8.Simulate();
		
		// General Tracking Stuff
		super.addSeries(M1, M2, M3, M4, M5, M6, M7, M8);
		super.setChampionshipSeries(M8);
	}
}
