package com.lyrenhex;


import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class ChoppyWavesTests {
    @Test
    public void testInsideChoppyWaves {
        eng1game game = new eng1game();
        ChoppyWaves c = new ChoppyWaves(0,0);
        game.addPhysicsObject(c);
        Assert.assertNotEquals(game.gameScreen.getPlayerBoat().getRotation(),-90)
    }
    @Test
    public void testOutsideChoppyWaves {
        eng1game game;
        Assert.assertEquals(game.gameScreen.getPlayerBoat().getRotation(),-90)
    }
}
