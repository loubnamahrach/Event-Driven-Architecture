package ma.enset.kafkasupplierservice.service;

import ma.enset.kafkasupplierservice.entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class PageEventSupplier {

    private static final String[] PAGES = {"P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8"};
    private static final String[] USERS = {"user1", "user2", "user3", "user4", "user5"};
    private final Random random = new Random();

    @Bean
    public Supplier<PageEvent> pageEventSupplier() {
        return () -> {
            String page = PAGES[random.nextInt(PAGES.length)];
            String user = USERS[random.nextInt(USERS.length)];
            long duration = (long)(100 + Math.random() * 9900);

            PageEvent event = new PageEvent(page, user, new Date(), duration);
            System.out.println("[SUPPLIER] Publishing event: " + event);
            return event;
        };
    }
}
// Supplier bean configured
