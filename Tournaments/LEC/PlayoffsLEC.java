package LEC;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class PlayoffsLEC extends Bracket {
	
	public PlayoffsLEC(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}

	public PlayoffsLEC(String label, Tournament partOf, String fedTeamsThrough) {
		super(label, partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Group> groups) throws Exception {
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.WR1), "M1", 5, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.WR1), "M2", 5, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.LR1), "M3", 5, tracker);
		Series M4 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.LR2), "M4", 5, tracker);
		Series M5 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.LR3), "M5", 5, tracker);
		Series M6 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M6", 5, tracker);
		Series M7 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.LR4), "M7", 5, tracker);
		Series M8 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.FS), "M8", 5, tracker);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(A.GetTeamFromPlacement(4));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(2));
		M2.setTeamB(A.GetTeamFromPlacement(3));
		M2.Simulate();
		M3.setTeamA(A.GetTeamFromPlacement(5));
		M3.setTeamB(A.GetTeamFromPlacement(6));
		M3.Simulate();
		if (M1.getLoser() == A.GetTeamFromPlacement(4)) {
			M4.setTeamA(M1.getLoser());
		} else {
			M4.setTeamA(M2.getLoser());
		}
		M4.setTeamB(M3.getWinner());
		M4.Simulate();
		if (M1.getLoser() == A.GetTeamFromPlacement(4)) {
			M5.setTeamA(M2.getLoser());
		} else {
			M5.setTeamA(M1.getLoser());
		}
		M5.setTeamB(M4.getWinner());
		M5.Simulate();
		M6.setTeamA(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M6.Simulate();
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getLoser());
		M7.Simulate();
		M8.setTeamA(M6.getWinner());
		M8.setTeamB(M7.getWinner());
		M8.Simulate();
		// General Tracking Stuff
		super.addSeries(M1, M2, M3, M4, M5, M6, M7, M8);
		super.setChampionshipSeries(M8);
	}

}
