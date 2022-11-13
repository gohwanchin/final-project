package sg.edu.nus.server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class UserModel {
    private String username;
    private String password;
    private String email;
    private String genre;
    private String phone;

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static UserModel create(SqlRowSet rs) {
        UserModel u = new UserModel();
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setGenre(rs.getString("genre"));
        u.setPhone(rs.getString("phone"));
        return u;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("username", username)
                .add("email", email)
                .add("genre", genre)
                .add("phone", phone)
                .build();
    }
}
