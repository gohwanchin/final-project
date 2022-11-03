package sg.edu.nus.server.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import sg.edu.nus.server.configs.JwtTokenUtil;
import sg.edu.nus.server.models.*;
import sg.edu.nus.server.services.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        Optional<UserModel> opt = userDetailsService.register(user);
        if (opt.isPresent())
            return ResponseEntity.ok(opt.get());
        else
            return ResponseEntity.badRequest().build();
    }

    private void authenticate(String username, String password) throws Exception {
		try {
            // authentication manager uses userDetailsService to get userDetails and compares the password if user exists
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
            logger.error(e.getMessage());
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
