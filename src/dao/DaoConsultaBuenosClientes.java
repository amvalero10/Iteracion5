package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.ClienteUSAux2;
import vos.ClienteUs;
import vos.ClienteUsAux;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class DaoConsultaBuenosClientes {
	
	
	private ArrayList<Object> recursos;

	private Connection conn;

	public DaoConsultaBuenosClientes() {
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
	
	
	
	public ArrayList<ClienteUSAux2> ClientesConsumoSemanal()throws SQLException, Exception{
		
		ArrayList<ClienteUSAux2> ccs = new ArrayList<>();
		
		String sql = "SELECT DISTINCT(P.IDUSUARIO), P.FECHA, to_char(to_date(P.FECHA,'DD/MM/YY'),'WW') AS SEMANA, C.TIPOID, C.NOMBRE, C.CORREO, C.ROL, to_char(to_date(P.FECHA,'DD/MM/YY'),'Day') AS DIA\n" + 
				"FROM PEDIDO P INNER JOIN CLIENTEUS C ON P.IDUSUARIO = C.ID\n" + 
				"ORDER BY FECHA";
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

//		while(rs.next()) {
		
		for (int i = 0; i < 20; i++) {
			
			rs.next();
			
			Long id = rs.getLong("IDUSUARIO");
			Date fecha = rs.getDate("FECHA");
			int semana = rs.getInt("SEMANA");
			String tipoId = rs.getString("TIPOID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			String dia = rs.getString("DIA");
			
			ClienteUSAux2 cu2 = new ClienteUSAux2(id, tipoId, nombre, correo, rol, fecha, semana, dia);
			ccs.add(cu2);
		}
			
		return ccs;		
	}
	
	
	
	
	
	
	
	
	public ArrayList<ClienteUs> clientesSinConsumo() throws SQLException, Exception{
		
		ArrayList<ClienteUs> csc = new ArrayList<>();
		
		String sql = "SELECT C.ID, C.TIPOID, C.NOMBRE, C.CORREO, C.ROL\n"+
				"FROM PEDIDO P RIGHT JOIN CLIENTEUS C ON P.IDUSUARIO = C.ID\n"+
				"WHERE P.IDUSUARIO IS NULL\n"+
				"ORDER BY C.ID ASC";
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

//		while(rs.next()) {
		
		for (int i = 0; i < 20; i++) {
			
			rs.next();
			
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			
			ClienteUs nc = new ClienteUs(id, tipoId, nombre, correo, rol);
			csc.add(nc);			
		}
			
		return csc;	
	}
	
	
	
	
	public ArrayList<ClienteUsAux>clientesPremium() throws SQLException, Exception{
		
		ArrayList<ClienteUsAux> cp = new ArrayList<>();
		
		String sql = "SELECT P.ID, C.TIPOID, C.NOMBRE, C.CORREO, C.ROL, P.COSTO \n" + 
				"FROM PEDIDO P INNER JOIN CLIENTEUS C ON P.IDUSUARIO = C.ID\n" + 
				"AND P.COSTO > 1107000 AND P.ESTADO = 'Servido'";
				
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

//		while(rs.next()) {
		
		for (int i = 0; i < 20; i++) {
			
			rs.next();
			
			Long id = rs.getLong("ID");
			String tipoId = rs.getString("TIPOID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			double costo = rs.getDouble("COSTO");
			
			ClienteUsAux cua = new ClienteUsAux(id, tipoId, nombre, correo, rol, costo);
			cp.add(cua);			
		}
			
		return cp;	
	}
	
	
	
	
	
	
	
	
	
	

}
