package com.lyrenhex.Colleges;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.Saves.PlayerCollegeState;

/**
 * The friendly player college for healing.
 */
public class PlayerCollege extends College{

    int healAmount;

    public PlayerCollege(Vector2 position, Texture aliveTexture, Texture islandTexture) {
        healAmount = 15;
        range = 400;
        aliveSprite = new Sprite(aliveTexture);
        aliveSprite.setPosition(position.x, position.y);
        aliveSprite.setSize(100,100);
        islandSprite = new Sprite(islandTexture);
        islandSprite.setCenter(aliveSprite.getX()+5, aliveSprite.getY()+5);
        islandSprite.setSize(150, 150);
        this.position = position;
        collisionPolygon = new Polygon(new float[]{0,0,100,0,100,100,0,100});
        collisionPolygon.setPosition(position.x, position.y);
    }

    public PlayerCollege(PlayerCollegeState state) {
        this(state.position, new Texture(Gdx.files.internal(state.aliveTexturePath)), new Texture(Gdx.files.internal(state.islandTexturePath)));
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        // PlayerCollege doesn't need to handle any collisions itself
        // the case of the PlayerBoat crashing into it is handled by the
        // PlayerBoat.
    }

    @Override
    public void Update(float delta, PhysicsObject playerBoat)
    {
        PlayerBoat boat = (PlayerBoat) playerBoat;
        if(isInRange(boat))
        { // if the player boat is in range, heal it
            boat.Heal(healAmount, delta);
        }
    }

    @Override
    public void Draw(SpriteBatch batch)
    {
        islandSprite.draw(batch);
        aliveSprite.draw(batch);    
    }

    /**
     * Obtains a serialisable form of the current state of the object.
     * @return an object storing the state information of the object.
     */
    public PlayerCollegeState getSaveState() {
        return new PlayerCollegeState(position, aliveSprite.getTexture(), islandSprite.getTexture());
    }

}
