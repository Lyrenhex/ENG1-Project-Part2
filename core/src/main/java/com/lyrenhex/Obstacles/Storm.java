package com.lyrenhex.Obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.Saves.StormState;

/**
 * Class to handle Storms which the player may encounter.
 */
public class Storm extends Weather {
    float timeSinceLastDamage = 0.0f;
    float timeBetweenDamage = 1.0f;

    public Storm(GameController controller, Vector2 position) {
        // TODO: Storm graphics
        super(controller, position, new Vector2(500, 500), new Texture(Gdx.files.internal("img/storm.png")));
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        // collisions are handled by PlayerBoat.
    }

    @Override
    public void Update(float delta) {
        timeSinceLastDamage += delta;
    }

    /**
     * Resets the time since damage was last dealt.
     */
    public void DamageDealt() {
        timeSinceLastDamage = 0.0f;
    }

    /**
     * Returns whether sufficient time has passed since the last damage was dealt.
     * @return true if damage is allowed at this time.
     */
    public boolean isDamageAllowed() {
        return timeSinceLastDamage > timeBetweenDamage;
    }

    /**
     * Obtains a serialisable form of the current state of the object.
     * @return an object storing the state information of the object.
     */
    public StormState getSaveState() {
        return new StormState(position);
    }
}
