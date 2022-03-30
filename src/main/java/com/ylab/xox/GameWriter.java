package com.ylab.xox;


import com.ylab.xox.models.Gameplay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface GameWriter {

    void write(Gameplay gameplay);

    /**
     * Дефолтный метод для создания имени файла на основании даты и типа файла(его расширения)
     * @param fileType строка расширение файла(".xml", ".json")
     * @return строка имя файла с относительным путем
     */
    default String getFilename(String fileType) {
        // Файл для записи игры называем gameplay_dd_MM_yyyy_HH_mm_ss.xml чтобы не было одинаковых имен
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        String fileName = "src/main/java/com/ylab/xox/savedFiles/"
                + dateTime.format(formatter) + fileType;
        return fileName;
    }
}
