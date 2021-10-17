package TournamentComponents;

import java.util.ArrayList;
import java.util.List;

import DefiningMatches.Series;
import DefiningQualificationDetails.QualificationDetails;
import DefiningTeams.Team;
import StaticVariables.Settings;
import StaticVariables.Strings;

public class BracketSlice {
	private String bracketLabel;
	private String stageLabel;
	private int order;
	private List<Series> series = new ArrayList<Series>();
	
	public BracketSlice(String bracketLabel, String stageLabel, int order) {
		super();
		this.bracketLabel = bracketLabel;
		this.stageLabel = stageLabel;
		this.order = order;
	}

	private void addSeries(Series s) {
		series.add(s);
	}
	
	public void addSeries(Series ...series) {
		for (Series m : series) {
			this.series.add(m);
			m.setStageLabel(getFullLabel());
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

	public String getStageLabel() {
		return stageLabel;
	}
	
	public String getBracketLabel() {
		return bracketLabel;
	}
	
	public String getFullLabel() {
		return Strings.Concat(Strings.BasicBridgeWSpace, bracketLabel, stageLabel);
	}
	
	public int getOrder() {
		return order;
	}
	
	
	public List<Series> getSeriesList() {
		return series;
	}
}
