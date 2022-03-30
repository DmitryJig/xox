package com.ylab.xox.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Gameplay {

    // коллекция с игроками
    @SerializedName("Player")
    private List<Player> players = new ArrayList<>();

    // Список шагов, выполненных игроками
    @SerializedName("Game")
    private Game game = new Game();

    // результат игры (объект игорка победителя), если он null то будет обработано как ничья
    @SerializedName("GameResult")
    private GameResult gameResult = null;

    //Геттеры
    public List<Player> getGamers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    /**
     * метод добавления игрока в коллекцию gamers
     */
    public void addGamer(Player player) {
        players.add(player);
    }

    /**
     * Простой сеттер результата игры
     * @param gameResult
     */
    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
    /**
     * Сеттер результата игры с входящим объектом игрока
     * @param player объект игрока победителя
     */
    public void setGameResult(Player player) {

        GameResult gameResult = new GameResult(player);
        this.gameResult = gameResult;
    }


}
