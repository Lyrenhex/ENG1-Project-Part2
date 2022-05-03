package com.lyrenhex.Saves;

import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Obstacles.ChoppyWaves;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class ChoppyWavesStateTest {

    eng1game game;
    ChoppyWavesState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getChoppyWaves();
    }

    @Test
    public void loadChoppyWavesState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadChoppyWaves() {
        ChoppyWavesState choppyWaves = new ChoppyWaves(state.position).getSaveState();
        assertEquals(state.position.x, choppyWaves.position.x, 0.0);
        assertEquals(state.position.y, choppyWaves.position.y, 0.0);
    }
}
