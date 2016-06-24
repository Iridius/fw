package view;

public class User {
    private int _userID;
    private String _fio;
    private String _login;

    public User(String login, int userID, String fio) {
        _login = login;
        _userID = userID;
        _fio = fio;
    }

    public String getLogin() {return _login;}

    public int getUserID() {
        return _userID;
    }

    public String getFio() {
        return _fio;
    }

    @Override
    public String toString(){
        return _fio;
    }
}
