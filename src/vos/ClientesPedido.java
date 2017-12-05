package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *RF16
 */
public class ClientesPedido 
{
	@JsonProperty(value="idClientes")
	private ArrayList<Long> idClientes;
	
	@JsonProperty(value="idProductos")
	private ArrayList<Long> idProductos;
	
	@JsonProperty(value="pedidos")
	private ArrayList<Pedido> pedidos;
	
	public ClientesPedido(@JsonProperty(value="idClientes")ArrayList<Long> idClientes,
			@JsonProperty(value="idProductos")ArrayList<Long> idProductos, 
			@JsonProperty(value="pedidos")ArrayList<Pedido> pedidos) {
		super();
		this.idClientes = idClientes;
		this.idProductos = idProductos;	
		this.pedidos = pedidos;
	}

	
	public ArrayList<Long> getIdClientes() {
		return idClientes;
	}
	public void setIdClientes(ArrayList<Long> idClientes) {
		this.idClientes = idClientes;
	}

	
	
	public ArrayList<Long> getIdProductos() {
		return idProductos;
	}
	public void setIdProductos(ArrayList<Long> idProductos) {
		this.idProductos = idProductos;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
	
	
	

}
