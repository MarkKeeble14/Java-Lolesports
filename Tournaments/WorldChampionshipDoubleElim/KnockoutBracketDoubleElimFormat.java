package WorldChampionshipDoubleElim;

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
public class KnockoutBracketDoubleElimFormat extends Bracket {
	public KnockoutBracketDoubleElimFormat(String label, Tournament partOf) {
		super(label, partOf);
	}

	public KnockoutBracketDoubleElimFormat(String label, Tournament partOf, String fedThrough) {
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
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(Strings.LPoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), 
				C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		String r3vs4 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.R34);
		String s1 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S1);
		String s2 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S2);
		String s3 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S3);
		String s4 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S4);
		
		BracketSection S1 = new BracketSection(r3vs4);
		BracketSection S2 = new BracketSection(s1);
		BracketSection S3 = new BracketSection(s2);
		BracketSection S4 = new BracketSection(s3);
		BracketSection S5 = new BracketSection(s4);
		
		Series M1 = new Series(1, 3, tracker);
		Series M2 = new Series(2, 3, tracker);
		Series M3 = new Series(3, 3, tracker);
		Series M4 = new Series(4, 3, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		Series M7 = new Series(7, 5, tracker);
		Series M8 = new Series(8, 5, tracker);
		Series M9 = new Series(9, 5, tracker);
		Series M10 = new Series(10, 5, tracker);
		S2.addSeries(M5, M6, M7, M8, M9, M10);
		Series M11 = new Series(11, 5, tracker);
		Series M12 = new Series(12, 5, tracker);
		Series M13 = new Series(13, 5, tracker);
		Series M14 = new Series(14, 5, tracker);
		S3.addSeries(M11, M12, M13, M14);
		Series M15 = new Series(15, 5, tracker);
		Series M16 = new Series(16, 5, tracker);
		S4.addSeries(M15, M16);
		Series M17 = new Series(17, 7, tracker);
		Series M18 = new Series(18, 7, tracker);
		S5.addSeries(M18, M17);
		
		// Defining certain regions of the bracket
		ArrayList<Series> upperMatchups = new ArrayList<Series>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Series> lowerMatchups = new ArrayList<Series>(Arrays.asList(M5, M6, M7, M8));
		
		M1.setTeamA(poolTwo.Draw());
		M2.setTeamA(poolTwo.Draw());
		M3.setTeamA(poolTwo.Draw());
		M4.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolThree.DrawWithSameSideRule(M1, M2, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M2.setTeamB(poolThree.DrawWithSameSideRule(M2, M1, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M3.setTeamB(poolThree.DrawWithSameSideRule(M3, M4, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M4.setTeamB(poolThree.DrawWithSameSideRule(M4, M3, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M1.Simulate();
		M2.Simulate();
		M3.Simulate();
		M4.Simulate();
		
		standings.PlaceTeam(M1.getLoser(), 12);
		standings.PlaceTeam(M2.getLoser(), 12);
		standings.PlaceTeam(M3.getLoser(), 12);
		standings.PlaceTeam(M4.getLoser(), 12);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate();
		M6.Simulate();
		M7.Simulate();
		M8.Simulate();
		
		M9.setTeamA(M5.getLoser());
		M9.setTeamB(M7.getLoser());
		M10.setTeamA(M6.getLoser());
		M10.setTeamB(M8.getLoser());
		M9.Simulate();
		M10.Simulate();
		
		standings.PlaceTeam(M9.getLoser(), 8);
		standings.PlaceTeam(M10.getLoser(), 8);
		
		M11.setTeamA(M5.getWinner());
		M11.setTeamB(M6.getWinner());
		M12.setTeamA(M7.getWinner());
		M12.setTeamB(M8.getWinner());
		M11.Simulate();
		M12.Simulate();
		
		M13.setTeamA(M11.getLoser());
		M13.setTeamB(M9.getWinner());
		M14.setTeamA(M12.getLoser());
		M14.setTeamB(M10.getWinner());
		M13.Simulate();
		M14.Simulate();
		
		standings.PlaceTeam(M13.getLoser(), 6);
		standings.PlaceTeam(M14.getLoser(), 6);
		
		M15.setTeamA(M11.getWinner());
		M15.setTeamB(M12.getWinner());
		M15.Simulate();
		
		M16.setTeamA(M13.getWinner());
		M16.setTeamB(M14.getWinner());
		M16.Simulate();
		
		M17.setTeamA(M15.getLoser());
		M17.setTeamB(M16.getWinner());
		M17.Simulate();
		
		standings.PlaceTeam(M16.getLoser(), 4);
		standings.PlaceTeam(M17.getLoser(), 3);
		
		M18.setTeamA(M15.getWinner());
		M18.setTeamB(M17.getWinner());
		M18.Simulate();
		
		standings.PlaceTeam(M18.getLoser(), 2);
		standings.PlaceTeam(M18.getWinner(), 1);
		
		super.addBracketSections(S1, S2, S3, S4, S5);
		super.setChampionshipSeries(M18);
	}
}
