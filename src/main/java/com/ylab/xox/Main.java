package com.ylab.xox;



import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Person;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Главный класс если хотите поиграть
 */

public class Main {

    //Адрес файла где хранится рейтинг игроков,
    // изначально в нем не пустое значение что бы не было ошибки класса ObjectInputStream
    private final static String PATH = "src/main/java/com/ylab/xox/statistics/generalStatistics.bin";

    //Сканнер
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        // Считываем имена игроков и создаем обьекты игроков со считанными именами
        System.out.println("Игрок 1 введите свое имя");
        Person gamer1 = new Person(SCANNER.nextLine());
        System.out.println("Игрок 2 введите свое имя");
        Person gamer2 = new Person(SCANNER.nextLine());


        // десериализуем список игроков с их рейтингами из файла общей статистики(рейтинга всех игроков)
        List<Person> persons = Serialize.readPersons(PATH);

        // ищем индексы игроков в полученной из десериализации коллекции, сли их нет добавляем их в конец коллекции
        int index1 = getIndex(gamer1, persons);
        int index2 = getIndex(gamer2, persons);

        while (true) {
            // Игровой процесс  c возвратом записанного геймплея
            Gameplay gameplay = GameWithHuman.game(index1, index2, persons);

            //Создаем объекты писателя (XmlWriter для записи в формат xml и json)
            GameWriter xmlWtiter = new XmlWriter();
            GameWriter jsonWriter = new JsonWriter();

            // Передаем созданный после игры геймплей на запись
            xmlWtiter.write(gameplay);
            jsonWriter.write(gameplay);

            // После игры вызываем метод печатающий рейтинг игроков и их место в нем
            System.out.println(printRating(gamer1, gamer2, persons));

            System.out.println("Хотите сыграть еще? да/нет");
            String answer = SCANNER.nextLine();

            //если ответ не равен "да" выход из игры
            if (!answer.equalsIgnoreCase("да")) {
                break;
            }
        }

        // После всех боев записываем результаты игроков в файл (сериализуем)
        Serialize.writePersons(persons, PATH);
    }

    /**
     * Метод ищет во входящей коллекции индекс нахождения игрока,
     * если его нет то добавляет игрока в коллекцию и возвращает индекс его нахождения
     *
     * @param gamer   объект игрока
     * @param persons коллекция объектов игроков
     * @return индекс вхождения игрока в коллекции
     */
    public static int getIndex(Person gamer, List<Person> persons) {
        int index = 0;
        if (persons.contains(gamer)) {
            index = persons.indexOf(gamer);
        } else {
            persons.add(gamer);
            index = persons.size() - 1;
        }
        return index;
    }

    /**
     * Метод печатает рейтинг игроков и место только что игравших игроков в этом рейтинге
     * @param gamer1 объект игрока 1
     * @param gamer2 объект игрока 2
     * @param persons коллекция игроков
     */
    public static String printRating(Person gamer1, Person gamer2, List<Person> persons){
        // Сортируем коллекцию игроков в порядке убывания количества побед
        persons = persons.stream().sorted((x, y) -> (y.getWinsCount() - x.getWinsCount())).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        // Печатаем рейтинг игроков  (у кого больше побед те выше в списке)
        stringBuilder.append("Рейтинг игроков:\n");
        for (int i = 0; i < persons.size(); i++) {
            stringBuilder.append(i + 1).append(" место ").append(persons.get(i)).append("\n");

        }
        stringBuilder.append("Игрок ").append(gamer1.getName()).append(" Вы на ").append(persons.indexOf(gamer1) + 1).append(" месте\n");
        stringBuilder.append("Игрок ").append(gamer2.getName()).append(" Вы на ").append(persons.indexOf(gamer2) + 1).append(" месте\n");

        return stringBuilder.toString();
    }
}
