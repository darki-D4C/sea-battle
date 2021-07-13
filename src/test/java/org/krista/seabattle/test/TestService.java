package org.krista.seabattle.test;

import org.junit.Test;
import org.krista.seabattle.BasicAI;
import org.krista.seabattle.BattleShip;
import org.krista.seabattle.GameService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class TestService {

    @Test
    public void aiPlacementSizeTest() {
        BasicAI aiTest = new BasicAI();
        assertThat(aiTest.getShips().size()).isEqualTo(10);
    }

    @Test
    public void aiPlacementShipsTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
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