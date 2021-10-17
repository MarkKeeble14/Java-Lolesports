package Stats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import DefiningTeams.Team;
import Enums.REGION;
import StaticVariables.Settings;
import StaticVariables.Strings;
import Utility.Util;

public class ResultsTracker {
	public Map<REGION, Map<REGION, WinLossCounter>> tracker;
	private int numGames;
	
	public ResultsTracker() {
		tracker = new HashMap<REGION, Map<REGION, WinLossCounter>>();
		numGames = 0;
		
		// iterate over enums using for loop
        for (REGION r : REGION.values()) {
    		Map<REGION, WinLossCounter> b = new HashMap<REGION, WinLossCounter>();

            for (REGION r2 : REGION.values()) {
        		WinLossCounter d = new WinLossCounter();
            	b.put(r2, d);
            }
    		
    		tracker.put(r, b);
        }
	}
	
	public void Update(Team tWon, Team tLost) {
		tracker.get(tWon.getRegion()).get(tLost.getRegion()).Win();
		tracker.get(tLost.getRegion()).get(tWon.getRegion()).Lose();
		
		numGames++;
	}
	
	public void Print(REGION r) {
		Util.PrintSmallLineBreak(true);
		System.out.println("\nWin/Loss Records for: " + r);
		Util.PrintSmallLineBreak(true);

		if (Settings.PRINT_OVERALL_WL) {
			PrintOverall(r);
		}
		if (Settings.PRINT_INDIVIDUAL_WL) {
			PrintIndividual(r);
		}
	}
	
	public void PrintTournamentStats() {
		Util.PrintSmallLineBreak(true);
		System.out.println("\nTotal # of Games: " + numGames);
		Util.PrintSmallLineBreak(true);		
	}
	
	public void PrintOverall(REGION r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<REGION, WinLossCounter> t = tracker.get(r);
		
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
	
	public void PrintIndividual(REGION r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<REGION, WinLossCounter> t = tracker.get(r);
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
	
	public void NicePrintMajor() {
		Util.PrintSectionBreak("Win/Loss Records for Major Regions");
		
		List<REGION> major = new ArrayList<REGION>(Arrays.asList(
				REGION.EU,
				REGION.NA,
				REGION.KR,
				REGION.CN,
				REGION.SEA));
		
		for (REGION r : major) {
			Print(r);
		}
	}
	
	public void NicePrintMinor() {
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
			Print(r);
		}
	}
}
