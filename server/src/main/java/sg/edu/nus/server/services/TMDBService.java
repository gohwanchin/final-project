package sg.edu.nus.server.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.server.models.TVSearchPage;
import sg.edu.nus.server.models.Tv;

@Service
public class TMDBService {

    private static final String API_URL = "https://api.themoviedb.org/3/";
    private Logger logger = LoggerFactory.getLogger(TMDBService.class);

    @Value("${tmdb.key}")
    String apiKey;

    public TVSearchPage searchTV(String query, Integer page) {
        query = query.replace(" ", "-");
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("/search/tv")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("language", "en-US")
                .queryParam("include_adult", false)
                .toUriString();
        logger.info(url);
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        logger.debug(resp.getBody());
        TVSearchPage searchPage = new TVSearchPage();
        if (resp.getStatusCodeValue() != 200) {
            searchPage.setTotalResults(0);
            return searchPage;
        }
        searchPage = TVSearchPage.create(resp.getBody());
        searchPage.setQuery(query);
        return searchPage;
    }

    public Optional<Tv> getTV(Integer id) {
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("/tv/")
                .path(id.toString())
                .queryParam("api_key", apiKey)
                .queryParam("language", "en-US")
                .queryParam("append_to_response", "aggregate_credits")
                .toUriString();
        logger.info(url);
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        logger.debug(resp.getBody());
        if (resp.getStatusCodeValue() != 200)
            return Optional.empty();
        Tv tv = Tv.create(resp.getBody());
        return Optional.of(tv);
    }

    public String getGenres() {
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .path("/genre/tv/list")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en-US")
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        logger.debug(resp.getBody());
        if (resp.getStatusCodeValue() != 200)
            return "null";
        return resp.getBody();
    }
}
