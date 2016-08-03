package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

public class Config {

    private final static String CASHED_CATALOGS = "CashedCatalogs";
    private final static String CASHED_USERS = "CashedUsers";
    private final static String DELIMITER = ";";

    /* Добавление каталога баз данных в коллекцию (кэш) используемых */
    public static void addCatalog(String catalog) {
        add(CASHED_CATALOGS, catalog, false);
    }

    /* Коллекция (кэш) ранее используемых баз данных */
    public static Collection<String> getCatalogs() {
        String cashedCatalog = get(CASHED_CATALOGS);
        return split(cashedCatalog);
    }

    /* Добавление пользователя в коллекцию (кэш) авторизованных пользователей */
    public static void addUsers(String login) {
        add(CASHED_USERS, login, false);
    }

    /* Коллекция (кэш) пользователей, успешно авторизовавшихся ранее */
    public static Collection<String> getUsers(){
        String cashedUsers = get(CASHED_USERS);
        return split(cashedUsers);
    }

    private static Collection<String> split(String value){
        Collection<String> result = new ArrayList<>();
        if(value != null){
            String[] parts = value.split(DELIMITER);
            return Arrays.asList(parts);
        }

        return result;
    }

    private static String get(final String path) {
        Properties properties = new Properties();
        FileInputStream fis;

        File config = getConfig();

        try {
            fis = new FileInputStream(config);
            properties.load(fis);
            fis.close();

            return properties.getProperty(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void add(String path, String value, boolean overwrite) {
        Properties properties = new Properties();
        FileInputStream fis = null;
        FileOutputStream fos = null;

        File config = getConfig();

        try {
            fis = new FileInputStream(config);
            properties.load(fis);

            if(!overwrite){
                String cashedValue = properties.getProperty(path);

                if(cashedValue != null){
                    value = split(cashedValue).contains(value) ? cashedValue : value + ";" + cashedValue;
                }
            }

            properties.setProperty(path, value);

            fos = new FileOutputStream(config);
            properties.store(fos, "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static File getConfig() {
        File config = new File("config.cfg");
        if(!config.exists()){
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }
}
