package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MSI.GroupDraw;
import MSI.MSIGroupStage;
import MSI.RumbleStage;
import MSI.KnockoutBracket;
import StaticVariables.Strings;
import StaticVariables.TeamsWithPlayers;
import Stats.Standings;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class SummerLCS extends Tournament {
	GroupStage RR;
	Bracket MSS;
	
	public SummerLCS() {
		super(Strings.LSummerLCS, 10);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 10, 3, 1, 8, RR, pools.get(0).getPool()); 
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A));
		
		SimulateGroupStage(groups);
		
		SimulateKnockoutStage(groups);
		
		ConcludeTournament();
	}

	@Override
	public void Setup() {
		RR = new RegularSeasonLCS(Strings.RegularSeason, this);
		super.addGroupStage(RR);
		
		MSS = new SummerPlayoffsLCS(Strings.LCSPlayoffs, this, Strings.RegularSeason);
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
