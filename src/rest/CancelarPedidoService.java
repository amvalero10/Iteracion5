package rest;
/**
 * 
 * @author angeloMarcetty
 *
 */

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Acompaniamiento;
import vos.CancelarPedido;
import vos.Pedido;
import vos.Postre;

@Path("clientesUs/{idclientesUs: \\d+}/cancelar")
public class CancelarPedidoService {
	
	@Context
	private ServletContext context;
	
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	
	@DELETE
	@Path("/producto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProducto(CancelarPedido cancelarPedido,@PathParam("idclientesUs") Long idclientesUs) throws Exception {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());	
		
		Pedido pedF = null;

		
		if (tm.buscarClienteUsPorId(idclientesUs) != null) //valido que el cliente exista
		{
		try {
			
			String tipoAli = cancelarPedido.getTipoProducto();
			
			if(tipoAli.equalsIgnoreCase("acompaniamiento"))
			{
				tm.cancelarPedidoAcompaniamiento(cancelarPedido);
			}
			else if(tipoAli.equalsIgnoreCase("entrada"))
			{
				tm.cancelarPedidoEntrada(cancelarPedido);
			}
			else if(tipoAli.equalsIgnoreCase("bebida"))
			{
				tm.cancelarPedidoBebida(cancelarPedido);
			}
			else if(tipoAli.equalsIgnoreCase("platoFuerte"))
			{
				tm.cancelarPedidoPlatoFuerte(cancelarPedido);
			}
			else if(tipoAli.equalsIgnoreCase("postre"))
			{
				tm.cancelarPedidoPostre(cancelarPedido);
			}
			
			pedF = tm.buscarPedidoPorId(cancelarPedido.getIdPedido());
						
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedF).build();
	}
	else
	{
		throw new Exception("No tiene permisos para acceder a estos recursos");
	}
	}
	
	
	
	
	
	
	@DELETE
	@Path("/pedido")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePedido(CancelarPedido cancelarPedido,@PathParam("idclientesUs") Long idclientesUs) throws Exception {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());	
				
		Long idCliente = cancelarPedido.getIdCliente();
		Long idPedido = cancelarPedido.getIdPedido();

		
		if (tm.buscarClienteUsPorId(idclientesUs) != null) //valido que el cliente exista
		{
		try {
			
			Pedido pedido = new Pedido(idPedido, idCliente, 0, 0, 0, 0, 0, 0, 0, null, null, 0);
			
			tm.deletePedido(pedido);
			
									
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cancelarPedido).build();
	}
	else
	{
		throw new Exception("No tiene permisos para acceder a estos recursos");
	}
	}
	
	
	
	
	@DELETE
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePedidosVarios(ArrayList<CancelarPedido> cancelarPedidos,@PathParam("idclientesUs") Long idclientesUs) throws Exception {
		
		RotondAndesTM tm = new RotondAndesTM(getPath());	
				
		
		if (tm.buscarClienteUsPorId(idclientesUs) != null) //valido que el cliente exista
		{
		try
		{
			
			
			tm.deleteVariosPedidos(cancelarPedidos);							
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cancelarPedidos).build();
	}
	else
	{
		throw new Exception("No tiene permisos para acceder a estos recursos");
	}
	}
	
	
	
	
	
	
	

}
