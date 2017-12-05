package vos;

import java.sql.Time;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class Bebida extends AbstractAlimento{

	public Bebida(@JsonProperty(value="id")Long id,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="cantidad") Integer cantidad,
			@JsonProperty(value="personalizable")Boolean personalizable,@JsonProperty(value="precioVenta") Double precioVenta,
			@JsonProperty(value="restaurante")String restaurante,@JsonProperty(value="categoria") String categoria,@JsonProperty(value="descripcion") String descripcion,
			@JsonProperty(value="tiempoPrep")Integer tiempoPrep,@JsonProperty(value="precioProd") Double precioProd,@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="descripcionIng")String descripcionIng,@JsonProperty(value="numeroVendidos") Integer numeroVendidos,@JsonProperty(value="traduccion") String traduccion,
			@JsonProperty(value="equivalencia")String equivalencia) {
		super(id, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd,
				tipo, descripcionIng, numeroVendidos, traduccion,equivalencia);
		// TODO Auto-generated constructor stub
	}



}
