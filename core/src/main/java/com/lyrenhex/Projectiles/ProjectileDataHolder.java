package com.lyrenhex.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to hold an instance of each projectile type in the game, avoiding unnecessary ProjectileData objects
 * when one would suffice.
 */
public  class ProjectileDataHolder {
    public enum Option { Stock, Enemy, Boss, Duck };

    public static ProjectileData stock;
    public static ProjectileData enemy;
    public static ProjectileData boss;
    public static ProjectileData duck;

    public ProjectileDataHolder() {
        stock = new ProjectileData(250, 20, new Vector2(20,20),
                new Texture("img/cannonball.png"));
        boss = new ProjectileData(300, 20, new Vector2(20,20),
                new Texture("img/cannonball.png"));
        duck = new ProjectileData(250, 30, new Vector2(20, 20),
                new Texture("img/duck.png"));
    }

    public static ProjectileData fromEnum(Option option) {
        switch (option) {
            case Stock:
                return stock;
            case Enemy:
                return enemy;
            case Boss:
                return boss;
            case Duck:
                return duck;
        }
        return null;
    }

    public static Option toEnum(ProjectileData option) {
        if (option == stock) return Option.Stock;
        if (option == enemy) return Option.Enemy;
        if (option == boss) return Option.Boss;
        if (option == duck) return Option.Duck;
        return null;
    }
}
