package com.lyrenhex.Saves;

import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Boats.Blessing;
import com.lyrenhex.Colleges.EnemyCollege;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestsRunner.class)
public class EnemyCollegeStateTest {

    eng1game game;
    ArrayList<EnemyCollegeState> states;

    @Before
    public void setUp() {
        game = new eng1game();
        states = SaveUtil.getState().getEnemyColleges();
    }

    @Test
    public void loadEnemyCollegeStates() {
        for (EnemyCollegeState s : states) {
            assertNotEquals(null, s);
        }
    }

    @Test
    public void loadEnemyColleges() {
        EnemyCollegeState college;
        for (EnemyCollegeState s : states) {
            college = new EnemyCollege(game.getGameScreen(), s).getSaveState(s.numBoats);
            assertEquals(s.aliveTexturePath, college.aliveTexturePath);
            assertEquals(s.HP, college.HP);
            assertEquals(s.islandTexturePath, college.islandTexturePath);
            assertEquals(s.maxHP, college.maxHP);
            assertEquals(s.invulnerable, college.invulnerable);
            assertEquals(s.position.x, college.position.x, 0.0);
            assertEquals(s.position.y, college.position.y, 0.0);
        }
    }
}
