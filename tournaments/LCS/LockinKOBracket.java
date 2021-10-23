package LCS;

import java.util.List;

import DefiningMatches.Series;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Tournament;

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
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.QFS, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.SFS, 2);
		BracketSlice S3 = new BracketSlice(super.getLabel(), Strings.FS, 3);
		
		Series M1 = new Series(1, 3, tracker);
		Series M2 = new Series(2, 3, tracker);
		Series M3 = new Series(3, 3, tracker);
		Series M4 = new Series(4, 3, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		S2.addSeries(M5, M6);
		Series M7 = new Series(7, 5, tracker);
		S3.addSeries(M7);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(B.GetTeamFromPlacement(4));
		M1.Simulate();
		standings.PlaceTeamDuringBacketStage(M1.getLoser(), true);
		
		M2.setTeamA(A.GetTeamFromPlacement(3));
		M2.setTeamB(B.GetTeamFromPlacement(2));
		M2.Simulate();
		standings.PlaceTeamDuringBacketStage(M2.getLoser(), false);
		
		M3.setTeamA(B.GetTeamFromPlacement(1));
		M3.setTeamB(A.GetTeamFromPlacement(4));
		M3.Simulate();
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), false);
		
		M4.setTeamA(B.GetTeamFromPlacement(3));
		M4.setTeamB(A.GetTeamFromPlacement(2));
		M4.Simulate();
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), false);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M5.Simulate();
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M6.Simulate();
		standings.PlaceTeamDuringBacketStage(M5.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M6.getLoser(), false);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		standings.PlaceTeamDuringBacketStage(M7.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M7.getWinner(), true);
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2, S3);
		super.setChampionshipSeries(M7);
	}
	
	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
