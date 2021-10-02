package Classes;

import Misc.ReasonForQual;

public class QualifiedThroughSeriesWin extends QualificationDetails {
	private Match m;
	
	public QualifiedThroughSeriesWin(String label, Match m) {
		super.setLabel(label);
		super.setQualificationReason(ReasonForQual.WonSeries);
		this.m = m;
	}
	
	@Override
	public String Print() {
		String s = "\n" + super.getLabel() + " - " + super.getT().getTag() 
				+ ": Reason For Qualification: " + super.getQualificationReason() 
				+ "\nMatch: " + m;
		return s;
	}

}
