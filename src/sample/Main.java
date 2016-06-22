package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.io.IOException;

public class Main {

    Main(User current_user) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        /* Основная форма */
        Stage primaryStage = new Stage();
        primaryStage.setTitle("fw");

        /* Раскладка */
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//        grid.setMinSize(800, 600);

        Label lab1 = new Label("Пользователь:");
//        grid.add(labUserName, 0, 1);

        /* Главное меню */
        MenuBar menu = MainMenu.create(current_user);

        /* Панель статуса */
        StatusBar statusBar = new StatusBar();
        statusBar.getLeftItems().add(new Button(current_user.getFio()));
        statusBar.getRightItems().add(new Label("v.0.0.1"));
        statusBar.setText("");



        BorderPane bottom = new BorderPane();
        bottom.setPrefSize(800, 600);
        bottom.setTop(menu);
        bottom.setCenter(lab1);
        bottom.setBottom(statusBar);
        //bottom.setAlignment(statusBar, Pos.CENTER);





        //statusBar.setProgress(.5);
        //grid.getChildren().add(statusBar);

//        AnchorPane.setBottomAnchor(statusBar, 0.0);
//        AnchorPane.setLeftAnchor(statusBar, 0.0);
//        AnchorPane.setRightAnchor(statusBar, 0.0);

//
//        Text scenetitle = new Text("Авторизация");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        Label labUserName = new Label("Пользователь:");
//        grid.add(labUserName, 0, 1);
//
//        final TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Пароль:");
//        grid.add(pw, 0, 2);
//
//        final PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
//        Button btn = new Button("Войти");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 4);
//
//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 1, 6);
//
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                User user = DbData.getCurrentUser(userTextField.getText(), pwBox.getText());
//
//                if(user != null) {
//                    Notifications.create()
//                            .title("Текущий пользователь")
//                            .text("UserID: " + user.getUserID() + "\nFIO: " + user.getFio())
//                            .showInformation();
//                    try {
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                } else {
//                    Notifications.create()
//                            .title("Ошибка")
//                            .text("Неправильное имя пользователя или пароль")
//                            .showError();
//                }
//
//                //actiontarget.setFill(Color.FIREBRICK);
//                //actiontarget.setText("Sign in button pressed");
//            }
//        });


        primaryStage.setScene(new Scene(bottom, 800, 600));
        primaryStage.show();



    }
}
