package com.lyrenhex.Colleges;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.GdxTestsRunner;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Projectiles.Projectile;
import com.lyrenhex.Projectiles.ProjectileData;
import com.lyrenhex.Projectiles.ProjectileDataHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

@RunWith(GdxTestsRunner.class)
public class EnemyCollegeTest extends TestCase {

    EnemyCollege testCollege;
    eng1game testGame;

    @Before
    public void init(){
        testGame = new eng1game();
        testCollege = new EnemyCollege(new Vector2(), new Texture(Gdx.files.internal("img/castle1.png")), new Texture(Gdx.files.internal("img/islandBarrier.png")), testGame.getGameScreen(), ProjectileDataHolder.stock, 200);
    }

    @Test
    public void testDamage() {
        //Save the starting health
        int startHealth = testCollege.getHP();
        PlayerBoat pb = new PlayerBoat(testGame.getGameScreen(), new Vector2(1000, 1000), new Vector2(3000, 3000));
        Projectile p = new Projectile(new Vector2(), 0, new ProjectileData(2, 100, new Vector2(10, 10), new Texture(Gdx.files.internal("img/cannonball.png"))), true, pb);
        testCollege.OnCollision(p);
        assertTrue(startHealth > testCollege.getHP());
    }
}
