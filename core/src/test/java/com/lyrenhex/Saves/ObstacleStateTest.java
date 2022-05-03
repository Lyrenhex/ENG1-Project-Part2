package com.lyrenhex.Saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Obstacles.ChoppyWaves;
import com.lyrenhex.Obstacles.Obstacle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class ObstacleStateTest {

    eng1game game;
    ArrayList<ObstacleState> states;

    @Before
    public void setUp() {
        game = new eng1game();
        states = SaveUtil.getState().getObstacles();
    }

    @Test
    public void loadObstacleStates() {
        for (ObstacleState s : states) {
            assertNotEquals(null, s);
        }
    }

    @Test
    public void loadObstacles() {
        ObstacleState os;
        for (ObstacleState s : states) {
            os = new Obstacle(game.getGameScreen(), s.position, new Texture(Gdx.files.internal(s.texturePath))).getSaveState();
            assertEquals(s.texturePath, os.texturePath);
            assertEquals(s.position.x, os.position.x, 0.0);
            assertEquals(s.position.y, os.position.y, 0.0);
        }
    }
}
