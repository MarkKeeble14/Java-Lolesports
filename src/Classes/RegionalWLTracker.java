package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import Misc.Region;
import Misc.Util;
import TournamentSimulator.Driver;

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
		System.out.println("\nRegional Win Loss Record for: " + r);
		Util.PrintSmallLineBreak(true);

		Map<Region, WinLossCounter> t = tracker.get(r);
		
		Set<Entry<Region, WinLossCounter>> set = t.entrySet();
		for (Entry<Region, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			if (Driver.SHOW_REGIONAL_WL_WITH_0_GAMES) {
				System.out.println("\n" + e.getKey() + ", " + e.getValue());
			} else {
				if (c.getWins() != 0 || c.getLosses() != 0) {
					System.out.println("\nVS: " + e.getKey() + ", " + e.getValue());
				}	
			}
		}
	}
	
	public void NicePrintAll() {
		NicePrintMajor();
		NicePrintMinor();
	}
	
	public void NicePrintMajor() {
		Util.PrintLargeLineBreak(true);
		System.out.println("\nRegional Win Loss Records for Major Regions");
		
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
		Util.PrintLargeLineBreak(false);
		System.out.println("\nRegional Win Loss Records for Minor Regions");
		
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
