package org.krista.seabattle;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;

@SessionScoped
public class GameService implements Serializable {

    private GameField playerField = new GameField();
    private GameField serverField = new GameField();
    private ArrayList<Coordinate> alreadyAttackedByServer = new ArrayList<>();

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
            for (Coordinate coord : ship.getRemainingShipParts()) {
                if (!(checkField[coord.getX()][coord.getY()] == 1)) {
                    checkField[coord.getX()][coord.getY()] = 1;
                } else return false;
            }
        }
        return true;
    }

    private boolean checkShip(BattleShip ship) {


        int[][] field = playerField.getField();
        List<Coordinate> coords = ship.getRemainingShipParts();

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



    public JSONArray attackPlayerField() {
        boolean serverTurn = true;
        JSONArray serverTurnActions = new JSONArray();
        Coordinate coordToAttack;
        while(serverTurn){
            coordToAttack = generateCoord();
            if(getPlayerField().getField()[coordToAttack.getX()][coordToAttack.getY()] == 1){
                getPlayerField().attackCoord(coordToAttack);
                BattleShip foundShip = getPlayerField().findShipByCord(coordToAttack);
                if(foundShip.getNumberOfDecks()==0){
                    serverTurnActions = JsonCreator.addInfoAboutDestroyedShip(serverTurnActions,foundShip);
                }
                serverTurnActions = JsonCreator.addInfoAboutDestroyedCord(serverTurnActions,coordToAttack);


            }else{
                serverTurn = false;
                //Добавить оповещение о победе
            }
        }
        if(serverTurnActions.isEmpty()){
            return JsonCreator.returnInfoAboutCompleteMiss();
        }
        return serverTurnActions;
    }

    public Coordinate generateCoord(){
        int randomX = (int) (Math.random() * 10);
        int randomY = (int) (Math.random() * 10);
        Coordinate pickedCord = new Coordinate(randomX,randomY);
        while((getAttackedCoordsByServer().contains(pickedCord))){
            randomX = (int) (Math.random() * 10);
            randomY = (int) (Math.random() * 10);
            pickedCord = new Coordinate(randomX,randomY);
        }
        return pickedCord;
    }

    public List<Coordinate> getAttackedCoordsByServer(){
        return alreadyAttackedByServer;
    }




}
