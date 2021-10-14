package MSI;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class GroupStageGroupStageCurrentFormat extends GroupStage {
	public GroupStageGroupStageCurrentFormat(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 3;
	
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
		
		// Play out games
		A.FullSimulate(super.getLabel(), tracker, true); 
		B.FullSimulate(super.getLabel(), tracker, true); 
		C.FullSimulate(super.getLabel(), tracker, true); 
		
		super.setGroups(groups);
	}
}
