package org.krista.seabattle;

import java.util.ArrayList;

public class GameField {
    private int[][] field = new int[10][10];

    private ArrayList<BattleShip> ships;

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
        for (int[] coord : ship.getShipParts()) {
            field[coord[0]][coord[1]] = 1;
        }
    }

    public void setFieldAndShips(ArrayList<BattleShip> ships) {
        this.ships = new ArrayList<>();
        for (BattleShip ship : ships) {
            this.ships.add(ship);
            for (int[] coord : ship.getShipParts()) {
                field[coord[0]][coord[1]] = 1;
            }
        }
    }
}
