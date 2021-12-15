package WorldChampionshipLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningMatches.Game;
import DefiningMatches.Series;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

// 4 Group Double Elim Bracket
public class FinalEliminationKnockoutBracket extends Bracket {
	public FinalEliminationKnockoutBracket(String label, Tournament partOf) {
		super(label, partOf);
	}

	public FinalEliminationKnockoutBracket(String label, Tournament partOf, String fedThrough) {
		super(label, partOf, fedThrough);
	}
	
	int requiredNumberOfGroups = 4;
	
	public void Simulate(List<Group> groups) throws Exception {
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Pool poolOne = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		
		Pool poolTwo = new Pool(Strings.LPoolOne, b.getSeries(5).getWinner(), b.getSeries(6).getWinner(), 
				b.getSeries(7).getWinner(), b.getSeries(8).getWinner());
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.QFS, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.SFS, 2);
		BracketSlice S3 = new BracketSlice(super.getLabel(), Strings.FS, 3);
		
		Series M1 = new Series(1, 5, tracker);
		Series M2 = new Series(2, 5, tracker);
		Series M3 = new Series(3, 5, tracker);
		Series M4 = new Series(4, 5, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		S2.addSeries(M5, M6);
		Series M7 = new Series(7, 5, tracker);
		S3.addSeries(M7);
		
		ArrayList<Series> matches = new ArrayList<Series>(Arrays.asList(M1, M2, M3, M4));
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolOne.Draw());
		M3.setTeamA(poolOne.Draw());
		M4.setTeamA(poolOne.Draw());
		
		M1.setTeamB(poolTwo.DrawWithSameMatchRule(M1,  poolTwo, new ArrayList<Team>(), matches, groups));
		M2.setTeamB(poolTwo.DrawWithSameMatchRule(M2, poolTwo, new ArrayList<Team>(), matches, groups));
		M3.setTeamB(poolTwo.DrawWithSameMatchRule(M3, poolTwo, new ArrayList<Team>(), matches, groups));
		M4.setTeamB(poolTwo.DrawWithSameMatchRule(M4, poolTwo, new ArrayList<Team>(), matches, groups));
				
		M1.Simulate();
		standings.PlaceTeamDuringBacketStage(M1.getLoser(), true);
		M2.Simulate();
		standings.PlaceTeamDuringBacketStage(M2.getLoser(), false);
		M3.Simulate();
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), false);
		M4.Simulate();
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), false);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate();
		standings.PlaceTeamDuringBacketStage(M5.getLoser(), true);
		M6.Simulate();
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
}
