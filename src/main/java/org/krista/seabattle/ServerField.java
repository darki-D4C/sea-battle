package org.krista.seabattle;

import jdk.nashorn.api.scripting.JSObject;

import javax.enterprise.context.SessionScoped;
import javax.json.Json;
import javax.json.JsonArray;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.*;

@SessionScoped
public class ServerField implements Serializable {
     /*
    Here -1 element in fields represent an unknown tile, it is unclear whether there is a ship or not.
    0 - empty tile, means no ship or part of the ship there
    1 - ship tile, there is a ship or theres a part of some ship
     */

    private int[][] field = new int[10][10];

    //private int[][] serverField = new int[10][10];

    //private int[][] visibleServerField = new int[10][10]; - ?

    private ArrayList<BattleShip> ships;

    //private ArrayList<BattleShip> serverShips;

    public ServerField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = 0;
            }
        }

        placeServerShips();
    }

    public void attackPlayerField(int x, int y) {

    }

    public JSONObject receivePlayerAttack() {
        return null;
    }

    public void updateTile(int x, int y) {

    }


    public void placeServerShips() {

    }

    //These two methods will be returned(json data) as a response from server in case of successful attack of miss
    public JSONObject jsonSuccessAttack() {
        return null;
    }

    public JSONObject jsonMissAttack() {
        return null;
    }


}
