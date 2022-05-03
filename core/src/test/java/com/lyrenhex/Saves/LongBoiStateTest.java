package com.lyrenhex.Saves;

import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Obstacles.ChoppyWaves;
import com.lyrenhex.Obstacles.LongBoi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class LongBoiStateTest {

    eng1game game;
    LongBoiState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getLongBoi();
    }

    @Test
    public void loadLongBoiState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadLongBoi() {
        LongBoiState longBoi = new LongBoi(game.getGameScreen(), state).getSaveState();
        assertEquals(state.position.x, longBoi.position.x, 0.0);
        assertEquals(state.position.y, longBoi.position.y, 0.0);
        assertEquals(state.HP, longBoi.HP);
    }
}
