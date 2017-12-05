package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Menu;
import vos.Postre;

@Path("restaurantesus/{idRestaurantesus: \\d+}/menus")
public class MenuService 
{
	
	
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
	public Response getMenu( @PathParam("idRestaurantesus") Long idRestaurantesus ) throws Exception
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try
		{
			Menu m = tm.buscarMenuPorIdRestaurante(idRestaurantesus);		
			return Response.status( 200 ).entity( m ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@DELETE
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteMenu(Menu menu,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
//		{
//		try {
//			tm.deleteMenu(menu);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(menu).build();
//		}
//		else
//		{
//			throw new Exception("No tiene permisos para acceder a estos recursos");
//		}
//	}

	
	
	
	
	
	
	
	
	
	

}