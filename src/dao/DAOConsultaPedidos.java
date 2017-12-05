package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ConsultaPedidoProducto;
import vos.ConsultaPedidosAux;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class DAOConsultaPedidos {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOConsultaPedidos() {
		recursos = new ArrayList<Object>();
	}

	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	public void setConn(Connection con) {
		this.conn = con;
	}

	public Integer numeroDePedidos(Long IdRestaurante) throws SQLException, Exception {

		Integer numeroT = 0;

		String sql = "SELECT COUNT(ID_RESTAURANTE) FROM PEDIDO WHERE ID_RESTAURANTE =" + IdRestaurante;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			numeroT = rs.getInt("COUNT(ID_RESTAURANTE)");
		}

		return numeroT;
	}

	public Double gananciasGenerdas(Long IdRestaurante) throws SQLException, Exception {

		Double numeroT = 0.0;

		String sql = "SELECT SUM(COSTO) FROM PEDIDO WHERE ID_RESTAURANTE = " + IdRestaurante;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			numeroT = rs.getDouble("SUM(COSTO)");
		}

		return numeroT;
	}

	
	
	
	public ArrayList<ConsultaPedidosAux> idsEntradas(Long IdRestaurante) throws SQLException, Exception {

		ArrayList<ConsultaPedidosAux> resp = new ArrayList<>();
		
		String sql = "SELECT DISTINCT ID_ENTRADA, COUNT(*) FROM PEDIDO WHERE ID_RESTAURANTE ="+IdRestaurante+" GROUP BY ID_ENTRADA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			Long idEntrad = rs.getLong("ID_ENTRADA");
			Integer cantidad = rs.getInt("COUNT(*)");
			if (idEntrad == 0) continue;
			
			ConsultaPedidosAux consAct = new ConsultaPedidosAux("entrada", idEntrad, cantidad);
			resp.add(consAct);
		}

		return resp;
	}
	
	
	
	
	public ArrayList<ConsultaPedidosAux> idsAcomps(Long IdRestaurante) throws SQLException, Exception {

		ArrayList<ConsultaPedidosAux> resp = new ArrayList<>();
		
		String sql = "SELECT DISTINCT ID_ACOMP, COUNT(*) FROM PEDIDO WHERE ID_RESTAURANTE ="+IdRestaurante+" GROUP BY ID_ACOMP";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			Long idACOM= rs.getLong("ID_ACOMP");
			Integer cantidad = rs.getInt("COUNT(*)");
			if (idACOM == 0) continue;
			
			ConsultaPedidosAux consAct = new ConsultaPedidosAux("acompaniamiento", idACOM, cantidad);
			resp.add(consAct);
		}

		return resp;
	}
	
	
	
	
	public ArrayList<ConsultaPedidosAux> idsbebidas(Long IdRestaurante) throws SQLException, Exception {

		ArrayList<ConsultaPedidosAux> resp = new ArrayList<>();
		
		String sql = "SELECT DISTINCT ID_BEBIDA, COUNT(*) FROM PEDIDO WHERE ID_RESTAURANTE ="+IdRestaurante+" GROUP BY ID_BEBIDA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			Long idB= rs.getLong("ID_BEBIDA");
			Integer cantidad = rs.getInt("COUNT(*)");
			if (idB == 0) continue;
			
			ConsultaPedidosAux consAct = new ConsultaPedidosAux("bebida", idB, cantidad);
			resp.add(consAct);
		}

		return resp;
	}

	
	
	public ArrayList<ConsultaPedidosAux> idsPlatos(Long IdRestaurante) throws SQLException, Exception {

		ArrayList<ConsultaPedidosAux> resp = new ArrayList<>();
		
		String sql = "SELECT DISTINCT ID_PLATO, COUNT(*) FROM PEDIDO WHERE ID_RESTAURANTE ="+IdRestaurante+" GROUP BY ID_PLATO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			Long idP= rs.getLong("ID_PLATO");
			Integer cantidad = rs.getInt("COUNT(*)");
			if (idP == 0) continue;
			
			ConsultaPedidosAux consAct = new ConsultaPedidosAux("platoFuerte", idP, cantidad);
			resp.add(consAct);
		}

		return resp;
	}
	
	
	
	
	public ArrayList<ConsultaPedidosAux> idsPostres(Long IdRestaurante) throws SQLException, Exception {

		ArrayList<ConsultaPedidosAux> resp = new ArrayList<>();
		
		String sql = "SELECT DISTINCT ID_POSTRE, COUNT(*) FROM PEDIDO WHERE ID_RESTAURANTE ="+IdRestaurante+" GROUP BY ID_POSTRE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			Long idP= rs.getLong("ID_POSTRE");
			Integer cantidad = rs.getInt("COUNT(*)");
			if (idP == 0) continue;
			
			ConsultaPedidosAux consAct = new ConsultaPedidosAux("postre", idP, cantidad);
			resp.add(consAct);
		}

		return resp;
	}
	
	
	
	

}
