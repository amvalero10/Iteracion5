package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.AbstractAlimento;
import vos.RestauranteAux;
import vos.VOComparacionAli;

/**
 * 
 * @author angeloMarcetty
 *	RFC11
 */
public class DAOConsultaFuncionamiento {
	
	private ArrayList<Object> recursos;
	
	public String diaActual;


	private Connection conn;

	public DAOConsultaFuncionamiento() {
		recursos = new ArrayList<Object>();
		diaActual = "cs";
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

	

	
	
	
	public ArrayList<Date> fechasDisponibles() throws SQLException, Exception{
		
		ArrayList<Date> fechasDARRAY = new ArrayList<>();
		
		String sql = "SELECT DISTINCT FECHA\n" + 
				"FROM PEDIDO\n" + 
				"WHERE ESTADO = 'Servido'  ORDER BY FECHA";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()) {
			Date fechaDis = rs.getDate("FECHA");
			fechasDARRAY.add(fechaDis);	
		}	
		return fechasDARRAY;
	}
	
	
	
	

	
	
	public RestauranteAux restauranteMasVisitado(Date fecha) throws SQLException, Exception {
		
		RestauranteAux restMas = null;
		
		
		String sql = "SELECT * \n" + 
				"FROM\n" + 
				"(\n" + 
				"SELECT DISTINCT(P.ID_RESTAURANTE), COUNT(P.ID_RESTAURANTE) AS RECURRENCIA , to_char(to_date('"+fecha+"','YYYY/MM/DD'),'Day') AS DIA  \n" + 
				"FROM PEDIDO P\n" + 
				"WHERE FECHA = TO_DATE('"+ fecha +"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
				"GROUP BY P.ID_RESTAURANTE\n" + 
				") A INNER JOIN RESTAURANTES R ON A.ID_RESTAURANTE = R.ID\n" + 
				"WHERE ROWNUM = 1\n" + 
				"ORDER BY RECURRENCIA DESC";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) 
		{

		Long idRestaurante = rs.getLong("ID_RESTAURANTE");
		Integer numVisitas = rs.getInt("RECURRENCIA");
		String nombre = rs.getString("NOMBRE");
		Long cuentaBancaria = rs.getLong("CUENTA_BANCARIA");
		Boolean personzalible = false;
		if(rs.getInt("PERSONALIZABLE") == 1) personzalible = true;
		String descripcion = rs.getString("DESCRIPCION");
		String tipo = rs.getString("TIPO");
		String web = rs.getString("PAGINA_WEB");
		String representante = rs.getString("REPRESENTANTE");
		Integer capacidadMax = rs.getInt("CAPACIDADMAX");
		diaActual = rs.getString("DIA");
		
		restMas = new RestauranteAux(idRestaurante, nombre, cuentaBancaria, personzalible, descripcion, tipo, web, representante, capacidadMax, numVisitas);	
	
		}
		
		return restMas;
	}
	
	
	public RestauranteAux restauranteMenosVisitado(Date fecha) throws SQLException, Exception{
		
		RestauranteAux restMens = null;
		
		String sql = "SELECT * \n" + 
				"FROM\n" + 
				"(\n" + 
				"SELECT DISTINCT(P.ID_RESTAURANTE), COUNT(P.ID_RESTAURANTE) AS RECURRENCIA \n" + 
				"FROM PEDIDO P\n" + 
				"WHERE FECHA = TO_DATE('"+ fecha +"','YYYY/MM/DD') AND ESTADO = 'Servido' \n" + 
				"GROUP BY P.ID_RESTAURANTE\n" + 
				"ORDER BY RECURRENCIA ASC\n" + 
				") A INNER JOIN RESTAURANTES R ON A.ID_RESTAURANTE = R.ID\n" + 
				"WHERE ROWNUM = 1";		
		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) 
		{

		Long idRestaurante = rs.getLong("ID_RESTAURANTE");
		Integer numVisitas = rs.getInt("RECURRENCIA");
		String nombre = rs.getString("NOMBRE");
		Long cuentaBancaria = rs.getLong("CUENTA_BANCARIA");
		Boolean personzalible = false;
		if(rs.getInt("PERSONALIZABLE") == 1) personzalible = true;
		String descripcion = rs.getString("DESCRIPCION");
		String tipo = rs.getString("TIPO");
		String web = rs.getString("PAGINA_WEB");
		String representante = rs.getString("REPRESENTANTE");
		Integer capacidadMax = rs.getInt("CAPACIDADMAX");
		
		restMens = new RestauranteAux(idRestaurante, nombre, cuentaBancaria, personzalible, descripcion, tipo, web, representante, capacidadMax, numVisitas);	
	
		}
		
		return restMens;
		
		
	}
	
	
	
	
	
	public VOComparacionAli alimentoMasConsumido(Date fecha) throws SQLException, Exception {
		
		
		VOComparacionAli vocComp = null;
		
		//sentencia para iniciar con entrada
		String sql = "SELECT *\n" + 
				"FROM\n" + 
				"(\n" + 
				"SELECT DISTINCT(P.ID_ENTRADA) AS ID, COUNT(P.ID_ENTRADA) AS VECES\n" + 
				"FROM PEDIDO P\n" + 
				"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
				"GROUP BY P.ID_ENTRADA\n" + 
				"ORDER BY VECES DESC\n" + 
				") WHERE ROWNUM = 1";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()) {
			
			Integer id = rs.getInt("ID");
			Integer veces = rs.getInt("VECES");	
			vocComp = new VOComparacionAli(id, veces, "entrada");		
		}
		
		vocComp = AuxAlimentoMasAcompaniamiento(vocComp, fecha);
		vocComp = AuxAlimentoMasBebida(vocComp, fecha);
		vocComp = AuxAlimentoMasPlatoFuerte(vocComp, fecha);
		vocComp = AuxAlimentoMasPostre(vocComp, fecha);
		
		return vocComp;
		
	
	}

	
	
	//comparo con acompaniamiento
	private VOComparacionAli AuxAlimentoMasAcompaniamiento(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
		
		//sentencia para iniciar con entrada
				String sql = "SELECT *\n" + 
						"FROM\n" + 
						"(\n" + 
						"SELECT DISTINCT(P.ID_ACOMP) AS ID, COUNT(P.ID_ACOMP) AS VECES\n" + 
						"FROM PEDIDO P\n" + 
						"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
						"GROUP BY P.ID_ACOMP\n" + 
						"ORDER BY VECES DESC\n" + 
						") \n" + 
						"WHERE ROWNUM = 1";
				
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next()) {
					
					Integer id = rs.getInt("ID");
					Integer veces = rs.getInt("VECES");	
					
					if(veces > vopcomp.getVeces())
					{
						vopcomp.setId(id);
						vopcomp.setVeces(veces);
						vopcomp.setTipo("acompaniamiento");
						return vopcomp;
					}
					else
					{
						return vopcomp;
					}
				}
		return vopcomp;
	}
	
	
	
	//comparno con bebeida
	private VOComparacionAli AuxAlimentoMasBebida(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
		
		//sentencia para iniciar con entrada
				String sql = "SELECT *\n" + 
						"FROM\n" + 
						"(\n" + 
						"SELECT DISTINCT(P.ID_BEBIDA) AS ID, COUNT(P.ID_BEBIDA) AS VECES\n" + 
						"FROM PEDIDO P\n" + 
						"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
						"GROUP BY P.ID_BEBIDA\n" + 
						"ORDER BY VECES DESC\n" + 
						") \n" + 
						"WHERE ROWNUM = 1";
				
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				
				while(rs.next()) {
					
					Integer id = rs.getInt("ID");
					Integer veces = rs.getInt("VECES");	
					
					if(veces > vopcomp.getVeces())
					{
						vopcomp.setId(id);
						vopcomp.setVeces(veces);
						vopcomp.setTipo("bebida");
						return vopcomp;
					}
					else
					{
						return vopcomp;
					}
				}
		return vopcomp;
	}
	

	
	
	//comparo con PlatoFuerte
		private VOComparacionAli AuxAlimentoMasPlatoFuerte(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
			
			//sentencia para iniciar con entrada
					String sql = "SELECT *\n" + 
							"FROM\n" + 
							"(\n" + 
							"SELECT DISTINCT(P.ID_PLATO) AS ID, COUNT(P.ID_PLATO) AS VECES\n" + 
							"FROM PEDIDO P\n" + 
							"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
							"GROUP BY P.ID_PLATO\n" + 
							"ORDER BY VECES DESC\n" + 
							") \n" + 
							"WHERE ROWNUM = 1";
					
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next()) {
						
						Integer id = rs.getInt("ID");
						Integer veces = rs.getInt("VECES");	
						
						if(veces > vopcomp.getVeces())
						{
							vopcomp.setId(id);
							vopcomp.setVeces(veces);
							vopcomp.setTipo("platoFuerte");
							return vopcomp;
						}
						else
						{
							return vopcomp;
						}
					}
			return vopcomp;
		}

	

		
		//comparo con Postre
				private VOComparacionAli AuxAlimentoMasPostre(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
					
					//sentencia para iniciar con entrada
							String sql = "SELECT *\n" + 
									"FROM\n" + 
									"(\n" + 
									"SELECT DISTINCT(P.ID_POSTRE) AS ID, COUNT(P.ID_POSTRE) AS VECES\n" + 
									"FROM PEDIDO P\n" + 
									"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
									"GROUP BY P.ID_POSTRE\n" + 
									"ORDER BY VECES DESC\n" + 
									") \n" + 
									"WHERE ROWNUM = 1";
							
							PreparedStatement prepStmt = conn.prepareStatement(sql);
							recursos.add(prepStmt);
							ResultSet rs = prepStmt.executeQuery();
							
							while(rs.next()) {
								
								Integer id = rs.getInt("ID");
								Integer veces = rs.getInt("VECES");	
								
								if(veces > vopcomp.getVeces())
								{
									vopcomp.setId(id);
									vopcomp.setVeces(veces);
									vopcomp.setTipo("postre");
									return vopcomp;
								}
								else
								{
									return vopcomp;
								}
							}
					return vopcomp;
				}

		
		


		
				
				//comparo con las entradas
		public VOComparacionAli alimentoMenosCosumido(Date fecha)throws SQLException, Exception{
			
			VOComparacionAli vocComp = null;
			
			//sentencia para iniciar con entrada
			String sql = "SELECT *\n" + 
					"FROM\n" + 
					"(\n" + 
					"SELECT DISTINCT(P.ID_ENTRADA) AS ID, COUNT(P.ID_ENTRADA) AS VECES\n" + 
					"FROM PEDIDO P\n" + 
					"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
					"GROUP BY P.ID_ENTRADA\n" + 
					"ORDER BY VECES ASC\n" + 
					") \n" + 
					"WHERE ROWNUM = 1";
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			while(rs.next()) {
				
				Integer id = rs.getInt("ID");
				Integer veces = rs.getInt("VECES");	
				vocComp = new VOComparacionAli(id, veces, "entrada");		
			}
			
			vocComp = AuxAlimentoMenosAcompaniamiento(vocComp, fecha);
			vocComp = AuxAlimentoMenosBebida(vocComp, fecha);
			vocComp = AuxAlimentoMenosPlatoFuerte(vocComp, fecha);
			vocComp = AuxAlimentoMenosPostre(vocComp, fecha);
			
			return vocComp;
		}
				
		
		
				
			
		
		
		
		//comparo con acompaniamiento
		private VOComparacionAli AuxAlimentoMenosAcompaniamiento(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
			
			//sentencia para iniciar con entrada
					String sql = "SELECT *\n" + 
							"FROM\n" + 
							"(\n" + 
							"SELECT DISTINCT(P.ID_ACOMP) AS ID, COUNT(P.ID_ACOMP) AS VECES\n" + 
							"FROM PEDIDO P\n" + 
							"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
							"GROUP BY P.ID_ACOMP\n" + 
							"ORDER BY VECES ASC\n" + 
							") \n" + 
							"WHERE ROWNUM = 1";
					
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next()) {
						
						Integer id = rs.getInt("ID");
						Integer veces = rs.getInt("VECES");	
						
						if(veces < vopcomp.getVeces())
						{
							vopcomp.setId(id);
							vopcomp.setVeces(veces);
							vopcomp.setTipo("acompaniamiento");
							return vopcomp;
						}
						else
						{
							return vopcomp;
						}
					}
			return vopcomp;
		}
				


		
		
		//comparno con bebeida
		private VOComparacionAli AuxAlimentoMenosBebida(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
			
			//sentencia para iniciar con entrada
					String sql = "SELECT *\n" + 
							"FROM\n" + 
							"(\n" + 
							"SELECT DISTINCT(P.ID_BEBIDA) AS ID, COUNT(P.ID_BEBIDA) AS VECES\n" + 
							"FROM PEDIDO P\n" + 
							"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
							"GROUP BY P.ID_BEBIDA\n" + 
							"ORDER BY VECES ASC\n" + 
							") \n" + 
							"WHERE ROWNUM = 1";
					
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					ResultSet rs = prepStmt.executeQuery();
					
					while(rs.next()) {
						
						Integer id = rs.getInt("ID");
						Integer veces = rs.getInt("VECES");	
						
						if(veces < vopcomp.getVeces())
						{
							vopcomp.setId(id);
							vopcomp.setVeces(veces);
							vopcomp.setTipo("bebida");
							return vopcomp;
						}
						else
						{
							return vopcomp;
						}
					}
			return vopcomp;
		}
		
		
		
		
		
		
		
		
		
		//comparo con PlatoFuerte
				private VOComparacionAli AuxAlimentoMenosPlatoFuerte(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
					
					//sentencia para iniciar con entrada
							String sql = "SELECT *\n" + 
									"FROM\n" + 
									"(\n" + 
									"SELECT DISTINCT(P.ID_PLATO) AS ID, COUNT(P.ID_PLATO) AS VECES\n" + 
									"FROM PEDIDO P\n" + 
									"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
									"GROUP BY P.ID_PLATO\n" + 
									"ORDER BY VECES ASC\n" + 
									") \n" + 
									"WHERE ROWNUM = 1";
							
							PreparedStatement prepStmt = conn.prepareStatement(sql);
							recursos.add(prepStmt);
							ResultSet rs = prepStmt.executeQuery();
							
							while(rs.next()) {
								
								Integer id = rs.getInt("ID");
								Integer veces = rs.getInt("VECES");	
								
								if(veces < vopcomp.getVeces())
								{
									vopcomp.setId(id);
									vopcomp.setVeces(veces);
									vopcomp.setTipo("platoFuerte");
									return vopcomp;
								}
								else
								{
									return vopcomp;
								}
							}
					return vopcomp;
				}
		
		
		
		
				//comparo con Postre
				private VOComparacionAli AuxAlimentoMenosPostre(VOComparacionAli vopcomp, Date fecha) throws SQLException, Exception{
					
					//sentencia para iniciar con entrada
							String sql = "SELECT *\n" + 
									"FROM\n" + 
									"(\n" + 
									"SELECT DISTINCT(P.ID_POSTRE) AS ID, COUNT(P.ID_POSTRE) AS VECES\n" + 
									"FROM PEDIDO P\n" + 
									"WHERE FECHA = TO_DATE('"+fecha+"','YYYY/MM/DD') AND ESTADO = 'Servido'\n" + 
									"GROUP BY P.ID_POSTRE\n" + 
									"ORDER BY VECES ASC\n" + 
									") \n" + 
									"WHERE ROWNUM = 1";
							
							PreparedStatement prepStmt = conn.prepareStatement(sql);
							recursos.add(prepStmt);
							ResultSet rs = prepStmt.executeQuery();
							
							while(rs.next()) {
								
								Integer id = rs.getInt("ID");
								Integer veces = rs.getInt("VECES");	
								
								if(veces < vopcomp.getVeces())
								{
									vopcomp.setId(id);
									vopcomp.setVeces(veces);
									vopcomp.setTipo("postre");
									return vopcomp;
								}
								else
								{
									return vopcomp;
								}
							}
					return vopcomp;
				}
		
		
		
		
		
}
