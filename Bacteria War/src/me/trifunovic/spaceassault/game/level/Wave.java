package me.trifunovic.spaceassault.game.level;

import java.util.ArrayList;

public class Wave {
	
	private ArrayList<String> types = new ArrayList<String>();
	private ArrayList<Integer> counts = new ArrayList<Integer>();
	
	public int size(){
		return types.size();
	}

	public int getCount(int i) {
		return counts.get(i);
	}

	public void setCount(int c) {
		this.counts.add(c);
	}

	public String getType(int i) {
		return types.get(i);
	}

	public void setType(String t) {
		this.types.add(t);
	}
	
	public String display(){
		String temp = "";
		for(int i=0;i<types.size();i++){
			temp += (i+1)+". "+types.get(i)+" "+counts.get(i)+"\n";
		}
		return temp;
	}

}
