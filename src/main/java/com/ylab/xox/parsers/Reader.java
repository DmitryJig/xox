package com.ylab.xox.parsers;


import com.ylab.xox.models.Gameplay;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public interface Reader {

    Gameplay getGameplay(String fileName);

    /**
     * Метод выводит список файлов нужного разрешения в указанной директории
     * и спрашивает у пользователя какой из файлов  надо распарсить
     * @param directory директория из которой выводим список файлов для парсинга
     * @return относительный путь выбранного файла
     * @throws IOException
     */
    default String getFileName(String directory, String fileType) throws IOException {
        File dir = new File(directory);
        File[] arrFiles = dir.listFiles();
        List<File> fileList = Arrays.asList(arrFiles);
        // Фильтруем список чтобы имена файлов имели расширение FILE_TYPE (xml,json...)
        List<File> cleanList = fileList.stream().filter(x -> x.getName().endsWith(fileType)).collect(Collectors.toList());
        // если директория пуста то возвращаем пустоту
        if (cleanList.size() == 0) {
            return null;
        }
        // Выводим список файлов
        for (int i = 0; i < cleanList.size(); i++) {
            System.out.println((i + 1) + " " + cleanList.get(i).getName());
        }
        System.out.println("Выберите из списка номер файла для воспроизведения хода игры");
        Scanner scanner = new Scanner(System.in);
        int fileNumber = scanner.nextInt();

        return cleanList.get(fileNumber - 1).getCanonicalPath();
    }
}
