package com.example.crispr_x;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeciesChromosome {

	public List<Dict> Species(List<Dict> dict_species) {
		dict_species.add(new Dict("0", "Target Genome"));
		dict_species.add(new Dict("E.coli-K12-MG1655", "E.coli"));
		dict_species.add(new Dict("Saccharomyces-cerevisiae", "Saccharomyces"));
		dict_species.add(new Dict("Drosophila melanogaster", "Drosophila melanogaster"));
		dict_species.add(new Dict("Caenorhabditis elegans", "Caenorhabditis elegans"));
		dict_species.add(new Dict("Arabidopsis thaliana", "Arabidopsis thaliana"));
		return dict_species;
	}
	
	public List<Dict> Ecoli_Chromosome(List<Dict> dict_chromosome) {
		dict_chromosome.add(new Dict("NC_000913", "chromosome0"));
		return dict_chromosome;
	}
	
	public List<Dict> Saccharomyces_Chromosome(List<Dict> dict_chromosome) {
		dict_chromosome.add(new Dict("NC_001133-chromosome1", "chromosome1"));
		dict_chromosome.add(new Dict("NC_001134-chromosome2", "chromosome2"));
		dict_chromosome.add(new Dict("NC_001135-chromosome3", "chromosome3"));
		dict_chromosome.add(new Dict("NC_001136-chromosome4", "chromosome4"));
		dict_chromosome.add(new Dict("NC_001137-chromosome5", "chromosome5"));
		dict_chromosome.add(new Dict("NC_001138-chromosome6", "chromosome6"));
		dict_chromosome.add(new Dict("NC_001139-chromosome7", "chromosome7"));
		dict_chromosome.add(new Dict("NC_001140-chromosome8", "chromosome8"));
		dict_chromosome.add(new Dict("NC_001141-chromosome9", "chromosome9"));
		dict_chromosome.add(new Dict("NC_001142-chromosome10", "chromosome10"));
		dict_chromosome.add(new Dict("NC_001143-chromosome11", "chromosome11"));
		dict_chromosome.add(new Dict("NC_001144-chromosome12", "chromosome12"));
		dict_chromosome.add(new Dict("NC_001145-chromosome13", "chromosome13"));
		dict_chromosome.add(new Dict("NC_001146-chromosome14", "chromosome14"));
		dict_chromosome.add(new Dict("NC_001147-chromosome15", "chromosome15"));
		dict_chromosome.add(new Dict("NC_001148-chromosome16", "chromosome16"));
		return dict_chromosome;
	}
	
	public List<Dict> Drosophila_Chromosome(List<Dict> dict_chromosome) {
		dict_chromosome.add(new Dict("NT_033778-CHR2", "chromosome2"));
		dict_chromosome.add(new Dict("NT_033777-CHR3", "chromosome3"));
		dict_chromosome.add(new Dict("NC_004353-CHR4", "chromosome4"));
		dict_chromosome.add(new Dict("NC_004354-CHRX", "chromosomeX"));
		return dict_chromosome;
	}
	
	public List<Dict> Caenorhabditis_Chromosome(List<Dict> dict_chromosome) {
		dict_chromosome.add(new Dict("NC_003279-CHR1", "chromosome1"));
		dict_chromosome.add(new Dict("NC_003280-CHR2", "chromosome2"));
		dict_chromosome.add(new Dict("NC_003281-CHR3", "chromosome3"));
		dict_chromosome.add(new Dict("NC_003282-CHR4", "chromosome4"));
		dict_chromosome.add(new Dict("NC_003283-CHR5", "chromosome5"));
		dict_chromosome.add(new Dict("NC_003284-CHRX", "chromosomeX"));
		return dict_chromosome;
	}
	
	public List<Dict> Arabidopsis_Chromosome(List<Dict> dict_chromosome) {
		dict_chromosome.add(new Dict("NC_003070-CHR1", "chromosome1"));
		dict_chromosome.add(new Dict("NC_003071-CHR2", "chromosome2"));
		dict_chromosome.add(new Dict("NC_003074-CHR3", "chromosome3"));
		dict_chromosome.add(new Dict("NC_003075-CHR4", "chromosome4"));
		dict_chromosome.add(new Dict("NC_003076-CHR5", "chromosome5"));
		return dict_chromosome;
	}
	

	public List<Dict> paseUserSpeciesJson(String json, List<Dict> dict_species){
		dict_species.add(new Dict("0", "Target Genome"));
		try {
			JSONArray jsonArrays = new JSONArray(json);
			for (int i = 0; i < jsonArrays.length(); i++) {
				JSONObject jsonObj = ((JSONObject) jsonArrays.opt(i));
				dict_species.add(new Dict(jsonObj.getString("specie"), jsonObj.getString("specie")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dict_species;
	}
	
	public List<Dict> paseUserChromosomesJson(String json, int position, List<Dict> dict_chromosome){
		try {
			JSONArray jsonArrays = new JSONArray(json);
			JSONObject sclectSpecie = (JSONObject)jsonArrays.opt(position-1);
			JSONArray jsonArrays2 = sclectSpecie.getJSONArray("chromosomes");
			for (int i = 0; i < jsonArrays2.length(); i++) {
				JSONObject jsonObj = ((JSONObject) jsonArrays2.opt(i));
				dict_chromosome.add(new Dict(jsonObj.getString("chromosome"), jsonObj.getString("chromosome")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dict_chromosome;
	}
	
}
