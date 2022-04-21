package com.lyrenhex.Obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;

/**
 * Abstract class to manage common features between weather events.
 */
public abstract class Weather extends PhysicsObject {
    GameController controller;

    public Weather(GameController controller, Vector2 position, Vector2 dimensions, Texture texture) {
        this.controller = controller;

        this.position = position.cpy();

        sprite = new Sprite(texture);
        sprite.setSize(dimensions.x, dimensions.y);
        sprite.setPosition(position.x, position.y);
        sprite.setOrigin(dimensions.x / 2, dimensions.y / 2);

        collisionPolygon = new Polygon(new float[]{0,0,dimensions.x,0,dimensions.x,dimensions.y,0,dimensions.y});
        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(dimensions.x / 2, dimensions.y / 2);
        collisionPolygon.setRotation(rotation - 90);
    }

    @Override
    public abstract void Update(float delta);

    @Override
    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
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
