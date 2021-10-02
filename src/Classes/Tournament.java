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
		System.out.println("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp);
		System.out.println("\n" + winner + " Records: " + winner.qdLog());
		System.out.println("\n" + winner + " Records: " + winner.recordLog());
		System.out.println("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp);
	}
	
	public static void PrintQualified(String sectionQualifiedThrough, List<Team> qualified) {
		Util.PrintMediumLineBreak();
		System.out.println("\nQualified Teams Through " + sectionQualifiedThrough + ": ");
		for (Team t : qualified) {
			Util.PrintSmallLineBreak();
			System.out.println(t.getQD());
		}
	}

	public Team getWinner() {
		return winner;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}
}
