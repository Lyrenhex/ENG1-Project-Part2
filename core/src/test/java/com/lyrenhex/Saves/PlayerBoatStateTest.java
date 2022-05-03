package com.lyrenhex.Saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Boats.PlayerBoat;
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
public class PlayerBoatStateTest {

    eng1game game;
    PlayerBoatState state;

    @Before
    public void setUp() {
        game = new eng1game();
        state = SaveUtil.getState().getPlayer();
    }

    @Test
    public void loadPlayerState() {
        assertNotEquals(null, state);
    }

    @Test
    public void loadPlayer() {
        PlayerBoatState player = new PlayerBoat(game.getGameScreen(), state, new Vector2(3000, 3000)).getSaveState();
        assertEquals(state.HP, player.HP);
        assertEquals(state.defense, player.defense);
        assertEquals(state.maxHP, player.maxHP);
        assertEquals(state.projectileDamageMultiplier, player.projectileDamageMultiplier, 0.0);
        assertEquals(state.projectileSpeedMultiplier, player.projectileSpeedMultiplier, 0.0);
        assertEquals(state.speed, player.speed, 0.0);
        assertEquals(state.timeImmune, player.timeImmune, 0.0);
        assertEquals(state.turnSpeed, player.turnSpeed, 0.0);
        assertEquals(state.hasExtraCannons, player.hasExtraCannons);
        assertEquals(state.isImmune, player.isImmune);
        assertEquals(state.position.x, player.position.x, 0.0);
        assertEquals(state.position.y, player.position.y, 0.0);
    }
}
