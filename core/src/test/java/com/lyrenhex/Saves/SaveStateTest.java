package com.lyrenhex.Saves;

import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class SaveStateTest {

    eng1game game;
    SaveState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState();
    }

    @Test
    public void loadSaveState() {
        assertNotEquals(null, state);
    }
}
