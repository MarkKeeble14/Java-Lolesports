package Classes;

import java.util.ArrayList;
import java.util.List;

import Misc.Util;
import TournamentSimulator.Driver;

public abstract class Tournament {
	private String label;
	private Team winner;
	private Team runnerUp;
	
	public RegionalWLTracker t = new RegionalWLTracker();
	public EOTStandings eots = new EOTStandings();
	
	private List<TournamentComponent> tComps = new ArrayList<TournamentComponent>();
	
	private List<Bracket> brackets = new ArrayList<Bracket>();
	private List<DrawStructure> draws = new ArrayList<DrawStructure>();
	private List<GroupStage> grpStages = new ArrayList<GroupStage>();
	
	public Tournament(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public void PrintHeadline() {
		Util.StartHeadline(label, false);
	}
	
	public abstract void Simulate(List<Pool> pools) throws Exception;
	
	public void ConcludeTournament() {
		Bracket b = brackets.get(brackets.size() - 1);
		
		winner = b.getChampion();
		runnerUp = b.getRunnerUp();
		
		if (Driver.PRINT_WINNER) {
			PrintChampionshipStats(false);
		}
		
		if (Driver.PRINT_REGIONAL_WL) {
			// Print out regional W/L Records
			t.NicePrintMajor(Driver.SHOW_EMPTY_REGION_WL, false);	
		}
		if (Driver.PRINT_FINAL_STANDINGS) {
			// Print out final placements
			eots.Print(false);	
		}
	}
	
	public void PrintLists(boolean forcePrint) {
		Util.StartHeadline("Printing Results of Tournement", true);
		for (TournamentComponent t : tComps) {
			Util.StartSection(t.getLabel(), forcePrint);
			Util.Print(t.toString(), forcePrint);
		}
	}
	
	public void PrintInfo(boolean forcePrint, 
			boolean printWinner, boolean printRegionalWL, 
			boolean printFinalStandings, boolean printTComps) {
		if (printWinner) {
			PrintChampionshipStats(forcePrint);	
		}
		
		if (printRegionalWL) {
			t.NicePrintMajor(Driver.SHOW_EMPTY_REGION_WL, forcePrint);	
		}
		
		if (printFinalStandings) {
			eots.Print(forcePrint);	
		}
		
		if (printTComps) {
			PrintLists(forcePrint);
		}
	}
	
	public void PrintChampionshipStats(boolean forcePrint) {
		Util.Print("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp, forcePrint);
		Util.Print("\n" + winner + " Records: " + winner.qdLog(), forcePrint);
		Util.Print("\n" + winner + " Records: " + winner.recordLog(), forcePrint);
		Util.Print("\n" + winner + " has Won " + label + "; Runner Up: " + runnerUp, forcePrint);	
	}

	public Team getWinner() {
		return winner;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}
	
	public RegionalWLTracker getT() {
		return t;
	}

	public EOTStandings getEots() {
		return eots;
	}
	
	public void addBracket(Bracket b) {
		brackets.add(b);
		tComps.add(b);
	}
	
	public void addGroupStage(GroupStage gs) {
		grpStages.add(gs);
		tComps.add(gs);
	}
	
	public void addDrawStructure(DrawStructure ds) {
		draws.add(ds);
		tComps.add(ds);
	}
	
	public static void PrintQualified(String sectionQualifiedThrough, List<Team> qualified, boolean forcePrint) {
		Util.PrintMediumLineBreak(false);
		Util.Print("\nQualified Teams Through " + sectionQualifiedThrough + ": ", forcePrint);
		for (Team t : qualified) {
			Util.PrintSmallLineBreak(false);
			Util.Print(t.getQD().toString(), forcePrint);
		}
	}
}
