package me.trifunovic.spaceassault.game.level;

import org.anddev.andengine.entity.scene.Scene;

public class LevelScene extends Scene {
	
	private WaveList waves;

	public LevelScene(int pLayerCount) {
		super(pLayerCount);
		initScene();
	}
	
	public void setWaves(WaveList w){
		waves=w;
	}
	
	public WaveList getWaves(){
		return waves;
	}	
	
	public void initScene(){
		
	}

}
