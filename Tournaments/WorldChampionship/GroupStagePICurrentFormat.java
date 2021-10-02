package WorldChampionship;

import java.util.List;

import Classes.Group;
import Classes.GroupStage;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;

public class GroupStagePICurrentFormat extends GroupStage {
	int requiredNumberOfGroups = 2;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Play out games
		A.FullSimulate(Strings.PIGS, 1, true); 
		B.FullSimulate(Strings.PIGS, 1, true); 
		
		super.setGroups(groups);
		super.PrintGroups();
	}
}
