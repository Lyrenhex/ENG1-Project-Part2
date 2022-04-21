package com.lyrenhex.Obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;

/**
 * Class to handle choppy waves which the player may encounter.
 */
public class ChoppyWaves extends Weather {
    public ChoppyWaves(GameController controller, Vector2 position) {
        // TODO: Choppy Wave graphics
        super(controller, position, new Vector2(500, 300), new Texture(Gdx.files.internal("img/lightray.jpg")));
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        // collisions are handled by PlayerBoat.
    }

    @Override
    public void Update(float delta) {}
}
