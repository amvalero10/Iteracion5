package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestauranteUs extends Usuario{

	public RestauranteUs(@JsonProperty(value="id")long id, @JsonProperty(value="tipoId")String tipoId, 
			@JsonProperty(value="nombre")String nombre, @JsonProperty(value="correo")String correo,
			@JsonProperty(value="rol")String rol) {
		super(id, tipoId, nombre, correo, rol);
		// TODO Auto-generated constructor stub
	}

}
