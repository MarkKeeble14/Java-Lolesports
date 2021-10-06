package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.DrawStructure;
import Classes.EOTStandings;
import Classes.Group;
import Classes.GroupStage;
import Classes.Pool;
import Classes.QualifiedThroughGroupPlacement;
import Classes.Team;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Misc.Util;

public class TournamentMSI extends Tournament {
	DrawStructure groupDraw;
	GroupStage groupStage;
	GroupStage rumbleStage;
	Bracket knockoutBracket;
	
	public TournamentMSI(String label) {
		super(label);
	}

	int requiredNumberOfPools = 2;
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		super.PrintHeadline();
		
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		EOTStandings eots = super.getEots();
		
		// Setting Up Pools
		Pool P1 = pools.get(0);
		Pool P2 = pools.get(1);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 4); 
		Group B = new Group(Strings.LSecondGroup, 4); 
		Group C = new Group(Strings.LThirdGroup, 4);
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
		
		PrintQualified(groups, Q);
		
		Group RumbleGroup = new Group(Strings.LRumble, Q);
		List<Group> groups1 = new ArrayList<Group>(Arrays.asList(RumbleGroup));
		
		SimulateCurrentRumbleStage(groups1);
		
		// Place Teams
		eots.PlaceTeam(RumbleGroup.GetTeamFromPlacement(6), 6);
		eots.PlaceTeam(RumbleGroup.GetTeamFromPlacement(5), 5);
		//
		
		SimulateCurrentKnockoutStage(groups1);
		
		// Place Teams
		eots.PlaceTeam(knockoutBracket.getMatch(1).getLoser(), 4);
		eots.PlaceTeam(knockoutBracket.getMatch(2).getLoser(), 4);
		eots.PlaceTeam(knockoutBracket.getMatch(3).getLoser(), 2);
		eots.PlaceTeam(knockoutBracket.getMatch(3).getWinner(), 1);
		//
	}
	
	private void PrintQualified(List<Group> groups, List<Team> teams) {
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

		Tournament.PrintQualified(Strings.GS, teams, false);
	}

	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		String section = Strings.GSGD;
		Util.StartSection(section, false);
		groupDraw = new GroupDrawGroupStageCurrentFormat(this);
		super.addDrawStructure(groupDraw);
		groupDraw.Simulate(section, groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		String section = Strings.GS;
		Util.StartSection(section, false);
		groupStage = new GroupStageGroupStageCurrentFormat(this);
		super.addGroupStage(groupStage);
		groupStage.Simulate(section, groups);
	}
	
	private void SimulateCurrentRumbleStage(List<Group> groups) throws Exception {
		String section = Strings.RSGS;
		Util.StartSection(section, false);
		rumbleStage = new GroupStageRumbleStageCurrentFormat(this);
		super.addGroupStage(rumbleStage);
		rumbleStage.Simulate(section, groups);
	}
	
	private void SimulateCurrentKnockoutStage(List<Group> groups) throws Exception {
		String section = Strings.RSKS;
		Util.StartSection(section, false);
		knockoutBracket = new KnockoutBracketCurrentFormat(this);
		knockoutBracket.Simulate(section, groups);
	}
}
