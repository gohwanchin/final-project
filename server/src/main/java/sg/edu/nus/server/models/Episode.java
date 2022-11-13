package sg.edu.nus.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Episode {
    private Integer id;
    private String airDate;
    private Integer episodeNum;
    private String name;
    private String overview;
    private String poster;
    private Integer seasonNum;
    private String showName;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAirDate() {
        return airDate;
    }
    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
    public Integer getEpisodeNum() {
        return episodeNum;
    }
    public void setEpisodeNum(Integer episodeNum) {
        this.episodeNum = episodeNum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Integer getSeasonNum() {
        return seasonNum;
    }
    public void setSeasonNum(Integer seasonNum) {
        this.seasonNum = seasonNum;
    }
    public String getShowName() {
        return showName;
    }
    public void setShowName(String showName) {
        this.showName = showName;
    }
    
    public static Episode create(JsonObject o) {
        Episode ep = new Episode();
        ep.setId(o.getInt("id"));
        ep.setAirDate(o.getString("air_date", "null"));
        ep.setEpisodeNum(o.getInt("episode_number"));
        ep.setName(o.getString("name"));
        ep.setOverview(o.getString("overview"));
        ep.setSeasonNum(o.getInt("season_number"));
        return ep;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("airDate", airDate)
                .add("episodeNum", episodeNum)
                .add("name", name)
                .add("overview", overview)
                .add("poster", poster)
                .add("seasonNum", seasonNum)
                .add("showName", showName)
                .build();
    }
}
