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
import Stats.ResultsTracker;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

// 4 Group Double Elim Bracket
public class MainStageKnockoutBracket extends Bracket {
	public MainStageKnockoutBracket(String label, Tournament partOf) {
		super(label, partOf);
	}

	public MainStageKnockoutBracket(String label, Tournament partOf, String fedThrough) {
		super(label, partOf, fedThrough);
	}
	
	int requiredNumberOfGroups = 4;
	
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		ResultsTracker tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Set Pools
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.S1, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.S2, 2);
		
		Series M1 = new Series(1, 5, tracker);
		Series M2 = new Series(2, 5, tracker);
		Series M3 = new Series(3, 5, tracker);
		Series M4 = new Series(4, 5, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		Series M7 = new Series(7, 5, tracker);
		Series M8 = new Series(8, 5, tracker);
		S2.addSeries(M5, M6, M7, M8);
		
		// Defining certain regions of the bracket
		ArrayList<Series> matchups = new ArrayList<Series>(Arrays.asList(M5, M6, M7, M8));
		
		M1.setTeamA(A.GetTeamFromPlacement(3));
		M1.setTeamB(A.GetTeamFromPlacement(4));
		M1.Simulate();
		standings.PlaceTeamDuringBacketStage(M1.getLoser(), true);
		
		M2.setTeamA(B.GetTeamFromPlacement(3));
		M2.setTeamB(B.GetTeamFromPlacement(4));
		M2.Simulate();
		standings.PlaceTeamDuringBacketStage(M2.getLoser(), false);
		
		M3.setTeamA(C.GetTeamFromPlacement(3));
		M3.setTeamB(C.GetTeamFromPlacement(4));
		M3.Simulate();
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), false);
		
		M4.setTeamA(D.GetTeamFromPlacement(3));
		M4.setTeamB(D.GetTeamFromPlacement(4));
		M4.Simulate();
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), false);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), matchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), matchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), matchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), matchups, groups));
		
		M5.Simulate();
		standings.PlaceTeamDuringBacketStage(M5.getLoser(), true);
		M6.Simulate();
		standings.PlaceTeamDuringBacketStage(M6.getLoser(), false);
		M7.Simulate();
		standings.PlaceTeamDuringBacketStage(M7.getLoser(), false);
		M8.Simulate();
		standings.PlaceTeamDuringBacketStage(M8.getLoser(), false);
		
		super.addBracketSections(S1, S2);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
