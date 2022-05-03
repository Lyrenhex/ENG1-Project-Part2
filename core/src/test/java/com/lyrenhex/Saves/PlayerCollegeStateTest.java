package com.lyrenhex.Saves;

import com.lyrenhex.Colleges.PlayerCollege;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class PlayerCollegeStateTest {

    eng1game game;
    PlayerCollegeState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getPlayerCollege();
    }

    @Test
    public void loadPlayerCollegeState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadPlayerCollege() {
        PlayerCollegeState player = new PlayerCollege(state).getSaveState();
        assertEquals(state.aliveTexturePath, player.aliveTexturePath);
        assertEquals(state.islandTexturePath, player.islandTexturePath);
        assertEquals(state.position.x, player.position.x, 0.0);
        assertEquals(state.position.y, player.position.y, 0.0);
    }
}
