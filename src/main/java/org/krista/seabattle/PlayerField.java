package org.krista.seabattle;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

@SessionScoped
public class PlayerField implements Serializable {

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

    public PlayerField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = 0;
            }
        }
    }

    public void checkTile(int x, int y) {

    }

    //Probably gonna use Fabric Pattern for ships
    public void placePlayerShips() {

    }

    public boolean checkPlayerShips() {
        return false;
    }

    public void receiveAttackFromServer(int x, int y) {

    }

    // Return json version of player field
    public JSONObject sendFieldToClient() {
        JSONObject fieldJson = new JSONObject();
        for (int i = 0; i < 10; i++) {
            JSONArray arr = new JSONArray();
            for (int j = 0; j < 10; j++) {
                arr.put(field[i][j]);
            }
            fieldJson.put("field", arr);
        }
        return fieldJson;
    }

    //Parse JSONObject, which client sends, to 2d array "field"
    public void getFieldFromClient(JSONObject fieldJson) {

    }
}
