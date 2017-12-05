package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class Menu 
{
	
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
	
	@JsonProperty(value="acompaniamientos")
	private ArrayList<Acompaniamiento> acompaniamientos;
	
	@JsonProperty(value="bebidas")
	private ArrayList<Bebida> bebidas;

	@JsonProperty(value="entradas")
	private ArrayList<Entrada> entradas;

	@JsonProperty(value="platoFuertes")
	private ArrayList<PlatoFuerte> platoFuertes;

	@JsonProperty(value="postres")
	private ArrayList<Postre> postres;

	

	
	public Menu(@JsonProperty(value = "idRestaurante") long idRestaurante, @JsonProperty(value="acompaniamientos")ArrayList<Acompaniamiento> acompaniamientos, 
			@JsonProperty(value="bebidas")ArrayList<Bebida> bebidas , @JsonProperty(value="entradas")ArrayList<Entrada> entradas,@JsonProperty(value="platoFuertes")ArrayList<PlatoFuerte> platoFuertes,
			@JsonProperty(value="postres")ArrayList<Postre> postres) {
		super();
		this.idRestaurante = idRestaurante;
		this.acompaniamientos = acompaniamientos;
		this.bebidas = bebidas;
		this.entradas = entradas;
		this.platoFuertes = platoFuertes;
		this.postres = postres;
	}

	
	public long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}


	public ArrayList<Acompaniamiento> getAcompaniamientos() {
		return acompaniamientos;
	}
	public void setAcompaniamientos(ArrayList<Acompaniamiento> acompaniamientos) {
		this.acompaniamientos = acompaniamientos;
	}


	public ArrayList<Bebida> getBebidas() {
		return bebidas;
	}
	public void setBebidas(ArrayList<Bebida> bebidas) {
		this.bebidas = bebidas;
	}


	public ArrayList<Entrada> getEntradas() {
		return entradas;
	}
	public void setEntradas(ArrayList<Entrada> entradas) {
		this.entradas = entradas;
	}


	public ArrayList<PlatoFuerte> getPlatoFuertes() {
		return platoFuertes;
	}
	public void setPlatoFuertes(ArrayList<PlatoFuerte> platoFuertes) {
		this.platoFuertes = platoFuertes;
	}


	public ArrayList<Postre> getPostres() {
		return postres;
	}
	public void setPostres(ArrayList<Postre> postres) {
		this.postres = postres;
	}
	
	

	
	
	
	

}
