package LCS;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class LockinKOBracket extends Bracket {

	public LockinKOBracket(String label, Tournament partOf, String fedTeamsThrough) {
		super(label, partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}
	public LockinKOBracket(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M1", 3, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M2", 3, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M3", 3, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.QFS), "M4", 3, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.FS), "M7", 5, tracker);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(B.GetTeamFromPlacement(4));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(3));
		M2.setTeamB(B.GetTeamFromPlacement(2));
		M2.Simulate();
		
		M3.setTeamA(B.GetTeamFromPlacement(1));
		M3.setTeamB(A.GetTeamFromPlacement(4));
		M3.Simulate();
		M4.setTeamA(B.GetTeamFromPlacement(3));
		M4.setTeamB(A.GetTeamFromPlacement(2));
		M4.Simulate();
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M5.Simulate();
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M6.Simulate();
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		
		// General Tracking Stuff
		super.addSeries(M1, M2, M3, M4, M5, M6, M7);
		super.setChampionshipSeries(M7);
	}
}
