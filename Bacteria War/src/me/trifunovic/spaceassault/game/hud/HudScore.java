package me.trifunovic.spaceassault.game.hud;

public class HudScore {
	
	private int score;
	
	public HudScore(){
		score = 0;
	}
	
	public void reset(){
		score = 0;
	}

	public int getScore() {
		return score;
	}

	public void addPoints() {
		this.score +=50 ;
	}
	
	//¼õ·ÖÊý
	public void subPoints(int points) {
		this.score -= points ;
	}

}
