package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Acompaniamiento;
import vos.Bebida;
import vos.Entrada;
import vos.PlatoFuerte;
import vos.Postre;
import vos.ServidoProducto;

//RF10
@Path("restaurantesus/{idRestaurantesus: \\d+}/servido")
public class ServicioProductoService {
	
	
	
	@Context
	private ServletContext context;
	
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	
	
	@PUT
	@Path("/postres")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePostre(Postre postre,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try {
			tm.updatePostreCantidad(postre);
			postre = tm.buscarPostreId(postre.getId());
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(postre).build();
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	
	@PUT
	@Path("/acompanimientos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAcompaniemientos(Acompaniamiento acompaniamiento,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try {
			tm.updateAcompaniemientoCantidad(acompaniamiento);
			acompaniamiento = tm.buscarAcompaniamientoPorId(acompaniamiento.getId());
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(acompaniamiento).build();
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	
	
	@PUT
	@Path("/entradas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEntradas(Entrada entrada,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try {
			tm.updateEntradaCantidad(entrada);
			entrada = tm.buscarEntradaPorId(entrada.getId());
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(entrada).build();
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	@PUT
	@Path("/bebidas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBebidas(Bebida bebida,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try {
			tm.updateBebidaCantidad(bebida);
			bebida = tm.buscarBebidaPorId(bebida.getId());
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(bebida).build();
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	
	@PUT
	@Path("/platoFuertes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePlatofuerte(PlatoFuerte platoFuerte,@PathParam("idRestaurantesus") Long idRestaurantesus) throws Exception {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		if (tm.buscarRestauranteUsPorId(idRestaurantesus) != null) 
		{
		try {
			tm.updateplatoFuerteCantidad(platoFuerte);
			platoFuerte = tm.buscarPlatoFuerteId(platoFuerte.getId());
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(platoFuerte).build();
		}
		else
		{
			throw new Exception("No tiene permisos para acceder a estos recursos");
		}
	}
	
	
	
	
	

}
