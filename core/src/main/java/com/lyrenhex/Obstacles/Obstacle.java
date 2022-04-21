package com.lyrenhex.Obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;

/**
 * Obstacle class to handle the fixed obstacles in the game world, such as rocks and shipwrecks.
 */
public class Obstacle extends PhysicsObject {
    GameController controller;

    public Obstacle(GameController controller, Vector2 position, Texture texture) {
        this.controller = controller;

        this.position = position.cpy();

        sprite = new Sprite(texture);
        sprite.setSize(100, 100);
        sprite.setOrigin(50, 50);

        collisionPolygon = new Polygon(new float[]{0,0,100,0,100,100,0,100});
        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(50,50);
        collisionPolygon.setRotation(rotation - 90);
    }

    @Override
    public void Update(float delta) {}

    @Override
    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        // collisions are handled by PlayerBoat.
    }

    /**
     * Returns the x-coordinate of the center of the obstacle.
     *
     * @return the x-coordinate of the center of the obstacle.
     */
    public float GetCenterX()
    {
        return sprite.getOriginX();
    }

    /**
     * Returns the y-coordinate of the center of the obstacle.
     *
     * @return the y-coordinate of the center of the obstacle.
     */
    public float GetCenterY()
    {
        return sprite.getOriginY();
    }
}
