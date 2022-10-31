package sg.edu.nus.server.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.*;

public class TVSearchPage {
    private Integer page;
    private String query;
    private Integer totalResults;
    private Integer totalPages;
    private List<TvSummary> results;

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    public List<TvSummary> getResults() {
        return results;
    }
    public void setResults(List<TvSummary> results) {
        this.results = results;
    }
    
    public static TVSearchPage create(String json) {
        TVSearchPage page = new TVSearchPage();
        JsonObject o = Json.createReader(new StringReader(json)).readObject();
        page.setPage(o.getInt("page"));
        page.setTotalResults(o.getInt("total_results"));
        page.setTotalPages(o.getInt("total_pages"));
        List<TvSummary> results = o.getJsonArray("results").stream().map(r -> (JsonObject) r).map(r -> TvSummary.create(r)).toList();
        page.setResults(results);
        return page;
    }

    public JsonObject toJson() {
        JsonArrayBuilder tvArr = Json.createArrayBuilder();
        for (TvSummary tv: results)
            tvArr.add(tv.toJson());
            
        return Json.createObjectBuilder()
                .add("page", page)
                .add("query", query)
                .add("totalResults", totalResults)
                .add("totalPages", totalPages)
                .add("results", tvArr)
                .build();
    }

}
