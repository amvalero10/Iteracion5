package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Acompaniamiento;
import vos.Bebida;
import vos.Entrada;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.PlatoFuerte;
import vos.Postre;

public class DAOTablaMenu {
	
	
	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOTablaMenu() {
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


	
	
	
	public ArrayList<Acompaniamiento> darAcompaniamientosRestauranteId(Long id)throws SQLException, Exception {
				
		//acompaniamiento
		ArrayList<Acompaniamiento> acompaniamientos = new ArrayList<>();
				
		String sql = "SELECT * FROM MENU_ACOMPANIAMIENTO A INNER JOIN ACOMPANIAMIENTO A1 ON A.COD_ACOMPANIAMIENTO = A1.ID AND "
				+ "A.COD_RESTAURANTE = "
				+ id;	

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
				
		while(rs.next()) 
		{
				
				Long id2 = rs.getLong("ID");
				String nombre = rs.getString("NOMBRE");
				Integer cantidad = rs.getInt("CANTIDAD");
				Boolean personalizable = rs.getBoolean("PERSONALIZABLE");
				Double precioVenta = rs.getDouble("PRECIO_VENTA");
				String restaurante = rs.getString("RESTAURANTE");
				String categoria = rs.getString("CATEGORIA");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempoPrep = rs.getInt("TIEMPO_PREP");
				Double precioProd = rs.getDouble("PRECIO_PROD");
				String tipo = rs.getString("TIPO");
				String descripcionIng = rs.getString("DESCRIPCION_ING");
				Integer numeroVendidos = rs.getInt("NUMERO_VENDIDOS");
				String traduccion = rs.getString("TRADUCCION");
				String equivalencia = rs.getString("EQUIVALENCIA");
					
				Acompaniamiento acom = new Acompaniamiento(id2, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd, tipo, descripcionIng, numeroVendidos, traduccion, equivalencia);				
				acompaniamientos.add(acom);
		}
	
		return acompaniamientos;	
	}
	
	
	public ArrayList<Bebida> darBebidasRestauranteId(Long id)throws SQLException, Exception {
		
		ArrayList<Bebida> bebidas = new ArrayList<>();
				
		String sql = "SELECT * FROM MENU_BEBIDA B INNER JOIN BEBIDA B1 ON B.COD_BEBIDA = B1.ID AND "
				+ "B.COD_RESTAURANTE = "
				+ id;	

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
				
		while(rs.next()) 
		{
				
				Long id2 = rs.getLong("ID");
				String nombre = rs.getString("NOMBRE");
				Integer cantidad = rs.getInt("CANTIDAD");
				Boolean personalizable = rs.getBoolean("PERSONALIZABLE");
				Double precioVenta = rs.getDouble("PRECIO_VENTA");
				String restaurante = rs.getString("RESTAURANTE");
				String categoria = rs.getString("CATEGORIA");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempoPrep = rs.getInt("TIEMPO_PREP");
				Double precioProd = rs.getDouble("PRECIO_PROD");
				String tipo = rs.getString("TIPO");
				String descripcionIng = rs.getString("DESCRIPCION_ING");
				Integer numeroVendidos = rs.getInt("NUMERO_VENDIDOS");
				String traduccion = rs.getString("TRADUCCION");
				String equivalencia = rs.getString("EQUIVALENCIA");
					
				Bebida beb = new Bebida(id2, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd, tipo, descripcionIng, numeroVendidos, traduccion, equivalencia);				
				bebidas.add(beb);
		}
	
		return bebidas;	
	}
	
	
	
	
	
	
	public ArrayList<Entrada> darEntradasRestauranteId(Long id)throws SQLException, Exception {
		
		ArrayList<Entrada> entradas = new ArrayList<>();
				
		String sql = "SELECT * FROM MENU_ENTRADA A INNER JOIN ENTRADA A1 ON A.COD_ENTRADA = A1.ID AND "
				+ "A.COD_RESTAURANTE = "
				+ id;	

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
				
		while(rs.next()) 
		{
				
				Long id2 = rs.getLong("ID");
				String nombre = rs.getString("NOMBRE");
				Integer cantidad = rs.getInt("CANTIDAD");
				Boolean personalizable = rs.getBoolean("PERSONALIZABLE");
				Double precioVenta = rs.getDouble("PRECIO_VENTA");
				String restaurante = rs.getString("RESTAURANTE");
				String categoria = rs.getString("CATEGORIA");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempoPrep = rs.getInt("TIEMPO_PREP");
				Double precioProd = rs.getDouble("PRECIO_PROD");
				String tipo = rs.getString("TIPO");
				String descripcionIng = rs.getString("DESCRIPCION_ING");
				Integer numeroVendidos = rs.getInt("NUMERO_VENDIDOS");
				String traduccion = rs.getString("TRADUCCION");
				String equivalencia = rs.getString("EQUIVALENCIA");
					
				Entrada entrs = new Entrada(id2, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd, tipo, descripcionIng, numeroVendidos, traduccion, equivalencia);				
				entradas.add(entrs);
		}
	
		return entradas;	
	}
	
	
	
	
	public ArrayList<PlatoFuerte> darPlatoFuertesRestauranteId(Long id)throws SQLException, Exception {
		
		ArrayList<PlatoFuerte> platoFuertes = new ArrayList<>();
				
		String sql = "SELECT * FROM MENU_PLATOFUERTE A INNER JOIN PLATO_FUERTE A1 ON A.COD_PLATOFUERTE = A1.ID AND "
				+ "A.COD_RESTAURANTE = "
				+ id;	

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
				
		while(rs.next()) 
		{
				
				Long id2 = rs.getLong("ID");
				String nombre = rs.getString("NOMBRE");
				Integer cantidad = rs.getInt("CANTIDAD");
				Boolean personalizable = rs.getBoolean("PERSONALIZABLE");
				Double precioVenta = rs.getDouble("PRECIO_VENTA");
				String restaurante = rs.getString("RESTAURANTE");
				String categoria = rs.getString("CATEGORIA");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempoPrep = rs.getInt("TIEMPO_PREP");
				Double precioProd = rs.getDouble("PRECIO_PROD");
				String tipo = rs.getString("TIPO");
				String descripcionIng = rs.getString("DESCRIPCION_ING");
				Integer numeroVendidos = rs.getInt("NUMERO_VENDIDOS");
				String traduccion = rs.getString("TRADUCCION");
				String equivalencia = rs.getString("EQUIVALENCIA");
					
				PlatoFuerte pls = new PlatoFuerte(id2, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd, tipo, descripcionIng, numeroVendidos, traduccion, equivalencia);				
				platoFuertes.add(pls);
		}
	
		return platoFuertes;	
	}
	
	
	
	
	
	
public ArrayList<Postre> darPostresRestauranteId(Long id)throws SQLException, Exception {
		
		ArrayList<Postre> postres = new ArrayList<>();
				
		String sql = "SELECT * FROM MENU_POSTRE A INNER JOIN POSTRE A1 ON A.COD_POSTRE = A1.ID AND "
				+ "A.COD_RESTAURANTE = "
				+ id;	

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
				
		while(rs.next()) 
		{
				
				Long id2 = rs.getLong("ID");
				String nombre = rs.getString("NOMBRE");
				Integer cantidad = rs.getInt("CANTIDAD");
				Boolean personalizable = rs.getBoolean("PERSONALIZABLE");
				Double precioVenta = rs.getDouble("PRECIO_VENTA");
				String restaurante = rs.getString("RESTAURANTE");
				String categoria = rs.getString("CATEGORIA");
				String descripcion = rs.getString("DESCRIPCION");
				Integer tiempoPrep = rs.getInt("TIEMPO_PREP");
				Double precioProd = rs.getDouble("PRECIO_PROD");
				String tipo = rs.getString("TIPO");
				String descripcionIng = rs.getString("DESCRIPCION_ING");
				Integer numeroVendidos = rs.getInt("NUMERO_VENDIDOS");
				String traduccion = rs.getString("TRADUCCION");
				String equivalencia = rs.getString("EQUIVALENCIA");
					
				Postre pls = new Postre(id2, nombre, cantidad, personalizable, precioVenta, restaurante, categoria, descripcion, tiempoPrep, precioProd, tipo, descripcionIng, numeroVendidos, traduccion, equivalencia);				
				postres.add(pls);
		}
	
		return postres;	
	}
	
	

	

	

	
	
	

//	public void deleteMenu(Menu menu) throws SQLException, Exception {
//
//		String sql = "DELETE FROM MENU";
//		sql += " WHERE COD_RESTAURANTE = " + menu.getIdRestaurante();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
	
	
	
	
	
	

}
