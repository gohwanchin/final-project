package sg.edu.nus.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class SeasonSummary {
    private Integer id;
    private String airDate;
    private Integer episodeCount;
    private String name;
    private String overview;
    private String poster;
    private Integer seasonNum;

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

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
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

    public static SeasonSummary create(JsonObject o) {
        SeasonSummary season = new SeasonSummary();
        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        season.setId(o.getInt("id"));
        season.setAirDate(o.getString("air_date", ""));
        season.setEpisodeCount(o.getInt("episode_count"));
        season.setName(o.getString("name"));
        season.setOverview(o.getString("overview"));
        if (o.isNull("poster_path"))
            season.setPoster("null");
        else
            season.setPoster(BASE_URL + o.getString("poster_path"));
        season.setSeasonNum(o.getInt("season_number"));
        return season;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("airDate", airDate)
                .add("episodeCount", episodeCount)
                .add("name", name)
                .add("overview", overview)
                .add("poster", poster)
                .add("seasonNum", seasonNum)
                .build();
    }
}
