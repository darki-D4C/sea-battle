package org.krista.seabattle.services;

import org.krista.seabattle.models.BattleShip;
import org.krista.seabattle.models.Coordinate;
import org.krista.seabattle.utility.GameStatus;
import org.krista.seabattle.utility.JsonCreator;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
     * @param coord coordinate to attack
     * @return status of attack, either complete miss, successfully attacked coord by player or chain of attacks by server
     */
    @POST
    @Path("/attack")
    public Object attackServerField(Coordinate coord) {
        if (gameService.getPlayerField().checkValidity(coord)) {
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
            default:
                return Json.createObjectBuilder().add("status","error").build();
        }

        if (gameService.checkServerCoord(coord)) {
            gameService.getServerField().attackCoord(coord);
            if (gameService.checkGameOver("server")) {
                gameService.setStatus(GameStatus.FINISHED_P);
                return JsonCreator.notifyAboutVictory("player");
            }
            if (gameService.getServerField().findShipByCord(coord).getNumberOfDecks() == 0) {
                return JsonCreator.returnInfoAboutDestroyedShipByPlayer(gameService.getServerField().
                        findShipByCord(coord).getShipParts());
            }
            return JsonCreator.returnInfoAboutDestroyedCoordByPlayer(coord);
        } else {

            JsonArrayBuilder serverTurnActions = Json.createArrayBuilder();
            serverTurnActions.add(Json.createObjectBuilder().add("missed", Json.createObjectBuilder().
                    add("x", coord.getX()).add("y", coord.getY()).build()).build());
            serverTurnActions.add(Json.createObjectBuilder().add("side", "server").build());

            List<Coordinate> attack = gameService.attackPlayerField();
            while (!attack.isEmpty()) {
                if (attack.size() == 1) {
                    if (gameService.getPlayerField().findShipByCord(attack.get(0)).getNumberOfDecks() == 0) {
                        JsonCreator.addInfoAboutDestroyedShip(serverTurnActions, gameService.getPlayerField().findShipByCord(attack.get(0)));
                    } else {
                        JsonCreator.addInfoAboutDestroyedCord(serverTurnActions, attack.get(0));
                    }
                }
                if (attack.size() > 1) {
                    JsonCreator.addInfoAboutDestroyedShip(serverTurnActions, gameService.getPlayerField().findShipByCord(attack.get(0)));
                }
            }
            JsonArray serverTurn = serverTurnActions.build();
            if ((serverTurn.size() - 2) == 0) {
                return JsonCreator.returnInfoAboutCompleteMiss(coord);
            }
            if (gameService.checkGameOver("player")) {
                gameService.setStatus(GameStatus.FINISHED_S);
                return JsonCreator.notifyAboutVictory("server");
            }
            return serverTurn;
        }


    }

    /**
     * POST request for sending and checking player ships.
     *
     * @param ships , player ships
     * @return status of received field, either valid or invalid
     */
    @POST
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
