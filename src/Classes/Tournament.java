package Classes;

import java.util.List;

import Misc.Util;

public abstract class Tournament {
	private String label;
	private Team winner;
	private Team runnerUp;
	
	public Tournament(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public void PrintHeadline() {
		Util.StartHeadline(label);
	}
	
	public abstract void Simulate(List<Pool> pools) throws Exception;
	
	public void PrintChampionStats(Bracket b) {
		winner = b.getChampion();
		runnerUp = b.getRunnerUp();
		
		// Print
		Util.PrintChampionship("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp);
		Util.PrintChampionship("\n" + winner + " Records: " + winner.qdLog());
		Util.PrintChampionship("\n" + winner + " Records: " + winner.recordLog());
		Util.PrintChampionship("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp);
	}
	
	public static void PrintQualified(String sectionQualifiedThrough, List<Team> qualified) {
		Util.PrintMediumLineBreak();
		Util.Print("\nQualified Teams Through " + sectionQualifiedThrough + ": ");
		for (Team t : qualified) {
			Util.PrintSmallLineBreak();
			Util.Print(t.getQD().toString());
		}
	}

	public Team getWinner() {
		return winner;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}
}
