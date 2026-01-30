package uadb.gestion_commande.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Plus spécifique que "/**"
                .allowedOrigins(
                        "http://localhost:8000",  // Votre frontend
                        "http://localhost:3000",  // React dev
                        "http://localhost:4200",  // Angular dev
                        "http://localhost:5173"   // Vite dev
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type", "Content-Disposition")
                .allowCredentials(true)  // ← CHANGEZ À true
                .maxAge(3600);
    }
}