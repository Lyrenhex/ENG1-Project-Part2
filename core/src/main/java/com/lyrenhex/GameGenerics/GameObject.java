package com.lyrenhex.GameGenerics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Generic abstract class to store common details in all objects in the game.
 */
public abstract class GameObject {
    //every GameObject needs to have these values, and define these methods

    public Vector2 position;

    public float rotation = 0;
    public Sprite sprite = null;    
    public boolean killOnNextTick = false;

    /**
     * Method called on every frame to handle game logic.
     * To be defined on inheritance.
     *
     * @param  delta   time since last frame
     */
    public void Update(float delta) {}

    /**
     * Method called on every frame to handle game logic.
     * To be defined on inheritance.
     *
     * @param  delta   time since last frame
     * @param  other   the other object
     */
    public void Update(float delta, PhysicsObject other) {}

    /**
     * Method called on each frame to draw any visible game objects.
     *
     * @param batch the SpriteBatch to draw to.
     */
    public void Draw(SpriteBatch batch) {}
}
