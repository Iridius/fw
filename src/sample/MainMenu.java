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

public class MainMenu extends MenuBar {
    public static MenuBar create(User current_user){
        MenuBar result = new MenuBar();
        Collection<UserForm> forms = DbData.getUserForms(current_user.getUserID());
        Collection<String> topItems = new HashSet<>();

        for(UserForm form: forms){
            topItems.add(form.parentItem);
        }

        for(String item: topItems){
            Menu mi = new Menu(item);
            result.getMenus().add(mi);
        }

        return result;
    }
}
