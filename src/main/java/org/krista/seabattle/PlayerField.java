package org.krista.seabattle;

import org.json.JSONArray;
import org.json.JSONObject;


import javax.enterprise.context.SessionScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
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
    public void placePlayerShips(JSONObject ships) {
        ArrayList<BattleShip> shipsToPlace;
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(ships);
        shipsToPlace = jsonb.fromJson(result, ArrayList.class);
        for(BattleShip ship: shipsToPlace){
            this.ships.add(ship);
            updateField(ship);
        }
    }

    private void updateField(BattleShip ship) {
        for(int[] coord : ship.getShipParts()){
            field[coord[0]][coord[1]] = 1;
        }
    }

    public boolean checkPlayerShips(JSONObject ships) {
        ArrayList<BattleShip> shipsToCheck;
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(ships);
        shipsToCheck = jsonb.fromJson(result, ArrayList.class);
        int[][] checkField = field;
        for (BattleShip ship : shipsToCheck) {
            if (!checkShip(ship, checkField)) return false;
            for (int[] coord : ship.getShipParts()) {
                checkField[coord[0]][coord[1]] = 1;
            }
        }
        return true;
    }

    private boolean checkShip(BattleShip ship, int[][] checkField) {
        ArrayList<int[]> coords = ship.getShipParts();
        for (int[] coord : coords) {
            if (field[coord[0]][coord[1]] == 1) { // Means coord is not available for placement
                return false;
            }
        }
        int x = ship.getShipParts().get(0)[0];
        int y = ship.getShipParts().get(0)[1];
        // add check for out of bounds x and y
        for (int i = 1; i < ship.getNumberOfDecks() - 1; i++) {
            if (!(coords.get(i)[0] == x || coords.get(i)[1] == y ) || (coords.get(i)[0] == x && coords.get(i)[1] == y)) {
                // Если либо x либо y , не совпадают с
                // соответсвующими x y первой палубы,
                //то палуба не находится на одной линии с первой
                return false;
            }
        }
        return true;
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
