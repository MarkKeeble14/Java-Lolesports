package Stats;

import StaticVariables.Strings;
import Utility.Util;

public class TournamentStats {
	private Standings standings;
	private MatchStats matchStats;
	
	public TournamentStats(int numTeams) {
		standings = new Standings(numTeams);
		matchStats = new MatchStats();
	}
	
	public Standings getStandings() {
		return standings;
	}
	public MatchStats getMatchStats() {
		return matchStats;
	}
	
	public int getTotalNumberOfGames() {
		return matchStats.getTotalNumGames();
	}
	
	public int getNumberOfGamesByStage(String stageLabel) throws Exception {
		return matchStats.getNumGamesByStage(stageLabel);
	}
	
	public void Print() throws Exception {
		PrintNumGames();
	}
	
	public void PrintNumGames() throws Exception {
		Util.PrintSectionBreak("Number of Games Analysis");
		System.out.println(matchStats.stringifyNumGamesByStage() + Strings.SmallLineBreak 
				+ "\n\nTotal Number of Games: " + matchStats.getTotalNumGames() + "\n" + Strings.MediumLineBreak);
	}
}
