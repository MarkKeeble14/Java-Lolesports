package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.ResultsTracker;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Tournament;

public class RegularSeasonLCS extends GroupStage {

	public RegularSeasonLCS(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Group> groups) throws Exception {
		ResultsTracker tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		
		// Play out games
		A.FullSimulate(super.getLabel(), tracker, true); 
		
		standings.PlaceTeam(A.GetTeamFromPlacement(10), 10);
		standings.PlaceTeam(A.GetTeamFromPlacement(9), 9);
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								A.GetTeamFromPlacement(3),
								A.GetTeamFromPlacement(4),
								A.GetTeamFromPlacement(5),
								A.GetTeamFromPlacement(6),
								A.GetTeamFromPlacement(7),
								A.GetTeamFromPlacement(8)));
	
		SetQualified(groups, GSQ);
		
		super.setGroups(groups);
	}

	@Override
	public void SetQualified(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 2));
		Team A3 = teams.get(2);
		A3.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 3));
		Team A4 = teams.get(3);
		A4.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 4));
		Team A5 = teams.get(4);
		A5.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 5));
		Team A6 = teams.get(5);
		A6.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 6));
		Team A7 = teams.get(6);
		A7.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 7));
		Team A8 = teams.get(7);
		A8.setNewQD(new QualifiedThroughGroupPlacement(Strings.RegularSeason, A, 8));
	}
}
