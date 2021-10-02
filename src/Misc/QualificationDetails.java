package Misc;

import Classes.Team;

public abstract class QualificationDetails {
	private ReasonForQual qualificationReason;
	private String label;
	private Team t;
	
	public abstract String Print();

	public ReasonForQual getQualificationReason() {
		return qualificationReason;
	}

	public void setQualificationReason(ReasonForQual qualificationReason) {
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
