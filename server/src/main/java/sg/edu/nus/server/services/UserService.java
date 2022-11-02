package sg.edu.nus.server.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.server.models.Tv;
import sg.edu.nus.server.models.UserModel;
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

    public Boolean addTitleToWatchlist(UserModel u, Integer id) {
        return userRepo.addTitleToWatchlist(u.getUsername(), id);
    }

    public Boolean removeTitleFromWatchlist(UserModel u, Integer id) {
        return userRepo.removeTitleFromWatchlist(u.getUsername(), id);
    }

    public Boolean checkTitleExists(UserModel u, Integer id) {
        return userRepo.checkTitleExistsInWatchlist(u.getUsername(), id);
    }

    public List<Tv> getWatchlist(UserModel u) {
        List<Integer> list = userRepo.getWatchlist(u.getUsername());
        logger.info(list.toString());
        List<Tv> watchlist = list.stream()
                .map(id -> tmdbSvc.getTV(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return watchlist;
    }
}
