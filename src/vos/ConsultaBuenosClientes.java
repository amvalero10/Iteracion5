package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *	RFC12
 */
public class ConsultaBuenosClientes {
	
	@JsonProperty(value="clienteUnaVezSemana")
	private ArrayList<ClienteUSAux2> clienteUnaVezSemana;
	
	@JsonProperty(value="clientesPremium")
	private ArrayList<ClienteUsAux> clientesPremium;
	
	@JsonProperty(value="clienteSinConsumo")
	private ArrayList<ClienteUs> clienteSinConsumo;
	
	public ConsultaBuenosClientes(@JsonProperty(value="clienteUnaVezSemana")ArrayList<ClienteUSAux2> clienteUnaVezSemana,
								@JsonProperty(value="clientesPremium")ArrayList<ClienteUsAux> clientesPremium,
								@JsonProperty(value="clienteSinConsumo")ArrayList<ClienteUs> clienteSinConsumo) {
		super();
		this.setClienteUnaVezSemana(clienteUnaVezSemana);
		this.setClientesPremium(clientesPremium);
		this.setClienteSinConsumo(clienteSinConsumo);
	}
	

	public ArrayList<ClienteUSAux2> getClienteUnaVezSemana() {
		return clienteUnaVezSemana;
	}
	public void setClienteUnaVezSemana(ArrayList<ClienteUSAux2> clienteUnaVezSemana) {
		this.clienteUnaVezSemana = clienteUnaVezSemana;
	}


	public ArrayList<ClienteUsAux> getClientesPremium() {
		return clientesPremium;
	}
	public void setClientesPremium(ArrayList<ClienteUsAux> clientesPremium) {
		this.clientesPremium = clientesPremium;
	}


	public ArrayList<ClienteUs> getClienteSinConsumo() {
		return clienteSinConsumo;
	}
	public void setClienteSinConsumo(ArrayList<ClienteUs> clienteSinConsumo) {
		this.clienteSinConsumo = clienteSinConsumo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
