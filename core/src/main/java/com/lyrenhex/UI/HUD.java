package com.lyrenhex.UI;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.lyrenhex.GameGenerics.GameObject;
import com.lyrenhex.GameGenerics.Upgrades;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.Level.WaterBackground;

/**
 * Class managing the game's user interface components.
 */
public class HUD extends GameObject {


    GlyphLayout hpTextLayout;
    GlyphLayout timerTextLayout;
    GlyphLayout immunityTextLayout;
    WaterBackground bg;

    Stage stage;

    TextButton menuButton;
    TextButtonStyle menuButtonStyle;

    TextButton shopButton;
    TextButtonStyle shopButtonStyle;

    TextButton upgradeButton1;
    TextButtonStyle upgradeButton1Style;

    TextButton upgradeButton2;
    TextButtonStyle upgradeButton2Style;

    TextButton buyBoosterButton;
    TextButtonStyle buyBoosterButtonStyle;

    TextButton buyHealButton;
    TextButtonStyle buyHealButtonStyle;

    TextButton buyCannonsButton;
    TextButtonStyle buyCannonsButtonStyle;

    Image upgradeMenuBackground;
    Image shopMenuBackground;

    GlyphLayout xpTextLayout;
    GlyphLayout plunderTextLayout;

    BitmapFont font;
    GameController gc;

    public boolean hoveringOverButton = false; // Disable certain player behaviours when hovering over a button
    boolean upgradeMenuOpen = false;
    boolean upgradeMenuInitialised = false; // Set to true once initialised
    boolean shopMenuOpen = false;
    boolean shopMenuInitialised;

    Upgrades upgrade1;
    int upgrade1cost;
    float upgrade1amount;
    Upgrades upgrade2;
    int upgrade2cost;
    float upgrade2amount;
    int healthCost;
    int boosterCost;
    int cannonsCost;

    public HUD(GameController gameController)
    {
        gc = gameController;
        
        stage = new Stage(); // Lets us implement interactable UI elements
        font = new BitmapFont(Gdx.files.internal("fonts/bobcat.fnt"), false);
        hpTextLayout = new GlyphLayout();
        timerTextLayout = new GlyphLayout();
        xpTextLayout = new GlyphLayout();
        plunderTextLayout = new GlyphLayout();
        immunityTextLayout = new GlyphLayout();
        Gdx.input.setInputProcessor(stage);
        
        DrawUpgradeButton(); // put this in its own function to make this function look a bit cleaner
        DrawShopButton();
    }

    @Override
    public void Update(float delta)
    {
        hpTextLayout.setText(font, "HP: " + gc.playerBoat.HP + "/" + gc.playerBoat.maxHP);
        xpTextLayout.setText(font, "XP: " + gc.xp);
        timerTextLayout.setText(font, "Time: " + Math.round(gc.timer));
        plunderTextLayout.setText(font, "Plunder: " + gc.plunder);
        immunityTextLayout.setText(font, "IMMUNE FOR " + Math.round(gc.playerBoat.remainingTimeImmune()) + " SECONDS");
        font.getData().setScale(1);
    }

    @Override
    public void Draw(SpriteBatch batch)
    {
        // Draw the text showing the player's stats
        font.draw(batch, hpTextLayout, 5, gc.map.camera.viewportHeight - 10);
        font.draw(batch, timerTextLayout, 5, gc.map.camera.viewportHeight - 50);
        font.draw(batch, xpTextLayout, gc.map.camera.viewportWidth - xpTextLayout.width - 5, gc.map.camera.viewportHeight - 50);
        font.draw(batch, plunderTextLayout, gc.map.camera.viewportWidth - plunderTextLayout.width - 5, gc.map.camera.viewportHeight - 10);

        if (gc.playerBoat.isImmune()) {
            font.draw(batch, immunityTextLayout, (gc.map.camera.viewportWidth / 2) - (immunityTextLayout.width / 2), 10 + immunityTextLayout.height);
        }

        stage.draw();
    }
    
    // UI & Upgrade Functions

    /**
     * Draws the exp upgrade button on the screen.
     */
    public void DrawUpgradeButton(){
        // Create the upgrade button and add it to the UI stage
        menuButtonStyle = new TextButtonStyle();
        menuButtonStyle.font = font;
        menuButtonStyle.fontColor = Color.BLACK;
        menuButtonStyle.up = new TextureRegionDrawable(new Texture("ui/button.png"));
        menuButton = new TextButton("Exp Upgrade", menuButtonStyle);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // do some actions
                upgradeMenuOpen = !upgradeMenuOpen;
                ToggleMenu();
            }

            // Giving them enter and exit functions so that the player can't fire with left click while hovering over a button.
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                    System.out.println("Hovering over");
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        menuButton.setScale(1f, 1f);
        menuButton.setPosition(Gdx.graphics.getWidth()/3 - menuButton.getWidth()/2, Gdx.graphics.getHeight() - menuButton.getHeight());

        stage.addActor(menuButton);
    }


    /**
     * Draws the shop upgrade button on the screen.
     */
    public void DrawShopButton(){
        // Create the shop button and add it to the UI stage
        shopButtonStyle = new TextButtonStyle();
        shopButtonStyle.font = font;
        shopButtonStyle.fontColor = Color.BLACK;
        shopButtonStyle.up = new TextureRegionDrawable(new Texture("ui/button.png"));
        shopButton = new TextButton("Shop", shopButtonStyle);

        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // do some actions
                shopMenuOpen = !shopMenuOpen;
                ToggleShop();
            }

            // Giving them enter and exit functions so that the player can't fire with left click while hovering over a button.
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                    System.out.println("Hovering over");
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        shopButton.setScale(1f, 1f);
        shopButton.setPosition(2*Gdx.graphics.getWidth()/3 - shopButton.getWidth()/2, Gdx.graphics.getHeight() - shopButton.getHeight());

        stage.addActor(shopButton);
    }

    /**
     * Toggles display of the menu.
     */
    public void ToggleMenu(){
        // Put the XP menu drawing calls in its own function so that render doesn't get too cluttered
        // Closes shop menu when upgrades menu is open
        if(upgradeMenuOpen && shopMenuOpen){
            shopMenuOpen = false;
            ToggleShop();
        }

        // Initialise the menu if it hasn't been, this avoids repeatedly creating new buttons.
        if(!upgradeMenuInitialised) InitialiseMenu();
        
        // Add/re-add the UI elements back to the stage
        if(upgradeMenuOpen){
            UpdateMenu();
            stage.addActor(upgradeMenuBackground);
            stage.addActor(upgradeButton1);
            stage.addActor(upgradeButton2);
        } else{ // Remove all menu elements from the stage
            upgradeMenuBackground.remove();
            upgradeButton1.remove();
            upgradeButton2.remove();
        }
    }

    public void ToggleShop(){
        // Put the shop menu drawing calls in its own function so that render doesn't get too cluttered

        // Close the upgrade menu when the shop menu is opened
        if(shopMenuOpen && upgradeMenuOpen){
            upgradeMenuOpen = false;
            ToggleMenu();
        }

        // Initialise the menu if it hasn't been, this avoids repeatedly creating new buttons.
        if(!shopMenuInitialised) InitialiseShop();

        // Add/re-add the UI elements back to the stage
        if(shopMenuOpen){

            buyCannonsButton.setText("Item:\n" + "Extra Cannons"  + "\nCost:\n" + cannonsCost + " plunder");
            buyCannonsButton.setScale(1f, 1f);
            buyCannonsButton.setPosition(((Gdx.graphics.getWidth()/2 - shopMenuBackground.getWidth()/2 + 15)+(Gdx.graphics.getWidth()/2 + 35))/2, Gdx.graphics.getHeight()/2  - buyCannonsButton.getHeight() - 15);
            buyCannonsButton.setSize(350, 200);

            buyBoosterButton.setText("Item:\n" + "Rocket Booster"  + "\nCost:\n" + boosterCost + " plunder");
            buyBoosterButton.setScale(1f, 1f);
            buyBoosterButton.setPosition(Gdx.graphics.getWidth()/2 + 35, Gdx.graphics.getHeight()/2 + shopMenuBackground.getHeight()/2 - buyBoosterButton.getHeight() - 15);
            buyBoosterButton.setSize(350,200);

            buyHealButton.setText("Item:\n" + "Ship Repairs"  + "\nCost:\n" + healthCost + " plunder");
            buyHealButton.setScale(1f, 1f);
            buyHealButton.setPosition(Gdx.graphics.getWidth()/2 - shopMenuBackground.getWidth()/2 + 15, Gdx.graphics.getHeight()/2 + shopMenuBackground.getHeight()/2 - buyHealButton.getHeight() - 15);
            buyHealButton.setSize(350, 200);

            stage.addActor(shopMenuBackground);
            stage.addActor(buyBoosterButton);
            stage.addActor(buyHealButton);
            stage.addActor(buyCannonsButton);
        } else{ // Remove all menu elements from the stage
            shopMenuBackground.remove();
            buyCannonsButton.remove();
            buyBoosterButton.remove();
            buyHealButton.remove();
        }
    }

    /**
     * This function creates the menu for the first time, and also generates the first set of upgrades.
     */
    public void InitialiseMenu(){
        // Create the background
        upgradeMenuBackground = new Image(new Texture("ui/background.png"));
        upgradeMenuBackground.setPosition(Gdx.graphics.getWidth()/2 - upgradeMenuBackground.getWidth()/2, Gdx.graphics.getHeight()/2 - upgradeMenuBackground.getHeight()/2);
        upgradeMenuBackground.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        // Create the upgrade buttons
        upgradeButton1Style = new TextButtonStyle();   
        upgradeButton1Style.font = font;
        upgradeButton1Style.fontColor = Color.BLACK;
        upgradeButton1Style.up = new TextureRegionDrawable(new Texture("ui/upgradebutton.png"));

        upgradeButton2Style = new TextButtonStyle();
        upgradeButton2Style.font = font;
        upgradeButton2Style.fontColor = Color.BLACK;
        upgradeButton2Style.up = new TextureRegionDrawable(new Texture("ui/upgradebutton.png"));

        upgradeButton1 = new TextButton("", upgradeButton1Style);
        upgradeButton2 = new TextButton("", upgradeButton2Style);

        upgradeButton1.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(gc.xp >= upgrade1cost){
                    gc.xp -= upgrade1cost;
                    BuyUpgrade(1);
                    RandomiseUpgrades();
                }
                return true;
            }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        upgradeButton2.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // do some actions
                if(gc.xp >= upgrade2cost){
                    gc.xp -= upgrade2cost;
                    BuyUpgrade(2);
                    RandomiseUpgrades();
                }
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        upgradeMenuInitialised = true;

        RandomiseUpgrades();

        stage.addActor(upgradeMenuBackground);
        stage.addActor(upgradeButton1);
        stage.addActor(upgradeButton2);
    }


    public void InitialiseShop(){
        // Create the background
        shopMenuBackground = new Image(new Texture("ui/background.png"));
        shopMenuBackground.setPosition(Gdx.graphics.getWidth()/2 - shopMenuBackground.getWidth()/2, Gdx.graphics.getHeight()/2 - shopMenuBackground.getHeight()/2);
        shopMenuBackground.addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        // Create the item purchase buttons
        buyHealButtonStyle = new TextButtonStyle();
        buyHealButtonStyle.font = font;
        buyHealButtonStyle.fontColor = Color.BLACK;
        buyHealButtonStyle.up = new TextureRegionDrawable(new Texture("ui/upgradebutton.png"));

        buyBoosterButtonStyle = new TextButtonStyle();
        buyBoosterButtonStyle.font = font;
        buyBoosterButtonStyle.fontColor = Color.BLACK;
        buyBoosterButtonStyle.up = new TextureRegionDrawable(new Texture("ui/upgradebutton.png"));

        buyCannonsButtonStyle = new TextButtonStyle();
        buyCannonsButtonStyle.font = font;
        buyCannonsButtonStyle.fontColor = Color.BLACK;
        buyCannonsButtonStyle.up = new TextureRegionDrawable(new Texture("ui/upgradebutton.png"));

        buyHealButton = new TextButton("", buyHealButtonStyle);
        buyBoosterButton = new TextButton("", buyBoosterButtonStyle);
        buyCannonsButton = new TextButton("", buyCannonsButtonStyle);

        healthCost = 50;
        boosterCost = 150;
        cannonsCost = 150;

        buyHealButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(gc.plunder >= healthCost){
                    gc.plunder -= healthCost;
                    BuyUpgrade(3);
                }
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        buyBoosterButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // do some actions
                if(gc.plunder >= boosterCost){
                    gc.plunder -= boosterCost;
                    BuyUpgrade(4);
                }
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });


        buyCannonsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // do some actions
                if(gc.plunder >= cannonsCost){
                    gc.plunder -= cannonsCost;
                    BuyUpgrade(5);
                }
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(pointer == -1){
                    hoveringOverButton = false;
                }
            }
        });

        shopMenuInitialised = true;

        buyCannonsButton.setText("Item:\n" + "Extra Cannons"  + "\nCost:\n" + cannonsCost + " plunder");
        buyCannonsButton.setScale(1f, 1f);
        buyCannonsButton.setPosition(((Gdx.graphics.getWidth()/2 - shopMenuBackground.getWidth()/2 + 15)+(Gdx.graphics.getWidth()/2 + 35))/2, Gdx.graphics.getHeight()/2  - buyCannonsButton.getHeight() - 15);
        buyCannonsButton.setSize(350, 200);

        buyBoosterButton.setText("Item:\n" + "Rocket Booster"  + "\nCost:\n" + boosterCost + " plunder");
        buyBoosterButton.setScale(1f, 1f);
        buyBoosterButton.setPosition(Gdx.graphics.getWidth()/2 + 35, Gdx.graphics.getHeight()/2 + shopMenuBackground.getHeight()/2 - buyBoosterButton.getHeight() - 15);
        buyBoosterButton.setSize(350,200);

        buyHealButton.setText("Item:\n" + "Ship Repairs"  + "\nCost:\n" + healthCost + " plunder");
        buyHealButton.setScale(1f, 1f);
        buyHealButton.setPosition(Gdx.graphics.getWidth()/2 - shopMenuBackground.getWidth()/2 + 15, Gdx.graphics.getHeight()/2 + shopMenuBackground.getHeight()/2 - buyHealButton.getHeight() - 15);
        buyHealButton.setSize(350, 200);


        stage.addActor(shopMenuBackground);
        stage.addActor(buyCannonsButton);
        stage.addActor(buyHealButton);
        stage.addActor(buyBoosterButton);
    }





    /**
     * Updates the upgrade buttons (eg, after buying an upgrade).
     */
    public void UpdateMenu(){
        upgradeButton1.setText(!(upgrade1 == Upgrades.projectiledamage || upgrade1 == Upgrades.projectilespeed) ?
        "Upgrade:\n" + upgrade1.label + " + " + upgrade1amount + "\nCost:\n" + upgrade1cost + " XP" : 
        "Upgrade:\n" + upgrade1.label + " + " + upgrade1amount * 100 + "%\nCost:\n" + upgrade1cost + " XP");
        

        upgradeButton1.setScale(1f, 1f);
        upgradeButton1.setPosition(Gdx.graphics.getWidth()/2 - upgradeMenuBackground.getWidth()/2 + 15, Gdx.graphics.getHeight()/2 + upgradeMenuBackground.getHeight()/2 - upgradeButton1.getHeight() - 15);

        upgradeButton2.setText(!(upgrade2 == Upgrades.projectiledamage || upgrade2 == Upgrades.projectilespeed) ?
            "Upgrade:\n" + upgrade2.label + " + " + upgrade2amount + "\nCost:\n" + upgrade2cost + " XP" : 
            "Upgrade:\n" + upgrade2.label + " + " + upgrade2amount * 100 + "%\nCost:\n" + upgrade2cost + " XP");


        upgradeButton2.setScale(1f, 1f);
        upgradeButton2.setPosition(Gdx.graphics.getWidth()/2 + 35, Gdx.graphics.getHeight()/2 + upgradeMenuBackground.getHeight()/2 - upgradeButton2.getHeight() - 15);
    }

    /**
     * Purchases an upgrade.
     *
     * @param upgrade the identifier of the upgrade which was purchased.
     */
    void BuyUpgrade(int upgrade){
        switch(upgrade){
            case 1:
                gc.playerBoat.Upgrade(upgrade1, upgrade1amount);
                break;
            case 2:
                gc.playerBoat.Upgrade(upgrade2, upgrade2amount);
                break;
            case 3:
                gc.playerBoat.Upgrade(Upgrades.health, 100);
                break;
            case 4:
                gc.playerBoat.enableBooster();
                break;
            case 5:
                gc.playerBoat.addExtraCannons();
                break;
            default:
                break;
        }
    }

    /**
     * Generates random upgrades.
     */
    void RandomiseUpgrades(){
        Random r = new Random();
        switch(r.nextInt(6)){
            case 0: // Max Health
                upgrade1 = Upgrades.maxhealth;
                upgrade1amount = 10;
                upgrade1cost = 25;
                break;
            case 1: // Speed
                upgrade1 = Upgrades.speed;
                upgrade1amount = 6.25f;
                upgrade1cost = 25;
                break;
            case 2: // Turn Speed
                upgrade1 = Upgrades.turnspeed;
                upgrade1amount = 7.5f;
                upgrade1cost = 25;
                break;
            case 3: // Damage
                upgrade1 = Upgrades.projectiledamage;
                upgrade1amount = 0.1f;
                upgrade1cost = 25;
                break;
            case 4: // Speed
                upgrade1 = Upgrades.projectilespeed;
                upgrade1amount = 0.05f;
                upgrade1cost = 25;
                break;
            case 5: // Defense
                upgrade1 = Upgrades.defense;
                upgrade1amount = 1f;
                upgrade1cost = 35;
                break;
        }

        switch(r.nextInt(6)){
            case 0: // Max Health
                upgrade2 = Upgrades.maxhealth;
                upgrade2amount = 10;
                upgrade2cost = 25;
                break;
            case 1: // Speed
                upgrade2 = Upgrades.speed;
                upgrade2amount = 6.25f;
                upgrade2cost = 25;
                break;
            case 2: // Turn Speed
                upgrade2 = Upgrades.turnspeed;
                upgrade2amount = 7.5f;
                upgrade2cost = 25;
                break;
            case 3: // Damage
                upgrade2 = Upgrades.projectiledamage;
                upgrade2amount = 0.1f;
                upgrade2cost = 25;
                break;
            case 4: // Speed
                upgrade2 = Upgrades.projectilespeed;
                upgrade2amount = 0.05f;
                upgrade2cost = 25;
                break;
            case 5: // Defense
                upgrade2 = Upgrades.defense;
                upgrade2amount = 1f;
                upgrade2cost = 35;
                break;
        }

        UpdateMenu();
    }
}
