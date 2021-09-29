package bracketgen;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable<Team> {
	private String tag;
	private Region region;
	private double rating;
	
	private List<Record> records = new ArrayList<Record>();
	private int currentRecordIndex = -1;
	
	/**
	* Constructor
	* @param tag The teams tag, i.e., Cloud9 has C9, FNATIC has FNC, etc
	* @param region The region the team is from
	* @param rating A rating of the teams skill, higher is better
	*/
	public Team(String tag, Region region, double rating) {
		this.tag = tag;
		this.rating = rating;
		this.region = region;
	}
	
	/**
	* Constructor
	* @param tag The teams tag, i.e., Cloud9 has C9, FNATIC has FNC, etc
	* @param region The region the team is from
	* @param rating A rating of the teams skill, higher is better
	*/
	public Team(Team t) {
		this.tag = t.getTag();
		this.rating = t.getRating();
		this.region = t.getRegion();
	}

	public double getRating() {
		return rating;
	}

	public String getTag() {
		return tag;
	}

	public Region getRegion() {
		return region;
	}
	
	public int getCurrentRecordIndex() {
		return currentRecordIndex;
	}

	public void setCurrentRecordIndex(int currentRecordIndex) {
		this.currentRecordIndex = currentRecordIndex;
	}
	
	public void bumpCurrentRecordIndex() {
		currentRecordIndex++;
	}

	public Record getRecord() {
		return records.get(currentRecordIndex);
	}

	public void setNewRecord(String label) {
		currentRecordIndex++;
		records.add(new Record(label));
	}

	@Override
	public String toString() {
		return "Team [tag=" + tag + ", region=" + region + ", rating=" + rating + "]";
	}
	
	public String recordLog() {
		String s = "";
		for (int i = 0; i < records.size(); i++) {
			Record r = records.get(i);
			if (i == records.size() - 1) {
				s += r.detailedPrint();					
			} else {
				s += r.detailedPrint() + "\n";				
			}
		}
		return s;
	}

	@Override
	public int compareTo(Team that) {
		if (this.getRating() > that.getRating()) {
			return 1;
		} else if (this.getRating() < that.getRating()) {
			return -1;
		}
		return 0;
	}
}
