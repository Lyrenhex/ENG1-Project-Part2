package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;

public class PlayerCollegeState {
    public Vector2 position;
    public String aliveTexturePath;
    public String islandTexturePath;

    public PlayerCollegeState(Vector2 position, Texture aliveTexture, Texture islandTexture) {
        this.position = position;
        this.aliveTexturePath = ((FileTextureData) aliveTexture.getTextureData()).getFileHandle().path();
        this.islandTexturePath = ((FileTextureData) islandTexture.getTextureData()).getFileHandle().path();
    }
}
