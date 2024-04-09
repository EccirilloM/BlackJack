package it.polimi.blackjackbe;

import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlackJackBeApplication {

    private static Environment env;

    public BlackJackBeApplication(Environment env) {
        BlackJackBeApplication.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlackJackBeApplication.class, args);
        System.out.println("Server listening on port " + env.getProperty("server.port"));
    }

}
