package org.krista.seabattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to represent AI for placing ships
 */
public class BasicAI {
    /**
     * Mock versions of field and ships
     */
    private static int[][] mockField = new int[10][10];
    private static List<BattleShip> mockShips = new ArrayList<>();

    public static ArrayList<BattleShip> createBasicShips() {
        ArrayList<BattleShip> ships = new ArrayList<>();

        ships.add(new BattleShip((Arrays.asList(new Coordinate(9, 0)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(1, 8)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(7, 7)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(1, 4)))));

        ships.add(new BattleShip((Arrays.asList(new Coordinate(1, 2), new Coordinate(2, 2)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(5, 3), new Coordinate(5, 4)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(5, 9), new Coordinate(6, 9)))));

        ships.add(new BattleShip((Arrays.asList(new Coordinate(3, 5), new Coordinate(3, 6), new Coordinate(3, 7)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0)))));
        ships.add(new BattleShip((Arrays.asList(new Coordinate(7, 2), new Coordinate(7, 3), new Coordinate(7, 4), new Coordinate(7, 5)))));

        return ships;
    }

    /**
     * Main method to place certain types of ships certain number of times
     *
     * @return generated list of ships
     */
    public static List<BattleShip> createRandomlyGeneratedShip() {
        DeployShips(4, 1);
        DeployShips(3, 2);
        DeployShips(2, 3);
        DeployShips(1, 4);
        return mockShips;
    }

    /**
     * method to generate ships
     */
    private static void DeployShips(int numberOfDecks, int maxCountOfShips) {
        int countOfShip = 0;
        int[][] field = mockField;
        while (countOfShip < maxCountOfShips) {
            field = mockField;
            int randomX = (int) (Math.random() * 10);
            int randomY = (int) (Math.random() * 10);
            int randomDirection = (int) (Math.random() * 4);
            char direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';

            while (!checkCoords(randomX, randomY, direction, numberOfDecks) || field[randomX][randomY] == 1) {
                randomX = (int) (Math.random() * 10);
                randomY = (int) (Math.random() * 10);
                randomDirection = (int) (Math.random() * 4);
                direction = randomDirection == 0 ? 'n' : randomDirection == 1 ? 'e' : randomDirection == 2 ? 's' : 'w';
            }

            BattleShip ship = null;
            if (numberOfDecks > 1) {
                ship = new BattleShip(direction, randomX, randomY, numberOfDecks);
            } else {
                ship = new BattleShip(Arrays.asList(new Coordinate(randomX, randomY)));
            }
            countOfShip++;

            updateMockField(ship);

        }
    }

    /**
     * Method to check wether coord is valid
     *
     * @param randomX       , x
     * @param randomY,      y
     * @param direction,    direction
     * @param countOfDecks, number of decks
     * @return valid or not
     */
    private static boolean checkCoords(int randomX, int randomY, char direction, int countOfDecks) {
        int[][] field = mockField;

        if (field[randomX][randomY] == 1) {
            return false;
        }

        if (countOfDecks == 1) {
            return true;
        }

        for (int i = 0; i < countOfDecks; i++) {
            switch (direction) {
                case 'n':
                    randomY -= 1;
                    if (randomY < 0) return false;
                    if (field[randomX][randomY] == 1) return false;
                    break;
                case 'e':
                    randomX += 1;
                    if (randomX > 9) return false;
                    if (field[randomX][randomY] == 1) return false;
                    break;
                case 's':
                    randomY += 1;
                    if (randomY > 9) {
                        return false;
                    }
                    if (field[randomX][randomY] == 1) return false;
                    break;
                case 'w':
                    randomX -= 1;
                    if (randomX < 0) return false;
                    if (field[randomX][randomY] == 1) return false;
                    break;
                default:
                    System.out.println("Some thing is wrong!");
                    break;
            }
        }
        return true;
    }

    /**
     * Update mock field and ships
     *
     * @param ship
     */
    private static void updateMockField(BattleShip ship) {
        mockShips.add(ship);
        for (Coordinate coord : ship.getShipParts()) {
            mockField[coord.getX()][coord.getY()] = 1;
        }
    }

}
