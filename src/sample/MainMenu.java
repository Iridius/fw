package sample;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.DbData;
import model.UserForm;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

public class MainMenu extends MenuBar {
    public static MenuBar create(User current_user){
        MenuBar result = new MenuBar();
        Collection<UserForm> forms = DbData.getUserForms(current_user.getUserID());
        Collection<String> topItems = forms.stream().map(form -> form.parentItem).collect(Collectors.toSet());

        for(String item: topItems){
            Menu mi = new Menu(item);

            forms.stream().filter(form -> form.parentItem.equals(item)).forEach(form -> {
                MenuItem mi_child = new MenuItem(form.item);
                mi.getItems().add(mi_child);
            });

            result.getMenus().add(mi);
        }

        return result;
    }
}
