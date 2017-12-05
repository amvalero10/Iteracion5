package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import vos.Evento;

public class DAOTablaEventos {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOEventos
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */

	public DAOTablaEventos(){
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los eventos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM EVENTO;
	 * @return Arraylist con los eventos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Evento> darEventos() throws SQLException, Exception {
		ArrayList<Evento> eventos = new ArrayList<Evento>();

		String sql = "SELECT * FROM EVENTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String fecha = rs.getString("FECHA");
			//Time hora = rs.getTime("HORA");
			String nombre = rs.getString("NOMBRE");
			Integer cantidad = rs.getInt("CANTIDAD");
			eventos.add(new Evento(fecha, cantidad, nombre));
		}
		return eventos;
	}
	
	/**
	 * Metodo que busca el/los eventos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los events a buscar
	 * @return ArrayList con los eventos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Evento> buscarEventosPorName(String name) throws SQLException, Exception {
		ArrayList<Evento> eventos = new ArrayList<Evento>();

		String sql = "SELECT * FROM EVENTO WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String fecha = rs.getString("FECHA");
			//Time hora = rs.getTime("HORA");
			String nombre = rs.getString("NOMBRE");
			Integer cantidad = rs.getInt("CANTIDAD");
			eventos.add(new Evento(fecha, cantidad, nombre));
		}

		return eventos;
	}
	
	/**
	 * Metodo que agrega el evento que entra como parametro a la base de datos.
	 * @param evento - el evento a agregar. video !=  null
	 * <b> post: </b> se ha agregado el evento a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el evento baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el evento a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEvento(Evento evento) throws SQLException, Exception {

		String sql = "INSERT INTO EVENTO VALUES ('";
		sql += evento.getFecha() + "',";
		sql += evento.getCantidad() + ",'";
		sql += evento.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el evento que entra como parametro en la base de datos.
	 * @param evento - el evento a actualizar. evento !=  null
	 * <b> post: </b> se ha actualizado el evento en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el evento.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateEvento(Evento evento) throws SQLException, Exception {

		String sql = "UPDATE EVENTO SET ";
		sql += "FECHA='" + evento.getFecha() + "',";
		//sql += "HORA='" + evento.getHora() + "',";
		sql += "CANTIDAD=" + evento.getCantidad();
		sql += " WHERE NOMBRE = '" + evento.getNombre() + "'";
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	/**
	 * Metodo que elimina el evento que entra como parametro en la base de datos.
	 * @param evento - el evento a borrar. video !=  null
	 * <b> post: </b> se ha borrado el evento en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el evento.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteEvento(Evento evento) throws SQLException, Exception {

		String sql = "DELETE FROM EVENTO";
		sql += " WHERE NOMBRE = " + evento.getNombre();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
}
