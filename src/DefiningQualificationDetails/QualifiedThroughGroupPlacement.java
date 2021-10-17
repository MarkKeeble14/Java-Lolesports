package DefiningQualificationDetails;

import Enums.REASON_FOR_QUALIFICATION;
import TournamentComponents.Group;

public class QualifiedThroughGroupPlacement extends QualificationDetails {
	private Group qualifiedThrough;
	private int placement;
	
	public QualifiedThroughGroupPlacement(String label, Group g, int placement) {
		super.setLabel(label);
		super.setQualificationReason(REASON_FOR_QUALIFICATION.GroupPlacement);
		this.placement = placement;
		qualifiedThrough = g;
	}
	
	@Override
	public String Print() {
		String s = super.getT().getTag() + " - " + super.getLabel()
				+ "\nReason For Qualification: " + super.getQualificationReason() 
				+ "; Placed: " + placement + "\n" + qualifiedThrough.toStandings(super.getLabel(), true);
		return s;
	}
}
