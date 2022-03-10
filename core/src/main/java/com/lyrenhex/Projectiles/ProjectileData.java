package com.lyrenhex.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ProjectileData {
	// This class is used to define each type of projectile found in the game
	public float speed; // This is the scalar speed of the object, not the velocity
	public float damage;
	public Vector2 size;
	public Texture texture;

	public ProjectileData(float speed, float damage, Vector2 size, Texture texture)
	{
		this.speed = speed;
		this.damage = damage;
		this.size = size;
		this.texture = texture;
	}
}
