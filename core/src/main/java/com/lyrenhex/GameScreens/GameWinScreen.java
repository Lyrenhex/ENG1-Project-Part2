package com.lyrenhex.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.lyrenhex.GeneralControl.eng1game;

/**
 * The game over screen for when the player wins the game.
 */
public class GameWinScreen implements Screen{

    private final SpriteBatch batch;
    BitmapFont font;
    GlyphLayout winTextLayout;
    eng1game game;

    public GameWinScreen(eng1game g)
    {
        game = g;
        font = new BitmapFont(Gdx.files.internal("fonts/bobcat.fnt"));
        winTextLayout = new GlyphLayout();
        winTextLayout.setText(font, "YOU WIN! Press ESCAPE to return to the menu");
        batch = new SpriteBatch();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
        {
            game.gotoScreen(Screens.menuScreen);
        }

        //do draws
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin(); //start batch
        font.getData().setScale(1);
        //the below line centres the text on the centre of the screen
        font.draw(batch, winTextLayout, Gdx.graphics.getWidth()/2 - winTextLayout.width/2 ,Gdx.graphics.getHeight()/2 + winTextLayout.height/2);
        batch.end(); //end batch
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
    
}
