package WorldChampionshipLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.BracketSection;
import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Game;
import Matches.Series;
import Misc.Strings;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.Bracket;

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
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Set Pools
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		BracketSection S1 = new BracketSection(Strings.S1, 1);
		BracketSection S2 = new BracketSection(Strings.S2, 2);
		
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
		
		M2.setTeamA(B.GetTeamFromPlacement(3));
		M2.setTeamB(B.GetTeamFromPlacement(4));
		
		M3.setTeamA(C.GetTeamFromPlacement(3));
		M3.setTeamB(C.GetTeamFromPlacement(4));
		
		M4.setTeamA(D.GetTeamFromPlacement(3));
		M4.setTeamB(D.GetTeamFromPlacement(4));
		
		M1.Simulate();
		M2.Simulate();
		M3.Simulate();
		M4.Simulate();
		
		standings.PlaceTeam(M1.getLoser(), 16);
		standings.PlaceTeam(M2.getLoser(), 16);
		standings.PlaceTeam(M3.getLoser(), 16);
		standings.PlaceTeam(M4.getLoser(), 16);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), matchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), matchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), matchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), matchups, groups));
		M5.Simulate();
		M6.Simulate();
		M7.Simulate();
		M8.Simulate();
		
		standings.PlaceTeam(M5.getLoser(), 12);
		standings.PlaceTeam(M6.getLoser(), 12);
		standings.PlaceTeam(M7.getLoser(), 12);
		standings.PlaceTeam(M8.getLoser(), 12);
		
		super.addBracketSections(S1, S2);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
