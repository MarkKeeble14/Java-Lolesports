package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import StaticVariables.Strings;
import StaticVariables.Teams;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class CurrentStateOfTournamentWorldChampionship extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public CurrentStateOfTournamentWorldChampionship(String label) {
		super(label, 22);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		// Setting up PI Groups
		Group PA = new Group(Strings.LFirstGroup, 5, 1, 1, 4, PIGroupStage, 
				Teams.LNG, Teams.HLE, Teams.INF, Teams.PCE, Teams.RED); 
		Group PB = new Group(Strings.LSecondGroup, 5, 1, 1, 4, PIGroupStage,
				Teams.C9, Teams.BYG, Teams.UOL, Teams.GS, Teams.DFM);
		List<Group> PIGroups = new ArrayList<Group>(Arrays.asList(PA, PB));
		
		SimulateCurrentPIGroupStage(PIGroups);
		
		SimulateCurrentPlayinsKOStage(PIGroups);
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, 2, MGroupStage, Teams.DK, Teams.FPX, Teams.RGE, Teams.C9); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, 2, MGroupStage, Teams.EDG, Teams.T1, Teams.O100T, Teams.DFM);
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, 2, MGroupStage, Teams.FNC, Teams.RNG, Teams.HLE, Teams.PSG); 
		Group D = new Group(Strings.LFourthGroup, 4, 2, 1, 2, MGroupStage, Teams.TL, Teams.MAD, Teams.LNG, Teams.GEN);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupStage(groups);
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		PIGroupStage = new PIStageGroupStage(Strings.PIGS, this);
		super.addGroupStage(PIGroupStage);
		
		PIKnockoutBracket = new PIStageKnockoutBracket(Strings.PIKS, this, Strings.PIGS);
		super.addBracket(PIKnockoutBracket);
		
		MGroupStage = new MainStageGroupStage(Strings.MSGS, this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new MainStageKnockoutBracket(Strings.MSKS, this, Strings.MSGS);
		super.addBracket(MKnockoutBracket);
	}
	
	private void SimulateCurrentPIGroupStage(List<Group> groups) throws Exception {
		PIGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentPlayinsKOStage(List<Group> groups) throws Exception {
		PIKnockoutBracket.Simulate(groups);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		MGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		MKnockoutBracket.Simulate(groups);
	}
}
