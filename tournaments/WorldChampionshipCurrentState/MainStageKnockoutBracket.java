package WorldChampionshipCurrentState;

import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import DefiningMatches.Series;
import DefiningTeams.Team;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.MatchStats;
import TournamentComponents.Bracket;
import TournamentComponents.BracketSlice;
import TournamentComponents.Group;
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
		
		MatchStats tracker = super.getPartOf().getT();
		Standings standings = super.getPartOf().getEots();
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		Group D = groups.get(3);
		
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
		
		// Qualified Teams
		Team DK = A.GetTeamFromPlacement(1);
		Team C9 = A.GetTeamFromPlacement(2);
		Team T1 = B.GetTeamFromPlacement(1);
		Team EDG = B.GetTeamFromPlacement(2);
		Team RNG = C.GetTeamFromPlacement(1);
		Team HLE = C.GetTeamFromPlacement(2);
		Team GEN = D.GetTeamFromPlacement(1);
		Team MAD = D.GetTeamFromPlacement(2);
		
		M1.setTeamA(RNG);
		M2.setTeamA(GEN);
		M3.setTeamA(T1);
		M4.setTeamA(DK);
		M1.setTeamB(EDG);
		M2.setTeamB(C9);
		M3.setTeamB(HLE);
		M4.setTeamB(MAD);
		
		M1.setResult(RNG, EDG, 2, 3);
		
		M2.setResult(GEN, C9, 3, 0);
		
		M3.setResult(T1, HLE, 3, 0);
		
		M4.setResult(DK, MAD, 3, 0);
		
		standings.PlaceTeamDuringBacketStage(M1.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M2.getLoser(), false);
		standings.PlaceTeamDuringBacketStage(M3.getLoser(), false);
		standings.PlaceTeamDuringBacketStage(M4.getLoser(), false);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate();
		M6.Simulate();
		standings.PlaceTeamDuringBacketStage(M5.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M6.getLoser(), false);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		standings.PlaceTeamDuringBacketStage(M7.getLoser(), true);
		standings.PlaceTeamDuringBacketStage(M7.getWinner(), true);
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2, S3);
		super.setChampionshipSeries(M7);
	}

	@Override
	public void Simulate(Bracket b, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
