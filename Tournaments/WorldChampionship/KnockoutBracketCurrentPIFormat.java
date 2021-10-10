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
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class KnockoutBracketCurrentPIFormat extends Bracket {
	public KnockoutBracketCurrentPIFormat(Tournament partOf) {
		super(partOf);
	}

	public KnockoutBracketCurrentPIFormat(Tournament tournamentWorldChampionship, String pigs) {
		super(tournamentWorldChampionship, pigs);
	}

	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		super.setLabel(label);
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Series S1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, label, Strings.S1), "M1", 5, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4), tracker);
		Series S2 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, label, Strings.S1), "M2", 5, B.GetTeamFromPlacement(3), B.GetTeamFromPlacement(4), tracker);
		
		S1.Simulate();
		S2.Simulate();
		
		Series S3 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, label, Strings.S2), "M3", 5, A.GetTeamFromPlacement(2), S1.getWinner(), tracker);
		Series S4 = new Series(Strings.Concat( Strings.BasicBridgeWSpace, label, Strings.S2), "M4", 5, B.GetTeamFromPlacement(2), S2.getWinner(), tracker);
		
		S3.Simulate();
		S4.Simulate();
		
		super.addSeries(S1, S2, S3, S4);
	}
}
