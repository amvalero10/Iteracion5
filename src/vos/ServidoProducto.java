package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServidoProducto {
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="cantidad")
	private Long cantidad;
	
	public ServidoProducto(@JsonProperty(value="id")Long id,
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="cantidad")Long cantidad)
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		
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

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

}
