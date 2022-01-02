package jpaproject.knockknock;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class KnockKnockApplication {

    private static final Logger logger = LoggerFactory.getLogger(KnockKnockApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KnockKnockApplication.class, args);
        logger.info("--------------------");
        logger.debug("-----------------------");

    }

}
