package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Projectiles.ProjectileData;
import com.lyrenhex.Projectiles.ProjectileDataHolder;

public class EnemyCollegeState {
    public int numBoats;

    public boolean invulnerable;

    public Vector2 position;
    public String aliveTexturePath;
    public String islandTexturePath;
    public ProjectileDataHolder.Option projectileType;
    public int maxHP;
    public int HP;

    public EnemyCollegeState(int numBoats, boolean invulnerable, Vector2 position, Texture aliveTexture, Texture islandTexture, ProjectileData projectileType, int maxHP, int HP) {
        this.numBoats = numBoats;
        this.invulnerable = invulnerable;
        this.position = position;
        this.aliveTexturePath = ((FileTextureData) aliveTexture.getTextureData()).getFileHandle().path();
        this.islandTexturePath = ((FileTextureData) islandTexture.getTextureData()).getFileHandle().path();
        this.projectileType = ProjectileDataHolder.toEnum(projectileType);
        this.maxHP = maxHP;
        this.HP = HP;
    }
}
