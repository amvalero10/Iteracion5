package vos;

public class ConsultaPedidosAux {
	
	private String tipoProducto;
	private Long idProducto;
	private Integer Cantidad;
	
	public ConsultaPedidosAux(String tipoProducto, Long idProducto,Integer Cantidad){
		this.tipoProducto = tipoProducto;
		this.idProducto = idProducto;
		this.Cantidad = Cantidad;
		
	}

	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	

	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	
	public Integer getCantidad() {
		return Cantidad;
	}
	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}
	
	
	
	
	

}
