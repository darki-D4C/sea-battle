package org.krista.seabattle;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private int[][] field = new int[10][10];

    private List<BattleShip> ships = new ArrayList<>();

    public GameField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = 0;
            }
        }
    }

    public int[][] getField() {
        return field;
    }

    public void updateField(BattleShip ship) {
        ships.add(ship);
        for (Coordinate cord : ship.getRemainingShipParts()) {
            field[cord.getX()][cord.getY()] = 1;
        }
    }

    public void setFieldAndShips(List<BattleShip> ships) {
        for (BattleShip ship : ships) {
            updateField(ship);
        }
    }

    public List<BattleShip> getShips() {
        return ships;
    }

    public BattleShip findShipByCord(Coordinate cord){
        for(BattleShip ship: ships){
            if(ship.getShipParts().contains(cord)){
                return ship;
            }
        }
        return null; // Исправить потом
    }

    public void attackCoord(Coordinate cord) {
        getField()[cord.getX()][cord.getY()] = 0;
        // ships не null
        for (BattleShip ship : getShips()) {
            if (ship.hasPart(cord)) { // никогда не находит координату хотя она есть в каком то из обьектов BattleShip
                ship.damageShip(cord);
                //Check if ship has alive parts

            }
        }

    }

}
