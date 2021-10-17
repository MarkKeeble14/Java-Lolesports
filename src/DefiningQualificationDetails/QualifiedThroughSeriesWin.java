package DefiningQualificationDetails;

import DefiningMatches.Game;
import Enums.REASON_FOR_QUALIFICATION;

public class QualifiedThroughSeriesWin extends QualificationDetails {
	private Game m;
	
	public QualifiedThroughSeriesWin(String label, Game m) {
		super.setLabel(label);
		super.setQualificationReason(REASON_FOR_QUALIFICATION.WonSeries);
		this.m = m;
	}
	
	@Override
	public String Print() {
		String s = super.getLabel() + " - " + super.getT().getTag() 
				+ ": Reason For Qualification: " + super.getQualificationReason() 
				+ "\nOpponant: " + m.getLoser().getTag();
		return s;
	}

}
