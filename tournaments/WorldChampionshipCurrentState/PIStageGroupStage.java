package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningTeams.Team;
import StaticVariables.Strings;
import StaticVariables.Teams;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Tournament;

public class PIStageGroupStage extends GroupStage {
	public PIStageGroupStage(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		A.SetupMatches(super.getLabel(), tracker);
		B.SetupMatches(super.getLabel(), tracker);
		
		// Play out games
		// Day 1
		A.addResultToGameMatchup(Teams.HLE, Teams.LNG, Teams.LNG, Teams.HLE, true);
		A.addResultToGameMatchup(Teams.INF, Teams.RED, Teams.RED, Teams.INF, true);
		A.addResultToGameMatchup(Teams.LNG, Teams.PCE, Teams.LNG, Teams.PCE, true);
		A.addResultToGameMatchup(Teams.HLE, Teams.INF, Teams.HLE, Teams.INF, true);
		//
		B.addResultToGameMatchup(Teams.UOL, Teams.DFM, Teams.DFM, Teams.UOL, true);
		B.addResultToGameMatchup(Teams.GS, Teams.BYG, Teams.GS, Teams.BYG, true);
		B.addResultToGameMatchup(Teams.DFM, Teams.C9, Teams.C9, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.UOL, Teams.GS, Teams.GS, Teams.UOL, true);
		
		// Day 2
		A.addResultToGameMatchup(Teams.PCE, Teams.HLE, Teams.HLE, Teams.PCE, true);
		A.addResultToGameMatchup(Teams.RED, Teams.LNG, Teams.LNG, Teams.RED, true);
		A.addResultToGameMatchup(Teams.INF, Teams.PCE, Teams.PCE, Teams.INF, true);
		A.addResultToGameMatchup(Teams.RED, Teams.HLE, Teams.HLE, Teams.RED, true);
		//
		B.addResultToGameMatchup(Teams.BYG, Teams.C9, Teams.C9, Teams.BYG, true);
		B.addResultToGameMatchup(Teams.GS, Teams.DFM, Teams.DFM, Teams.GS, true);
		B.addResultToGameMatchup(Teams.BYG, Teams.UOL, Teams.BYG, Teams.UOL, true);
		B.addResultToGameMatchup(Teams.C9, Teams.GS, Teams.C9, Teams.GS, true);
		
		// Day 3
		A.addResultToGameMatchup(Teams.LNG, Teams.INF, Teams.LNG, Teams.INF, true);
		A.addResultToGameMatchup(Teams.PCE, Teams.RED, Teams.PCE, Teams.RED, true);
		//
		B.addResultToGameMatchup(Teams.DFM, Teams.BYG, Teams.DFM, Teams.BYG, true);
		B.addResultToGameMatchup(Teams.C9, Teams.UOL, Teams.UOL, Teams.C9, true);
		
		// Before the group is resolved, call this method
		A.SimulatePresetMatches(super.getLabel(), tracker, false);
		B.SimulatePresetMatches(super.getLabel(), tracker, false);
		
		// Tiebreakers
		B.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.C9, Teams.DFM, Teams.DFM, Teams.C9);
		B.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.BYG, Teams.UOL, Teams.BYG, Teams.UOL);
		
		SetQualified(groups, standings);
		
		super.setGroups(groups);
	}
}
