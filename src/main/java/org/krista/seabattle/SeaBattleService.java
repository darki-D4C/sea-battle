package org.krista.seabattle;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 * Class to represent interaction between server and client.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/game")
public class SeaBattleService implements Serializable {

    /**
     * CDI bean for game logic.
     */
    @Inject
    private GameService gameService;

    /**
     * GET request for starting game session.
     *
     * @return status of game
     */
    @GET
    @Path("/start")
    public Response startGame() {
        gameService.clearFields();
        gameService.setStatus(GameStatus.NOT_STARTED);
        gameService.generateRandomShipsAndPlaceThem();
        return Response.status(200).entity("{\"status\":\"started\"}").build();
    }

    /**
     * POST request for attacking server.
     *
     * @param cord coordinate to attack
     * @return status of attack, either complete miss, successfully attacked coord by player or chain of attacks by server
     */
    @POST
    @Consumes("application/json")
    @Path("/attack")
    public Object attackServerField(Coordinate cord) {
        if(!cord.checkValidity()){
            return Response.ok("\"coordinate\":\"invalid\"").build();
        }
        GameStatus currentStatus = gameService.getStatus();
        switch (currentStatus) {
            case ONGOING:
                break;
            case FINISHED_P:
                return JsonCreator.notifyAboutVictory("player");
            case NOT_STARTED:
                return Response.ok("\"status\":\"not_started\"");
            case FINISHED_S:
                return JsonCreator.notifyAboutVictory("server");
        }

        if (gameService.checkServerCoord(cord)) {
            gameService.getServerField().attackCoord(cord);
            if (gameService.checkGameOver("server")) {
                gameService.setStatus(GameStatus.FINISHED_P);
                return JsonCreator.notifyAboutVictory("player");
            }
            if (gameService.getServerField().findShipByCord(cord).getNumberOfDecks() == 0) {
                return JsonCreator.returnInfoAboutDestroyedShipByPlayer(gameService.getServerField().
                        findShipByCord(cord));
            }
            return JsonCreator.returnInfoAboutDestroyedCoordByPlayer(cord);
        } else {
            JsonArray attack = gameService.attackPlayerField(cord);
            if (gameService.checkGameOver("player")) {
                gameService.setStatus(GameStatus.FINISHED_S);
                return JsonCreator.notifyAboutVictory("server");
            }
            return attack;
        }


    }

    /**
     * POST request for sending and checking player ships.
     *
     * @param ships , player ships
     * @return status of received field, either valid or invalid
     */
    @POST
    @Consumes("application/json")
    @Path("/field")
    public Response sendPlayerField(List<BattleShip> ships) {
        if (gameService.getStatus() != GameStatus.NOT_STARTED) {
            return Response.ok("\"status\":\"already_started\"").build();
        }
        if (gameService.checkPlayerShips(ships)) {
            gameService.placePlayerShips(ships);
            gameService.setStatus(GameStatus.ONGOING);
            return Response.ok("{\"ships\":\"valid\"}").build(); // make json?
        } else {
            return Response.ok("{\"ships\":\"invalid\"}").build();
        }
    }

}
