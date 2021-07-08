package org.krista.seabattle;

import org.json.JSONPropertyName;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.ArrayList;
import java.util.List;

// Class to represent the coordinates for tiles
public class BattleShip {
    @JsonbProperty("shipParts")
    private List<Coordinate> shipParts;
    @JsonbProperty("numberOfDecks")
    private int numberOfDecks;


    public BattleShip() {
        this.numberOfDecks = 0;
        this.shipParts = new ArrayList<>();
    }


    public BattleShip(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    @JsonbCreator
    public BattleShip(@JsonbProperty("numberOfDecks") int numberOfDecks, @JsonbProperty("shipParts") ArrayList<Coordinate> shipParts) {
        this.numberOfDecks = numberOfDecks;
        this.shipParts = shipParts;
    }

    public BattleShip(ArrayList<Coordinate> coords) {
        this.shipParts = new ArrayList<>();
        shipParts.addAll(coords);
        this.numberOfDecks = shipParts.toArray().length;
    }

    public boolean checkCord(Coordinate cord) {
        if (this.getShipParts().contains(cord)) {
            return true;
        } else return false;
    }

    public void damageShip(Coordinate cord) {
        this.numberOfDecks--;
        this.shipParts.remove(cord); //Ship damaged,remove damaged deck
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public List<Coordinate> getShipParts() {
        return shipParts;
    }

    public void setCoords(char direction, int randomX, int randomY, int countOfDecks) {

    }

    public void setCoords(int randomX, int randomY) {

    }


    public boolean hasPart(Coordinate cord) {
        for (Coordinate xy : shipParts) {
            if (xy.getX() == cord.getX() && xy.getY() == cord.getY()) {
                return true;
            }
        }
        return false;
    }
}
