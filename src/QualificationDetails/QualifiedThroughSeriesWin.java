package QualificationDetails;

import Matches.Game;
import Misc.ReasonForQual;

public class QualifiedThroughSeriesWin extends QualificationDetails {
	private Game m;
	
	public QualifiedThroughSeriesWin(String label, Game m) {
		super.setLabel(label);
		super.setQualificationReason(ReasonForQual.WonSeries);
		this.m = m;
	}
	
	@Override
	public String Print() {
		String s = "\n" + super.getLabel() + " - " + super.getT().getTag() 
				+ ": Reason For Qualification: " + super.getQualificationReason() 
				+ "\nOpponant: " + m.getLoser().getTag();
		return s;
	}

}
