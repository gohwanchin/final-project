package sg.edu.nus.server.models;

import java.util.List;

public class UserModel {
    private String username;
    private String password;
    private List<Tv> watchlist;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Tv> getWatchlist() {
        return watchlist;
    }
    public void setWatchlist(List<Tv> watchlist) {
        this.watchlist = watchlist;
    }
}
