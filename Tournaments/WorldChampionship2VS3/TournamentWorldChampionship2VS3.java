package WorldChampionship2VS3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Teams.Team;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;
import WorldChampionship.MainStageGroupDraw;
import WorldChampionship.PIStageGroupDraw;
import WorldChampionship.MainStageGroupStage;
import WorldChampionship.PIStageGroupStage;
import WorldChampionship.PIStageKnockoutBracket;

public class TournamentWorldChampionship2VS3 extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public TournamentWorldChampionship2VS3(String label) {
		super(Strings.LWC);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		Setup();
		
		// Setting Up Pools
		Pool PIPoolOne = pools.get(0);
		Pool PIPoolTwo = pools.get(1);
		List<Pool> PIPools = new ArrayList<Pool>(Arrays.asList(PIPoolOne, PIPoolTwo));
		
		// Setting up PI Groups
		Group PA = new Group(Strings.LFirstGroup, 5, 1, 1, PIGroupStage); 
		Group PB = new Group(Strings.LSecondGroup, 5, 1, 1, PIGroupStage);
		List<Group> PIGroups = new ArrayList<Group>(Arrays.asList(PA, PB));
	  
		SimulateCurrentPIGroupDraw(PIGroups, PIPools);
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		SimulateCurrentPlayinsKOStage(PIGroups);
		
		List<Team> Q = new ArrayList<Team>(
				Arrays.asList(	PA.GetTeamFromPlacement(1),
								PB.GetTeamFromPlacement(1),
								PIKnockoutBracket.getSeries(3).getWinner(),
								PIKnockoutBracket.getSeries(4).getWinner()));
		Pool PI = new Pool(Strings.LQualifiedPI, Q);
		
		Pool P1 = pools.get(2);
		Pool P2 = pools.get(3);
		Pool P3 = pools.get(4);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(PI, P1, P2, P3)); 
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, MGroupStage); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, MGroupStage);
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, MGroupStage); 
		Group D = new Group(Strings.LFourthGroup, 4, 2, 1, MGroupStage);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		SimulateCurrentDrawKO(groups);
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		PIGroupDraw = new PIStageGroupDraw(Strings.PIGD, this);
		super.addDrawStructure(PIGroupDraw);
		
		PIGroupStage = new PIStageGroupStage(Strings.PIGS, this);
		super.addGroupStage(PIGroupStage);
		
		PIKnockoutBracket = new PIStageKnockoutBracket(Strings.PIKS, this, Strings.PIGS);
		super.addBracket(PIKnockoutBracket);
		
		MGroupDraw = new MainStageGroupDraw(Strings.MSGD, this);
		super.addDrawStructure(MGroupDraw);
		
		MGroupStage = new MainStageGroupStage(Strings.MSGS, this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new MainStageKnockoutBracket2VS3(Strings.MSKS, this, Strings.MSKS);
		super.addBracket(MKnockoutBracket);
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
}
