package br.com.asd4m.javaspringidea.controllers;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExampleController
 *
 * @author ASD-Morning-4
 *
 */

@RestController // thic class means it is ready for use by Spring MVC to handle web requests
//@RequestMapping("/api/example")
public class ExampleController {

    //@GetMapping("/hello-world")
    //public ResponseEntity<String> get() {
    //    return ResponseEntity.ok("Hello World!");
    //}

    // http://localhost:8080/
    @RequestMapping("/") // this maps to the index() method
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
