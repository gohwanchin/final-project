package sg.edu.nus.server.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${cors.pathMapping}")
    String path;
    
    @Value("${cors.origins}")
    String origins;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        logger.info("CORS: path=%s origins=%s".formatted(path, origins));
        return new CORSConfiguration(path, origins);
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
