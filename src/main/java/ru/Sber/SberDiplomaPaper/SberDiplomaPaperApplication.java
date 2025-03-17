package ru.Sber.SberDiplomaPaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SberDiplomaPaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SberDiplomaPaperApplication.class, args);
    }

}
