package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class CancelarPedido {
	
	@JsonProperty(value="idProducto")
	private long idProducto;
	
	@JsonProperty(value="idPedido")
	private long idPedido;
	
	@JsonProperty(value="idCliente")
	private long idCliente;
	
	@JsonProperty(value="tipoProducto")
	private String tipoProducto;

	
	public CancelarPedido(@JsonProperty(value="idProducto")long idProducto,
						  @JsonProperty(value="idPedido")long idPedido,
						  @JsonProperty(value="idCliente")long idCliente,
						  @JsonProperty(value="tipoProducto")String tipoProducto) {
		super();
		this.idProducto = idProducto;
		this.idPedido = idPedido;
		this.idCliente = idCliente;
		this.tipoProducto = tipoProducto;			
	}

	public long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	
	public long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	

}
