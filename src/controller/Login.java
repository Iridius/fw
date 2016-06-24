package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
    private ComboBox<String> txtCatalog;
    private TextField txtUser;
    private PasswordField txtPassword;
    private Button btn;

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
        String current_catalog = DbData.getCatalog() != null ? DbData.getCatalog() : "LSTTEST";
        txtCatalog = new ComboBox<>();
        txtCatalog.setEditable(true);
        txtCatalog.getItems().add(current_catalog);
        if(!txtCatalog.getItems().isEmpty()){
            txtCatalog.setValue(current_catalog);
        }
        grid.add(new Label("Каталог базы данных:"), 0, 1);
        grid.add(txtCatalog, 1, 1);

        /* Пользователь */
        txtUser = new TextField();
        txtUser.setText(DbData.getUser() != null ? DbData.getUser().getLogin() : "");
        txtUser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });
        grid.add(new Label("Пользователь:"), 0, 2);
        grid.add(txtUser, 1, 2);

        /* Пароль */
        txtPassword = new PasswordField();
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btn.fire();
            }
        });
        grid.add(new Label("Пароль:"), 0, 3);
        grid.add(txtPassword, 1, 3);

        /* Войти */
        btn = new Button("Войти");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
        btn.setOnAction(e -> {
            DbData.init(txtCatalog.getSelectionModel().getSelectedItem(), txtUser.getText(), txtPassword.getText());
            user = DbData.getUser();

            if (user != null) {
                try {
                    loginStage.close();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else {
                Notifications.create()
                        .title("Ошибка")
                        .text("Неправильное имя пользователя или пароль")
                        .showError();
            }
        });
        grid.add(hbBtn, 1, 4);

        /* Текст ошибки */
        final Text txtMessages = new Text();
        grid.add(txtMessages, 1, 6);

        /* Итого  */
        loginStage.setScene(new Scene(grid, 400, 300));
        if(txtUser.getText().length() == 0){
            txtUser.requestFocus();
        } else {
            txtPassword.requestFocus();
        }
        loginStage.showAndWait();
    }
}
