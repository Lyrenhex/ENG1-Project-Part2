package com.lyrenhex.Obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lyrenhex.GameGenerics.PhysicsObject;

/**
 * Class to handle choppy waves which the player may encounter.
 */
public class ChoppyWaves extends PhysicsObject {
    Vector2 position;

    TextureRegion[] waterTextureRegion;
    TextureRegionDrawable waterTextureRegionDrawable;
    int waterTextureNumber = 0;
    float lastWaterTextureChange;
    final float waterChangeDelay = 1f;

    public ChoppyWaves(Vector2 position) {
        this.position = position;

        waterTextureRegion = new TextureRegion[2];
        for (int i=0; i < 2; i++)
        {
            Texture x = new Texture("img/choppyWater" + (i + 1) + ".png"); //load a texture into memory
            x.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            waterTextureRegion[i] = new TextureRegion(x); // make a texture region and set the size
            waterTextureRegion[i].setRegionWidth(500);
            waterTextureRegion[i].setRegionHeight(300);
        }
        waterTextureRegionDrawable = new TextureRegionDrawable(waterTextureRegion[0]);
        //make a drawable texture region

        collisionPolygon = new Polygon(new float[]{0,0,500,0,500,300,0,300});
        collisionPolygon.setPosition(position.x, position.y);

        lastWaterTextureChange = 0; //setup the counter
    }

    @Override
    public void OnCollision(PhysicsObject other) {
        // collisions are handled by PlayerBoat.
    }

    @Override
    public void Update(float delta) {
        lastWaterTextureChange += delta;
        if(lastWaterTextureChange >= waterChangeDelay)
        {
            waterTextureNumber = (waterTextureNumber + 1) % 2;
            waterTextureRegionDrawable.setRegion(waterTextureRegion[waterTextureNumber]);
            lastWaterTextureChange = 0;
        }
    }

    @Override
    public void Draw(SpriteBatch batch) {
        waterTextureRegionDrawable.draw(batch, position.x, position.y, 500, 300);
    }

}
