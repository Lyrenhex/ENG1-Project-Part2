package com.lyrenhex.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to hold an instance of each projectile type in the game, avoiding unnecessary ProjectileData objects
 * when one would suffice.
 */
public  class ProjectileDataHolder {
    public ProjectileData stock;
    public ProjectileData enemy;
    public ProjectileData boss;
    public ProjectileData duck;

    public ProjectileDataHolder() {
        stock = new ProjectileData(250, 20, new Vector2(20,20),
                new Texture("img/cannonball.png"));
        boss = new ProjectileData(300, 20, new Vector2(20,20),
                new Texture("img/cannonball.png"));
        duck = new ProjectileData(250, 30, new Vector2(20, 20),
                new Texture("img/duck.png"));
    }
}
