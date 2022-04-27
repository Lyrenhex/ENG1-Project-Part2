package com.lyrenhex.Saves;

import com.google.gson.Gson;
import com.lyrenhex.Boats.PlayerBoat;
import com.lyrenhex.GameGenerics.PhysicsObject;

import java.util.ArrayList;

public class SaveState {
    public float timer;
    public int xp;
    public int plunder;

    PlayerBoatState player;
    PlayerCollegeState playerCollege;
    ArrayList<EnemyCollegeState> enemyColleges;
    BlessingState blessing;
    ChoppyWavesState choppyWaves;
    LongBoiState longBoi;
    ArrayList<ObstacleState> obstacles;
    StormState storm;

    public SaveState(float timer, int xp, int plunder, PlayerBoatState player, PlayerCollegeState playerCollege, ArrayList<EnemyCollegeState> enemyColleges, BlessingState blessing, ChoppyWavesState choppyWaves, LongBoiState longBoi, ArrayList<ObstacleState> obstacles, StormState storm) {
        this.timer = timer;
        this.xp = xp;
        this.plunder = plunder;
        this.player = player;
        this.playerCollege = playerCollege;
        this.enemyColleges = enemyColleges;
        this.blessing = blessing;
        this.choppyWaves = choppyWaves;
        this.longBoi = longBoi;
        this.obstacles = obstacles;
        this.storm = storm;
    }

    public String serialise() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public PlayerBoatState getPlayer() {
        return player;
    }

    public PlayerCollegeState getPlayerCollege() {
        return playerCollege;
    }

    public ArrayList<EnemyCollegeState> getEnemyColleges() {
        return enemyColleges;
    }

    public BlessingState getBlessing() {
        return blessing;
    }

    public ChoppyWavesState getChoppyWaves() {
        return choppyWaves;
    }

    public LongBoiState getLongBoi() {
        return longBoi;
    }

    public ArrayList<ObstacleState> getObstacles() {
        return obstacles;
    }

    public StormState getStorm() {
        return storm;
    }
}

