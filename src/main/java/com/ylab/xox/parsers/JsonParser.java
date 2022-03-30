package com.ylab.xox.parsers;

import com.google.gson.Gson;
import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.JsonGameplayRoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonParser implements Reader {
    @Override

    public Gameplay getGameplay(String fileName) {
        Gameplay gameplay = new Gameplay();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)))){

            Gson gson = new Gson();
            JsonGameplayRoot gRoot = gson.fromJson(bufferedReader, JsonGameplayRoot.class);
            gameplay = gRoot.getGameplay();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameplay;
    }
}
