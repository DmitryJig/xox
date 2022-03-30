package com.ylab.xox.models;

import com.google.gson.annotations.SerializedName;

public class JsonGameplayRoot {

    @SerializedName("Gameplay")
    private Gameplay gameplay;

    public JsonGameplayRoot() {
    }
    public JsonGameplayRoot(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    public Gameplay getGameplay() {
        return gameplay;
    }
}
