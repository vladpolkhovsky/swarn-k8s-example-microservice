package by.vpolkhovsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableWebMvc
@EnableJpaRepositories(basePackages = "by.vpolkhovsky.repository")
public class SwarmK8sMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwarmK8sMicroserviceApplication.class, args);
    }

}
