package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.BracketSection;
import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Game;
import Matches.Series;
import Misc.Strings;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class PIStageKnockoutBracket extends Bracket {
	public PIStageKnockoutBracket(String label, Tournament partOf) {
		super(label, partOf);
	}

	public PIStageKnockoutBracket(String label, Tournament tournamentWorldChampionship, String pigs) {
		super(label, tournamentWorldChampionship, pigs);
	}

	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
		String s1 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S1);
		String s2 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S2);
		
		BracketSection S1 = new BracketSection(s1);
		BracketSection S2 = new BracketSection(s2);
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Series M1 = new Series(1, 5, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4), tracker);
		Series M2 = new Series(2, 5, B.GetTeamFromPlacement(3), B.GetTeamFromPlacement(4), tracker);
		S1.addSeries(M1, M2);
		M1.Simulate();
		M2.Simulate();
		
		standings.PlaceTeam(M1.getLoser(), 20);
		standings.PlaceTeam(M2.getLoser(), 20);
		
		Series M3 = new Series(3, 5, A.GetTeamFromPlacement(2), M1.getWinner(), tracker);
		Series M4 = new Series(4, 5, B.GetTeamFromPlacement(2), M2.getWinner(), tracker);
		S2.addSeries(M3, M4);
		M3.Simulate();
		M4.Simulate();
		
		standings.PlaceTeam(M3.getLoser(), 18);
		standings.PlaceTeam(M4.getLoser(), 18);

		super.addBracketSections(S1, S2);
	}
}
