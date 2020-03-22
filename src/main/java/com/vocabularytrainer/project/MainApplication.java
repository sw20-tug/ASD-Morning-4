package com.vocabularytrainer.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Show all beans (1)
/*
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
*/

@SpringBootApplication
public class MainApplication {

    // Main Class
	public static void main(String[] args) {

        // Start SpringApplication
	    SpringApplication.run(MainApplication.class, args);
	}

	// Show all beans (2)
    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
    */
}
