package LCS;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class SummerPlayoffsLCS extends Bracket {
	public SummerPlayoffsLCS(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}
	
	public SummerPlayoffsLCS(Tournament partOf, String fedTeamsThrough) {
		super(partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		super.setLabel(label);
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR1), "M1", 5, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR1), "M2", 5, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR1), "M3", 5, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR1), "M4", 5, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR2), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR2), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR2), "M7", 5, tracker);
		Series M8 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR3), "M8", 5, tracker);
		Series M9 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WR3), "M9", 5, tracker);
		Series M10 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LR3), "M10", 5, tracker);
		Series M11 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.LFS), "M11", 5, tracker);
		Series M12 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.WFS), "M12", 5, tracker);
		
		M1.setTeamA(A.GetTeamFromPlacement(4));
		M1.setTeamB(A.GetTeamFromPlacement(5));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(3));
		M2.setTeamB(A.GetTeamFromPlacement(6));
		M2.Simulate();
		M3.setTeamA(A.GetTeamFromPlacement(7));
		M3.setTeamB(M2.getLoser());
		M3.Simulate();
		M4.setTeamA(A.GetTeamFromPlacement(8));
		M4.setTeamB(M1.getLoser());
		M4.Simulate();
		M5.setTeamA(A.GetTeamFromPlacement(1));
		M5.setTeamB(M1.getWinner());
		M5.Simulate();
		M6.setTeamA(A.GetTeamFromPlacement(2));
		M6.setTeamB(M2.getWinner());
		M6.Simulate();
		M7.setTeamA(M3.getWinner());
		M7.setTeamB(M5.getLoser());
		M7.Simulate();
		M8.setTeamA(M4.getWinner());
		M8.setTeamB(M6.getLoser());
		M8.Simulate();
		M9.setTeamA(M5.getWinner());
		M9.setTeamB(M6.getWinner());
		M9.Simulate();
		M10.setTeamA(M7.getWinner());
		M10.setTeamB(M8.getWinner());
		M10.Simulate();
		M11.setTeamA(M10.getWinner());
		M11.setTeamB(M9.getLoser());
		M11.Simulate();
		M12.setTeamA(M9.getWinner());
		M12.setTeamB(M11.getWinner());
		M12.Simulate();
		
		// General Tracking Stuff
		super.addSeries(M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12);
		super.setChampionshipSeries(M12);
	}

}
