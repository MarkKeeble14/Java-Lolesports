package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.ResultsTracker;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Tournament;

public class LockinGroupStage extends GroupStage {

	public LockinGroupStage(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Group> groups) throws Exception {
		ResultsTracker tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Play out games
		A.FullSimulate(super.getLabel(), tracker, true); 
		B.FullSimulate(super.getLabel(), tracker, true); 
		
		standings.PlaceTeam(A.GetTeamFromPlacement(5), 10);
		standings.PlaceTeam(B.GetTeamFromPlacement(5), 10);
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								A.GetTeamFromPlacement(3),
								A.GetTeamFromPlacement(4),
								B.GetTeamFromPlacement(1),
								B.GetTeamFromPlacement(2),
								B.GetTeamFromPlacement(3),
								B.GetTeamFromPlacement(4)));
	
		SetQualified(groups, GSQ);
		
		super.setGroups(groups);
	}

	@Override
	public void SetQualified(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, A, 2));
		Team A3 = teams.get(2);
		A3.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, A, 3));
		Team A4 = teams.get(3);
		A4.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, A, 4));

		Team B1 = teams.get(4);
		B1.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, B, 1));
		Team B2 = teams.get(5);
		B2.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, B, 2));
		Team B3 = teams.get(6);
		B3.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, B, 3));
		Team B4 = teams.get(7);
		B4.setNewQD(new QualifiedThroughGroupPlacement(Strings.LockinGroupStage, B, 4));
	}

}
