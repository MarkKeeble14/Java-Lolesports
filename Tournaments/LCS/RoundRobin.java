package LCS;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.GroupStage;

public class RoundRobin extends GroupStage {

	public RoundRobin(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		super.setLabel(label);
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		// Play out games
		A.FullSimulate(label, tracker, true); 
		
		super.setGroups(groups);
	}
}
