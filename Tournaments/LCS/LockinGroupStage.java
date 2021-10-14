package LCS;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class LockinGroupStage extends GroupStage {

	public LockinGroupStage(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Group> groups) throws Exception {
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Play out games
		A.FullSimulate(super.getLabel(), tracker, true); 
		B.FullSimulate(super.getLabel(), tracker, true); 
		
		super.setGroups(groups);
	}

}
