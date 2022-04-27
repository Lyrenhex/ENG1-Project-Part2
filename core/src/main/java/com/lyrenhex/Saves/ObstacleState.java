package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;

public class ObstacleState {
    public Vector2 position;
    public String texturePath;

    public ObstacleState(Vector2 position, Texture texture) {
        this.position = position;
        this.texturePath = ((FileTextureData) texture.getTextureData()).getFileHandle().path();
    }
}
