package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Bracket;
import Classes.DrawStructure;
import Classes.EOTStandings;
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
		
		EOTStandings eots = super.getEots();
		
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
		
		// Place Teams
		eots.PlaceTeam(PA.GetTeamFromPlacement(5), 22);
		eots.PlaceTeam(PB.GetTeamFromPlacement(5), 22);
		//
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		Match M3 = PIKO.getMatch(3);
		Match M4 = PIKO.getMatch(4);
		
		// Place Teams
		eots.PlaceTeam(PIKO.getMatch(1).getLoser(), 20);
		eots.PlaceTeam(PIKO.getMatch(2).getLoser(), 20);
		eots.PlaceTeam(PIKO.getMatch(3).getLoser(), 18);
		eots.PlaceTeam(PIKO.getMatch(4).getLoser(), 18);		
		//
		
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
		
		// Place Teams
		eots.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		//
		eots.PlaceTeam(A.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(B.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(C.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(D.GetTeamFromPlacement(3), 12);
		//
		
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
		
		// Place Teams
		eots.PlaceTeam(MKnockoutBracket.getMatch(1).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(2).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(3).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(4).getLoser(), 8);
		//
		eots.PlaceTeam(MKnockoutBracket.getMatch(5).getLoser(), 4);
		eots.PlaceTeam(MKnockoutBracket.getMatch(6).getLoser(), 4);
		//
		eots.PlaceTeam(MKnockoutBracket.getMatch(7).getLoser(), 2);
		eots.PlaceTeam(MKnockoutBracket.getMatch(7).getWinner(), 1);
		//
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

		Tournament.PrintQualified(Strings.GS, teams, false);
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
		
		Tournament.PrintQualified(Strings.GS, teams, false);
	}
	
	private void SimulateCurrentPIGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		String section = Strings.PIGD;
		Util.StartSection(section, false);
		PIGroupDraw = new GroupDrawPIStageCurrentFormat(this);
		super.addDrawStructure(PIGroupDraw);
		PIGroupDraw.Simulate(section, groups, pools);
	}
	
	private void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		String section = Strings.PIGS;
		Util.StartSection(section, false);
		PIGroupStage = new GroupStagePICurrentFormat(this);
		super.addGroupStage(PIGroupStage);
		PIGroupStage.Simulate(section, groups);
	}
	
	private Bracket SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		String section = Strings.PIKS;
		Util.StartSection(section, false);
		PIKnockoutBracket = new KnockoutBracketCurrentPIFormat(this);
		super.addBracket(PIKnockoutBracket);
		PIKnockoutBracket.Simulate(section, groups);
		return PIKnockoutBracket;
	}
	
	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		String section = Strings.MSGD;
		Util.StartSection(section, false);
		MGroupDraw = new GroupDrawMainStageCurrentFormat(this);
		super.addDrawStructure(MGroupDraw);
		MGroupDraw.Simulate(section, groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		String section = Strings.MSGS;
		Util.StartSection(section, false);
		MGroupStage = new GroupStageMainCurrentFormat(this);
		super.addGroupStage(MGroupStage);
		MGroupStage.Simulate(section, groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		String section = Strings.MSKS;
		Util.StartSection(section, false);
		MKnockoutBracket = new KnockoutBracketCurrentFormat(this);
		super.addBracket(MKnockoutBracket);
		MKnockoutBracket.Simulate(section, groups);
	}
	
	public void SimulateCurrentWorldsState() throws Exception {
		Group PA = new Group(Strings.LFirstGroup, 5, Teams.LNG, Teams.HLE, Teams.INF, Teams.PCE, Teams.RED);
		Group PB = new Group(Strings.LSecondGroup, 5, Teams.C9, Teams.BYG, Teams.UOL, Teams.GS, Teams.DFM);
		List<Group> PIGroups = new ArrayList<>(Arrays.asList(PA, PB));
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		// Place Teams
		eots.PlaceTeam(PA.GetTeamFromPlacement(5), 22);
		eots.PlaceTeam(PB.GetTeamFromPlacement(5), 22);
		//
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		Match M3 = PIKO.getMatch(3);
		Match M4 = PIKO.getMatch(4);
		
		// Place Teams
		eots.PlaceTeam(PIKO.getMatch(1).getLoser(), 20);
		eots.PlaceTeam(PIKO.getMatch(2).getLoser(), 20);
		eots.PlaceTeam(PIKO.getMatch(3).getLoser(), 18);
		eots.PlaceTeam(PIKO.getMatch(4).getLoser(), 18);		
		//
		
		List<Match> QMatches = new ArrayList<Match>(Arrays.asList(M3, M4));
		
		List<Team> Q = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(1),
								M3.getWinner(),
								M4.getWinner()));
		Pool PI = new Pool(Strings.LQualifiedPI, Q);
		
		PrintQualifiedThroughPI(PIGroups, QMatches, Q);
		
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
		
		// Place Teams
		eots.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		//
		eots.PlaceTeam(A.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(B.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(C.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(D.GetTeamFromPlacement(3), 12);
		//
		
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
		
		// Main Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		// Place Teams
		eots.PlaceTeam(MKnockoutBracket.getMatch(1).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(2).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(3).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getMatch(4).getLoser(), 8);
		//
		eots.PlaceTeam(MKnockoutBracket.getMatch(5).getLoser(), 4);
		eots.PlaceTeam(MKnockoutBracket.getMatch(6).getLoser(), 4);
		//
		eots.PlaceTeam(MKnockoutBracket.getMatch(7).getLoser(), 2);
		eots.PlaceTeam(MKnockoutBracket.getMatch(7).getWinner(), 1);
		//
	}
}
