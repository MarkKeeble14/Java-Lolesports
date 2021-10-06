package TournamentSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import MSI.TournamentMSI;
import Misc.MapUtil;
import Misc.Strings;
import Misc.Teams;
import Misc.Util;
import WorldChampionship.TournamentWorldChampionship;

public class Driver {
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 75;
	
	// If false, nothing will be printed
	public static final boolean PRINT_OUTPUT = false;
	
	//
	public static final boolean PRINT_WINNER = true; 
	
	// 
	public static final boolean PRINT_REGIONAL_WL = false;
	public static final boolean SHOW_EMPTY_REGION_WL = false;
	
	//
	public static final boolean PRINT_FINAL_STANDINGS = false;
	
	// Main
	public static void main(String[] args) throws Exception {
		// SimulateCurrentWorldsState();
		// SimulateCurrentWorldsFormatFromScratch();
		
		// SimulateCurrentMSIFormatFromScratch();
		
		LoopTournament(1);
	}
	
	// Simulates an Entire Tournament
	public static Tournament SimulateCurrentWorldsFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool PIPool1 = new Pool(Strings.LPIPoolOne, Teams.LNG, Teams.HLE, Teams.BYG, Teams.C9); 
		Pool PIPool2 = new Pool(Strings.LPIPoolTwo, Teams.INF, Teams.GS, Teams.UOL, Teams.PCE, Teams.RED, Teams.DFM);
		Pool P1 = new Pool(Strings.LPoolOne, Teams.DK, Teams.EDG, Teams.MAD, Teams.PSG); 
		Pool P2 = new Pool(Strings.LPoolTwo, Teams.O100T, Teams.FNC, Teams.GEN, Teams.FPX);
		Pool P3 = new Pool(Strings.LPoolThree, Teams.TL, Teams.T1, Teams.RGE, Teams.RNG);
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(PIPool1, PIPool2, P1, P2, P3));
		
		Tournament WC = new TournamentWorldChampionship(Strings.LWC);
		WC.Simulate(pools);
		WC.ConcludeTournament();
		return WC;
	}
	
	// Simulates the current World Championship, from it's current state (Updated Manually).
	public static Tournament SimulateCurrentWorldsState() throws Exception {
		TournamentWorldChampionship WC = new TournamentWorldChampionship(Strings.LWC);
		WC.SimulateCurrentWorldsState();
		WC.ConcludeTournament();
		return WC;
	}
	
	public static Tournament SimulateCurrentMSIFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool P1 = new Pool(Strings.LPoolOne, Teams.RNG, Teams.DK, Teams.PSG, Teams.C9, Teams.MAD, Teams.GAM); 
		Pool P2 = new Pool(Strings.LPoolTwo, Teams.PGG, Teams.UOL, Teams.PNG, Teams.IW, Teams.DFM, Teams.INF);
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		Tournament MSI = new TournamentMSI(Strings.LMSI);
		MSI.Simulate(pools);
		MSI.ConcludeTournament();
		return MSI;
	}
	
	// Doesn't allow for very large numbers; 200: Safe Limit - Takes like 5ish Seconds to run
	// 
	// A way to up the threshhold is to remove/comment out any sections where it calls Util.Print; 
	// Doing so allowed me to safely test with numbers up to ~100,000
	private static void LoopTournament(int x) throws Exception {
		Map<Integer, Tournament> tournamentMap = new HashMap<Integer, Tournament>();
		Map<Team, Integer> timesTeamWonMap = new HashMap<Team, Integer>();
		for (int i = 0; i < x; i++) {
			Tournament T = SimulateCurrentWorldsFormatFromScratch();
			tournamentMap.put(tournamentMap.size(), T);
			Team champion = T.getWinner();
			if (timesTeamWonMap.containsKey(champion)) {
				timesTeamWonMap.put(champion, timesTeamWonMap.get(champion) + 1);
			} else {
				timesTeamWonMap.put(champion, 1);
			}
		}
		
		// Print Results 
		// Currently can count duplicate tournament results (Same exact outcomes) as a seperate tournament win,
		// Which might skew the results a little?
		timesTeamWonMap = MapUtil.sortByIntegerValue(timesTeamWonMap);
		Util.NicePrintResults(timesTeamWonMap, x);
		
		int index = 0;
		Util.PrintSectionBreak("Printing out Results of: Simulation #" + index + " -", true);
		tournamentMap.get(index).PrintInfo(true, true, true, true, true);
	}
}
