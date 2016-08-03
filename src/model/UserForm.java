package model;

public class UserForm {
    public String parentItem;
    public String item;
    public String viewName;

    UserForm(String pi, String i, String path){
        parentItem = pi;
        item = i;
        viewName = path;
    }
}
