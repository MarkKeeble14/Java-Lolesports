package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningMatches.Game;
import DefiningMatches.Series;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Tournament;

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
		
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.S1, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.S2, 2);
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Team C9 = B.GetTeamFromPlacement(2);
		Team GS = B.GetTeamFromPlacement(3);
		Team BYG = B.GetTeamFromPlacement(4);
		Team HLE = A.GetTeamFromPlacement(2);
		Team PCE = A.GetTeamFromPlacement(3);
		Team RED = A.GetTeamFromPlacement(4);
		
		Series M1 = new Series(1, 5, PCE, RED, tracker);
		Series M2 = new Series(2, 5, GS, BYG, tracker);
		S1.addSeries(M1, M2);
		
		M1.setResult(PCE, RED, 3, 2);
		M2.setResult(GS, BYG, 2, 3);
		
		standings.PlaceTeamDuringBacketStage(M1.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M2.getLoser(), false);
		
		Series M3 = new Series(3, 5, C9, M1.getWinner(), tracker);
		Series M4 = new Series(4, 5, HLE, M2.getWinner(), tracker);
		S2.addSeries(M3, M4);
		
		M3.setResult(C9, PCE, 3, 0);
		M4.setResult(HLE, BYG, 3, 0);
		
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), false);
		
		super.addBracketSections(S1, S2);
		super.setChampionshipSeries(M3, M4);
		SetQualified(standings);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
