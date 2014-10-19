package me.trifunovic.spaceassault.game.level;

import java.util.ArrayList;

public class WaveList {

	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private int i=0;

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public void addWave(Wave w) {
		this.waves.add(w);
	}
	
	public Wave getCurrentWave(){
		if(i==waves.size())
			return null;
		else{
			return waves.get(i);
		}
	}
	
	public void next(){
		i++;
	}
	
	public void reset(){
		i=-1;
		waves.clear();
	}

}
