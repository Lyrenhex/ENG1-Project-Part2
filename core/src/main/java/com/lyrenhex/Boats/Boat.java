package com.lyrenhex.Boats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

/**
 * Abstract class to implement basic features applicable to all Boats (Player and AI).
 */
public abstract class Boat extends PhysicsObject {
    GameController controller;
    
    // Boat stats
    public int HP;
    public int maxHP;
    protected float speed;
    protected float turnSpeed;

    protected float shotDelay = 0.5f;
    protected float timeSinceLastShot = 0f;

    protected Array<Vector2> mapBounds;
    protected Vector2 mapSize;
    
    public Boat() {
        sprite = new Sprite(new Texture(Gdx.files.internal("img/boosterOff.png")));
        sprite.setSize(100, 50);
        sprite.setOrigin(50, 25);
        sprite.setCenter(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        collisionPolygon = new Polygon(new float[]{0,0,0,68,25,100,50,68,50,0});
        
        position = new Vector2();
    }
    
    public abstract void Update(float delta);
    
    /**
     * Generic move method for boats to move forward by their set speed, and a multiplier
     *
     * @param    delta        time since last frame
     * @param    multiplier    multiplier to set forward or reverse motion (1 or -1)
     */
    void Move(float delta, int multiplier) {
        // Convention: 0 degrees means the object is pointing right, positive angles are counter-clockwise
        Vector2 oldPos = position.cpy();
        position.x += Math.cos(Math.toRadians(rotation)) * speed * delta * multiplier;
        position.y += Math.sin(Math.toRadians(rotation)) * speed * delta * multiplier;
        
        sprite.setPosition(position.x, position.y);
        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(25,50);

        if(!Intersector.isPointInPolygon(mapBounds, new Vector2(position.x + GetCenterX(), position.y+GetCenterY())))
        {
            position = oldPos.cpy();
        }
    }
    
    /**
     * Turns the boat by its turn speed, in the direction specified by multiplier.
     * A positive multiplier will turn the boat anti-clockwise, and negative clockwise.
     *
     * @param    delta        time since last frame
     * @param    multiplier    turn anti-clockwise if +ve, clockwise if -ve
     */
    void Turn(float delta, float multiplier) {
        rotation = (rotation + turnSpeed * delta * multiplier) % 360;
        sprite.setRotation(rotation);
        collisionPolygon.setRotation(rotation - 90);
    }

    /**
     * Fires a cannonball.
     */
    abstract void Shoot();

    /**
     * Destroys the boat.
     */
    abstract void Destroy();

    /**
     * Set the boat's position in global space.
     *
     * @param    x    the x-coordinate of the boat
     * @param    y    the y-coordinate of the boat
     */
    public void SetPosition(float x, float y) {
        position.x = x;
        position.y = y;
        sprite.setPosition(x, y);
    }

    @Override
    public void Draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    /**
     * Returns the x-coordinate of the center of the boat.
     *
     * @return the x-coordinate of the center of the boat.
     */
    public float GetCenterX()
    {
        return sprite.getOriginX();
    }

    /**
     * Returns the y-coordinate of the center of the boat.
     *
     * @return the y-coordinate of the center of the boat.
     */
    public float GetCenterY()
    {
        return sprite.getOriginY();
    }
}
