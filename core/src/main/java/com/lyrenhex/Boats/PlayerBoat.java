package com.lyrenhex.Boats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.lyrenhex.Colleges.EnemyCollege;
import com.lyrenhex.Colleges.PlayerCollege;
import com.lyrenhex.GameGenerics.PhysicsObject;
import com.lyrenhex.GameGenerics.Upgrades;
import com.lyrenhex.GameScreens.GameController;
import com.lyrenhex.GeneralControl.Difficulty;
import com.lyrenhex.Obstacles.ChoppyWaves;
import com.lyrenhex.Obstacles.Obstacle;
import com.lyrenhex.Obstacles.Storm;
import com.lyrenhex.Projectiles.Projectile;
import com.lyrenhex.Projectiles.ProjectileData;
import com.lyrenhex.Projectiles.ProjectileDataHolder;
import com.lyrenhex.Saves.PlayerBoatState;
import com.lyrenhex.Saves.SaveState;

/**
 * The boat under player control - in practice, this class represents the player.
 */
public class PlayerBoat extends Boat{
    float projectileDamageMultiplier = 1;
    float projectileSpeedMultiplier = 1;

    // The higher the defense, the stronger the player, this is subtracted from the damage
    int defense = 10;

    float timeSinceLastHeal = 0;

    boolean hasExtraCannons = false;
    boolean isImmune = false;
    float timeImmune = 0.0f;
    final float maxTimeImmune = 15.0f;

    boolean invertedControl = false;

    public ProjectileData projectileType;

    public PlayerBoat(GameController controller, Vector2 initialPosition, Vector2 mapSize) {
        this.projectileType = ProjectileDataHolder.stock;

        this.controller = controller;

        this.HP = 100;
        this.maxHP = 100;
        this.speed = 200;
        this.turnSpeed = 150;
        position = initialPosition;

        collisionPolygon.setPosition(initialPosition.x + GetCenterX()/2, initialPosition.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(25,50);
        collisionPolygon.setRotation(rotation - 90);

        sprite.setPosition(initialPosition.x, initialPosition.y);

        this.mapSize = mapSize;
        mapBounds = new Array<Vector2>(true, 4);
        mapBounds.add(new Vector2(0,0));
        mapBounds.add(new Vector2(mapSize.x, 0));
        mapBounds.add(new Vector2(mapSize.x, mapSize.y));
        mapBounds.add(new Vector2(0, mapSize.y));
    }

    public PlayerBoat(GameController controller, PlayerBoatState state, Vector2 mapSize) {
        this.controller = controller;

        this.HP = state.HP;
        this.maxHP = state.maxHP;
        this.speed = state.speed;
        this.turnSpeed = state.turnSpeed;
        this.defense = state.defense;
        this.position = state.position;
        this.projectileDamageMultiplier = state.projectileDamageMultiplier;
        this.projectileSpeedMultiplier = state.projectileSpeedMultiplier;
        this.hasExtraCannons = state.hasExtraCannons;
        this.isImmune = state.isImmune;
        this.timeImmune = state.timeImmune;
        this.projectileType = ProjectileDataHolder.fromEnum(state.projectileType);

        collisionPolygon.setPosition(position.x + GetCenterX()/2, position.y - GetCenterY()/2 - 10);
        collisionPolygon.setOrigin(25,50);
        collisionPolygon.setRotation(rotation - 90);

        sprite.setPosition(position.x, position.y);
        sprite.setTexture(new Texture(Gdx.files.internal(state.texturePath)));

        this.mapSize = mapSize;
        mapBounds = new Array<Vector2>(true, 4);
        mapBounds.add(new Vector2(0,0));
        mapBounds.add(new Vector2(mapSize.x, 0));
        mapBounds.add(new Vector2(mapSize.x, mapSize.y));
        mapBounds.add(new Vector2(0, mapSize.y));
    }
    
    @Override
    public void Update(float delta) {
        timeSinceLastShot += delta;

        if (isImmune) timeImmune += delta;
        if (timeImmune > maxTimeImmune) {
            isImmune = false;
            timeImmune = 0.0f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            Move(delta, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            Move(delta, -1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if (invertedControl)
                Turn(delta, 1);
            else
                Turn(delta, -1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if (invertedControl)
                Turn(delta, -1);
            else
                Turn(delta, 1);
        }

        if(((Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !controller.hud.hoveringOverButton) // make sure we don't fire when hovering over a button and clicking
        || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) // doesn't matter if we're over a button or not when pressing space
        && shotDelay <= timeSinceLastShot){
            Shoot();
            timeSinceLastShot = 0;
        }

        if(HP <= 0)
        {
            //the player is dead
            controller.gameOver();
        }

        // if controls are inverted, disable that
        // OnCollision is called after Update each frame, so it will reassert inverted controls if appropriate.
        invertedControl = false;
    }
    
    /*
        Method that executes when a collision is detected

        @param    other    the other object, as a PhysicsObject to be generic
    */
    @Override
    public void OnCollision(PhysicsObject other) {
        if(other instanceof Projectile){ //check the type of object passed
            Projectile p = (Projectile) other;
            if(! p.isPlayerProjectile)
            {
                p.killOnNextTick = true;
                if (!isImmune) {
                    HP -= (p.damage - defense);
                }
            }
        }
        else if (!isImmune) { // prevent damage or auto-loss whilst immune.
            if(other instanceof EnemyCollege || other instanceof PlayerCollege)
            {
                controller.gameOver();
            }
            else if ((other instanceof Boat && !(other instanceof Blessing)) || other instanceof Obstacle)
            {
                HP -= 50;
            }
            else if (other instanceof Storm)
            {
                Storm s = (Storm) other;
                if (s.isDamageAllowed()) {
                    HP -= 5;
                    controller.xp += 10; // grant bonus XP for sailing through storms.
                    s.DamageDealt();
                }
            }
            else if (other instanceof ChoppyWaves)
            {
                invertedControl = true;
            }
        }
    }

    @Override
    void Shoot(){
        Projectile proj = new Projectile(new Vector2(GetCenterX() + position.x, GetCenterY() + position.y),
                                         rotation, projectileType, true, this,
                                         projectileDamageMultiplier, projectileSpeedMultiplier);
        controller.NewPhysicsObject(proj); // Add the projectile to the GameController's physics objects list so it receives updates

        if (hasExtraCannons) {
            proj = new Projectile(new Vector2(GetCenterX() + position.x, GetCenterY() + position.y),
                    rotation - 90, projectileType, true, this,
                    projectileDamageMultiplier, projectileSpeedMultiplier);
            controller.NewPhysicsObject(proj);
            proj = new Projectile(new Vector2(GetCenterX() + position.x, GetCenterY() + position.y),
                    rotation + 90, projectileType, true, this,
                    projectileDamageMultiplier, projectileSpeedMultiplier);
            controller.NewPhysicsObject(proj);
        }
    }

    @Override
    void Destroy(){
        controller.gameOver();
    }

    /**
     * Allows the player to upgrade their boat
     *
     * @param    upgrade        The requested upgrade
     * @param    amount        the amount to upgrade by
    */
    public void Upgrade(Upgrades upgrade, float amount){
        if(upgrade == Upgrades.health) {
            HP = (int) Math.min(maxHP, HP + amount); // Keeps HP from exceeding max
        } else if(upgrade == Upgrades.maxhealth) {
            maxHP += amount;
            HP += amount; // Also heal the player, we're feeling generous.
        } else if(upgrade == Upgrades.speed) {
            speed += amount;
        } else if(upgrade == Upgrades.turnspeed) {
            turnSpeed += amount;
        } else if(upgrade == Upgrades.projectiledamage) {
            projectileDamageMultiplier += amount;
        } else if(upgrade == Upgrades.projectilespeed) {
            projectileSpeedMultiplier += amount;
        } else if(upgrade == Upgrades.defense) {
            defense += amount;
        }
    }

    @Override
    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Heals the player by the `amount` specified, multiplied by the time elapsed since the last heal.
     *
     * @param amount the base amount to heal the player.
     * @param delta the time delta since the last frame.
     */
    public void Heal(int amount, float delta)
    {
        timeSinceLastHeal += delta;
        if(amount * timeSinceLastHeal >= 1)
        {
            HP += amount * timeSinceLastHeal;
            timeSinceLastHeal = 0;
            if(HP > maxHP)
            {
                HP = maxHP;
            }
        }
    }

    /**
     * Called when starting a game, sets the difficulty conditions of the game.
     * Defaults to 'Normal' if not called on game start.
     *
     * @param difficulty the difficulty of the game (Easy, Normal, or Hard).
     */
    public void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case Easy:
                maxHP *= 2;
                HP *= 2;
                projectileDamageMultiplier *= 2;
                break;
            case Normal:
                break;
            case Hard:
                defense = 0;
                projectileDamageMultiplier /= 2;
        }
    }

    /**
     * Resets the immunity timer and makes the player immune.
     */
    public void setImmune() {
        timeImmune = 0.0f;
        isImmune = true;
    }

    /**
     * Returns whether the player is immune or not at the given time.
     *
     * @return whether the player is immune at the time.
     */
    public boolean isImmune() {
        return isImmune;
    }

    /**
     * Returns the time left on the player's immunity, which will be the maximum
     * allowed immunity time if the player is not immune when this method is called.
     *
     * @return the time left on the player's immunity.
     */
    public float remainingTimeImmune() {
        return maxTimeImmune - timeImmune;
    }

    /**
     * Grants the player the 'Extra Cannons' power-up, which adds two bonus projectiles
     * firing to each side when the player fires a cannonball.
     */
    public void addExtraCannons() {
        hasExtraCannons = true;
    }

    /**
     * Enables the booster for the player.
     */
    public void enableBooster() {
        sprite.setTexture(new Texture(Gdx.files.internal("img/boosterOn.png")));
        Upgrade(Upgrades.speed, 100);
        Upgrade(Upgrades.projectilespeed, 2);
    }

    /**
     * Obtains a serialisable form of the current state of the player object.
     * @return an object storing the state information of the player object.
     */
    public PlayerBoatState getSaveState() {
        return new PlayerBoatState(HP, maxHP, speed, turnSpeed, defense, position, projectileDamageMultiplier, projectileSpeedMultiplier, hasExtraCannons, isImmune, timeImmune, projectileType, sprite.getTexture());
    }
}

