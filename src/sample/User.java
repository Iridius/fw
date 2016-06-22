package sample;

public class User {
    private int _userID;
    private String _fio;

    public User(int userID, String fio) {
        _userID = userID;
        _fio = fio;
    }

    public int getUserID() {
        return _userID;
    }

    public String getFio() {
        return _fio;
    }
}
