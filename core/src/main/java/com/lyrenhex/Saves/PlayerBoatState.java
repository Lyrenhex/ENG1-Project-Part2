package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.Projectiles.ProjectileData;
import com.lyrenhex.Projectiles.ProjectileDataHolder;

public class PlayerBoatState {
    public float projectileDamageMultiplier;
    public float projectileSpeedMultiplier;

    public int defense;

    public boolean hasExtraCannons;
    public boolean isImmune;
    public float timeImmune;

    public ProjectileDataHolder.Option projectileType;

    public int HP;
    public int maxHP;
    public float speed;
    public float turnSpeed;
    public Vector2 position;

    public String texturePath;

    public PlayerBoatState(int HP, int maxHP, float speed, float turnSpeed, int defense, Vector2 position, float projectileDamageMultiplier, float projectileSpeedMultiplier, boolean hasExtraCannons, boolean isImmune, float timeImmune, ProjectileData projectileType, Texture texture) {
        this.HP = HP;
        this.maxHP = maxHP;
        this.speed = speed;
        this.turnSpeed = turnSpeed;
        this.defense = defense;
        this.position = position;
        this.projectileDamageMultiplier = projectileDamageMultiplier;
        this.projectileSpeedMultiplier = projectileSpeedMultiplier;
        this.hasExtraCannons = hasExtraCannons;
        this.isImmune = isImmune;
        this.timeImmune = timeImmune;
        this.projectileType = ProjectileDataHolder.toEnum(projectileType);
        this.texturePath = ((FileTextureData) texture.getTextureData()).getFileHandle().path();
    }
}
