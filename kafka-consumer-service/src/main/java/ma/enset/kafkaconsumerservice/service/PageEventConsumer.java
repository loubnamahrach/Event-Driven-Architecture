package ma.enset.kafkaconsumerservice.service;

import ma.enset.kafkaconsumerservice.entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class PageEventConsumer {

    @Bean
    public Consumer<PageEvent> pageEventConsumer() {
        return (pageEvent) -> {
            System.out.println("*****************************");
            System.out.println("PageEvent Received :");
            System.out.println("  Name     : " + pageEvent.getName());
            System.out.println("  User     : " + pageEvent.getUser());
            System.out.println("  Date     : " + pageEvent.getDate());
            System.out.println("  Duration : " + pageEvent.getDuration() + " ms");
            System.out.println("*****************************");
        };
    }
}
// Consumer function registered
