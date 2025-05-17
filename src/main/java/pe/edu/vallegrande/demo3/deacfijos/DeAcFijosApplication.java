package pe.edu.vallegrande.demo3.deacfijos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "pe.edu.vallegrande.demo3.deacfijos.repository")
public class DeAcFijosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeAcFijosApplication.class, args);
    }

}
