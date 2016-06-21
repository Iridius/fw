package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class Login {

    Stage primaryStage;

    Login () throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage = new Stage();
        primaryStage.setTitle("Hello World");

        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Авторизация");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label labUserName = new Label("Пользователь:");
        grid.add(labUserName, 0, 1);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Пароль:");
        grid.add(pw, 0, 2);

        final PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Войти");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                User user = DbData.getCurrentUser(userTextField.getText(), pwBox.getText());

                if(user != null) {
                    Notifications.create()
                            .title("Текущий пользователь")
                            .text("UserID: " + user.getUserID() + "\nFIO: " + user.getFio())
                            .showInformation();
                    try {
                        primaryStage.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Notifications.create()
                            .title("Ошибка")
                            .text("Неправильное имя пользователя или пароль")
                            .showError();
                }
            }
        });

        primaryStage.setScene(new Scene(grid, 300, 275));
        primaryStage.showAndWait();

    }
}
