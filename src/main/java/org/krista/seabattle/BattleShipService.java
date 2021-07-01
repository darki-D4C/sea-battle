package org.krista.seabattle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;

@Path("/game")
public class BattleShipService {

    private SeaFields fields;

    @GET
    @Path("/start")
    public Response startGame(){
        this.fields = new SeaFields();
        return Response.status(200).entity("Game started").build();
    }

    @GET
    @Path("player/{x}/{y}")
    public Response attackServerField(@PathParam("x") Integer x, @PathParam("y") Integer y){
        //fields.attackServerField(x,y);
        return Response.status(200).entity("Attacked server field").build();
    }


    @GET
    @Path("server/{x}/{y}")
    public Response attackPlayerField(@PathParam("x") Integer x, @PathParam("y") Integer y ){
        //fields.attackPlayerField(x,y);
        return Response.status(200).entity("Attacked player field").build();

    }
}
