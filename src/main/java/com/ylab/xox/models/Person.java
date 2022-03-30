package com.ylab.xox.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс игрока для ведения статистики игр
 */

public class Person implements Serializable {

    private String name;

    private int gamesCount; // Количество боев игрока
    private int winsCount;  // Количество побед
    private int lossCount;  // Количество поражений
    private int drawCount;  // Количество игр в ничью

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWinsCount() {
        return winsCount;
    }

    // Далее идут 3 метода для инкрементирования количества побед, ничьих и поражений

    public void incrementWinsCount() {
        this.winsCount++;
        this.gamesCount++;
    }

    public void incrementLossCount() {
        this.lossCount++;
        this.gamesCount++;
    }

    public void incrementDrawCont() {
        this.drawCount++;
        this.gamesCount++;
    }

    /**
     * Переоопределяем класс для вывода информации об игроке
     */
    @Override
    public String toString() {
        return "Игрок " + name +
                ", количество боев = " + gamesCount +
                ", из них побед = " + winsCount +
                ", поражений = " + lossCount +
                ", ничьих = " + drawCount;
    }

    /**
     * Переопределили метод для сравнения игроков только по имени
     * чтобы проще было искать игрока в массиве из файла
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
