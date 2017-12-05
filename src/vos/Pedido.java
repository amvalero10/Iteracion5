package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {
	
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="idUsuario")
	private long idUsuario;
	
	@JsonProperty(value="mesa")
	private int mesa;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="idEntrada")
	private long idEntrada;
	
	@JsonProperty(value="idAcomp")
	private long idAcomp;
	
	@JsonProperty(value="idPlato")
	private long idPlato;
	
	@JsonProperty(value="idBebida")
	private long idBebida;
	
	@JsonProperty(value="idPostre")
	private long idPostre;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="idRestaurante")
	private long idRestaurante;
	
	public Pedido(@JsonProperty(value="id")long id, @JsonProperty(value="idUsuario")long idUsuario,
			@JsonProperty(value="mesa")int mesa, @JsonProperty(value="costo")double costo, @JsonProperty(value="idEntrada")long idEntrada,
			@JsonProperty(value="idAcomp")long idAcomp, @JsonProperty(value="idPlato")long idPlato, @JsonProperty(value="idBebida")long idBebida,
			@JsonProperty(value="idPostre")long idPostre, @JsonProperty(value="fecha")Date fecha,
			@JsonProperty(value="estado")String estado, @JsonProperty(value="idRestaurante")long idRestaurante){
		setId(id);
		setIdUsuario(idUsuario);
		setMesa(mesa);
		setCosto(costo);
		setIdEntrada(idEntrada);
		setIdAcomp(idAcomp);
		setIdPlato(idPlato);
		setIdBebida(idBebida);
		setIdPostre(idPostre);
		setFecha(fecha);
		setEstado(estado);
		setIdRestaurante(idRestaurante);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the mesa
	 */
	public int getMesa() {
		return mesa;
	}

	/**
	 * @param mesa the mesa to set
	 */
	public void setMesa(int mesa) {
		this.mesa = mesa;
	}

	/**
	 * @return the costo
	 */
	public double getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(double costo) {
		this.costo = costo;
	}

	/**
	 * @return the idEntrada
	 */
	public long getIdEntrada() {
		return idEntrada;
	}

	/**
	 * @param idEntrada the idEntrada to set
	 */
	public void setIdEntrada(long idEntrada) {
		this.idEntrada = idEntrada;
	}

	/**
	 * @return the idAcomp
	 */
	public long getIdAcomp() {
		return idAcomp;
	}

	/**
	 * @param idAcomp the idAcomp to set
	 */
	public void setIdAcomp(long idAcomp) {
		this.idAcomp = idAcomp;
	}

	/**
	 * @return the idPlato
	 */
	public long getIdPlato() {
		return idPlato;
	}

	/**
	 * @param idPlato the idPlato to set
	 */
	public void setIdPlato(long idPlato) {
		this.idPlato = idPlato;
	}

	/**
	 * @return the idBebida
	 */
	public long getIdBebida() {
		return idBebida;
	}

	/**
	 * @param idBebida the idBebida to set
	 */
	public void setIdBebida(long idBebida) {
		this.idBebida = idBebida;
	}

	/**
	 * @return the idPostre
	 */
	public long getIdPostre() {
		return idPostre;
	}

	/**
	 * @param idPostre the idPostre to set
	 */
	public void setIdPostre(long idPostre) {
		this.idPostre = idPostre;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the idRestaurante
	 */
	public long getIdRestaurante() {
		return idRestaurante;
	}

	/**
	 * @param idRestaurante the idRestaurante to set
	 */
	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
}
