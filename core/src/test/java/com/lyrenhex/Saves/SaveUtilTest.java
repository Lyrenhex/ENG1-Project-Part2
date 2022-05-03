package com.lyrenhex.Saves;

import com.lyrenhex.GdxTestsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class SaveUtilTest {

    @Test
    public void loadSavedData() {
        assertNotEquals(null, SaveUtil.getState());
    }
}