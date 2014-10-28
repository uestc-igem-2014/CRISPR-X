package Model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class AnalyzeUserspce {
	static String[] specieStr,pamsStr,chromosomeStr;
	static String[][] speciexcStr;
	static List<Specie> kk=new ArrayList<Specie>();
	
	public List<Specie> AnalyzeUserspce(){
		return kk;
	}
	
	public String[] Analyze(String files) throws JSONException{
//		System.out.println(files);
		JSONArray filesname=new JSONArray(files);
		int length=filesname.length();
		specieStr=new String[length];
		for(int i=0;i<length;i++){
			Specie kc=new Specie();
			specieStr[i]=filesname.getJSONObject(i).getString("specie");								
			JSONArray PAMs=filesname.getJSONObject(i).getJSONArray("PAMs");
			JSONArray chromosomes=filesname.getJSONObject(i).getJSONArray("chromosomes");
			int lengthPam=PAMs.length();		
			int lengthchromosome=chromosomes.length();
			pamsStr=new String[lengthPam];
			chromosomeStr=new String[lengthchromosome];
			for(int j=0;j<lengthPam;j++){
				pamsStr[j]=PAMs.getJSONObject(j).getString("PAM");
			}
			
			for(int w=0;w<lengthchromosome;w++){
				chromosomeStr[w]=chromosomes.getJSONObject(w).getString("chromosome");
			}
			kc.setSpecieName(filesname.getJSONObject(i).getString("specie"));
			kc.setPams(pamsStr);
			kc.setChromosomes(chromosomeStr);
			kk.add(kc);
		}
//		new AnalyzeHistory(specieStr);
//		new Intex("").change(filesStr);
		new AnalyzeHistory(kk);
		return specieStr;
	}	
}
