package sg.edu.nus.server.models;

import java.util.List;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class CastSummary {
    private Integer id;
    private Integer gender;
    private String name;
    private Double popularity;
    private String profile;
    private List<Map<String, String>> roles;
    private Integer totalEpCount;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPopularity() {
        return popularity;
    }
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public List<Map<String, String>> getRoles() {
        return roles;
    }
    public void setRoles(List<Map<String, String>> roles) {
        this.roles = roles;
    }
    public Integer getTotalEpCount() {
        return totalEpCount;
    }
    public void setTotalEpCount(Integer totalEpCount) {
        this.totalEpCount = totalEpCount;
    }
    
    public static CastSummary create(JsonObject o) {
        CastSummary cast = new CastSummary();
        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        cast.setId(o.getInt("id"));
        cast.setGender(o.getInt("gender", 0));
        cast.setName(o.getString("name"));
        cast.setPopularity(o.getJsonNumber("popularity").doubleValue());
        if(o.isNull("profile_path"))
            cast.setProfile("null");
        else
            cast.setProfile(BASE_URL + o.getString("profile_path"));
        //TODO: roles
        cast.setTotalEpCount(o.getInt("total_episode_count"));
        return cast;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("gender", gender)
                .add("name", name)
                .add("popularity", popularity)
                .add("profile", profile)
                .add("totalEpCount", totalEpCount)
                .build();
    }
}
