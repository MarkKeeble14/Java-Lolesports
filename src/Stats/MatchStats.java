package Stats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DefiningMatches.Matchup;

import java.util.Map.Entry;

import DefiningTeams.Team;
import Enums.REGION;
import StaticVariables.Settings;
import StaticVariables.Strings;
import Utility.Util;

public class MatchStats {
	public Map<REGION, Map<REGION, WinLossCounter>> regionalTracker;
	
	public Map<String, List<Matchup>> stageTracker;
	
	public MatchStats() {
		// Regional Tracker
		regionalTracker = new HashMap<REGION, Map<REGION, WinLossCounter>>();
		
		// Iterate over enums using for loop to add a WinLossCounter for every region
        for (REGION r : REGION.values()) {
    		Map<REGION, WinLossCounter> b = new HashMap<REGION, WinLossCounter>();

            for (REGION r2 : REGION.values()) {
        		WinLossCounter d = new WinLossCounter();
            	b.put(r2, d);
            }
    		
    		regionalTracker.put(r, b);
        }
        
        // Stage Tracker
		stageTracker = new LinkedHashMap<String, List<Matchup>>();
	}
	
	public void Update(String stageLabel, Matchup match) {
		UpdateRegional(match);
		UpdateStage(stageLabel, match);
	}
	
	private void UpdateRegional(Matchup match) {
		Team tWon = match.getWinner();
		Team tLost = match.getLoser();
		
		regionalTracker.get(tWon.getRegion()).get(tLost.getRegion()).Win();
		regionalTracker.get(tLost.getRegion()).get(tWon.getRegion()).Lose();
	}
	
	private void UpdateStage(String stageLabel, Matchup match) {
		List<Matchup> stageMatchups = stageTracker.get(stageLabel);
		if (stageMatchups == null) {
			stageMatchups = new ArrayList<Matchup>();
		}
		stageMatchups.add(match);
		stageTracker.put(stageLabel, stageMatchups);
	}
	
	public void PrintAllWLForRegionR(REGION r) {
		Util.PrintSmallLineBreak(true);
		System.out.println("\nWin/Loss Records for: " + r);
		Util.PrintSmallLineBreak(true);

		if (Settings.PRINT_OVERALL_WL) {
			PrintOverallWLForRegionR(r);
		}
		if (Settings.PRINT_INDIVIDUAL_WL) {
			PrintWLByRegionForRegionR(r);
		}
	}
	
	public void PrintOverallWLForRegionR(REGION r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<REGION, WinLossCounter> t = regionalTracker.get(r);
		
		Set<Entry<REGION, WinLossCounter>> set = t.entrySet();
		int wins = 0;
		int losses = 0;
		for (Entry<REGION, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			wins += c.getWins();
			losses += c.getLosses();
		}
		String s1 = "\nOverall W/L:";
		String s2 = "Wins=" + wins;
		String s3 = "| Losses=" + losses;
		String s4 = "| Percent - " + df.format((float)wins / (losses + wins) * 100) + "%";
		System.out.format(Strings.TableFormat, s1, s2, s3, s4);
	}
	
	public void PrintWLByRegionForRegionR(REGION r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<REGION, WinLossCounter> t = regionalTracker.get(r);
		Set<Entry<REGION, WinLossCounter>> set = t.entrySet();
		for (Entry<REGION, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			if (Settings.SHOW_REGIONAL_WL_WITH_0_GAMES) {
				System.out.println(e.getKey() + ", " + e.getValue());
			} else {
				if (c.getWins() != 0 || c.getLosses() != 0) {
					int wins = c.getWins();
					int losses = c.getLosses();
					String s1 = "\n" + e.getKey().toString() + ":";
					String s2 = "Wins=" + wins;
					String s3 = "| Losses=" + losses;
					String s4 = "| Percent - " + df.format((float)wins / (losses + wins) * 100) + "%";
					System.out.format(Strings.TableFormat, s1, s2, s3, s4);
					
				}	
			}
		}
	}
	
	public void PrintMajorRegionWL() {
		Util.PrintSectionBreak("Win/Loss Records for Major Regions");
		
		List<REGION> major = new ArrayList<REGION>(Arrays.asList(
				REGION.EU,
				REGION.NA,
				REGION.KR,
				REGION.CN,
				REGION.SEA));
		
		for (REGION r : major) {
			PrintAllWLForRegionR(r);
		}
	}
	
	public void PrintMinorRegionWL() {
		Util.PrintSectionBreak("Win/Loss Records for Minor Regions");
		
		List<REGION> minor = new ArrayList<REGION>(Arrays.asList(
				REGION.RUS,
				REGION.TR,
				REGION.JP,
				REGION.OCE,
				REGION.BR,
				REGION.LAN,
				REGION.VIET));
		
		for (REGION r : minor) {
			PrintAllWLForRegionR(r);
		}
	}
	
	public int getNumGamesForR(REGION r) {
		int numGames = 0;
		Map<REGION, WinLossCounter> t = regionalTracker.get(r);
		Set<Entry<REGION, WinLossCounter>> set = t.entrySet();
		for (Entry<REGION, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			numGames += c.getWins() + c.getLosses();
		}
		return numGames;
	}
	
	public int getTotalNumEntries() {
		int numGames = 0;
        for (REGION r : REGION.values()) {
        	numGames += getNumGamesForR(r);
        }
        return numGames;
	}
	
	public int getNumGamesByStage(String stageLabel) throws Exception {
		List<Matchup> matches = stageTracker.get(stageLabel);
		if (matches.size() == 0) {
			throw new Exception();
		}
		return matches.size();
	}
	
	public String stringifyNumGamesByStage() throws Exception {
		String s = "\n";
		
		Set<Entry<String, List<Matchup>>> set = stageTracker.entrySet();
		for (Entry<String, List<Matchup>> e : set) {
			s += e.getKey() + ": " + getNumGamesByStage(e.getKey()) + "\n";
		}
		
		return s;
	}
	
	public int getTotalNumGames() {
		int numGames = 0;
		
		Collection<List<Matchup>> values = stageTracker.values();
		for (List<Matchup> m : values) {
			numGames += m.size();
		}
		
        return numGames;
	}
}
