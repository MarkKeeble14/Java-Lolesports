package WorldChampionship2VS3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.BracketSection;
import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.Bracket;

public class MainStageKnockoutBracket2VS3 extends Bracket {
	public MainStageKnockoutBracket2VS3(String label, Tournament partOf) {
		super(label, partOf);
	}

	public MainStageKnockoutBracket2VS3(String label, Tournament partOf, String fedThrough) {
		super(label, partOf, fedThrough);
	}
	
	int requiredNumberOfGroups = 4;
	
	@Override
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
		
		BracketSection S1 = new BracketSection(Strings.R34, 1);
		BracketSection S2 = new BracketSection(Strings.QFS, 2);
		BracketSection S3 = new BracketSection(Strings.SFS, 3);
		BracketSection S4 = new BracketSection(Strings.FS, 4);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(Strings.LPoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		Series M1 = new Series(1, 3, tracker);
		Series M2 = new Series(2, 3, tracker);
		Series M3 = new Series(3, 3, tracker);
		Series M4 = new Series(4, 3, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		Series M7 = new Series(7, 5, tracker);
		Series M8 = new Series(8, 5, tracker);
		S2.addSeries(M5, M6, M7, M8);
		Series M9 = new Series(9, 5, tracker);
		Series M10 = new Series(10, 5, tracker);
		S3.addSeries(M9, M10);
		Series M11 = new Series(11, 5, tracker);
		S4.addSeries(M11);
		
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
		
		standings.PlaceTeam(M5.getLoser(), 8);
		standings.PlaceTeam(M6.getLoser(), 8);
		standings.PlaceTeam(M7.getLoser(), 8);
		standings.PlaceTeam(M8.getLoser(), 8);
		
		M9.setTeamA(M5.getWinner());
		M9.setTeamB(M6.getWinner());
		M10.setTeamA(M7.getWinner());
		M10.setTeamB(M8.getWinner());
		M9.Simulate();
		M10.Simulate();
		
		standings.PlaceTeam(M9.getLoser(), 4);
		standings.PlaceTeam(M10.getLoser(), 4);
		
		M11.setTeamA(M9.getWinner());
		M11.setTeamB(M10.getWinner());
		M11.Simulate();
		
		standings.PlaceTeam(M11.getLoser(), 2);
		standings.PlaceTeam(M11.getWinner(), 1);
		
		super.addBracketSections(S1, S2, S3, S4);
		super.setChampionshipSeries(M11);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
