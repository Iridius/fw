package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DbData;
import org.controlsfx.control.Notifications;
import view.User;

public class Login {

    public User user;

    private Stage loginStage;

    public Login() {
        loginStage = new Stage();
        loginStage.setTitle("Hello World");

        /* Раскладка */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        /* Заголовок формы */
        Text scenetitle = new Text("Авторизация");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

         /* Каталог базы данных */
        final Label labCatalog = new Label("Каталог базы данных:");
        grid.add(labCatalog, 0, 1);

        String current_catalog = DbData.getCatalog() != null ? DbData.getCatalog() : "LSTTEST";
        final ComboBox txtCatalog = new ComboBox();
        txtCatalog.setEditable(true);
        txtCatalog.getItems().add(current_catalog);
        if(!txtCatalog.getItems().isEmpty()){
            txtCatalog.setValue(current_catalog);
        }

        grid.add(txtCatalog, 1, 1);

        /* Пользователь */
        Label labUserName = new Label("Пользователь:");
        grid.add(labUserName, 0, 2);

        final TextField txtUser = new TextField();
        txtUser.setText(DbData.getUser() != null ? DbData.getUser().getLogin() : "");
        grid.add(txtUser, 1, 2);

        /* Пароль */
        Label pw = new Label("Пароль:");
        grid.add(pw, 0, 3);

        final PasswordField txtPassword = new PasswordField();
        grid.add(txtPassword, 1, 3);

        /* Войти */
        Button btn = new Button("Войти");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //DbData.init(txtCatalog.getText(), txtUser.getText(), txtPassword.getText());
                DbData.init(txtCatalog.getSelectionModel().getSelectedItem().toString(), txtUser.getText(), txtPassword.getText());
                user = DbData.getUser();

                if (user != null) {
                    Notifications.create()
                            .title("Текущий пользователь")
                            .text("UserID: " + user.getUserID() + "\nFIO: " + user.getFio())
                            .showInformation();
                    try {
                        loginStage.close();
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

        /* Текст ошибки */
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        /* Итого  */
        loginStage.setScene(new Scene(grid, 400, 300));
        txtUser.requestFocus();
        loginStage.showAndWait();
    }
}
