package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Game;
import Matches.Series;
import Misc.Strings;
import Misc.Teams;
import Misc.Util;
import QualificationDetails.QualifiedThroughGroupPlacement;
import QualificationDetails.QualifiedThroughSeriesWin;
import StatsTracking.EOTStandings;
import Teams.Team;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;

public class TournamentWorldChampionshipDoubleElim extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public TournamentWorldChampionshipDoubleElim(String label) {
		super(Strings.LWC);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		Setup();
		
		EOTStandings eots = super.getEots();
		
		// Setting Up Pools
		Pool PIPoolOne = pools.get(0);
		Pool PIPoolTwo = pools.get(1);
		List<Pool> PIPools = new ArrayList<Pool>(Arrays.asList(PIPoolOne, PIPoolTwo));
		
		// Setting up PI Groups
		Group PA = new Group(Strings.LFirstGroup, 5, 1, 1, PIGroupStage); 
		Group PB = new Group(Strings.LSecondGroup, 5, 1, 1, PIGroupStage);
		List<Group> PIGroups = new ArrayList<Group>(Arrays.asList(PA, PB));
	  
		SimulateCurrentPIGroupDraw(PIGroups, PIPools);
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		// Place Teams
		eots.PlaceTeam(PA.GetTeamFromPlacement(5), 22);
		eots.PlaceTeam(PB.GetTeamFromPlacement(5), 22);
		//
		
		List<Team> QPI = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PA.GetTeamFromPlacement(2), 
								PA.GetTeamFromPlacement(3),
								PA.GetTeamFromPlacement(4),
								PB.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(2),
								PB.GetTeamFromPlacement(3),
								PB.GetTeamFromPlacement(4)));
		SetQualifiedToPIKO(PIGroups, QPI);
		
		Bracket PIKO = SimulateCurrentPlayinsKOStage(PIGroups);
		Series M3 = PIKO.getSeries(3);
		Series M4 = PIKO.getSeries(4);
		
		// Place Teams
		eots.PlaceTeam(PIKO.getSeries(1).getLoser(), 20);
		eots.PlaceTeam(PIKO.getSeries(2).getLoser(), 20);
		eots.PlaceTeam(PIKO.getSeries(3).getLoser(), 18);
		eots.PlaceTeam(PIKO.getSeries(4).getLoser(), 18);		
		//
		
		List<Team> Q = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(1),
								M3.getWinner(),
								M4.getWinner()));
		Pool PI = new Pool(Strings.LQualifiedPI, Q);
		
		// SetQualifiedThroughPI(PIGroups, QMatches, Q);
		
		Pool P1 = pools.get(2);
		Pool P2 = pools.get(3);
		Pool P3 = pools.get(4);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(PI, P1, P2, P3)); 
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, MGroupStage); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, MGroupStage);
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, MGroupStage); 
		Group D = new Group(Strings.LFourthGroup, 4, 2, 1, MGroupStage);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		// Place Teams
		eots.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		//
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								A.GetTeamFromPlacement(3),
								B.GetTeamFromPlacement(1),
								B.GetTeamFromPlacement(2),
								B.GetTeamFromPlacement(3),
								C.GetTeamFromPlacement(1),
								C.GetTeamFromPlacement(2),
								C.GetTeamFromPlacement(3),
								D.GetTeamFromPlacement(1),
								D.GetTeamFromPlacement(2),
								D.GetTeamFromPlacement(3)));
	
		SetQualifiedToMainKO(groups, GSQ);
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		// Place Teams
		eots.PlaceTeam(MKnockoutBracket.getSeries(1).getLoser(), 12);
		eots.PlaceTeam(MKnockoutBracket.getSeries(2).getLoser(), 12);
		eots.PlaceTeam(MKnockoutBracket.getSeries(3).getLoser(), 12);
		eots.PlaceTeam(MKnockoutBracket.getSeries(4).getLoser(), 12);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(9).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(10).getLoser(), 8);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(13).getLoser(), 6);
		eots.PlaceTeam(MKnockoutBracket.getSeries(14).getLoser(), 6);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(16).getLoser(), 4);
		eots.PlaceTeam(MKnockoutBracket.getSeries(17).getLoser(), 3);
		eots.PlaceTeam(MKnockoutBracket.getSeries(18).getLoser(), 2);
		eots.PlaceTeam(MKnockoutBracket.getSeries(18).getWinner(), 1);
		//		
		//
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		PIGroupDraw = new GroupDrawPIStageCurrentFormat(this);
		super.addDrawStructure(PIGroupDraw);
		
		PIGroupStage = new GroupStagePICurrentFormat(this);
		super.addGroupStage(PIGroupStage);
		
		PIKnockoutBracket = new KnockoutBracketCurrentPIFormat(this, Strings.PIGS);
		super.addBracket(PIKnockoutBracket);
		
		MGroupDraw = new GroupDrawMainStageCurrentFormat(this);
		super.addDrawStructure(MGroupDraw);
		
		MGroupStage = new GroupStageMainCurrentFormat(this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new KnockoutBracketDoubleElimFormat(this, Strings.MSKS);
		super.addBracket(MKnockoutBracket);
	}
	
	private void SimulateCurrentPIGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		String section = Strings.PIGD;
		PIGroupDraw.Simulate(section, groups, pools);
	}
	
	private void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		String section = Strings.PIGS;
		PIGroupStage.Simulate(section, groups);
	}
	
	private Bracket SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		String section = Strings.PIKS;
		PIKnockoutBracket.Simulate(section, groups);
		return PIKnockoutBracket;
	}
	
	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		String section = Strings.MSGD;
		MGroupDraw.Simulate(section, groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		String section = Strings.MSGS;
		MGroupStage.Simulate(section, groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		String section = Strings.MSKS;
		MKnockoutBracket.Simulate(section, groups);
	}
	
	private void SetQualifiedToPIKO(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		Team Q1 = teams.get(0);
		Q1.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, A, 1));
		Team Q2 = teams.get(1);
		Q2.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, A, 2));
		Team Q3 = teams.get(2);
		Q3.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, A, 3));
		Team Q4 = teams.get(3);
		Q4.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, A, 4));
		
		Team Q5 = teams.get(4);
		Q5.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, B, 1));
		Team Q6 = teams.get(5);
		Q6.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, B, 2));
		Team Q7 = teams.get(6);
		Q7.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, B, 3));
		Team Q8 = teams.get(7);
		Q8.setNewQD(new QualifiedThroughGroupPlacement(Strings.PIGS, B, 4));
	}
	
	private void SetQualifiedToMainKO(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 2));
		Team A3 = teams.get(2);
		A3.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 3));

		Team B1 = teams.get(3);
		B1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 1));
		Team B2 = teams.get(4);
		B2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 2));
		Team B3 = teams.get(5);
		B3.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 3));
		
		Team C1 = teams.get(6);
		C1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 1));
		Team C2 = teams.get(7);
		C2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 2));
		Team C3 = teams.get(8);
		C3.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 3));
		
		Team D1 = teams.get(9);
		D1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 1));
		Team D2 = teams.get(10);
		D2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 2));
		Team D3 = teams.get(11);
		D3.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 3));
	}
}
