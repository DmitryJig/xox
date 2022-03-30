package com.ylab.xox;

import com.ylab.xox.models.Gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * В этом классе проходит игровой процесс и содержатся методы для него
 */

public class GameMethods {

    //поле
    private static char[][] map;

    //размер поля любое целое положительное число
    public static final int SIZE = 3;

    //точек для победы любое целое положительное число меньшее или равно SIZE
    public static final int DOTS_TO_WIN = 3;

    /**
     * Инициализируем поле map значениями '-'
     */
    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = '-';
            }
        }
    }

    /**
     * Вывод игрового поля на экран
     */
    public static List<String> printMap() {
        List<String> gameStrings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= SIZE; i++) {
            stringBuilder.append(i + " ");
            System.out.print(i + " ");
        }
        gameStrings.add(stringBuilder.toString());
        System.out.println();
        stringBuilder.setLength(0);
        for (int i = 0; i < SIZE; i++) {
            stringBuilder.append(i + 1).append("|");
            System.out.print((i + 1) + "|");
            for (int j = 0; j < SIZE; j++) {
                stringBuilder.append(map[i][j]).append("|");
                System.out.print(map[i][j] + "|");
            }
            gameStrings.add(stringBuilder.toString());
            System.out.println();
            stringBuilder.setLength(0);
        }
        return gameStrings;
    }

    /**
     * ход игрока (человека)
     */
    public static void humanTurn(char symbol, Gameplay gameplay) {
        Scanner scanner = new Scanner(System.in);
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = symbol;

            gameplay.getGame().addStep((x + 1) + "" + (y + 1));

        System.out.println("Вы сходили в точку " + (x + 1) + " " + (y + 1));
    }

    public static boolean springTurn(char symbol, int x, int y, Gameplay gameplay) {
        if (!isCellValid(x, y)){
            return false;
        }
        map[y][x] = symbol;

        gameplay.getGame().addStep((x + 1) + "" + (y + 1));

        System.out.println("Вы сходили в точку " + (x + 1) + " " + (y + 1));
        return true;
    }

    public static void gameplayTurn(char symbol, int x, int y, List<String> gameStrings) {
        map[y - 1][x - 1] = symbol;
        gameStrings.add("Ход в точку " + x + " " + y);
    }

    /**
     * проверка валидны ли значения поля
     *
     * @param x координата х
     * @param y координата у
     * @return да если сходить можно, нет если место занято или за пределами поля
     */
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        if (map[y][x] != '-') {
            return false;
        }
        return true;
    }

    /**
     * Проверка победы, метод работает с любым размером поля, с любым количеством точек для победы
     *
     * @param symbol символ для которого проверяем крестик или нолик
     * @return true если выиграл
     */
    public static boolean checkWin(char symbol) {

        //проверяем строки и колонки
        for (int i = 0; i < SIZE; i++) {
            int dotCountX = 0;
            int dotCountY = 0;
            for (int j = 0; j < SIZE; j++) {
                dotCountX = map[i][j] == symbol ? ++dotCountX : 0;
                dotCountY = map[j][i] == symbol ? ++dotCountY : 0;
                if (dotCountX >= DOTS_TO_WIN || dotCountY >= DOTS_TO_WIN) {
                    return true;
                }
            }
        }
        // диагональ слева направо сверху вниз

        int countPositions = SIZE - DOTS_TO_WIN + 1; // количество линий, по которым можно выиграть на одной оси

        for (int startPos = 0; startPos < countPositions; startPos++) { // стартовая позиция счетчика
            int j = 0;
            int dotCount1 = 0; // счетчик точек слева направо сверху вниз на диагонали и ниже
            int dotCount2 = 0; // счетчик точек слева направо сверху вниз на диагонали и выше
            for (int i = startPos; i < SIZE; i++) {
                dotCount1 = map[i][j] == symbol ? ++dotCount1 : 0;
                dotCount2 = map[j][i] == symbol ? ++dotCount2 : 0;
                if (dotCount1 >= DOTS_TO_WIN || dotCount2 >= DOTS_TO_WIN) {
                    return true;
                }
                j++;
            }
        }

        // диагональ слева направо снизу вверх

        for (int startPos = SIZE - 1; startPos >= (SIZE - countPositions); startPos--) {
            int j = 0;
            int dotCount1 = 0;
            int dotCount2 = 0;
            for (int i = startPos; i >= 0; i--) {
                dotCount1 = map[i][j] == symbol ? ++dotCount1 : 0;
                dotCount2 = map[SIZE - j - 1][SIZE - i - 1] == symbol ? ++dotCount2 : 0;
                if (dotCount1 >= DOTS_TO_WIN || dotCount2 >= DOTS_TO_WIN) {
                    return true;
                }
                j++;
            }
        }
        return false;
    }

    /**
     * Проверка что в поле не осталось ни одной свободной ячейки
     *
     * @return true если нет ни одной свободной
     */
    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}
