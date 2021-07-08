package org.krista.seabattle;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class GameService implements Serializable {

    private GameField playerField = new GameField();
    private GameField serverField = new GameField();

    public GameService() {
        serverField.setFieldAndShips(BasicAI.createBasicShips());
    }

    /*
    public void placeServerShips() {
        createServerShip(4, 1);                            //generate battleship
        createServerShip(3, 2);                               //generate Cruisers
        createServerShip(2, 3);                             //generate Destroyers
        createServerShip(1, 4);
    }

    private void createServerShip(int countOfDecks, int maxCountOfShips) {
        int countOfShip = 0;
        while (countOfShip < maxCountOfShips) {
            int[][] field = serverField.getField();
            int randomX = (int) (Math.random() * 10);
            int randomY = (int) (Math.random() * 10);
            int randomDirection = (int) (Math.random() * 4);
            char direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';

            while(!checkCoords(randomX,randomY,direction,countOfDecks) && field[randomX][randomY] == 1 ){
                randomX = (int) (Math.random() * 10);
                randomY = (int) (Math.random() * 10);
                randomDirection = (int) (Math.random() * 4);
                direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';
            }

            BattleShip ship = new BattleShip(countOfDecks);
            if (countOfDecks > 1) {
                ship.setCoords(direction, randomX, randomY, countOfDecks);
            } else {
                ship.setCoords(randomX, randomY);
            }
            countOfShip++;
            //
            serverField.updateField(ship);

        }
    }

    private boolean checkCoords(int randomX, int randomY, char direction, int countOfDecks) {
        ArrayList<int[]> coords;
        int[][] field = serverField.getField();

        if(field[randomX][randomY] == 1){
            return false;
        }

        if ( countOfDecks == 1 ){
            return true;
        }

        for(int i = 0; i< countOfDecks;i++){
            switch (direction){
                case 'n':
                    if(randomY-- < 0) return false;
                    if(field[randomX][randomY]==1) return false;
                    break;
                case 'e':
                    if(randomX++ > 9) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                case 's':
                    if(randomY++ > 9) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                case 'w':
                    if(randomX-- < 0) return false;
                    if(field[randomX][randomY] == 1) return false;
                    break;
                default:
                    System.out.println("Some thing is wrong!");
            }
        }
        return true;
    }

     */

    public void placePlayerShips(List<BattleShip> ships) {
        for (BattleShip ship : ships) {
            playerField.updateField(ship);
        }
    }

    public boolean checkPlayerShips(List<BattleShip> ships) {
        // if(ships == null) return false;
        int[][] checkField = playerField.getField();
        for (BattleShip ship : ships) {
            if (!checkShip(ship)) return false;
            for (Coordinate coord : ship.getShipParts()) {
                if (!(checkField[coord.getX()][coord.getY()] == 1)) {
                    checkField[coord.getX()][coord.getY()] = 1;
                } else return false;
            }
        }
        return true;
    }

    private boolean checkShip(BattleShip ship) {


        int[][] field = playerField.getField();
        List<Coordinate> coords = ship.getShipParts();

        for (Coordinate coord : coords) {
            if (field[coord.getX()][coord.getY()] == 1) {
                return false;
            }
        }


        int x = (int) coords.get(0).getX();
        int y = (int) coords.get(0).getY();
        for (int i = 1; i < ship.getNumberOfDecks() - 1; i++) {
            if (!(coords.get(i).getX() == x || coords.get(i).getY() == y) || (coords.get(i).getX() == x && coords.get(i).getY() == y)) {
                // Если либо x либо y , не совпадают с
                // соответсвующими x y первой палубы,
                //то палуба не находится на одной линии с первой
                return false;
            }
        }
        return true;
    }

    public GameField getPlayerField() {
        return playerField;
    }

    public GameField getServerField() {
        return serverField;
    }

    public boolean checkServerCoord(Coordinate cord) {

        return serverField.getField()[cord.getX()][cord.getY()] == 1;

    }

    public void attackServerCoord(Coordinate cord) {
        getServerField().getField()[cord.getX()][cord.getY()] = 0;
        List<BattleShip> ships = serverField.getShips();
        // ships не null
        for (BattleShip ship : ships) {
            if (ship.hasPart(cord)) { // никогда не находит координату хотя она есть в каком то из обьектов BattleShip
                ship.damageShip(cord);
            }
        }



    }

    public void attackPlayerField() {
    }
}
