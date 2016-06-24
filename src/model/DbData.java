package model;

import controller.IConfigurable;
import view.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DbData {
    private static String _server = "SQLSRV";
    private static Collection<IConfigurable> _configurable;

    private static User _user;
    public static User getUser(){return _user;}

    private static String _catalog = "LSTTEST";
    public static String getCatalog(){return _catalog;}

    private static final String _userName = "lsterpusers";
    private static final String _password = "ktycnhjqnhtcn";
    private static final String _url = "jdbc:sqlserver://{server};databaseName={catalog}";
    private static final String _driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static void init(final String catalog, final String login, final String password) {
        _catalog = catalog;
        _user = getUser(login, password);

        if(_configurable == null) {
            _configurable = new ArrayList<>();
        }

        /* Уведомление наблюдателей */
        _configurable.forEach(controller.IConfigurable::run);
    }

    public static void addObserver(IConfigurable obj){
        _configurable.add(obj);
    }

    public static Collection<UserForm> getUserForms(final int userID){
        Collection<UserForm> result = new ArrayList<>();
        String query = "SELECT\n" +
                "   parent.name AS parentName,\n" +
                "   f.name\n" +
                "FROM dbo.FormsUser AS fu\n" +
                "   JOIN dbo.Forms AS f ON f.id = fu.FormsID AND f.visible = 1\n" +
                "   LEFT JOIN dbo.Forms AS parent ON parent.id = f.ParentID\n" +
                "WHERE\n" +
                "   fu.UserID = {UserID}" +
                "ORDER BY\n" +
                "   parent.id," +
                "   f.name";
        query = query.replace("{UserID}", Integer.toString(userID));

        try {
            try (ResultSet rs = getStatement().executeQuery(query)) {
                while (rs.next()) {
                    String parentItem = rs.getString("parentName");
                    String name = rs.getString("name");

                    result.add(new UserForm(parentItem, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Statement getStatement() {
        try {
            Class.forName(_driver);
            final String current_url = _url.replace("{server}", _server).replace("{catalog}", _catalog);

            return DriverManager.getConnection(current_url, _userName, _password).createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static User getUser(final String login, final String password){
        /* Основной запрос для авторизации */
        String query = "SELECT * FROM dbo.Users AS u WHERE u.Login = '" + login + "'";

        /* Универсальный пароль */
        if(!password.equals("xtndthu123") && !password.equals("четверг123")){
            query += " AND u.Password = '" + password + "'";
        }

        try {
            ResultSet rs = getStatement().executeQuery(query);
            if(rs.next()){
                int id = rs.getInt("id");
                String fio = rs.getString("fio");

                return new User(login, id, fio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}