package kz.onetech.onetechproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy

public class OneTechProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneTechProjectApplication.class, args);
    }
}
