package org.krista.seabattle;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonCreator {
    public static JSONArray addInfoAboutDestroyedShip(JSONArray info, BattleShip destroyedShip) {

        JSONObject shipParameters = new JSONObject();
        shipParameters.put("shipParts", destroyedShip.getShipParts());
        info.put(shipParameters);
        return info;
    }

    public static JSONArray addInfoAboutDestroyedCord(JSONArray info, Coordinate destroyedCord) {

        JSONObject cordParameters = new JSONObject();
        cordParameters.put("x",destroyedCord.getX());
        cordParameters.put("y",destroyedCord.getY());

        info.put(cordParameters);

        return info;
    }

    public static JSONArray returnInfoAboutCompleteMiss() {

        return new JSONArray().put(new JSONObject().put("status","miss"));
    }
}
