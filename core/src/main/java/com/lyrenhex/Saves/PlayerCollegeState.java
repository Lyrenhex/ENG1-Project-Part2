package com.lyrenhex.Saves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerCollegeState {
    public Vector2 position;
    public Texture aliveTexture;
    public Texture islandTexture;

    public PlayerCollegeState(Vector2 position, Texture aliveTexture, Texture islandTexture) {
        this.position = position;
        this.aliveTexture = aliveTexture;
        this.islandTexture = islandTexture;
    }
}
