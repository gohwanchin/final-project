package sg.edu.nus.server.repositories;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.server.models.UserModel;

@Repository
public class UserRepository implements Queries {
    
    @Autowired
    JdbcTemplate template;

    public Boolean userLogin(UserModel u) {
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_BY_USERNAME_AND_PASS, u.getUsername(), u.getPassword());
        return rs.next();
    }

    public String loadUserByUsername(String username) {
        SqlRowSet rs = template.queryForRowSet(SQL_LOAD_USER_BY_USERNAME, username);
        while(rs.next())
            return rs.getString("password");
        return "null";
    }

    public Boolean addUser(UserModel u) {
        int added = template.update(SQL_ADD_USER, u.getUsername(), u.getPassword());
        return added > 0;
    }

    public Boolean addTitleToWatchlist(String username, Integer id) {
        int added = template.update(SQL_ADD_TITLE_TO_WATCHLIST, username, id, new Date());
        return added > 0;
    }

    public Boolean removeTitleFromWatchlist(String username, Integer id) {
        int removed = template.update(SQL_REMOVE_TITLE_FROM_WATCHLIST, username, id);
        return removed > 0;
    }

    public Boolean checkTitleExistsInWatchlist(String username, Integer id) {
        SqlRowSet rs = template.queryForRowSet(SQL_CHECK_TITLE_EXISTS_IN_WATCHLIST, username, id);
        return rs.next();
    }

    public List<Integer> getWatchlist(String username) {
        List<Integer> list = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_WATCHLIST_BY_USER, username);
        while(rs.next())
            list.add(rs.getInt("id"));
        return list;
    }
}
