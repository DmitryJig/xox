package com.ylab.xox.models;

import com.google.gson.annotations.SerializedName;

public class GameResult {
    @SerializedName("Player")
    public Player player;

    public GameResult(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
