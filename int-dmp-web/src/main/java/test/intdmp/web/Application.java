package test.intdmp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("test.intdmp")
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}
