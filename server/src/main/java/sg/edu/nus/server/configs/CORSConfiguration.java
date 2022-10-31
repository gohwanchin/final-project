package sg.edu.nus.server.configs;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CORSConfiguration implements WebMvcConfigurer{
    private String path;
    private String origins;
    
    public CORSConfiguration(String path, String origins) {
        this.path = path;
        this.origins = origins;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(path).allowedOrigins(origins);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
