package sg.edu.nus.server.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.server.models.Profile;
import sg.edu.nus.server.models.Response;
import sg.edu.nus.server.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    UserService userSvc;

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload")
    public ResponseEntity<String> uploadProfile(@RequestPart MultipartFile file, String username) {
        Response resp = new Response();

        if (!userSvc.uploadProfile(file, username)) {
            resp.setCode(400);
            resp.setMessage("Upload failed");
            return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
        }
        resp.setCode(200);
        resp.setMessage("Successfully uploaded profile picture");
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }

    @GetMapping("/p/{username}")
    public ResponseEntity<byte[]> getProfile(@PathVariable String username) {
        logger.info("Getting profile picture for %s".formatted(username));
        Optional<Profile> opt = userSvc.getProfile(username);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Profile p = opt.get();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(p.getContentType()))
                .body(p.getImage());
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<String> deleteProfile(@PathVariable String username) {
        Response resp = new Response();
        if (!userSvc.deleteProfile(username)) {
            resp.setCode(400);
        } else {
            resp.setCode(200);
            resp.setMessage("%s profile picture deleted".formatted(username));
        } 
        return ResponseEntity.status(HttpStatus.OK).body(resp.toJson().toString());
    }
}
