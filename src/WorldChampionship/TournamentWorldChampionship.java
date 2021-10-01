package WorldChampionship;

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
import Misc.Teams;
import Tournaments.TournamentFormat;

public class TournamentWorldChampionship extends TournamentFormat {
	int requiredNumberOfPools = 5;
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		System.out.println("Tournament Start!");
		
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		// Setting Up Pools
		Pool PIPoolOne = pools.get(0);
		Pool PIPoolTwo = pools.get(1);
		List<Pool> PIPools = new ArrayList<Pool>(Arrays.asList(PIPoolOne, PIPoolTwo));
		
		// Setting up PI Groups
		Group PA = new Group(Strings.LFirstGroup, 5); 
		Group PB = new Group(Strings.LSecondGroup, 5);
		List<Group> PIGroups = new ArrayList<Group>(Arrays.asList(PA, PB));
	  
		SimulateCurrentPIGroupDraw(PIGroups, PIPools);
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		
		Pool PI = new Pool(Strings.LQualifiedPI, new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(1),
								PIKO.GetMatch(3).getWinner(),
								PIKO.GetMatch(4).getWinner())));
		Pool P1 = pools.get(2);
		Pool P2 = pools.get(3);
		Pool P3 = pools.get(4);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(PI, P1, P2, P3)); 
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4); 
		Group B = new Group(Strings.LSecondGroup, 4);
		Group C = new Group(Strings.LThirdGroup, 4); 
		Group D = new Group(Strings.LFourthGroup, 4);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
	}

	public static void SimulateCurrentWorldsState() throws Exception {
		System.out.println("\nSimulating Current World's State");
		
		Group PA = new Group(Strings.LFirstGroup, 5, Teams.LNG, Teams.HLE, Teams.INF, Teams.PCE, Teams.RED);
		Group PB = new Group(Strings.LSecondGroup, 5, Teams.C9, Teams.BYG, Teams.UOL, Teams.GS, Teams.DFM);
		List<Group> PIGroups = new ArrayList<>(Arrays.asList(PA, PB));
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		
		Pool PI = new Pool(Strings.LQualifiedPI, new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
						PB.GetTeamFromPlacement(1),
						PIKO.GetMatch(3).getWinner(),
						PIKO.GetMatch(4).getWinner())));
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 4, Teams.DK, Teams.FPX, Teams.RGE); 
		Group B = new Group(Strings.LSecondGroup, 4, Teams.EDG, Teams.O100T, Teams.T1);
		Group C = new Group(Strings.LThirdGroup, 4, Teams.PSG, Teams.FNC, Teams.RNG); 
		Group D = new Group(Strings.LFourthGroup, 4, Teams.MAD, Teams.GEN, Teams.TL);
		
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		ArrayList<Pool> pools = new ArrayList<Pool>(Arrays.asList(PI));
		
		// Draw PI Pool Teams into Groups
		A.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		C.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 2, new ArrayList<Team>()));
		D.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 3, new ArrayList<Team>()));
		pools.remove(0);
		
		SimulateCurrentGroupStage(groups);
		
		// Main Knockout Stage
		SimulateCurrentDrawKO(groups);
	}
	
	private static void SimulateCurrentPIGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		DrawStructure draw = new GroupDrawPIStageCurrentFormat();
		draw.Simulate(groups, pools);
	}
	
	private static void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		GroupStageFormat GS = new GroupStagePICurrentFormat();
		GS.Simulate(groups);
	}
	
	private static void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		DrawStructure draw = new GroupDrawMainStageCurrentFormat();
		draw.Simulate(groups, pools);
	}
	
	private static Bracket SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		Bracket bracket = new KnockoutBracketCurrentPIFormat();
		bracket.Simulate(groups);
		return bracket;
	}
	
	private static void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		GroupStageFormat GS = new GroupStageMainCurrentFormat();
		GS.Simulate(groups);
	}
	
	private static void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		Bracket bracket = new KnockoutBracketCurrentFormat();
		bracket.Simulate(groups);
	}
	
}
