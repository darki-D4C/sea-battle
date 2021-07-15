package org.krista.seabattle;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.krista.seabattle.Position.HORIZONTAL;
import static org.krista.seabattle.Position.VERTICAL;

/**
 * Class to represent AI for placing ships.
 */
public class BasicAI {
    /**
     * Mock versions of field and ships.
     */
    private final int[][] mockField;
    private final List<BattleShip> mockShips;
    private final Random rand = SecureRandom.getInstanceStrong();

    public BasicAI() throws NoSuchAlgorithmException {
        this.mockField = new int[10][10];
        this.mockShips = new ArrayList<>();
        createRandomlyGeneratedShip();
    }


    /**
     * Main method to place certain types of ships certain number of times.
     */
    public void createRandomlyGeneratedShip() {
        deployShips(1, 4);
        deployShips(4, 1);
        deployShips(3, 2);
        deployShips(2, 3);
    }

    /**
     * Method to generate ships.
     */
    private void deployShips(int numberOfDecks, int maxCountOfShips) {
        int countOfShip = 0;
        int[][] field;
        while (countOfShip < maxCountOfShips) {
            field = mockField;
            int randomX =  this.rand.nextInt(9);
            int randomY =  this.rand.nextInt(9);
            int randomDirection =  this.rand.nextInt(2);
            Position direction = randomDirection == 0 ? HORIZONTAL : VERTICAL;

            while (!checkCoords(randomX, randomY, direction, numberOfDecks) || field[randomX][randomY] == 1) {
                randomX =  this.rand.nextInt(9);
                randomY =  this.rand.nextInt(9);
                randomDirection =  this.rand.nextInt(3);
                direction = randomDirection == 0 ? HORIZONTAL : VERTICAL;
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
    private boolean checkCoords(int randomX, int randomY, Position direction, int countOfDecks) {
        int[][] field = mockField;

        if (field[randomX][randomY] == 1) {
            return false;
        }

        if (countOfDecks == 1) {
            return true;
        }

        for (int i = 0; i < countOfDecks; i++) {
            if (direction == VERTICAL) {
                randomY += 1;
                if (randomY > 9) return false;
                if (field[randomX][randomY] == 1) return false;
            }
            if(direction == HORIZONTAL){
                    randomX += 1;
                    if (randomX > 9) return false;
                    if (field[randomX][randomY] == 1) return false;
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
