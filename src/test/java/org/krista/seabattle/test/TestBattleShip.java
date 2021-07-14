package org.krista.seabattle.test;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.krista.seabattle.BattleShip;
import org.krista.seabattle.Coordinate;

import java.util.Arrays;


public class TestBattleShip {

    @Test
    public void damageShipTestActual() {
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        testShip.damageShip(new Coordinate(0, 1));
        assertThat(testShip.getNumberOfDecks()).isEqualTo(1);
    }

    @Test
    public void damageShipTestActualNotExist() {
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        testShip.damageShip(new Coordinate(1, 1));
        assertThat(testShip.getNumberOfDecks()).isEqualTo(2);
    }

    @Test
    public void hasPartTestTrue() {
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        assertThat(testShip.hasPart(new Coordinate(0, 1))).isTrue();
    }

    @Test
    public void hasPartTestFalse() {
        BattleShip testShip = new BattleShip(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 1)));
        assertThat(testShip.hasPart(new Coordinate(0, 2))).isFalse();
    }


}
