package com.lyrenhex.GameGenerics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.math.Intersector;

/**
 * Abstract class to handle common features between PhysicsObjects.
 */
public abstract class PhysicsObject extends GameObject {
    //in addition to the GameObject, PhysicsObjects must also implement the following

    public Polygon collisionPolygon = null;

    /**
        Returns true if the 2 PhysicsObjects collide.

        @param  other   PhysicsObject to check collision with
        @return true if the PhysicsObjects collide
    */
    public boolean CheckCollisionWith(PhysicsObject other)
    {
        return Intersector.intersectPolygons(new FloatArray(collisionPolygon.getTransformedVertices()), new FloatArray(other.collisionPolygon.getTransformedVertices()));
    }

    /**
     * Method executed when a collision is detected.
     *
     * @param other the PhysicsObject with which the collision was detected.
     */
    public abstract void OnCollision(PhysicsObject other);
}