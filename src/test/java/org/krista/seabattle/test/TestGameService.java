package org.krista.seabattle.test;

import org.junit.Test;
import org.krista.seabattle.models.BattleShip;
import org.krista.seabattle.models.Coordinate;
import org.krista.seabattle.services.GameService;
import org.krista.seabattle.utility.BasicAI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class TestGameService {

    @Test
    public void checkShipValidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int[][] testField = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testField[i][j] = 0;
            }
        }
        BattleShip shipTest = new BattleShip(Arrays.asList(new Coordinate(5, 4), new Coordinate(5, 5), new Coordinate(5, 6)));
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class,int[][].class);
        checkShip.setAccessible(true);
        assertThat((boolean) checkShip.invoke(new GameService(), shipTest,testField)).isTrue();
    }

    @Test
    public void checkShipInvalidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int[][] testField = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testField[i][j] = 0;
            }
        }
        BattleShip shipTest = new BattleShip(Arrays.asList(new Coordinate(5, 4), new Coordinate(6, 5), new Coordinate(5, 6)));
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class,int[][].class);
        checkShip.setAccessible(true);
        assertThat((boolean) checkShip.invoke(new GameService(), shipTest,testField)).isFalse();
    }

    @Test
    public void aiPlacementShipsTest() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        int[][] testField = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                testField[i][j] = 0;
            }
        }
        BasicAI aiTest = new BasicAI();
        Method checkShip = GameService.class.getDeclaredMethod(
                "checkShip", BattleShip.class,int[][].class);
        checkShip.setAccessible(true);
        boolean valid = true;
        for (BattleShip shipTest : aiTest.getShips()) {
            if (!((boolean) checkShip.invoke(new GameService(), shipTest,testField))) {
                valid = false;
                break;
            }
        }
        assertThat(valid).isTrue();
    }
}
