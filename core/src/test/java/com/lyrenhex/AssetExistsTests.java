package com.lyrenhex;


import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class AssetExistsTests {
    private void assetTest(String path) {
        assertTrue(path + " missing", Gdx.files.internal(path).exists());
    }

    @Test
    public void AudioAssetsTest() {
        assetTest("audio/splash/burp.wav");
        assetTest("audio/splash/ding.mp3");
    }

    @Test
    public void FontAssetsTest() {
        assetTest("fonts/bobcat.fnt");
        assetTest("fonts/bobcat.png");
    }

    @Test
    public void ImageAssetsTest() {
        assetTest("img/boat1.png");
        assetTest("img/boat2.png");
        assetTest("img/boat_neutral.png");
        assetTest("img/cannonball.png");
        assetTest("img/castle1.png");
        assetTest("img/castle2.png");
        assetTest("img/castle3.png");
        assetTest("img/castle4.png");
        assetTest("img/castle5.png");
        assetTest("img/castle6.png");
        assetTest("img/castle7.png");
        assetTest("img/castle8.png");
        assetTest("img/castle9.png");
        assetTest("img/castle10.png");
        assetTest("img/grass.png");
        assetTest("img/island.png");
        assetTest("img/water1.png");
        assetTest("img/water2.png");
        assetTest("img/water3.png");
        assetTest("img/longboi.png");
        assetTest("img/duck.png");
        assetTest("img/CharlieJeffery.png");
        assetTest("img/boosterOff.png");
        assetTest("img/boosterOn.png");
        assetTest("img/choppyWater1.png");
        assetTest("img/choppyWater2.jpg");
    }
    
    @Test
    public void MarioAssetsTest() {
        assetTest("mario/full.png");
        assetTest("mario/mario_0.png");
        assetTest("mario/mario_1.png");
        assetTest("mario/mario_2.png");
        assetTest("mario/mario_3.png");
        assetTest("mario/yanderedev.jpg");
    }
}
