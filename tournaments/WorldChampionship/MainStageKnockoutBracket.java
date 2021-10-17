package WorldChampionship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningMatches.Series;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.ResultsTracker;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

// 4 Group Bracket
public class MainStageKnockoutBracket extends Bracket {
	public MainStageKnockoutBracket(String label, Tournament partOf) {
		super(label, partOf);
	}

	public MainStageKnockoutBracket(String label, Tournament tournamentWorldChampionship, String msgs) {
		super(label, tournamentWorldChampionship, msgs);
	}

	int requiredNumberOfGroups = 4;
	
	@Override
	public void Simulate(List<Group> groups) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		
		ResultsTracker tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
		Pool poolOne = new Pool(Strings.LPoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), 
				C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(Strings.LPoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), 
				C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		BracketSlice S1 = new BracketSlice(super.getLabel(), Strings.QFS, 1);
		BracketSlice S2 = new BracketSlice(super.getLabel(), Strings.SFS, 2);
		BracketSlice S3 = new BracketSlice(super.getLabel(), Strings.FS, 3);
		
		Series M1 = new Series(1, 5, tracker);
		Series M2 = new Series(2, 5, tracker);
		Series M3 = new Series(3, 5, tracker);
		Series M4 = new Series(4, 5, tracker);
		S1.addSeries(M1, M2, M3, M4);
		Series M5 = new Series(5, 5, tracker);
		Series M6 = new Series(6, 5, tracker);
		S2.addSeries(M5, M6);
		Series M7 = new Series(7, 5, tracker);
		S3.addSeries(M7);
		
		ArrayList<Series> matches = new ArrayList<Series>(Arrays.asList(M1, M2, M3, M4));
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolOne.Draw());
		M3.setTeamA(poolOne.Draw());
		M4.setTeamA(poolOne.Draw());
		
		M1.setTeamB(poolTwo.DrawWithSameSideRule(M1, M2, poolTwo, new ArrayList<Team>(), matches, groups));
		M2.setTeamB(poolTwo.DrawWithSameSideRule(M2, M1, poolTwo, new ArrayList<Team>(), matches, groups));
		M3.setTeamB(poolTwo.DrawWithSameSideRule(M3, M4, poolTwo, new ArrayList<Team>(), matches, groups));
		M4.setTeamB(poolTwo.DrawWithSameSideRule(M4, M3, poolTwo, new ArrayList<Team>(), matches, groups));
				
		M1.Simulate();
		M2.Simulate();
		M3.Simulate();
		M4.Simulate();
		
		standings.PlaceTeam(M1.getLoser(), 8);
		standings.PlaceTeam(M2.getLoser(), 8);
		standings.PlaceTeam(M3.getLoser(), 8);
		standings.PlaceTeam(M4.getLoser(), 8);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate();
		M6.Simulate();
		
		standings.PlaceTeam(M5.getLoser(), 4);
		standings.PlaceTeam(M6.getLoser(), 4);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		
		standings.PlaceTeam(M7.getLoser(), 2);
		standings.PlaceTeam(M7.getWinner(), 1);
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2, S3);
		super.setChampionshipSeries(M7);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
