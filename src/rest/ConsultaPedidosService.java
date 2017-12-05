package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.ConsultaPedidos;
import vos.Entrada;

/**
 * 
 * @author angeloMarcetty
 *
 */
@Path("restaurantesus/{idRestaurantesus: \\d+}/consultaPedidos")
public class ConsultaPedidosService {
	
	
	@Context
	private ServletContext context;
	
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	@GET
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response consultarReportePedidos( @PathParam("idRestaurantesus") Long idRestaurantesus ) throws Exception
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) {
			try {
											
				ConsultaPedidos cp = tm.consultarReportePedidos(idRestaurantesus);
				
				return Response.status(200).entity(cp).build();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		} else {
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
		
	}
	

}
