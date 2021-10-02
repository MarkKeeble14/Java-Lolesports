package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.DrawStructure;
import Classes.Group;
import Classes.GroupStage;
import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.QualifiedThroughGroupPlacement;
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
		
		List<Team> Q = new ArrayList<Team>(Arrays.asList(	
				A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2),
				B.GetTeamFromPlacement(1), B.GetTeamFromPlacement(2),
				C.GetTeamFromPlacement(1), C.GetTeamFromPlacement(2)));
		
		PrintQualified(groups, Q);
		
		Group RumbleGroup = new Group(Strings.LRumble, Q);
		List<Group> groups1 = new ArrayList<Group>(Arrays.asList(RumbleGroup));
		
		SimulateCurrentRumbleStage(groups1);
		
		SimulateCurrentKnockoutStage(groups1);
		
		super.PrintChampionStats(knockoutBracket);
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

		Tournament.PrintQualified(Strings.GS, teams);
	}

	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		Util.StartSection(Strings.GSGD);
		groupDraw = new GroupDrawGroupStageCurrentFormat();
		groupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.GS);
		rumbleStage = new GroupStageGroupStageCurrentFormat();
		rumbleStage.Simulate(groups);
	}
	
	private void SimulateCurrentRumbleStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.RSGS);
		groupStage = new GroupStageRumbleStageCurrentFormat();
		groupStage.Simulate(groups);
	}
	
	private void SimulateCurrentKnockoutStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.RSKS);
		knockoutBracket = new KnockoutBracketCurrentFormat();
		knockoutBracket.Simulate(groups);
	}
}
