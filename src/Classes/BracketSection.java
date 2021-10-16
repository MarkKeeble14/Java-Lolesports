package Classes;

import java.util.ArrayList;
import java.util.List;

import Matches.Series;
import Misc.GlobalVariables;
import Misc.Strings;
import QualificationDetails.QualificationDetails;
import Teams.Team;

public class BracketSection {
	private String label;
	private List<Series> series = new ArrayList<Series>();
	
	public BracketSection(String label) {
		super();
		this.label = label;
	}

	private void addSeries(Series s) {
		series.add(s);
	}
	
	public void addSeries(Series ...series) {
		for (Series m : series) {
			this.series.add(m);
			m.setStageLabel(label);
		}
	}
	
	public Series getSeries(int no) throws Exception {
		for (Series s : series) {
			if (no == s.getLabel()) {
				return s;
			}
		}
		return null;
	}

	public String getLabel() {
		return label;
	}
	
	public List<Series> getSeriesList() {
		return series;
	}
}
