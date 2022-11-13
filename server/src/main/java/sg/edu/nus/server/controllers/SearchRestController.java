package sg.edu.nus.server.controllers;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.json.*;
import sg.edu.nus.server.models.*;
import sg.edu.nus.server.services.TMDBService;
import sg.edu.nus.server.services.UserService;

@RestController
@RequestMapping("/api")
public class SearchRestController {

    private Logger logger = LoggerFactory.getLogger(SearchRestController.class);

    @Autowired
    TMDBService tmdbSvc;

    @Autowired
    UserService userSvc;

    @GetMapping("/search")
    public ResponseEntity<String> searchTV(@RequestParam String query, @RequestParam(defaultValue = "1") Integer page) {
        Response resp = new Response();
        TVSearchPage search = tmdbSvc.searchTV(query, page);
        resp.setCode(200);
        resp.setMessage("Search %s in TV".formatted(query));
        resp.setData(search.toJson().toString());
        logger.debug("Response: ", resp.toJson().toString());
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @PostMapping("/tv/{tv_id}")
    public ResponseEntity<String> getTV(@PathVariable Integer tv_id, @RequestBody String payload) {
        Response resp = new Response();

        JsonObject o = Json.createReader(new StringReader(payload)).readObject();
        String username = o.getString("username");

        Optional<Tv> opt = tmdbSvc.getTV(tv_id);
        if (opt.isEmpty()) {
            resp.setCode(404);
            resp.setMessage("ID %d does not exist".formatted(tv_id));
            resp.setData("");
            return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
        }
        Tv tv = opt.get();
        Integer rating = userSvc.getRating(username, tv_id);
        tv.setRating(rating);
        Boolean added = userSvc.checkTitleExists(username, tv_id);
        JsonObject obj = Json.createObjectBuilder().add("added", added).add("details", tv.toJson()).build();
        resp.setCode(200);
        resp.setMessage(tv.getName());
        resp.setData(obj.toString());
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @PostMapping("/tv/{tv_id}/add")
    public ResponseEntity<String> addToWatchlist(@PathVariable Integer tv_id, @RequestBody String payload) {
        Response resp = new Response();

        JsonObject o = Json.createReader(new StringReader(payload)).readObject();
        String username = o.getString("username");

        userSvc.addTitleToWatchlist(username, tv_id);
        resp.setCode(200);
        resp.setMessage("Successfully added");
        resp.setData("");
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @PostMapping("/tv/{tv_id}/remove")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Integer tv_id, @RequestBody String payload) {
        Response resp = new Response();

        JsonObject o = Json.createReader(new StringReader(payload)).readObject();
        String username = o.getString("username");

        userSvc.removeTitleFromWatchlist(username, tv_id);
        resp.setCode(200);
        resp.setMessage("Successfully removed");
        resp.setData("");
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @PostMapping("/tv/{tv_id}/rate")
    public ResponseEntity<String> addRating(@PathVariable Integer tv_id, @RequestBody String payload) {
        Response resp = new Response();

        JsonObject o = Json.createReader(new StringReader(payload)).readObject();
        Integer rate = o.getInt("rate");
        String username = o.getString("username");

        userSvc.addRating(username, tv_id, rate);
        resp.setCode(200);
        resp.setMessage("Successfully rated");
        resp.setData("");
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @PostMapping("/tv/{tv_id}/clearRating")
    public ResponseEntity<String> clearRating(@PathVariable Integer tv_id, @RequestBody String payload) {
        Response resp = new Response();

        JsonObject o = Json.createReader(new StringReader(payload)).readObject();
        String username = o.getString("username");

        userSvc.clearRating(username, tv_id);
        resp.setCode(200);
        resp.setMessage("Successfully cleared rating");
        resp.setData("");
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @GetMapping("/{username}/watchlist")
    public ResponseEntity<String> getWatchlist(@PathVariable String username) {
        Response resp = new Response();

        List<Tv> list = userSvc.getWatchlist(username);
        JsonArrayBuilder ab = Json.createArrayBuilder();
        for (Tv tv : list)
            ab.add(tv.toJson());
        JsonArray watchlist = ab.build();
        resp.setCode(200);
        resp.setMessage("%s watchlist".formatted(username));
        resp.setData(watchlist.toString());
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @GetMapping("/genres")
    public ResponseEntity<String> getGenres() {
        Response resp = new Response();

        String json = tmdbSvc.getGenres();
        if (json.equals("null")) {
            resp.setCode(404);
            resp.setMessage("There was an error getting list of genres");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp.toJson().toString());
        }
        resp.setCode(200);
        resp.setMessage("Successfully retrieved list of genres");
        resp.setData(json);
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }
}
