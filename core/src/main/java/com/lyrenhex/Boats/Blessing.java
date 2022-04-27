package com.lyrenhex.Boats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameScreens.GameController;

/**
 * Class for the Blessing power-up encountered on the map, which grants Charlie Jeffery's blessing on pickup (collision).
 */
public class Blessing extends Boat {
    public Blessing(GameController controller, Vector2 position, Vector2 mapSize) {
        this.HP = 500;
        this.maxHP = 500;
        // does not move
        this.speed = 0;
        this.turnSpeed = 0;

        this.controller = controller;

        this.position = position;

        sprite = new Sprite(new Texture(Gdx.files.internal("img/CharlieJeffery.png")));
        sprite.setSize(100, 100);
        sprite.setOrigin(50, 50);

        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(50,50);
        collisionPolygon.setRotation(rotation - 90);


        sprite.setPosition(position.x, position.y);

        this.mapSize = mapSize.cpy(); //copy the array so we dont modify the original
        mapBounds = new Array<Vector2>(true, 4); //use a libgdx array of vectors because
        // its an easy way to check point x box collision
        mapBounds.add(new Vector2(0,0));
        mapBounds.add(new Vector2(mapSize.x, 0));
        mapBounds.add(new Vector2(mapSize.x, mapSize.y));
        mapBounds.add(new Vector2(0, mapSize.y));
    }

    public void Destroy(){
        killOnNextTick = true;
    }

    public void Update(float delta){ }

    public void Shoot(){ }

    public void OnCollision(PhysicsObject object){
        if(object instanceof PlayerBoat){
            // grant the player immunity and destroy the world object.
            controller.playerBoat.setImmune();
            Destroy();
        }
    }
}
