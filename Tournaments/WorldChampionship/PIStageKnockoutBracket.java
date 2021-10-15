package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Series S1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S1), "M1", 5, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4), tracker);
		Series S2 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, super.getLabel(), Strings.S1), "M2", 5, B.GetTeamFromPlacement(3), B.GetTeamFromPlacement(4), tracker);
		
		S1.Simulate();
		S2.Simulate();
		
		standings.PlaceTeam(S1.getLoser(), 20);
		standings.PlaceTeam(S2.getLoser(), 20);
		
		Series S3 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, super.getLabel(), Strings.S2), "M3", 5, A.GetTeamFromPlacement(2), S1.getWinner(), tracker);
		Series S4 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, super.getLabel(), Strings.S2), "M4", 5, B.GetTeamFromPlacement(2), S2.getWinner(), tracker);
		
		S3.Simulate();
		S4.Simulate();
		
		standings.PlaceTeam(S3.getLoser(), 18);
		standings.PlaceTeam(S4.getLoser(), 18);
		
		super.addSeries(S1, S2, S3, S4);
	}
}
