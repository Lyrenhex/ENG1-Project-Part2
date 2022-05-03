package com.lyrenhex.Saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Obstacles.ChoppyWaves;
import com.lyrenhex.Obstacles.Obstacle;
import com.lyrenhex.Obstacles.Storm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class StormStateTest {

    eng1game game;
    PlayerBoatState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getPlayer();
    }

    @Test
    public void loadStormState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadStorm() {
        StormState storm = new Storm(game.getGameScreen(), state.position).getSaveState();
        assertEquals(state.position.x, storm.position.x, 0.0);
        assertEquals(state.position.y, storm.position.y, 0.0);
    }
}
