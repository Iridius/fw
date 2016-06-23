package model;

import sample.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DbData {
    private static final String _userName = "lsterpusers";
    private static final String _password = "ktycnhjqnhtcn";
    private static final String _url = "jdbc:sqlserver://SQLSRV;databaseName=LSTTEST";
    private static final String _driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Statement _statement;

    private static Statement getStatement() {
        if (_statement != null) {
            return _statement;
        }

        try {
            Class.forName(_driver);
            Connection conn = DriverManager.getConnection(_url, _userName, _password);
            _statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _statement;
    }

    public static User getCurrentUser(final String login, final String password) {
        String query = "SELECT * FROM dbo.Users AS u WHERE u.Login = '{login}' AND u.Password = '{password}'";
        query = query.replace("{login}", login);
        query = query.replace("{password}", password);

        try {
            ResultSet rs = getStatement().executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String fio = rs.getString("fio");

                return new User(id, fio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            ResultSet rs = getStatement().executeQuery(query);
            while(rs.next()){
                String parentItem = rs.getString("parentName");
                String name = rs.getString("name");

                result.add(new UserForm(parentItem, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
