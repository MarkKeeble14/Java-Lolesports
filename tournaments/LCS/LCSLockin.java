package LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import StaticVariables.Strings;
import StaticVariables.TeamsWithPlayers;
import Stats.Standings;
import TournamentComponents.Bracket;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class LCSLockin extends Tournament {
	GroupStage GS;
	Bracket KOStage;
	
	public LCSLockin() {
		super(Strings.LLockin, 10);
	}
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		Pool p = pools.get(0);
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 5, 1, 1, 4, GS); 
		Group B = new Group(Strings.LSecondGroup, 5, 1, 1, 4, GS); 
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
		
		SimulateKnockoutStage(groups);
		
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
