package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Global {

    /* Создание каталога в операционной системе */
    public static void createFolder(String folder) {
        Path path = Paths.get(folder);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Проверка существования объекта (файла или папки) в операционной системе */
    public static boolean checkExists(String object) {
        File file = new File(object);
        return file.exists();
    }

    /* Удаление объекта (файла или папки) из операционной системы */
    public static void delete(String object) {
        File file = new File(object);
        file.delete();
    }
}
