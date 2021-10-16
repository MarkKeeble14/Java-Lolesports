package LCS;

import java.util.List;

import Classes.BracketSection;
import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import Matches.Series;
import Misc.Strings;
import StatsTracking.RegionalWLTracker;
import TournamentComponents.Bracket;

public class MidSeasonShowdown extends Bracket {
	public MidSeasonShowdown(String label, Tournament partOf) {
		super(label, partOf);
		// TODO Auto-generated constructor stub
	}	
	
	public MidSeasonShowdown(String label, Tournament partOf, String fedTeamsThrough) {
		super(label, partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(List<Group> groups) throws Exception {
		RegionalWLTracker tracker = super.getPartOf().getT();
		
		// Set Groups
		Group A = groups.get(0);
		
		String s1 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S1);
		String s2 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S2);
		String s3 = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.S3);
		String fs = Strings.Concat(Strings.BasicBridgeWSpace, super.getLabel(), Strings.FS);
		
		BracketSection S1 = new BracketSection(s1);
		BracketSection S2 = new BracketSection(s2);
		BracketSection S3 = new BracketSection(s3);
		BracketSection S4 = new BracketSection(fs);
		
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
		Series M8 = new Series(8, 5, tracker);
		S4.addSeries(M8);
		
		M1.setTeamA(A.GetTeamFromPlacement(1));
		M1.setTeamB(A.GetTeamFromPlacement(4));
		M1.Simulate();
		M2.setTeamA(A.GetTeamFromPlacement(3));
		M2.setTeamB(A.GetTeamFromPlacement(2));
		M2.Simulate();
		
		M3.setTeamA(A.GetTeamFromPlacement(6));
		M3.setTeamB(M2.getLoser());
		M3.Simulate();
		M4.setTeamA(A.GetTeamFromPlacement(5));
		M4.setTeamB(M1.getLoser());
		M4.Simulate();
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M5.Simulate();
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M6.Simulate();
		
		M7.setTeamA(M5.getLoser());
		M7.setTeamB(M6.getWinner());
		M7.Simulate();
		
		M8.setTeamA(M5.getWinner());
		M8.setTeamB(M7.getWinner());
		M8.Simulate();
		
		// General Tracking Stuff
		super.addBracketSections(S1, S2, S3, S4);
		super.setChampionshipSeries(M8);
	}
}
