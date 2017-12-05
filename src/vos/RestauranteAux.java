package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestauranteAux extends Restaurante {
	
	@JsonProperty(value="numeroVisitas")
	private Integer numeroVisita;
	
	public RestauranteAux(@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="cuentaBancaria")Long cuentaBancaria,
			@JsonProperty(value="personalizable")Boolean personalizable,
			@JsonProperty(value="descripcion")String descripcion,
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="paginaWeb")String paginaWeb,
			@JsonProperty(value="representante")String representante,
			@JsonProperty(value="capacidadMax")Integer capacidadMax,
			@JsonProperty(value="numeroVisitas")Integer numeroVisita) {
	super(id,nombre,cuentaBancaria,personalizable,descripcion,tipo,paginaWeb,representante,capacidadMax);
	this.numeroVisita = numeroVisita;
	}


	public Integer getNumeroVisita() {
		return numeroVisita;
	}
	public void setNumeroVisita(Integer numeroVisita) {
		this.numeroVisita = numeroVisita;
	}
	
	
}
