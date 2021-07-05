package org.krista.seabattle;

import javax.enterprise.context.SessionScoped;
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

    public void attackPlayerField(int x,int y) {

    }

    public JSONObject receivePlayerAttack() {
        return null;
    }

    public void updateTile(int x, int y) {

    }


    public void placeServerShips() {
        createServerShip(4, 1);                            //generate battleship
        createServerShip(3, 2);                               //generate Cruisers
        createServerShip(2, 3);                             //generate Destroyers
        createServerShip(1, 4);
    }

    private void createServerShip(int countOfDecks, int maxCountOfShips) {
        int countOfShip = 0;
        while (countOfShip < maxCountOfShips) {
            int randomX = (int) (Math.random() * 10);
            int randomY = (int) (Math.random() * 10);
            int randomDirection = (int) (Math.random() * 4);
            char direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';

            while(!checkCoords(randomX,randomY,direction,countOfDecks) && field[randomX][randomY] == 0 ){
                randomX = (int) (Math.random() * 10);
                randomY = (int) (Math.random() * 10);
                randomDirection = (int) (Math.random() * 4);
                direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';
            }

            BattleShip ship = new BattleShip(countOfDecks);
            if (countOfDecks > 1) {
                ship.setCoords(direction, randomX, randomY, countOfDecks);
            } else {
                ship.setCoords(randomX, randomY);
            }
            countOfShip++;
            //
            updateField(ship);
            this.ships.add(ship);
        }
    }

    private boolean checkCoords(int randomX, int randomY, char direction, int countOfDecks) {
        ArrayList<int[]> coords;

        if(field[randomX][randomY] == 1){
            return false;
        }

        if ( countOfDecks == 1 ){
            return true;
        }

        for(int i = 0; i< countOfDecks;i++){
            switch (direction){
                case 'n':
                    if(randomY-- < 0) return false;
                    if(field[randomX][randomY]==1) return false;
                    break;
                case 'e':
                    if(randomX++ > 9) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                case 's':
                    if(randomY++ > 9) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                case 'w':
                    if(randomX-- < 0) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                default:
                    System.out.println("Some thing is wrong!");
            }
        }
        return true;
    }

    private void updateField(BattleShip ship) {
        for (int[] coord : ship.getShipParts()) {
            field[coord[0]][coord[1]] = 1;
        }
    }

    //These two methods will be returned(json data) as a response from server in case of successful attack of miss
    public JSONObject jsonSuccessAttack() {
        return null;
    }

    public JSONObject jsonMissAttack() {
        return null;
    }


}
