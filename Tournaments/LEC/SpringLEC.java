package LEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import LCS.MidSeasonShowdown;
import LCS.RoundRobin;
import Misc.Strings;
import Misc.TeamsWithPlayers;
import TournamentComponents.Bracket;
import TournamentComponents.GroupStage;

public class SpringLEC extends Tournament {
	GroupStage RR;
	Bracket KO;
	
	public SpringLEC() {
		super(Strings.LSpringLEC);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up Groups
		Group A = new Group(Strings.RegularSeason, 10, 2, 1, RR, pools.get(0).getPool()); 
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A));
		
		SimulateGroupStage(groups);
		
		eots.PlaceTeam(A.GetTeamFromPlacement(10), 10);
		eots.PlaceTeam(A.GetTeamFromPlacement(9), 9);
		eots.PlaceTeam(A.GetTeamFromPlacement(8), 8);
		eots.PlaceTeam(A.GetTeamFromPlacement(7), 7);
		
		SimulateKnockoutStage(groups);
		
		eots.PlaceTeam(KO.getSeries(3).getLoser(), 6);
		eots.PlaceTeam(KO.getSeries(4).getLoser(), 5);
		eots.PlaceTeam(KO.getSeries(5).getLoser(), 4);
		eots.PlaceTeam(KO.getSeries(7).getLoser(), 3);
		eots.PlaceTeam(KO.getSeries(8).getLoser(), 2);
		eots.PlaceTeam(KO.getSeries(8).getWinner(), 1);
		
		ConcludeTournament();
	}

	private void SimulateKnockoutStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		KO.Simulate(Strings.LECPlayoffs, groups);
	}
	
	private void SimulateGroupStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		RR.Simulate(Strings.RegularSeason, groups);
	}

	@Override
	public void Setup() {
		RR = new RoundRobin(this);
		super.addGroupStage(RR);
		
		KO = new PlayoffsLEC(this, Strings.RegularSeason);
		super.addBracket(KO);
	}

}
