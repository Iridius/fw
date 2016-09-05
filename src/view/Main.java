package view;

import controller.MainMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.UserForm;
import org.controlsfx.control.StatusBar;

import java.io.IOException;

public class Main {

    private static TabPane _tabPane;

    public Main(User current_user) throws IOException {
        /* Основная форма */
        Stage primaryStage = new Stage();
        primaryStage.setTitle("fw");

        /* Главное меню */
        MenuBar menu = MainMenu.create(current_user);

        /* Панель статуса */
        StatusBar statusBar = (new MainStatusBar()).create(current_user);

        /* Рабочее пространство (либо рабочий стол, либо активные формы) */
        StackPane workspace = new StackPane();
        workspace.setAlignment(Pos.CENTER);

            /* Рабочий стол пользователя */
            Label lab1 = new Label("Пользователь:");
            workspace.getChildren().add(lab1);

            /* Панель форм */
            _tabPane = new TabPane();
            workspace.getChildren().add(_tabPane);

        /* Компоновка */
        BorderPane pane = new BorderPane();
        pane.setPrefSize(800, 600);
        pane.setTop(menu);
        pane.setCenter(workspace);
        pane.setBottom(statusBar);


        Scene scene = new Scene(pane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /* Показ сущности базы данных в табличном виде */
    public static void showForm(final UserForm form){
        Tab tab = new TableDisplay().create(form);
        _tabPane.getTabs().add(tab);

        if(!_tabPane.isVisible()){
            _tabPane.setVisible(true);
        }
    }


}
