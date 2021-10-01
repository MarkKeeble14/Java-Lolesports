package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.DrawStructure;
import Classes.Group;
import Classes.GroupStageFormat;
import Classes.Pool;
import Classes.Team;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Tournaments.TournamentFormat;
import WorldChampionship.GroupDrawPIStageCurrentFormat;
import WorldChampionship.GroupStagePICurrentFormat;

public class TournamentMSI extends TournamentFormat {
	int requiredNumberOfPools = 2;
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		System.out.println("Tournament Start!");
		
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
		
		List<Team> Q = new ArrayList<Team>(Arrays.asList(A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2),
										B.GetTeamFromPlacement(1), B.GetTeamFromPlacement(2),
										C.GetTeamFromPlacement(1), C.GetTeamFromPlacement(2)));
		Group RumbleGroup = new Group(Strings.LRumble, Q);
		List<Group> groups1 = new ArrayList<Group>(Arrays.asList(RumbleGroup));
		
		SimulateCurrentRumbleStage(groups1);
		
		SimulateCurrentKnockoutStage(groups1);
	}

	private void SimulateCurrentKnockoutStage(List<Group> groups) throws Exception {
		Bracket bracket = new KnockoutBracketCurrentFormat();
		bracket.Simulate(groups);
	}

	private void SimulateCurrentRumbleStage(List<Group> groups) throws Exception {
		GroupStageFormat GS = new GroupStageRumbleStageCurrentFormat();
		GS.Simulate(groups);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		GroupStageFormat GS = new GroupStageGroupStageCurrentFormat();
		GS.Simulate(groups);
	}

	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		DrawStructure draw = new GroupDrawGroupStageCurrentFormat();
		draw.Simulate(groups, pools);
	}
}
