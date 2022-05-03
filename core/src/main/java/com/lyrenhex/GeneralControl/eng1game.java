package com.lyrenhex.GeneralControl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.GameScreens.GameOverScreen;
import com.lyrenhex.GameScreens.GameWinScreen;
import com.lyrenhex.GameScreens.Menu;
import com.lyrenhex.GameScreens.Screens;
import com.lyrenhex.GameScreens.Splash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The core class of the game, which handles high level logic and screen transitions.
 */
public class eng1game extends Game {
    SpriteBatch batch;
    Texture img;
    Menu menuScreen;
    GameController gameScreen;

    public String savePath;
    public String gameState;

    public boolean timeUp = false;

    public boolean gameStarted = false;
    
    @Override
    public void create () {
        savePath = Gdx.files.external("21direction.save").file().getAbsolutePath();
        // load the save game if one exists.
        if (Files.exists(Path.of(savePath))) {
            try {
                gameState = Files.readString(Path.of(savePath));
                gameStarted = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // create a menu and game screen, then switch to a new splash screen
        menuScreen = new Menu(this);
        if (gameStarted) {
            gameScreen = new GameController(this, gameState);
        } else {
            gameScreen = new GameController(this);
        }
        gotoScreen(Screens.splashScreen);
    }

    /**
     * Switch to a screen defined in the Screens enum.
     *
     * @param s the Screen to switch to.
     */
    public void gotoScreen(Screens s)
    {
        switch(s){
            case splashScreen: //creates a new splash screen
                Splash splashScreen = new Splash(this);
                setScreen(splashScreen);
                break;
            case menuScreen: //switch back to the menu screen
                setScreen(menuScreen);
                break;
            case gameScreen: //switch back to the game screen
                gameStarted = true;
                setScreen(gameScreen);
                break;
            case gameOverScreen:
                GameOverScreen gameOverScreen = new GameOverScreen(this, timeUp ? "Time Up! ESCAPE to go to menu, R to restart" : "You Died! ESCAPE to go to menu, R to restart");
                gameScreen = new GameController(this);
                gameStarted = false;
                gameState = null;
                try {
                    Files.deleteIfExists(Path.of(savePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(gameOverScreen);
                break;
            case gameWinScreen:
                GameWinScreen gameWinScreen = new GameWinScreen(this);
                gameScreen = new GameController(this);
                gameStarted = false;
                gameState = null;
                try {
                    Files.deleteIfExists(Path.of(savePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(gameWinScreen);
        }
    }

    /**
     * Method to set the difficulty, using the method defined in GameController.
     *
     * @param difficulty the difficulty of the game (Easy, Normal, or Hard).
     */
    public void setDifficulty(Difficulty difficulty) {
        gameScreen.setDifficulty(difficulty);
    }

    public GameController getGameScreen(){
        return this.gameScreen;
    }

    @Override
    public void render () {
        super.render();
    }
    
    @Override
    public void dispose () {
        super.dispose();
    }
}

