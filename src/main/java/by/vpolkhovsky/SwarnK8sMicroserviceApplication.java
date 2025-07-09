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
public class SwarnK8sMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwarnK8sMicroserviceApplication.class, args);
    }

}
