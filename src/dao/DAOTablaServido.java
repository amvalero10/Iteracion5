package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author angeloMarcetty
 *
 */
public class DAOTablaServido {
	
	private ArrayList<Object> recursos;

	private Connection conn;
	
	public DAOTablaServido() {
		recursos = new ArrayList<Object>();

	}
	
	
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
	
	
	public void setConn(Connection con){
		this.conn = con;
	}
	
	

	
	
	
	
	
	
public void updatePostreCantidad(Long idpostre, Long cantidad)throws SQLException, Exception{
		
		String sql = "UPDATE POSTRE SET CANTIDAD= CANTIDAD-"+cantidad+" WHERE ID = "+idpostre;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	
	
	
}
