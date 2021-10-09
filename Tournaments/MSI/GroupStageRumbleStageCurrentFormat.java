package MSI;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class GroupStageRumbleStageCurrentFormat extends GroupStage {
	public GroupStageRumbleStageCurrentFormat(Tournament partOf) {
		super(partOf);
	}

	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		super.setLabel(label);
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		// Play out games
		A.FullSimulate(label, tracker, 2, true, this); 
		
		super.setGroups(groups);
	}
}
