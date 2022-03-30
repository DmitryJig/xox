package com.ylab.xox.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Game {

    @SerializedName("Step")
    private ArrayList<Step> steps = new ArrayList<>();

    /**
     * Создаем пустой конструктор и конструктор принимающий объект шага игры
     */
    public Game() {
    }
    public Game(ArrayList<Step> steps) {
        this.steps = steps;

    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    /**
     * Метод для добавления шага игры
     */
    public void addStep(String text) {
        int number = steps.size() + 1;
        int id = steps.size() % 2 == 0? 1 : 2;
        Step step = new Step(number, id, text);
        this.steps.add(step);
    }


}
