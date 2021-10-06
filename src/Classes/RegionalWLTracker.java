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
	
	public void Print(Region r, boolean forcePrint) {
		Util.PrintSmallLineBreak(forcePrint);
		Util.Print("\nRegional Win Loss Record for: " + r, forcePrint);
		Util.PrintSmallLineBreak(forcePrint);

		Map<Region, WinLossCounter> t = tracker.get(r);
		
		Set<Entry<Region, WinLossCounter>> set = t.entrySet();
		for (Entry<Region, WinLossCounter> e : set) {
			WinLossCounter c = e.getValue();
			if (Driver.SHOW_EMPTY_REGION_WL) {
				Util.Print("\n" + e.getKey() + ", " + e.getValue(), forcePrint);
			} else {
				if (c.getWins() != 0 || c.getLosses() != 0) {
					Util.Print("\nVS: " + e.getKey() + ", " + e.getValue(), forcePrint);
				}	
			}
		}
	}
	
	public void NicePrintAll(boolean showEmpty, boolean forcePrint) {
		NicePrintMajor(showEmpty, forcePrint);
		NicePrintMinor(showEmpty, forcePrint);
	}
	
	public void NicePrintMajor(boolean showEmpty, boolean forcePrint) {
		Util.PrintLargeLineBreak(forcePrint);
		Util.Print("\nRegional Win Loss Records for Major Regions", forcePrint);
		
		List<Region> major = new ArrayList<Region>(Arrays.asList(
				Region.EU,
				Region.NA,
				Region.KR,
				Region.CN,
				Region.SEA));
		
		for (Region r : major) {
			Print(r, forcePrint);
		}
	}
	
	public void NicePrintMinor(boolean showEmpty, boolean forcePrint) {
		Util.PrintLargeLineBreak(false);
		Util.Print("\nRegional Win Loss Records for Minor Regions", forcePrint);
		
		List<Region> minor = new ArrayList<Region>(Arrays.asList(
				Region.RUS,
				Region.TR,
				Region.JP,
				Region.OCE,
				Region.BR,
				Region.LAN,
				Region.VIET));
		
		for (Region r : minor) {
			Print(r, forcePrint);
		}
	}
}
