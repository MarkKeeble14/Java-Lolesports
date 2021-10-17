package MSI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.GroupStage;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;
import Utility.Util;

public class TournamentMSI extends Tournament {
	DrawStructure groupDraw;
	GroupStage groupStage;
	GroupStage rumbleStage;
	Bracket knockoutBracket;
	
	public TournamentMSI() {
		super(Strings.LMSI);
	}

	int requiredNumberOfPools = 2;
	
	@Override
	public void Simulate(List<Pool> pools) throws Exception {
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfPools, pools.size());
		}
		
		Setup();
		
		// Setting Up Pools
		Pool P1 = pools.get(0);
		Pool P2 = pools.get(1);
		List<Pool> pools1 = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		// Setting up Groups
		Group A = new Group(Strings.LFirstGroup, 4, 2, 1, groupStage); 
		Group B = new Group(Strings.LSecondGroup, 4, 2, 1, groupStage); 
		Group C = new Group(Strings.LThirdGroup, 4, 2, 1, groupStage);
		List<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C));
		
		SimulateCurrentGroupDraw(groups, pools1);
		
		SimulateCurrentGroupStage(groups);
		
		List<Team> Q = new ArrayList<Team>(Arrays.asList(	
				A.GetTeamFromPlacement(1), A.GetTeamFromPlacement(2),
				B.GetTeamFromPlacement(1), B.GetTeamFromPlacement(2),
				C.GetTeamFromPlacement(1), C.GetTeamFromPlacement(2)));
		
		Group Rumble = new Group(Strings.LRumble, 6, 2, 1, groupStage, Q);
		List<Group> RGroup = new ArrayList<Group>(Arrays.asList(Rumble));
		
		SimulateCurrentRumbleStage(RGroup);
		
		SimulateCurrentKnockoutStage(RGroup);
		
		ConcludeTournament();
	}
	
	@Override
	public void Setup() {
		groupDraw = new GroupDraw(Strings.GSGD, this);
		super.addDrawStructure(groupDraw);
		
		groupStage = new MSIGroupStage(Strings.GS, this);
		super.addGroupStage(groupStage);
		
		rumbleStage = new RumbleStage(Strings.LRumble, this);
		super.addGroupStage(rumbleStage);
		
		knockoutBracket = new KnockoutBracket(Strings.RSKS, this, Strings.LRumble);
		super.addBracket(knockoutBracket);
	}

	private void SimulateCurrentGroupDraw(List<Group> groups, List<Pool> pools) throws Exception {
		groupDraw.Simulate(groups, pools);
	}
	
	private void SimulateCurrentGroupStage(List<Group> groups) throws Exception {
		groupStage.Simulate(groups);
	}
	
	private void SimulateCurrentRumbleStage(List<Group> groups) throws Exception {
		rumbleStage.Simulate(groups);
	}
	
	private void SimulateCurrentKnockoutStage(List<Group> groups) throws Exception {
		knockoutBracket.Simulate(groups);
	}
}
