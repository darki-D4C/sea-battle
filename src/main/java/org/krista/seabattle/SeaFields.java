package org.krista.seabattle;

import java.util.ArrayList;

public class SeaFields{

    /*
    Here -1 element in fields represent an unknown tile, it is unclear whether there is a ship or not.
    0 - empty tile, means no ship or part of the ship there
    1 - ship tile, there is a ship or theres a part of some ship
     */

    private  int[][] playerField = new int[10][10];

    private  int[][] serverField = new int[10][10];

    private  int[][] visibleServerField = new int[10][10];

    private ArrayList<BattleShip> playerShips;

    private ArrayList<BattleShip> serverShips;

    public void initFields(){

        //Set markers on each of three fields
        for(int i = 0; i<10 ; i++){
            for(int j = 0 ; j < 10 ; j++){
                playerField[i][j] = 0;
                serverField[i][j] = 0;
                visibleServerField[i][j] = -1;
            }
        }
    }

    public void attackServerField(int x, int y){

    }

    public void attackPlayerField(int x, int y){

    }

    public void updateTile(int x, int y){

    }

    //Probably gonna use Fabric Pattern for ships
    public void placePlayerShips(){

    }

    public void placeServerShips(){

    }

}
