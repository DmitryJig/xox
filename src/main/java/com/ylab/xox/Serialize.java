package com.ylab.xox;

import com.ylab.xox.models.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * В этом классе методы, реализующие запись и чтение коллекций с объектами игроков
 * объекты игроков содержат данные рейтинга (количество игр, побед, ничьих и поражений)
 */
public class Serialize {

    public static void writePersons(List<Person> outputList, String PATH) {
        // сериализация
        try (FileOutputStream outputStream = new FileOutputStream(PATH);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(outputList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> readPersons(String PATH) {
        // Десериализация
        List<Person> inputList = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {

            inputList = (List<Person>) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return inputList;
    }

    // метод инициализирует файл статистики пустым игроком (выполняется когда еще никто не играл)
    public static void initGeneralStatistics(String PATH){
        List<Person> noname = new ArrayList<>();
        Person nonamePerson = new Person("noname");
        noname.add(nonamePerson);
        writePersons(noname, PATH);
    }
}
