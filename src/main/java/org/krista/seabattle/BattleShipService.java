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
            gameService.getServerField().attackCoord(cord);
            if(gameService.getServerField().findShipByCord(cord).getNumberOfDecks()==0){
                return Response.status(200).entity(gameService.getPlayerField().findShipByCord(cord).getShipParts()).build();
            }
            return Response.status(201).entity(cord).build();
        } else {
            //Почему то в в BasicAi создается список обьектов BattleShip у которых все координаты равны последенему добавленому кораблю
            return Response.status(202).entity(gameService.attackPlayerField().toString()).build();

        }

        //
        //gameService.checkServerCoord(cord)+" " + cord.getX()+" " + cord.getY()

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
