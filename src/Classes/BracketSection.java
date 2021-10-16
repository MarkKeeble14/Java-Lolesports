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
	private int order;
	private List<Series> series = new ArrayList<Series>();
	
	public BracketSection(String label, int order) {
		super();
		this.label = label;
		this.order = order;
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
	
	public int getOrder() {
		return order;
	}
	
	
	public List<Series> getSeriesList() {
		return series;
	}
}
