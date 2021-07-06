package org.krista.seabattle;

import org.json.JSONPropertyName;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.json.bind.annotation.JsonbCreator;
import java.util.ArrayList;

// Class to represent the coordinates for tiles
public class BattleShip {
    ArrayList<int[]> shipParts;
    int numberOfDecks;

    public void checkPart(int x, int y) {

    }


    public BattleShip(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    @JsonbCreator
    public BattleShip(int numberOfDecks, ArrayList<int[]> shipParts){
        this.numberOfDecks = numberOfDecks;
        this.shipParts = shipParts;
    }

    public BattleShip(ArrayList<int[]> coords) {
        this.shipParts = new ArrayList<>();
        shipParts.addAll(coords);
        this.numberOfDecks = shipParts.toArray().length;
    }

    public void checkPlacement(int x, int y) {

    }

    public void damagedShip(int x, int y) {
        this.numberOfDecks--;
        //shipParts.remove(int x, int y); //Ship damaged,remove damaged deck
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public ArrayList<int[]> getShipParts() {
        return shipParts;
    }

    public void setCoords(char direction, int randomX, int randomY, int countOfDecks) {

    }

    public void setCoords(int randomX, int randomY) {

    }


}
