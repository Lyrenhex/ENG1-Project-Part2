package com.lyrenhex.GameGenerics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.math.Intersector;

public abstract class PhysicsObject extends GameObject {
    //in addition to the GameObject, PhysicsObjects must also implement the following

    public Polygon collisionPolygon = null;

    /**
        Returns true if the 2 physicsobjects collide

        @param  other   physicsobject to check collision with
        @return boolean true if the physicsobjects collide        
    */
    public boolean CheckCollisionWith(PhysicsObject other)
    {
        return Intersector.intersectPolygons(new FloatArray(collisionPolygon.getTransformedVertices()), new FloatArray(other.collisionPolygon.getTransformedVertices()));
    }
    public abstract void OnCollision(PhysicsObject other);
}