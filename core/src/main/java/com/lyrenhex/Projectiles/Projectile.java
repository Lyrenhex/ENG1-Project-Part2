package com.lyrenhex.Projectiles;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import com.lyrenhex.GameGenerics.PhysicsObject;

import java.lang.Math;

public class Projectile extends PhysicsObject {
    
    private Vector2 velocity;
    private float speed; 
    private Sprite sprite;
    public boolean isPlayerProjectile;
    public float damage;

    /*
        @param  origin              where it should start
        @param  originRot           the rotation of the projectile
        @param  data        r        the data to use
        @param  isPlayerProjectile  true if the projectile is shot by the player
    */
    public Projectile(Vector2 origin, float originRot, ProjectileData data, boolean isPlayerProjectile) {
        position = origin;
        speed = data.speed;
        this.isPlayerProjectile = isPlayerProjectile;
        damage = data.damage;
        
        // Calculate the projectile's velocity in the game space
        velocity = new Vector2((float) Math.cos(Math.toRadians(originRot)) * speed, 
        		(float) Math.sin(Math.toRadians(originRot)) * speed);
        
        sprite = new Sprite(data.texture);
        sprite.setSize(data.size.x, data.size.y);
        sprite.setOrigin(data.size.x / 2, data.size.y / 2);
        sprite.setRotation(originRot);

        collisionPolygon = new Polygon(new float[]{data.size.x/2,0,
            data.size.x,data.size.y/2,
            data.size.x/2,data.size.y,
            0,data.size.y/2});
        collisionPolygon.setOrigin(data.size.x/2, data.size.y/2);
    }

    public Projectile(Vector2 origin, float originRot, ProjectileData data, boolean isPlayerProjectile, float damageMultiplier, float speedMultiplier) {
        position = origin;
        speed = data.speed * speedMultiplier;
        damage = data.damage * damageMultiplier;
        this.isPlayerProjectile = isPlayerProjectile;
        
        // Calculate the projectile's velocity in the game space
        velocity = new Vector2((float) Math.cos(Math.toRadians(originRot)) * speed, 
        		(float) Math.sin(Math.toRadians(originRot)) * speed);
        
        sprite = new Sprite(data.texture);
        sprite.setSize(data.size.x, data.size.y);
        sprite.setOrigin(data.size.x / 2, data.size.y / 2);
        sprite.setRotation(originRot);

        collisionPolygon = new Polygon(new float[]{data.size.x/2,0,
            data.size.x,data.size.y/2,
            data.size.x/2,data.size.y,
            0,data.size.y/2});
        collisionPolygon.setOrigin(data.size.x/2, data.size.y/2);
    }

    @Override
    public void Update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        collisionPolygon.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);
    }

    @Override
    public void Draw(SpriteBatch batch) {
        sprite.setCenter(position.x, position.y);
        sprite.draw(batch);
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        if(other.getClass() == Projectile.class)
        {
            Projectile p = (Projectile) other;
            if(p.isPlayerProjectile != isPlayerProjectile)
            {
                other.killOnNextTick = true;
                killOnNextTick = true;
            }
        }
    }
}
