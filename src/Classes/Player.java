package Classes;

import Misc.Region;

public class Player {
	private String name;
	private float ratingOutOf1; // 1.00 is a perfect score, think of it as a scale out of 100, just divided by 100
	private Region region;
	private boolean isImport;
	
	public String getName() {
		return name;
	}
	
	public float getRatingOutOf1() {
		return ratingOutOf1;
	}
	
	public Region getRegion() {
		return region;
	}

	public boolean isImport() {
		return isImport;
	}

	public Player(String name, float ratingOutOf1, Region region, boolean isImport) {
		super();
		this.name = name;
		this.ratingOutOf1 = ratingOutOf1;
		this.region = region;
		this.isImport = isImport;
	}
}
