package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Projectiles.ProjectileData;

public class EnemyCollegeState {
    public int numBoats;

    public boolean invulnerable;

    public Vector2 position;
    public Texture aliveTexture;
    public Texture islandTexture;
    public ProjectileData projectileType;
    public int maxHP;
    public int HP;

    public EnemyCollegeState(int numBoats, boolean invulnerable, Vector2 position, Texture aliveTexture, Texture islandTexture, ProjectileData projectileType, int maxHP, int HP) {
        this.numBoats = numBoats;
        this.invulnerable = invulnerable;
        this.position = position;
        this.aliveTexture = aliveTexture;
        this.islandTexture = islandTexture;
        this.projectileType = projectileType;
        this.maxHP = maxHP;
        this.HP = HP;
    }
}
