package sg.edu.nus.server.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.server.models.*;
import sg.edu.nus.server.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    TMDBService tmdbSvc;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public Boolean login(UserModel u) {
        return userRepo.userLogin(u);
    }

    public Boolean addUser(UserModel u) {
        try {
            return userRepo.addUser(u);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean addTitleToWatchlist(String username, Integer id) {
        return userRepo.addTitleToWatchlist(username, id);
    }

    public Boolean removeTitleFromWatchlist(String username, Integer id) {
        return userRepo.removeTitleFromWatchlist(username, id);
    }

    public Boolean checkTitleExists(String username, Integer id) {
        return userRepo.checkTitleExistsInWatchlist(username, id);
    }

    public List<Tv> getWatchlist(String username) {
        List<Integer> list = userRepo.getWatchlist(username);
        logger.info(list.toString());
        List<Tv> watchlist = list.stream()
                .map(id -> tmdbSvc.getTV(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return watchlist;
    }

    public Boolean uploadProfile(MultipartFile file, String username) {
        try {
            return userRepo.uploadProfile(file, username);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public Optional<Profile> getProfile(String username) {
        try {
            return userRepo.getProfile(username);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean deleteProfile(String username) {
        return userRepo.deleteProfile(username);
    }
}
