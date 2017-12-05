package rest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Pedido;
import vos.Postre;

/**
 * 
 * @author angeloMarcetty
 *
 */
@Path("clientesPedidos")
public class ClientesPedidoService {	
	
	@Context
	private ServletContext context;
	
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
		
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePedidosServidos(ArrayList<Pedido> pedidos) throws Exception {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());
		
		//valido el cliente y el restaurante
		Iterator<Pedido> iterPedidos = pedidos.iterator();
		while(iterPedidos.hasNext()) {
			
			Pedido pedidoActual = iterPedidos.next();
			
			if(tm.buscarRestaurantePorId(pedidoActual.getIdRestaurante()) == null)  throw new Exception("No  existe el restaurante con el id Dado");
			if(tm.buscarClienteUsPorId(pedidoActual.getIdUsuario()) == null) throw new Exception("No  existe el Cliente con el id Dado");
		}
		
		ArrayList<Pedido> pedidosOK = new ArrayList<>();

		
		try 
		{
			tm.updatePedidosClientes(pedidos);
			
			
			Iterator<Pedido> iterPedidos2 = pedidos.iterator();
			while(iterPedidos2.hasNext()) {
				Pedido pedidoActual2 = iterPedidos2.next();
				Pedido pedidoFinal = tm.buscarPedidoPorId(pedidoActual2.getId());
				pedidosOK.add(pedidoFinal);
			}
			
			
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(pedidosOK).build();
		
	}
	
	
	
	
	

}
