package sg.edu.nus.server.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.*;

public class Tv {
    private Integer id;
    private String backdrop;
    private List<CastSummary> cast;
    private String firstAirDate;
    private List<String> genres;
    private String homepage;
    private String name;
    private Episode nextEp;
    private Integer numOfEps;
    private Integer numOfSeasons;
    private String originalLang;
    private String originalName;
    private List<String> originCountry;
    private String overview;
    private String poster;
    private Double popularity;
    private Integer rating;
    private List<SeasonSummary> seasons;
    private String tagline;
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


    public List<CastSummary> getCast() {
        return cast;
    }


    public void setCast(List<CastSummary> cast) {
        this.cast = cast;
    }


    public String getFirstAirDate() {
        return firstAirDate;
    }


    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }


    public List<String> getGenres() {
        return genres;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }


    public String getHomepage() {
        return homepage;
    }


    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Episode getNextEp() {
        return nextEp;
    }


    public void setNextEp(Episode nextEp) {
        this.nextEp = nextEp;
    }

    public Integer getNumOfEps() {
        return numOfEps;
    }


    public void setNumOfEps(Integer numOfEps) {
        this.numOfEps = numOfEps;
    }


    public Integer getNumOfSeasons() {
        return numOfSeasons;
    }


    public void setNumOfSeasons(Integer numOfSeasons) {
        this.numOfSeasons = numOfSeasons;
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

    public Integer getRating() {
        return rating;
    }


    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<SeasonSummary> getSeasons() {
        return seasons;
    }


    public void setSeasons(List<SeasonSummary> seasons) {
        this.seasons = seasons;
    }


    public String getTagline() {
        return tagline;
    }


    public void setTagline(String tagline) {
        this.tagline = tagline;
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


    public static Tv create(String json) {
        Tv tv = new Tv();
        JsonObject o = Json.createReader(new StringReader(json)).readObject();
        String BASE_URL = "https://image.tmdb.org/t/p/w500";

        tv.setId(o.getInt("id"));
        if(o.isNull("backdrop_path"))
            tv.setBackdrop("null");
        else
            tv.setBackdrop(BASE_URL + o.getString("backdrop_path"));
        JsonObject credits = o.getJsonObject("aggregate_credits");
        List<CastSummary> cast = credits.getJsonArray("cast").stream().map(c -> (JsonObject)c).map(c -> CastSummary.create(c)).toList();
        tv.setCast(cast);
        tv.setFirstAirDate(o.getString("first_air_date"));
        List<String> genres = o.getJsonArray("genres").stream().map(g -> (JsonObject) g).map(g -> g.getString("name")).toList();
        tv.setGenres(genres);
        tv.setHomepage("homepage");
        tv.setName(o.getString("name"));
        if(o.isNull("next_episode_to_air")){}
        else{
            Episode ep = Episode.create(o.getJsonObject("next_episode_to_air"));
            ep.setShowName(tv.getName());
            ep.setPoster("https://image.tmdb.org/t/p/w154" + o.getString("poster_path"));
            tv.setNextEp(ep);
        }
        tv.setNumOfEps(o.getInt("number_of_episodes"));
        tv.setNumOfSeasons(o.getInt("number_of_seasons"));
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
        List<SeasonSummary> seasons = o.getJsonArray("seasons").stream().map(s -> (JsonObject) s).map(s -> SeasonSummary.create(s)).toList();
        tv.setSeasons(seasons);
        tv.setTagline(o.getString("tagline"));
        tv.setVoteAverage(o.getJsonNumber("vote_average").doubleValue());
        tv.setVoteCount(o.getInt("vote_count"));
        return tv;
    }

    public JsonObject toJson() {
        JsonArrayBuilder castArr = Json.createArrayBuilder();
        for (CastSummary castSummary : cast) 
            castArr.add(castSummary.toJson());
        JsonArrayBuilder genreArr = Json.createArrayBuilder();
        for (String g : genres) 
            genreArr.add(g);
        JsonArrayBuilder countryArr = Json.createArrayBuilder();
        for (String c : originCountry)
            countryArr.add(c);
        JsonArrayBuilder seasonArr = Json.createArrayBuilder();
        for (SeasonSummary s : seasons)
            seasonArr.add(s.toJson());
        if (rating == null)
            this.rating = 0;
        JsonObjectBuilder ob = Json.createObjectBuilder();
        if (nextEp == null)
            ob.addNull("nextEp");
        else
            ob.add("nextEp", nextEp.toJson());
        
        return ob.add("id", id)
                .add("backdrop", backdrop)
                .add("cast", castArr)
                .add("firstAirDate", firstAirDate)
                .add("genres", genreArr)
                .add("homepage", homepage)
                .add("name", name)
                .add("numOfEps", numOfEps)
                .add("numOfSeasons", numOfSeasons)
                .add("originalLang", originalLang)
                .add("originalName", originalName)
                .add("originCountry", countryArr)
                .add("overview", overview)
                .add("poster", poster)
                .add("popularity", popularity)
                .add("rating", rating)
                .add("seasons", seasonArr)
                .add("tagline", tagline)
                .add("voteAverage", voteAverage)
                .add("voteCount", voteCount)
                .build();
    }
}
