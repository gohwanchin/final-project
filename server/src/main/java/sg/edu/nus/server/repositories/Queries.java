package sg.edu.nus.server.repositories;

public interface Queries {
        public static final String SQL_GET_USER_BY_USERNAME_AND_PASS = "select * from users where username = ? and password = ?";
        public static final String SQL_LOAD_USER_BY_USERNAME = "select password from users where username = ?";
        public static final String SQL_ADD_USER = "insert into users(username, password, email, genre, phone) values(?, ?, ?, ?, ?)";
        public static final String SQL_GET_USER = "select * from users where username = ?";
        public static final String SQL_ADD_TITLE_TO_WATCHLIST = "insert into watchlists(username, id, date_added) values(?, ?, ?)";
        public static final String SQL_REMOVE_TITLE_FROM_WATCHLIST = "delete from watchlists where (username, id) = (?, ?)";
        public static final String SQL_CHECK_TITLE_EXISTS_IN_WATCHLIST = "select * from watchlists where (username, id) = (?, ?)";
        public static final String SQL_GET_WATCHLIST_BY_USER = "select id from watchlists where username = ?";
        public static final String SQL_UPLOAD_BLOB = "insert into profile(username, image, content_type) values(?, ?, ?)";
        public static final String SQL_GET_PROFILE = "select * from profile where username = ?";
        public static final String SQL_DELETE_PROFILE = "delete from profile where username = ?";
        public static final String SQL_ADD_RATING = "replace into ratings(username, id, date_added, rate) values(?, ?, ?, ?)";
        public static final String SQL_CLEAR_RATING = "delete from ratings where (username, id) = (?, ?)";
        public static final String SQL_GET_RATING = "select * from ratings where (username, id) = (?, ?)";
}
