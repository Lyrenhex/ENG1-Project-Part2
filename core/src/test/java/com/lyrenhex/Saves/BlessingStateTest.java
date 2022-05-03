package com.lyrenhex.Saves;

import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Boats.Blessing;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class BlessingStateTest {

    eng1game game;
    BlessingState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getBlessing();
    }

    @Test
    public void loadBlessingState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadBlessing() {
        BlessingState blessing = new Blessing(game.getGameScreen(), state.position, new Vector2(3000, 3000)).getSaveState();
        assertEquals(state.position.x, blessing.position.x, 0.0);
        assertEquals(state.position.y, blessing.position.y, 0.0);
    }
}
