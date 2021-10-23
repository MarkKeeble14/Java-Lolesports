package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningTeams.Team;
import StaticVariables.Strings;
import StaticVariables.Teams;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Tournament;

public class MainStageGroupStage extends GroupStage {
	public MainStageGroupStage(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Adding results to matches: A
		A.SetupMatches(super.getLabel(), tracker);
		A.addResultToGameMatchup(Teams.DK, Teams.FPX, Teams.DK, Teams.FPX, true);
		A.addResultToGameMatchup(Teams.RGE, Teams.C9, Teams.RGE, Teams.C9, true);
		A.addResultToGameMatchup(Teams.DK, Teams.C9, Teams.DK, Teams.C9, true);
		A.addResultToGameMatchup(Teams.FPX, Teams.C9, Teams.FPX, Teams.C9, true);
		A.addResultToGameMatchup(Teams.DK, Teams.RGE, Teams.DK, Teams.RGE, true);
		A.addResultToGameMatchup(Teams.FPX, Teams.RGE, Teams.FPX, Teams.RGE, true);
		//
		A.addResultToGameMatchup(Teams.C9, Teams.FPX, Teams.C9, Teams.FPX, true);
		A.addResultToGameMatchup(Teams.C9, Teams.RGE, Teams.C9, Teams.RGE, true);
		A.addResultToGameMatchup(Teams.DK, Teams.C9, Teams.DK, Teams.C9, true);
		A.addResultToGameMatchup(Teams.DK, Teams.FPX, Teams.DK, Teams.FPX, true);
		A.addResultToGameMatchup(Teams.DK, Teams.RGE, Teams.DK, Teams.RGE, true);
		A.addResultToGameMatchup(Teams.RGE, Teams.FPX, Teams.RGE, Teams.FPX, true);
		
		A.SimulatePresetMatches(super.getLabel(), tracker, false);
		
		// 
		A.addTiebreakerSeeding(Teams.DK, Teams.C9, Teams.RGE, Teams.FPX);
		A.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.RGE, Teams.FPX, Teams.RGE, Teams.FPX);
		A.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.C9, Teams.RGE, Teams.C9, Teams.RGE);
		
		// 
		B.SetupMatches(super.getLabel(), tracker);
		B.addResultToGameMatchup(Teams.EDG, Teams.O100T, Teams.EDG, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.T1, Teams.O100T, Teams.T1, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.O100T, Teams.DFM, Teams.O100T, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.T1, Teams.DFM, Teams.T1, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.DFM, Teams.EDG, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.T1, Teams.EDG, Teams.T1, true);
		//
		B.addResultToGameMatchup(Teams.T1, Teams.EDG, Teams.T1, Teams.EDG, true);
		B.addResultToGameMatchup(Teams.T1, Teams.O100T, Teams.T1, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.T1, Teams.DFM, Teams.T1, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.O100T, Teams.O100T, Teams.EDG, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.DFM, Teams.EDG, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.DFM, Teams.O100T, Teams.O100T, Teams.DFM, true);
		
		// Before the group is resolved, call this method
		B.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		// After groups are resolved / heading into tiebreakers, use the methods in the same way as seen below group C
		
		//
		C.SetupMatches(super.getLabel(), tracker);
		C.addResultToGameMatchup(Teams.PSG, Teams.FNC, Teams.PSG, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.HLE, Teams.FNC, Teams.HLE, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.FNC, Teams.RNG, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.HLE, Teams.RNG, Teams.HLE, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.PSG, Teams.RNG, Teams.PSG, true);
		C.addResultToGameMatchup(Teams.PSG, Teams.HLE, Teams.PSG, Teams.HLE, true);
		//
		C.addResultToGameMatchup(Teams.PSG, Teams.FNC, Teams.PSG, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.HLE, Teams.FNC, Teams.HLE, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.FNC, Teams.FNC, Teams.RNG, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.HLE, Teams.HLE, Teams.RNG, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.PSG, Teams.RNG, Teams.PSG, true);
		C.addResultToGameMatchup(Teams.PSG, Teams.HLE, Teams.HLE, Teams.PSG, true);
		
		// Tiebreakers
		// Before the group is resolved, call this method
		C.SimulatePresetMatches(super.getLabel(), tracker, false);
		C.addTiebreakerSeeding(Teams.RNG, Teams.HLE, Teams.PSG, Teams.FNC);
		
		// After groups are resolved / heading into tiebreakers, use the following
		C.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.RNG, Teams.HLE, Teams.RNG, Teams.HLE);
		
		//
		D.SetupMatches(super.getLabel(), tracker);
		D.addResultToGameMatchup(Teams.TL, Teams.MAD, Teams.TL, Teams.MAD, true);
		D.addResultToGameMatchup(Teams.MAD, Teams.GEN, Teams.MAD, Teams.GEN, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.LNG, Teams.GEN, Teams.LNG, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.TL, Teams.LNG, Teams.TL, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.TL, Teams.GEN, Teams.TL, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.MAD, Teams.LNG, Teams.MAD, true);
		//
		D.addResultToGameMatchup(Teams.TL, Teams.MAD, Teams.MAD, Teams.TL, true);
		D.addResultToGameMatchup(Teams.MAD, Teams.GEN, Teams.GEN, Teams.MAD, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.LNG, Teams.LNG, Teams.GEN, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.TL, Teams.TL, Teams.LNG, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.TL, Teams.TL, Teams.GEN, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.MAD, Teams.MAD, Teams.LNG, true);
		
		// Tiebreakers
		D.SimulatePresetMatches(super.getLabel(), tracker, false);
		D.addTiebreakerSeeding(Teams.GEN, Teams.MAD, Teams.LNG, Teams.TL);
		D.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.GEN, Teams.TL, Teams.GEN, Teams.TL);
		D.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.MAD, Teams.LNG, Teams.MAD, Teams.LNG);
		D.ManuallySimulatePresetTiebreaker(super.getLabel(), tracker, Teams.MAD, Teams.GEN, Teams.GEN, Teams.MAD);
	
		SetQualified(groups, standings);
		
		super.setGroups(groups);
	}
}
