package sg.edu.nus.server.services;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.nus.server.models.UserModel;

@Service
public class JwtUserDetailsService implements UserDetailsService{

    @Autowired
    UserService userSvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pass = userSvc.loadUserByUsername(username);
        if (!pass.equals("null"))
            return new User(username, pass, new ArrayList<>());
        else{
            logger.error("Username %s not found".formatted(username));
            throw new UsernameNotFoundException("Username %s not found".formatted(username));
        }
    }

    public Optional<UserModel> register(UserModel u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        if (userSvc.addUser(u))
            return Optional.of(u);
        else    
            return Optional.empty();
    }
}
