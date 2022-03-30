package com.ylab.xox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.JsonGameplayRoot;
import com.ylab.xox.parsers.Reader;
import com.ylab.xox.parsers.XmlStaxParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements GameWriter{

    // метод для тестирования вывода json , можно в последствии удалить
    public static void main(String[] args) {

        String xmlFileName = "src/main/java/lessons/lesson_3/ticTacToe/savedFiles/gameplay15_03_2022_08_46_04.xml";
        Reader reader = new XmlStaxParser();
        Gameplay gameplay = reader.getGameplay(xmlFileName);
        JsonGameplayRoot jsonRoot = new JsonGameplayRoot(gameplay);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String json = gson.toJson(jsonRoot);

        System.out.println(json);
        try(FileWriter fileWriter= new FileWriter(new File("src/main/java/lessons/lesson_3/ticTacToe/savedFiles/forDelete.json"))){
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Gameplay gameplay) {
        // Получаем имя файла в который будем сохранять xml из дефолтного метода интерфейса GameWriter
        String fileName = getFilename(".json");
        JsonGameplayRoot jsonRoot = new JsonGameplayRoot(gameplay);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String json = gson.toJson(jsonRoot);
        try(FileWriter fileWriter= new FileWriter(new File(fileName))){
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
