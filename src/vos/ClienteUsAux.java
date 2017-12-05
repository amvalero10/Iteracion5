package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteUsAux extends Usuario{
	
	@JsonProperty(value="valorConsumido")
	private double valorConsumido;
	
	
	
	public ClienteUsAux(@JsonProperty(value="id")long id, 
			@JsonProperty(value="tipoId")String tipoId, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="correo")String correo,
			@JsonProperty(value="rol")String rol,
			@JsonProperty(value="valorConsumido")double valorConsumido) {
		
		super(id, tipoId, nombre, correo, rol);
		// TODO Auto-generated constructor stub
		this.valorConsumido = valorConsumido;
		
	}



	public double getValorConsumido() {
		return valorConsumido;
	}
	public void setValorConsumido(double valorConsumido) {
		this.valorConsumido = valorConsumido;
	}

	
	
	

}
