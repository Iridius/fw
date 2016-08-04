package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.DbData;
import model.UserForm;
import org.controlsfx.control.Notifications;
import view.Main;
import view.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainMenu extends MenuBar {

    private static Collection<UserForm> forms;

    public static MenuBar create(User current_user){
        MenuBar result = new MenuBar();

        forms = DbData.getUserForms(current_user.getUserID());
        Collection<String> topItems = forms.stream().map(form -> form.parentItem).collect(Collectors.toSet());

        for(String item: topItems){
            Menu mi = new Menu(item);

            forms.stream().filter(form -> form.parentItem.equals(item)).forEach(form -> {
                MenuItem mi_child = new MenuItem(form.item);
                mi_child.setId(form.viewName);
                mi_child.setOnAction(mi_child_Click);
                mi.getItems().add(mi_child);
            });

            result.getMenus().add(mi);
        }

        return result;
    }

    /* Активация пункта меню */
    static EventHandler<ActionEvent> mi_child_Click = event -> {
        final UserForm form = getUserForm(((MenuItem) event.getSource()).getId());

        if(form.getFilePath() != null){
            System.out.println(form.getFilePath());
            Main.showForm(form);
        } else{
            Notifications.create().title("file not exists").text("file not exists:\n" + form.getFilePath()).showError();
        }
    };

    private static UserForm getUserForm(String viewName) {
        for(UserForm form: forms){
            if(form.viewName.equals(viewName)){
                return form;
            }
        }

        return null;
    }
}
