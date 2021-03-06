package com.lyrenhex.GameScreens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyrenhex.Boats.*;
import com.lyrenhex.Colleges.College;
import com.lyrenhex.Colleges.EnemyCollege;
import com.lyrenhex.Colleges.PlayerCollege;
import com.lyrenhex.GameGenerics.GameObject;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GeneralControl.Difficulty;
import com.lyrenhex.GeneralControl.eng1game;
import com.lyrenhex.Level.GameMap;
import com.lyrenhex.Obstacles.ChoppyWaves;
import com.lyrenhex.Obstacles.LongBoi;
import com.lyrenhex.Obstacles.Obstacle;
import com.lyrenhex.Obstacles.Storm;
import com.lyrenhex.Projectiles.ProjectileDataHolder;
import com.lyrenhex.Saves.*;
import com.lyrenhex.UI.HUD;

/**
 * The game screen, controlling the core gameplay.
 */
public class GameController implements Screen {

    eng1game game;
    ArrayList<GameObject> gameObjects;
    public ArrayList<PhysicsObject> physicsObjects;
    public ArrayList<College> colleges;
    public GameMap map;
    private final Vector2 mapSize;
    public PlayerBoat playerBoat;
    private EnemyCollege bossCollege;
    
    public float timer = 600;

    // UI Related Variables
    private SpriteBatch batch;
    BitmapFont font;
    public HUD hud;


    // Player Stats
    public int xp = 0;
    public int plunder = 0;

    float xpTick = 1f;
    float xpTickMultiplier = 1f;


    // projectile variables
    public ProjectileDataHolder projectileHolder;


    public GameController(eng1game game){ //passes the game class so that we can change scene back later
        this.game = game;
        gameObjects = new ArrayList<GameObject>();
        physicsObjects = new ArrayList<PhysicsObject>();
        colleges = new ArrayList<College>();
        projectileHolder = new ProjectileDataHolder();
        hud = new HUD(this);
        mapSize = new Vector2(3000, 3000);

        batch = new SpriteBatch();
        Random rd = new Random();

        // Spawn the Choppy Waves somewhere; collisions do not matter for weather effects.
        ChoppyWaves c = new ChoppyWaves(new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)));
        physicsObjects.add(c);

        // Create the player boat and place it in the centre of the screen
        playerBoat = new PlayerBoat(this, new Vector2(200,200), mapSize.cpy());
        physicsObjects.add(playerBoat);

        // this section creates a array of textures for the colleges, shuffles it and assigns to
        // the created colleges
        Texture[] collegeTextures = new Texture[10];
        for(int i=0; i < 9; i++)
        {
            collegeTextures[i] = new Texture("img/castle" + (i+1) + ".png");
        } //load the textures

        for(int i=0; i < 9; i++)
        {
            Texture tmp = collegeTextures[i];
            int randomInt = rd.nextInt(9);
            collegeTextures[i] = collegeTextures[randomInt];
            collegeTextures[randomInt] = tmp;
        } //shuffle the array of textures

        Texture islandTexture = new Texture("img/island.png"); // get the texture for colleges to sit on
        PlayerCollege p = new PlayerCollege(new Vector2(50,50), collegeTextures[0], islandTexture);
        physicsObjects.add(p); //add college to physics object, for updates
        colleges.add(p); //also add a reference to the colleges list

        // Account for increased map size (to allow space for obstacles and weather events) by randomly placing the
        // enemy colleges.
        boolean isCollision;
        EnemyCollege e;
        for (int i = 0; i < 3; i++) {
            do {
                e = new EnemyCollege(new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), collegeTextures[i+1], islandTexture, this, ProjectileDataHolder.stock, 200);
                isCollision = false;
                for (PhysicsObject current : physicsObjects) {
                    if (e.CheckCollisionWith(current)) {
                        isCollision = true;
                        break;
                    }
                }
            } while (isCollision);

            physicsObjects.add(e);
            colleges.add(e);

            CollegeBoat b;
            for (int j = 0; j < 4; j++) {
                do {
                    b = new CollegeBoat(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), mapSize, e);
                    isCollision = false;
                    for (PhysicsObject current : physicsObjects) {
                        if (b.CheckCollisionWith(current)) {
                            isCollision = true;
                            break;
                        }
                    }
                } while (isCollision);
                physicsObjects.add(b);
            }
        }

        do {
            bossCollege = new EnemyCollege(new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), collegeTextures[4], new Texture(Gdx.files.internal("img/islandBarrier.png")),
                    this, ProjectileDataHolder.boss, 200);
            isCollision = false;
            for (PhysicsObject current : physicsObjects) {
                if (bossCollege.CheckCollisionWith(current)) {
                    isCollision = true;
                    break;
                }
            }
        } while (isCollision);

        bossCollege.invulnerable = true;
        physicsObjects.add(bossCollege);
        colleges.add(bossCollege);

        // Spawn Long Boi somewhere...
        LongBoi l;
        do {
            l = new LongBoi(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)));
            isCollision = false;
            for (PhysicsObject current : physicsObjects) {
                if (l.CheckCollisionWith(current)) {
                    isCollision = true;
                    break;
                }
            }
        } while (isCollision);
        physicsObjects.add(l);

        // Spawn the Blessing somewhere...
        Blessing b;
        do {
            b = new Blessing(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), mapSize);
            isCollision = false;
            for (PhysicsObject current : physicsObjects) {
                if (b.CheckCollisionWith(current)) {
                    isCollision = true;
                    break;
                }
            }
        } while (isCollision);
        physicsObjects.add(b);

        // spawn some rocks
        for (int i = 0; i < 7; i++) {
            Obstacle o;
            do {
                o = new Obstacle(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), new Texture(Gdx.files.internal("img/rocks.png")));
                isCollision = false;
                for (PhysicsObject current : physicsObjects) {
                    if (o.CheckCollisionWith(current)) {
                        isCollision = true;
                        break;
                    }
                }
            } while (isCollision);

            physicsObjects.add(o);
        }

        // spawn some shipwrecks
        for (int i = 0; i < 3; i++) {
            Obstacle o;
            do {
                o = new Obstacle(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), new Texture(Gdx.files.internal("img/shipWreck.png")));
                isCollision = false;
                for (PhysicsObject current : physicsObjects) {
                    if (o.CheckCollisionWith(current)) {
                        isCollision = true;
                        break;
                    }
                }
            } while (isCollision);

            physicsObjects.add(o);
        }

        // Spawn the Storm somewhere; collisions do not matter for weather effects.
        Storm s = new Storm(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)));
        physicsObjects.add(s);

        //create the moving camera/map borders
        map = new GameMap(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                playerBoat, batch, (int) mapSize.x, (int) mapSize.y);
    }

    public GameController(eng1game game, String saveData) {
        this.game = game;
        gameObjects = new ArrayList<GameObject>();
        physicsObjects = new ArrayList<PhysicsObject>();
        colleges = new ArrayList<College>();
        projectileHolder = new ProjectileDataHolder();
        hud = new HUD(this);
        mapSize = new Vector2(3000, 3000);

        batch = new SpriteBatch();
        Random rd = new Random();

        Gson gson = new Gson();
        SaveState state = gson.fromJson(saveData, SaveState.class);
        timer = state.timer;
        xp = state.xp;
        plunder = state.plunder;

        if (state.getChoppyWaves() != null) {
            ChoppyWaves c = new ChoppyWaves(state.getChoppyWaves().position);
            physicsObjects.add(c);
        }

        playerBoat = new PlayerBoat(this, state.getPlayer(), mapSize.cpy());
        physicsObjects.add(playerBoat);

        PlayerCollege p = new PlayerCollege(state.getPlayerCollege());
        physicsObjects.add(p); //add college to physics object, for updates
        colleges.add(p); //also add a reference to the colleges list

        boolean isCollision;
        EnemyCollege e;
        for (EnemyCollegeState es : state.getEnemyColleges()) {
            e = new EnemyCollege(this, es);

            if (es.projectileType == ProjectileDataHolder.Option.Boss) bossCollege = e;

            physicsObjects.add(e);
            colleges.add(e);

            CollegeBoat b;
            for (int j = 0; j < es.numBoats; j++) {
                do {
                    b = new CollegeBoat(this, new Vector2(rd.nextInt((int) mapSize.x), rd.nextInt((int) mapSize.y)), mapSize, e);
                    isCollision = false;
                    for (PhysicsObject current : physicsObjects) {
                        if (b.CheckCollisionWith(current)) {
                            isCollision = true;
                            break;
                        }
                    }
                } while (isCollision);
                physicsObjects.add(b);
            }
        }

        if (state.getLongBoi() != null) {
            LongBoi l = new LongBoi(this, state.getLongBoi());
            physicsObjects.add(l);
        }

        if (state.getBlessing() != null) {
            Blessing b = new Blessing(this, state.getBlessing().position, mapSize);
            physicsObjects.add(b);
        }

        Obstacle o;
        for (ObstacleState os : state.getObstacles()) {
            o = new Obstacle(this, os.position, new Texture(Gdx.files.internal(os.texturePath)));
            physicsObjects.add(o);
        }

        if (state.getStorm() != null) {
            // Spawn the Storm somewhere; collisions do not matter for weather effects.
            Storm s = new Storm(this, state.getStorm().position);
            physicsObjects.add(s);
        }

        //create the moving camera/map borders
        map = new GameMap(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                playerBoat, batch, (int) mapSize.x, (int) mapSize.y);
    }

    /**
     * Method to set the difficulty, using the method defined on the PlayerBoat (which is the only object affected by
     * difficulty).
     *
     * @param difficulty the difficulty of the game (Easy, Normal, or Hard).
     */
    public void setDifficulty(Difficulty difficulty) {
        playerBoat.setDifficulty(difficulty);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        // do updates here
        timer -= delta;
        if(timer <= 0) gameOver();
        
        // give the player XP and Plunder each frame, normalised using delta
        xpTick -= delta * xpTickMultiplier;
        if(xpTick <= 0){
            xp += 1;
            xpTick = 1;
        }
        
        hud.Update(delta);
        map.Update(delta);

        UpdateObjects(delta); //update all physicsobjects
        ClearKilledObjects(); //clear any 'killed' objects

        if(bossCollege.HP <= 0)
        { //if the boss college is dead, the game is won
            game.gotoScreen(Screens.gameWinScreen);
        }

        // do draws here
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(map.camera.combined); //set the sprite batch to use the correct camera

        batch.begin(); //begin the sprite batch
        
        map.Draw(batch);

        if (physicsObjects.size() > 0) //draw all the physics objects
        {
            for (PhysicsObject physicsObject : physicsObjects) {
                physicsObject.Draw(batch);
            }
        }


        hud.Draw(batch);
        batch.end(); //end the sprite batch

        //begin debug sprite batch
        boolean debugCollisions = false;
        if(debugCollisions) //this should be off during normal gameplay, but can be on to debug collisions
        {
            ShapeRenderer sr = new ShapeRenderer();
            sr.setProjectionMatrix(map.camera.combined);
            sr.begin(ShapeType.Line);
            for (PhysicsObject physicsObject : physicsObjects) {
                sr.polygon(physicsObject.collisionPolygon.getTransformedVertices());
            }
            sr.end();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            // collect all of the states to save together from the physics objects list.
            PlayerCollegeState playerCollege = null;
            ArrayList<EnemyCollegeState> enemyColleges = new ArrayList<>();
            BlessingState blessing = null;
            ChoppyWavesState choppyWaves = null;
            LongBoiState longBoi = null;
            ArrayList<ObstacleState> obstacles = new ArrayList<>();
            StormState storm = null;

            // TODO: if we have time, refactoring such that these Objects inherit
            // from some `SavableObject` (itself inheriting from PhysicsObject)
            // to simplify the loop would be ideal.
            for (PhysicsObject object : physicsObjects) {
                if (object instanceof PlayerCollege) {
                    playerCollege = ((PlayerCollege) object).getSaveState();
                } else if (object instanceof EnemyCollege) {
                    EnemyCollege college = (EnemyCollege) object;
                    int numBoats = 0;
                    for (PhysicsObject current : physicsObjects) {
                        if (current instanceof CollegeBoat) {
                            if (((CollegeBoat) current).college == college) {
                                numBoats++;
                            }
                        }
                    }
                    enemyColleges.add(college.getSaveState(numBoats));
                } else if (object instanceof Blessing) {
                    blessing = ((Blessing) object).getSaveState();
                } else if (object instanceof ChoppyWaves) {
                    choppyWaves = ((ChoppyWaves) object).getSaveState();
                } else if (object instanceof LongBoi) {
                    longBoi = ((LongBoi) object).getSaveState();
                } else if (object instanceof Obstacle) {
                    obstacles.add(((Obstacle) object).getSaveState());
                } else if (object instanceof Storm) {
                    storm = ((Storm) object).getSaveState();
                }
            }
            SaveState state = new SaveState(timer, xp, plunder, playerBoat.getSaveState(), playerCollege, enemyColleges, blessing, choppyWaves, longBoi, obstacles, storm);
            game.gameState = state.serialise();
            this.game.gotoScreen(Screens.menuScreen);
        }
    }

    /**
     * Updates all physics objects in the PhysicsObjects array
     *
     * @param  delta   time since last frame
     */
    public void UpdateObjects(float delta){
        for(int i=0; i < physicsObjects.size(); i++)
        {
            PhysicsObject current = physicsObjects.get(i);
            if(current instanceof College)
            { //colleges need a slightly different update method signature, so use that specifically for them
                current.Update(delta, playerBoat);
            }
            else
            { //other physics objects update
                current.Update(delta);
            }
            for(int j=0; j < physicsObjects.size(); j++)
            {
                PhysicsObject other = physicsObjects.get(j);
                if(i==j)
                    continue;

                if(current.CheckCollisionWith(other))
                {
                    current.OnCollision(other);
                }
            }
        }
        boolean playerIsInDanger = false;
        for (College college : colleges) {
            if (college.isInRange(playerBoat) && college instanceof EnemyCollege) {
                playerIsInDanger = true;
            }
        }

        if(playerIsInDanger)
            xpTickMultiplier = 2f;
        else
            xpTickMultiplier = 1f;
    }

    /**
     * Called when a college is destroyed
     * Makes sure the boss college will be made vulnerable after the rest of the
     * colleges are destroyed
     */
    public void CollegeDestroyed()
    {
        boolean foundCollege = false;
        AddXP(100);
        for (PhysicsObject current : physicsObjects) {
            if (current.getClass() == EnemyCollege.class) {
                EnemyCollege e = (EnemyCollege) current;
                if (e.HP > 0 && !e.invulnerable) // there is still a normal college alive
                {
                    foundCollege = true;
                    break;
                }
            }
        }
        if (!foundCollege)
        {
            bossCollege.becomeVulnerable();
        }
    }

    /**
     * Goes through all the PhysicsObjects and removes ones from the list
     * that have had the flag set (killOnNextTick) in a safe manner
     */
    public void ClearKilledObjects(){
        physicsObjects.removeIf(current -> current.killOnNextTick);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    /**
     * Ends the game with a failure screen.
     */
    public void gameOver(){
        game.timeUp = timer <= 0;
        game.gotoScreen(Screens.gameOverScreen);
    }

    /**
     * Called to give a reference to a new `PhysicsObject` to the `physicsObjects` list
     * @param  obj     the object to add
    */    
    public void NewPhysicsObject(PhysicsObject obj) {
        // A new PhysicsObject has been created, add it to the list, so it receives updates
        physicsObjects.add(obj);
    }

    /**
     * Add xp to the player's amount
     */
    public void AddXP(int amount){
        // Give the player an equal amount of gold and XP
        xp += amount;
        plunder += amount;
    }

}
