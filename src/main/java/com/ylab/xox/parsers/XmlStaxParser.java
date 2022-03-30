package com.ylab.xox.parsers;


import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Player;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Класс stax парсера XML файлов
 */

public class XmlStaxParser implements Reader {

    /**
     * Метод считывает из файла данные игроков и их ходы,
     * записывает все в объект класса Gameplay
     *
     * @param fileName имя файла для чтения
     * @return объект класса Gameplay со считанными в него данными
     */
    @Override
    public Gameplay getGameplay(String fileName) {

        Gameplay gameplay = new Gameplay();
        int id = 0;
        String name = "";
        Character symbol = 'X';
        boolean isGameResult = false; // флаг о том находимся ли мы внутри тэга GameResult
        try {
            XMLStreamReader reader = XMLInputFactory.newDefaultFactory().createXMLStreamReader(new FileInputStream(fileName));

            while (reader.hasNext()) {
                reader.next();

                if (reader.isStartElement()) {
                    // Если мы заходим в тэг GameResult ставим флаг в true
                    if (reader.getLocalName().equals(Tags.TAG_GAMERESULT.getValue())) {
                        isGameResult = true;
                    }
                    // Если имя тэга Player пишем в gameplay информацию об игроках
                    if (!isGameResult && reader.getLocalName().equals(Tags.TAG_PLAYER.getValue())) {

                        id = Integer.parseInt(reader.getAttributeValue(0));
                        name = reader.getAttributeValue(1);
                        symbol = reader.getAttributeValue(2).toCharArray()[0];
                        gameplay.addGamer(new Player(id, name, symbol));
                        continue;
                    }
                    // Если имя тэга Step заносим координату хода в коллекцию объекта gameplay
                    if (reader.getLocalName().equals(Tags.TAG_STEP.getValue())) {
                        gameplay.getGame().addStep(reader.getElementText());
                    }
                    // Если мы внутри тега Player и внутри тега GameResult записываем победителя в результат игры
                    // (если результат будет пуст будет распечатано "Draw!"
                    if (isGameResult && reader.getLocalName().equals(Tags.TAG_PLAYER.getValue())) {
                        id = Integer.parseInt(reader.getAttributeValue(0));
                        name = reader.getAttributeValue(1);
                        symbol = reader.getAttributeValue(2).toCharArray()[0];
                        gameplay.setGameResult(new Player(id, name, symbol));
                    }
                }
                // Если тег GameResult закрывающий возвращаем значение флага в false
                if (reader.isEndElement() && reader.getLocalName().equals(Tags.TAG_GAMERESULT.getValue())) {
                    isGameResult = false;
                }
            }
        } catch (
                FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return gameplay;
    }
}
