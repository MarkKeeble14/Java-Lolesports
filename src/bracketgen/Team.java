package bracketgen;

public class Team implements Comparable<Team> {
	private String tag;
	private Region region;
	private double rating;
	private Record record = new Record();

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

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Team [tag=" + tag + ", region=" + region + ", rating=" + rating + "]";
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
