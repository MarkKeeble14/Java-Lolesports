package TournamentSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Pool;
import Classes.Tournament;
import MSI.TournamentMSI;
import Misc.Strings;
import Misc.Teams;
import WorldChampionship.TournamentWorldChampionship;

public class Driver {
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 75;
	
	// Main
	public static void main(String[] args) throws Exception {
		SimulateCurrentWorldsFormatFromScratch();
		// SimulateCurrentWorldsState();
		// SimulateCurrentMSIFormatFromScratch();
	}
	
	// Simulates an Entire Tournament
	public static void SimulateCurrentWorldsFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool PIPool1 = new Pool(Strings.LPIPoolOne, Teams.LNG, Teams.HLE, Teams.BYG, Teams.C9); 
		Pool PIPool2 = new Pool(Strings.LPIPoolTwo, Teams.INF, Teams.GS, Teams.UOL, Teams.PCE, Teams.RED, Teams.DFM);
		Pool P1 = new Pool(Strings.LPoolOne, Teams.DK, Teams.EDG, Teams.MAD, Teams.PSG); 
		Pool P2 = new Pool(Strings.LPoolTwo, Teams.O100T, Teams.FNC, Teams.GEN, Teams.FPX);
		Pool P3 = new Pool(Strings.LPoolThree, Teams.TL, Teams.T1, Teams.RGE, Teams.RNG);
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(PIPool1, PIPool2, P1, P2, P3));
		
		Tournament WC = new TournamentWorldChampionship(Strings.LWC);
		WC.Simulate(pools);
	}
	
	// Simulates the current World Championship, from it's current state (Updated Manually).
	public static void SimulateCurrentWorldsState() throws Exception {
		TournamentWorldChampionship WC = new TournamentWorldChampionship(Strings.LWC);
		WC.SimulateCurrentWorldsState();
	}
	
	public static void SimulateCurrentMSIFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool P1 = new Pool(Strings.LPoolOne, Teams.RNG, Teams.DK, Teams.PSG, Teams.C9, Teams.MAD, Teams.GAM); 
		Pool P2 = new Pool(Strings.LPoolTwo, Teams.PGG, Teams.UOL, Teams.PNG, Teams.IW, Teams.DFM, Teams.INF);
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		Tournament MSI = new TournamentMSI(Strings.LMSI);
		MSI.Simulate(pools);
	}
}
