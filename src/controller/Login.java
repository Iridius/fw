package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DbData;
import org.controlsfx.control.Notifications;
import view.User;

import java.util.Collection;

public class Login {

    public User user;

    private Stage loginStage;
    private ComboBox<String> txtCatalog;
    private ComboBox<String> txtUser;
    private PasswordField txtPassword;
    private Button btn;

    public Login() {
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
        //String current_catalog = DbData.getCatalog() != null ? DbData.getCatalog() : "LSTTEST";
        txtCatalog = new ComboBox<>();
        txtCatalog.setEditable(true);
        //txtCatalog.getItems().add(current_catalog);
        //txtCatalog.setValue(current_catalog);
        grid.add(new Label("Каталог базы данных:"), 0, 1);
        grid.add(txtCatalog, 1, 1);

        /* Пользователь */
        txtUser = new ComboBox<>();
        txtUser.setEditable(true);
        txtUser.setOnKeyPressed(txtUser_KeyPressed);
        grid.add(new Label("Пользователь:"), 0, 2);
        grid.add(txtUser, 1, 2);

        /* Пароль */
        txtPassword = new PasswordField();
        txtPassword.setOnKeyPressed(txtPassword_KeyPressed);
        grid.add(new Label("Пароль:"), 0, 3);
        grid.add(txtPassword, 1, 3);

        /* Войти */
        btn = new Button("Войти");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
        btn.setOnAction(btn_Click);
        grid.add(hbBtn, 1, 4);

        /* Текст ошибки */
        final Text txtMessages = new Text();
        grid.add(txtMessages, 1, 6);

        /* Компоновка формы */
        loginStage = new Stage();
        loginStage.setTitle("Авторизация");
        loginStage.setScene(new Scene(grid, 400, 300));
        loginStage.addEventHandler(WindowEvent.WINDOW_SHOWN, loginStage_Show);
        loginStage.showAndWait();
    }

    /* Открытие (показ) формы авторизации */
    EventHandler<WindowEvent> loginStage_Show = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            Login.this.getCashedBases();
            Login.this.setCurrentBase();

            Login.this.getCashedUsers();
            Login.this.setCurrentUser();

            Login.this.setFocus();
        }
    };

    private void setCurrentBase() {
        if(DbData.getCatalog() != null){
            txtCatalog.setValue(DbData.getCatalog());
            return;
        }

        if(!txtCatalog.getItems().isEmpty()){
            txtCatalog.getSelectionModel().selectFirst();
        }
    }

    private void setCurrentUser() {
        if(DbData.getUser() != null){
            txtUser.setValue(DbData.getUser().getLogin());
            return;
        }

        if(!txtUser.getItems().isEmpty()){
            txtUser.getSelectionModel().selectFirst();
        }
    }

    private void getCashedBases() {
        Collection<String> catalogs = Config.getCatalogs();
        txtCatalog.getItems().removeAll();

        for(String catalog: catalogs){
            txtCatalog.getItems().add(catalog);
        }
    }

    private void getCashedUsers() {
        Collection<String> users = Config.getUsers();
        txtUser.getItems().removeAll();

        for(String user: users){
            txtUser.getItems().add(user);
        }
    }

    private void setFocus() {
        if(txtUser.getItems().isEmpty()){
            txtUser.requestFocus();
        } else {
            txtPassword.requestFocus();
        }
    }

    /* Переход по нажатию клавиши ENTER "пользователь" -> "пароль" */
    EventHandler<KeyEvent> txtUser_KeyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        }
    };

    /* Переход по нажатию клавиши ENTER на "пароль" -> "войти" */
    EventHandler<KeyEvent> txtPassword_KeyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                btn.fire();
            }
        }
    };

    /* Авторизация по нажатию клавиши ENTER на кпопке "войти". В случае успеха - запоминание настроек */
    EventHandler<ActionEvent> btn_Click = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DbData.init(txtCatalog.getSelectionModel().getSelectedItem(), txtUser.getValue(), txtPassword.getText());
            user = DbData.getUser();
            if (user != null) {
                try {
                    cashCurrentBase(DbData.getCatalog());
                    cashCurrentUser(DbData.getUser().getLogin());

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
        }
    };

    private void cashCurrentUser(String login) {
        Config.addUsers(login);
    }

    private void cashCurrentBase(String catalog) {
        Config.addCatalog(catalog);
    }
}
