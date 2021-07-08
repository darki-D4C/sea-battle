package org.krista.seabattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (Coordinate cord : ship.getShipParts()) {
            field[cord.getX()][cord.getY()] = 1;
        }
    }

    public void setFieldAndShips(List<BattleShip> ships) {
        for (BattleShip ship : ships) {
            this.ships.add(ship);
            for (Coordinate cord : ship.getShipParts()) {
                field[cord.getX()][cord.getY()] = 1;
            }
        }
    }

    public List<BattleShip> getShips() {
        return ships;
    }

}
