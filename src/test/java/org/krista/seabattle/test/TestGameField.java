package org.krista.seabattle.test;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import org.krista.seabattle.BattleShip;
import org.krista.seabattle.Coordinate;
import org.krista.seabattle.GameField;

import java.util.Arrays;

public class TestGameField {

    @Test
    public void attackCoordTest() {
        GameField testField = new GameField();
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        testField.updateField(testShip);
        testField.attackCoord(new Coordinate(0, 1));
        assertThat(testField.findShipByCord(new Coordinate(0, 1)).hasPart(new Coordinate(0, 1))).isFalse();
    }

    @Test
    public void findShipByCoord() {
        GameField testField = new GameField();
        BattleShip testShipFirst = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        BattleShip testShipSecond = new BattleShip(Arrays.asList(new Coordinate(5, 5), new Coordinate(5, 6)));
        testField.updateField(testShipFirst);
        testField.updateField(testShipSecond);
        assertThat(testField.findShipByCord(new Coordinate(5, 5))).isEqualTo(testField.getShips().get(1));
    }
}
