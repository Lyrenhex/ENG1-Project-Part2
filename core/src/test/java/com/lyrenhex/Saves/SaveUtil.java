
package com.lyrenhex.Saves;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveUtil {

    static SaveState gameState;

    public static SaveState getState() {
        if (gameState == null) {
            String savePath = Gdx.files.internal("test.save").file().getAbsolutePath();
            // load the save game if one exists.
            if (Files.exists(Path.of(savePath))) {
                try {
                    Gson gson = new Gson();
                    gameState = gson.fromJson(Files.readString(Path.of(savePath)), SaveState.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return gameState;
    }



}