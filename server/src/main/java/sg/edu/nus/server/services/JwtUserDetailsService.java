package sg.edu.nus.server.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import sg.edu.nus.server.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository userRepo; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pass = userRepo.loadUserByUsername(username);
        if (!pass.equals("null"))
            return new User(username, pass, new ArrayList<>());
        else
            throw new UsernameNotFoundException("Username %s not found".formatted(username));
    }
}
