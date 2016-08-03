package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.DbData;
import model.UserForm;
import org.controlsfx.control.Notifications;
import view.User;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                mi_child.setOnAction(mi_child_Click);
                mi.getItems().add(mi_child);
            });

            result.getMenus().add(mi);
        }

        return result;
    }

    /* Активация пункта меню */
    static EventHandler<ActionEvent> mi_child_Click = event -> {
        final String menuName = ((MenuItem)event.getSource()).getText();
        final String viewName = getViewName(menuName);
        final String appPath = System.getProperty("user.dir");

        File viewFile = Paths.get(appPath, viewName + ".xml").toFile();
        if(!viewFile.exists() || !viewFile.isFile()){
            Notifications.create().title("file not exists").text("file not exists:\n" + viewFile.getAbsolutePath()).showError();
        } else{
            System.out.println(viewFile.getAbsolutePath());
        }
    };

    private static String getViewName(final String menuName) {
        for(UserForm form: forms){
            if(form.item.equals(menuName)){
                return form.viewName;
            }
        }

        return null;
    }
}
