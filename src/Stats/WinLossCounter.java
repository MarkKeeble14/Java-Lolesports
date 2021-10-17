package Stats;

import java.text.DecimalFormat;

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
		int total = (wins + losses);
		if (total > 0) {
			DecimalFormat df = new DecimalFormat("#0.00");
			float n = (float) wins / total * 100;
			return "Wins=" + wins + " | Losses=" + losses + " | Percent - " + df.format(n) + "%";	
		} else {
			return "Wins=" + wins + " | Losses=" + losses;
		}
	}
}
