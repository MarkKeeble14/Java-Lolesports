package TournamentSimulator;

import Tournaments.CurrentWorldsFormat;
import Tournaments.TournamentFormat;

public class TournamentSimulator {
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 75;
	
	// Main
	public static void main(String[] args) throws Exception {
		SimulateCurrentFormatFromScratch();
		// SimulateCurrentWorldsState();
	}
	
	// Simulates an Entire Tournament
	public static void SimulateCurrentFormatFromScratch() throws Exception {
		TournamentFormat WC = new CurrentWorldsFormat();
		WC.Simulate();
	}
	
	// Simulates the current World Championship, from it's current state (Updated Manually).
	public static void SimulateCurrentWorldsState() throws Exception {
		CurrentWorldsFormat.SimulateCurrentWorldsState();
	}
}
