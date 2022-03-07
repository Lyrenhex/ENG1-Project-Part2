package com.lyrenhex.GeneralControl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.GameScreens.GameOverScreen;
import com.lyrenhex.GameScreens.GameWinScreen;
import com.lyrenhex.GameScreens.Menu;
import com.lyrenhex.GameScreens.Screens;
import com.lyrenhex.GameScreens.Splash;

public class eng1game extends Game {
	SpriteBatch batch;
	Texture img;
	Menu menuScreen;
	GameController gameScreen;
	
	public boolean timeUp = false;
	
	@Override
	public void create () {
		// create a menu and game screen, then switch to a new splash screen
		menuScreen = new Menu(this);
		gameScreen = new GameController(this);
		gotoScreen(Screens.splashScreen); 
		// splash screen commented out for now, in order to make testing faster,
		// splash will be re-added when the game is done
		// for now go directly to the menu
		// gotoScreen(Screens.menuScreen);
	}
	
	// uses the Screens enum to change between any screen
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
				gameScreen = new GameController(this);
				setScreen(gameScreen);
				break;
			case gameOverScreen:
				GameOverScreen gameOverScreen = new GameOverScreen(this, timeUp ? "Time Up! ENTER to go to menu, R to restart" : "You Died! ENTER to go to menu, R to restart");
				setScreen(gameOverScreen);
				break;
			case gameWinScreen:
				GameWinScreen gameWinScreen = new GameWinScreen(this);
				setScreen(gameWinScreen);
		}
	}

	@Override
	public void render () {
		/*ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
		super.render();
	}
	
	@Override
	public void dispose () {
		/*batch.dispose();
		img.dispose();*/
		super.dispose();
	}
}

