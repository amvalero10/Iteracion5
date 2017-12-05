package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteUSAux2 extends Usuario{
	
	@JsonProperty(value="fechaConsumo")
	private Date fechaConsumo;
	
	@JsonProperty(value="numSemana")
	private int numSemana;
	
	@JsonProperty(value="dia")
	private String dia;
	
	
	
	public ClienteUSAux2(@JsonProperty(value="id")long id, 
			@JsonProperty(value="tipoId")String tipoId, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="correo")String correo,
			@JsonProperty(value="rol")String rol,
			@JsonProperty(value="fechaConsumo")Date fechaConsumo,
			@JsonProperty(value="numSemana")int numSemana,
			@JsonProperty(value="dia")String dia) {
		
		super(id, tipoId, nombre, correo, rol);
		// TODO Auto-generated constructor stub
		this.fechaConsumo = fechaConsumo;
		this.numSemana = numSemana;
		this.dia = dia;
		
	}



	public Date getFechaConsumo() {
		return fechaConsumo;
	}
	public void setFechaConsumo(Date fechaConsumo) {
		this.fechaConsumo = fechaConsumo;
	}



	public int getNumSemana() {
		return numSemana;
	}
	public void setNumSemana(int numSemana) {
		this.numSemana = numSemana;
	}



	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	
	

}
