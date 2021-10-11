package LEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import LCS.MidSeasonShowdown;
import LCS.RoundRobin;
import Misc.Strings;
import Misc.TeamsWithPlayers;
import TournamentComponents.Bracket;
import TournamentComponents.GroupStage;

public class SummerLEC extends Tournament {
	GroupStage RR;
	Bracket KO;
	
	public SummerLEC() {
		super(Strings.LSummerLEC);
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
