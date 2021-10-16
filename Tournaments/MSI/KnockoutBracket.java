package MSI;

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
import TournamentComponents.Bracket;

public class KnockoutBracket extends Bracket {
	public KnockoutBracket(String label, Tournament partOf) {
		super(label, partOf);
	}

	public KnockoutBracket(String label, TournamentMSI tournamentMSI, String rsgs) {
		super(label, tournamentMSI, rsgs);
	}

	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(3), A.GetTeamFromPlacement(4));
		
		BracketSection S1 = new BracketSection(Strings.SFS, 1);
		BracketSection S2 = new BracketSection(Strings.FS, 2);
		
		Series M1 = new Series(1, 5, tracker);
		Series M2 = new Series(2, 5, tracker);
		S1.addSeries(M1, M2);
		Series M3 = new Series(3, 5, tracker);
		S2.addSeries(M3);
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolOne.Draw());
		M2.setTeamB(poolTwo.Draw());

		M1.Simulate();
		M2.Simulate();
		
		standings.PlaceTeam(M1.getLoser(), 4);
		standings.PlaceTeam(M2.getLoser(), 4);
		
		M3.setTeamA(M1.getWinner());
		M3.setTeamB(M2.getWinner());
		
		M3.Simulate();
		
		standings.PlaceTeam(M3.getLoser(), 2);
		standings.PlaceTeam(M3.getWinner(), 1);
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2);
		super.setChampionshipSeries(M3);
	}
}
