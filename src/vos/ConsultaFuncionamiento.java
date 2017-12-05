package vos;
/**
 * 
 * @author angeloMarcetty
 * RFC11
 */

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaFuncionamiento {

	@JsonProperty(value="productoMasC")
	private AbstractAlimento productoMasC;
	
	@JsonProperty(value="productoMenosC")
	private AbstractAlimento productoMenosC;
	
	@JsonProperty(value="restauranteMasF")
	private RestauranteAux restauranteMasF;
	
	@JsonProperty(value="restauranteMenosF")
	private RestauranteAux restauranteMenosF;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="dia")
	private String dia;
	
	
	
	

	
	public ConsultaFuncionamiento(@JsonProperty(value="productoMasC") AbstractAlimento productoMasC,
								@JsonProperty(value="productoMenosC") AbstractAlimento productoMenosC,
								@JsonProperty(value="restauranteMasF") RestauranteAux restauranteMasF,
								@JsonProperty(value="restauranteMenosF") RestauranteAux restauranteMenosF,
								@JsonProperty(value="fecha")Date fecha,
								@JsonProperty(value="dia")String dia) {
		super();
		this.productoMasC = productoMasC;
		this.productoMenosC = productoMenosC;
		this.restauranteMasF = restauranteMasF;
		this.restauranteMenosF = restauranteMenosF;	
		this.fecha = fecha;
		this.dia = dia;
	}
	
	
	
	public AbstractAlimento getProductoMasC() {
		return productoMasC;
	}
	public void setProductoMasC(AbstractAlimento productoMasC) {
		this.productoMasC = productoMasC;
	}
	

	public AbstractAlimento getProductoMenosC() {
		return productoMenosC;
	}
	public void setProductoMenosC(AbstractAlimento productoMenosC) {
		this.productoMenosC = productoMenosC;
	}

	
	public RestauranteAux getRestauranteMasF() {
		return restauranteMasF;
	}
	public void setRestauranteMasF(RestauranteAux restauranteMasF) {
		this.restauranteMasF = restauranteMasF;
	}

	
	
	public RestauranteAux getRestauranteMenosF() {
		return restauranteMenosF;
	}
	public void setRestauranteMenosF(RestauranteAux restauranteMenosF) {
		this.restauranteMenosF = restauranteMenosF;
	}


	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}

	
	

	

	
	
	
	
}
