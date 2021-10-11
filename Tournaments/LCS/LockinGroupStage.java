package LCS;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class LockinGroupStage extends GroupStage {

	public LockinGroupStage(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		super.setLabel(label);
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Play out games
		A.FullSimulate(label, tracker, true); 
		B.FullSimulate(label, tracker, true); 
		
		super.setGroups(groups);
	}

}
