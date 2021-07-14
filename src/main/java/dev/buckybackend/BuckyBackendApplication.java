package dev.buckybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableJpaAuditing
@EnableRedisHttpSession
public class BuckyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuckyBackendApplication.class, args);
    }

}
