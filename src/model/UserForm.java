package model;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

public class UserForm {
    private String _parentItem;
    private String _item;
    private String _viewName;

    private File _file;
    private HashMap<String, String> _columns;

    UserForm(String parent, String item, String viewName){
        _parentItem = parent;
        _item = item;
        _viewName = viewName;

        _file = getViewFile(viewName);
        //_columns = getColumns(_file);
    }

    private static File getViewFile(final String viewName){
        final String appPath = System.getProperty("user.dir");
        File viewFile = Paths.get(appPath, viewName + ".xml").toFile();

        return viewFile.exists() && viewFile.isFile()? viewFile: null;
    }

    public HashMap<String, String> getColumns(){
        if(_columns == null){
            _columns = new HashMap<>();
        }

        //if(_columns.size() == 0){
        //    _columns.putAll(XmlParser.getFormColumns());
        //}

        return _columns;
    }
}
