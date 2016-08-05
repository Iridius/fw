package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Global {

    public static final String TAG_WINDOW_CAPTION = "DataFormProperties.WindowCaption";
    public static final String TAG_GRID_LAYOUT_XML = "DataFormProperties.GridLayoutXml";
    public static final String TAG_ROW_COLOR_COLUMN_NAME = "Grid.GridProperties.RowColorColumnName";

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

    /* Получение файла по его частичному имени */
    public static File getFile(String fileName){


        final String appPath = System.getProperty("user.dir");
        File viewFile = Paths.get(appPath, fileName.toLowerCase().contains(".xml")?fileName: fileName + ".xml").toFile();

        return viewFile.exists() && viewFile.isFile()? viewFile: null;
    }
}
