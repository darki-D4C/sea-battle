package org.krista.seabattle;

import java.util.ArrayList;

public class BasicAI {
    public static ArrayList<BattleShip> createBasicShips(){

        ArrayList<BattleShip> ships = new ArrayList<>();
        ArrayList<int[]> coords = new ArrayList<>();
        coords.add(new int[]{9, 0});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{1, 8});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{7, 7});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{1, 4});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{1, 2});
        coords.add(new int[]{2, 2});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{5, 3});
        coords.add(new int[]{5, 4});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{5, 9});
        coords.add(new int[]{6, 9});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{3, 5});
        coords.add(new int[]{3, 6});
        coords.add(new int[]{3, 7});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{1, 0});
        coords.add(new int[]{2, 0});
        coords.add(new int[]{3, 0});
        ships.add(new BattleShip(coords));
        coords.clear();
        coords.add(new int[]{7, 2});
        coords.add(new int[]{7, 3});
        coords.add(new int[]{7, 4});
        coords.add(new int[]{7, 5});
        ships.add(new BattleShip(coords));
        coords.clear();

        return ships;
    }


}
