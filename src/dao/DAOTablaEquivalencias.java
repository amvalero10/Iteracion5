package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Pedido;

public class DAOTablaEquivalencias {
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */

	public DAOTablaEquivalencias(){
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

	public void addEquivalenciaIngrediente(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_ING VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalenciaEntrada(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_ENTRADA VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalenciaBebida(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_BEBIDA VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalenciaPostre(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_POSTRE VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalenciaAcomp(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_ACOMP VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalenciaPlato(Long id, Long id2) throws SQLException, Exception{
		String sql = "INSERT INTO EQUIV_PLATO VALUES (";
		sql += id + ",";
		sql += id2 + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaIngrediente(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_ING";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_ENTRADA = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaEntrada(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_ENTRADA";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_ENTRADA = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaBebida(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_BEBIDA";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_BEBIDA = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaPostre(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_POSTRE";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_POSTRE = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaAcompaniamiento(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_ACOMP";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_ACOMP = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteEquivalenciaPlato(Long id, Long id2)throws SQLException, Exception{
		String sql = "DELETE FROM EQUIV_PLATO";
		sql += " WHERE COD_CLIENTE = " + id; 
		sql +=" AND COD_PLATO = " + id2 ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public boolean tieneEquivalenciaEntradaPorId(Long id1, Long id2) throws SQLException, Exception 
	{
		
		String sql = "SELECT * FROM EQUIV_ENTRADA WHERE ID1 =" + id1;
		sql += " AND ID2 =" + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			return true;
		}

		return false;
	}
	
	public boolean tieneEquivalenciaBebidaPorId(Long id1, Long id2) throws SQLException, Exception 
	{
		
		String sql = "SELECT * FROM EQUIV_BEBIDA WHERE ID1 =" + id1;
		sql += " AND ID2 =" + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			return true;
		}

		return false;
	}
	
	public boolean tieneEquivalenciaPostrePorId(Long id1, Long id2) throws SQLException, Exception 
	{
		
		String sql = "SELECT * FROM EQUIV_POSTRE WHERE ID1 =" + id1;
		sql += " AND ID2 =" + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			return true;
		}

		return false;
	}
	
	public boolean tieneEquivalenciaAcompPorId(Long id1, Long id2) throws SQLException, Exception 
	{
		
		String sql = "SELECT * FROM EQUIV_ACOMP WHERE ID1 =" + id1;
		sql += " AND ID2 =" + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			return true;
		}

		return false;
	}
	
	public boolean tieneEquivalenciaPlatoPorId(Long id1, Long id2) throws SQLException, Exception 
	{
		
		String sql = "SELECT * FROM EQUIV_PLATO WHERE ID1 =" + id1;
		sql += " AND ID2 =" + id2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			return true;
		}

		return false;
	}


}
