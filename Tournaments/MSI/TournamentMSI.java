package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Misc.Util;
import QualificationDetails.QualifiedThroughGroupPlacement;
import StatsTracking.EOTStandings;
import Teams.Team;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;

public class TournamentMSI extends Tournament {
	DrawStructure groupDraw;
	GroupStage groupStage;
	GroupStage rumbleStage;
	Bracket knockoutBracket;
	
	public TournamentMSI() {
		super(Strings.LMSI);
	}

	int requiredNumberOfPools = 2;
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		EOTStandings eots = super.getEots();
		
		Setup();
		
		// Setting Up Pools
		Pool P1 = pools.get(0);
		Pool P2 = pools.get(1);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, groupStage); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, groupStage); 
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, groupStage);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		// Place Teams
		eots.PlaceTeam(A.GetTeamFromPlacement(4), 12);
		eots.PlaceTeam(B.GetTeamFromPlacement(4), 12);
		eots.PlaceTeam(C.GetTeamFromPlacement(4), 12);
		eots.PlaceTeam(A.GetTeamFromPlacement(3), 9);
		eots.PlaceTeam(B.GetTeamFromPlacement(3), 9);
		eots.PlaceTeam(C.GetTeamFromPlacement(3), 9);
		// 
		
		List<Team> Q = new ArrayList<Team>(Arrays.asList(	
				A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2),
				B.GetTeamFromPlacement(1), B.GetTeamFromPlacement(2),
				C.GetTeamFromPlacement(1), C.GetTeamFromPlacement(2)));
		
		SetQDsForGroups(groups, Q);
		
		Group RumbleGroup = new Group(Strings.LRumble, Q);
		List<Group> groups1 = new ArrayList<Group>(Arrays.asList(RumbleGroup));
		
		SimulateCurrentRumbleStage(groups1);
		
		// Place Teams
		eots.PlaceTeam(RumbleGroup.GetTeamFromPlacement(6), 6);
		eots.PlaceTeam(RumbleGroup.GetTeamFromPlacement(5), 5);
		//
		
		List<Team> QR = new ArrayList<Team>(Arrays.asList(	
				RumbleGroup.GetTeamFromPlacement(1), RumbleGroup.GetTeamFromPlacement(2),
				RumbleGroup.GetTeamFromPlacement(3), RumbleGroup.GetTeamFromPlacement(4)));
		SetQDsForRumble(groups1, QR);
		
		SimulateCurrentKnockoutStage(groups1);
		
		// Place Teams
		eots.PlaceTeam(knockoutBracket.getSeries(1).getLoser(), 4);
		eots.PlaceTeam(knockoutBracket.getSeries(2).getLoser(), 4);
		eots.PlaceTeam(knockoutBracket.getSeries(3).getLoser(), 2);
		eots.PlaceTeam(knockoutBracket.getSeries(3).getWinner(), 1);
		//
		
		ConcludeTournament();
	}
	
	@Override
	public void Setup() {
		groupDraw = new GroupDrawGroupStageCurrentFormat(Strings.GSGD, this);
		super.addDrawStructure(groupDraw);
		
		groupStage = new GroupStageGroupStageCurrentFormat(Strings.GS, this);
		super.addGroupStage(groupStage);
		
		rumbleStage = new GroupStageRumbleStageCurrentFormat(Strings.LRumble, this);
		super.addGroupStage(rumbleStage);
		
		knockoutBracket = new KnockoutBracketCurrentFormat(Strings.RSKS, this, Strings.LRumble);
		super.addBracket(knockoutBracket);
	}

	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		groupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		groupStage.Simulate(groups);
	}
	
	private void SimulateCurrentRumbleStage(List<Group> groups) throws Exception {
		rumbleStage.Simulate(groups);
	}
	
	private void SimulateCurrentKnockoutStage(List<Group> groups) throws Exception {
		knockoutBracket.Simulate(groups);
	}
	
	private void SetQDsForGroups(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		
		Team Q1 = teams.get(0);
		Q1.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, A, 1));
		Team Q2 = teams.get(1);
		Q2.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, A, 2));
		Team Q3 = teams.get(2);
		Q3.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, B, 1));
		Team Q4 = teams.get(3);
		Q4.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, B, 2));
		Team Q5 = teams.get(4);
		Q5.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, C, 1));
		Team Q6 = teams.get(5);
		Q6.setNewQD(new QualifiedThroughGroupPlacement(Strings.GS, C, 2));
	}
	
	private void SetQDsForRumble(List<Group> groups, List<Team> teams) {
		Group g = groups.get(0);
		
		Team Q1 = teams.get(0);
		Q1.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, g, 1));
		Team Q2 = teams.get(1);
		Q2.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, g, 2));
		Team Q3 = teams.get(2);
		Q3.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, g, 3));
		Team Q4 = teams.get(3);
		Q4.setNewQD(new QualifiedThroughGroupPlacement(Strings.LRumble, g, 4));
	}
}
