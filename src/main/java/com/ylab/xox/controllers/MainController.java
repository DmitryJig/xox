package com.ylab.xox.controllers;


import com.ylab.xox.*;
import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Person;
import com.ylab.xox.models.Player;
import com.ylab.xox.parsers.JsonParser;
import com.ylab.xox.parsers.ParserMain;
import com.ylab.xox.parsers.Reader;
import com.ylab.xox.parsers.XmlStaxParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/gameplay", "/"})
public class MainController {
    private final static String PATH = "src/main/java/com/ylab/xox/statistics/generalStatistics.bin";
    GameWriter xmlWtiter = new XmlWriter();
    GameWriter jsonWriter = new JsonWriter();
    Gameplay gameplay;
    List<Person> persons;
    List<String> gameStrings;
    int stepnumber;
    int index1;
    int index2;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/game")
    public String game(Model model){
        model.addAttribute("title", "Игра");
        return "game";
    }
    @PostMapping("/game/battle")
    public String battle(@RequestParam String playerName1, @RequestParam String playerName2,  Model model) {
        gameplay = new Gameplay();
        gameStrings = new ArrayList<>();
        stepnumber = 0;
        GameWithHuman.initMap();

        Person gamer1 = new Person(playerName1);
        Person gamer2 = new Person(playerName2);
        persons = Serialize.readPersons("src/main/java/com/ylab/xox/statistics/generalStatistics.bin");
        index1 = Main.getIndex(gamer1, persons);
        index2 = Main.getIndex(gamer2, persons);
        gameplay.addGamer(new Player(1, playerName1, 'X'));
        gameplay.addGamer(new Player(2, playerName2, '0'));

        gameStrings = GameWithHuman.printMap();
        model.addAttribute("gameStrings", gameStrings);
        model.addAttribute("title", "Битва");
        return "battle";
    }

    @GetMapping("/game/battle")
    public String battle(@RequestParam int stepX, @RequestParam int  stepY,  Model model) {
        gameStrings = new ArrayList<>();
        stepnumber++;
        String rating = "";
        String message = "";
        if (!(stepnumber % 2 == 0)){

            // ход игрока 1, в методе же проверяется валидность хода
            if (!GameWithHuman.springTurn('X', stepX - 1, stepY - 1, gameplay)) {
                model.addAttribute("message", "Неверный ход");
                stepnumber--;
                return "battle";
            }
            gameStrings.addAll(GameWithHuman.printMap());
            if (GameWithHuman.checkWin('X')){
                persons.get(index1).incrementWinsCount();
                persons.get(index2).incrementLossCount();
                model.addAttribute("message", "Побеждает " + gameplay.getGamers().get(0).getName());
                gameplay.setGameResult(gameplay.getGamers().get(0));
                xmlWtiter.write(gameplay);
                jsonWriter.write(gameplay);
                model.addAttribute("rating", Main.printRating(persons.get(index1), persons.get(index2), persons));
                Serialize.writePersons(persons, PATH);
                return "winPage";
            }
            if (GameWithHuman.isMapFull()){
                model.addAttribute("message", "Draw!");
                persons.get(index1).incrementDrawCont();
                persons.get(index2).incrementDrawCont();
                xmlWtiter.write(gameplay);
                jsonWriter.write(gameplay);
                Serialize.writePersons(persons, PATH);
                model.addAttribute("rating", Main.printRating(persons.get(index1), persons.get(index2), persons));;
                return "winPage";
            }
        } else {
            // ход игрока 2, в методе же проверяется валидность хода
            if (!GameWithHuman.springTurn('0', stepX - 1, stepY - 1, gameplay)) {
                model.addAttribute("message", "Неверный ход");
                stepnumber--;
                return "battle";
            }
            gameStrings.addAll(GameWithHuman.printMap());
            if (GameWithHuman.checkWin('0')){
                persons.get(index2).incrementWinsCount();
                persons.get(index1).incrementLossCount();
                model.addAttribute("message", "Побеждает " + gameplay.getGamers().get(1).getName());
                gameplay.setGameResult(gameplay.getGamers().get(1));
                xmlWtiter.write(gameplay);
                jsonWriter.write(gameplay);
                Serialize.writePersons(persons, PATH);
                model.addAttribute("rating", Main.printRating(persons.get(index1), persons.get(index2), persons));
                return "winPage";
            }
            if (GameWithHuman.isMapFull()){
                model.addAttribute("message", "Draw!");
                persons.get(index1).incrementDrawCont();
                persons.get(index2).incrementDrawCont();
                xmlWtiter.write(gameplay);
                jsonWriter.write(gameplay);
                Serialize.writePersons(persons, PATH);
                model.addAttribute("rating", Main.printRating(persons.get(index1), persons.get(index2), persons));
                return "winPage";
            }
        }

        model.addAttribute("gameStrings", gameStrings);
        model.addAttribute("title", "Битва");
        return "battle";
    }



    // выводим список сохраненных геймплеев в папке
    @GetMapping("/repr")
    public String reproduce(@RequestParam(name="name", required=false) String name, Model model){
        model.addAttribute("title", "Воспроизведение игры");
        List<String> listFiles = ParserMain.getListFiles();

        model.addAttribute("files", listFiles);
        if (!(name==null)){
            Reader reader = new XmlStaxParser();
            if (name.endsWith(".json")){
                reader = new JsonParser();
            }
            String fileName = "src/main/java/com/ylab/xox/savedFiles/" + name;
            Gameplay gameplay = reader.getGameplay(fileName);
            gameStrings = GameFromGameplay.game(gameplay);

            model.addAttribute("gameStrings", gameStrings);
        }
        return "repr";
    }
}
