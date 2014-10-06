package Model;

public class Specie{
	private String specieName;
	private String[] pams;
	private String[] chromosomes;
	/**
	 * @return specieName
	 */
	public String getSpecieName() {
		return specieName;
	}
	/**
	 * @param specieName 要设置的 specieName
	 */
	public void setSpecieName(String specieName) {
		this.specieName = specieName;
	}
	/**
	 * @return pams
	 */
	public String[] getPams() {
		return pams;
	}
	/**
	 * @param pams 要设置的 pams
	 */
	public void setPams(String[] pams) {
		this.pams = pams;
	}
	/**
	 * @return chromosomes
	 */
	public String[] getChromosomes() {
		return chromosomes;
	}
	/**
	 * @param chromosomes 要设置的 chromosomes
	 */
	public void setChromosomes(String[] chromosomes) {
		this.chromosomes = chromosomes;
	}
	public String[] getPamsOther(String specieName){
		if(this.specieName.equals(specieName)){
			return pams;
		}else{
			return null;
		}
		
		
	}
}
