package sg.edu.nus.server.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sg.edu.nus.server.models.*;
import sg.edu.nus.server.services.TMDBService;

@RestController
@RequestMapping("/api")
public class SearchRestController {
    @Autowired
    TMDBService tmdbSvc;
    
    private Logger logger = LoggerFactory.getLogger(SearchRestController.class);
    
    @GetMapping("/search")
    public ResponseEntity<String> searchTV(@RequestParam String query, @RequestParam(defaultValue = "1") Integer page) {
        Response resp = new Response();
        TVSearchPage search = tmdbSvc.searchTV(query, page);
        resp.setCode(200);
        resp.setMessage("Search %s in TV".formatted(query));
        resp.setData(search.toJson().toString());
        logger.info("Response: ", resp.toJson().toString());
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @GetMapping("/tv/{tv_id}")
    public ResponseEntity<String> getTV(@PathVariable Integer tv_id) {
        Response resp = new Response();
        Optional<Tv> opt = tmdbSvc.getTV(tv_id);
        if (opt.isEmpty()) {
            resp.setCode(404);
            resp.setMessage("ID %d does not exist".formatted(tv_id));
            resp.setData("");
        }
        Tv tv = opt.get();
        resp.setCode(200);
        resp.setMessage(tv.getName());
        resp.setData(tv.toJson().toString());
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }
}
