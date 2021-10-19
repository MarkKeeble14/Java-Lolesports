package LEC;

import java.util.List;

import DefiningMatches.Series;
import StaticVariables.Strings;
import Stats.ResultsTracker;
import Stats.Standings;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Tournament;

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
		ResultsTracker tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.S1, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.S2, 2);
		BracketSlice S3 = new BracketSlice(super.getLabel(), Strings.S3, 3);
		BracketSlice S4 = new BracketSlice(super.getLabel(), Strings.SFS, 4);
		BracketSlice S5 = new BracketSlice(super.getLabel(), Strings.FS, 5);
		
		Series M1 = new Series(1, 5, tracker);
		Series M2 = new Series(2, 5, tracker);
		Series M3 = new Series(3, 5, tracker);
		S1.addSeries(M1, M2, M3);
		Series M4 = new Series(4, 5, tracker);
		S2.addSeries(M4);
		Series M5 = new Series(5, 5, tracker);
		S3.addSeries(M5);
		Series M6 = new Series(6, 5, tracker);
		Series M7 = new Series(7, 5, tracker);
		S4.addSeries(M6, M7);
		Series M8 = new Series(8, 5, tracker);
		S5.addSeries(M8);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(A.GetTeamFromPlacement(4));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(2));
		M2.setTeamB(A.GetTeamFromPlacement(3));
		M2.Simulate();
		M3.setTeamA(A.GetTeamFromPlacement(5));
		M3.setTeamB(A.GetTeamFromPlacement(6));
		M3.Simulate();
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), true);
		
		if (M1.getLoser() == A.GetTeamFromPlacement(4)) {
			M4.setTeamA(M1.getLoser());
		} else {
			M4.setTeamA(M2.getLoser());
		}
		M4.setTeamB(M3.getWinner());
		M4.Simulate();
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), true);
		
		if (M1.getLoser() == A.GetTeamFromPlacement(4)) {
			M5.setTeamA(M2.getLoser());
		} else {
			M5.setTeamA(M1.getLoser());
		}
		M5.setTeamB(M4.getWinner());
		M5.Simulate();
		standings.PlaceTeamDuringBacketStage(M5.getLoser(), true);
		
		M6.setTeamA(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M6.Simulate();
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getLoser());
		M7.Simulate();
		standings.PlaceTeamDuringBacketStage(M6.getLoser(), true);
		
		M8.setTeamA(M6.getWinner());
		M8.setTeamB(M7.getWinner());
		M8.Simulate();
		standings.PlaceTeamDuringBacketStage(M8.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M8.getWinner(), true);
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2, S3, S4, S5);
		super.setChampionshipSeries(M8);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
