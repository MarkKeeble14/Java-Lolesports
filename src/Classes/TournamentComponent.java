package Classes;

public abstract class TournamentComponent {
	private String label;
	
	public abstract String toString();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
