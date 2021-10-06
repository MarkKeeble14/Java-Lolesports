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
		
		LoopTournament(12000);
	}
	
	// Simulates an Entire Tournament
	public static Tournament SimulateCurrentWorldsFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool PIPool1 = new Pool(Strings.LPIPoolOne, new Team(Teams.LNG), new Team(Teams.HLE), new Team(Teams.BYG), new Team(Teams.C9)); 
		Pool PIPool2 = new Pool(Strings.LPIPoolTwo, new Team(Teams.INF), new Team(Teams.GS), new Team(Teams.UOL), new Team(Teams.PCE), new Team(Teams.RED), new Team(Teams.DFM));
		Pool P1 = new Pool(Strings.LPoolOne, new Team(Teams.DK), new Team(Teams.EDG), new Team(Teams.MAD), new Team(Teams.PSG)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new Team(Teams.O100T), new Team(Teams.FNC), new Team(Teams.GEN), new Team(Teams.FPX));
		Pool P3 = new Pool(Strings.LPoolThree, new Team(Teams.TL), new Team(Teams.T1), new Team(Teams.RGE), new Team(Teams.RNG));
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
		Pool P1 = new Pool(Strings.LPoolOne, new Team(Teams.RNG), new Team(Teams.DK), new Team(Teams.PSG), new Team(Teams.C9), new Team(Teams.MAD), new Team(Teams.GAM)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new Team(Teams.PGG), new Team(Teams.UOL), new Team(Teams.PNG), new Team(Teams.IW), new Team(Teams.DFM), new Team(Teams.INF));
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		Tournament MSI = new TournamentMSI(Strings.LMSI);
		MSI.Simulate(pools);
		MSI.ConcludeTournament();
		return MSI;
	}
	
	// Doesn't allow for SUPER large numbers; 10,000 Works: Takes like 10ish Seconds to run for me
	// You can probably go higher if you want to wait.
	private static void LoopTournament(int x) throws Exception {
		Map<Integer, Tournament> tournamentMap = new HashMap<Integer, Tournament>();
		Map<String, Integer> timesTeamWonMap = new HashMap<String, Integer>();
		for (int i = 0; i < x; i++) {
			Tournament T = SimulateCurrentWorldsFormatFromScratch();
			tournamentMap.put(tournamentMap.size(), T);
			Team champion = T.getWinner();
			if (timesTeamWonMap.containsKey(champion.getTag())) {
				timesTeamWonMap.put(champion.getTag(), timesTeamWonMap.get(champion.getTag()) + 1);
			} else {
				timesTeamWonMap.put(champion.getTag(), 1);
			}
		}
		
		// Print Results 
		// Currently can count duplicate tournament results (Same exact outcomes) as a seperate tournament win,
		// Which might skew the results a little?
		timesTeamWonMap = MapUtil.sortByIntegerValue(timesTeamWonMap);
		Util.NicePrintResults(timesTeamWonMap, x);
		
		int index = 147;
		Util.PrintSectionBreak("Printing out Results of: Simulation #" + index + " -", true);
		tournamentMap.get(index).PrintInfo(true, true, true, true, true);
	}
}
