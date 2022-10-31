package sg.edu.nus.server.models;

import java.util.List;

import jakarta.json.*;

public class TvSummary {
    private Integer id;   
    private String backdrop;
    private String firstAirDate;
    private List<Integer> genreIds;
    private String name;
    private String originalLang;
    private String originalName;
    private List<String> originCountry;
    private String overview;
    private String poster;
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public static TvSummary create(JsonObject o) {
        TvSummary tv = new TvSummary();
        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        tv.setId(o.getInt("id"));
        if(o.isNull("backdrop_path"))
            tv.setBackdrop("null");
        else
            tv.setBackdrop(BASE_URL + o.getString("backdrop_path"));
        tv.setFirstAirDate(o.getString("first_air_date",""));
        JsonArray objGenreIds = o.getJsonArray("genre_ids");
        List<Integer> genreIds = objGenreIds.getValuesAs(JsonNumber::intValue);
        tv.setGenreIds(genreIds);
        tv.setName(o.getString("name"));
        tv.setOriginalLang(o.getString("original_language"));
        tv.setOriginalName(o.getString("original_name"));
        JsonArray objCountryList = o.getJsonArray("origin_country");
        List<String> countryList = objCountryList.getValuesAs(JsonString::getString);
        tv.setOriginCountry(countryList);
        tv.setOverview(o.getString("overview"));
        if(o.isNull("poster_path"))
            tv.setPoster("null");
        else
            tv.setPoster(BASE_URL + o.getString("poster_path"));
        tv.setPopularity(o.getJsonNumber("popularity").doubleValue());
        tv.setVoteAverage(o.getJsonNumber("vote_average").doubleValue());
        tv.setVoteCount(o.getInt("vote_count"));
        return tv;
    }

    public JsonObject toJson() {
        JsonArrayBuilder genreArr = Json.createArrayBuilder();
        for (Integer i : genreIds)
            genreArr.add(i);
        JsonArrayBuilder countryArr = Json.createArrayBuilder();
        for (String c : originCountry) 
            countryArr.add(c);
            
        return Json.createObjectBuilder()
                .add("id", id)
                .add("backdrop", backdrop)
                .add("firstAirDate", firstAirDate)
                .add("genreIds", genreArr)
                .add("name", name)
                .add("originalLang", originalLang)
                .add("originalName", originalName)
                .add("originCountry", countryArr)
                .add("poster", poster)
                .add("popularity", popularity)
                .add("voteAverage", voteAverage)
                .add("voteCount", voteCount)
                .build();
    }
}
