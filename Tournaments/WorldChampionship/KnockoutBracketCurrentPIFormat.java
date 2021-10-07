package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.Group;
import Classes.Match;
import Classes.RegionalWLTracker;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class KnockoutBracketCurrentPIFormat extends Bracket {
	public KnockoutBracketCurrentPIFormat(Tournament partOf) {
		super(partOf);
	}

	public KnockoutBracketCurrentPIFormat(TournamentWorldChampionship tournamentWorldChampionship, String pigs) {
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
		
		Match M1 = new Match("M1", A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4));
		Match M2 = new Match("M2", B.GetTeamFromPlacement(3), B.GetTeamFromPlacement(4));
		
		M1.Simulate(label, tracker, 5);
		M2.Simulate(label, tracker, 5);

		Match M3 = new Match("M3", B.GetTeamFromPlacement(2), M1.getWinner());
		Match M4 = new Match("M4", A.GetTeamFromPlacement(2), M2.getWinner());
		M3.Simulate(Strings.PIKS, tracker, 5);
		M4.Simulate(Strings.PIKS, tracker, 5);
		
		super.addMatches(M1, M2, M3, M4);
	}
}
