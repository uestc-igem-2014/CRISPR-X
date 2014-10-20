package Model;

import java.awt.Color;

public class Imageweizhi {
	String description;
	int endpoint;
	double bili;
	public Color getDescription() {
		if(description.equals("INTERGENIC")){
			return Color.black;
		}
		if(description.equals("UTR")){
			return Color.yellow;
		}
		if(description.equals("EXON")){
			return Color.blue;
		}
		if(description.equals("INTRON")){
			return Color.green;
		}
		return null;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(int endpoint) {
		this.endpoint = endpoint;
	}
	
}
