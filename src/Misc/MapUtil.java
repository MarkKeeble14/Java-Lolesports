package Misc;

import java.util.LinkedHashMap;
import java.util.Map;

import Classes.Record;
import Classes.Team;

public class MapUtil {
	public static Map<Team, Integer> sortByIntegerValue(Map<Team, Integer> unsortMap)
    {
		LinkedHashMap<Team, Integer> sortedMap = new LinkedHashMap<>();
		 
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
    
}
