package me.trifunovic.spaceassault.game.level;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import me.trifunovic.spaceassault.game.R;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Level {

	private BaseGameActivity activity;
	private ArrayList<LevelScene> scenes;
	private int currLevel;
	
	public Level(BaseGameActivity e){
		activity = e;
		scenes = new ArrayList<LevelScene>();
		currLevel = 0;
		initScenes();
	}
	
	public void initScenes(){
		for(int i=0;i<4;i++){
			LevelScene s = new LevelScene(3);
			s.setBackground(new ColorBackground(0, 0, 0));
			// LOAD LEVEL
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				LevelHandler myXMLHandler = new LevelHandler();
				xr.setContentHandler(myXMLHandler);
				switch(i){
				case 0:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level1)));
					break;
				case 1:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level2)));
					break;
				case 2:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level3)));
					break;
				case 3:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level4)));
					break;
				}
				s.setWaves(LevelHandler.wavesList);
			} catch (Exception e) {
				System.out.println("XML Pasing Excpetion = " + e);
			}
			scenes.add(s);
		}
	}
	
	public int getLevel(){
		return currLevel;
	}
	
	public LevelScene getScene(){
		return scenes.get(currLevel);
	}
	
	public void next(){
		currLevel++;
		activity.getEngine().setScene(scenes.get(currLevel));
	}
	
	public void reset(){
		currLevel = 0;
	}
}
