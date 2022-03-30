package com.ylab.xox;


import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Person;
import com.ylab.xox.models.Player;

import java.util.List;

/**
 * Класс игрового процесса при игре 2 человек
 * Тут же происходит запись каждой игры в XML файл
 */

public class GameWithHuman extends GameMethods {

    private static char SYMBOL_X = 'X';
    private static char SYMBOL_0 = '0';

    public static Gameplay game(int index1, int index2, List<Person> persons) {

        Gameplay gameplay = new Gameplay();
        // Достаем имена игроков
        String name1 = persons.get(index1).getName();
        String name2 = persons.get(index2).getName();
        Player player1 = new Player(1, name1, SYMBOL_X);
        Player player2 = new Player(2, name2, SYMBOL_0);
        gameplay.addGamer(player1);
        gameplay.addGamer(player2);


        int stepNumber = 0;

        initMap();
        printMap();

        while (true) {

            stepNumber++;

            if (!(stepNumber % 2 == 0)) {
                humanTurn(SYMBOL_X, gameplay); // ход игрока 1
                printMap();
                if (checkWin('X')) {
                    persons.get(index1).incrementWinsCount();
                    persons.get(index2).incrementLossCount();
                    System.out.println("Побеждает " + name1);
                    gameplay.setGameResult(player1);
                    break;
                }
                if (isMapFull()) {
                    System.out.println("Ничья");
                    persons.get(index1).incrementDrawCont();
                    persons.get(index2).incrementDrawCont();
                    break;

                }
            } else {
                humanTurn(SYMBOL_0, gameplay); // ход игрока 2
                printMap();
                if (checkWin('0')) {
                    persons.get(index2).incrementWinsCount();
                    persons.get(index1).incrementLossCount();
                    System.out.println("Побеждает " + name2);
                    gameplay.setGameResult(player2);
                    break;
                }
                if (isMapFull()) {
                    System.out.println("Ничья");
                    break;
                }
            }

        }
        return gameplay;
    }
}
