package WorldChampionship;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Teams;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class GroupStageMainCurrentFormat extends GroupStage {
	public GroupStageMainCurrentFormat(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Adding results to matches: A
		A.SetupMatches(super.getLabel(), tracker);
		A.addResultToGameMatchup(Teams.DK, Teams.FPX);
		A.addResultToGameMatchup(Teams.RGE, Teams.C9);
		A.addResultToGameMatchup(Teams.DK, Teams.C9);
		A.addResultToGameMatchup(Teams.FPX, Teams.C9);
		A.addResultToGameMatchup(Teams.DK, Teams.RGE);
		A.addResultToGameMatchup(Teams.FPX, Teams.RGE);
		A.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		// Play out games
		B.FullSimulate(super.getLabel(), tracker, true); 
		C.FullSimulate(super.getLabel(), tracker, true); 
		D.FullSimulate(super.getLabel(), tracker, true); 
		
		super.setGroups(groups);
	}
}
