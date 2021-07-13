package org.krista.seabattle;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent battleships
 */
public class BattleShip {
    @JsonbProperty("shipParts")
    private List<Coordinate> shipParts;

    @JsonbProperty("numberOfDecks")
    private int numberOfDecks;

    private List<Coordinate> remainingShipParts;

    /**
     * Json constructor for battleship
     *
     * @param numberOfDecks, number of decks of the ship
     * @param shipParts,     number of decks of the ship
     */
    @JsonbCreator
    public BattleShip(@JsonbProperty("numberOfDecks") int numberOfDecks, @JsonbProperty("shipParts") List<Coordinate> shipParts) {
        this.numberOfDecks = numberOfDecks;
        this.shipParts = shipParts;
        this.remainingShipParts = new ArrayList<>();
        this.remainingShipParts.addAll(shipParts);
    }

    /**
     * Battleship constructor with only parts and number of decks
     *
     * @param coords, parts of ship
     */
    public BattleShip(List<Coordinate> coords) {
        this.shipParts = coords;
        this.numberOfDecks = coords.size();
        this.remainingShipParts = new ArrayList<>();
        this.remainingShipParts.addAll(coords);
    }

    /**
     * Constructor for battleships with direction
     *
     * @param direction,   direction
     * @param randomX      , x
     * @param randomY      , y
     * @param countOfDecks ,  number of decks
     */
    public BattleShip(char direction, int randomX, int randomY, int countOfDecks) {
        List<Coordinate> parts = new ArrayList<>();
        this.shipParts = new ArrayList<>();
        this.remainingShipParts = new ArrayList<>();
        parts.add(new Coordinate(randomX, randomY));
        for (int i = 1; i < countOfDecks; i++) {
            switch (direction) {
                case 'n':
                    randomY--;
                    parts.add(new Coordinate(randomX, randomY));
                    break;
                case 'e':
                    randomX++;
                    parts.add(new Coordinate(randomX, randomY));
                    break;
                case 's':
                    randomY++;
                    parts.add(new Coordinate(randomX, randomY));
                    break;
                case 'w':
                    randomX--;
                    parts.add(new Coordinate(randomX, randomY));
                    break;
                default:
                    System.out.println("Something is wrong!");
            }
        }
        this.shipParts.addAll(parts);
        this.remainingShipParts.addAll(parts);
        this.numberOfDecks = parts.size();
    }

    /**
     * Method to damage ship.
     *
     * @param cord, which was damaged.
     */
    public void damageShip(Coordinate cord) {
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
     * @param cord, certain part.
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
