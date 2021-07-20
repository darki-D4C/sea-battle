package org.krista.seabattle.test;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import org.krista.seabattle.models.BattleShip;
import org.krista.seabattle.models.Coordinate;
import org.krista.seabattle.models.GameField;

import java.util.Arrays;

public class TestGameField {

    @Test
    public void attackCoordTestActual() {
        GameField testField = new GameField();
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        testField.updateField(testShip);
        testField.attackCoord(new Coordinate(0, 1));
        assertThat(testField.findShipByCord(new Coordinate(0, 1)).hasPart(new Coordinate(0, 1))).isFalse();
    }

    @Test
    public void attackCoordTestActualNotExist() {
        GameField testField = new GameField();
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        testField.updateField(testShip);
        testField.attackCoord(new Coordinate(1, 1));
        assertThat(testField.findShipByCord(new Coordinate(0,1)).getNumberOfDecks()).isEqualTo(2);
    }

    @Test
    public void findShipByCoordHas() {
        GameField testField = new GameField();
        BattleShip testShipFirst = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        BattleShip testShipSecond = new BattleShip(Arrays.asList(new Coordinate(5, 5), new Coordinate(5, 6)));
        testField.updateField(testShipFirst);
        testField.updateField(testShipSecond);
        assertThat(testField.findShipByCord(new Coordinate(5, 5))).isEqualTo(testField.getShips().get(1));
    }

    @Test
    public void findShipByCoordDoesntHave() {
        GameField testField = new GameField();
        BattleShip testShipFirst = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        BattleShip testShipSecond = new BattleShip(Arrays.asList(new Coordinate(5, 5), new Coordinate(5, 6)));
        testField.updateField(testShipFirst);
        testField.updateField(testShipSecond);
        assertThat(testField.findShipByCord(new Coordinate(6, 6))).isNull();
    }
}
