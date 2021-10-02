package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.DrawStructure;
import Classes.Group;
import Classes.GroupStage;
import Classes.Match;
import Classes.Pool;
import Classes.QualifiedThroughGroupPlacement;
import Classes.QualifiedThroughSeriesWin;
import Classes.Team;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Misc.Teams;
import Misc.Util;

public class TournamentWorldChampionship extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public TournamentWorldChampionship(String label) {
		super(label);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		super.PrintHeadline();
		
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
		Match M3 = PIKO.getMatch(3);
		Match M4 = PIKO.getMatch(4);
		List<Match> QMatches = new ArrayList<Match>(Arrays.asList(M3, M4));
		
		List<Team> Q = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(1),
								M3.getWinner(),
								M4.getWinner()));
		Pool PI = new Pool(Strings.LQualifiedPI, Q);
		
		PrintQualifiedThroughPI(PIGroups, QMatches, Q);
		
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
		
		List<Team> GSQ = new ArrayList<Team>(
					Arrays.asList(	A.GetTeamFromPlacement(1),
									A.GetTeamFromPlacement(2),
									B.GetTeamFromPlacement(1),
									B.GetTeamFromPlacement(2),
									C.GetTeamFromPlacement(1),
									C.GetTeamFromPlacement(2),
									D.GetTeamFromPlacement(1),
									D.GetTeamFromPlacement(2)));
		
		PrintQualifiedThroughGroups(groups, GSQ);
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		super.PrintChampionStats(MKnockoutBracket);
	}
	
	private void PrintQualifiedThroughPI(List<Group> groups, List<Match> matches, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Match M1 = matches.get(0);
		Match M2 = matches.get(1);
		
		Team Q1 = teams.get(0);
		Q1.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, A, 1));
		Team Q2 = teams.get(1);
		Q2.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, B, 1));
		Team Q3 = teams.get(2);
		Q3.setNewQD(new QualifiedThroughSeriesWin(Strings.PIKS, M1));
		Team Q4 = teams.get(3);
		Q4.setNewQD(new QualifiedThroughSeriesWin(Strings.PIKS, M2));

		Tournament.PrintQualified(Strings.GS, teams);
	}
	
	private void PrintQualifiedThroughGroups(List<Group> groups, List<Team> teams) {
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
		
		Tournament.PrintQualified(Strings.GS, teams);
	}
	
	private void SimulateCurrentPIGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		Util.StartSection(Strings.PIGD);
		PIGroupDraw = new GroupDrawPIStageCurrentFormat();
		PIGroupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.PIGS);
		PIGroupStage = new GroupStagePICurrentFormat();
		PIGroupStage.Simulate(groups);
	}
	
	private Bracket SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.PIKS);
		PIKnockoutBracket = new KnockoutBracketCurrentPIFormat();
		PIKnockoutBracket.Simulate(groups);
		return PIKnockoutBracket;
	}
	
	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		Util.StartSection(Strings.MSGD);
		MGroupDraw = new GroupDrawMainStageCurrentFormat();
		MGroupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		Util.StartSection(Strings.MSGS);
		MGroupStage = new GroupStageMainCurrentFormat();
		MGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		Util.StartSection(Strings.MSKS);
		MKnockoutBracket = new KnockoutBracketDoubleElimFormat();
		MKnockoutBracket.Simulate(groups);
	}
	
	public void SimulateCurrentWorldsState() throws Exception {
		Group PA = new Group(Strings.LFirstGroup, 5, Teams.LNG, Teams.HLE, Teams.INF, Teams.PCE, Teams.RED);
		Group PB = new Group(Strings.LSecondGroup, 5, Teams.C9, Teams.BYG, Teams.UOL, Teams.GS, Teams.DFM);
		List<Group> PIGroups = new ArrayList<>(Arrays.asList(PA, PB));
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		
		Pool PI = new Pool(Strings.LQualifiedPI, new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
						PB.GetTeamFromPlacement(1),
						PIKO.getMatch(3).getWinner(),
						PIKO.getMatch(4).getWinner())));
		
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
		
		super.PrintChampionStats(MKnockoutBracket);
	}
}
