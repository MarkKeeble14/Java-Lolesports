package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Misc.Teams;
import QualificationDetails.QualifiedThroughGroupPlacement;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.GroupStage;

public class MainStageGroupStage extends GroupStage {
	public MainStageGroupStage(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		A.FullSimulate(super.getLabel(), tracker, true);
		B.FullSimulate(super.getLabel(), tracker, true);
		C.FullSimulate(super.getLabel(), tracker, true);
		D.FullSimulate(super.getLabel(), tracker, true);
		
		standings.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		
		standings.PlaceTeam(A.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(B.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(C.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(D.GetTeamFromPlacement(3), 12);
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								B.GetTeamFromPlacement(1),
								B.GetTeamFromPlacement(2),
								C.GetTeamFromPlacement(1),
								C.GetTeamFromPlacement(2),
								D.GetTeamFromPlacement(1),
								D.GetTeamFromPlacement(2)));
	
		SetQualified(groups, GSQ);
		
		super.setGroups(groups);
	}
	
	@Override
	public void SetQualified(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 2));

		Team B1 = teams.get(2);
		B1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 1));
		Team B2 = teams.get(3);
		B2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 2));
		
		Team C1 = teams.get(4);
		C1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 1));
		Team C2 = teams.get(5);
		C2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 2));
		
		Team D1 = teams.get(6);
		D1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 1));
		Team D2 = teams.get(7);
		D2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 2));
	}
}
