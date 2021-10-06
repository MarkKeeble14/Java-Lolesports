package Misc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Classes.Record;
import Classes.Team;

public class MapUtil {
	public static <K> Map<K, Integer> sortByIntegerValue(Map<K, Integer> unsortMap)
    {
		LinkedHashMap<K, Integer> sortedMap = new LinkedHashMap<>();
		 
		unsortMap.entrySet()
		    .stream()
		    .sorted(Map.Entry.comparingByValue())
		    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		return sortedMap;
    }

	
	public static Map<Team, Record> sortByRecordValue(Map<Team, Record> unsortMap)
    {
		LinkedHashMap<Team, Record> sortedMap = new LinkedHashMap<>();
		 
		unsortMap.entrySet()
		    .stream()
		    .sorted(Map.Entry.comparingByValue())
		    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		return sortedMap;
    }
    
	public static Map<Record, List<Team>> sortSectionsByRecordValue(Map<Record, List<Team>> unsortMap)
    {
		LinkedHashMap<Record, List<Team>> sortedMap = new LinkedHashMap<>();
		 
		unsortMap.entrySet()
		    .stream()
		    .sorted(Map.Entry.comparingByKey())
		    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		return sortedMap;
    }
}
