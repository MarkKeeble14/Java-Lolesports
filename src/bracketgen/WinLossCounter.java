package bracketgen;

public class WinLossCounter {
	private int wins = 0;
	private int losses = 0;
	
	public int getWins() {
		return wins;
	}
	public int getLosses() {
		return losses;
	}
	
	public void Win() {
		wins++;
	}
	
	public void Lose() {
		losses++;
	}
	
	@Override
	public String toString() {
		return "WinLossCounter [wins=" + wins + ", losses=" + losses + "]";
	}
}
