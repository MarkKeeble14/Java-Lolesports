package StatsTracking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Classes.Team;

import java.util.Map.Entry;
import Misc.Region;
import Misc.Strings;
import Misc.Util;
import TournamentSimulator.DomesticDriver;

public class RegionalWLTracker {
	public Map<Region, Map<Region, WinLossCounter>> tracker;
	
	public RegionalWLTracker() {
		tracker = new HashMap<Region, Map<Region, WinLossCounter>>();
		
		// iterate over enums using for loop
        for (Region r : Region.values()) {
    		Map<Region, WinLossCounter> b = new HashMap<Region, WinLossCounter>();

            for (Region r2 : Region.values()) {
        		WinLossCounter d = new WinLossCounter();
            	b.put(r2, d);
            }
    		
    		tracker.put(r, b);
        }
	}
	
	public void Update(Team tWon, Team tLost) {
		tracker.get(tWon.getRegion()).get(tLost.getRegion()).Win();
		tracker.get(tLost.getRegion()).get(tWon.getRegion()).Lose();
	}
	
	public void Print(Region r) {
		Util.PrintSmallLineBreak(true);
		System.out.println("\nWin/Loss Records for: " + r);
		Util.PrintSmallLineBreak(true);

		if (DomesticDriver.PRINT_OVERALL_WL) {
			PrintOverall(r);
		}
		if (DomesticDriver.PRINT_INDIVIDUAL_WL) {
			PrintIndividual(r);
		}
	}
	
	public void NicePrintAll() {
		NicePrintMajor();
		NicePrintMinor();
	}
	
	public void PrintOverall(Region r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<Region, WinLossCounter> t = tracker.get(r);
		
		Set<Entry<Region, WinLossCounter>> set = t.entrySet();
		int wins = 0;
		int losses = 0;
		for (Entry<Region, WinLossCounter> e : set) {
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
	
	public void PrintIndividual(Region r) {
		DecimalFormat df = new DecimalFormat("#0.00");
		
		Map<Region, WinLossCounter> t = tracker.get(r);
		Set<Entry<Region, WinLossCounter>> set = t.entrySet();
		for (Entry<Region, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			if (DomesticDriver.SHOW_REGIONAL_WL_WITH_0_GAMES) {
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
		
		List<Region> major = new ArrayList<Region>(Arrays.asList(
				Region.EU,
				Region.NA,
				Region.KR,
				Region.CN,
				Region.SEA));
		
		for (Region r : major) {
			Print(r);
		}
	}
	
	public void NicePrintMinor() {
		Util.PrintSectionBreak("Win/Loss Records for Minor Regions");
		
		List<Region> minor = new ArrayList<Region>(Arrays.asList(
				Region.RUS,
				Region.TR,
				Region.JP,
				Region.OCE,
				Region.BR,
				Region.LAN,
				Region.VIET));
		
		for (Region r : minor) {
			Print(r);
		}
	}
}
