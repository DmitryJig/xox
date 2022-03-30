package com.ylab.xox.models;

import java.util.Objects;

/**
 * Класс - модель игрока для сохранения в геймплее
 */

public class Player {
    private int id;
    private String name;
    private char symbol;

    public Player(int id, String name, char symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && symbol == player.symbol && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol);
    }
}
