package WorldChampionshipCurrentState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Group;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Misc.Strings;
import Misc.Teams;
import QualificationDetails.QualifiedThroughGroupPlacement;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.GroupStage;

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
		
		RegionalWLTracker tracker = super.getPartOf().getT();
		EOTStandings standings = super.getPartOf().getEots();
		
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
		
		A.addResultToGameMatchup(Teams.C9, Teams.FPX, Teams.C9, Teams.FPX, true);
		A.addResultToGameMatchup(Teams.C9, Teams.RGE, Teams.C9, Teams.RGE, true);
		A.addResultToGameMatchup(Teams.DK, Teams.C9, Teams.DK, Teams.C9, true);
		A.addResultToGameMatchup(Teams.DK, Teams.FPX, Teams.DK, Teams.FPX, true);
		A.addResultToGameMatchup(Teams.DK, Teams.RGE, Teams.DK, Teams.RGE, true);
		A.addResultToGameMatchup(Teams.RGE, Teams.FPX, Teams.RGE, Teams.FPX, true);
		
		A.addTiebreakerSeeding(Teams.DK, Teams.C9, Teams.RGE, Teams.FPX);
		A.addPresetTiebreaker(super.getLabel(), Teams.RGE, Teams.FPX, Teams.RGE, Teams.FPX, tracker);
		A.addPresetTiebreaker(super.getLabel(), Teams.C9, Teams.RGE, Teams.C9, Teams.RGE, tracker);
		
		A.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		B.SetupMatches(super.getLabel(), tracker);
		B.addResultToGameMatchup(Teams.EDG, Teams.O100T, Teams.EDG, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.T1, Teams.O100T, Teams.T1, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.O100T, Teams.DFM, Teams.O100T, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.T1, Teams.DFM, Teams.T1, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.DFM, Teams.EDG, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.T1, Teams.EDG, Teams.T1, true);
		
		B.addResultToGameMatchup(Teams.T1, Teams.EDG, Teams.T1, Teams.EDG, true);
		B.addResultToGameMatchup(Teams.T1, Teams.O100T, Teams.T1, Teams.O100T, true);
		B.addResultToGameMatchup(Teams.T1, Teams.DFM, Teams.T1, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.O100T, Teams.O100T, Teams.EDG, true);
		B.addResultToGameMatchup(Teams.EDG, Teams.DFM, Teams.EDG, Teams.DFM, true);
		B.addResultToGameMatchup(Teams.DFM, Teams.O100T, Teams.O100T, Teams.DFM, true);
		B.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		C.SetupMatches(super.getLabel(), tracker);
		C.addResultToGameMatchup(Teams.PSG, Teams.FNC, Teams.PSG, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.HLE, Teams.FNC, Teams.HLE, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.FNC, Teams.RNG, Teams.FNC, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.HLE, Teams.RNG, Teams.HLE, true);
		C.addResultToGameMatchup(Teams.RNG, Teams.PSG, Teams.RNG, Teams.PSG, true);
		C.addResultToGameMatchup(Teams.PSG, Teams.HLE, Teams.PSG, Teams.HLE, true);
		C.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		D.SetupMatches(super.getLabel(), tracker);
		D.addResultToGameMatchup(Teams.TL, Teams.MAD, Teams.TL, Teams.MAD, true);
		D.addResultToGameMatchup(Teams.MAD, Teams.GEN, Teams.MAD, Teams.GEN, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.LNG, Teams.GEN, Teams.LNG, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.TL, Teams.LNG, Teams.TL, true);
		D.addResultToGameMatchup(Teams.GEN, Teams.TL, Teams.GEN, Teams.TL, true);
		D.addResultToGameMatchup(Teams.LNG, Teams.MAD, Teams.LNG, Teams.MAD, true);
		D.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		List<Team> GSQ = new ArrayList<Team>(
				Arrays.asList(	A.GetTeamFromPlacement(1),
								A.GetTeamFromPlacement(2),
								B.GetTeamFromPlacement(1),
								B.GetTeamFromPlacement(2),
								C.GetTeamFromPlacement(1),
								C.GetTeamFromPlacement(2),
								D.GetTeamFromPlacement(1),
								D.GetTeamFromPlacement(2)));
	
		SetQualified(groups, GSQ);
		
		// Place Teams
		standings.PlaceTeam(A.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(B.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(C.GetTeamFromPlacement(4), 16);
		standings.PlaceTeam(D.GetTeamFromPlacement(4), 16);
		//
		standings.PlaceTeam(A.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(B.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(C.GetTeamFromPlacement(3), 12);
		standings.PlaceTeam(D.GetTeamFromPlacement(3), 12);
		//
		
		super.setGroups(groups);
	}
	
	@Override
	public void SetQualified(List<Group> groups, List<Team> teams) {
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Team A1 = teams.get(0);
		A1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 1));
		Team A2 = teams.get(1);
		A2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, A, 2));

		Team B1 = teams.get(2);
		B1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 1));
		Team B2 = teams.get(3);
		B2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, B, 2));
		
		Team C1 = teams.get(4);
		C1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 1));
		Team C2 = teams.get(5);
		C2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, C, 2));
		
		Team D1 = teams.get(6);
		D1.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 1));
		Team D2 = teams.get(7);
		D2.setNewQD(new QualifiedThroughGroupPlacement(Strings.MSGS, D, 2));
	}
}
