package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class ConsultaPedidoProducto {
	
	@JsonProperty(value="tipoProducto")
	private String tipoProducto;
	
	@JsonProperty(value="idProducto")
	private long idProducto;
	
	@JsonProperty(value="numeroUnidadesVendidas")
	private Integer numeroUnidadesVendidas;
	
	@JsonProperty(value="valorProducto")
	private Double valorProducto;
	
	@JsonProperty(value="gananciasGeneradasProducto")
	private Double gananciasGeneradasProducto;
	
	
	public ConsultaPedidoProducto(@JsonProperty(value="tipoProducto")String tipoProducto,
								 @JsonProperty(value="idProducto")long idProducto,
								 @JsonProperty(value="numeroUnidadesVendidas")Integer numeroUnidadesVendidas,
								 @JsonProperty(value="valorProducto")Double valorProducto,
								 @JsonProperty(value="gananciasGeneradasProducto")Double gananciasGeneradasProducto){
		super();
		this.tipoProducto = tipoProducto;
		this.idProducto = idProducto;
		this.numeroUnidadesVendidas = numeroUnidadesVendidas;
		this.valorProducto = valorProducto;
		this.gananciasGeneradasProducto = gananciasGeneradasProducto;	
	}


	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}


	public long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}


	public Integer getNumeroUnidadesVendidas() {
		return numeroUnidadesVendidas;
	}
	public void setNumeroUnidadesVendidas(Integer numeroUnidadesVendidas) {
		this.numeroUnidadesVendidas = numeroUnidadesVendidas;
	}


	public Double getValorProducto() {
		return valorProducto;
	}
	public void setValorProducto(Double valorProducto) {
		this.valorProducto = valorProducto;
	}


	public Double getGananciasGeneradasProducto() {
		return gananciasGeneradasProducto;
	}
	public void setGananciasGeneradasProducto(Double gananciasGeneradasProducto) {
		this.gananciasGeneradasProducto = gananciasGeneradasProducto;
	}
	
	
	
	
	
	

}
