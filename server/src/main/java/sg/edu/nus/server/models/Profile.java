package sg.edu.nus.server.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    private byte[] image;
    private String contentType;
    
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public static Profile create(ResultSet rs) throws SQLException {
        Profile p = new Profile();
        p.setContentType(rs.getString("content_type"));
        p.setImage(rs.getBytes("image"));
        return p;
    }
}
