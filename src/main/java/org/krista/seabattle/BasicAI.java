package org.krista.seabattle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent AI for placing ships.
 */
public class BasicAI {
    /**
     * Mock versions of field and ships.
     */
    private final int[][] mockField;
    private final List<BattleShip> mockShips;

    public BasicAI() {
        this.mockField = new int[10][10];
        this.mockShips = new ArrayList<>();
        createRandomlyGeneratedShip();
    }


    /**
     * Main method to place certain types of ships certain number of times.
     */
    public void createRandomlyGeneratedShip() {
        DeployShips(1, 4);
        DeployShips(4, 1);
        DeployShips(3, 2);
        DeployShips(2, 3);
    }

    /**
     * Method to generate ships.
     */
    private void DeployShips(int numberOfDecks, int maxCountOfShips) {
        int countOfShip = 0;
        int[][] field;
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

            BattleShip ship;
            if (numberOfDecks > 1) {
                ship = new BattleShip(direction, randomX, randomY, numberOfDecks);
            } else {
                ship = new BattleShip(Collections.singletonList(new Coordinate(randomX, randomY)));
            }

            countOfShip++;

            updateMockField(ship);

        }
    }

    /**
     * Method to check whether coord is valid.
     *
     * @param randomX      x
     * @param randomY      y
     * @param direction    direction
     * @param countOfDecks number of decks
     * @return valid or not
     */
    private boolean checkCoords(int randomX, int randomY, char direction, int countOfDecks) {
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
            }
        }
        return true;
    }

    /**
     * Update mock field and ships
     *
     * @param ship ship to update field with
     */
    private void updateMockField(BattleShip ship) {
        mockShips.add(ship);
        for (Coordinate coord : ship.getShipParts()) {
            mockField[coord.getX()][coord.getY()] = 1;
        }
    }

    public List<BattleShip> getShips() {
        return mockShips;
    }
}
