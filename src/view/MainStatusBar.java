package view;

import controller.IConfigurable;
import controller.Login;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.DbData;
import org.controlsfx.control.StatusBar;

public class MainStatusBar implements IConfigurable {
    private static Button _btnUser;
    private static Button _btnCatalog;

    public StatusBar create(User current_user) {
        StatusBar statusBar = new StatusBar();
        DbData.addObserver(this);

        /* Профиль пользователя */
        _btnUser = new Button(current_user.getFio());
        statusBar.getLeftItems().add(_btnUser);

        /* Подключение к базе данных */
        _btnCatalog = new Button(DbData.getCatalog());
        _btnCatalog.setOnAction(event -> new Login());
        statusBar.getLeftItems().add(_btnCatalog);

        /* Версия */
        statusBar.getRightItems().add(new Label("v.0.0.1"));
        statusBar.setText("");

        return statusBar;
    }

    @Override
    public void run() {
        /* Реакция полосы статуса на изменение глобальной конфигурации - каталога БД, пользователя */
        _btnUser.setText(DbData.getUser().getFio());
        _btnCatalog.setText(DbData.getCatalog());
    }
}
