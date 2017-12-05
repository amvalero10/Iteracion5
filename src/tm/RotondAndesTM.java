package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.ws.server.ServerSchemaValidationTube;

import dao.DAOTablaEntrada;
import dao.DAOTablaRestaurante;
import vos.AbstractAlimento;
import vos.Entrada;
import vos.Restaurante;
import vos.RestauranteAux;
import vos.Acompaniamiento;
import vos.AdministradorUs;
import vos.Bebida;
import vos.CancelarPedido;
import vos.ClienteUSAux2;
import vos.ClienteUs;
import vos.ClienteUsAux;
import vos.ConsultaBuenosClientes;
import vos.ConsultaFuncionamiento;
import vos.ConsultaPedidoProducto;
import vos.ConsultaPedidos;
import vos.ConsultaPedidosAux;
import vos.Evento;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.PlatoFuerte;
import vos.Postre;
import vos.RestauranteUs;
import vos.ServidoProducto;
import vos.Tarjeta;
import vos.VOComparacionAli;
import vos.Zona;
import dao.DAOConsultaFuncionamiento;
import dao.DAOConsultaPedidos;
import dao.DAOTablaAcompaniamiento;
import dao.DAOTablaAdministradorUs;
import dao.DAOTablaBebida;
import dao.DAOTablaClientePreferencia;
import dao.DAOTablaClienteUs;
import dao.DAOTablaEquivalencias;
import dao.DAOTablaEventos;
import dao.DAOTablaIngrediente;
import dao.DAOTablaMenu;
import dao.DAOTablaPedido;
import dao.DAOTablaPlatoFuerte;
import dao.DAOTablaPostre;
import dao.DAOTablaRestauranteUs;
import dao.DAOTablaServido;
import dao.DAOTablaTarjeta;
import dao.DAOTablaZona;
import dao.DaoConsultaBuenosClientes;



/**
 * Transaction Manager de la aplicacion (TM)
 * @author am.valero10
 *
 */

public class RotondAndesTM {
	
	
	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	
	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;
	
	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;
	
	
	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;
	
	
	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;
	
	
	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * conexion a la base de datos
	 */
	private Connection conn;
	
	
	
	/**
	 * Metodo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	
	
	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	
	
	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////
	

	//aqui van los metodos para traer los dao
	
	// Evento
		public List<Evento> darEventos() throws Exception {
			List<Evento> eventos;
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEventos.setConn(conn);
				eventos = daoEventos.darEventos();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return eventos;
		}
		
		/**
		 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del video a buscar. name != null
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Evento> buscarEventosPorName(String name) throws Exception {
			List<Evento> eventos;
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEventos.setConn(conn);
				eventos = daoEventos.buscarEventosPorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return eventos;
		}

		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addEvento(Evento evento) throws Exception {
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEventos.setConn(conn);
				daoEventos.addEvento(evento);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addEventos(List<Evento> eventos) throws Exception {
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoEventos.setConn(conn);
				Iterator<Evento> it = eventos.iterator();
				while(it.hasNext())
				{
					daoEventos.addEvento(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateEvento(Evento evento) throws Exception {
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEventos.setConn(conn);
				daoEventos.updateEvento(evento);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}


		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteEvento(Evento evento) throws Exception {
			DAOTablaEventos daoEventos = new DAOTablaEventos();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEventos.setConn(conn);
				daoEventos.deleteEvento(evento);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEventos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		//Pedido
		
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Pedido> darPedidos() throws Exception {
			List<Pedido> pedidos;
			DAOTablaPedido daoPedidos = new DAOTablaPedido();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedidos.setConn(conn);
				pedidos = daoPedidos.darPedidos();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedidos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedidos;
		}
		
		public List<AbstractAlimento> darAlimentosPedidosUsuario(Long id) throws Exception {
			List<AbstractAlimento> alimentos = new ArrayList<AbstractAlimento>();
			DAOTablaPedido daoPedidos = new DAOTablaPedido();
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			DAOTablaBebida daoBebida = new DAOTablaBebida();
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			DAOTablaAcompaniamiento daoAcomp = new DAOTablaAcompaniamiento();
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				daoAcomp.setConn(conn);
				daoPostre.setConn(conn);
				daoBebida.setConn(conn);
				daoEntrada.setConn(conn);
				daoPedidos.setConn(conn);
				List<Pedido> pedidos = daoPedidos.darPedidosUsuario(id);
				for (int i = 0; i < pedidos.size(); i++) {
					if(pedidos.get(i).getIdEntrada()!=0){
						AbstractAlimento entAct = daoEntrada.buscarEntradaPorId(pedidos.get(i).getIdEntrada());
						alimentos.add(entAct);
					}
					if(pedidos.get(i).getIdBebida()!=0){
						AbstractAlimento bebAct = daoBebida.buscarBebidaPorId(pedidos.get(i).getIdBebida());
						alimentos.add(bebAct);
					}
					if(pedidos.get(i).getIdPostre()!=0){
						AbstractAlimento posAct = daoPostre.buscarPostrePorId(pedidos.get(i).getIdPostre());
						alimentos.add(posAct);
					}
					if(pedidos.get(i).getIdAcomp()!=0){
						AbstractAlimento acoAct = daoAcomp.buscarAcompaniamientoPorId(pedidos.get(i).getIdAcomp());
						alimentos.add(acoAct);
					}
					if(pedidos.get(i).getIdPlato()!=0){
						AbstractAlimento plaAct = daoPlatoFuerte.buscarPlatoFuertePorId(pedidos.get(i).getIdPlato());
						alimentos.add(plaAct);
					}
				}

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedidos.cerrarRecursos();
					daoEntrada.cerrarRecursos();
					daoBebida.cerrarRecursos();
					daoPostre.cerrarRecursos();
					daoAcomp.cerrarRecursos();
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return alimentos;
		}

		/**
		 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
		 * @param name - Id del video a buscar. name != null
		 * @return Video - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public Pedido buscarPedidoPorId(Long id) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				pedido = daoPedido.buscarPedidoPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addPedido(Pedido pedido) throws Exception {
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaClienteUs daoCliente = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoCliente.setConn(conn);
				ClienteUs cliente = daoCliente.buscarClientePorId(pedido.getIdUsuario());
				cliente.getPedidos().add(pedido);
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoCliente.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		public Pedido addPedidoEquivEntrada(Long id, Long id1, Long id2, Long idN) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaEquivalencias daoEquiv = new DAOTablaEquivalencias();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoEquiv.setConn(conn);
				Boolean entradaEsta = daoEquiv.tieneEquivalenciaEntradaPorId(id1, id2);
			    pedido = daoPedido.buscarPedidoPorId(id);
				if(entradaEsta){
					pedido.setIdEntrada(id2);
					pedido.setId(idN);
				}
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEquiv.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		public Pedido addPedidoEquivBebida(Long id, Long id1, Long id2, Long idN) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaEquivalencias daoEquiv = new DAOTablaEquivalencias();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoEquiv.setConn(conn);
				Boolean entradaEsta = daoEquiv.tieneEquivalenciaBebidaPorId(id1, id2);
			    pedido = daoPedido.buscarPedidoPorId(id);
				if(entradaEsta){
					pedido.setIdBebida(id2);
					pedido.setId(idN);
				}
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEquiv.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		public Pedido addPedidoEquivPostre(Long id, Long id1, Long id2, Long idN) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaEquivalencias daoEquiv = new DAOTablaEquivalencias();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoEquiv.setConn(conn);
				Boolean entradaEsta = daoEquiv.tieneEquivalenciaPostrePorId(id1, id2);
			    pedido = daoPedido.buscarPedidoPorId(id);
				if(entradaEsta){
					pedido.setIdPostre(id2);
					pedido.setId(idN);
				}
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEquiv.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		public Pedido addPedidoEquivAcomp(Long id, Long id1, Long id2, Long idN) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaEquivalencias daoEquiv = new DAOTablaEquivalencias();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoEquiv.setConn(conn);
				Boolean entradaEsta = daoEquiv.tieneEquivalenciaAcompPorId(id1, id2);
			    pedido = daoPedido.buscarPedidoPorId(id);
				if(entradaEsta){
					pedido.setIdAcomp(id2);
					pedido.setId(idN);
				}
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEquiv.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		public Pedido addPedidoEquivPlato(Long id, Long id1, Long id2, Long idN) throws Exception {
			Pedido pedido;
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			DAOTablaEquivalencias daoEquiv = new DAOTablaEquivalencias();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedido.setConn(conn);
				daoEquiv.setConn(conn);
				Boolean entradaEsta = daoEquiv.tieneEquivalenciaPlatoPorId(id1, id2);
			    pedido = daoPedido.buscarPedidoPorId(id);
				if(entradaEsta){
					pedido.setIdPlato(id2);
					pedido.setId(idN);
				}
				daoPedido.addPedido(pedido);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEquiv.cerrarRecursos();
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return pedido;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addPedidos(List<Pedido> pedidos) throws Exception {
			DAOTablaPedido daoPedido = new DAOTablaPedido();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoPedido.setConn(conn);
				Iterator<Pedido> it = pedidos.iterator();
				while(it.hasNext())
				{
					daoPedido.addPedido(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoPedido.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updatePedido(Pedido pedido) throws Exception {
			DAOTablaPedido daoPedidos = new DAOTablaPedido();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedidos.setConn(conn);
				daoPedidos.updatePedido(pedido);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedidos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deletePedido(Pedido pedido) throws Exception {
			DAOTablaPedido daoPedidos = new DAOTablaPedido();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPedidos.setConn(conn);
				daoPedidos.deletePedido(pedido);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPedidos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		//Tarjeta
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Tarjeta> darTarjetas() throws Exception {
			List<Tarjeta> tarjetas;
			DAOTablaTarjeta daoTarjetas = new DAOTablaTarjeta();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoTarjetas.setConn(conn);
				tarjetas = daoTarjetas.darTarjetas();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoTarjetas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return tarjetas;
		}

			
		/**
		 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
		 * @param name - Id del video a buscar. name != null
		 * @return Video - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public Tarjeta buscarTarjetaPorNumero(Long id) throws Exception {
			Tarjeta tarjeta;
			DAOTablaTarjeta daoTarjetas = new DAOTablaTarjeta();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoTarjetas.setConn(conn);
				tarjeta = daoTarjetas.buscarTarjetasPorNumero(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoTarjetas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return tarjeta;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addTarjeta(Tarjeta tarjeta) throws Exception {
			DAOTablaTarjeta daoTarjeta = new DAOTablaTarjeta();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoTarjeta.setConn(conn);
				daoTarjeta.addTarjeta(tarjeta);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoTarjeta.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addTarjetas(List<Tarjeta> tarjetas) throws Exception {
			DAOTablaTarjeta daoTarjetas = new DAOTablaTarjeta();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoTarjetas.setConn(conn);
				Iterator<Tarjeta> it = tarjetas.iterator();
				while(it.hasNext())
				{
					daoTarjetas.addTarjeta(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoTarjetas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateTarjeta(Tarjeta tarjeta) throws Exception {
			DAOTablaTarjeta daoTarjetas = new DAOTablaTarjeta();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoTarjetas.setConn(conn);
				daoTarjetas.updateTarjeta(tarjeta);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoTarjetas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteTarjeta(Tarjeta tarjeta) throws Exception {
			DAOTablaTarjeta daoTarjeta = new DAOTablaTarjeta();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoTarjeta.setConn(conn);
				daoTarjeta.deleteTarjeta(tarjeta);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoTarjeta.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		//Zona
		
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Zona> darZonas() throws Exception {
			List<Zona> zonas;
			DAOTablaZona daoZonas = new DAOTablaZona();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoZonas.setConn(conn);
				zonas = daoZonas.darZonas();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZonas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return zonas;
		}

		/**
		 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del video a buscar. name != null
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Zona> buscarZonasPorName(String name) throws Exception {
			List<Zona> zonas;
			DAOTablaZona daoZonas = new DAOTablaZona();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoZonas.setConn(conn);
				zonas = daoZonas.buscarZonasPorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZonas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return zonas;
		}
		
			/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addZona(Zona zona) throws Exception {
			DAOTablaZona daoZonas = new DAOTablaZona();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoZonas.setConn(conn);
				daoZonas.addZona(zona);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZonas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addZonas(List<Zona> zonas) throws Exception {
			DAOTablaZona daoZonas = new DAOTablaZona();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoZonas.setConn(conn);
				Iterator<Zona> it = zonas.iterator();
				while(it.hasNext())
				{
					daoZonas.addZona(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoZonas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateZona(Zona zona) throws Exception {
			DAOTablaZona daoZona = new DAOTablaZona();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoZona.setConn(conn);
				daoZona.updateZona(zona);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZona.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteZona(Zona zona) throws Exception {
			DAOTablaZona daoZona = new DAOTablaZona();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoZona.setConn(conn);
				daoZona.deleteVideo(zona);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZona.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		//AdministradorUs
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<AdministradorUs> darAdministradoresUs() throws Exception {
			List<AdministradorUs> administradores;
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				administradores = daoAdministradorUs.darAdministradores();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return administradores;
		}

		/**
		 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del video a buscar. name != null
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<AdministradorUs> buscarAdministradoresUsPorName(String name) throws Exception {
			List<AdministradorUs> administradores;
			DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradoresUs.setConn(conn);
				administradores = daoAdministradoresUs.buscarAdministradorPorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradoresUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return administradores;
		}

		public List<ClienteUs> buscarClientesPedidoFecha(Long id, Date ini, Date fin, String ordenar, int idr) throws Exception {
			List<ClienteUs> clientes;
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			DAOTablaAdministradorUs daoAdmin = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClienteUs.setConn(conn);
				daoAdmin.setConn(conn);
				AdministradorUs admin = daoAdmin.buscarAdministradorPorId(id);
				if(admin==null) throw new Exception("no se encontro el admin");
				clientes = daoClienteUs.buscarClientesPedidoFecha(ini, fin, ordenar, idr);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoAdmin.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return clientes;
		}
		
		public List<ClienteUs> buscarClientesPedidoFechaPedido(Long id, Date ini, Date fin, String ordenar, int idr) throws Exception {
			List<ClienteUs> clientes;
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			DAOTablaAdministradorUs daoAdmin = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClienteUs.setConn(conn);
				daoAdmin.setConn(conn);
				AdministradorUs admin = daoAdmin.buscarAdministradorPorId(id);
				if(admin==null) throw new Exception("no se encontro el admin");
				clientes = daoClienteUs.buscarClientesPedidoFechaPedido(ini, fin, ordenar, idr);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoAdmin.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return clientes;
		}
		/**
		 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
		 * @param name - Id del video a buscar. name != null
		 * @return Video - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public AdministradorUs buscarAdministradorUsPorId(Long id) throws Exception {
			AdministradorUs administrador;
			DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradoresUs.setConn(conn);
				administrador = daoAdministradoresUs.buscarAdministradorPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradoresUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return administrador;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addAdministradorUs(AdministradorUs administrador) throws Exception {
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				daoAdministradorUs.addAdministrador(administrador);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		//revisar que este el cliente
		public void addClienteAdministradorUs(Long id, ClienteUs cliente) throws Exception {
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				daoClienteUs.setConn(conn);
				daoAdministradorUs.buscarAdministradorPorId(id);
				daoClienteUs.addCliente(cliente);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

  

		//revisar que este el cliente
		public void addZonaAdministradorUs(Long id, Zona zona) throws Exception {
			DAOTablaZona daoZona = new DAOTablaZona();
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				daoZona.setConn(conn);
				daoAdministradorUs.buscarAdministradorPorId(id);
				daoZona.addZona(zona);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoZona.cerrarRecursos();
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addAdministradoresUs(List<AdministradorUs> administradores) throws Exception {
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoAdministradorUs.setConn(conn);
				Iterator<AdministradorUs> it = administradores.iterator();
				while(it.hasNext())
				{
					daoAdministradorUs.addAdministrador(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateAdministradorUs(AdministradorUs administradorUs) throws Exception {
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				daoAdministradorUs.updateAdministrador(administradorUs);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteAdministradorUs(AdministradorUs administrador) throws Exception {
			DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAdministradorUs.setConn(conn);
				daoAdministradorUs.deleteAdministrador(administrador);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAdministradorUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		//ClienteUs
		
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<ClienteUs> darClientes() throws Exception {
			List<ClienteUs> clientes;
			DAOTablaClienteUs daoclientesUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoclientesUs.setConn(conn);
				clientes = daoclientesUs.darClientes();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoclientesUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return clientes;
		}

		/**
		 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del video a buscar. name != null
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<ClienteUs> buscarClientesUsPorName(String name) throws Exception {
			List<ClienteUs> clientes;
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClienteUs.setConn(conn);
				clientes = daoClienteUs.buscarClientePorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return clientes;
		}
		
		/**
		 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
		 * @param name - Id del video a buscar. name != null
		 * @return Video - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public ClienteUs buscarClienteUsPorId(Long id) throws Exception {
			ClienteUs cliente;
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClienteUs.setConn(conn);
				cliente = daoClienteUs.buscarClientePorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return cliente;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addClienteUs(ClienteUs cliente) throws Exception {
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClienteUs.setConn(conn);
				daoClienteUs.addCliente(cliente);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		public Entrada addPreferenciaEntradaClienteUs(Long id, Long id2) throws Exception {
			Entrada entrada;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoEntrada.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				entrada = daoEntrada.buscarEntradaPorId(id2);
				daoClientePref.addClienteEntrada(id, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoEntrada.cerrarRecursos();
					daoClientePref.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entrada;
		}

		public Postre addPreferenciaPostreClienteUs(Long id, Long id2) throws Exception {
			Postre postre;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoPostre.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				postre = daoPostre.buscarPostrePorId(id2);
				daoClientePref.addClientePostre(id, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoPostre.cerrarRecursos();
					daoClientePref.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postre;
		}
		
		public Acompaniamiento addPreferenciaAcompaniamientoClienteUs(Long id, Long id2) throws Exception {
			Acompaniamiento acomp;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaAcompaniamiento daoAcomp = new DAOTablaAcompaniamiento();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoAcomp.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				acomp = daoAcomp.buscarAcompaniamientoPorId(id2);
				daoClientePref.addClienteAcomp(id, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoAcomp.cerrarRecursos();
					daoClientePref.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acomp;
		}
		
		public PlatoFuerte addPreferenciaPlatoFuerteClienteUs(Long id, Long id2) throws Exception {
			PlatoFuerte plato;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaPlatoFuerte daoPlato = new DAOTablaPlatoFuerte();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoPlato.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				plato = daoPlato.buscarPlatoFuertePorId(id2);
				daoClientePref.addClientePlato(id, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoPlato.cerrarRecursos();
					daoClientePref.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return plato;
		}
		
		
		public Bebida addPreferenciaBebidaClienteUs(Long id, Long id2) throws Exception {
			Bebida bebida;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaBebida daoBebida = new DAOTablaBebida();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoBebida.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				bebida = daoBebida.buscarBebidaPorId(id2);
				daoClientePref.addClienteBebida(id, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoBebida.cerrarRecursos();
					daoClientePref.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebida;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addClientesUs(List<ClienteUs> clientes) throws Exception {
			DAOTablaClienteUs daoClientesUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoClientesUs.setConn(conn);
				Iterator<ClienteUs> it = clientes.iterator();
				while(it.hasNext())
				{
					daoClientesUs.addCliente(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoClientesUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateClienteUs(ClienteUs cliente) throws Exception {
			DAOTablaClienteUs daoClientes = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientes.setConn(conn);
				daoClientes.updateCliente(cliente);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteClienteUs(ClienteUs cliente) throws Exception {
			DAOTablaClienteUs daoClientes = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientes.setConn(conn);
				daoClientes.deleteCliente(cliente);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		public Entrada deletePreferenciaEntradaClienteUs(long id, long id2) throws Exception {
			Entrada entrada;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoEntrada.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				entrada = daoEntrada.buscarEntradaPorId(id2);
				daoClientePref.deleteClienteEntrada(id, id2);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientePref.cerrarRecursos();
					daoClienteUs.cerrarRecursos();
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entrada;
		}

		public Bebida deletePreferenciaBebidaClienteUs(long id, long id2) throws Exception {
			Bebida bebida;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaBebida daoBebida = new DAOTablaBebida();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoBebida.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				bebida = daoBebida.buscarBebidaPorId(id2);
				daoClientePref.deleteClienteBebida(id, id2);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientePref.cerrarRecursos();
					daoClienteUs.cerrarRecursos();
					daoBebida.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebida;
		}

		public Postre deletePreferenciaPostreClienteUs(long id, long id2) throws Exception {
			Postre postre;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoPostre.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				postre = daoPostre.buscarPostrePorId(id2);
				daoClientePref.deleteClientePostre(id, id2);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientePref.cerrarRecursos();
					daoClienteUs.cerrarRecursos();
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postre;
		}
		
		public Acompaniamiento deletePreferenciaAcompaniamientoClienteUs(long id, long id2) throws Exception {
			Acompaniamiento acomp;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaAcompaniamiento daoAcomp = new DAOTablaAcompaniamiento();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoAcomp.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				acomp = daoAcomp.buscarAcompaniamientoPorId(id2);
				daoClientePref.deleteClienteAcompaniamiento(id, id2);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientePref.cerrarRecursos();
					daoClienteUs.cerrarRecursos();
					daoAcomp.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acomp;
		}

		public PlatoFuerte deletePreferenciaPlatoFuerteClienteUs(long id, long id2) throws Exception {
			PlatoFuerte plato;
			ClienteUs cliente;
			DAOTablaClientePreferencia daoClientePref = new DAOTablaClientePreferencia();
			DAOTablaPlatoFuerte daoPlato = new DAOTablaPlatoFuerte();
			DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoClientePref.setConn(conn);
				daoPlato.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarClientePorId(id);
				plato = daoPlato.buscarPlatoFuertePorId(id2);
				daoClientePref.deleteClientePlatoFuerte(id, id2);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClientePref.cerrarRecursos();
					daoClienteUs.cerrarRecursos();
					daoPlato.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return plato;
		}

		
		//RestauranteUs
		
		/**
		 * Metodo que modela la transaccion que retorna todos los videos de la base de datos.
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<RestauranteUs> darRestaurantesUs() throws Exception {
			List<RestauranteUs> restaurantesUs;
			DAOTablaRestauranteUs daoRestaurantes = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantes.setConn(conn);
				restaurantesUs = daoRestaurantes.darRestaurantesUs();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurantesUs;
		}

		/**
		 * Metodo que modela la transaccion que busca el/los videos en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del video a buscar. name != null
		 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<RestauranteUs> buscarRestauranteUsPorName(String name) throws Exception {
			List<RestauranteUs> restaurantesUs;
			DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantesUs.setConn(conn);
				restaurantesUs = daoRestaurantesUs.buscarRestauranteUsPorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantesUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurantesUs;
		}
		
		/**
		 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
		 * @param name - Id del video a buscar. name != null
		 * @return Video - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public RestauranteUs buscarRestauranteUsPorId(Long id) throws Exception {
			RestauranteUs restauranteUs;
			DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestauranteUs.setConn(conn);
				restauranteUs = daoRestauranteUs.buscarRestauranteUsPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestauranteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restauranteUs;
		}
		
		/**
		 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
		 * <b> post: </b> se ha agregado el video que entra como parametro
		 * @param video - el video a agregar. video != null
		 * @throws Exception - cualquier error que se genere agregando el video
		 */
		public void addRestauranteUs(RestauranteUs restauranteUs) throws Exception {
			DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestauranteUs.setConn(conn);
				daoRestauranteUs.addRestauranteUs(restauranteUs);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestauranteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		public ArrayList<Entrada> addEquivalenciaEntrada(Long id, Long id1,Long id2) throws Exception {
			ArrayList<Entrada> entradas = new ArrayList<>();
			Entrada entrada;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoEntrada.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				entrada = daoEntrada.buscarEntradaPorId(id1);
				entradas.add(entrada);
				entrada = daoEntrada.buscarEntradaPorId(id2);
				entradas.add(entrada);
				daoEquivalencias.addEquivalenciaEntrada(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoEntrada.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entradas;
		}

		public ArrayList<Acompaniamiento> addEquivalenciaAcomp(Long id, Long id1,Long id2) throws Exception {
			ArrayList<Acompaniamiento> acomps = new ArrayList<>();
			Acompaniamiento acomp;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaAcompaniamiento daoAcomp = new DAOTablaAcompaniamiento();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoAcomp.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				acomp = daoAcomp.buscarAcompaniamientoPorId(id1);
				acomps.add(acomp);
				acomp = daoAcomp.buscarAcompaniamientoPorId(id2);
				acomps.add(acomp);
				daoEquivalencias.addEquivalenciaAcomp(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoAcomp.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acomps;
		}

		public ArrayList<Bebida> addEquivalenciaBebida(Long id, Long id1,Long id2) throws Exception {
			ArrayList<Bebida> bebidas = new ArrayList<>();
			Bebida bebida;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaBebida daoBebida = new DAOTablaBebida();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoBebida.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				bebida = daoBebida.buscarBebidaPorId(id1);
				bebidas.add(bebida);
				bebida = daoBebida.buscarBebidaPorId(id2);
				bebidas.add(bebida);
				daoEquivalencias.addEquivalenciaBebida(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoBebida.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebidas;
		}

		public ArrayList<PlatoFuerte> addEquivalenciaPlato(Long id, Long id1,Long id2) throws Exception {
			ArrayList<PlatoFuerte> platos = new ArrayList<>();
			PlatoFuerte plato;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaPlatoFuerte daoPlato = new DAOTablaPlatoFuerte();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoPlato.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				plato = daoPlato.buscarPlatoFuertePorId(id1);
				platos.add(plato);
				plato = daoPlato.buscarPlatoFuertePorId(id2);
				platos.add(plato);
				daoEquivalencias.addEquivalenciaPlato(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoPlato.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return platos;
		}

		public ArrayList<Postre> addEquivalenciaPostre(Long id, Long id1,Long id2) throws Exception {
			ArrayList<Postre> postres = new ArrayList<>();
			Postre postre;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoPostre.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				postre = daoPostre.buscarPostrePorId(id1);
				postres.add(postre);
				postre = daoPostre.buscarPostrePorId(id2);
				postres.add(postre);
				daoEquivalencias.addEquivalenciaPostre(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoPostre.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postres;
		}

		public ArrayList<Ingrediente> addEquivalenciaIng(Long id, Long id1,Long id2) throws Exception {
			ArrayList<Ingrediente> ings = new ArrayList<>();
			Ingrediente ing;
			RestauranteUs restaurante;
			DAOTablaEquivalencias daoEquivalencias = new DAOTablaEquivalencias();
			DAOTablaIngrediente daoIng = new DAOTablaIngrediente();
			DAOTablaRestauranteUs daoClienteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEquivalencias.setConn(conn);
				daoIng.setConn(conn);
				daoClienteUs.setConn(conn);
				daoClienteUs.buscarRestauranteUsPorId(id);
				ing = daoIng.buscarIngredientePorId(id1);
				ings.add(ing);
				ing = daoIng.buscarIngredientePorId(id2);
				ings.add(ing);
				daoEquivalencias.addEquivalenciaIngrediente(id1, id2);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoClienteUs.cerrarRecursos();
					daoIng.cerrarRecursos();
					daoEquivalencias.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return ings;
		}

		
		/**
		 * Metodo que modela la transaccion que agrega los videos que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parametro
		 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void addRestaurantesUs(List<RestauranteUs> restaurantes) throws Exception {
			DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoRestaurantesUs.setConn(conn);
				Iterator<RestauranteUs> it = restaurantes.iterator();
				while(it.hasNext())
				{
					daoRestaurantesUs.addRestauranteUs(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoRestaurantesUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		/**
		 * Metodo que modela la transaccion que actualiza el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el video que entra como parametro
		 * @param video - Video a actualizar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void updateRestauranteUs(RestauranteUs restauranteUs) throws Exception {
			DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantesUs.setConn(conn);
				daoRestaurantesUs.updateRestauranteUs(restauranteUs);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantesUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}

		/**
		 * Metodo que modela la transaccion que elimina el video que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el video que entra como parametro
		 * @param video - Video a eliminar. video != null
		 * @throws Exception - cualquier error que se genera actualizando los videos
		 */
		public void deleteRestauranteUs(RestauranteUs restaurante) throws Exception {
			DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestauranteUs.setConn(conn);
				daoRestauranteUs.deleteRestauranteUs(restaurante);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestauranteUs.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * Metodo que modela la transaccion que retorna todos los restaurantes de la base de datos.
		 * @return ListaRestaurantes - objeto que modela  un arreglo de restaurantes. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Restaurante> darRestaurantes() throws Exception {
			List<Restaurante> restaurantes;
			DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurante.setConn(conn);
				restaurantes = daoRestaurante.darRestaurantes();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurante.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurantes;
		}
		
		
		
		/**
		 * Metodo que modela la transaccion que busca el/los restaurantes en la base de datos con el nombre entra como parametro.
		 * @param name - Nombre del restaurante a buscar. name != null
		 * @return ListaRestaurantes - objeto que modela  un arreglo de restaurantes. este arreglo contiene el resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public List<Restaurante> buscarRestaurantePorName(String name) throws Exception {
			List<Restaurante> restaurantes;
			DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantes.setConn(conn);
				restaurantes = daoRestaurantes.buscarRestauratePorName(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurantes;
		}
		
		
		
		
		/**
		 * Metodo que modela la transaccion que busca el restaurante en la base de datos con el id que entra como parametro.
		 * @param name - Id del restaurante a buscar. name != null
		 * @return restaurante - Resultado de la busqueda
		 * @throws Exception -  cualquier error que se genere durante la transaccion
		 */
		public Restaurante buscarRestaurantePorId(Long id) throws Exception {
			Restaurante restaurante;
			DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantes.setConn(conn);
				restaurante = daoRestaurantes.buscarRestaurantePorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurante;
		}
		
		
		
		
		/**
		 * Metodo que modela la transaccion que agrega un solo Restaurante a la base de datos.
		 * <b> post: </b> se ha agregado el Restaurante que entra como parametro
		 * @param Restaurante - el Restaurante a agregar. Restaurante != null
		 * @throws Exception - cualquier error que se genere agregando el Restaurante
		 */
		public void addRestaurante(Restaurante restaurante) throws Exception {
			DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurantes.setConn(conn);
				daoRestaurantes.addRestaurante(restaurante);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		/**
		 * Metodo que modela la transaccion que agrega los Restaurantes que entran como parametro a la base de datos.
		 * <b> post: </b> se han agregado los Restaurantes que entran como parametro
		 * @param Restaurante - objeto que modela una lista de Restaurantes y se estos se pretenden agregar. Restaurantes != null
		 * @throws Exception - cualquier error que se genera agregando los videos
		 */
		public void  addRestaurantes(List<Restaurante> restaurantes) throws Exception {
			DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoRestaurantes.setConn(conn);
				Iterator<Restaurante> it = restaurantes.iterator();
				while(it.hasNext())
				{
					daoRestaurantes.addRestaurante(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoRestaurantes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		/**
		 * Metodo que modela la transaccion que actualiza el Restaurante que entra como parametro a la base de datos.
		 * <b> post: </b> se ha actualizado el Restaurante que entra como parametro
		 * @param Restaurante - Restaurante a actualizar. Restaurante != null
		 * @throws Exception - cualquier error que se genera actualizando
		 */
		public void updateRestaurante(Restaurante restaurante) throws Exception {
			DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurante.setConn(conn);
				daoRestaurante.updateRestaurante(restaurante);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurante.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		/**
		 * Metodo que modela la transaccion que elimina el Restaurante que entra como parametro a la base de datos.
		 * <b> post: </b> se ha eliminado el Restaurante que entra como parametro
		 * @param Restaurante - Restaurante a eliminar. Restaurante != null
		 * @throws Exception - cualquier error que se genera.
		 */
		public void deleteRestaurante(Restaurante restaurante) throws Exception {
			DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoRestaurante.setConn(conn);
				daoRestaurante.deleteRestaurante(restaurante);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoRestaurante.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		public List<Bebida> darBebidas() throws Exception {
			List<Bebida> bebidas;
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				bebidas = dao.darBebidas();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebidas;
		}
		
		
		
		
		public List<Bebida> buscarBebidaPorName(String name) throws Exception {
			List<Bebida> bebidas;
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				bebidas = dao.buscarBebidasPorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebidas;
		}
		
		
		
		
		
		
		public Bebida buscarBebidaPorId(Long id) throws Exception {
			Bebida bebida;
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				bebida = dao.buscarBebidaPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return bebida;
		}
		
		
		
		
		
		
		
		
		
		
		
		public void addBebida(Bebida bebida) throws Exception {
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				dao.addBebida(bebida);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public void addBebidas(List<Bebida> bebidas) throws Exception {
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				dao.setConn(conn);
				Iterator<Bebida> it = bebidas.iterator();
				while(it.hasNext())
				{
					dao.addBebida(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void updateBebida(Bebida bebida) throws Exception {
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				dao.updateBebida(bebida);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deleteBebida(Bebida bebida) throws Exception {
			DAOTablaBebida dao = new DAOTablaBebida();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				dao.setConn(conn);
				dao.deleteBebida(bebida);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					dao.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
	
		
		
		
		
		
		
		
		public List<Acompaniamiento> darAcompaniamientos() throws Exception {
			List<Acompaniamiento> acompaniamientos;
			DAOTablaAcompaniamiento daoAcompaniamiento= new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				acompaniamientos = daoAcompaniamiento.darAcompaniamientos();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acompaniamientos;
		}
		
		
		
	
		
		
		public List<Acompaniamiento> buscarAcompaniamientoPorName(String name) throws Exception {
			List<Acompaniamiento> acompaniamientos;
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				acompaniamientos = daoAcompaniamiento.buscarAcompaniamientosPorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acompaniamientos;
		}
	
		
		
		
		public Acompaniamiento buscarAcompaniamientoPorId(Long id) throws Exception {
			Acompaniamiento acompaniamiento;
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				acompaniamiento = daoAcompaniamiento.buscarAcompaniamientoPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return acompaniamiento;
		}
		
		
		
		
		
		
		
		
		
		public void addAcompaniamiento(Acompaniamiento acompaniamiento) throws Exception {
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				daoAcompaniamiento.addAcompaniamiento(acompaniamiento);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		public void addAcompaniamientos(List<Acompaniamiento> acompaniamientos) throws Exception {
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoAcompaniamiento.setConn(conn);
				Iterator<Acompaniamiento> it = acompaniamientos.iterator();
				while(it.hasNext())
				{
					daoAcompaniamiento.addAcompaniamiento(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		
		public void updateAcompaniamiento(Acompaniamiento acompaniamiento) throws Exception {
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				daoAcompaniamiento.updateAcompaniamiento(acompaniamiento);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deleteAcompaniamiento(Acompaniamiento acompaniamiento) throws Exception {
			DAOTablaAcompaniamiento daoAcompaniamiento = new DAOTablaAcompaniamiento();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoAcompaniamiento.setConn(conn);
				daoAcompaniamiento.deleteAcompaniamiento(acompaniamiento);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoAcompaniamiento.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public List<Entrada> darEntradas() throws Exception {
			List<Entrada> entradas;
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				entradas = daoEntrada.darEntradas();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entradas;
		}
		
		
		
		
		public List<Entrada> buscarEntradaPorName(String name) throws Exception {
			List<Entrada> entradas;
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				entradas = daoEntrada.buscarEntradasPorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entradas;
		}
		
		
		
		
		
		
		public Entrada buscarEntradaPorId(Long id) throws Exception {
			Entrada entrada;
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				entrada = daoEntrada.buscarEntradaPorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return entrada;
		}
		
		
		
		
		
		
		
		
		
		
		
		public void addEntrada(Entrada entrada) throws Exception {
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				daoEntrada.addEntrada(entrada);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public void addEntradas(List<Entrada> entradas) throws Exception {
			DAOTablaEntrada daoEntradas = new DAOTablaEntrada();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoEntradas.setConn(conn);
				Iterator<Entrada> it = entradas.iterator();
				while(it.hasNext())
				{
					daoEntradas.addEntrada(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoEntradas.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void updateEntrada(Entrada entrada) throws Exception {
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				daoEntrada.updateEntrada(entrada);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deleteEntrada(Entrada entrada) throws Exception {
			DAOTablaEntrada daoEntrada = new DAOTablaEntrada();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoEntrada.setConn(conn);
				daoEntrada.deleteEntrada(entrada);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoEntrada.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		public List<PlatoFuerte> darPlatoFuertes() throws Exception {
			List<PlatoFuerte> platoFuertes;
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				platoFuertes = daoPlatoFuerte.darPlatosFuertes();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return platoFuertes;
		}
		
		
		
		
		public List<PlatoFuerte> buscarplatoFuertesPorName(String name) throws Exception {
			List<PlatoFuerte> platoFuertes;
			DAOTablaPlatoFuerte daoPlatoFuertes = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuertes.setConn(conn);
				platoFuertes = daoPlatoFuertes.buscarPlatoFuertePorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuertes.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return platoFuertes;
		}
		
		
		
		
		
		
		public PlatoFuerte buscarPlatoFuerteId(Long id) throws Exception {
			PlatoFuerte platoFuerte;
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				platoFuerte = daoPlatoFuerte.buscarPlatoFuertePorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return platoFuerte;
		}
		
		
		
		
		
		
		
		
		
		
		
		public void addPlatoFuerte(PlatoFuerte platoFuerte) throws Exception {
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				daoPlatoFuerte.addPlatoFuerte(platoFuerte);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public void addPlatoFuertes(List<PlatoFuerte> platoFuertes) throws Exception {
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoPlatoFuerte.setConn(conn);
				Iterator<PlatoFuerte> it = platoFuertes.iterator();
				while(it.hasNext())
				{
					daoPlatoFuerte.addPlatoFuerte(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void updatePlatoFuerte(PlatoFuerte platoFuerte) throws Exception {
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				daoPlatoFuerte.updatePlatoFuerte(platoFuerte);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deletePlatoFuerte(PlatoFuerte platoFuerte) throws Exception {
			DAOTablaPlatoFuerte daoPlatoFuerte = new DAOTablaPlatoFuerte();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPlatoFuerte.setConn(conn);
				daoPlatoFuerte.deletePlatoFuerte(platoFuerte);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPlatoFuerte.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public List<Postre> darPostres() throws Exception {
			List<Postre> postres;
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				postres = daoPostre.darPostres();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postres;
		}
		
		
		
		
		public List<Postre> buscarPostrePorName(String name) throws Exception {
			List<Postre> postres;
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				postres = daoPostre.buscarPostresPorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postres;
		}
		
		
		
		
		
		
		public Postre buscarPostreId(Long id) throws Exception {
			Postre postre;
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				postre = daoPostre.buscarPostrePorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return postre;
		}
		
		
		
		
		
		
		
		
		
		
		
		public void addPostre(Postre postre) throws Exception {
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				daoPostre.addPostre(postre);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public void addPostres(List<Postre> postres) throws Exception {
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoPostre.setConn(conn);
				Iterator<Postre> it = postres.iterator();
				while(it.hasNext())
				{
					daoPostre.addPostre(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void updatePostre(Postre postre) throws Exception {
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				daoPostre.updatePostre(postre);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deletePostre(Postre postre) throws Exception {
			DAOTablaPostre daoPostre = new DAOTablaPostre();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoPostre.setConn(conn);
				daoPostre.deletePostre(postre);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPostre.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public List<Ingrediente> darIngredientes() throws Exception {
			List<Ingrediente> ingredientes;
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				ingredientes = daoIngrediente.darIngredientes();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return ingredientes;
		}
		
		
		
		
		public List<Ingrediente> buscarIngredientePorName(String name) throws Exception {
			List<Ingrediente> ingredientes;
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				ingredientes = daoIngrediente.buscarIngredientePorNombre(name);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return ingredientes;
		}
		
		
		
		
		
		
		public Ingrediente buscarIngredienteId(Long id) throws Exception {
			Ingrediente ingrediente;
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				ingrediente = daoIngrediente.buscarIngredientePorId(id);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return ingrediente;
		}
		
		
		
		
		
		
		
		
		
		
		
		public void addIngrediente(Ingrediente ingrediente) throws Exception {
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				daoIngrediente.addIngrediente(ingrediente);
				conn.commit();

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		public void addIngredientes(List<Ingrediente> ingredientes) throws Exception {
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion - ACID Example
				this.conn = darConexion();
				conn.setAutoCommit(false);
				daoIngrediente.setConn(conn);
				Iterator<Ingrediente> it = ingredientes.iterator();
				while(it.hasNext())
				{
					daoIngrediente.addIngrediente(it.next());
				}
				
				conn.commit();
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				conn.rollback();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void updateIngrediente(Ingrediente ingrediente) throws Exception {
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				daoIngrediente.updateIngrediente(ingrediente);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		public void deleteIngrediente(Ingrediente ingrediente) throws Exception {
			DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoIngrediente.setConn(conn);
				daoIngrediente.deleteIngrediente(ingrediente);

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoIngrediente.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	///////////
	/// Menu
	////////////
	public Menu buscarMenuPorIdRestaurante(Long id) throws Exception {
		Menu menus = null;
		DAOTablaMenu daoMenu = new DAOTablaMenu();

		try {
			////// transaccion
			this.conn = darConexion();
			daoMenu.setConn(conn);

			ArrayList<Acompaniamiento> acompaniamientos = daoMenu.darAcompaniamientosRestauranteId(id);
			ArrayList<Bebida> bebidas = daoMenu.darBebidasRestauranteId(id);
			ArrayList<Entrada> entradas = daoMenu.darEntradasRestauranteId(id);
			ArrayList<PlatoFuerte> platoFuertes = daoMenu.darPlatoFuertesRestauranteId(id);
			ArrayList<Postre> postres = daoMenu.darPostresRestauranteId(id);

			menus = new Menu(id, acompaniamientos, bebidas, entradas, platoFuertes, postres);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}
		
		
		
		
		
		
		
	/////////////
	// RF10
	/////////////

	public void updatePostreCantidad(Postre postre) throws Exception {
		DAOTablaPostre daoPostre = new DAOTablaPostre();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPostre.setConn(conn);

			Postre postreActual = daoPostre.buscarPostrePorId(postre.getId());
			postreActual.setCantidad((int) (postreActual.getCantidad() - postre.getCantidad()));
			postreActual.setNumeroVendidos(postreActual.getNumeroVendidos() + postre.getCantidad());
			daoPostre.updatePostre(postreActual);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPostre.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
	//RF10
	public void updateAcompaniemientoCantidad(Acompaniamiento acompaniamiento) throws Exception {
		DAOTablaAcompaniamiento daoAcomp = new DAOTablaAcompaniamiento();
		try {
			////// transaccion
			this.conn = darConexion();
			daoAcomp.setConn(conn);

			Acompaniamiento AcompanimientoActual = daoAcomp.buscarAcompaniamientoPorId(acompaniamiento.getId());
			AcompanimientoActual.setCantidad((int) (AcompanimientoActual.getCantidad() - acompaniamiento.getCantidad()));
			AcompanimientoActual.setNumeroVendidos(AcompanimientoActual.getNumeroVendidos() + acompaniamiento.getCantidad());
			daoAcomp.updateAcompaniamiento(AcompanimientoActual);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAcomp.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
		
	//RF10
	public void updateEntradaCantidad(Entrada entrada) throws Exception {
		DAOTablaEntrada daoE = new DAOTablaEntrada();
		try {
			////// transaccion
			this.conn = darConexion();
			daoE.setConn(conn);

			Entrada entradaActual = daoE.buscarEntradaPorId(entrada.getId());
			entradaActual.setCantidad((int) (entradaActual.getCantidad() - entrada.getCantidad()));
			entradaActual.setNumeroVendidos(entradaActual.getNumeroVendidos() + entrada.getCantidad());
			daoE.updateEntrada(entradaActual);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoE.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
		
		
	//RF10
	public void updateBebidaCantidad(Bebida bebida) throws Exception {
		DAOTablaBebida daoB = new DAOTablaBebida();
		try {
			////// transaccion
			this.conn = darConexion();
			daoB.setConn(conn);

			Bebida bebidaActual = daoB.buscarBebidaPorId(bebida.getId());
			bebidaActual.setCantidad((int) (bebidaActual.getCantidad() - bebida.getCantidad()));
			bebidaActual.setNumeroVendidos(bebidaActual.getNumeroVendidos() + bebida.getCantidad());
			daoB.updateBebida(bebidaActual);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoB.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
		
		
	// RF10
	public void updateplatoFuerteCantidad(PlatoFuerte platoFuerte) throws Exception {
		DAOTablaPlatoFuerte daoP = new DAOTablaPlatoFuerte();
		try {
			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			PlatoFuerte PlatoFuerteActual = daoP.buscarPlatoFuertePorId(platoFuerte.getId());
			PlatoFuerteActual.setCantidad((int) (PlatoFuerteActual.getCantidad() - platoFuerte.getCantidad()));
			PlatoFuerteActual.setNumeroVendidos(PlatoFuerteActual.getNumeroVendidos() + platoFuerte.getCantidad());
			daoP.updatePlatoFuerte(PlatoFuerteActual);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
	///////////
	// RF16
	////////////
	public void updatePedidosClientes(ArrayList<Pedido> pedidos) throws Exception {
		DAOTablaPedido daoP = new DAOTablaPedido();

		try {
			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Iterator<Pedido> iterPedidos = pedidos.iterator();
			while (iterPedidos.hasNext()) {

				Pedido pedidoActual = iterPedidos.next();

				Long id = pedidoActual.getId();
				Long idUsuario = pedidoActual.getIdUsuario();
				Integer mesa = pedidoActual.getMesa();
				Double costo = pedidoActual.getCosto();
				Long idEntrada = pedidoActual.getIdEntrada();
				Long idAcompani = pedidoActual.getIdAcomp();
				Long idPlato = pedidoActual.getIdPlato();
				Long idBebida = pedidoActual.getIdBebida();
				Long idPostre = pedidoActual.getIdPostre();
				Date fecha = pedidoActual.getFecha();
				String estado = pedidoActual.getEstado();
				Long idRestaurante = pedidoActual.getIdRestaurante();

				// Actualizo las cantidades de los productos y agrego el pedido
				// costo Final
				Double entradaCos = buscarEntradaPorId(idEntrada).getPrecioProd();
				Double acompCos = buscarAcompaniamientoPorId(idAcompani).getPrecioProd();
				Double platoCos = buscarPlatoFuerteId(idPlato).getPrecioProd();
				Double bebCos = buscarBebidaPorId(idBebida).getPrecioProd();
				Double postreCos = buscarPostreId(idPostre).getPrecioProd();
				Double costoFinal = entradaCos + acompCos + platoCos + bebCos + postreCos;

				Pedido pedidoF = new Pedido(id, idUsuario, mesa, costoFinal, idEntrada, idAcompani, idPlato, idBebida,
						idPostre, fecha, "SERVIDO", idRestaurante);
				addPedido(pedidoF);

				Entrada entradaAct = new Entrada(idEntrada, null, 1, null, null, null, null, null, null, null, null,
						null, null, null, null);
				updateEntradaCantidad(entradaAct);

				Acompaniamiento acompAct = new Acompaniamiento(idAcompani, null, 1, null, null, null, null, null, null,
						null, null, null, null, null, null);
				updateAcompaniemientoCantidad(acompAct);

				PlatoFuerte platoFAct = new PlatoFuerte(idPlato, null, 1, null, null, null, null, null, null, null,
						null, null, null, null, null);
				updateplatoFuerteCantidad(platoFAct);

				Bebida bebidaAct = new Bebida(idBebida, null, 1, null, null, null, null, null, null, null, null, null,
						null, null, null);
				updateBebidaCantidad(bebidaAct);

				Postre postreAct = new Postre(idPostre, null, 1, null, null, null, null, null, null, null, null, null,
						null, null, null);
				updatePostreCantidad(postreAct);

			}
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
	//////////
	// RF17
	//////////
	public void cancelarPedidoPostre(CancelarPedido cancelarPedido) throws Exception {

		DAOTablaPedido daoP = new DAOTablaPedido();

		try {

			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Long idCliente = cancelarPedido.getIdCliente();
			Long idPedido = cancelarPedido.getIdPedido();
			Long idProducto = cancelarPedido.getIdProducto();

			Pedido pedidoAct = daoP.buscarPedidoPorId(idPedido);
			pedidoAct.setIdPostre(0);
			recalcularPedido(pedidoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
		
		
	// RF17
	public void cancelarPedidoAcompaniamiento(CancelarPedido cancelarPedido) throws Exception {

		DAOTablaPedido daoP = new DAOTablaPedido();

		try {

			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Long idCliente = cancelarPedido.getIdCliente();
			Long idPedido = cancelarPedido.getIdPedido();
			Long idProducto = cancelarPedido.getIdProducto();

			Pedido pedidoAct = daoP.buscarPedidoPorId(idPedido);
			pedidoAct.setIdAcomp(0);
			recalcularPedido(pedidoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
	// RF17
	public void cancelarPedidoEntrada(CancelarPedido cancelarPedido) throws Exception {

		DAOTablaPedido daoP = new DAOTablaPedido();

		try {

			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Long idCliente = cancelarPedido.getIdCliente();
			Long idPedido = cancelarPedido.getIdPedido();
			Long idProducto = cancelarPedido.getIdProducto();

			Pedido pedidoAct = daoP.buscarPedidoPorId(idPedido);
			pedidoAct.setIdEntrada(0);
			recalcularPedido(pedidoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		



	// RF17
	public void cancelarPedidoBebida(CancelarPedido cancelarPedido) throws Exception {

		DAOTablaPedido daoP = new DAOTablaPedido();

		try {

			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Long idCliente = cancelarPedido.getIdCliente();
			Long idPedido = cancelarPedido.getIdPedido();
			Long idProducto = cancelarPedido.getIdProducto();

			Pedido pedidoAct = daoP.buscarPedidoPorId(idPedido);
			pedidoAct.setIdBebida(0);
			recalcularPedido(pedidoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	// RF17
	public void cancelarPedidoPlatoFuerte(CancelarPedido cancelarPedido) throws Exception {

		DAOTablaPedido daoP = new DAOTablaPedido();

		try {

			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Long idCliente = cancelarPedido.getIdCliente();
			Long idPedido = cancelarPedido.getIdPedido();
			Long idProducto = cancelarPedido.getIdProducto();

			Pedido pedidoAct = daoP.buscarPedidoPorId(idPedido);
			pedidoAct.setIdPlato(0);
			recalcularPedido(pedidoAct);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
		
		
		
	// Auxiliar RF17
	private void recalcularPedido(Pedido pedido) throws Exception {
		DAOTablaPedido daoP = new DAOTablaPedido();

		try {
			////// transaccion
			this.conn = darConexion();
			daoP.setConn(conn);

			Pedido pedidoActual = pedido;

			Long id = pedidoActual.getId();
			Long idUsuario = pedidoActual.getIdUsuario();
			Integer mesa = pedidoActual.getMesa();
			Double costo = pedidoActual.getCosto();
			Long idEntrada = pedidoActual.getIdEntrada();
			Long idAcompani = pedidoActual.getIdAcomp();
			Long idPlato = pedidoActual.getIdPlato();
			Long idBebida = pedidoActual.getIdBebida();
			Long idPostre = pedidoActual.getIdPostre();
			Date fecha = pedidoActual.getFecha();
			String estado = pedidoActual.getEstado();
			Long idRestaurante = pedidoActual.getIdRestaurante();

			// Actualizo las cantidades de los productos y agrego el pedido
			// costo Final

			Double entradaCos = (double) 0;
			Double acompCos = (double) 0;
			Double platoCos = (double) 0;
			Double bebCos = (double) 0;
			Double postreCos = (double) 0;

			if (idEntrada != 0 && idEntrada != null)
				entradaCos = buscarEntradaPorId(idEntrada).getPrecioProd();
			if (idAcompani != 0 && idAcompani != null)
				acompCos = buscarAcompaniamientoPorId(idAcompani).getPrecioProd();
			if (idPlato != 0 && idPlato != null)
				platoCos = buscarPlatoFuerteId(idPlato).getPrecioProd();
			if (idBebida != 0 && idBebida != null)
				bebCos = buscarBebidaPorId(idBebida).getPrecioProd();
			if (idPostre != 0 && idPostre != null)
				postreCos = buscarPostreId(idPostre).getPrecioProd();

			Double costoFinal = entradaCos + acompCos + platoCos + bebCos + postreCos;

			Pedido pedidoF = new Pedido(id, idUsuario, mesa, costoFinal, idEntrada, idAcompani, idPlato, idBebida,
					idPostre, fecha, "PENDIENTE", idRestaurante);
			updatePedido(pedidoF);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoP.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
		
				
				
				
				
				
				
	//RF17	
	public void deleteVariosPedidos(ArrayList<CancelarPedido> cancelarPedidos) throws Exception {
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try {
			////// transaccion
			this.conn = darConexion();
			daoPedidos.setConn(conn);

			Iterator<CancelarPedido> iterCancelPed = cancelarPedidos.iterator();
			while (iterCancelPed.hasNext()) {
				Pedido pedidoAct = buscarPedidoPorId(iterCancelPed.next().getIdPedido());
				daoPedidos.deletePedido(pedidoAct);
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
				
	
	
	
	
	////////////
	//RFC8
	////////////
	public ConsultaPedidos consultarReportePedidos(Long idRestaurante) throws Exception{
	
		ConsultaPedidos consulta = null;
		
		DAOConsultaPedidos daoC = new DAOConsultaPedidos();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoC.setConn(conn);
			
			Integer numpedidos = daoC.numeroDePedidos(idRestaurante);
			Double ganaciasGen = daoC.gananciasGenerdas(idRestaurante);
			
			ArrayList<ConsultaPedidoProducto> productos = new ArrayList<>();
			
			
			ArrayList<ConsultaPedidosAux> idsEntradas = daoC.idsEntradas(idRestaurante);
			ArrayList<ConsultaPedidosAux> idsAcomps = daoC.idsAcomps(idRestaurante);
			ArrayList<ConsultaPedidosAux> idsbebidas = daoC.idsbebidas(idRestaurante);
			ArrayList<ConsultaPedidosAux> idsPlatos = daoC.idsPlatos(idRestaurante);
			ArrayList<ConsultaPedidosAux> idsPostres = daoC.idsPostres(idRestaurante);
			
			Iterator<ConsultaPedidosAux> iterIdsEntradas = idsEntradas.iterator();
			while(iterIdsEntradas.hasNext()) {
				
				ConsultaPedidosAux PedAct = iterIdsEntradas.next();
				
				String tipoAlim = PedAct.getTipoProducto();
				Long idProducto = PedAct.getIdProducto();
				Integer cantidad = PedAct.getCantidad();
				
				Double valor = buscarEntradaPorId(idProducto).getPrecioProd();
				Double gananciaGen = valor * cantidad;
				
				ConsultaPedidoProducto consProF = new ConsultaPedidoProducto(tipoAlim, idProducto, cantidad, valor, gananciaGen);
				productos.add(consProF);		
			}

			
			Iterator<ConsultaPedidosAux> iteridsAcomps = idsAcomps.iterator();
			while(iteridsAcomps.hasNext()) {
				
				ConsultaPedidosAux PedAct = iteridsAcomps.next();
				
				String tipoAlim = PedAct.getTipoProducto();
				Long idProducto = PedAct.getIdProducto();
				Integer cantidad = PedAct.getCantidad();
				
				Double valor = buscarAcompaniamientoPorId(idProducto).getPrecioProd();
				Double gananciaGen = valor * cantidad;
				
				ConsultaPedidoProducto consProF = new ConsultaPedidoProducto(tipoAlim, idProducto, cantidad, valor, gananciaGen);
				productos.add(consProF);		
			}
			
			
			Iterator<ConsultaPedidosAux> iteridsbebidas = idsbebidas.iterator();
			while(iteridsbebidas.hasNext()) {
				
				ConsultaPedidosAux PedAct = iteridsbebidas.next();
				
				String tipoAlim = PedAct.getTipoProducto();
				Long idProducto = PedAct.getIdProducto();
				Integer cantidad = PedAct.getCantidad();
				
				Double valor = buscarBebidaPorId(idProducto).getPrecioProd();
				Double gananciaGen = valor * cantidad;
				
				ConsultaPedidoProducto consProF = new ConsultaPedidoProducto(tipoAlim, idProducto, cantidad, valor, gananciaGen);
				productos.add(consProF);		
			}
			
			
			
			Iterator<ConsultaPedidosAux> iteridsPlatos = idsPlatos.iterator();
			while(iteridsPlatos.hasNext()) {
				
				ConsultaPedidosAux PedAct = iteridsPlatos.next();
				
				String tipoAlim = PedAct.getTipoProducto();
				Long idProducto = PedAct.getIdProducto();
				Integer cantidad = PedAct.getCantidad();
				
				Double valor = buscarPlatoFuerteId(idProducto).getPrecioProd();
				Double gananciaGen = valor * cantidad;
				
				ConsultaPedidoProducto consProF = new ConsultaPedidoProducto(tipoAlim, idProducto, cantidad, valor, gananciaGen);
				productos.add(consProF);		
			}
			
			
			Iterator<ConsultaPedidosAux> iteridsPostres = idsPostres.iterator();
			while(iteridsPostres.hasNext()) {
				
				ConsultaPedidosAux PedAct = iteridsPostres.next();
				
				String tipoAlim = PedAct.getTipoProducto();
				Long idProducto = PedAct.getIdProducto();
				Integer cantidad = PedAct.getCantidad();
				
				Double valor = buscarPostreId(idProducto).getPrecioProd();
				Double gananciaGen = valor * cantidad;
				
				ConsultaPedidoProducto consProF = new ConsultaPedidoProducto(tipoAlim, idProducto, cantidad, valor, gananciaGen);
				productos.add(consProF);		
			}

			
			consulta = new ConsultaPedidos(idRestaurante, numpedidos, ganaciasGen, productos);
			
			
			
//			return consulta;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoC.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consulta;	
	}
				
				
	
	
	
	
	
	////////////
	//RFC12
	////////////
	public ConsultaBuenosClientes consultaBuenosClientesReq(long idAdmin) throws Exception{
		
		ConsultaBuenosClientes cbc = null;
		
		//valido el administrador
		AdministradorUs admin = buscarAdministradorUsPorId(idAdmin);
		if(admin == null) throw new Exception("No tiene permisos para acceder a estos recursos");

		DaoConsultaBuenosClientes daoC = new DaoConsultaBuenosClientes();
		
		try 
		{				
			//////transaccion
			this.conn = darConexion();
			daoC.setConn(conn);
			
			ArrayList<ClienteUSAux2> cConsumoSemanal = daoC.ClientesConsumoSemanal();
			ArrayList<ClienteUs> cSinConsumo = daoC.clientesSinConsumo();
			ArrayList<ClienteUsAux> cPremium = daoC.clientesPremium();
			
			cbc = new ConsultaBuenosClientes(cConsumoSemanal, cPremium, cSinConsumo);
	
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoC.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cbc;	
	}
				
				
				
	
	
////////////
//RFC11
////////////
public ArrayList<ConsultaFuncionamiento> consultaFuncionamientoArray(Long idAdministrador) throws Exception{

	ArrayList<ConsultaFuncionamiento> cfArray = new ArrayList<>();
	
	//valido el administrador
			AdministradorUs admin = buscarAdministradorUsPorId(idAdministrador);
			if(admin == null) throw new Exception("No tiene permisos para acceder a estos recursos");
				
			DAOConsultaFuncionamiento daoC = new DAOConsultaFuncionamiento();
	
	try 
	{		
		//////transaccion
		this.conn = darConexion();
		daoC.setConn(conn);
		
		
		
		//extraigo todas las fechas disponibles en las tablas
		ArrayList<Date> fechasDisponibles = daoC.fechasDisponibles();
		
		Iterator iterDates = fechasDisponibles.iterator();
		
//		while(iterDates.hasNext()) 
		for (int i = 0; i < 20; i++)
		{
			
			
			Date dateActual = (Date) iterDates.next();
			
			
			RestauranteAux restauranteMasvisitado = daoC.restauranteMasVisitado(dateActual);
			RestauranteAux restauranteMenosVisi =  daoC.restauranteMenosVisitado(dateActual);
			
			String dia = daoC.diaActual;
			
			
			//alimento Mas consumido
			AbstractAlimento aliMasCons = null;
			
			VOComparacionAli aliMasConsu = daoC.alimentoMasConsumido(dateActual);
			if(aliMasConsu.getTipo().equalsIgnoreCase("entrada")) aliMasCons = buscarEntradaPorId((long) aliMasConsu.getId());
			else if(aliMasConsu.getTipo().equalsIgnoreCase("acompaniamiento")) aliMasCons = buscarAcompaniamientoPorId((long) aliMasConsu.getId());
			else if(aliMasConsu.getTipo().equalsIgnoreCase("bebida")) aliMasCons = buscarBebidaPorId((long) aliMasConsu.getId());
			else if(aliMasConsu.getTipo().equalsIgnoreCase("platoFuerte")) aliMasCons = buscarPlatoFuerteId((long) aliMasConsu.getId());
			else aliMasCons = buscarPostreId((long) aliMasConsu.getId());
			
			
			//alimento menos consumido
			AbstractAlimento alimentoMenosCons = null;
			
			VOComparacionAli alimenos = daoC.alimentoMenosCosumido(dateActual);
					if(alimenos.getTipo().equalsIgnoreCase("entrada")) alimentoMenosCons = buscarEntradaPorId((long) alimenos.getId());
					else if(alimenos.getTipo().equalsIgnoreCase("acompaniamiento")) alimentoMenosCons = buscarAcompaniamientoPorId((long) alimenos.getId());
					else if(alimenos.getTipo().equalsIgnoreCase("bebida")) alimentoMenosCons = buscarBebidaPorId((long) alimenos.getId());
					else if(alimenos.getTipo().equalsIgnoreCase("platoFuerte")) alimentoMenosCons = buscarPlatoFuerteId((long) alimenos.getId());
					else alimentoMenosCons = buscarPostreId((long) alimenos.getId());
			

			ConsultaFuncionamiento consultActual = new ConsultaFuncionamiento(aliMasCons, alimentoMenosCons, restauranteMasvisitado, restauranteMenosVisi, dateActual, dia);
			cfArray.add(consultActual);
			
			
			
		}
		
		
		
	} catch (SQLException e) {
		System.err.println("SQLException:" + e.getMessage());
		e.printStackTrace();
		throw e;
	} catch (Exception e) {
		System.err.println("GeneralException:" + e.getMessage());
		e.printStackTrace();
		throw e;
	} finally {
		try {
			daoC.cerrarRecursos();
			if(this.conn!=null)
				this.conn.close();
		} catch (SQLException exception) {
			System.err.println("SQLException closing resources:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		}
	}
	return cfArray;	
}
		
		
		
		
}
