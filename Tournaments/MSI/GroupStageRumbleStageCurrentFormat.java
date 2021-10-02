package MSI;

import java.util.List;

import Classes.Group;
import Classes.GroupStage;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStageRumbleStageCurrentFormat extends GroupStage {
	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		
		// Play out games
		A.FullSimulate(Strings.MSGS, 2, true); 
		
		super.setGroups(groups);
	}
}
