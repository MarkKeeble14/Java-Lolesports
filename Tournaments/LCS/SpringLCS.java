package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
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
	
	public SpringLCS() {
		super(Strings.LSpringLCS);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up Groups
		// Setting up Groups
		Group A = new Group(Strings.RegularSeason, 10, 2, 1, RR, pools.get(0).getPool()); 
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
		RR = new RoundRobin(Strings.RegularSeason, this);
		super.addGroupStage(RR);
		
		MSS = new MidSeasonShowdown(Strings.MSS, this, Strings.RegularSeason);
		super.addBracket(MSS);
	}
	
	private void SimulateKnockoutStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		MSS.Simulate(groups);
	}
	
	private void SimulateGroupStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		RR.Simulate(groups);
	}
}
