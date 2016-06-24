package controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import view.MainStatusBar;
import view.User;

import java.io.IOException;

public class Main {

    Main(User current_user) throws IOException {
        /* Основная форма */
        Stage primaryStage = new Stage();
        primaryStage.setTitle("fw");

        Label lab1 = new Label("Пользователь:");

        /* Главное меню */
        MenuBar menu = MainMenu.create(current_user);

        /* Панель статуса */
        StatusBar statusBar = (new MainStatusBar()).create(current_user);

        /* Компоновка */
        BorderPane bottom = new BorderPane();
        bottom.setPrefSize(800, 600);
        bottom.setTop(menu);
        bottom.setCenter(lab1);
        bottom.setBottom(statusBar);

        primaryStage.setScene(new Scene(bottom, 800, 600));
        primaryStage.show();
    }
}
