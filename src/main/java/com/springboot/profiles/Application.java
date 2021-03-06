package com.springboot.profiles;

import com.springboot.profiles.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

    private Config config;
    private Environment environment;

    @Autowired
    Application(Config config, Environment environment) {
        this.config = config;
        this.environment = environment;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .profiles("prod")
                .run(args);
    }

    @Bean
    CommandLineRunner execute() {
        return args -> {
            config.setup();
            environmentSpecificMethod();
        };
    }

    void environmentSpecificMethod() {
        if (environment.acceptsProfiles("prod")) {
            System.out.println("This will be executed only in production mode");
            //do some stuff here
        } else {
            System.out.println("This will be executed for all other profiles");
        }
    }
}