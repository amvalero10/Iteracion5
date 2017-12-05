package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.ClienteUs;

public class DAOTablaClienteUs {
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClienteUs() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTESUS;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteUs> darClientes() throws SQLException, Exception {
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();

		String sql = "SELECT * FROM CLIENTEUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientes.add(new ClienteUs(id, tipoId, nombre, correo, rol));
		}
		return clientes;
	}

	/**
	 * Metodo que busca el/los clientes con el nombre que entra como parametro.
	 * @param name - Nombre de el/los clientes a buscar
	 * @return ArrayList con los clientes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteUs> buscarClientePorName(String name) throws SQLException, Exception {
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();

		String sql = "SELECT * FROM CLIENTEUS WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientes.add(new ClienteUs(id, tipoId, nombre, correo, rol));
		}

		return clientes;
	}
	
	public ArrayList<ClienteUs> buscarClientesPedidoFecha(Date ini, Date fin, String ordenar, int idr) throws SQLException{
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();
		String sql = "";
		if(ordenar.equals("nada")){
			 sql = "select c.ID,c.TIPOID,c.NOMBRE,c.CORREO,c.ROL "
					+ "from CLIENTEUS c inner JOIN PEDIDO p on c.ID = p.IDUSUARIO "
					+ "where p.FECHA > '"+ini.getDate()+"/"+ini.getMonth()+"/"+ini.getYear()+"' AND p.FECHA < '"+fin.getDate()+"/"+fin.getMonth()+"/"+fin.getYear()+"' AND p.ID_RESTAURANTE ="+idr+" "
					+ "GROUP BY c.ID, c.TIPOID, c.NOMBRE, c.CORREO, c.ROL";
		}else{
			 sql = "select c.ID,c.TIPOID,c.NOMBRE,c.CORREO,c.ROL "
					+ "from CLIENTEUS c inner JOIN PEDIDO p on c.ID = p.IDUSUARIO "
					+ "where p.FECHA > '"+ini.getDate()+"/"+ini.getMonth()+"/"+ini.getYear()+"' AND p.FECHA < '"+fin.getDate()+"/"+fin.getMonth()+"/"+fin.getYear()+"' AND p.ID_RESTAURANTE ="+idr+" "
					+ "GROUP BY c.ID, c.TIPOID, c.NOMBRE, c.CORREO, c.ROL "
					+ "ORDER BY c."+ordenar;
		}
//		String sql2 = "select c.ID,c.TIPOID,c.NOMBRE,c.CORREO,c.ROL "
//				+ "from CLIENTEUS c full OUTER JOIN PEDIDO p on c.ID = p.IDUSUARIO "
//				+ "where p.FECHA > '01-01-2016' AND p.FECHA < '31-12-2017' "
//				+ "GROUP BY c.ID, c.TIPOID, c.NOMBRE, c.CORREO, c.ROL";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientes.add(new ClienteUs(id, tipoId, nombre, correo, rol));
		}

		return clientes;
	}

	public ArrayList<ClienteUs> buscarClientesPedidoFechaPedido(Date ini, Date fin, String ordenar, int idr) throws SQLException{
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();
		String sql = "";
		
			 sql = "select c.ID,c.TIPOID,c.NOMBRE,c.CORREO,c.ROL,p."+ordenar+" "
					+ "from CLIENTEUS c inner JOIN PEDIDO p on c.ID = p.IDUSUARIO "
					+ "where p.FECHA > '"+ini.getDate()+"/"+ini.getMonth()+"/"+ini.getYear()+"' AND p.FECHA < '"+fin.getDate()+"/"+fin.getMonth()+"/"+fin.getYear()+"' AND p.ID_RESTAURANTE ="+idr+" "
					+ "GROUP BY c.ID, c.TIPOID, c.NOMBRE, c.CORREO, c.ROL, p."+ordenar+" "
					+ "ORDER BY p."+ordenar;
		
//		select c.ID,c.TIPOID,c.NOMBRE,c.CORREO,c.ROL, p.ESTADO 
//		 from CLIENTEUS c full OUTER JOIN PEDIDO p on c.ID = p.IDUSUARIO 
//		 where p.FECHA > '01-01-2016' AND p.FECHA < '31-12-2017' 
//		 GROUP BY c.ID, c.TIPOID, c.NOMBRE, c.CORREO, c.ROL, p.ESTADO 
//        ORDER BY p.ESTADO;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			clientes.add(new ClienteUs(id, tipoId, nombre, correo, rol));
		}

		return clientes;
	}
	
	/**
	 * Metodo que busca el clientes con el id que entra como parametro.
	 * @param name - Id de el clientes a buscar
	 * @return cliente encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteUs buscarClientePorId(Long id) throws SQLException, Exception 
	{
		ClienteUs cliente = null;

		String sql = "SELECT * FROM CLIENTEUS WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id2 = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			cliente = new ClienteUs(id2, tipoId, nombre, correo, rol);
		}

		return cliente;
	}
	
	/**
	 * Metodo que agrega el cliente que entra como parametro a la base de datos.
	 * @param cliente - el cliente a agregar. video !=  null
	 * <b> post: </b> se ha agregado el clientes a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el cliente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cliente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTEUS VALUES (";
		sql += cliente.getId() + ",'";
		sql += cliente.getTipoId() + "','";
		sql += cliente.getNombre() + "','";
		sql += cliente.getCorreo() + "','";
		sql += cliente.getRol() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el cliente en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "UPDATE CLIENTEUS SET ";
		sql += "TIPOID='" + cliente.getTipoId() + "',";
		sql += "NOMBRE='" + cliente.getNombre() + "',";
		sql += "CORREO='" + cliente.getCorreo() + "',";
		sql += "ROL='" + cliente.getRol() + "'";
		sql += " WHERE ID = " + cliente.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a borrar. video !=  null
	 * <b> post: </b> se ha borrado el cliente en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTEUS";
		sql += " WHERE ID = " + cliente.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	
	
	
	
}
