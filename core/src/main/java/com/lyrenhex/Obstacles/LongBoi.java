package com.lyrenhex.Obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.Colleges.College;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.Projectiles.Projectile;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.Projectiles.ProjectileData;
import com.lyrenhex.Projectiles.ProjectileDataHolder;
import com.lyrenhex.Saves.LongBoiState;

/**
 * Class for the Long Boi world event, in which the player may defeat the neutral Long Boi in exchange for a damage
 * upgrade and the ability to fire ducks rather than cannonballs.
 */
public class LongBoi extends College {

    float shootingInaccuracy = 10f; // in degrees (each side)
    float fireRate = 1.5f;
    float timeSinceLastShot = 0;
    Random rd = new Random();
    GameController gc;
    ProjectileData projectileType;
    BitmapFont font;
    GlyphLayout hpText;
    int maxHP;
    public int HP;

    int xpValue = 200;

    public LongBoi(GameController controller, Vector2 position)
    {
        range = 500;
        gc = controller;
        projectileType = ProjectileDataHolder.duck;

        this.maxHP = 400;
        this.HP = maxHP;

        this.position = position.cpy();

        sprite = new Sprite(new Texture(Gdx.files.internal("img/longBoi.png")));
        sprite.setSize(100, 100);
        sprite.setOrigin(50, 50);

        collisionPolygon = new Polygon(new float[]{0,0,100,0,100,100,0,100});
        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(50,50);
        collisionPolygon.setRotation(rotation - 90);

        sprite.setPosition(position.x, position.y);

        aliveSprite = sprite;

        font = new BitmapFont(Gdx.files.internal("fonts/bobcat.fnt"), false);
        hpText = new GlyphLayout();
        hpText.setText(font, HP + "/" + maxHP);
    }

    public LongBoi(GameController controller, LongBoiState state)
    {
        this(controller, state.position);
        this.HP = state.HP;
        hpText.setText(font, HP + "/" + maxHP);
    }

    /**
     * Returns the x-coordinate of the center of the sprite.
     *
     * @return the x-coordinate of the center of the sprite.
     */
    public float GetCenterX()
    {
        return sprite.getOriginX();
    }

    /**
     * Returns the y-coordinate of the center of the sprite.
     *
     * @return the y-coordinate of the center of the sprite.
     */
    public float GetCenterY()
    {
        return sprite.getOriginY();
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        if (other instanceof Projectile && HP > 0)
        {
            Projectile p = (Projectile) other;
            if(p.isPlayerProjectile)
            { // if it's a player projectile
                p.killOnNextTick = true;
                HP -= p.damage;
                hpText.setText(font, HP + "/" + maxHP);
                if(HP <= 0) {
                    gc.playerBoat.projectileType = ProjectileDataHolder.duck;
                    gc.xp += xpValue;
                    killOnNextTick = true;
                }
            }
        }
    }

    public void Update(float delta, PhysicsObject playerBoat)
    {
        if (HP > 0)
        {
            if(timeSinceLastShot < fireRate)
            {
                timeSinceLastShot += delta;
            } // increase the time on the timer to allow for fire rate calculation

            PlayerBoat boat = (PlayerBoat) playerBoat; //cast to playerboat
            if(isInRange(boat)) // is the player boat in range
            {
                if(timeSinceLastShot >= fireRate)
                {
                    ShootAt(new Vector2(boat.position.x, boat.position.y));
                    timeSinceLastShot = 0;
                }
            }
        }
    }

    public void Draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    /**
     * Fire a cannonball at the location specified by `target`.
     *
     * @param target the position in the game world to fire the cannonball at.
     */
    void ShootAt(Vector2 target)
    {
        float shotAngle = (float) Math.toDegrees(Math.atan2(target.y - (position.y + sprite.getHeight()/2),
                target.x - (position.x + sprite.getWidth()/2)));
        //calculate the shot angle by getting a vector from the centre of the college to the target
        //convert to degrees for the inaccuracy calculation
        shotAngle += (rd.nextFloat() * shootingInaccuracy * 2) - (shootingInaccuracy);
        //inaccuracy calculation works by rd.nextfloat() gets a pseudorandom float from 0-1
        // we multiply it by 2* the shooting inaccuracy to get the right width of distribution
        // then - the shooting inaccuracy to centre the distribution on 0
        gc.NewPhysicsObject(new Projectile(new Vector2(position.x + sprite.getWidth()/2,
                position.y + sprite.getHeight()/2),
                shotAngle, projectileType, false, this));
        //instantiate a new bullet and pass a reference to the gamecontroller so it can be updated and drawn
    }

    /**
     * Obtains a serialisable form of the current state of the object.
     * @return an object storing the state information of the object.
     */
    public LongBoiState getSaveState() {
        return new LongBoiState(position, HP);
    }
}
