package LEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import LCS.MidSeasonShowdown;
import LCS.RegularSeasonLCS;
import StaticVariables.Strings;
import StaticVariables.TeamsWithPlayers;
import TournamentComponents.Bracket;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class SummerLEC extends Tournament {
	GroupStage RR;
	Bracket KO;
	
	public SummerLEC() {
		super(Strings.LSummerLEC, 10);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up Groups
		Group A = new Group(Strings.RegularSeason, 10, 2, 1, 6, RR, pools.get(0).getPool()); 
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A));
		
		SimulateGroupStage(groups);
		
		SimulateKnockoutStage(groups);
		
		ConcludeTournament();
	}

	private void SimulateKnockoutStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		KO.Simulate(groups);
	}
	
	private void SimulateGroupStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		RR.Simulate(groups);
	}

	@Override
	public void Setup() {
		RR = new RegularSeasonLCS(Strings.RegularSeason, this);
		super.addGroupStage(RR);
		
		KO = new PlayoffsLEC(Strings.LECPlayoffs, this, Strings.RegularSeason);
		super.addBracket(KO);
	}

}
