package org.krista.seabattle.test;

import org.junit.Test;
import org.krista.seabattle.BasicAI;
import org.krista.seabattle.BattleShip;
import org.krista.seabattle.Coordinate;
import org.krista.seabattle.GameService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class TestGameService {

    @Test
    public void checkShipValidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BattleShip shipTest = new BattleShip(Arrays.asList(new Coordinate(5, 4), new Coordinate(5, 5), new Coordinate(5, 6)));
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class);
        checkShip.setAccessible(true);
        assertThat((boolean) checkShip.invoke(new GameService(), shipTest)).isTrue();
    }

    @Test
    public void checkShipInvalidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BattleShip shipTest = new BattleShip(Arrays.asList(new Coordinate(5, 4), new Coordinate(6, 5), new Coordinate(5, 6)));
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class);
        checkShip.setAccessible(true);
        assertThat((boolean) checkShip.invoke(new GameService(), shipTest)).isFalse();
    }

    @Test
    public void aiPlacementShipsTest() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        BasicAI aiTest = new BasicAI();
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class);
        checkShip.setAccessible(true);
        boolean valid = true;
        for (BattleShip shipTest : aiTest.getShips()) {
            if (!((boolean) checkShip.invoke(new GameService(), shipTest))) {
                valid = false;
                break;
            }
        }
        assertThat(valid).isTrue();
    }
}
