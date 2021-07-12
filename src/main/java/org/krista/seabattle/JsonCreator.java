package org.krista.seabattle;

import javax.json.*;
import java.util.List;

/**
 * Class to represent methods that modify json arrays
 */
public class JsonCreator {
    /**
     * Method to add info about destroyed ship coords to json array
     *
     * @param info,          existing json array
     * @param destroyedShip, a ship coords to add to array
     * @return json array with added info about destroyedShip coords
     */
    public static JsonArrayBuilder addInfoAboutDestroyedShip(JsonArrayBuilder info, BattleShip destroyedShip) {
        JsonObjectBuilder shipParameters = Json.createObjectBuilder();
        shipParameters.add("shipParts", markShips(destroyedShip.getShipParts()));//here
        info.add(shipParameters.build());
        return info;
    }

    /**
     * Method to add info about destroyed tile to json array
     *
     * @param info,          existing json array
     * @param destroyedCord, coord that was destroyed
     * @return json array with added info about destroyedCoord
     */
    public static JsonArrayBuilder addInfoAboutDestroyedCord(JsonArrayBuilder info, Coordinate destroyedCord) {
        JsonObjectBuilder cordParameters = Json.createObjectBuilder();
        cordParameters.add("x", destroyedCord.getX());
        cordParameters.add("y", destroyedCord.getY());
        info.add(cordParameters.build());
        return info;
    }

    /**
     * Method to create and to return Json array with info about complete miss of both sides
     *
     * @return json array with info of complete miss of both sides
     */
    public static JsonArray returnInfoAboutCompleteMiss() {

        return Json.createArrayBuilder().add(Json.createObjectBuilder().add("status", "miss").build()).build();
    }

    /**
     * Method to create and to return Json array with info who won the game
     *
     * @param side, that won the game
     * @return Json array with info about who won the game
     */
    public static JsonArray notifyAboutVictory(String side) {
        return Json.createArrayBuilder().add(Json.createObjectBuilder().add("winner", side).build()).build();
    }

    /**
     * Method to create json array of destroyed ship's parts (coords)
     *
     * @param shipParts, destroyed parts of the ship
     * @return json array with info about destroyed ship parts
     */
    public static JsonArray markShips(List<Coordinate> shipParts) {
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
}
