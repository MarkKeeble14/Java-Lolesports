package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import Misc.Strings;
import Misc.TeamsWithPlayers;
import TournamentComponents.Bracket;
import TournamentComponents.GroupStage;

public class LCSLockin extends Tournament {
	GroupStage GS;
	Bracket KOStage;
	
	public LCSLockin() {
		super(Strings.LLockin);
	}
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		Pool p = pools.get(0);
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 5, 1, 1, GS); 
		Group B = new Group(Strings.LSecondGroup, 5, 1, 1, GS); 
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B));
		
		A.Add(p.Draw());
		B.Add(p.Draw());
		A.Add(p.Draw());
		B.Add(p.Draw());
		A.Add(p.Draw());
		B.Add(p.Draw());
		A.Add(p.Draw());
		B.Add(p.Draw());
		A.Add(p.Draw());
		B.Add(p.Draw());
		
		SimulateGroupStage(groups);
		
		eots.PlaceTeam(A.GetTeamFromPlacement(5), 10);
		eots.PlaceTeam(B.GetTeamFromPlacement(5), 10);
		
		SimulateKnockoutStage(groups);
		
		eots.PlaceTeam(KOStage.getSeries(1).getLoser(), 6);
		eots.PlaceTeam(KOStage.getSeries(2).getLoser(), 6);
		
		eots.PlaceTeam(KOStage.getSeries(3).getLoser(), 6);
		eots.PlaceTeam(KOStage.getSeries(4).getLoser(), 6);
		
		eots.PlaceTeam(KOStage.getSeries(5).getLoser(), 4);
		eots.PlaceTeam(KOStage.getSeries(6).getLoser(), 4);
		
		eots.PlaceTeam(KOStage.getSeries(7).getLoser(), 2);
		eots.PlaceTeam(KOStage.getSeries(7).getWinner(), 1);
		
		ConcludeTournament();
	}

	private void SimulateKnockoutStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		KOStage.Simulate(groups);
	}
	
	private void SimulateGroupStage(List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		GS.Simulate(groups);
	}
	
	@Override
	public void Setup() {
		// TODO Auto-generated method stub
		GS = new LockinGroupStage(Strings.LockinGroupStage, this);
		super.addGroupStage(GS);
		
		KOStage = new LockinKOBracket(Strings.LockinKOStage, this, Strings.LockinGroupStage);
		super.addBracket(KOStage);
	}

}
