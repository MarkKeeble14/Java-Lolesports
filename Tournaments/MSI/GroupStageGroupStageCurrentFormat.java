package MSI;

import java.util.List;

import Classes.Group;
import Classes.GroupStage;
import Classes.RegionalWLTracker;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;

public class GroupStageGroupStageCurrentFormat extends GroupStage {
	public GroupStageGroupStageCurrentFormat(Tournament partOf) {
		super(partOf);
	}

	int requiredNumberOfGroups = 3;
	
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
		Group C = groups.get(2);
		
		// Play out games
		A.FullSimulate(label, tracker, 2, true, this); 
		B.FullSimulate(label, tracker,  2, true, this); 
		C.FullSimulate(label, tracker,  2, true, this); 
		
		super.setGroups(groups);
		super.PrintGroups();
	}
}
