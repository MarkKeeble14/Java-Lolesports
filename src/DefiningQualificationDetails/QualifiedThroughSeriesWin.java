package DefiningQualificationDetails;

import java.util.Map;

import DefiningMatches.Series;
import DefiningTeams.Team;
import Enums.REASON_FOR_QUALIFICATION;
import StaticVariables.Strings;

public class QualifiedThroughSeriesWin extends QualificationDetails {
	private Series series;
	
	public QualifiedThroughSeriesWin(String label, Series s) {
		super.setLabel(label);
		super.setQualificationReason(REASON_FOR_QUALIFICATION.WonSeries);
		this.series = s;
	}
	
	@Override
	public String Print() {
		Map<Team, Integer> gamescore = series.getGamescore();
		String s = series.getWinner().getTag() + " - " 
				+ series.getFullLabel()
				+ "\nReason For Qualification: " + super.getQualificationReason() 
				+ "; Defeated " + series.getLoser().getTag() 
				+ " | Score: " + gamescore.get(series.getWinner()) + "-" + gamescore.get(series.getLoser());
		return s;
	}

}
