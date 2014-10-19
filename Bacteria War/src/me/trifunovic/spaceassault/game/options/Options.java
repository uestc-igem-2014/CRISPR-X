package me.trifunovic.spaceassault.game.options;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Options {
	
	SharedPreferences options;
	
	public Options(Activity activity){
		options = PreferenceManager.getDefaultSharedPreferences(activity);
	}
	
	public boolean getSoundEffects(){
		return options.getBoolean("chkSound",true);
	}
	
	public boolean getMusic(){
		return options.getBoolean("chkMusic",true);
	}
	
	public boolean getVibration(){
		return options.getBoolean("chkVibration",false);
	}
	
	public boolean getAutoFire(){
		return options.getBoolean("chkAutoFire",true);
	}
	
	public String getUsername(){
		return options.getString("edUsername",null);
	}
	
	public String getControlls(){
		return options.getString("lstControls","1");
	}
	
	public String getDifficulty(){
		return options.getString("lstDifficulty","-1");
	}
	
	public String getResolution(){
		return options.getString("lstResolution","-1");
	}

}
