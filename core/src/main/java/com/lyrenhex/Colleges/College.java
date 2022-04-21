package com.lyrenhex.Colleges;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.lyrenhex.Boats.Boat;
import com.lyrenhex.GameGenerics.PhysicsObject;

import java.lang.Math;

/**
 * Abstract class to implement common functions between enemy colleges and the player's college.
 */
public abstract class College extends PhysicsObject {
    protected int range;
    int HP;
    int damage;
    int fireRate;
    protected Sprite aliveSprite;
    Sprite deadSprite;
    Sprite islandSprite;

    /**
     * Returns true if the specified boat object is in range of the college
     *
     * @param  other   the boat to check the range of
     * @return true if the boat is in range of the college
    */
    public boolean isInRange(Boat other)
    {
        // work out euclidean distance to the other physics object, and then returns true if the 
        // that distance is <= the range of the college
        // this will be used to check if the enemy college should attack the player
        // this will be used to check if the friendly college should heal the player
        return range >= Math.sqrt(Math.pow((aliveSprite.getX() + aliveSprite.getWidth()/2) - (other.position.x + other.GetCenterX()), 2) +
                                  Math.pow((aliveSprite.getY() + aliveSprite.getHeight()/2) - (other.position.y + other.GetCenterY()), 2));
    }
}
