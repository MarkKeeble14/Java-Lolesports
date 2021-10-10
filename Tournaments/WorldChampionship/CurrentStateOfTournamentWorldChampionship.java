package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classes.Group;
import Classes.Pool;
import Classes.Team;
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
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;

public class CurrentStateOfTournamentWorldChampionship extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public CurrentStateOfTournamentWorldChampionship(String label) {
		super(label);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		EOTStandings eots = super.getEots();
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, MGroupStage, Teams.DK, Teams.FPX, Teams.RGE, Teams.C9); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, MGroupStage, Teams.EDG, Teams.T1, Teams.O100T, Teams.DFM);
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, MGroupStage, Teams.FNC, Teams.RNG, Teams.HLE, Teams.PSG); 
		Group D = new Group(Strings.LFourthGroup, 4, 2, 1, MGroupStage, Teams.TL, Teams.MAD, Teams.LNG, Teams.GEN);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
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
	
		SetQualifiedToMainKO(groups, GSQ);
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		// Place Teams
		eots.PlaceTeam(MKnockoutBracket.getSeries(1).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(2).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(3).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(4).getLoser(), 8);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(5).getLoser(), 4);
		eots.PlaceTeam(MKnockoutBracket.getSeries(6).getLoser(), 4);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(7).getLoser(), 2);
		eots.PlaceTeam(MKnockoutBracket.getSeries(7).getWinner(), 1);
		//
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		MGroupStage = new GroupStageMainCurrentFormat(this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new KnockoutBracketDoubleElimFormat(this, Strings.MSKS);
		super.addBracket(MKnockoutBracket);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		String section = Strings.MSGS;
		MGroupStage.Simulate(section, groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		String section = Strings.MSKS;
		MKnockoutBracket.Simulate(section, groups);
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
	}
}
