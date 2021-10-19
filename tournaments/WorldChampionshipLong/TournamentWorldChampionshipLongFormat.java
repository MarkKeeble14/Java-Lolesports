package WorldChampionshipLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import CustomExceptions.MismatchedNumberOfPoolsException;
import DefiningTeams.Team;
import StaticVariables.Strings;
import StaticVariables.Teams;
import Stats.Standings;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class TournamentWorldChampionshipLongFormat extends Tournament {
	int requiredNumberOfPools = 6;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	Bracket FKnockoutBracket;
	
	public TournamentWorldChampionshipLongFormat(String label) {
		super(label, 28);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfPoolsException(requiredNumberOfPools, pools.size());
		}
		
		Setup();
		
		// Setting Up Pools
		Pool PIPoolOne = pools.get(0);
		Pool PIPoolTwo = pools.get(1);
		List<Pool> PIPools = new ArrayList<Pool>(Arrays.asList(PIPoolOne, PIPoolTwo));
		
		// Setting up PI Groups
		Group PA = new Group(Strings.LFirstGroup, 7, 1, 1, 5, PIGroupStage); 
		Group PB = new Group(Strings.LSecondGroup, 7, 1, 1, 5, PIGroupStage); 
		List<Group> PIGroups = new ArrayList<Group>(Arrays.asList(PA, PB));
	  
		SimulateCurrentPIGroupDraw(PIGroups, PIPools);
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		SimulateCurrentPlayinsKOStage(PIGroups);
		
		List<Team> Q = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PA.GetTeamFromPlacement(2),
								PB.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(2),
								PIKnockoutBracket.getSeries(3).getWinner(),
								PIKnockoutBracket.getSeries(4).getWinner()));
		Pool P4 = pools.get(5);
		P4.Add(Q);
		
		Pool P1 = pools.get(2);
		Pool P2 = pools.get(3);
		Pool P3 = pools.get(4);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(P1, P2, P3, P4)); 
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 5, 2, 1, 4, MGroupStage); 
		Group B = new Group(Strings.LSecondGroup, 5, 2, 1, 4, MGroupStage);
		Group C = new Group(Strings.LThirdGroup, 5, 2, 1, 4, MGroupStage); 
		Group D = new Group(Strings.LFourthGroup, 5, 2, 1, 4, MGroupStage);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		SimulateCurrentDrawKO(groups);
		
		SimulateFinalKO(MKnockoutBracket, groups);
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		PIGroupDraw = new PIStageGroupDraw(Strings.PIGD, this);
		super.addDrawStructure(PIGroupDraw);
		
		PIGroupStage = new PIStageGroupStage(Strings.PIGS, this);
		super.addGroupStage(PIGroupStage);
		
		//
		
		PIKnockoutBracket = new PIStageKnockoutBracket(Strings.PIKS, this, Strings.PIGS);
		super.addBracket(PIKnockoutBracket);
		
		MGroupDraw = new MainStageGroupDraw(Strings.MSGD, this);
		super.addDrawStructure(MGroupDraw);
		
		MGroupStage = new MainStageGroupStage(Strings.MSGS, this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new MainStageKnockoutBracket(Strings.MSKS, this, Strings.MSGS);
		super.addBracket(MKnockoutBracket);
		
		FKnockoutBracket = new FinalEliminationKnockoutBracket(Strings.FSKS, this, Strings.MSKS);
		super.addBracket(FKnockoutBracket);
	}
	
	
	private void SimulateCurrentPIGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		PIGroupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		PIGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		PIKnockoutBracket.Simulate(groups);
	}
	
	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		MGroupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		MGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		MKnockoutBracket.Simulate(groups);
	}
	
	private void SimulateFinalKO(Bracket qualifier, List<Group> groups) throws Exception {
		FKnockoutBracket.Simulate(qualifier, groups);
	}
}
