package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import MSI.GroupDrawGroupStageCurrentFormat;
import MSI.GroupStageGroupStageCurrentFormat;
import MSI.GroupStageRumbleStageCurrentFormat;
import MSI.KnockoutBracketCurrentFormat;
import Misc.Strings;
import Misc.TeamsWithPlayers;
import StatsTracking.EOTStandings;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;

public class SpringLCS extends Tournament {
	GroupStage RR;
	Bracket MSS;
	
	public SpringLCS(String label) {
		super(label);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 10, 2, 1, RR, 
				new Team(TeamsWithPlayers.TSM), new Team(TeamsWithPlayers.C9), new Team(TeamsWithPlayers.TL), 
				new Team(TeamsWithPlayers.EG), new Team(TeamsWithPlayers.FLY), new Team(TeamsWithPlayers.GG), 
				new Team(TeamsWithPlayers.CLG), new Team(TeamsWithPlayers.DIG), new Team(TeamsWithPlayers.O100), 
				new Team(TeamsWithPlayers.IMT)); 
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A));
		
		SimulateGroupStage(groups);
		
		eots.PlaceTeam(A.GetTeamFromPlacement(10), 10);
		eots.PlaceTeam(A.GetTeamFromPlacement(9), 9);
		eots.PlaceTeam(A.GetTeamFromPlacement(8), 8);
		eots.PlaceTeam(A.GetTeamFromPlacement(7), 7);
		
		SimulateKnockoutStage(groups);
		
		eots.PlaceTeam(MSS.getSeries(3).getLoser(), 6);
		eots.PlaceTeam(MSS.getSeries(4).getLoser(), 6);
		
		eots.PlaceTeam(MSS.getSeries(6).getLoser(), 4);
		eots.PlaceTeam(MSS.getSeries(7).getLoser(), 4);
		
		eots.PlaceTeam(MSS.getSeries(8).getLoser(), 2);
		eots.PlaceTeam(MSS.getSeries(8).getWinner(), 1);
		
		ConcludeTournament();
	}

	@Override
	public void Setup() {
		RR = new RoundRobin(this);
		super.addGroupStage(RR);
		
		MSS = new MidSeasonShowdown(this, Strings.RegularSeason);
		super.addBracket(MSS);
	}
	
	private void SimulateKnockoutStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		MSS.Simulate(Strings.MSS, groups);
	}
	
	private void SimulateGroupStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		RR.Simulate(Strings.RegularSeason, groups);
	}
}
