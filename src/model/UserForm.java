package model;

import controller.Global;
import controller.XmlParser;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserForm {
    private static final String REGEX = "\\((.+)\\)\\.(.+)";

    private String _parentItem;
    private String _item;
    private String _viewName;
    private File _file;

    private Collection<UserFormColumn> _columns;

    public UserForm addMenu(String parent, String item){
        _parentItem = parent;
        _item = item;

        return this;
    }

    public UserForm addView(String viewName){
        _viewName = viewName;
        if(_viewName != null) {
            _file = Global.getFile(viewName);
        }
        if(_file != null) {
            _columns = getColumns();
        }

        return this;
    }

    public Collection<UserFormColumn> getColumns(){
        if(_columns != null){
            return _columns;
        }

        _columns = new HashSet<>();
        if(_file == null){
            return _columns;
        }

        String gridLayout = XmlParser.parse(_file).get(Global.TAG_GRID_LAYOUT_XML);
        HashMap<String, String> columns = XmlParser.parse(gridLayout);
        Pattern pattern = Pattern.compile(REGEX);

        for(String key: columns.keySet()){
            Matcher matcher = pattern.matcher(key);
            if(matcher.find()){
                UserFormColumn column = getOrCreateColumn(matcher.group(1));
                column.put(matcher.group(2), columns.get(key));
                _columns.add(column);
            }
        }
        return _columns;
    }

    public UserFormColumn getColumn(String attribute){
        if(_columns == null || _columns.size() == 0){
            return null;
        }

        for(UserFormColumn column: _columns){
            if(column.get("key").equals(attribute.toLowerCase())){
                return column;
            }
        }

        return null;
    }

    private UserFormColumn getOrCreateColumn(String key) {
        UserFormColumn result = getColumn(key);
        if(result != null){
            return result;
        }

        return new UserFormColumn(key);
    }

    public String getItem() {
        return _item;
    }

    public String getParentItem() {
        return _parentItem;
    }

    public String getViewName() {
        return _viewName;
    }
}
