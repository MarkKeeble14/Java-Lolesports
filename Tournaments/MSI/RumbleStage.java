package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import QualificationDetails.QualifiedThroughGroupPlacement;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.GroupStage;

public class RumbleStage extends GroupStage {
	public RumbleStage(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 1;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		
		// Play out games
		A.FullSimulate(super.getLabel(), tracker, true); 
		
		standings.PlaceTeam(A.GetTeamFromPlacement(6), 6);
		standings.PlaceTeam(A.GetTeamFromPlacement(5), 5);
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								A.GetTeamFromPlacement(3),
								A.GetTeamFromPlacement(4)));
	
		SetQualified(groups, GSQ);
		
		super.setGroups(groups);
	}

	@Override
	public void SetQualified(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, A, 2));
		Team A3 = teams.get(2);
		A3.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, A, 3));
		Team A4 = teams.get(3);
		A4.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, A, 4));
	}
}
