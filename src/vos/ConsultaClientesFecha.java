package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaClientesFecha {

	
	@JsonProperty(value="ordenar")
	private String ordenar;
	
	@JsonProperty(value="dini")
	private int dini;
	
	@JsonProperty(value="mini")
	private int mini;
	
	@JsonProperty(value="yini")
	private int yini;
	
	@JsonProperty(value="dfin")
	private int dfin;
	
	@JsonProperty(value="mfin")
	private int mfin;
	
	@JsonProperty(value="yfin")
	private int yfin;
	
	@JsonProperty(value="idr")
	private int idr;
	
	public ConsultaClientesFecha(@JsonProperty(value="ordenar")String ordenar,
			@JsonProperty(value="dini")int dini, @JsonProperty(value="mini")int mini, @JsonProperty(value="yini")int yini,
			@JsonProperty(value="dfin")int dfin, @JsonProperty(value="mfin")int mfin, @JsonProperty(value="yfin")int yfin,
			@JsonProperty(value="idr")int idr){
		setOrdenar(ordenar);
		setDini(dini);
		setMini(mini);
		setYini(yini);
		setDfin(dfin);
		setMfin(mfin);
		setYfin(yfin);
		setIdr(idr);
	}

	/**
	 * @return the ordenar
	 */
	public String getOrdenar() {
		return ordenar;
	}

	/**
	 * @param ordenar the ordenar to set
	 */
	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
	}

	/**
	 * @return the dini
	 */
	public int getDini() {
		return dini;
	}

	/**
	 * @param dini the dini to set
	 */
	public void setDini(int dini) {
		this.dini = dini;
	}

	/**
	 * @return the mini
	 */
	public int getMini() {
		return mini;
	}

	/**
	 * @param mini the mini to set
	 */
	public void setMini(int mini) {
		this.mini = mini;
	}

	/**
	 * @return the yini
	 */
	public int getYini() {
		return yini;
	}

	/**
	 * @param yini the yini to set
	 */
	public void setYini(int yini) {
		this.yini = yini;
	}

	/**
	 * @return the dfin
	 */
	public int getDfin() {
		return dfin;
	}

	/**
	 * @param dfin the dfin to set
	 */
	public void setDfin(int dfin) {
		this.dfin = dfin;
	}

	/**
	 * @return the mfin
	 */
	public int getMfin() {
		return mfin;
	}

	/**
	 * @param mfin the mfin to set
	 */
	public void setMfin(int mfin) {
		this.mfin = mfin;
	}

	/**
	 * @return the yfin
	 */
	public int getYfin() {
		return yfin;
	}

	/**
	 * @param yfin the yfin to set
	 */
	public void setYfin(int yfin) {
		this.yfin = yfin;
	}

	/**
	 * @return the idr
	 */
	public int getIdr() {
		return idr;
	}

	/**
	 * @param idr the idr to set
	 */
	public void setIdr(int idr) {
		this.idr = idr;
	}
	
	
	
}
