package org.krista.seabattle.utility;

import org.krista.seabattle.models.BattleShip;
import org.krista.seabattle.models.Coordinate;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class to represent AI for placing ships.
 */
public class BasicAI {
    /**
     * Mock versions of field and ships.
     */
    private final int[][] mockField;
    private final List<BattleShip> mockShips;
    private Random rand;

    public BasicAI() {
        this.mockField = new int[10][10];
        this.mockShips = new ArrayList<>();
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            rand = new Random();
        }
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
            int randomX = this.rand.nextInt(9);
            int randomY = this.rand.nextInt(9);
            int randomDirection = this.rand.nextInt(2);
            Position direction = randomDirection == 0 ? Position.HORIZONTAL : Position.VERTICAL;

            while (!checkCoords(randomX, randomY, direction, numberOfDecks) || field[randomX][randomY] == 1) {
                randomX = this.rand.nextInt(9);
                randomY = this.rand.nextInt(9);
                randomDirection = this.rand.nextInt(3);
                direction = randomDirection == 0 ? Position.HORIZONTAL : Position.VERTICAL;
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
        //Check if first tile is free
        if (field[randomX][randomY] == 1) {
            return false;
        }

        //check if ship with one deck doesnt have other ship in one tile proximity
        if (countOfDecks == 1 && checkTileAround(randomX,randomY,"single")) {
            return false;
        }


        for (int i = 0; i < countOfDecks; i++) {
            //check for vertical
            if (direction == Position.VERTICAL) {
                //check if vertical first tile doesnt have other ships tile on top,right or left
                if (i == 0 && checkTileAround(randomX, randomY, "verticalHighMost")) {
                    return false;
                }

                //check for out of bounds
                randomY += 1;
                if (randomY > 9) return false;

                //check if vertical tile doesnt have other ships tile on right or left
                if (checkTileAround(randomX,randomY,"vertical")) {
                    return false;
                }
                //check if vertical last tile doesnt have other ships tile on bottom,right or left
                if (i == countOfDecks - 1 && checkTileAround(randomX, randomY, "verticalDownMost")) {
                    return false;
                }

                //check if current tile is free
                if (field[randomX][randomY] == 1) return false;

            }
            //check for horizontal
            if (direction == Position.HORIZONTAL) {
                //check if horizontal first tile doesnt have other ships tile on top,right or bottom
                if (i == 0 && checkTileAround(randomX, randomY, "horizontalLeftMost")) {
                    return false;
                }

                //check for out of bounds
                randomX += 1;
                if (randomX > 9) return false;

                //check if horizontal tile doesnt have other ships tile on top or bottom
                if (checkTileAround(randomX, randomY, "horizontal")) {
                    return false;
                }

                //check if horizontal last tile doesnt have other ships tile on top,left or bottom
                if (i == countOfDecks - 1 && checkTileAround(randomX, randomY, "horizontalRightMost")) {
                    return false;
                }

                //check if current tile is free
                if (field[randomX][randomY] == 1) return false;
            }
        }
        return true;
    }

    private boolean checkTileAround(int randomX, int randomY, String tilePosition) {
        int[][] field = mockField;

        switch (tilePosition) {
            case "single" :
                return ((randomY - 1 <= 0) || field[randomX][randomY - 1] == 1) &&
                        ((randomX - 1 <= 0) || field[randomX - 1][randomY] == 1) &&
                        ((randomX + 1 >= 10) || field[randomX + 1][randomY] == 1) &&
                        ((randomY + 1 >= 10) || field[randomX][randomY + 1] == 1);
            case "horizontalLeftMost":
                return ((randomY - 1 > 0) && field[randomX][randomY - 1] == 1) ||
                        ((randomX - 1 > 0) && field[randomX - 1][randomY] == 1) ||
                        ((randomY + 1 < 10) && field[randomX][randomY + 1] == 1);
            case "horizontalRightMost":
                return ((randomY - 1 > 0) && field[randomX][randomY - 1] == 1) ||
                        ((randomX + 1 < 10) && field[randomX + 1][randomY] == 1) ||
                        ((randomY + 1 < 10) && field[randomX][randomY + 1] == 1);
            case "horizontal":
                return ((randomY - 1 > 0) && field[randomX][randomY - 1] == 1) ||
                        ((randomY + 1 < 10) && field[randomX][randomY + 1] == 1);
            case "verticalHighMost":
                return ((randomY - 1 > 0) && field[randomX][randomY - 1] == 1) ||
                        ((randomX - 1 > 0) && field[randomX - 1][randomY] == 1) ||
                        ((randomX + 1 < 10) && field[randomX + 1][randomY] == 1);
            case "verticalDownMost":
                return ((randomY + 1 < 10) && field[randomX][randomY - 1] == 1) ||
                        ((randomX - 1 > 0) && field[randomX - 1][randomY] == 1) ||
                        ((randomX + 1 < 10) && field[randomX + 1][randomY] == 1);
            case "vertical":
                return ((randomX - 1 > 0) && field[randomX - 1][randomY] == 1) ||
                        ((randomX + 1 < 10) && field[randomX + 1][randomY] == 1);
            default:
                return true;
        }

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
