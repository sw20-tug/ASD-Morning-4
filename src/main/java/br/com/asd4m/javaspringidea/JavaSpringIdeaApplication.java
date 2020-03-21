package br.com.asd4m.javaspringidea;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaSpringIdeaApplication {

    // Main Class
	public static void main(String[] args) {

	    // here you can write "classic" java ...
	    // System.out.println("This is som text\n");

        // Start SpringApplication
	    SpringApplication.run(JavaSpringIdeaApplication.class, args);
	}

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
