package org.krista.seabattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BasicAI {
    public static ArrayList<BattleShip> createBasicShips() {

        ArrayList<BattleShip> ships = new ArrayList<>();


        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(9, 0)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(1, 8)}))));

        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(7, 7)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(1, 4)}))));

        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(1, 2),new Coordinate(2, 2)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(5, 3),new Coordinate(5, 4)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(5, 9),new Coordinate(6, 9)}))));

        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(3, 5),new Coordinate(3, 6),new Coordinate(3, 7)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(1, 0),new Coordinate(2, 0),new Coordinate(3, 0)}))));
        ships.add(new BattleShip(new ArrayList<>(Arrays.asList(new Coordinate[]{new Coordinate(7, 2),new Coordinate(7, 3),new Coordinate(7, 4),new Coordinate(7, 5)}))));





        return ships;
    }


}
