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
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		// Adding results to matches: A
		A.SetupMatches(super.getLabel(), tracker);
		A.addResultToGameMatchup(Teams.DK, Teams.FPX);
		A.addResultToGameMatchup(Teams.RGE, Teams.C9);
		A.addResultToGameMatchup(Teams.DK, Teams.C9);
		A.addResultToGameMatchup(Teams.FPX, Teams.C9);
		A.addResultToGameMatchup(Teams.DK, Teams.RGE);
		A.addResultToGameMatchup(Teams.FPX, Teams.RGE);
		A.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		B.SetupMatches(super.getLabel(), tracker);
		B.addResultToGameMatchup(Teams.EDG, Teams.O100T);
		B.addResultToGameMatchup(Teams.T1, Teams.O100T);
		B.addResultToGameMatchup(Teams.O100T, Teams.DFM);
		B.addResultToGameMatchup(Teams.T1, Teams.DFM);
		B.addResultToGameMatchup(Teams.EDG, Teams.DFM);
		B.addResultToGameMatchup(Teams.EDG, Teams.T1);
		B.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		C.SetupMatches(super.getLabel(), tracker);
		C.addResultToGameMatchup(Teams.PSG, Teams.FNC);
		C.addResultToGameMatchup(Teams.HLE, Teams.FNC);
		C.addResultToGameMatchup(Teams.RNG, Teams.FNC);
		C.addResultToGameMatchup(Teams.RNG, Teams.HLE);
		C.addResultToGameMatchup(Teams.RNG, Teams.PSG);
		C.addResultToGameMatchup(Teams.PSG, Teams.HLE);
		C.SimulatePresetMatches(super.getLabel(), tracker, true);
		
		D.SetupMatches(super.getLabel(), tracker);
		D.addResultToGameMatchup(Teams.TL, Teams.MAD);
		D.addResultToGameMatchup(Teams.MAD, Teams.GEN);
		D.addResultToGameMatchup(Teams.GEN, Teams.LNG);
		D.addResultToGameMatchup(Teams.LNG, Teams.TL);
		D.addResultToGameMatchup(Teams.GEN, Teams.TL);
		D.addResultToGameMatchup(Teams.LNG, Teams.MAD);
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
