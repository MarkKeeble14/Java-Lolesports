package WorldChampionship;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class GroupStageMainCurrentFormat extends GroupStage {
	public GroupStageMainCurrentFormat(Tournament partOf) {
		super(partOf);
	}

	int requiredNumberOfGroups = 4;
	
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
		Group D = groups.get(3);
		
		// Play out games
		A.FullSimulate(label, tracker, 2, true, this); 
		B.FullSimulate(label, tracker, 2, true, this); 
		C.FullSimulate(label, tracker, 2, true, this); 
		D.FullSimulate(label, tracker, 2, true, this); 
		
		super.setGroups(groups);
	}
}
