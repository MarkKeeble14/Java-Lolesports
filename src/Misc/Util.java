package Misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import StatsTracking.Record;
import Teams.Team;
import TournamentSimulator.DomesticDriver;

import java.util.Map.Entry;

public class Util {
	public static void PrintLargeLineBreak(boolean linebreak) {
			if (linebreak) {
				System.out.println(Strings.LargeLineBreak);	
			} else {
				System.out.println(Strings.LargeLineBreakNoNL);	
			}
	}
	
	public static void PrintMediumLineBreak(boolean linebreak) {
			if (linebreak) {
				System.out.println(Strings.MediumLineBreak);	
			} else {
				System.out.println(Strings.MediumLineBreakNoNL);	
			}
	}
	
	public static void PrintSmallLineBreak(boolean linebreak) {
			if (linebreak) {
				System.out.println(Strings.SmallLineBreak);	
			} else {
				System.out.println(Strings.SmallLineBreakNoNL);	
			}
	}
	
	public static void StartSection(String sectionLabel) {
			PrintMediumLineBreak(true);
			System.out.println("\nSimulating: " + sectionLabel);
			PrintMediumLineBreak(true);	
	}

	public static void StartHeadline(String sectionLabel) {
			PrintLargeLineBreak(true);
			System.out.println("\nSimulating: " + sectionLabel);
			PrintLargeLineBreak(true);
	}
	
	public static void PrintSectionBreak(String sectionLabel) {
			PrintLargeLineBreak(true);
			System.out.println("\n" + sectionLabel);
			PrintLargeLineBreak(true);
	}
	
	public static <K, V> void NicePrintMap(Map<K, V> map) {
		Set<Entry<K, V>> set = map.entrySet();
		for (Entry<K, V> e : set) {
			System.out.println(e.getKey() + ", " + e.getValue() + "\n");
		}
	}
	
	public static <K> void NicePrintResults(Map<K, Integer> map, int timesRan) {
		PrintMediumLineBreak(true);
		System.out.println("\n# of Times X Team Won; Results from " + timesRan + " Simulations");
		PrintMediumLineBreak(true);
		System.out.println();
		
		DecimalFormat df = new DecimalFormat("#0.00###");
		Set<Entry<K, Integer>> set = map.entrySet();
		int i = 0;
		for (Entry<K, Integer> e : set) {
			float n = (float) e.getValue() / timesRan * 100;
			
			if (i == set.size() - 1) {
				System.out.println(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%");
			} else {
				System.out.println(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%\n");	
			}
			i++;
		}
	}
	
	public static void NicePrintStandings(Map<Team, Integer> map) {
		System.out.println();
		
		ArrayList<Team> keys = new ArrayList<Team>(map.keySet());
        for(int i=keys.size()-1; i>=0;i--){
        	Team t = keys.get(i);
        	Record top = t.getTopRecord();
        	
        	if (i == 0) {
        		System.out.println(t.getTag() + ": Finished - " + map.get(t) + " | Run Ended During: " 
            	        + top.getLabel());
        	} else {
        		System.out.println(t.getTag() + ": Finished - " + map.get(t) + " | Run Ended During: " 
            	        + top.getLabel() + "\n");	
        	}
        }
	}
}
