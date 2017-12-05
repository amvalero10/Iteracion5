package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.ClienteUs;
import vos.Pedido;

public class DAOTablaPedido {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedido() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDO;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Integer mesa = rs.getInt("MESA");
			Double costo = rs.getDouble("COSTO");
			Long idEntrada = rs.getLong("ID_ENTRADA");
			Long idAcomp = rs.getLong("ID_ACOMP");
			Long idPlato = rs.getLong("ID_PLATO");
			Long idBebida = rs.getLong("ID_BEBIDA");
			Long idPostre = rs.getLong("ID_POSTRE");
			Date fecha = rs.getDate("FECHA"); 
			String estado = rs.getString("ESTADO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			pedidos.add(new Pedido(id, idUsuario, mesa, costo, idEntrada, idAcomp, idPlato, idBebida, idPostre, fecha, estado, idRestaurante));
		}
		return pedidos;
	}

	public ArrayList<Pedido> darPedidosUsuario(Long id) throws SQLException, Exception {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO WHERE IDUSUARIO =" +id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id1 = rs.getLong("ID");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Integer mesa = rs.getInt("MESA");
			Double costo = rs.getDouble("COSTO");
			Long idEntrada = rs.getLong("ID_ENTRADA");
			Long idAcomp = rs.getLong("ID_ACOMP");
			Long idPlato = rs.getLong("ID_PLATO");
			Long idBebida = rs.getLong("ID_BEBIDA");
			Long idPostre = rs.getLong("ID_POSTRE");
			Date fecha = rs.getDate("FECHA");
			String estado = rs.getString("ESTADO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			pedidos.add(new Pedido(id1, idUsuario, mesa, costo, idEntrada, idAcomp, idPlato, idBebida, idPostre, fecha, estado, idRestaurante));
		}
		return pedidos;
	}

	
	/**
	 * Metodo que busca el pedido con el id que entra como parametro.
	 * @param id - Id de el pedido a buscar
	 * @return pedido encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Pedido buscarPedidoPorId(Long id) throws SQLException, Exception 
	{
		Pedido pedido = null;

		String sql = "SELECT * FROM PEDIDO WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id2 = rs.getLong("ID");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Integer mesa = rs.getInt("MESA");
			Double costo = rs.getDouble("COSTO");
			Long idEntrada = rs.getLong("ID_ENTRADA");
			Long idAcomp = rs.getLong("ID_ACOMP");
			Long idPlato = rs.getLong("ID_PLATO");
			Long idBebida = rs.getLong("ID_BEBIDA");
			Long idPostre = rs.getLong("ID_POSTRE");
			Date fecha = rs.getDate("FECHA");
			String estado = rs.getString("ESTADO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			pedido = new Pedido(id2, idUsuario, mesa, costo, idEntrada, idAcomp, idPlato, idBebida, idPostre, fecha, estado, idRestaurante);
		}

		return pedido;
	}

	/**
	 * Metodo que agrega el pedido que entra como parametro a la base de datos.
	 * @param pedido - el pedido a agregar. video !=  null
	 * <b> post: </b> se ha agregado el pedido a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el pedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el pedido a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO VALUES (";
		sql += pedido.getId() + ",";
		sql += pedido.getIdUsuario() + ",";
		sql += pedido.getMesa() + ",";
		sql += pedido.getCosto() + ",";
		sql += pedido.getIdEntrada() + ",";
		sql += pedido.getIdAcomp() + ",";
		sql += pedido.getIdPlato() + ",";
		sql += pedido.getIdBebida() + ",";
		sql += pedido.getIdPostre() + ",";
		sql += pedido.getFecha() + ",'";
		sql += pedido.getEstado() + "',";
		sql += pedido.getIdRestaurante() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	/**
	 * Metodo que actualiza el pedido que entra como parametro en la base de datos.
	 * @param pedido - el pedido a actualizar. pedido !=  null
	 * <b> post: </b> se ha actualizado el pedido en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "UPDATE PEDIDO SET ";
		sql += "IDUSUARIO=" + pedido.getIdUsuario() + ",";
		sql += "MESA=" + pedido.getMesa() + ",";
		sql += "COSTO=" + pedido.getCosto()+ ",";
		sql += "ID_ENTRADA=" + pedido.getIdEntrada()+ ",";
		sql += "ID_ACOMP=" + pedido.getIdAcomp()+ ",";
		sql += "ID_PLATO=" + pedido.getIdPlato()+ ",";
		sql += "ID_BEBIDA=" + pedido.getIdBebida()+ ",";
		sql += "ID_POSTRE=" + pedido.getIdPostre()+ ",";
		sql += "FECHA=" + pedido.getFecha()+ ",";
		sql += "ESTADO='" + pedido.getEstado()+ "',";
		sql += "ID_RESTAURANTE=" + pedido.getIdRestaurante();
		sql += " WHERE ID = " + pedido.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el pedido que entra como parametro en la base de datos.
	 * @param pedido - el pedido a borrar. pedido !=  null
	 * <b> post: </b> se ha borrado el pedido en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE ID = " + pedido.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	
	
	
}
