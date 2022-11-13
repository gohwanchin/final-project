package sg.edu.nus.server.repositories;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.server.models.Profile;
import sg.edu.nus.server.models.UserModel;

@Repository
public class UserRepository implements Queries {

    @Autowired
    JdbcTemplate template;

    public String loadUserByUsername(String username) {
        SqlRowSet rs = template.queryForRowSet(SQL_LOAD_USER_BY_USERNAME, username);
        while (rs.next())
            return rs.getString("password");
        return "null";
    }

    public Boolean addUser(UserModel u) {
        int added = template.update(SQL_ADD_USER, u.getUsername(), u.getPassword(), u.getEmail(), u.getGenre(), u.getPhone());
        return added > 0;
    }

    public Optional<UserModel> getUserDetails(String username) {
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER, username);
        if (rs.next())
            return Optional.of(UserModel.create(rs));
        return Optional.empty();
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
        while (rs.next())
            list.add(rs.getInt("id"));
        return list;
    }

    public Boolean uploadProfile(MultipartFile file, String username) throws IOException {
        return template.update(SQL_UPLOAD_BLOB, username, file.getInputStream(), file.getContentType()) == 1;
    }

    public Optional<Profile> getProfile(String username) {
        return template.query(SQL_GET_PROFILE, (ResultSet rs) -> {
            if (!rs.next())
                return Optional.empty();
            return Optional.of(Profile.create(rs));
        }, username);
    }

    public Boolean deleteProfile(String username) {
        return template.update(SQL_DELETE_PROFILE, username) > 0;
    }

    public Boolean addRating(String username, Integer id, Integer rate) {
        int added = template.update(SQL_ADD_RATING, username, id, new Date(), rate);
        return added > 0;
    }

    public Boolean clearRating(String username, Integer id) {
        int removed = template.update(SQL_CLEAR_RATING, username, id);
        return removed > 0;
    }

    public Integer getRating(String username, Integer id) {
        SqlRowSet rs = template.queryForRowSet(SQL_GET_RATING, username, id);
        if (rs.next())
            return rs.getInt("rate");
        return 0;
    }
}
