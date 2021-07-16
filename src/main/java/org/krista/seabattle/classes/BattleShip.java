package org.krista.seabattle.classes;

import org.krista.seabattle.utility.Position;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

import static org.krista.seabattle.utility.Position.HORIZONTAL;
import static org.krista.seabattle.utility.Position.VERTICAL;

/**
 * Class to represent battleships.
 */
public class BattleShip {
    private final List<Coordinate> shipParts;
    private int numberOfDecks;
    private final List<Coordinate> remainingShipParts;

    /**
     * Json constructor for battleship.
     *
     * @param shipParts number of decks of the ship
     */
    @JsonbCreator
    public BattleShip(@JsonbProperty("shipParts") List<Coordinate> shipParts) {
        this.numberOfDecks = shipParts.size();
        this.shipParts = shipParts;
        this.remainingShipParts = new ArrayList<>();
        this.remainingShipParts.addAll(shipParts);
    }


    /**
     * Constructor for battleships with direction.
     *
     * @param direction    direction
     * @param randomX      x
     * @param randomY      y
     * @param countOfDecks number of decks
     */
    public BattleShip(Position direction, int randomX, int randomY, int countOfDecks) {
        List<Coordinate> parts = new ArrayList<>();
        this.shipParts = new ArrayList<>();
        this.remainingShipParts = new ArrayList<>();
        parts.add(new Coordinate(randomX, randomY));
        for (int i = 1; i < countOfDecks; i++) {
            if (direction == VERTICAL) {
                randomY++;
                parts.add(new Coordinate(randomX, randomY));
            }
            if (direction == HORIZONTAL) {
                randomX++;
                parts.add(new Coordinate(randomX, randomY));
            }
        }
        this.shipParts.addAll(parts);
        this.remainingShipParts.addAll(parts);
        this.numberOfDecks = parts.size();
    }

    /**
     * Method to damage ship.
     *
     * @param cord which was damaged.
     */
    public void damageShip(Coordinate cord) {
        if (!getShipParts().contains(cord)) {
            return;
        }
        this.numberOfDecks--;
        this.remainingShipParts.remove(cord); //Ship damaged,remove damaged deck
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public List<Coordinate> getRemainingShipParts() {
        return remainingShipParts;
    }

    /**
     * Check if ship has certain part.
     *
     * @param cord certain part.
     * @return status(has / no).
     */
    public boolean hasPart(Coordinate cord) {
        for (Coordinate xy : remainingShipParts) {
            if (xy.getX() == cord.getX() && xy.getY() == cord.getY()) {
                return true;
            }
        }
        return false;
    }

    public List<Coordinate> getShipParts() {
        return shipParts;
    }
}
