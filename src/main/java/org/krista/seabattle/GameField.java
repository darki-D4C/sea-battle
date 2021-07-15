package org.krista.seabattle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent server/player field.
 */
public class GameField {
    /**
     * 2d array of tiles
     * and list of ships on field.
     */
    private final int[][] field = new int[10][10];
    private final List<BattleShip> ships = new ArrayList<>();

    /**
     * Initialize 2d array, field of tiles.
     */
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

    /**
     * Method to update field with certain ship and mark tiles on 2d array with ships coords.
     *
     * @param ship to add to field
     */
    public void updateField(BattleShip ship) {
        ships.add(ship);
        for (Coordinate cord : ship.getRemainingShipParts()) {
            field[cord.getX()][cord.getY()] = 1;
        }
    }


    public List<BattleShip> getShips() {
        return ships;
    }

    /**
     * Method to find ship by certain coord.
     *
     * @param coord by which to find
     * @return found ship
     */
    public BattleShip findShipByCord(Coordinate coord) {
        for (BattleShip ship : getShips()) {
            if (ship.getShipParts().contains(coord)) {
                return ship;
            }
        }
        return null; // Исправить потом
    }

    /**
     * Method to attack certain coord, and damage ship, which has this coord.
     *
     * @param coord which to attack
     */
    public void attackCoord(Coordinate coord) {
        if (checkValidity(coord)) {
            return;
        }
        getField()[coord.getX()][coord.getY()] = 0;
        for (BattleShip ship : getShips()) {
            if (ship.hasPart(coord)) {
                ship.damageShip(coord);
                return;
            }
        }
    }


    public void clearSelf() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = 0;
            }
        }
        this.ships.clear();
    }

    public boolean checkValidity(Coordinate coord) {
        return !(coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() <= 9 && coord.getY() <= 9);
    }
}
