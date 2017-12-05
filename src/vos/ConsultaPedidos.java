package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class ConsultaPedidos {
	
	
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
		
	@JsonProperty(value="numeroPedidosRealizados")
	private Integer numeroPedidosRealizados;
	
	@JsonProperty(value="gananciasGeneradas")
	private Double gananciasGeneradas;
	
	@JsonProperty(value="productos")
	private ArrayList<ConsultaPedidoProducto> productos;

	public ConsultaPedidos(@JsonProperty(value="idRestaurante")long idRestaurante,
						   @JsonProperty(value="numeroPedidosRealizados")Integer numeroPedidosRealizados,
						   @JsonProperty(value="gananciasGeneradas")Double gananciasGeneradas,
						   @JsonProperty(value="productos")ArrayList<ConsultaPedidoProducto> productos){
		this.idRestaurante = idRestaurante;
		this.numeroPedidosRealizados = numeroPedidosRealizados;
		this.gananciasGeneradas = gananciasGeneradas;
		this.productos = productos;
	}

	public long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	

	public Integer getNumeroPedidosRealizados() {
		return numeroPedidosRealizados;
	}
	public void setNumeroPedidosRealizados(Integer numeroPedidosRealizados) {
		this.numeroPedidosRealizados = numeroPedidosRealizados;
	}

	
	public Double getGananciasGeneradas() {
		return gananciasGeneradas;
	}
	public void setGananciasGeneradas(Double gananciasGeneradas) {
		this.gananciasGeneradas = gananciasGeneradas;
	}

	
	public ArrayList<ConsultaPedidoProducto> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<ConsultaPedidoProducto> productos) {
		this.productos = productos;
	}
	
	
	
	

}
