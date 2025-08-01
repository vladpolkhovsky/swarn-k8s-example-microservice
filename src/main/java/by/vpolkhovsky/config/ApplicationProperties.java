package by.vpolkhovsky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private JwtProperties jwt;
    private String name;

    public record JwtProperties(String secret) {

    }
}
