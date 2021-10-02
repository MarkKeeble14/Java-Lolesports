package MSI;

import java.util.List;

import Classes.Group;
import Classes.GroupStage;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStageGroupStageCurrentFormat extends GroupStage {
	int requiredNumberOfGroups = 3;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 2, true); 
		B.FullSimulate(Strings.MSGS, 2, true); 
		C.FullSimulate(Strings.MSGS, 2, true); 
		
		super.setGroups(groups);
		super.PrintGroups();
	}
}
