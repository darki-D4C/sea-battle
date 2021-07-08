package org.krista.seabattle;


import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/game")

public class BattleShipService implements Serializable {

    @Inject
    private GameService gameService;


    @GET
    @Path("/start")
    public Response startGame() {
        return Response.status(200).entity("{\"success\":\"true\"}").build(); // make json?
    }


    @POST
    @Consumes("application/json")
    @Path("/attack")
    public Response attackServerField(Coordinate cord) { // not finished
        if (gameService.checkServerCoord(cord)) {
            gameService.attackServerCoord(cord);
            return Response.status(200).entity(gameService.getServerField().getShips()).build();
        } else {
            //gameService.attackPlayerField()
            return Response.status(200).entity("fuck").build();

        }

    }


    @POST
    @Consumes("application/json")
    @Path("/field")
    public Response sendPlayerField(List<BattleShip> ships) {

        if (gameService.checkPlayerShips(ships)) {
            gameService.placePlayerShips(ships);
            return Response.ok("{\"ships\":\"valid\"}").build(); // make json?
        } else {
            return Response.ok("{\"ships\":\"invalid\"}").build();
        }
    }


}
