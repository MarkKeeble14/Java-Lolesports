package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import Misc.Strings;
import Misc.Teams;
import StatsTracking.EOTStandings;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;
import WorldChampionship.MainStageKnockoutBracket;

public class CurrentStateOfTournamentWorldChampionship extends Tournament {
	int requiredNumberOfPools = 5;
	
	DrawStructure PIGroupDraw;
	GroupStage PIGroupStage;
	Bracket PIKnockoutBracket;
	DrawStructure MGroupDraw;
	GroupStage MGroupStage;
	Bracket MKnockoutBracket;
	
	public CurrentStateOfTournamentWorldChampionship(String label) {
		super(label);
	}

	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		Setup();
		
		EOTStandings eots = super.getEots();
		
		// Setting up groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, MGroupStage, Teams.DK, Teams.FPX, Teams.RGE, Teams.C9); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, MGroupStage, Teams.EDG, Teams.T1, Teams.O100T, Teams.DFM);
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, MGroupStage, Teams.FNC, Teams.RNG, Teams.HLE, Teams.PSG); 
		Group D = new Group(Strings.LFourthGroup, 4, 2, 1, MGroupStage, Teams.TL, Teams.MAD, Teams.LNG, Teams.GEN);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		SimulateCurrentGroupStage(groups);
		
		// Place Teams
		eots.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		eots.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		//
		eots.PlaceTeam(A.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(B.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(C.GetTeamFromPlacement(3), 12);
		eots.PlaceTeam(D.GetTeamFromPlacement(3), 12);
		//
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(groups);
		
		// Place Teams
		eots.PlaceTeam(MKnockoutBracket.getSeries(1).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(2).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(3).getLoser(), 8);
		eots.PlaceTeam(MKnockoutBracket.getSeries(4).getLoser(), 8);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(5).getLoser(), 4);
		eots.PlaceTeam(MKnockoutBracket.getSeries(6).getLoser(), 4);
		//
		eots.PlaceTeam(MKnockoutBracket.getSeries(7).getLoser(), 2);
		eots.PlaceTeam(MKnockoutBracket.getSeries(7).getWinner(), 1);
		//
		
		ConcludeTournament();
	}
	
	@Override 
	public void Setup() {
		MGroupStage = new MainStageGroupStage(Strings.MSGS, this);
		super.addGroupStage(MGroupStage);
		
		MKnockoutBracket = new MainStageKnockoutBracket(Strings.MSKS, this, Strings.MSGS);
		super.addBracket(MKnockoutBracket);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		MGroupStage.Simulate(groups);
	}
	
	private void SimulateCurrentDrawKO(List<Group> groups) throws Exception {
		MKnockoutBracket.Simulate(groups);
	}
}
