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
import vos.ConsultaBuenosClientes;

/**
 * 
 * @author angeloMarcetty
 *	RFC12
 */
@Path("administradores/{idAdministradores: \\d+}/consultaBuenosClientes")
public class ConsultaBuenosClientesServices {
	
	
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
	public Response consultaBuenosClientes( @PathParam("idAdministradores") Long idAdministradores ) throws Exception
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
			try {			
				ConsultaBuenosClientes cbc = tm.consultaBuenosClientesReq(idAdministradores);				
				return Response.status(200).entity(cbc).build();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
	
	}
}
