package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class KnockoutBracketCurrentFormat extends Bracket {
	public KnockoutBracketCurrentFormat(String label, Tournament partOf) {
		super(label, partOf);
	}

	public KnockoutBracketCurrentFormat(String label, TournamentMSI tournamentMSI, String rsgs) {
		super(label, tournamentMSI, rsgs);
	}

	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4));
		
		Series M1 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M1", 5, tracker);
		Series M2 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.SFS), "M2", 5, tracker);
		Series M3 = new Series(Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.FS), "M3", 5, tracker);
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolOne.Draw());
		M2.setTeamB(poolTwo.Draw());

		M1.Simulate();
		M2.Simulate();
		
		M3.setTeamA(M1.getWinner());
		M3.setTeamB(M2.getWinner());
		
		M3.Simulate();
		
		// General Tracking Stuff
		super.addSeries(M1, M2, M3);
		super.setChampionshipSeries(M3);
	}
}
