package org.krista.seabattle.test;

import org.junit.Test;
import org.krista.seabattle.utility.BasicAI;
import org.krista.seabattle.classes.BattleShip;
import org.krista.seabattle.services.GameService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.*;

public class TestService {

    @Test
    public void aiPlacementSizeTest(){
        BasicAI aiTest = new BasicAI();
        assertThat(aiTest.getShips().size()).isEqualTo(10);
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
