package DefiningQualificationDetails;

import DefiningTeams.Team;
import Enums.REASON_FOR_QUALIFICATION;

public abstract class QualificationDetails {
	private REASON_FOR_QUALIFICATION qualificationReason;
	private String label;
	private Team t;
	
	public abstract String Print();

	public REASON_FOR_QUALIFICATION getQualificationReason() {
		return qualificationReason;
	}

	public void setQualificationReason(REASON_FOR_QUALIFICATION qualificationReason) {
		this.qualificationReason = qualificationReason;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Team getT() {
		return t;
	}

	public void setT(Team t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return Print();
	}
}
