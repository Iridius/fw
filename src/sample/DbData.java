package sample;

import org.controlsfx.control.Notifications;

import java.sql.*;

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

//        try {
//
//            ResultSet rs = statement.executeQuery("SELECT * FROM dbo.Users");
//
//            int i = 0;
//            while(rs.next()){
//                i++;
//            }
//
//            Notifications.create()
//                    .title("Record recieved")
//                    .text(String.valueOf(i))
//                    .showInformation();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

}
