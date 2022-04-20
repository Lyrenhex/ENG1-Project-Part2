package com.lyrenhex.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to store data defining each type of projectile which may be found in the game.
 */
public class ProjectileData {
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
