package sg.edu.nus.server.repositories;

public interface Queries {
    public static final String SQL_GET_USER_BY_USERNAME_AND_PASS 
            = "select * from users where username = ? and password = sha1(?)";
    public static final String SQL_ADD_USER = "insert into users(username, password) values(?, sha1(?))";
    public static final String SQL_ADD_TITLE_TO_WATCHLIST 
            = "insert into watchlists(username, id, date_added) values(?, ?, ?)";
    public static final String SQL_REMOVE_TITLE_FROM_WATCHLIST 
                = "delete from watchlists where (username, id) = (?, ?)";
    public static final String SQL_CHECK_TITLE_EXISTS_IN_WATCHLIST 
                = "select * from watchlists where (username, id) = (?, ?)";
    public static final String SQL_GET_WATCHLIST_BY_USER 
                = "select id from watchlists where username = ?";
}
