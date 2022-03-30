package com.ylab.xox.models;

import java.util.Arrays;
import java.util.List;

public class Step {

    // Список для преобразования координат из формата с одной цифрой
    private static List<String> STEP_VALUES = Arrays.asList("11", "21", "31", "12", "22", "32", "13", "23", "33");

    private int num;
    private int playerId;
    private String text;

    public Step(int num, int playerId, String text) {
        this.num = num;
        this.playerId = playerId;
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public int getPlayerId() {
        return playerId;
    }

    /**
     * Геттер, на выходе валидирует значение координат
     * @return значение координат в формате "22"
     */
    public String getText() {
        return validationStep(text);
    }

    /**
     * метод преобразует любой допускаемый формат шагов к формату "22"
     *
     * @param step входящее значение хода в любом допустимом формате
     * @return строка шага в формате "22"
     */
    public String validationStep(String step) {
        if (step.length() == 1) {
            return STEP_VALUES.get(Integer.parseInt(step) - 1);

        } else if (step.length() > 2) {
            return step.replaceAll("[^0-9]", "");
        }
        return step;
    }

}
