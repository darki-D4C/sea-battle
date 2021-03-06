package org.krista.seabattle.utility;

import org.krista.seabattle.models.BattleShip;
import org.krista.seabattle.models.Coordinate;

import javax.json.*;
import java.util.List;

/**
 * Class to represent methods that modify json arrays.
 */
public class JsonCreator {
    private JsonCreator() {

    }

    /**
     * Method to add info about destroyed ship coords to json array.
     *
     * @param info          existing json array
     * @param destroyedShip a ship coords to add to array
     */
    public static void addInfoAboutDestroyedShip(JsonArrayBuilder info, BattleShip destroyedShip) {
        JsonObjectBuilder shipParameters = Json.createObjectBuilder();
        shipParameters.add("shipParts", markShip(destroyedShip.getShipParts()));//here
        info.add(shipParameters.build());
    }

    /**
     * Method to add info about destroyed tile to json array.
     *
     * @param info          existing json array
     * @param destroyedCord coord that was destroyed
     */
    public static void addInfoAboutDestroyedCord(JsonArrayBuilder info, Coordinate destroyedCord) {
        JsonObjectBuilder cordParameters = Json.createObjectBuilder();
        cordParameters.add("x", destroyedCord.getX());
        cordParameters.add("y", destroyedCord.getY());
        info.add(cordParameters.build());
    }

    /**
     * Method to create and to return Json array with info about complete miss of both sides
     *
     * @return json array with info of complete miss of both sides
     */
    public static JsonArray returnInfoAboutCompleteMiss(Coordinate missedCoordPlayer) {

        return Json.createArrayBuilder().add(Json.createObjectBuilder().add("both_missed", Json.createObjectBuilder().add("x", missedCoordPlayer.getX()).add("y", missedCoordPlayer.getY()).build()).build()).build();
    }

    /**
     * Method to create and to return Json array with info who won the game
     *
     * @param side that won the game
     * @return Json array with info about who won the game
     */

    public static JsonArray notifyAboutVictory(String side) {
        return Json.createArrayBuilder().add(Json.createObjectBuilder().add("winner", side).build()).build();
    }

    public static JsonArray notifyAboutVictory(String side,BattleShip ship) {
        JsonArrayBuilder shipPartsJson = Json.createArrayBuilder().add(Json.createObjectBuilder().add("winner",side).build());
        shipPartsJson.add(Json.createObjectBuilder().add("shipParts",markShip(ship.getShipParts())).build());
        return shipPartsJson.build();
    }

    public static JsonArray notifyAboutVictoryServer(String side, List<BattleShip> ships) {
        JsonArrayBuilder shipPartsJson = Json.createArrayBuilder().add(Json.createObjectBuilder().add("winner",side).build());
        for(BattleShip ship : ships){
            shipPartsJson.add(Json.createObjectBuilder().add("shipParts",markShip(ship.getShipParts())).build());
        }
        return shipPartsJson.build();

    }

    /**
     * Method to create json array of destroyed ship's parts (coords).
     *
     * @param shipParts destroyed parts of the ship
     * @return json array with info about destroyed ship parts
     */
    public static JsonArray markShip(List<Coordinate> shipParts) {
        JsonArrayBuilder shipPartsJson = Json.createArrayBuilder();
        JsonObjectBuilder cordParameters = Json.createObjectBuilder();
        for (Coordinate cord : shipParts) {
            cordParameters.add("x", cord.getX());
            cordParameters.add("y", cord.getY());
            shipPartsJson.add(cordParameters.build());
            cordParameters = Json.createObjectBuilder();
        }
        return shipPartsJson.build();

    }

    public static JsonArray returnInfoAboutDestroyedCoordByPlayer(Coordinate coord) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        JsonObject side = Json.createObjectBuilder().add("side", "player").build();
        json.add(side);
        JsonObject coordJson = Json.createObjectBuilder().add("x", coord.getX()).add("y", coord.getY()).build();
        json.add(coordJson);
        return json.build();
    }

    public static JsonArray returnInfoAboutDestroyedShipByPlayer(List<Coordinate> parts) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        JsonObject side = Json.createObjectBuilder().add("side", "player").build();
        json.add(side);
        JsonObject arrayOfParts = Json.createObjectBuilder().add("shipParts", markShip(parts)).build();
        json.add(arrayOfParts);
        return json.build();
    }


}
