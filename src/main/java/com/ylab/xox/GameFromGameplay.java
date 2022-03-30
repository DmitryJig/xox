package com.ylab.xox;


import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс воспроизводит ход игры из объекта gameplay возвращая коллекцию строк игры
 */
public class GameFromGameplay extends GameMethods {

    public static List<String> game(Gameplay gameplay) {
        // коллекция строк игры
        List<String> gameStrings = new ArrayList<>();

        List<Player> gamers = gameplay.getGamers();
        String name1 = gamers.get(0).getName();
        String name2 = gamers.get(1).getName();
        char symbolX = gamers.get(0).getSymbol();
        char symbol0 = gamers.get(1).getSymbol();

        char symbol;

        List<String> steps = gameplay.getGame().getSteps().stream().map(x -> x.getText()).collect(Collectors.toList());

        initMap();

        int stepNumber = 0;

        for (String step : steps) {
            stepNumber++;
            int x = Character.getNumericValue(step.charAt(0));
            int y = Character.getNumericValue(step.charAt(1));
            if(!(stepNumber % 2 == 0)){
                symbol = symbolX;
            } else {
                symbol = symbol0;
            }
            gameplayTurn(symbol, x, y, gameStrings);
            gameStrings.addAll(printMap());
        }
        if (gameplay.getGameResult() == null){
            gameStrings.add("Draw!");
        } else {
            Player result = gameplay.getGameResult().getPlayer();
            gameStrings.add("Player " + result.getId() + " -> " + result.getName()
                    + " is winner as '" + result.getSymbol() +"'!");

        }
        return gameStrings;
    }
}


