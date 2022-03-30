package com.ylab.xox;

import com.ylab.xox.models.Gameplay;
import com.ylab.xox.models.Player;
import com.ylab.xox.parsers.Tags;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XML Stax writer
 */

public class XmlWriter implements GameWriter {
    @Override
    public void write(Gameplay gameplay) {
        // Получаем имя файла в который будем сохранять xml из дефолтного метода интерфейса GameWriter
        String fileName = getFilename(".xml");

        List<Player> gamers = gameplay.getGamers();
        String name1 = gamers.get(0).getName();
        String name2 = gamers.get(1).getName();
        char symbolX = gamers.get(0).getSymbol();
        char symbol0 = gamers.get(1).getSymbol();

        XMLOutputFactory output = XMLOutputFactory.newInstance();

        try {
            XMLStreamWriter writer = output.
                    createXMLStreamWriter(new FileOutputStream(fileName), "windows-1251");
            writer.writeStartDocument("windows-1251", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement(Tags.TAG_GAMEPLAY.getValue());
            writer.writeCharacters("\n\t");

            writer.writeEmptyElement(Tags.TAG_PLAYER.getValue()); // закрывать не надо
            writer.writeAttribute("id", "1");
            writer.writeAttribute("name", name1);
            writer.writeAttribute("symbol", String.valueOf(symbolX));
            writer.writeCharacters("\n\t");

            writer.writeEmptyElement(Tags.TAG_PLAYER.getValue()); // закрывать не надо
            writer.writeAttribute("id", "2");
            writer.writeAttribute("name", name2);
            writer.writeAttribute("symbol", String.valueOf(symbol0));
            writer.writeCharacters("\n\t");

            writer.writeStartElement(Tags.TAG_GAME.getValue()); // open tag Game

            List<String> steps = gameplay.getGame().getSteps().stream().map(x -> x.getText()).collect(Collectors.toList());

            for (int i = 0; i < steps.size(); i++) {        // далее цикл по кллекции выполненных шагов
                writer.writeCharacters("\n\t\t");
                writer.writeStartElement(Tags.TAG_STEP.getValue()); // open tag Step
                writer.writeAttribute("num", String.valueOf(i + 1));
                if (i % 2 == 0) {
                    writer.writeAttribute("playerId", "1");
                } else {
                    writer.writeAttribute("playerId", "2");
                }
                writer.writeCharacters(steps.get(i));
                writer.writeEndElement();
            }
            writer.writeCharacters("\n\t");
            writer.writeEndElement(); //close tag Game
            writer.writeCharacters("\n\t");
            writer.writeStartElement(Tags.TAG_GAMERESULT.getValue()); // open tag Gameresult
            if (gameplay.getGameResult() == null){
                writer.writeCharacters("Draw!");
            } else {
                writer.writeEmptyElement(Tags.TAG_PLAYER.getValue()); // закрывать не надо
                Player player = gameplay.getGameResult().getPlayer();
                writer.writeAttribute("id", String.valueOf(player.getId()));
                writer.writeAttribute("name", player.getName());
                writer.writeAttribute("symbol", String.valueOf(player.getSymbol()));
            }

            writer.writeEndElement(); //close tag Gameresult

            writer.writeCharacters("\n");
            // закрываем
            writer.writeEndElement(); //close Gameplay
            writer.writeCharacters("\n");
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }
}
