package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class Ingrediente {
	
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="descripcionEsp")
	private String descripcionEsp;
	
	@JsonProperty(value="descripcionIng")
	private String descripcionIng;
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	@JsonProperty(value="alimentoAsc")
	private String alimentoAsc;
	
	@JsonProperty(value="equivalencia")
	private String equivalencia;
	
	public Ingrediente(@JsonProperty(value="id")Long id,@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="tipo")String tipo, @JsonProperty(value="descripcionEsp")String descripcionEsp,
			@JsonProperty(value="descripcionIng")String descripcionIng,@JsonProperty(value="traduccion")String traduccion,
			@JsonProperty(value="alimentoAsc")String alimentoAsc, @JsonProperty(value="equivalencia")String equivalencia)
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcionEsp = descripcionEsp;
		this.descripcionIng = descripcionIng;
		this.traduccion = traduccion;
		this.alimentoAsc = alimentoAsc;
		this.equivalencia = equivalencia;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcionEsp() {
		return descripcionEsp;
	}
	public void setDescripcionEsp(String descripcionEsp) {
		this.descripcionEsp = descripcionEsp;
	}

	public String getDescripcionIng() {
		return descripcionIng;
	}
	public void setDescripcionIng(String descripcionIng) {
		this.descripcionIng = descripcionIng;
	}

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public String getAlimentoAsc() {
		return alimentoAsc;
	}

	public void setAlimentoAsc(String alimentoAsc) {
		this.alimentoAsc = alimentoAsc;
	}

	public String getEquivalencia() {
		return equivalencia;
	}

	public void setEquivalencia(String equivalencia) {
		this.equivalencia = equivalencia;
	}
	
	

}
