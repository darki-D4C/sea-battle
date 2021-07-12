package org.krista.seabattle;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent interaction between server and client
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/game")
public class BattleShipService implements Serializable {

    /**
     * CDI bean for game logic
     */
    @Inject
    private GameService gameService;

    /**
     * GET request for starting game session
     *
     * @return status of game
     */
    @GET
    @Path("/start")
    public Response startGame() {
        return Response.status(200).entity("{\"success\":\"true\"}").build();
    }

    /**
     * POST request for attacking server
     *
     * @param cord coordinate to attack
     * @return status of attack, either complete miss, successfully attacked coord by player or chain of attacks by server
     */
    @POST
    @Consumes("application/json")
    @Path("/attack")
    public Object attackServerField(Coordinate cord) {
        if (gameService.checkServerCoord(cord)) {
            gameService.getServerField().attackCoord(cord);
            if (gameService.checkGameOver("server")) {
                return JsonCreator.notifyAboutVictory("player");
            }
            if (gameService.getServerField().findShipByCord(cord).getNumberOfDecks() == 0) {
                return gameService.getServerField().findShipByCord(cord).getShipParts();
            }
            return cord;
        } else {
            JsonArray attack = gameService.attackPlayerField();
            if (gameService.checkGameOver("player")) {
                return JsonCreator.notifyAboutVictory("server");
            }
            return attack;
        }


    }

    /**
     * POST request for sending and checking player ships
     *
     * @param ships , player ships
     * @return status of recieved field, either valid or invalid
     */
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

    @GET
    @Produces
    @Path("/serverfield")
    public Response mockSendServerField() {
        ArrayList<BattleShip> sev = (ArrayList<BattleShip>) gameService.getPlayerField().getShips();
        return Response.ok(sev).build();
    }

}
