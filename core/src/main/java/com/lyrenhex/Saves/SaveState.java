package com.lyrenhex.Saves;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SaveState {
    float timer;
    int xp;
    int plunder;

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
}

