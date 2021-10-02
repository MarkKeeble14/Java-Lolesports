package Classes;

import Misc.ReasonForQual;

public class QualifiedThroughGroupPlacement extends QualificationDetails {
	private Group qualifiedThrough;
	private int placement;
	
	public QualifiedThroughGroupPlacement(String label, Group g, int placement) {
		super.setLabel(label);
		super.setQualificationReason(ReasonForQual.GroupPlacement);
		this.placement = placement;
		qualifiedThrough = g;
	}
	
	@Override
	public String Print() {
		String s = "\n" + super.getLabel() + " - " + super.getT().getTag() 
				+ ": Reason For Qualification: " + super.getQualificationReason() 
				+ "; Placed: " + placement + "\n" + qualifiedThrough.toStandings(super.getLabel());
		return s;
	}
}
