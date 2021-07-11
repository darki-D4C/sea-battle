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

    private List<Coordinate> remainingShipParts;


    @JsonbCreator
    public BattleShip(@JsonbProperty("numberOfDecks") int numberOfDecks, @JsonbProperty("shipParts") List<Coordinate> shipParts) {
        this.numberOfDecks = numberOfDecks;
        this.shipParts = shipParts;
        this.remainingShipParts = new ArrayList<>();
        this.remainingShipParts.addAll(shipParts);
    }

    public BattleShip(List<Coordinate> coords) {
        this.shipParts = coords;
        this.numberOfDecks = coords.size();
        this.remainingShipParts = new ArrayList<>();
        this.remainingShipParts.addAll(coords);

    }



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

    public void setCoords(char direction, int randomX, int randomY, int countOfDecks) {

    }

    public void setCoords(int randomX, int randomY) {

    }


    public boolean hasPart(Coordinate cord) {
        for (Coordinate xy : remainingShipParts) {
            if (xy.getX() == cord.getX() && xy.getY() == cord.getY()) {
                return true;
            }
        }
        return false;
    }

    public List<Coordinate> getShipParts(){
        return shipParts;
    }

}
