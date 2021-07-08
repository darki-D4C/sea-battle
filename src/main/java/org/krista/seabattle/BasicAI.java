package org.krista.seabattle;

import java.util.ArrayList;

public class BasicAI {
    public static ArrayList<BattleShip> createBasicShips() {

        ArrayList<BattleShip> ships = new ArrayList<>();
        ArrayList<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(9, 0));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(1, 8));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(7, 7));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(1, 4));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(1, 2));
        coords.add(new Coordinate(2, 2));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(5, 3));
        coords.add(new Coordinate(5, 4));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(5, 9));
        coords.add(new Coordinate(6, 9));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(3, 5));
        coords.add(new Coordinate(3, 6));
        coords.add(new Coordinate(3, 7));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(1, 0));
        coords.add(new Coordinate(2, 0));
        coords.add(new Coordinate(3, 0));
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new Coordinate(7, 2));
        coords.add(new Coordinate(7, 3));
        coords.add(new Coordinate(7, 4));
        coords.add(new Coordinate(7, 5));
        ships.add(new BattleShip(coords));
        coords.clear();

        return ships;
    }


}
