package by.vpolkhovsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SwarnK8sMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwarnK8sMicroserviceApplication.class, args);
	}

}
