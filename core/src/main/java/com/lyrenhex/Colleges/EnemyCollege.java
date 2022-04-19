package com.lyrenhex.Colleges;

import java.util.Random;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.Projectiles.Projectile;
import com.lyrenhex.Projectiles.ProjectileData;

public class EnemyCollege extends College{
   
    int damage;
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
    public boolean invulnerable;
   
    public EnemyCollege(Vector2 position, Texture aliveTexture, Texture islandTexture,
                        GameController controller, ProjectileData projectileData, int maxHP)
    {
        aliveSprite = new Sprite(aliveTexture);
        aliveSprite.setPosition(position.x, position.y);
        aliveSprite.setSize(100, 100);
        deadSprite = new Sprite(new Texture(Gdx.files.internal("img/castle10.png")));
        deadSprite.setPosition(position.x, position.y);
        deadSprite.setSize(100, 100);
        islandSprite = new Sprite(islandTexture);
        islandSprite.setCenter(aliveSprite.getX()+5, aliveSprite.getY()+5);
        islandSprite.setSize(120, 120);
        this.position = position;
        range = 500;
        gc = controller;
        projectileType = projectileData;
        collisionPolygon = new Polygon(new float[]{0,0,100,0,100,100,0,100});
        collisionPolygon.setPosition(position.x, position.y);
        this.maxHP = maxHP;
        HP = maxHP;
        font = new BitmapFont(Gdx.files.internal("fonts/bobcat.fnt"), false);
        hpText = new GlyphLayout();
        hpText.setText(font, HP + "/" + maxHP);
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        if(other.getClass() == Projectile.class && HP>0)
        {//if the enemycollege is hit by a projectile
            Projectile p = (Projectile) other;
            if(p.isPlayerProjectile)
            { // if its a player projectile
                p.killOnNextTick = true;
                if(!invulnerable)
                {
                    HP -= p.damage;
                    hpText.setText(font, HP + "/" + maxHP);
                    if(HP <= 0)
                        gc.CollegeDestroyed();
                }
                else
                    hpText.setText(font, "RESISTED, destroy other colleges first!");
            }
        }
    }    

    public void Update(float delta, PhysicsObject playerBoat)
    {
        if(HP > 0)
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
        islandSprite.draw(batch);
        if(HP > 0)
        {
            aliveSprite.draw(batch);
            font.draw(batch, hpText, aliveSprite.getWidth()/2 + position.x - hpText.width/2, position.y - hpText.height/2);
        }
        else
            deadSprite.draw(batch);
    }

    void ShootAt(Vector2 target)
    {
        float shotAngle = (float) Math.toDegrees(Math.atan2(target.y - (position.y + aliveSprite.getHeight()/2),
                                                            target.x - (position.x + aliveSprite.getWidth()/2)));
        //calculate the shot angle by getting a vector from the centre of the college to the target
        //convert to degrees for the inaccuracy calculation
        shotAngle += (rd.nextFloat() * shootingInaccuracy * 2) - (shootingInaccuracy);
        //inaccuracy calculation works by rd.nextfloat() gets a pseudorandom float from 0-1
        // we multiply it by 2* the shooting inaccuracy to get the right width of distribution
        // then - the shooting inaccuracy to centre the distribution on 0
        gc.NewPhysicsObject(new Projectile(new Vector2(position.x + aliveSprite.getWidth()/2,
                                                       position.y + aliveSprite.getHeight()/2),
                            shotAngle, projectileType, false, this));
        //instantiate a new bullet and pass a reference to the gamecontroller so it can be updated and drawn
    }

    public void becomeVulnerable()
    {
        invulnerable = false;
        hpText.setText(font, HP + "/" + maxHP);
    }

}
